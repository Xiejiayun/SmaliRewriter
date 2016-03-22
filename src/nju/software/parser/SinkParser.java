package nju.software.parser;

import nju.software.parser.model.SinkInfo;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 专门用来解析沉淀点的转换器
 * <p/>
 * Created by Xie on 2016/3/20.
 */
public class SinkParser {

    private static SinkParser sinkParser = new SinkParser();

    public static SinkParser v() {
        return sinkParser;
    }

    public static void main(String[] args) {
        Map map = genereateMapInfo("");
        System.out.println(map);
    }

    public static Map<String, Set<SinkInfo>> genereateMapInfo(String filePath) {
        Map<String, Set<SinkInfo>> map = new HashMap<>();
        //Source To Sink BufferedReader
        BufferedReader ss = null;
        BufferedReader es = null;
        try {
            ss = new BufferedReader(new FileReader(new File(filePath+"data/sourcetosink.txt")));
            es = new BufferedReader(new FileReader(new File(filePath+"data/entrytosink.txt")));
            Set<String> ssSink = SinkParser.v().generateSinkSet(ss);
            Set<String> esSink = SinkParser.v().generateSinkSet(es);
            generateMapInfo(map, ssSink);
            generateMapInfo(map, esSink);
            ss.close();
            es.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    private static void generateMapInfo(Map<String, Set<SinkInfo>> map, Set<String> ssSink) {
        for (String sssink : ssSink) {
            SinkInfo sinkInfo = SinkParser.v().generateSinkInfo(sssink);
            Set<SinkInfo> sinkInfos = map.get(sinkInfo.getClazz());
            if (sinkInfos == null) {
                sinkInfos = new HashSet<>();
            }
            sinkInfos.add(sinkInfo);
            if (sinkInfo.getClazz() != null)
                map.put(sinkInfo.getClazz(), sinkInfos);
            System.out.println(sinkInfo);
        }
    }

    private Map<String, Set<String>> generateSinkSourceMap(BufferedReader bufferedReader) throws IOException {
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
                        bufferedReader.readLine();
                    sources.add(source);
                    line = bufferedReader.readLine();
                }
                map.put(sink, sources);
            }
        }
        return map;
    }

    private Set<String> generateSinkSet(BufferedReader bufferedReader) {
        Set<String> sinkSets = new HashSet<>();
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                if (line.startsWith("Sink")) {
                    line = bufferedReader.readLine();
                    sinkSets.add(line);
                }
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sinkSets;
    }

    public SinkInfo generateSinkInfo(String sink) {
        SinkInfo sinkInfo = new SinkInfo();
        //这个就是匹配的模式串
        String pattern = "^.*<(.*)>\\(.*\\)\\s(\\d*)\\s.*\\s.*<(.*):.*$";
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(sink);
        if (!matcher.matches())
            return sinkInfo;
        assert matcher.group(1) != null && matcher.group(2) != null && matcher.group(3) != null;
        String clazz = matcher.group(3);
        String method = matcher.group(1);
        Integer lineNumber = Integer.parseInt(matcher.group(2));
        sinkInfo.setClazz(clazz);
        sinkInfo.setMethod(method);
        sinkInfo.setLineNumber(lineNumber);
        return sinkInfo;
    }


}