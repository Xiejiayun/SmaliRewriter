package nju.software.rewrite;

import nju.software.parser.model.SinkInfo;

import java.io.*;
import java.util.*;

/**
 * Created by Xie on 2016/3/15.
 */
public class LogRewriter extends AbstractRewriter implements SinkInfoRewriterInterface{
    Map<Integer, String> paraMap = new HashMap<>();

    @Override
    public void search(String smaliFile, String ruleFile, List<String> messageList) {
        try {
            File file = new File(smaliFile);
            InputStream inputStream = null;
            inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            // 记录行号，为之后要插入的位置提供标记
            int lineNumber = 0;
            // 记录第一行内容，从中提取出包名
            String firstLine = "";
            // 记录所有的需要修改的行filePath
            Map<Integer, Integer> insertMap = new HashMap<>();

            // 遍历文件的每一行，查找发送短信的相关代码
            firstLine = generateFirstLine(bufferedReader);
            insertMap = generateInsertmap(messageList, bufferedReader, lineNumber, firstLine, insertMap);
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            // 调用插入smali语句的方法
            insertSmalis(file, insertMap, firstLine, new File(ruleFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void search(String smaliFile, String ruleFile, Set<SinkInfo> sinkInfos) {
        try {
            File file = new File(smaliFile);
            InputStream inputStream = null;
            inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            // 记录行号，为之后要插入的位置提供标记
            int lineNumber = 0;
            // 记录第一行内容，从中提取出包名
            String firstLine = "";
            // 记录所有的需要修改的行filePath
            Map<Integer, Integer> insertMap = new HashMap<>();
            firstLine = generateFirstLine(bufferedReader);
            insertMap = generateInsertmap(sinkInfos, bufferedReader, insertMap);
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            // 调用插入smali语句的方法
            insertSmalis(file, insertMap, firstLine, new File(ruleFile));
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

        for (int i = keys.length - 1; i >= 0; i--) {
            int lineNumber = (Integer) keys[i];
            System.out.println("行号" + lineNumber);
            int methodType = insertMap.get(lineNumber);
            // 要插入的代码内容
            String lineToBeInserted = null;
            switch (methodType) {
                case 1:
                    lineToBeInserted = paraMap.get(1)
                            + packageName
                            + "CheckLogRule;->checkLog(Ljava/lang/String;Ljava/lang/String;)V";
                    break;
                case 2:
                    lineToBeInserted = paraMap.get(2)
                            + packageName
                            + "CheckLogRule;->checkLog(Ljava/lang/String;Ljava/lang/String;)V";
                    break;
                default:
                    break;
            }
            try {
                insertStringInFile(objectFile, (lineNumber),
                        lineToBeInserted);
            } catch (Exception e) {
                System.out.println("插入smali代码时出错！");
            }
        }
    }

    protected Map generateInsertmap(Set<SinkInfo> sinkInfos, BufferedReader bufferedReader, Map<Integer, Integer> insertMap) throws IOException {
        String lineText;
        List<Integer> lineNumbers = new LinkedList<>();
        for (SinkInfo sinkInfo : sinkInfos) {
            int lineNumber = sinkInfo.getLineNumber();
            lineNumbers.add(lineNumber);
        }
        Collections.sort(lineNumbers);
        int count = sinkInfos.size(), index = 0, lineNumber = 1;
        boolean startSearch = false;
        while ((lineText = bufferedReader.readLine()) != null) {
            lineNumber++;
            //如果已经超过所需插入的行，则停止搜索
            if (index >= count)
                break;
            if (startSearch) {
                if (lineText.contains("Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I")
                        || lineText.contains("Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I")
                        || lineText.contains("Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I")
                        || lineText.contains("Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I")
                        || lineText.contains("Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I")
                        || lineText.contains("Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/Throwable;)I")
                        || lineText.contains("Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/String;)I")
                        || lineText.contains("Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/Throwable;)I")
                        ) {
                    insertMap.put(lineNumber, 1);
                    paraMap.put(1, lineText.split("Landroid")[0]);
                    startSearch = false;
                    index++;
                } else if (lineText.contains("Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I")
                        || lineText.contains("Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I")
                        || lineText.contains("Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I")
                        || lineText.contains("Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I")
                        || lineText.contains("Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I")
                        || lineText.contains("Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I")
                        ) {
                    insertMap.put(lineNumber, 2);
                    paraMap.put(2, lineText.split("}, Landroid")[0]);
                    startSearch = false;
                    index++;
                } else {
                    insertMap.put(lineNumber, 3);
                }
            } else {
                //这边的line其实就是方法调用的行数
                int line = lineNumbers.get(index);
                String lineLabel = ".line " + line;
                if (lineText.contains(lineLabel)) {
                    startSearch = true;
                }
            }
        }
        return insertMap;
    }

    protected Map generateInsertmap(List<String> messageList, BufferedReader bufferedReader, int lineNumber, String firstLine, Map<Integer, Integer> insertMap) throws IOException {
        String lineText;
        while ((lineText = bufferedReader.readLine()) != null) {
            lineNumber++;
            int methodType;
            for (String message : messageList) {
                if (lineText.replace(" ", "").contains("Landroid/util/Log;") && message.replace(" ", "").contains("Landroid/util/Log;")) {
                    if (lineText.contains("Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I")
                            || lineText.contains("Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I")
                            || lineText.contains("Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I")
                            || lineText.contains("Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I")
                            || lineText.contains("Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I")
                            || lineText.contains("Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/Throwable;)I")
                            || lineText.contains("Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/String;)I")
                            || lineText.contains("Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/Throwable;)I")
                            ) {
                        insertMap.put(lineNumber, 1);
                        paraMap.put(1, lineText.split("Landroid")[0]);
                        break;
                    } else if (lineText.contains("Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I")
                            || lineText.contains("Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I")
                            || lineText.contains("Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I")
                            || lineText.contains("Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I")
                            || lineText.contains("Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I")
                            || lineText.contains("Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I")
                            ) {
                        insertMap.put(lineNumber, 2);
                        paraMap.put(2, lineText.split("}, Landroid")[0]);
                        break;
                    } else {
                        insertMap.put(lineNumber, 3);
                    }
                }
            }
        }
        return insertMap;
    }
}