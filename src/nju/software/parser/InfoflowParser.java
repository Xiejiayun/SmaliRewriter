package nju.software.parser;

import java.io.*;
import java.util.*;

/**
 * 提取生成的有用的信息流信息
 * <p/>
 * Created by Xie on 2016/2/29.
 */
public class InfoflowParser {

    public static final String fileSpliter = "/";
    private static final InfoflowParser infoflowParser = new InfoflowParser();


    public InfoflowParser() {
    }

    public static InfoflowParser v() {
        return infoflowParser;
    }

    public static void main(String[] args) {
//        Map map = InfoflowParser.v().generateEntryPointPermissionMap("InterAppCommunication/SendSMS.apk");
//        Map map1 = InfoflowParser.v().generateEntryExitMap("InterAppCommunication/SendSMS.apk");
//        Map map2 = InfoflowParser.v().generateEntrySinkMap("InterAppCommunication/SendSMS.apk");
//        Map map3 = InfoflowParser.v().generateSourceExitMap("InterAppCommunication/SendSMS.apk");
//        Map map4 = InfoflowParser.v().generateSourceSinkMap("InterAppCommunication/SendSMS.apk");
//        System.out.println(map);
//        System.out.println(map1);
//        System.out.println(map2);
//        System.out.println(map3);
//        System.out.println(map4);
        try {
            BufferedReader is = new BufferedReader(new FileReader(new File("InterAppCommunication/SendSMSdata/sourcetosink.txt")));
            Map<String, Set<String>> map5 = InfoflowParser.v().generatePathMap(is);
            for (String sink : map5.keySet()) {
                Set<String> paths = map5.get(sink);
                for (String path : paths) {
                    String[] vertexs = path.split("\\),");
                    for (String vertex : vertexs)
                        System.out.println(vertex);
                }
            }
            System.out.println(map5);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Set<String>> generateSinkPointDescription(InputStream is) {
        Map<String, Set<String>> result = new HashMap<>();

        return result;
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
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(apkName + "data" + fileSpliter + "entrypermissions.txt")));
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
     * 获取入口点权限的方法映射(已经在checkActivityRule中使用了)
     *
     * @param is 输入流
     * @return 映射
     */
//    public static Map<String, Set<String>> generateEntryPointPermissionMap(InputStream is) {
//        Map<String, Set<String>> sourceToExitMap = new HashMap<String, Set<String>>();
//        try {
//            InputStreamReader reader = new InputStreamReader(is);
//            BufferedReader bufferedReader = new BufferedReader(reader);
//            String line = bufferedReader.readLine();
//            while (line != null) {
//                //表明这是一个入口点方法
//                if (line.startsWith("<")) {
//                    String method = line;
//                    Set<String> permissions = new HashSet<String>();
//                    line = bufferedReader.readLine();
//                    while (line != null && !line.startsWith("<")) {
//                        permissions.add(line);
//                        line = bufferedReader.readLine();
//                    }
//                    sourceToExitMap.put(method, permissions);
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return sourceToExitMap;
//    }

    /**
     * 获取入口点到出口点的方法映射
     *
     * @param is 输入流
     * @return 映射
     */
    public Map<String, Set<SourcePath>> generateEntryExitMap(InputStream is) {
        Map<String, Set<SourcePath>> entryToExitMap = new HashMap<>();
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
    public Map<String, Set<SourcePath>> generateEntryExitMap(String apkName) {
        if (apkName.endsWith(".apk")) {
            apkName = apkName.substring(0, apkName.length() - 4);
        }
        Map<String, Set<SourcePath>> entryToExitMap = new HashMap<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(apkName + "data" + fileSpliter + InfoflowEnum.ENTRYTOEXIT.getType() + ".txt")));
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
    public Map<String, Set<SourcePath>> generateEntrySinkMap(InputStream is) {
        Map<String, Set<SourcePath>> entryToSinkMap = new HashMap<>();
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
    public Map<String, Set<SourcePath>> generateEntrySinkMap(String apkName) {
        if (apkName.endsWith(".apk")) {
            apkName = apkName.substring(0, apkName.length() - 4);
        }
        Map<String, Set<SourcePath>> entryToSinkMap = new HashMap<>();
        try {
            File file = new File(apkName + "data" + fileSpliter + InfoflowEnum.ENTRYTOSINK.getType() + ".txt");
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
    public Map<String, Set<SourcePath>> generateSourceExitMap(InputStream is) {
        Map<String, Set<SourcePath>> sourceToExitMap = new HashMap<>();
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
    public Map<String, Set<SourcePath>> generateSourceExitMap(String apkName) {
        if (apkName.endsWith(".apk")) {
            apkName = apkName.substring(0, apkName.length() - 4);
        }
        Map<String, Set<SourcePath>> sourceToExitMap = new HashMap<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(apkName + "data" + fileSpliter + InfoflowEnum.SOURCETOEXIT.getType() + ".txt")));
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
    public Map<String, Set<SourcePath>> generateSourceSinkMap(InputStream is) {
        Map<String, Set<SourcePath>> entryToSinkMap = new HashMap<>();
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
     * 获取源点至沉淀点的方法映射
     *
     * @param apkName apk名称
     * @return 映射
     */
    public Map<String, Set<SourcePath>> generateSourceSinkMap(String apkName) {
        if (apkName.endsWith(".apk")) {
            apkName = apkName.substring(0, apkName.length() - 4);
        }
        Map<String, Set<SourcePath>> entryToSinkMap = new HashMap<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(apkName + "data" + fileSpliter + InfoflowEnum.SOURCETOSINK.getType() + ".txt")));
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
    private Map<String, Set<SourcePath>> generateMap(BufferedReader bufferedReader) throws IOException {
        Map<String, Set<SourcePath>> map = new HashMap<>();
        String line = bufferedReader.readLine();
        while (line != null) {
            if (line.startsWith("Sink")) {
                String source = "", path = "";
                String sink = bufferedReader.readLine();
                Set<SourcePath> sources = new HashSet<>();
                line = bufferedReader.readLine();
                while (line != null && !line.startsWith("Sink")) {
                    if (line.startsWith("Source"))
                        source = bufferedReader.readLine();
                    line = bufferedReader.readLine();
                    if (line.startsWith("Path"))
                        path = bufferedReader.readLine();
                    sources.add(new SourcePath(source, path));
                    line = bufferedReader.readLine();
                }
                map.put(sink, sources);
            }
        }
        return map;
    }

    /**
     * 生成<沉淀点-路径>映射Map
     *
     * @param bufferedReader
     * @return
     * @throws IOException
     */
    private Map<String, Set<String>> generatePathMap(BufferedReader bufferedReader) throws IOException {
        Map<String, Set<String>> map = new HashMap<>();
        String line = bufferedReader.readLine();
        while (line != null) {
            if (line.startsWith("Sink")) {
                String source = "", path = "";
                String sink = bufferedReader.readLine();
                Set<String> sources = new HashSet<>();
                line = bufferedReader.readLine();
                while (line != null && !line.startsWith("Sink")) {
                    if (line.startsWith("Source"))
                        source = bufferedReader.readLine();
                    line = bufferedReader.readLine();
                    if (line.startsWith("Path"))
                        path = bufferedReader.readLine();
                    sources.add(path);
                    line = bufferedReader.readLine();
                }
                map.put(sink, sources);
            }
        }
        return map;
    }

    //信息流Enum，列举出具体的信息流方法
    public enum InfoflowEnum {
        //源点到沉淀点，默认为此
        SOURCETOSINK("sourcetosink"),
        //源点到出口点
        SOURCETOEXIT("sourcetoexit"),
        //入口点到沉淀点
        ENTRYTOSINK("entrytosink"),
        //入口点到出口点
        ENTRYTOEXIT("entrytoexit"),
        //入口点到源点，用于构造Android权限方法
        ENTRYTOSOURCE("entrytosource");

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

    private class SourcePath {

        private String source;
        private String path;

        public SourcePath() {
        }

        public SourcePath(String source, String path) {
            this.source = source;
            this.path = path;
        }
    }
}