package nju.software.main;

import java.io.IOException;
import java.util.List;

import nju.software.modifier.rewrite.SmaliRewriter;
import nju.software.modifier.search.SinkPointSearch;
import nju.software.parser.SmaliParser;
import nju.software.repackage.SmaliRepackage;

public class Main {

	// 存放发送短信的方法的smali代码
	static List<String> messageList;

	/*
	 * 1.调用SmaliParser.parser()方法反编译apk文件
	 * 2.调用SinkPointSearch.searchMessageSmalis方法来搜索发送短信的smali方法
	 * 3.调用SmaliRewriter类的相关方法来插入相应的smali代码
	 * 4.调用SmaliRepackage.repackage方法来重新打包新的项目
	 */
	public static void main(String[] args) {

		SmaliParser.parser("apkToSmali.bat");

		try {
			messageList = SinkPointSearch
					.searchMessageSmalis("MessageSmaliExample.smali");
		} catch (IOException e) {
			e.printStackTrace();
		}

		SmaliRewriter smaliRewriter = new SmaliRewriter();
		try {
			smaliRewriter.readSmalis(
					"D://androidAPK//auto-apk-test//SendSMSAutoDapk",
					"CheckMessage.smali", messageList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		SmaliRepackage.repackage("SmaliToApk.bat");
	}

}
