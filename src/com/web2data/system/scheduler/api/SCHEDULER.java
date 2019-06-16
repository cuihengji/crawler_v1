package com.web2data.system.scheduler.api;

import com.rkylin.crawler.engine.flood.common.Result;
import com.rkylin.crawler.engine.flood.model.MessageObject;
import com.rkylin.crawler.engine.flood.scheduler.client.SchedulerFacade;
import com.rkylin.crawler.engine.flood.tools.ReportHostStatusTool;
import com.web2data.__main.ENV;
import com.web2data.open.RxTask;
import com.web2data.system.scheduler.SchedulerMessage;

public class SCHEDULER {

	// 
	static boolean TESTING = true;
	
	// 
	public static RxTask fetchNewTask() {
		RxTask result = null;
		
		if ( ENV.IS_TESTING ) {
			
			// 自己造一条信息
			result = new RxTask();
			//result.setTimeoutSeconds(30);
			
		} else {
			
			MessageObject aMessageObject = fetchMessage2();
			if ( aMessageObject != null ) {
				// 收到消息进行组装，如果组装有问题，则退还 message 给 Scheduler
				result = new RxTask();
				//result.setTimeoutSeconds(30);
				
				
				// 保护测试数据，每次只取一条
				//TESTING = false;
			}
		}
		
		// 没有消息则返回
		if ( result == null ) {
			return null;
		}
		return result;
	}
	
	
	// 从Message系统获取一条消息, 在本方法中，保存fetch的各种逻辑：随机逻辑等
    private static MessageObject fetchMessage2() {
    	
//    	Long localCurrentTime = curTime.getCurrentTime();
    	
    	MessageObject messageObject = null;
    	
    	Result message = null;
	    
    	boolean isBusy = ReportHostStatusTool.isCpuAndMemoryBusy();
	    //log("fetchMessage", 601, "isCpuAndMemoryBusy", curTime, "-1", "isBusy=" + isBusy);
	   
	    if (!isBusy) {
	    	// String ip = gethostIp(this.needHttpProxy);
	    	int crawlerid = -1;
	    	int sessid = -1;
	    	String ip = "1.2.3.4";
	    	int selectedApp = 72;
	    	boolean isBrowserSession = true;
	    	
	    	//log("fetchMessage", 602, "gethostIp", curTime, "-1", "needHttpProxy=" + this.needHttpProxy);
	    	//int accountIndex = ConfigFacade.getInstance().getAccountIndex( this.selectedApp, this.sessid );
	    	int accountIndex = -1; // 暂时不考account的需求
	    	
	    	
	    	// 为了统计的目的：crawlerid, sessid，其实还少个参数：sessionType
	    	// 根据这个参数判断是否允许取消息：ip,  SCHEDULER不判断具体session是不是在一个应该取消息的云中
	    	// 根据这三个参数判断到哪个队列取消息：selectedApp, accountIndex, isBrowserSession
	    	// 应该做两个改造： (一) 控制是否需要Scheduler端做 Policy Check, 
	    	// （二）把服务器端的queue做细粒度化控制, 到Stage级别, 而不是 App级别, 对于account, 分成两个 queue
	    	// 现在Stage级别随机，然后在Stage的两个queue中，先取account queue中的消息，然后取普通queue	
	    	
	    	Result response = SchedulerFacade.getInstance().fetchMessage3( crawlerid, sessid, ip, selectedApp, accountIndex, isBrowserSession );

	    	// 取不到消息,sleep 3 s 继续循环取
	    	if (response == null || response.getMessage() == null || "".equals(response.getMessage())) {

	    		message = null;
	    		//log("fetchMessage", 603, "this.message = null", curTime);
	    		//logger.error("不能取到消息：" + "　crawlerid　" + crawlerid + " sessid " + sessid + " this.selectedApp " + this.selectedApp);

	    	} else {
	    		message = response;
	    		messageObject = getAppMessageObject( message );
	    		//logger.debug("------------------------------------this.message: " + this.message.getMessage());
	    		//log("fetchMessage", 604, "this.message != null", curTime, "this.message = " + this.message.getMessage());
	    	}

	    } else {

	    	//logger.info("!!!HostStatus CPU or Memory is too high!!!");
	    	Runtime.getRuntime().gc();
	    	//log("fetchMessage", 605, "CpuRate > configCpuRate || MemoryRate > configMemoryRate", curTime);
	    }

//	    curTime.setCurrentTime(localCurrentTime);
	    
	    return messageObject;
	}
	
    
    private static MessageObject getAppMessageObject( Result message ) {
    	
    	MessageObject appMessageObject = null;
    	try {
			if (null != message) {
				appMessageObject = SchedulerFacade.getInstance().convertMsgToObj( message.getMessage() );
			}
		} catch (Throwable e) {
			//logger.error("getAppMessageObject Exception: ", e);
			throw e;
		}
    	
    	return appMessageObject;
    }
    
}
