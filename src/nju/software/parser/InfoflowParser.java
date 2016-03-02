package nju.software.parser;

import nju.software.constants.Constants;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 提取生成的有用的信息流信息
 * <p/>
 * Created by Xie on 2016/2/29.
 */
public class InfoflowParser {

    private static final InfoflowParser infoflowParser = new InfoflowParser();

    public InfoflowParser() {
    }

    public static InfoflowParser v () {
        return infoflowParser;
    }

    public static void main(String[] args) {
        Map map = InfoflowParser.v().generateEntryPointPermissionMap("InterAppCommunication/SendSMS.apk");
        System.out.println(map);
    }

    /**
     * 获取入口点权限的方法映射
     *
     * @param apkName apk名称
     * @return 映射
     */
    public Map<String, Set<String>> generateEntryPointPermissionMap(String apkName) {
        if (apkName.endsWith(".apk")) {
            apkName = apkName.substring(0, apkName.length()-4);
        }
        Map<String, Set<String>> sourceToExitMap = new HashMap<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(apkName + Constants.fileSpliter + "ENTRYPERMISSIONS.txt")));
            String line = bufferedReader.readLine();
            while (line != null) {
                //表明这是一个入口点方法
                if (line.startsWith("<")) {
                    String method = line;
                    Set<String> permissions = new HashSet<>();
                    line = bufferedReader.readLine();
                    while (line != null && !line.startsWith("<")) {
                        permissions.add(line);
                        line = bufferedReader.readLine();
                    }
                    sourceToExitMap.put(method, permissions);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sourceToExitMap;
    }

}