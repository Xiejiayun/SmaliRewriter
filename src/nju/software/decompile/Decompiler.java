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

    public static void main(String[] args) {
        decompile("InterAppCommunication/SendSMS.apk");
    }
    /**
     * 调用执行apktool的bat文件将一个apk文件反编译成smali文件
     *
     * @param apkFile apk文件路径
     */
    public static void decompile(String apkFile) {
        String decomilePath = apkFile.substring(0, apkFile.length()-4);
        System.out.println("run decompiling...");
        Runtime rt = Runtime.getRuntime();
        Process process = null;
        try {
            process = rt.exec("cmd.exe /c apktool d -f "+apkFile+" -o "+decomilePath);
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
            System.out.println("执行Decompiler decompile操作出错！");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
