package com.rkylin.crawler.engine.flood.crawler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.rkylin.crawler.engine.flood.common.Constant;
import com.rkylin.crawler.engine.flood.pausecrawler.RxRedisPauseFetchThread;
import com.rkylin.crawler.engine.flood.pausecrawler.SessionStatus;
import com.rkylin.crawler.engine.flood.stoptask.RxRedisSubscribeThread;
import com.rkylin.crawler.engine.flood.tools.ReportHostStatusTool;

public class CrawlerManager {
	
    private static final transient Logger logger = Logger.getLogger(CrawlerManager.class);

    private static CrawlerManager crsm = null;

    private List<CrawlerRunner> workThreads = null;

    private static boolean isPauseFetchMsg = false;
    
    private CrawlerManager() {
        workThreads = new ArrayList<CrawlerRunner>();
    }

    public static CrawlerManager getIns() {
        if (crsm == null) {
            crsm = new CrawlerManager();
        }
        return crsm;
    }


    /**
     * 初始化工作线程
     * @param browserNum
     *            Browser session数量
     * @param clientNum 
     * 			  Client session数量
     */
    public void initializeCrawlerThread(int crawlerid, int browserNum, int clientNum) {
//        WebDriver wd = null;
        //本地启动时,只测试一个Session
        int browserSessionNumber = 1;
        int clientNumber = 1;
		if (!Constant.LocalConfig.TEST_LOCAL) {
			browserSessionNumber = browserNum;
			clientNumber = clientNum;
		}
		
		logger.info("browserSessionNumber: " + browserSessionNumber);
		logger.info("clientNum: " + clientNum);

    	SessionStatus sessionStatus = new SessionStatus();
        for (int browseId = 1; browseId <= browserSessionNumber; browseId++) {
        	sessionStatus.setPausedFetchMessage(false);
    	 	Date currentDate = new Date();
    	 	sessionStatus.setUpdatedTime(currentDate);
        	ReportHostStatusTool.setSessionStatusMap(browseId, sessionStatus);
    		logger.info("session["+ browseId + "] set the setPausedFetchMessage false" + " current date time: " + currentDate);
        	CrawlerRunner cr = new CrawlerRunner(crawlerid, browseId, true);
            cr.start();
         
            workThreads.add(cr);
        }
        for (int clientId = browserSessionNumber+1; clientId <= browserSessionNumber + clientNumber; clientId++) {
        	sessionStatus.setPausedFetchMessage(false);
    	 	Date currentDate = new Date();
    	 	sessionStatus.setUpdatedTime(currentDate);
        	ReportHostStatusTool.setSessionStatusMap(clientId, sessionStatus);
    		logger.info("session["+ clientId + "] set the setPausedFetchMessage false" + " current date time: " + currentDate);
        	CrawlerRunner cr = new CrawlerRunner(crawlerid, clientId, false);
            cr.start();
         
            workThreads.add(cr);
        }
        
        // 启动线程RXRedisSubscribeThread
        RxRedisSubscribeThread.getInstance(crawlerid).start();
     // 启动线程RxRedisPauseFetchThread
        RxRedisPauseFetchThread.getInstance(crawlerid).start();
    }

    /**
     * 关闭所有Thread
     */
    public void closeAllThread() {
        if (workThreads == null || workThreads.size() == 0) {
            return;
        }

        // 把flag设定为stop
        setStopFlag();
        // 等待所有Thread执行完毕
        waitAllThreadTermination();
    }

    private void setStopFlag() {
        for (CrawlerRunner cr : workThreads) {
            if (cr.isAlive()) {
                cr.setStopFlag(true);
            }
        }
    }

    private void waitAllThreadTermination() {
        for (CrawlerRunner cr : workThreads) {
            try {
                if (cr.isAlive()) {
                    cr.join();
                }
            } catch (Exception e) {
                logger.info(e.getMessage(), e);
            }
        }

        workThreads.clear();
    }

	public static boolean isPauseFetchMsg() {
		return isPauseFetchMsg;
	}

	public static void setPauseFetchMsg(boolean isPauseFetchMsg) {
		CrawlerManager.isPauseFetchMsg = isPauseFetchMsg;
	}

}
