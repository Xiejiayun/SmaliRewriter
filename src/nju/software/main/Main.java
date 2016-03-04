package nju.software.main;

import nju.software.constants.Constants;
import nju.software.decompile.Decompiler;
import nju.software.repackage.SmaliRepackage;
import nju.software.rewrite.SmaliRewriter;
import nju.software.search.EntryPointSearch;
import nju.software.search.SinkPointSearch;

import java.util.List;

public class Main {

    // 存放沉淀点的方法的smali代码
    static List<String> messageList;

    /*
     * 1.调用Decompiler.decompile()方法反编译apk文件
     * 2.调用SinkPointSearch.searchMessageSmalis方法来搜索发送短信的smali方法
     * 3.调用SmaliRewriter类的相关方法来插入相应的smali代码
     * 4.调用SmaliRepackage.repackage方法来重新打包新的项目
     */
    public static void main(String[] args) {

        Decompiler.decompile(Constants.apkToSmali);
        messageList = SinkPointSearch
                .searchSmalis("SinkSetAPI.smali");
        SmaliRewriter smaliRewriter = new SmaliRewriter();
//        String apkFilePath = null;
        try {

//            String apkFileFolder = apkFilePath.endsWith(".apk") ?
//                    apkFilePath.substring(0, apkFilePath.length() - 4) : apkFilePath;
            messageList = SinkPointSearch
                    .searchSmalis("SinkSetAPI.smali");
            smaliRewriter.readSinkSmalis(
                    "D://androidAPK//SendSMSAutoDapk//smali//org", messageList);
            messageList = EntryPointSearch
                    .searchSmalis("EntrySetAPI.smali");
            smaliRewriter.readEntrySmalis(
                    "D://androidAPK//SendSMSAutoDapk//smali//org", messageList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SmaliRepackage.repackage(Constants.smaliToApk);
    }

}
