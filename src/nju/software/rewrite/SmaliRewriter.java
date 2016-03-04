package nju.software.rewrite;

import nju.software.constants.SmaliFileEnum;

import java.io.File;
import java.util.List;

/**
 * 用于将具体的规则插入到目标代码中 流程如下：（1）搜索目标代码，查找指定入口点和出口点 （2）在搜索到的点插入相应的规则 Created by Xie
 * on 2016/2/3.
 */
public class SmaliRewriter{

    /**
     * 遍历项目，同归递归，查找所有的后缀为smali的文件
     *
     * @param filePath
     * @param messageList
     * @throws Exception
     */
    public void readSinkSmalis(String filePath, List<String> messageList) throws Exception {
        File root = new File(filePath);
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                // 递归调用
                readSinkSmalis(file.getAbsolutePath(), messageList);
            } else {
                // 对所有的smali文件调用searchSMS方法处理
                //TODO 在这边我们可以针对想要调用的入口点方法，沉淀点方法进行查找
                if (file.getName().endsWith(".smali")) {
                    SmsRewriter smsRewriter = new SmsRewriter();
                    smsRewriter.search(file.getAbsolutePath(), SmaliFileEnum.SMS.getFileName(), messageList);
                    ActivityRewriter activityRewriter = new ActivityRewriter();
                    activityRewriter.search(file.getAbsolutePath(), SmaliFileEnum.ENTRY.getFileName(), messageList);
                }
            }
        }
    }

    public void readEntrySmalis(String filePath, List<String> messageList) throws Exception {
        File root = new File(filePath);
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                // 递归调用
                readEntrySmalis(file.getAbsolutePath(), messageList);
            } else {
                // 对所有的smali文件调用searchSMS方法处理
                //TODO 在这边我们可以针对想要调用的入口点方法，沉淀点方法进行查找
                if (file.getName().endsWith(".smali")) {
                    ActivityRewriter activityRewriter = new ActivityRewriter();
                    activityRewriter.search(file.getAbsolutePath(), SmaliFileEnum.ENTRY.getFileName(), messageList);
                }
            }
        }
    }

}