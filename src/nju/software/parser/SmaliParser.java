package nju.software.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SmaliParser {

	/*
	 * 调用执行apktool的bat文件将一个apk文件反编译成smali文件
	 */
	public static void parser(String filePath) {
		System.out.println("run decompiling...");
		Runtime rt = Runtime.getRuntime();
		Process process = null;
		try {
			process = rt.exec("cmd.exe /c " + filePath);
			process.waitFor();

			InputStream fis = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(fis);

			BufferedReader br = new BufferedReader(isr);

			while ((br.readLine()) != null) {
				// System.out.println("");
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
