package nju.software.constants;

/**
 * Created by Xie on 2016/3/3.
 */
public enum SmaliFileEnum {

    SMS("CheckMessageRule.smali"),
    NETWORK("CheckNetworkRule.smali"),
    ENTRY("CheckActivityRule.smali"),
    RAW("R$raw.smali");


    private String fileName;

    SmaliFileEnum(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
