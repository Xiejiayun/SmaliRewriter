package nju.software.transformer;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * 文件转换器，主要负责将生成的资源文件自动传入反编译的apk目录下
 * <p/>
 * Created by Xie on 2016/3/9.
 */
public class FileTransformer {

    public static void transform(String apkFile) {
        apkFile = apkFile.endsWith(".apk") ? apkFile.substring(0, apkFile.length() - 4) : apkFile;
        String sourceDir = apkFile + "data";
        String desDir = apkFile + "/res/raw/";
        transform(sourceDir, desDir);
    }

    public static void transform(String sourceDir, String destDir) {
        File sourceDirectory = new File(sourceDir);
        File destDirectory = new File(destDir);
        if (!destDirectory.exists())
            destDirectory.mkdirs();
        File files[] = sourceDirectory.listFiles();
        for (File source : files) {
            String sourcePath = source.getPath();
            String[] paths = sourcePath.split("\\\\");
            File dest = new File(destDirectory + "\\" + paths[paths.length - 1]);
            if (!dest.exists())
                try {
                    dest.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            try (
                    FileInputStream fi = new FileInputStream(source);
                    FileOutputStream fo = new FileOutputStream(dest);
                    FileChannel in = fi.getChannel();// 得到对应的文件通道
                    FileChannel out = fo.getChannel()// 得到对应的文件通道
            ) {
                in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void transform(File source, File dest) throws Exception {
        if (!dest.exists())
            dest.createNewFile();
        try (
                FileInputStream fi = new FileInputStream(source);
                FileOutputStream fo = new FileOutputStream(dest);
                FileChannel in = fi.getChannel();// 得到对应的文件通道
                FileChannel out = fo.getChannel()// 得到对应的文件通道
        ) {
            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
