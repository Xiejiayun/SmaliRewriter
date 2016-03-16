package nju.software.rewrite;

import nju.software.constants.SmaliFileEnum;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    public void readSinkSmalis(String filePath, List<String> messageList, int depth) throws Exception {
        File root = new File(filePath);
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if (depth == 0 && file.getName().endsWith("android"))
                    continue;
                // 递归调用
                readSinkSmalis(file.getAbsolutePath(), messageList, depth+1);
            } else {
                // 对所有的smali文件调用searchSMS方法处理
                //TODO 在这边我们可以针对想要调用的入口点方法，沉淀点方法进行查找
                if (file.getName().endsWith(".smali")) {
                    SmsRewriter smsRewriter = new SmsRewriter();
                    smsRewriter.search(file.getAbsolutePath(), SmaliFileEnum.SMS.getFileName(), messageList);
                    ActivityRewriter activityRewriter = new ActivityRewriter();
                    activityRewriter.search(file.getAbsolutePath(), SmaliFileEnum.ENTRY.getFileName(), messageList);
//                    LogRewriter logRewriter = new LogRewriter();
//                    logRewriter.search(file.getAbsolutePath(), SmaliFileEnum.LOG.getFileName(), messageList);
                }
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
                readEntrySmalis(file.getAbsolutePath(), messageList, depth+1);
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
                updateResourceSmalis(file.getAbsolutePath(), depth+1);
            } else {
                // 对所有的smali文件调用searchSMS方法处理
                if (file.getName().equals("R.smali")) {
                    //添加相应的
                    ResourceRewriter resourceRewriter = new ResourceRewriter();
                    List<String> messageList = new ArrayList<>();
                    messageList.add("value = {");
                    resourceRewriter.search(file.getAbsolutePath(), SmaliFileEnum.RAW.getFileName(),messageList);
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