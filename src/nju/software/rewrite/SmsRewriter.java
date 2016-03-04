package nju.software.rewrite;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xie on 2016/3/3.
 */
public class SmsRewriter extends AbstractRewriter {
    @Override
    public void search(String smaliFile, String ruleFile, List<String> messageList) {
        File file = new File(smaliFile);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lineText = null;
            // 记录行号，为之后要插入的位置提供标记
            int lineNumber = 0;
            // 记录第一行内容，从中提取出包名
            String firstLine = null;

            // 记录所有的需要修改的行filePath
            Map<Integer, Integer> insertMap = new HashMap<>();
            // 遍历文件的每一行，查找发送短信的相关代码
            firstLine = generateFirstLine(bufferedReader);
            insertMap = generateInsertmap(messageList, bufferedReader, lineNumber, firstLine, insertMap);
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            // 调用插入smali语句的方法
            insertSmalis(file, new File(ruleFile), insertMap, firstLine);
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
                            + "CheckMessageRule;->checkSMSMessage(Ljava/lang/String;Ljava/lang/String;Landroid/app/Activity;)V";
                    break;
                case 2:
                    lineToBeInserted = "invoke-static {v1, v4, p0}, "
                            + packageName
                            + "CheckMessageRule;->checkSMSMessage(Ljava/lang/String;Ljava/lang/String;Landroid/app/Activity;)V";
                    break;
                case 3:
                    lineToBeInserted = "invoke-static {v1, v3, p0}, "
                            + packageName
                            + "CheckMessageRule;->checkSMSmessageWithList(Ljava/lang/String;Ljava/util/ArrayList;Landroid/app/Activity;)V";
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

    protected Map generateInsertmap(List<String> messageList, BufferedReader bufferedReader, int lineNumber, String firstLine, Map<Integer, Integer> insertMap) throws IOException {
        String lineText;
        while ((lineText = bufferedReader.readLine()) != null) {
            lineNumber++;
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
        return insertMap;
    }
}