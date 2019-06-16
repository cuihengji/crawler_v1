package com.web2data.engine.crawler.browser.impl.a;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.rkylin.crawler.engine.flood.common.Constant;
import com.rkylin.crawler.engine.flood.model.MessageObject;
import com.web2data.system.app.AppFacade;

public class RxCrawlerImpl_96_Log {

	
    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxCrawlerImpl_96_Log.class);
   
	
	
//	private static void pushAppTaskLog(String logContent, MessageObject messageObject) {
//		
//		//logger.info("pushAppTaskLog:" + logContent);
//		
//		if ("TEST".equals( messageObject.getScheduledType() )) {
//			// 开发平台log记入redis
//			String log = Constant.APPTASK_LOG_KEY.APP_LOG_STATE_RUNNING + "|" + new SimpleDateFormat("HH:mm:ss").format(new Date())
//					             + "|" +  "打开网页 "+ logContent;
//			try {
//				AppFacade.getInstance().pushAppTaskLog(messageObject.getAppSeq(),
//						                               messageObject.getScenarioIndex(),
//						                               messageObject.getRuleIndex(),
//						                               ruleVersion,
//						                               messageObject.getScheduledType(),
//						                               log);	
//			} catch (Exception e) {
//				logger.error("pushAppTaskLog异常", e);
//			}
//		}
//		
//	}
	
}
