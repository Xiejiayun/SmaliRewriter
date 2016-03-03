package nju.software.search;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用来搜寻沉淀点的smali代码
 *
 * Created by Xie on 2016/2/3.
 */
public class SinkPointSearch {

	/*
	 * 查找发送短信的smali代码
	 */
	public static List<String> searchMessageSmalis(String filePath) {
		List<String> messageList = new ArrayList<String>();
		try {
			File file = new File(filePath);
			FileInputStream inputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return messageList;
	}

	/*
	 * 查找通过网络方式泄露数据的smali方法
	 */
	public static List<String> searchNetworkSmalis(String filePath) {
		List<String> messageList = new ArrayList<String>();
		try {
			File file = new File(filePath);
			FileInputStream inputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String lineText = null;
			// 遍历文件的每一行，查找发送短信的相关代码
			while ((lineText = bufferedReader.readLine()) != null) {
				if (lineText.contains("write")
						|| lineText.contains("set")
						|| lineText.contains("setRequestProperty")
						|| lineText.contains("connect")
						|| lineText.contains("execute")) {
					System.out.println("text:  " + lineText.replace(" ", ""));
					messageList.add(lineText);
				}
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return messageList;
	}
}