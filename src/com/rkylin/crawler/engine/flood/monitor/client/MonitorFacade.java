package com.rkylin.crawler.engine.flood.monitor.client;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.rkylin.crawler.engine.flood.common.Constant;
import com.rkylin.crawler.engine.flood.common.Constant.MONITOR_INFO;
import com.rkylin.crawler.engine.flood.exception.SystemException;
import com.rkylin.crawler.engine.flood.model.HostStatus;
import com.rkylin.crawler.engine.flood.pausecrawler.SessionStatus;
import com.rkylin.crawler.engine.flood.tools.ReportHostStatusTool;
import com.rkylin.crawler.engine.flood.util.HTTP;
import com.web2data.__main.LocalConfigLoader;
import com.web2data.system.config._main.ConfigFacade;

public class MonitorFacade {
	
    private static final transient Logger logger = Logger.getLogger(MonitorFacade.class);

    private static MonitorFacade instance = null;

    private MonitorFacade() {
    	
    }

    public static MonitorFacade getInstance() {
    	
        if (instance == null) {
        	instance = new MonitorFacade();
        }
        
        return instance;
    }
    

    /**
     * 调用php把监控信息写到db中(flood_monitor.crawler)
     */
    public void doCrawlerReportByPhp(HostStatus hostStatus) {
    	
        try {
        	
            // 调用API通知爬取任务开始
            String apiUri = getMonitorUri(Constant.MONITOR_API_KEY.CRAWLER_REPORT3);
            String chromeVersion = URLEncoder.encode(MONITOR_INFO.CHROME_VERSION, "UTF-8");
            
            String pauseFetchMsgList = "";
            String sessionReportTimeList = "";
            
            ConcurrentHashMap<Integer, SessionStatus> statusMap =  ReportHostStatusTool.getSessionStatusMap();
           		
            for (SessionStatus status : statusMap.values()) {
				
            	String dbValue = "";
				if (status.isPausedFetchMessage()) {
					dbValue = "Y";
				} else {
					dbValue = "N";
				}
            	pauseFetchMsgList += dbValue+"|";
            	sessionReportTimeList += convertDateToString(status.getUpdatedTime()) +"|";
			}
            
			if (pauseFetchMsgList.contains("|")) {
				pauseFetchMsgList = pauseFetchMsgList.substring(0, pauseFetchMsgList.length() - 1);
			}
			if (pauseFetchMsgList.contains("|")) {
				sessionReportTimeList = sessionReportTimeList.substring(0, sessionReportTimeList.length() - 1);
			}
         
			pauseFetchMsgList = URLEncoder.encode(pauseFetchMsgList, "UTF-8");
            sessionReportTimeList = URLEncoder.encode(sessionReportTimeList, "UTF-8");
            
			String php = String.format(apiUri, hostStatus.getHostSeq(),
					                           hostStatus.getCompanySeq(),
					                           hostStatus.getCompanyName(),
					                           hostStatus.getSiteSeq(),
					                           hostStatus.getSiteName(),
					                           hostStatus.getHostName(),
					                           hostStatus.getCpuRate(),
					                           hostStatus.getMemoryRate(),
					                           hostStatus.getDiskRate(),
					                           hostStatus.getJavaMemory(),
					                           hostStatus.getMysqlMemory(),
					                           hostStatus.getFiddlerMemory(),
					                           hostStatus.getChromes(),
					                           hostStatus.getChromeMemory(),
					                           chromeVersion,
					                           MONITOR_INFO.MYSQL_STATUS,
					                           MONITOR_INFO.WAR_VERSION,
					                           hostStatus.getChromeDrivers(),
					                           hostStatus.getChromeDriverMemory(),
					                           pauseFetchMsgList,
					                           sessionReportTimeList);
			logger.info("doCrawlerReportByPhp php:" + php);

            HTTP.doGet(php);
            
        } catch (Exception ex) {
            logger.error("doCrawlerReportByPhp网络异常", ex);
        }
	}

	private String getMonitorUri(String apiKey) {

		String uri = null;

		try {

			String monitorHost = ConfigFacade.getInstance().getMonitorHost();
			if (monitorHost == null) {
				throw new SystemException("没有找到提供Monitor服务的机器！");
			}

			String apiUrl = ConfigFacade.getInstance().getApiMonitorUriByKey(apiKey);
			if (apiUrl == null) {
				throw new SystemException("Monitor API 配置不正确！");
			}

			uri = String.format(Constant.API_URI_FORMAT.MQ_API_URI, monitorHost, apiUrl);

		} catch (Exception ex) {
			logger.error(ex);
		}

		return uri;
	}
	
	
    public static void main(String[] args) {  
  	
    	
        try {
			LocalConfigLoader.load();
	        // 取得所有的配置，必须在业务代码之前进行！
	        ConfigFacade cf = ConfigFacade.getInstance();
	        cf.initializeConf();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
//		String php = String.format(apiUri, hostStatus.getHostSeq(),
//                hostStatus.getCompanySeq(),
//                hostStatus.getCompanyName(),
//                hostStatus.getSiteSeq(),
//                hostStatus.getSiteName(),
//                hostStatus.getHostName(),
//                hostStatus.getCpuRate(),
//                hostStatus.getMemoryRate(),
//                hostStatus.getDiskRate(),
//                hostStatus.getJavaMemory(),
//                hostStatus.getMysqlMemory(),
//                hostStatus.getFiddlerMemory(),
//                hostStatus.getChromes(),
//                hostStatus.getChromeMemory());
        
        HostStatus hostStatus = new HostStatus();
        hostStatus.setCompanySeq(1);
        hostStatus.setCompanyName("Tencent");
        hostStatus.setSiteSeq(2);
        hostStatus.setSiteName("wwww.baidu.com");
        hostStatus.setHostName("aaaa");
        hostStatus.setCpuRate(80);
        hostStatus.setMemoryRate(70);
        hostStatus.setDiskRate(10);
        hostStatus.setJavaMemory(10);
        hostStatus.setMysqlMemory(20);
        hostStatus.setFiddlerMemory(0);
        hostStatus.setChromes(0);
        hostStatus.setChromeMemory(0);
        
        MonitorFacade.getInstance().doCrawlerReportByPhp(hostStatus);
    	
    }
    
    public static String convertDateToString(java.util.Date dateDate) {  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");  
        String dateString = formatter.format(dateDate);  
        return dateString;  
    }
}
