package nju.software.rules.entry;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
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
        String callerPackage = null;
        Intent intent = act.getIntent();
        Log.e("Intent",intent.getStringExtra("package") == null ? "nothing" : intent.getStringExtra("package"));
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
                String[] selfPermissions = new String[]{"android.permission.SEND_SMS"};
                if (selfPermissions != null)
                    Collections.addAll(calleePermissionSet, selfPermissions);

                calleePermissionSet.removeAll(callerPermissionsSet);
                if (calleePermissionSet.size() > 0) {

                    return true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 判断是否发生权限再授权攻击
     *
     * @param act         当前Activity
     * @return 是否发生权限再授权
     */
    public static boolean isPermissionRedelegation(Activity act) {
        //首先这边的Package需要获得的内容就比较多
        String callerPackage = null;
        Intent data = act.getIntent();
        Log.e("Intent",data.getStringExtra("package") == null ? "nothing" : data.getStringExtra("package"));
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
                String[] selfPermissions = new String[]{"android.permission.SEND_SMS"};
                if (selfPermissions != null)
                    Collections.addAll(calleePermissionSet, selfPermissions);

                calleePermissionSet.removeAll(callerPermissionsSet);
                if (calleePermissionSet.size() > 0) {

                    return true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public static void back(Activity act) {
        Toast.makeText(act.getApplicationContext(), "发生越权调用，将返回", Toast.LENGTH_LONG).show();
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}