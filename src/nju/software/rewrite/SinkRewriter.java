//package nju.software.rewrite;
//
//import java.io.*;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 对所有沉淀点进行查询，之后进行插入校验代码的过程
// * <p/>
// * Created by Xie on 2016/3/11.
// */
//public class SinkRewriter extends AbstractRewriter {
//    @Override
//    public void search(String smaliFile, String ruleFile, List<String> messageList) {
//        try {
//            File file = new File(smaliFile);
//            InputStream inputStream = null;
//            inputStream = new FileInputStream(file);
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            // 记录行号，为之后要插入的位置提供标记
//            int lineNumber = 0;
//            // 记录第一行内容，从中提取出包名
//            String firstLine = null;
//            // 记录所有的需要修改的行filePath
//            Map<Integer, Integer> insertMap = new HashMap<>();
//            // 遍历文件的每一行，查找发送短信的相关代码
//            firstLine = generateFirstLine(bufferedReader);
//            insertMap = generateInsertmap(messageList, bufferedReader, lineNumber, firstLine, insertMap);
//            bufferedReader.close();
//            inputStreamReader.close();
//            inputStream.close();
//            // 调用插入smali语句的方法
//            insertSmalis(file, insertMap, firstLine, new File(ruleFile));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void insert(File objectFile, String packageName, Map<Integer, Integer> insertMap, String firstLine) {
//        Object[] keys = insertMap.keySet().toArray();
//        Arrays.sort(keys);
//
//        int index = 0;
//        for (int i = keys.length - 1; i >= 0; i--) {
//            int lineNumber = (Integer) keys[i];
//            System.out.println("行号" + lineNumber);
//            int methodType = insertMap.get(lineNumber);
//            // 要插入的代码内容
//            String lineToBeInserted = null;
//            switch (methodType) {
//                case 1:
//                    lineToBeInserted = "";
//                    break;
//                case 2:
//                    lineToBeInserted =
//                            "";
//                    break;
//                case 3:
//                    lineToBeInserted =
//                            "";
//                    break;
//                case 4:
//                    lineToBeInserted =
//                            "";
//                    break;
//                case 5:
//                    lineToBeInserted =
//                            "invoke-static {v1, v3, p0}, "
//                                    + packageName
//                                    + "CheckMessageRule;->checkSMSMessage(Ljava/lang/String;Ljava/lang/String;Landroid/app/Activity;)V";
//                    break;
//                case 6:
//                    lineToBeInserted =
//                            "";
//                    break;
//                default:
//                    break;
//            }
//            try {
//                insertStringInFile(objectFile, (lineNumber + index),
//                        lineToBeInserted);
//            } catch (Exception e) {
//                System.out.println("插入smali代码时出错！");
//            }
//            index++;
//        }
//    }
//
//    protected Map generateInsertmap(List<String> messageList, BufferedReader bufferedReader, int lineNumber, String firstLine, Map<Integer, Integer> insertMap) throws IOException {
//        String lineText;
//        while ((lineText = bufferedReader.readLine()) != null) {
//            lineNumber++;
//            int methodType;
//            for (String message : messageList) {
//                if (lineText.replace(" ", "").equals(message.replace(" ", ""))) {
//                    if (lineText.contains("Lorg/apache/http/client/HttpClient;->execute(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;")) {
//                        insertMap.put(lineNumber + 1, 1);
//                        break;
//                    } else if (lineText.contains("Ljava/io/OutputStream;->write([B)V")) {
//                        insertMap.put(lineNumber + 1, 2);
//                        break;
//                    } else if (lineText.contains("Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I")
//                            || lineText.contains("Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I")
//                            || lineText.contains("Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I")
//                            || lineText.contains("Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I")
//                            || lineText.contains("Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I")
//                            || lineText.contains("Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I")
//                            || lineText.contains("Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I")
//                            || lineText.contains("Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I")
//                            || lineText.contains("Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I")
//                            || lineText.contains("Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/Throwable;)I")
//                            || lineText.contains("Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I")
//                            || lineText.contains("Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/String;)I")
//                            || lineText.contains("Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/Throwable;)I")
//                            || lineText.contains("Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I")
//                            ) {
//                        insertMap.put(lineNumber, 3);
//                    } else if (lineText.contains("Landroid/media/MediaRecorder;->setAudioSource(I)V")) {
//                        insertMap.put(lineNumber, 4);
//                    } else if (lineText.contains("Landroid/telephony/SmsManager;->sendTextMessage(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/app/PendingIntent;Landroid/app/PendingIntent;)V")) {
//                        insertMap.put(lineNumber, 5);
//                    } else {
//                        insertMap.put(lineNumber, 6);
//                    }
//                }
//            }
//        }
//        return insertMap;
//    }
//
//    private class Data{
//        private int type;
//        private String args;
//
//        public Data(int type, String args) {
//            this.type = type;
//            this.args = args;
//        }
//
//        public int getType() {
//            return type;
//        }
//
//        public void setType(int type) {
//            this.type = type;
//        }
//
//        public String getArgs() {
//            return args;
//        }
//
//        public void setArgs(String args) {
//            this.args = args;
//        }
//    }
//}