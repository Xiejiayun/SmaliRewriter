package nju.software.rules.entry;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Xie on 2016/3/2.
 */
public class CheckActivityRule {

    /**
     * 判断是否发生权限再授权攻击
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        数据
     * @param act         当前Activity
     * @return 是否发生权限再授权
     */
    public static boolean isPermissionRedelegation(int requestCode, int resultCode, Intent data, Activity act) {
        //首先这边的Package需要获得的内容就比较多
        boolean isPermissionRedelegation = false;
        String callerPackage = null;
        Intent intent = act.getIntent();
        Log.e("Intent", intent.getStringExtra("package") == null ? "nothing" : intent.getStringExtra("package"));
        if (resultCode == 0 && requestCode == 0 && data != null) {
            if (data.hasExtra("package")) {
                if (data.getExtras().getString("package") != null) {
                    callerPackage = data.getExtras().getString("package");
                }
            }
        }
        Set<String> callerPermissionsSet = new HashSet<String>();
        Set<String> calleePermissionSet = new HashSet<String>();
        String selfPackage = act.getPackageName();
        if (callerPackage != null && !callerPackage.equals(selfPackage)) {
            Log.i(selfPackage, callerPackage);
            try {
                PackageInfo packageInfo = null;
                packageInfo = act.getPackageManager().getPackageInfo(callerPackage, act.getPackageManager().GET_PERMISSIONS);
                String[] callerPermissions = packageInfo.requestedPermissions;
                if (callerPermissions != null)
                    Collections.addAll(callerPermissionsSet, callerPermissions);
                //其次这边自己permission集合的获取需要读文件
                String className = act.getComponentName().getClassName();
                String methodName = new Throwable().getStackTrace()[1].getMethodName();

                String methodSignature = "<" + className + ": " + methodName;
                //TODO 在真正生成smali代码的时候下面这段话是需要的
                InputStream is = null;
//                InputStream is = act.getResources().openRawResource(R.raw.entrypermissions);
                Map<String, Set<String>> map = generateEntryPointPermissionMap(is);
                for (String key : map.keySet()) {
                    //这边不好生成相应的方法签名
                    if (key.contains(className) && key.contains(methodName)) {
                        map.get(key);
                        calleePermissionSet = map.get(key);
                    }
                }
                calleePermissionSet.removeAll(callerPermissionsSet);
                if (calleePermissionSet.size() > 0) {
                    isPermissionRedelegation = true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        Log.e("on Activity Result Pe", isPermissionRedelegation + "");
        return isPermissionRedelegation;
    }

    /**
     * 判断是否发生权限再授权攻击
     *
     * @param act 当前Activity
     * @return 是否发生权限再授权
     */
    public static boolean isPermissionRedelegation(Activity act) {
        boolean isPermissionRedelegation = false;
        //首先这边的Package需要获得的内容就比较多
        String callerPackage = null;
        Intent data = act.getIntent();
        Log.e("Intent", data.getStringExtra("package") == null ? "nothing" : data.getStringExtra("package"));
        if (data != null) {
            if (data.hasExtra("package")) {
                if (data.getExtras().getString("package") != null) {
                    callerPackage = data.getExtras().getString("package");
                }
            }
        }
        Set<String> callerPermissionsSet = new HashSet<String>();
        Set<String> calleePermissionSet = new HashSet<String>();
        String selfPackage = act.getPackageName();
        if (callerPackage != null && !callerPackage.equals(selfPackage)) {
            Log.i(selfPackage, callerPackage);
            try {
                PackageInfo packageInfo = null;
                packageInfo = act.getPackageManager().getPackageInfo(callerPackage, act.getPackageManager().GET_PERMISSIONS);
                String[] callerPermissions = packageInfo.requestedPermissions;
                if (callerPermissions != null)
                    Collections.addAll(callerPermissionsSet, callerPermissions);
                //其次这边自己permission集合的获取需要读文件
                String packageName = packageInfo.packageName;
                String className = act.getComponentName().getClassName();
                String methodName = new Throwable().getStackTrace()[1].getMethodName();

                //TODO 在真正生成smali代码的时候下面这段话是需要的
                InputStream is = null;
//                InputStream is = act.getResources().openRawResource(R.raw.entrypermissions);
                Map<String, Set<String>> map = generateEntryPointPermissionMap(is);
                for (String key : map.keySet()) {
                    //这边不好生成相应的方法签名
                    if (key.contains(className) && key.contains(methodName)) {
                        map.get(key);
                        calleePermissionSet = map.get(key);
                    }
                }
                calleePermissionSet.removeAll(callerPermissionsSet);
                if (calleePermissionSet.size() > 0) {
                    isPermissionRedelegation = true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        Log.e("onCreate Permission", isPermissionRedelegation + "");
        return isPermissionRedelegation;
    }


    public static void back(Activity act) {
        Log.e("BackCheckActivityRule", "in Back");
        Toast.makeText(act.getApplicationContext(), "发生越权调用，将返回", Toast.LENGTH_LONG).show();
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}