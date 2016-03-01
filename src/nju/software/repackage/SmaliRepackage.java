package nju.software.repackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SmaliRepackage {

	/*
	 * 调用执行apktool的bat文件将已经插入了smali代码的smali文件重新打包生成apk文件
	 */
	public static void repackage(String filePath) {
		System.out.println("run repackaging ..");
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
			System.out.println("finish repackaging");
		} catch (IOException e) {
			System.out.println("执行SmaliToApk.bat文件出错！");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
