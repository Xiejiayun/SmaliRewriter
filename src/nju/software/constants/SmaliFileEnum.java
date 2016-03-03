package nju.software.constants;

/**
 * Created by Xie on 2016/3/3.
 */
public enum SmaliFileEnum {

    SMS("CheckMessageRule.smali"),
    ENTRY("CheckActivityRule.smali");


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
