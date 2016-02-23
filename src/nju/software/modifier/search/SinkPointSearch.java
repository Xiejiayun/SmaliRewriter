package nju.software.modifier.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xie on 2016/2/3.
 */
public class SinkPointSearch {

	static File file;
	static BufferedReader bufferedReader;
	static FileInputStream inputStream;
	static InputStreamReader inputStreamReader;
	static List<String> messageList;

	/*
	 * 查找发送短信的smali代码
	 */
	public static List<String> searchMessageSmalis(String filePath)
			throws IOException {
		messageList = new ArrayList<String>();
		file = new File(filePath);
		inputStream = new FileInputStream(file);
		inputStreamReader = new InputStreamReader(inputStream);
		bufferedReader = new BufferedReader(inputStreamReader);
		String lineText = null;
		// 遍历文件的每一行，查找发送短信的相关代码
		while ((lineText = bufferedReader.readLine()) != null) {
			if (lineText.contains("sendTextMessage")
					|| lineText.contains("sendDataMessage")
					|| lineText.contains("sendMultipartTextMessage")) {
				System.out.println("text:  " + lineText.replace(" ", ""));
				messageList.add(lineText);
			}
		}
		bufferedReader.close();
		inputStreamReader.close();
		inputStream.close();
		return messageList;
	}

	/*
	 * 查找通过网络方式泄露数据的smali方法
	 */
	public static List<String> searchNetworkSmalis(String filePath)
			throws IOException {
		return messageList;
	}
}
