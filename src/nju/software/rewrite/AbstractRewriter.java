package nju.software.rewrite;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;

/**
 * Created by Xie on 2016/3/3.
 */
public abstract class AbstractRewriter {

    /**
     * （1）搜索目标代码
     *
     * @param smaliFile
     * @param ruleFile
     * @param messageList
     * @throws Exception
     */
    public abstract void search(String smaliFile, String ruleFile, List<String> messageList);

    /**
     * （2）在搜索到的点插入相应的规则 首先将要添加的smali文件拷贝到相应的目录下 其次，在被添加的相应smali文件中添加invoke代码
     * objectFile是要添加smali文件的目标文件 copyFile是要拷贝的smali文件 lineNumber是要出入的位置的行号
     * methodType是要处理的发送短信的方式的类别 firstLine是文件的第一行内容，包含包名，等一下要从中提取出包名
     *
     * @param objectFile 目标文件
     * @param copyFiles  拷贝文件
     * @param insertMap  插入映射
     * @param firstLine  第一行数据
     * @throws Exception
     */
    public void insertSmalis(File objectFile,
                             Map<Integer, Integer> insertMap,
                             String firstLine, File... copyFiles) throws Exception {
        if (insertMap.size() == 0) {
            return;
        }
        System.out.println("firstLine: " + firstLine);

        // System.out.println("lineNumber:" + lineNumber);
        File parentDir = objectFile.getParentFile();
        // 提取包名
        String[] decodeTemp = firstLine.split(" ");
        String temp = decodeTemp[decodeTemp.length - 1];
        // System.out.println("temp: " + temp);
        String packageName = temp.substring(0, temp.lastIndexOf('/') + 1);

        if (copyFiles != null) {
            for (File copyFile : copyFiles) {
                // 将要添加的smali文件file2拷贝到相应的目录file下，名字同file2一样
                File file3 = new File(parentDir.getAbsolutePath() + "/"
                        + copyFile.getName());
                fileChannelCopy(copyFile, file3);
                modifyFilterFile(packageName, file3);
            }
        }

        // 根据实际的情况调用不同的插入方法，分别对发送短信和通过网络的方式插入相应的代码
        //具体实现延迟到子类
        try {
            insert(objectFile, packageName, insertMap, firstLine);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * （3）在关键点前插入代码
     *
     * @param objectFile
     * @param packageName
     * @param insertMap
     * @param firstLine
     */
    public abstract void insert(File objectFile, String packageName,
                                Map<Integer, Integer> insertMap, String firstLine);

    /**
     * 使用文件通道的方式复制文件
     *
     * @param source 源文件
     * @param dest   目标文件
     * @throws Exception
     */
    private void fileChannelCopy(File source, File dest) throws Exception {
        FileInputStream fi = new FileInputStream(source);
        FileOutputStream fo = new FileOutputStream(dest);
        FileChannel in = fi.getChannel();// 得到对应的文件通道
        FileChannel out = fo.getChannel();// 得到对应的文件通道
        try {
            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在文件里面的指定行插入一行数据
     *
     * @param inFile           文件
     * @param lineno           行号
     * @param lineToBeInserted 要插入的数据
     * @throws Exception IO操作引发的异常
     */
    protected void insertStringInFile(File inFile, int lineno,
                                      String lineToBeInserted) throws Exception {
        if (lineToBeInserted == null)
            return;
        System.out.println("Mehtod:insertStringInFile!   "
                + inFile.getAbsolutePath());
        System.out.println("Method:lineno" + lineno);
        System.out.println("Method:lineToBeInserted " + lineToBeInserted);
        // 临时文件
        File outFile = new File("tmp.tmp");

        // 输入
        FileInputStream fis = new FileInputStream(inFile);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
        // 输出
        FileOutputStream fos = new FileOutputStream(outFile);
        PrintWriter out = new PrintWriter(fos);
        // 保存一行数据
        String thisLine = null;
        // 行号从1开始
        int i = 1;
        while ((thisLine = in.readLine()) != null) {
            // 如果行号等于目标行，则输出要插入的数据
            if (i == lineno) {
                System.out.println("lineToBeInserted: " + lineToBeInserted);
                out.println(lineToBeInserted);
            }
            // 输出读取到的数据
            out.println(thisLine);
//            System.out.println(thisLine);
            // 行号增加
            i++;
        }
        out.flush();
        out.close();
        in.close();
        // 删除原始文件
        inFile.delete();
        // 把临时文件改名为原文件名
        outFile.renameTo(inFile);

        System.out.println("over!");
    }

    /**
     * 修改添加的监控文件的包名
     *
     * @param packageName 包名
     * @param inFile      输入文件
     * @throws IOException
     */
    private void modifyFilterFile(String packageName, File inFile)
            throws IOException {
        System.out.println("rename!");
        // 临时文件
        File outFile = File.createTempFile("name", ".tmp");
        // 输入
        FileInputStream fis = new FileInputStream(inFile);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
        // 输出
        FileOutputStream fos = new FileOutputStream(outFile);
        PrintWriter out = new PrintWriter(fos);

        // 保存一行数据
        String thisLine;

        // 行号从1开始
        int i = 1;
        while ((thisLine = in.readLine()) != null) {
            // 如果行号等于目标行，则输出要插入的数据
            if (i == 1) {
                String className = thisLine
                        .substring(thisLine.lastIndexOf("/") + 1);
                String newPackageLine = ".class public " + packageName
                        + className;

                System.err.println("newPacKLine:" + newPackageLine);
                out.println(newPackageLine);

                i++;
            }
            // 输出读取到的数据
            else {
                out.println(thisLine);
            }
        }
        out.flush();
        out.close();
        in.close();
        // 删除原始文件
        inFile.delete();
        // 把临时文件改名为原文件名
        outFile.renameTo(inFile);
    }

    /**
     * 生成第一行的信息
     *
     * @return
     */
    protected String generateFirstLine(BufferedReader bufferedReader) {
        String lineText, firstLine = null;
        try {
            while ((lineText = bufferedReader.readLine()) != null) {
                if (lineText.contains(".class")) {
                    firstLine = lineText;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return firstLine;
    }
}