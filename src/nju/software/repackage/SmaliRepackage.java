package nju.software.repackage;

import nju.software.transformer.FileTransformer;

import java.io.*;

public class SmaliRepackage {

    public static void main(String[] args) {
        repackage("InterAppCommonication/SendSMS.apk");
    }

    /*
     * 调用执行apktool的bat文件将已经插入了smali代码的smali文件重新打包生成apk文件
     */
    public static void repackage(String apkFile) {
        String repackagePath = apkFile.substring(0, apkFile.length() - 4);
        System.out.println("run repackaging ..");
        String[] paths = apkFile.split("/");
        String apkName = paths[paths.length - 1];
        Runtime rt = Runtime.getRuntime();
        Process process = null;
        try {
            System.out.println("start building ..");
            process = rt.exec("cmd.exe /c apktool b " + repackagePath + " -o " + repackagePath + "/New" + apkName);
            process.waitFor();
            InputStream fis = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("finish building ..");
            FileTransformer.transform(new File("debug.keystore"), new File(repackagePath + "/debug.keystore"));
//            System.out.println("start jarsigner ..");
//            process = rt.exec("cmd.exe /c jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore debug.keystore "+repackagePath+"/New"+apkName+" android\n");
//            process.waitFor();
//            fis = process.getInputStream();
//            isr = new InputStreamReader(fis);
//            br = new BufferedReader(isr);
//            line = "";
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//            }
//            System.out.println("finish jarsigner ..");

            System.out.println("finish repackaging");
        } catch (IOException e) {
            System.out.println("执行SmaliToApk.bat文件出错！");
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}