package nju.software.decompile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 反编译
 *
 * Created by Xie on 2016/3/1.
 */
public class Decompiler {

    /**
     * 调用执行apktool的bat文件将一个apk文件反编译成smali文件
     *
     * @param filePath bat文件路径
     */
    public static void decompile(String filePath) {
        System.out.println("run decompiling...");
        Runtime rt = Runtime.getRuntime();
        Process process = null;
        try {
            process = rt.exec("cmd.exe /c " + filePath);
            process.waitFor();

            InputStream fis = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("finish decompiling...");
        } catch (IOException e) {
            System.out.println("执行apkToSmali.bat文件出错！");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
