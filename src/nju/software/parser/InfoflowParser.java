package nju.software.parser;

import nju.software.constants.Constants;
import nju.software.constants.InfoflowEnum;

import java.io.*;
import java.util.*;

/**
 * 提取生成的有用的信息流信息
 * <p/>
 * Created by Xie on 2016/2/29.
 */
public class InfoflowParser {

    private static final InfoflowParser infoflowParser = new InfoflowParser();

    public InfoflowParser() {
    }

    public static InfoflowParser v() {
        return infoflowParser;
    }

    public static void main(String[] args) {
        Map map = InfoflowParser.v().generateEntryPointPermissionMap("InterAppCommunication/SendSMS.apk");
        Map map1 = InfoflowParser.v().generateEntryExitMap("InterAppCommunication/SendSMS.apk");
        Map map2 = InfoflowParser.v().generateEntrySinkMap("InterAppCommunication/SendSMS.apk");
        Map map3 = InfoflowParser.v().generateSourceExitMap("InterAppCommunication/SendSMS.apk");
        Map map4 = InfoflowParser.v().generateSourceSinkMap("InterAppCommunication/SendSMS.apk");
        System.out.println(map);
    }

    public static Map<String, Set<String>> generateEntryPointPermissionMap(InputStream is) {
        Map<String, Set<String>> sourceToExitMap = new HashMap<String, Set<String>>();
        try {
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            while (line != null) {
                //表明这是一个入口点方法
                if (line.startsWith("<")) {
                    String method = line;
                    Set<String> permissions = new HashSet<String>();
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

    /**
     * 获取入口点权限的方法映射
     *
     * @param apkName apk名称
     * @return 映射
     */
    public Map<String, Set<String>> generateEntryPointPermissionMap(String apkName) {
        if (apkName.endsWith(".apk")) {
            apkName = apkName.substring(0, apkName.length() - 4);
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

    /**
     * 获取入口点权限的方法映射
     *
     * @param apkName apk名称
     * @return 映射
     */
    public Map<String, Set<String>> generateEntryExitMap(String apkName) {
        if (apkName.endsWith(".apk")) {
            apkName = apkName.substring(0, apkName.length() - 4);
        }
        Map<String, Set<String>> entryToExitMap = new HashMap<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(apkName + Constants.fileSpliter + InfoflowEnum.ENTRYTOEXIT+".txt")));
            entryToExitMap = generateMap(bufferedReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entryToExitMap;
    }

    /**
     * 获取入口点权限的方法映射
     *
     * @param apkName apk名称
     * @return 映射
     */
    public Map<String, Set<String>> generateEntrySinkMap(String apkName) {
        if (apkName.endsWith(".apk")) {
            apkName = apkName.substring(0, apkName.length() - 4);
        }
        Map<String, Set<String>> entryToSinkMap = new HashMap<>();
        try {
            File file = new File(apkName + Constants.fileSpliter + InfoflowEnum.ENTRYTOSINK+".txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            entryToSinkMap = generateMap(bufferedReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entryToSinkMap;
    }

    /**
     * 获取入口点权限的方法映射
     *
     * @param apkName apk名称
     * @return 映射
     */
    public Map<String, Set<String>> generateSourceExitMap(String apkName) {
        if (apkName.endsWith(".apk")) {
            apkName = apkName.substring(0, apkName.length() - 4);
        }
        Map<String, Set<String>> sourceToExitMap = new HashMap<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(apkName + Constants.fileSpliter + InfoflowEnum.SOURCETOEXIT+".txt")));
            sourceToExitMap = generateMap(bufferedReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sourceToExitMap;
    }


    /**
     * 获取源点至沉淀点的方法映射
     *
     * @param apkName apk名称
     * @return 映射
     */
    public Map<String, Set<String>> generateSourceSinkMap(String apkName) {
        if (apkName.endsWith(".apk")) {
            apkName = apkName.substring(0, apkName.length() - 4);
        }
        Map<String, Set<String>> entryToSinkMap = new HashMap<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(apkName + Constants.fileSpliter + InfoflowEnum.SOURCETOSINK+".txt")));
            entryToSinkMap = generateMap(bufferedReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entryToSinkMap;
    }

    /**
     * 根据缓冲流获得相应的映射
     *
     * @param bufferedReader
     * @return
     * @throws IOException
     */
    private Map<String, Set<String>> generateMap(BufferedReader bufferedReader) throws IOException {
        Map<String, Set<String>> map = new HashMap<>();
        String line = bufferedReader.readLine();
        while (line != null) {
            if (line.startsWith("Sink")) {
                String sink = bufferedReader.readLine();
                Set<String> sources = new HashSet<>();
                line = bufferedReader.readLine();
                if (line.startsWith("Source"))
                    line = bufferedReader.readLine();
                while (line != null && !line.startsWith("Sink")) {
                    sources.add(line);
                    line = bufferedReader.readLine();
                }
                map.put(sink, sources);
            }
        }
        return map;
    }
}