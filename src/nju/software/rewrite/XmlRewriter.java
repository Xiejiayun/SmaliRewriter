package nju.software.rewrite;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对public.xml进行相应的操作来添加相应的资源文件
 *
 * Created by Xie on 2016/3/10.
 */
public class XmlRewriter extends AbstractRewriter {

    public static void main(String[] args) {
        File file = new File("F:\\workspace\\soot-infoflow-android\\InterAppCommunication\\SendSMS\\res\\values\\public.xml");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void search(String smaliFile, String ruleFile, List<String> messageList) {
        File file = new File(smaliFile);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            // 记录行号，为之后要插入的位置提供标记
            int lineNumber = 0;
            // 记录第一行内容，从中提取出包名
            String firstLine = null;
            // 记录所有的需要修改的行filePath
            Map<Integer, Integer> insertMap = new HashMap<>();
            // 遍历文件的每一行，查找发送短信的相关代码
            firstLine = bufferedReader.readLine();
            insertMap = generateInsertmap(messageList, bufferedReader, lineNumber, insertMap);
            bufferedReader.close();
            // 调用插入smali语句的方法
            insertSmalis(file, insertMap, firstLine, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void insert(File objectFile, String packageName, Map<Integer, Integer> insertMap, String firstLine) {
        Object[] keys = insertMap.keySet().toArray();
        Arrays.sort(keys);
        //除去首行，然后在选中的那行下面插入相应的smali代码
        int index = 2;
        for (int i = keys.length-1; i >= 0; i--) {

            int lineNumber = (Integer) keys[i];
            System.out.println("行号" + lineNumber);
            int methodType = insertMap.get(lineNumber);

            // 要插入的代码内容
            String lineToBeInserted = null;
            switch (methodType) {
                case 1:
                    lineToBeInserted = "\t<public type=\"raw\" name=\"entrypermissions\" id=\"0x7fff0000\" />\n" +
                            "\t<public type=\"raw\" name=\"entrytoexit\" id=\"0x7fff0001\" />\n" +
                            "\t<public type=\"raw\" name=\"entrytosink\" id=\"0x7fff0002\" />\n" +
                            "\t<public type=\"raw\" name=\"entrytosource\" id=\"0x7fff0003\" />\n" +
                            "\t<public type=\"raw\" name=\"sourcetoexit\" id=\"0x7fff0004\" />\n" +
                            "\t<public type=\"raw\" name=\"sourcetosink\" id=\"0x7fff0005\" />";
                    break;
                default:
                    break;
            }

            try {
                insertStringInFile(objectFile, (lineNumber + index),
                        lineToBeInserted);
            } catch (Exception e) {
                System.out.println("插入smali代码时出错！");
            }
            index++;
        }
    }

    protected Map generateInsertmap(List<String> messageList, BufferedReader bufferedReader, int lineNumber, Map<Integer, Integer> insertMap) throws IOException {
        String lineText;
        while ((lineText = bufferedReader.readLine()) != null) {
            lineNumber++;
            for (String message : messageList) {
                if (lineText.replace(" ", "").equals(message.replace(" ", ""))) {
                    int methodType;
                    if (message.contains("<resources>")) {
                        methodType = 1;
                    } else {
                        methodType = 2;
                    }
                    insertMap.put(lineNumber, methodType);
                }
            }
        }
        return insertMap;
    }
}
