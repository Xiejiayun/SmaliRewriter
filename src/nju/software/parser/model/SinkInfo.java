package nju.software.parser.model;

/**
 * Created by Xie on 2016/3/22.
 */
public class SinkInfo {
    //沉淀点所在的类
    private String clazz;
    //沉淀点所在的方法
    private String method;
    //沉淀点位于Java文件中的行数
    private int lineNumber;

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return "SinkInfo{" +
                "clazz='" + clazz + '\'' +
                ", method='" + method + '\'' +
                ", lineNumber=" + lineNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SinkInfo)) return false;

        SinkInfo sinkInfo = (SinkInfo) o;

        if (getLineNumber() != sinkInfo.getLineNumber()) return false;
        if (getClazz() != null ? !getClazz().equals(sinkInfo.getClazz()) : sinkInfo.getClazz() != null)
            return false;
        return getMethod() != null ? getMethod().equals(sinkInfo.getMethod()) : sinkInfo.getMethod() == null;

    }

    @Override
    public int hashCode() {
        int result = getClazz() != null ? getClazz().hashCode() : 0;
        result = 31 * result + (getMethod() != null ? getMethod().hashCode() : 0);
        result = 31 * result + getLineNumber();
        return result;
    }
}