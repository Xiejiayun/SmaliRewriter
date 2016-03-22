package nju.software.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Xie on 2016/3/20.
 */
public class TestParser {

    public static void main(String[] args) {

        //staticinvoke <android.util.Log: int i(java.lang.String,java.lang.String)>("SendSMS: ", $r6) 25 (in <org.cert.sendsms.Button1Listener: void onClick(android.view.View)>) 19
        String sink = "staticinvoke <android.util.Log: int i(java.lang.String,java.lang.String)>(\"SendSMS: \", $r6) 25 (in <org.cert.sendsms.Button1Listener: void onClick(android.view.View)>) 19";
//        String pattern = "^.*\\s<(.*)>\\s(\\d)\\s(.*\\s<(.*):.*>)\\s\\d$";
        String pattern = "^.*\\s<(.*)>\\(.*\\)\\s(\\d*)\\s.*\\s.*<(.*):.*$";
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(sink);
        System.out.println(matcher.matches());
        String group = matcher.group();
        System.out.println(matcher.groupCount());
        String clazz = matcher.group(3);
        String method = matcher.group(1);
        Integer lineNumber = Integer.parseInt(matcher.group(2));
        System.out.println(group + "\n" + "\n" + method + "\n" + lineNumber + "\n" + clazz);
    }
}
