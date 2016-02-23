package nju.software.modifier.rewrite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于将具体的规则插入到目标代码中 流程如下：（1）搜索目标代码，查找指定入口点和出口点 （2）在搜索到的点插入相应的规则 Created by Xie
 * on 2016/2/3.
 */
public class SmaliRewriter {

	/*
	 * 遍历项目，同归递归，查找所有的后缀为smali的文件
	 */
	public void readSmalis(String filePath, String filePath2,
			List<String> messageList) throws Exception {
		File root = new File(filePath);
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				// 递归调用
				readSmalis(file.getAbsolutePath(), filePath2, messageList);
			} else {
				// 对所有的smali文件调用searchSMS方法处理
				if (file.getName().endsWith(".smali")) {
					try {
						searchSMS(file.getAbsolutePath(), filePath2,
								messageList);
					} catch (IOException e) {
						System.out.println("处理文件" + file.getName() + "时出现错误！");
						System.out.println(e.toString());
					}
				}
			}
		}
	}

	/*
	 * （1）搜索目标代码，查找指定入口点和出口点 查找发送短信的代码
	 */
	public void searchSMS(String filePath, String filePath2,
			List<String> messageList) throws Exception {

		File file = new File(filePath);
		InputStream inputStream = new FileInputStream(file);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String lineText = null;
		// 记录行号，为之后要插入的位置提供标记
		int lineNumber = 0;
		// 记录第一行内容，从中提取出包名
		String firstLine = null;

		// 记录所有的需要修改的行
		Map<Integer, Integer> insertMap = new HashMap<Integer, Integer>();
		// 遍历文件的每一行，查找发送短信的相关代码
		while ((lineText = bufferedReader.readLine()) != null) {
			lineNumber++;
			if (lineText.contains(".class")) {
				firstLine = lineText;
			}
			for (String message : messageList) {
				if (lineText.replace(" ", "").equals(message.replace(" ", ""))) {
					int methodType;
					if (message.contains("sendTextMessage")) {
						methodType = 1;
					} else if (message.contains("sendDataMessage")) {
						methodType = 2;
					} else {
						methodType = 3;
					}

					insertMap.put(lineNumber, methodType);
				}
			}

		}

		bufferedReader.close();
		inputStreamReader.close();
		inputStream.close();

		// 调用插入smali语句的方法
		insertSmalis(file, new File(filePath2), insertMap, firstLine);

	}

	/*
	 * 查找通过网络泄露隐私数据的方法
	 */
	public void searchNetwork(String filePath, String filePath2,
			List<String> networkList) throws Exception {

	}

	/*
	 * （2）在搜索到的点插入相应的规则 首先将要添加的smali文件拷贝到相应的目录下 其次，在被添加的相应smali文件中添加invoke代码
	 * file是要添加smali文件的目标文件 file2是要拷贝的smali文件 lineNumber是要出入的位置的行号
	 * methodType是要处理的发送短信的方式的类别 firstLine是文件的第一行内容，包含包名，等一下要从中提取出包名
	 */
	private void insertSmalis(File file, File file2,
			Map<Integer, Integer> insertMap, String firstLine) throws Exception {
		// System.out.println("methodType: " + methodType);
		if (insertMap.size() == 0) {
			return;
		}
		System.out.println("firstLine: " + firstLine);

		// System.out.println("lineNumber:" + lineNumber);
		File parentDir = file.getParentFile();
		// 将要添加的smali文件file2拷贝到相应的目录file下，名字同file2一样
		File file3 = new File(parentDir.getAbsolutePath() + "/"
				+ file2.getName());

		// System.out.println(parentDir.getAbsolutePath());
		// 提取包名
		String[] decodeTemp = firstLine.split(" ");
		String temp = decodeTemp[decodeTemp.length - 1];
		// System.out.println("temp: " + temp);
		String packageName = temp.substring(0, temp.lastIndexOf('/') + 1);
		// System.out.println("packageName: " + packageName);

		fileChannelCopy(file2, file3);
		modifyFilterFile(packageName, file3);
		// 根据实际的情况调用不同的插入方法，分别对发送短信和通过网络的方式插入相应的代码
		insertSMSSmalis(file, packageName, insertMap, firstLine);
	}

	/*
	 * 在发送短信的方法之前插入相应的代码
	 */
	private void insertSMSSmalis(File file, String packageName,
			Map<Integer, Integer> insertMap, String firstLine) {
		Object[] keys = insertMap.keySet().toArray();
		Arrays.sort(keys);

		int index = 0;
		for (int i = 0; i < keys.length; i++) {

			int lineNumber = (Integer) keys[i];
			System.out.println("行号" + lineNumber);
			int methodType = insertMap.get(lineNumber);

			// 要插入的代码内容
			String lineToBeInserted = null;
			switch (methodType) {
			case 1:
				lineToBeInserted = "invoke-static {v1, v3, p0}, "
						+ packageName
						+ "CheckMessage;->checkSMSMessage(Ljava/lang/String;Ljava/lang/String;Landroid/app/Activity;)V";
				break;
			case 2:
				lineToBeInserted = "invoke-static {v1, v4, p0}, "
						+ packageName
						+ "CheckMessage;->checkSMSMessage(Ljava/lang/String;Ljava/lang/String;Landroid/app/Activity;)V";
				break;
			case 3:
				lineToBeInserted = "invoke-static {v1, v3, p0}, "
						+ packageName
						+ "CheckMessage;->checkSMSmessageWithList(Ljava/lang/String;Ljava/util/ArrayList;Landroid/app/Activity;)V";
				break;
			default:
				break;
			}

			try {
				insertStringInFile(file, (lineNumber - 1 + index),
						lineToBeInserted);
			} catch (Exception e) {
				System.out.println("插入smali代码时出错！");
			}
			index++;
		}
	}

	/*
	 * 在通过网络的方法前后插入代码（可能前，可能后）
	 */
	@SuppressWarnings("unused")
	private void insertNetworkSmalis(File file, String packageName,
			Map<Integer, Integer> insertMap, String firstLine) {

	}

	/*
	 * 使用文件通道的方式复制文件
	 */
	private void fileChannelCopy(File s, File t) throws Exception {

		FileInputStream fi = new FileInputStream(s);
		FileOutputStream fo = new FileOutputStream(t);
		FileChannel in = fi.getChannel();// 得到对应的文件通道
		FileChannel out = fo.getChannel();// 得到对应的文件通道
		try {

			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 在文件里面的指定行插入一行数据
	 * 
	 * @param inFile
	 *            文件
	 * @param lineno
	 *            行号
	 * @param lineToBeInserted
	 *            要插入的数据
	 * @throws Exception
	 *             IO操作引发的异常
	 */
	private void insertStringInFile(File inFile, int lineno,
			String lineToBeInserted) throws Exception {
		System.out.println("Mehtod:insertStringInFile!   "
				+ inFile.getAbsolutePath());
		System.out.println("Method:lineno" + lineno);
		System.out.println("Method:lineToBeInserted " + lineToBeInserted);
		// 临时文件
		File outFile = File.createTempFile("name", ".tmp");

		// 输入
		FileInputStream fis = new FileInputStream(inFile);
		BufferedReader in = new BufferedReader(new InputStreamReader(fis));
		// 输出
		FileOutputStream fos = new FileOutputStream(outFile);
		PrintWriter out = new PrintWriter(fos);
		// 保存一行数据
		String thisLine;
		// 行号从1开始
		int i = 1;
		while ((thisLine = in.readLine()) != null) {
			// 如果行号等于目标行，则输出要插入的数据
			if (i == lineno) {
				System.out.println("lineToBeInserted: " + lineToBeInserted);
				out.println(lineToBeInserted);
			}
			// 输出读取到的数据
			out.println(thisLine);
			// 行号增加
			i++;
		}
		out.flush();
		out.close();
		in.close();
		// 删除原始文件
		inFile.delete();
		// 把临时文件改名为原文件名
		outFile.renameTo(inFile);

		System.out.println("over!");
	}

	// 修改添加的监控文件的包名
	private void modifyFilterFile(String packageName, File inFile)
			throws IOException {
		System.out.println("rename!");
		// 临时文件
		File outFile = File.createTempFile("name", ".tmp");
		// 输入
		FileInputStream fis = new FileInputStream(inFile);
		BufferedReader in = new BufferedReader(new InputStreamReader(fis));
		// 输出
		FileOutputStream fos = new FileOutputStream(outFile);
		PrintWriter out = new PrintWriter(fos);

		// 保存一行数据
		String thisLine;

		// 行号从1开始
		int i = 1;
		while ((thisLine = in.readLine()) != null) {
			// 如果行号等于目标行，则输出要插入的数据
			if (i == 1) {
				String className = thisLine
						.substring(thisLine.lastIndexOf("/") + 1);
				String newPackageLine = ".class public " + packageName
						+ className;

				System.err.println("newPacKLine:" + newPackageLine);
				out.println(newPackageLine);

				i++;
			}
			// 输出读取到的数据
			else {
				out.println(thisLine);
			}

		}
		out.flush();
		out.close();
		in.close();
		// 删除原始文件
		inFile.delete();
		// 把临时文件改名为原文件名
		outFile.renameTo(inFile);

	}

}
