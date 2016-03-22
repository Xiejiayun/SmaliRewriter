package nju.software.rewrite;

import nju.software.parser.model.SinkInfo;

import java.util.Set;

/**
 * Created by Xie on 2016/3/22.
 */
public interface SinkInfoRewriterInterface {

    public void search(String smaliFile, String ruleFile, Set<SinkInfo> sinkInfos);
}
