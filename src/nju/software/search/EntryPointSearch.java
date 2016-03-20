package nju.software.search;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用来搜寻入口点的smali代码
 * <p/>
 * Created by Xie on 2016/2/3.
 */
public class EntryPointSearch {

    public static List<String> searchSmalis(String filePath) {
        List<String> messageList = new ArrayList<>();
        try {
            File file = new File(filePath);
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lineText = null;
            // 遍历文件的每一行，查找发送短信的相关代码
            while ((lineText = bufferedReader.readLine()) != null) {
                if (lineText.contains(".method")) {
//					System.out.println("text:  " + lineText.replace(" ", ""));
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
