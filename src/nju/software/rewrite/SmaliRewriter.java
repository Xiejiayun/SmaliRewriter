package nju.software.rewrite;

import nju.software.constants.SmaliFileEnum;
import nju.software.parser.SinkParser;
import nju.software.parser.model.SinkInfo;

import java.io.File;
import java.util.*;

/**
 * 用于将具体的规则插入到目标代码中 流程如下：（1）搜索目标代码，查找指定入口点和出口点 （2）在搜索到的点插入相应的规则 Created by Xie
 * on 2016/2/3.
 */
public class SmaliRewriter {

    public static void main(String[] args) {

    }

    /**
     * 遍历项目，同归递归，查找所有的后缀为smali的文件
     *
     * @param filePath
     * @param messageList
     * @throws Exception
     */
    public void readSinkSmalis(String filePath, List<String> messageList, int depth) throws Exception {

        File root = new File(filePath);
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if (depth == 0 && file.getName().endsWith("android"))
                    continue;
                // 递归调用
                readSinkSmalis(file.getAbsolutePath(), messageList, depth + 1);
            } else {
                // 对所有的smali文件调用searchSMS方法处理
                //TODO 在这边我们可以针对想要调用的入口点方法，沉淀点方法进行查找
                if (file.getName().endsWith(".smali")) {
                    SmsRewriter smsRewriter = new SmsRewriter();
                    smsRewriter.search(file.getAbsolutePath(), SmaliFileEnum.SMS.getFileName(), messageList);
                    ActivityRewriter activityRewriter = new ActivityRewriter();
                    activityRewriter.search(file.getAbsolutePath(), SmaliFileEnum.ENTRY.getFileName(), messageList);
                    LogRewriter logRewriter = new LogRewriter();
                    logRewriter.search(file.getAbsolutePath(), SmaliFileEnum.LOG.getFileName(), messageList);
                }
            }
        }
    }

    /**
     * 读取精确的沉淀点的Smali并进行处理
     */
    public void readPreciseSinkSmalis(String filePath) {
        Map<String,Set<SinkInfo>> map = SinkParser.genereateMapInfo(filePath);
        //针对每个clazz做处理，由于一个clazz代表一个类，也就是一个smali文件，所以需要把所有的聚集到一起处理
        for (String clazz : map.keySet()) {
            Set<SinkInfo> sinkInfos = map.get(clazz);
            filePath += "/smali/" + clazz.replaceAll("\\.","\\\\").concat(".smali");
            Set<SinkInfo> logInfos = new HashSet<>();
            Set<SinkInfo> smsInfos = new HashSet<>();
            Set<SinkInfo> netInfos = new HashSet<>();
            for (SinkInfo sinkInfo : sinkInfos) {
                if (sinkInfo.getMethod().contains("Log")) {
                    logInfos.add(sinkInfo);
                } else if (sinkInfo.getMethod().contains("Message")) {
                    smsInfos.add(sinkInfo);
                } else if (sinkInfo.getMethod().contains("net") || sinkInfo.getMethod().contains("http")) {
                    netInfos.add(sinkInfo);
                }
            }
            if (logInfos.size() !=  0) {
                LogRewriter logRewriter = new LogRewriter();
                logRewriter.search(filePath, SmaliFileEnum.LOG.getFileName(), logInfos);
            }
            if (smsInfos.size() != 0) {
                SmsRewriter smsRewriter = new SmsRewriter();
//                smsRewriter
            }
        }
    }

    public void readEntrySmalis(String filePath, List<String> messageList, int depth) throws Exception {
        File root = new File(filePath);
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if (depth == 0 && file.getName().endsWith("android"))
                    continue;
                // 递归调用
                readEntrySmalis(file.getAbsolutePath(), messageList, depth + 1);
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


    public void updateResourceSmalis(String filePath, int depth) {
        File root = new File(filePath);
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if (depth == 0 && file.getName().endsWith("android"))
                    continue;
                // 递归调用
                updateResourceSmalis(file.getAbsolutePath(), depth + 1);
            } else {
                // 对所有的smali文件调用searchSMS方法处理
                if (file.getName().equals("R.smali")) {
                    //添加相应的
                    ResourceRewriter resourceRewriter = new ResourceRewriter();
                    List<String> messageList = new ArrayList<>();
                    messageList.add("value = {");
                    resourceRewriter.search(file.getAbsolutePath(), SmaliFileEnum.RAW.getFileName(), messageList);
                }
            }
        }
    }

    public void updateResourceXML(String filePath) {
        File publicXmlFile = new File(filePath);
        if (!publicXmlFile.exists())
            return;
        List<String> messageList = new ArrayList<>();
        messageList.add("<resources>");
        XmlRewriter xmlRewriter = new XmlRewriter();
        xmlRewriter.search(publicXmlFile.getAbsolutePath(), null, messageList);

    }
}