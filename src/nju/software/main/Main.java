package nju.software.main;

import nju.software.decompile.Decompiler;
import nju.software.repackage.SmaliRepackage;
import nju.software.rewrite.SmaliRewriter;
import nju.software.search.EntryPointSearch;
import nju.software.search.SinkPointSearch;
import nju.software.transformer.FileTransformer;

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

        String apkFile = "InterAppCommunication/SendSMS.apk";
        rewriteAPK(apkFile);
    }

    private static void rewriteAPK(String apkFile) {
        String apkDir = apkFile.substring(0, apkFile.length()-4);
        Decompiler.decompile(apkFile);
        //传输已经计算好的数据
        FileTransformer.transform(apkFile);
        SmaliRewriter smaliRewriter = new SmaliRewriter();

        try {
            //更新资源文件，对计算好的资源文件更新数据库
            smaliRewriter.updateResourceSmalis(apkDir+
                    "/smali", 0);
            smaliRewriter.updateResourceXML(apkDir+ "/res/values/public.xml");
            messageList = SinkPointSearch
                    .searchSmalis("SinkSetAPI.smali");
            smaliRewriter.readSinkSmalis(apkDir+
                    "/smali", messageList, 0);
            messageList = EntryPointSearch
                    .searchSmalis("EntrySetAPI.smali");
            smaliRewriter.readEntrySmalis(apkDir+
                    "/smali", messageList, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SmaliRepackage.repackage(apkFile);
    }
}
