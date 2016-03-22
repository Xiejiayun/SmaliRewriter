package nju.software.rules.sink;

import android.util.Log;

/**
 * Created by Xie on 2016/3/22.
 */
public class CheckLogRule {

    public static void checkLog(String description, String message) {
        String sms = "正在尝试写入日志 描述"+description+" 信息"+message;
        Log.w("日志警告", sms);
    }
}
