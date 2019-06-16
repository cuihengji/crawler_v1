package com.rkylin.crawler.engine.flood.tools;

import org.apache.log4j.Logger;

public class SnapshotTool {
    private static final transient Logger logger = Logger.getLogger(SnapshotTool.class);

    /**
     * 记录执行状况
     * 
     * @param cid
     *            crawler id
     * @param sid
     *            session id
     * @param appSeq
     *            app sequence
     * @param scIndex
     *            scenario index
     * @param ruleIndex
     *            rule index
     */
    public static void recordExecSnapshot(int cid, int sid, int appSeq, int scIndex, int ruleIndex) {
        ThreadPoolManager4Tool.exec(new Runnable() {
            @Override
            public void run() {
                logger.info(String.format("%d,%d,%d,%d,%d", cid, sid, appSeq, scIndex, ruleIndex));
            }
        });
    }
}
