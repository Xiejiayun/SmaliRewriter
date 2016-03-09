package nju.software.parser;

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

    public static final String fileSpliter = "/";

    public enum InfoflowEnum {
        //源点到沉淀点，默认为此
        SOURCETOSINK("SOURCETOSINK"),
        //源点到出口点
        SOURCETOEXIT("SOURCETOEXIT"),
        //入口点到沉淀点
        ENTRYTOSINK("ENTRYTOSINK"),
        //入口点到出口点
        ENTRYTOEXIT("ENTRYTOEXIT"),
        //入口点到源点，用于构造Android权限方法
        ENTRYTOSOURCE("ENTRYTOSOURCE");

        private String type;

        InfoflowEnum(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

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

    /**
     * 获取入口点权限的方法映射
     *
     * @param is 输入流
     * @return 映射
     */
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
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(apkName + fileSpliter + "ENTRYPERMISSIONS.txt")));
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
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sourceToExitMap;
    }

    /**
     * 获取入口点到出口点的方法映射
     *
     * @param is 输入流
     * @return 映射
     */
    public Map<String, Set<String>> generateEntryExitMap(InputStream is) {
        Map<String, Set<String>> entryToExitMap = new HashMap<>();
        try (InputStreamReader reader = new InputStreamReader(is);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            entryToExitMap = generateMap(bufferedReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entryToExitMap;
    }

    /**
     * 获取入口点到出口点的方法映射
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
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(apkName + fileSpliter + InfoflowEnum.ENTRYTOEXIT + ".txt")));
            entryToExitMap = generateMap(bufferedReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entryToExitMap;
    }


    /**
     * 获取入口点至沉淀点的方法映射
     *
     * @param is 输入流
     * @return 映射
     */
    public Map<String, Set<String>> generateEntrySinkMap(InputStream is) {
        Map<String, Set<String>> entryToSinkMap = new HashMap<>();
        try (InputStreamReader reader = new InputStreamReader(is);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            entryToSinkMap = generateMap(bufferedReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entryToSinkMap;
    }


    /**
     * 获取入口点值沉淀点的方法映射
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
            File file = new File(apkName + fileSpliter + InfoflowEnum.ENTRYTOSINK + ".txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            entryToSinkMap = generateMap(bufferedReader);
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entryToSinkMap;
    }

    /**
     * 获取源点到出口点的方法映射
     *
     * @param is 输入流
     * @return 映射
     */
    public Map<String, Set<String>> generateSourceExitMap(InputStream is) {
        Map<String, Set<String>> sourceToExitMap = new HashMap<>();
        try (InputStreamReader reader = new InputStreamReader(is);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            sourceToExitMap = generateMap(bufferedReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sourceToExitMap;
    }

    /**
     * 获取源点到出口点的方法映射
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
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(apkName + fileSpliter + InfoflowEnum.SOURCETOEXIT + ".txt")));
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
     * @param is 输入流
     * @return 映射
     */
    public Map<String, Set<String>> generateSourceSinkMap(InputStream is) {
        Map<String, Set<String>> entryToSinkMap = new HashMap<>();
        try (InputStreamReader reader = new InputStreamReader(is);
             BufferedReader bufferedReader = new BufferedReader(reader)){
            entryToSinkMap = generateMap(bufferedReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entryToSinkMap;
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
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(apkName + fileSpliter + InfoflowEnum.SOURCETOSINK + ".txt")));
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