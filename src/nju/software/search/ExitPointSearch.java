package nju.software.search;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用来搜寻出口点的smali代码
 * <p/>
 * Created by Xie on 2016/2/29.
 */
public class ExitPointSearch {

    /**
     * 查找通过Activity与其他应用交互的smali代码
     */
    public static List<String> searchActivitySmalis(String filePath) {
        List<String> messageList = new ArrayList<>();
        try {
            File file = new File(filePath);
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lineText = null;
            // 遍历文件的每一行，查找发送短信的相关代码
            while ((lineText = bufferedReader.readLine()) != null) {
                if (lineText.contains("startActivity")
                        || lineText.contains("startActivities")) {
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

    /**
     * 查找通过Service方式与其他应用交互的smali方法
     */
    public static List<String> searchServiceSmalis(String filePath) {
        List<String> messageList = new ArrayList<>();
        try {
            File file = new File(filePath);
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lineText = null;
            // 遍历文件的每一行，查找发送短信的相关代码
            while ((lineText = bufferedReader.readLine()) != null) {
                if (lineText.contains("startService")
                        || lineText.contains("bindService")
                        ) {
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

    /**
     * 查找ContentProvider通过方式与其他应用交互的smali方法
     */
    public static List<String> searchContentSmalis(String filePath) {
        List<String> messageList = new ArrayList<>();
        try {
            File file = new File(filePath);
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lineText = null;
            // 遍历文件的每一行，查找发送短信的相关代码
            while ((lineText = bufferedReader.readLine()) != null) {
                if (lineText.contains("insert")
                        || lineText.contains("delete")
                        || lineText.contains("update")
                        || lineText.contains("query")
                        ) {
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

    /**
     * 查找Broadcast通过方式与其他应用交互的smali方法
     */
    public static List<String> searchBroadcastSmalis(String filePath) {
        List<String> messageList = new ArrayList<>();
        try {
            File file = new File(filePath);
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lineText = null;
            // 遍历文件的每一行，查找发送短信的相关代码
            while ((lineText = bufferedReader.readLine()) != null) {
                if (lineText.contains("sendBroadcast")
                        || lineText.contains("sendBroadcastAsUser")
                        || lineText.contains("sendOrderedBroadcast")
                        || lineText.contains("sendStickyBroadcast")
                        || lineText.contains("sendStickyOrderedBroadcast")
                        ) {
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
