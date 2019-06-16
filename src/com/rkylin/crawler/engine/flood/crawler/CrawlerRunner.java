package com.rkylin.crawler.engine.flood.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLDecoder;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.rkylin.crawler.engine.flood.common.Constant;
import com.rkylin.crawler.engine.flood.common.Constant.MONITOR_INFO;
import com.rkylin.crawler.engine.flood.common.Constant.SYSTEM_ERR;
import com.rkylin.crawler.engine.flood.common.Constant.ScriptFile;
import com.rkylin.crawler.engine.flood.common.Constant.WEBDRIVER_ERR;
import com.rkylin.crawler.engine.flood.common.Constant.WebDriverConfig;
import com.rkylin.crawler.engine.flood.common.IpProxyResult;
import com.rkylin.crawler.engine.flood.common.Result;
import com.rkylin.crawler.engine.flood.exception.LoopProcessMessagesException;
import com.rkylin.crawler.engine.flood.exception.OpenBrowserException;
import com.rkylin.crawler.engine.flood.exception.PrepareProcessMsgException;
import com.rkylin.crawler.engine.flood.exception.ProcessMsgException;
import com.rkylin.crawler.engine.flood.exception.TestBrowserException;
import com.rkylin.crawler.engine.flood.exception.UpdateProxyException;
import com.rkylin.crawler.engine.flood.exception.WebDriverGetException;
import com.rkylin.crawler.engine.flood.log.LogSession;
import com.rkylin.crawler.engine.flood.model.AppRandomSelector;
import com.rkylin.crawler.engine.flood.model.AppTaskAndDBInfoResponse;
import com.rkylin.crawler.engine.flood.model.AppWeight;
import com.rkylin.crawler.engine.flood.model.CloudRandomSelector;
import com.rkylin.crawler.engine.flood.model.MessageObject;
import com.rkylin.crawler.engine.flood.model.ProcessMsgParameter;
import com.rkylin.crawler.engine.flood.model.ProxyContent;
import com.rkylin.crawler.engine.flood.model.TimeWrapper;
import com.rkylin.crawler.engine.flood.model.UserAndPassword;
import com.rkylin.crawler.engine.flood.model.UserRandomSelector;
import com.rkylin.crawler.engine.flood.pausecrawler.SessionStatus;
import com.rkylin.crawler.engine.flood.policy.client.PolicyFacade;
import com.rkylin.crawler.engine.flood.policy.client.PolicyParameter;
import com.rkylin.crawler.engine.flood.proxy.client.ProxyFacade;
import com.rkylin.crawler.engine.flood.scheduler.client.SchedulerAppListCacheManager;
import com.rkylin.crawler.engine.flood.scheduler.client.SchedulerFacade;
import com.rkylin.crawler.engine.flood.stoptask.RxRedisSubscribeThread;
import com.rkylin.crawler.engine.flood.tools.ReportHostStatusTool;
import com.rkylin.crawler.engine.flood.util.DynamicJarLoader;
import com.rkylin.crawler.engine.flood.util.FileUtil;
import com.rkylin.crawler.engine.flood.util.HttpDownload;
import com.rkylin.crawler.engine.flood.util.JsonUtil;
import com.rkylin.crawler.engine.flood.util.StringUtil;
import com.rkylin.crawler.engine.flood.webdriver.WebDriverManager;
import com.rkylin.crawler.engine.flood.websocket.ProxyAddressProducer;
import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.RxResult;
import com.ruixuesoft.crawler.open.RxRule;
import com.ruixuesoft.crawler.open.RxTask;
import com.ruixuesoft.crawler.open.impl.Accounting;
import com.ruixuesoft.crawler.open.impl.RxCrawlerImpl;
import com.ruixuesoft.crawler.open.impl.RxDatabaseImpl;
import com.ruixuesoft.crawler.open.impl.RxTaskImpl;
import com.ruixuesoft.crawler.open.python.model.InputJsonForPython;
import com.web2data.engine.database.ConnectionInfo;
import com.web2data.engine.database.ConnectionManager;
import com.web2data.system.app.AppFacade;
import com.web2data.system.config._main.ConfigFacade;
import com.web2data.system.config.entity.Apps;
import com.web2data.system.config.entity.AppsPerUser;
import com.web2data.system.config.entity.Cloud_users;
import com.web2data.system.config.entity.Rules;
import com.web2data.system.config.entity.User_apps;
import com.web2data.system.config.entity.User_apps_scheduler;
import com.web2data.system.config.entity.app_data_host;
import com.web2data.system.config.entity.app_task_info;

public class CrawlerRunner extends Thread {
	
    private static final transient Logger logger = Logger.getLogger(CrawlerRunner.class);
    
    private int sessid = -999;
    private int crawlerid = -999;
    private String fileName = "CrawlerRunner";
	private WebDriver webDriver;
    private boolean stopThreadFlag = false;
    
    private boolean isStop = false;
    
    private boolean needHttpProxy = false; 	
    // 代理IP, 代理port
    private IpProxyResult proxy = null;
    // 重启浏览器的开始时间
    private long start = -1;				
    
    private Result message = null;
    private MessageObject messageObject = null;
//    private RxResult rxResult = null;
    
    private Connection localDbCon = null;
    ConnectionInfo localDbConectionInfo = null;
    private int selectedApp; 
    
    private int selectedCloud;
    private int selectedUser;
    
   	TimeWrapper curTime = new TimeWrapper(System.currentTimeMillis()); 
   	
   	private final ExecutorService executorService = Executors.newSingleThreadExecutor(); 
   	
   	private int ruleVersion;
	private String scriptType = ScriptFile.SCRIPT_TYPE_JAVA;
	private String pythonExecutionResult;
	private boolean isBrowserSession;
   	
    public CrawlerRunner(int crawlerid, int sessid, boolean isBrowserSession) {
    	
    	super.setName("crawler_session_" + sessid);
        this.sessid = sessid;
        this.crawlerid = crawlerid;
        this.isBrowserSession = isBrowserSession;
    }
    
    @Override
    public void run() {
    	
    	curTime.setCurrentTime(System.currentTimeMillis());
		//setLocalDbConnectionInfo();
		
//		try {
//			this.localDbCon = ConnectionManager.openConnection(this.localDbConectionInfo);
//		} catch (Throwable e) {
//			
//			logger.error("打开本地mysql数据库连接失败",e);
//			log( "run", 901, "openConnection - Throwable", curTime, "打开本地mysql数据库连接失败");
//		}
		
		log( "run", 601, "openConnection", curTime, "打开本地mysql数据库连接成功");

		if (!Constant.LocalConfig.TEST_LOCAL) {
			// 让不同Thread交错开执行，单位秒数
			sleepRandomSeconds(60);
		}
    	
    	log( "run", 602, "sleep", curTime, "让不同Thread交错开执行60秒");
    	
    	while ( !this.stopThreadFlag ) {
    		
    		curTime.setCurrentTime(System.currentTimeMillis());
    		
//    		reOpenLocalDb();
    		log( "run", 603, "reOpenLocalDb", curTime);
    		
        	// 如果当前爬虫机不可用，直接退出本次取任务循环
        	if ( isCrawlerUsed() == false ) {
        		
        		log( "run", 711, "isCrawlerUsed", curTime);
        		// 还应该把webdriver关掉，防止时间过程，关联丢失引起未知异常（暂时不处理）
				logger.info("isCrawlerUsed() == false sleep: 60 seconds ");
        		sleepSeconds(60);
        		
        		log( "run", 712, "sleep", curTime,"-1", "60" );
        		continue;
        	}
        	
        	log( "run", 605, "isCrawlerUsed", curTime,"true");
        	
            try {
				if (isBrowserSession) {
					// 打开浏览器
					openBrowser();

					log("run", 606, "openBrowser", curTime);

					if (!Constant.LocalConfig.TEST_LOCAL) {
						getChromeVersion();
					}

					log("run", 608, "getChromeVersion", curTime);
				}
	           	// 循环处理消息。如果是代理，则达到一定时间后，返回方法。如果非代理，则!this.stopThreadFlag时，返回方法。
	            loopProcessMessages();
	            
	           	log( "run", 607, "loopProcessMessages", curTime );
            } catch ( OpenBrowserException e ) {

            	logger.error("run OpenBrowserException",e);
            	log( "run", 911, "OpenBrowserException", curTime, e.getMessage(), e.getCode() + "", e.getName() + "" );
            } catch ( LoopProcessMessagesException e ) {
            	
            	log( "run", 921, "LoopProcessMessagesException", curTime, e.getMessage(), e.getCode() + "", e.getName() + "");
    			logger.error("run LoopProcessMessagesException",e);
            	
            } catch ( Throwable e) {
            	
    			logger.error("run 执行失败",e);
            	log( "run", 931, "Throwable", curTime, e.getMessage() );
            	
            } finally {
				if (isBrowserSession) {
					closeBrowser();

					log("run", 609, "closeBrowser", curTime);
					sleepSeconds(5);
					log("run", 610, "sleep", curTime, "-1", "5");
					
					deleteTempdataFolderBySessionID(""+sessid);
				}
//            	closeLocalDb();
            }
        }
    }
    
//    private void closeLocalDb() {
//		
//    	try {
//			if (this.localDbCon != null && !this.localDbCon.isClosed()) {
//				log( "closeLocalDb", 601, "close", curTime, "-1" );
//
//				try {
//					this.localDbCon.close();
//					this.localDbCon = null;
//					
//				} catch (SQLException e1) {
//					logger.error("关闭本地数据库失败", e1);
//				}	
//			}
//		} catch (SQLException e) {
//			logger.error("关闭本地数据库失败", e);
//		}
//    }
    
//    private void reOpenLocalDb() {
//    	
//    	Long localCurrentTime = curTime.getCurrentTime();
//    	
//    	boolean localDbConIsClose = false;
//		
//		if (this.localDbCon != null) {
//			
//			try {
//				localDbConIsClose = this.localDbCon.isClosed();
//				log( "reOpenLocalDb", 601, "localDbConIsClose",  curTime, "-1", "localDbConIsClose:" + localDbConIsClose);
//			
//			} catch (SQLException e) {
//				logger.error("SQLException:", e);
//				log( "reOpenLocalDb", 901, "reOpenLocalDb - SQLException", curTime, "关闭本地mysql数据库连接失败");
//			}
//		}
//		
//		if (this.localDbCon == null || localDbConIsClose) {
//			
//			try {
//				
//				this.localDbCon = ConnectionManager.openConnection(this.localDbConectionInfo);
//				
//				log( "reOpenLocalDb", 602, "ConnectionManager.openConnection", curTime);
//				
//			} catch (Throwable e) {
//				
//				logger.error("reOpenLocalDb - Throwable 内打开本地mysql数据库连接失败");
//				logger.error("Throwable: ",e);
//				log( "reOpenLocalDb", 911, "reOpenLocalDb - Throwable", curTime, "打开本地mysql数据库连接失败");
//			}
//		}
//		
//		curTime.setCurrentTime(localCurrentTime);
//    }
    
//    private void setLocalDbConnectionInfo() {
//    	
//    	this.localDbConectionInfo = new ConnectionInfo();
//    	this.localDbConectionInfo.setIp(Constant.LocalConfig.DB_IP);
//    	this.localDbConectionInfo.setSchema(Constant.LocalConfig.DB_SCHEMA);
//    	this.localDbConectionInfo.setPort(Integer.parseInt(Constant.LocalConfig.DB_PORT));
//    	this.localDbConectionInfo.setUsername(Constant.LocalConfig.DB_USERNAME);
//    	this.localDbConectionInfo.setPassword(Constant.LocalConfig.DB_PASSWORD);
//    	
//    }
     
    
	// 获得当前爬虫机的可用状态
    private boolean isCrawlerUsed() {
    	
    	Long localCurrentTime = curTime.getCurrentTime();
    	
	    boolean isUsed = ConfigFacade.getInstance().getLocalCrawlerIsUsedFlag();
       	log( "isCrawlerUsed", 601, "getLocalCrawlerIsUsedFlag", curTime, "-1", "isUsed:" + isUsed );
       	
       	curTime.setCurrentTime(localCurrentTime);
       	
	    return isUsed;
    }
    
    
    // 找一个测试用过的Proxy
	private void findProxy() {
		Long localCurrentTime = curTime.getCurrentTime();

		// 获取一个代理
		this.proxy = ProxyFacade.getInstance().fetchIpProxy(this.crawlerid, this.sessid);
		log("findProxy", 601, "fetchIpProxy", curTime);

		if (this.proxy == null) {
			logger.info("获取代理ip失败!");
			log("findProxy", 602, "this.proxy == null", curTime);
		} else {
			logger.info("获取代理成功:" + this.proxy.getIp() + " " +this.proxy.getPort());
			log("findProxy", 603, "this.proxy != null", curTime, "-1", this.proxy.getIp(), this.proxy.getPort() + "");
		}

		curTime.setCurrentTime(localCurrentTime);
	}
    
    
    // 如果webdriver没有准备好的话，进行准备
    private void openBrowser() throws OpenBrowserException {
    	
    	Long localCurrentTime = curTime.getCurrentTime();
    	try {
    		
    		// 打开浏览器
    		if (this.webDriver == null) {
    			
    			log( "openBrowser", 601, "if (this.webDriver == null)", curTime,  "-1");
  				WebDriverManager.getIns().initializeCrawler(this.crawlerid, this.sessid, true);

  				log( "openBrowser", 602, "initializeCrawler", curTime, "-1");
  				
    			this.webDriver = WebDriverManager.getIns().getDriver(crawlerid, sessid);
    			log( "openBrowser", 603, "getDriver", curTime, "-1");
    		}
    		
    	} catch (WebDriverException e ) {
    		
    		if ( StringUtil.contains(e.getMessage(), Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_TIMED_OUT) ) {
				
    			log( "openBrowser", 801, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_TIMED_OUT,  curTime, e.getMessage());	
				throw new OpenBrowserException( 801, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_TIMED_OUT, e);
				
    		} else if ( StringUtil.contains(e.getMessage(), Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_UN_REACHABLE_BROWSER_EXCEPTION) ) {
    			
				log( "openBrowser", 802, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_UN_REACHABLE_BROWSER_EXCEPTION, curTime, e.getMessage());
				throw new OpenBrowserException( 802, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_UN_REACHABLE_BROWSER_EXCEPTION, e);
    			
    		} else if ( StringUtil.contains(e.getMessage(), Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_NOT_REACHABLE) ) {
				
    			log( "openBrowser", 803, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_NOT_REACHABLE, curTime, e.getMessage());
				throw new OpenBrowserException( 803, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_NOT_REACHABLE, e);
    			
    		} else if ( StringUtil.contains(e.getMessage(), Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_CHROME_FAILED_TO_START) ) {
				
    			log( "openBrowser", 804, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_CHROME_FAILED_TO_START, curTime, e.getMessage());
				throw new OpenBrowserException( 804, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_CHROME_FAILED_TO_START, e);
    			
    		} else if (StringUtil.contains(e.getMessage(), Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_NO_SUCH_SESSION_EXCEPTION)) {
    			
    			log( "openBrowser", 808, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_NO_SUCH_SESSION_EXCEPTION, curTime, e.getMessage());
				throw new OpenBrowserException( 808, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_NO_SUCH_SESSION_EXCEPTION, e);
				
    		} else {
    			
    			log( "openBrowser", 800, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_UNKNOWN_ERROR, curTime, e.getMessage());
    			throw new OpenBrowserException( 800, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_UNKNOWN_ERROR, e );
    		}
    		
    	} catch ( Throwable e) {
    		
    		log( "openBrowser", 900, "Throwable", curTime, e.getMessage());
			logger.error("openBrowser 执行失败",e);
			throw new OpenBrowserException( 900, Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_UNKNOWN_EXCEPTION, e );
    	} finally {
    		curTime.setCurrentTime(localCurrentTime);
    	}
    }
    
    
    // 通过浏览器测试代理是否好用
    private void testBrowser() throws TestBrowserException {

    	Long localCurrentTime = curTime.getCurrentTime();
    	
    	String testUrl = "http://www.ip138.com/";
		
    	log( "testBrowser", 601, "start", curTime, "-1", "testUrl = " + testUrl);
    	
    	try {
    		
        	WebDriverManager.getIns().get(this.webDriver, testUrl, "-1", 10);

        	log( "testBrowser", 602, "WebDriverManager.get", curTime);
			
		} catch (NoSuchSessionException e) {
        	logger.error("testBrowser:->NoSuchSessionException", e);
        	throw new TestBrowserException(WEBDRIVER_ERR.WEBDRIVER_ERR_CODE_808, "NoSuchSessionException", e);
		}  
    	catch (WebDriverGetException e) {

        	log( "testBrowser", e.getCode(), e.getName(), curTime, e.getMessage() );
        	logger.error("testBrowser:WebDriverGetException", e);
			throw new TestBrowserException(e.getCode(), e.getName(), e);
		}  
    	catch (Throwable e) {
			
			log( "testBrowser", 900, "ThrowableExceptiom", curTime, e.getMessage() );
			logger.error("testBrowser:ThrowableException", e);
			
		} finally {
			
		    curTime.setCurrentTime(localCurrentTime);	
		}
    }
    

	private void checkProxyUsage() {
		Long localCurrentTime = curTime.getCurrentTime();
		
		this.needHttpProxy = ConfigFacade.getInstance().isAppUsingProxy(this.selectedApp);
		log( "checkProxyUsage", 601, "isAppUsingProxy", curTime);

		curTime.setCurrentTime(localCurrentTime);
	}
    
	
	private void updateProxyUsage() throws UpdateProxyException{
		
		Long localCurrentTime = curTime.getCurrentTime();
		
		String proxyInformation = "";

		if (this.needHttpProxy) {

			proxyInformation = generateProxyJSON(this.proxy);
			log("updateProxyUsage", 601, "generateProxyJSON", curTime, "-1", "IP=" + this.proxy.getIp());

		} else {
			proxyInformation = generateDirectAccessJSON();
			logger.info(" updateProxyUsage " + proxyInformation);
			log("updateProxyUsage", 602, "generateDirectAccessJSON", curTime, "-1");
		}

		try {
			
			ProxyAddressProducer.sendMessage(String.valueOf(this.sessid), proxyInformation);
			log("updateProxyUsage", 603, "sendMessage", curTime, "-1");
		} catch (Throwable e) {
			logger.error(" updateProxyUsage exception", e);
			log("updateProxyUsage", 901, "Throwable", curTime, e.getMessage(), "code=" + Constant.SYSTEM_ERR.SYSTEM_ERR_CODE_912,"IP=" + this.proxy.getIp());
			throw new UpdateProxyException(Constant.SYSTEM_ERR.SYSTEM_ERR_CODE_912,"updateProxyUsage sleepSeconds exception", e);
		} finally {
			
			curTime.setCurrentTime(localCurrentTime);
			
		}
	}
    
    private String generateProxyJSON(IpProxyResult updatedProxy){
		
		String updateProxyContent = "";
		String bypassListOnRule = "";
		String blockedUrlList = "";
		
    	if(this.messageObject!=null){
    		
    		bypassListOnRule = ConfigFacade.getInstance().getBypassListOnRule(
    				this.messageObject.getAppSeq(),
    				this.messageObject.getScenarioIndex(), 
    				this.messageObject.getRuleIndex());
    		
    		blockedUrlList = ConfigFacade.getInstance().getBlockedUrlListOnRule(
					this.messageObject.getAppSeq(),
					this.messageObject.getScenarioIndex(), 
					this.messageObject.getRuleIndex());
    	}
    	
    	logger.debug("---------------------------------------------- bypassListOnRule : "+bypassListOnRule);
    	logger.debug("---------------------------------------------- blockedUrlList : "+ blockedUrlList);
    	
		ProxyContent proxyContent = new ProxyContent();
    	
    	proxyContent.setMode("fixed_servers");
    	proxyContent.setIp(updatedProxy.getIp());
    	proxyContent.setPort(updatedProxy.getPort());
    	proxyContent.setUser_name(updatedProxy.getUser_name());
    	proxyContent.setPass_word(updatedProxy.getPass_word());
    	proxyContent.setBypassList(bypassListOnRule.replaceAll("\r|\n",""));
    	proxyContent.setBlockedUrlList(blockedUrlList.replaceAll("\r|\n",""));
    	
    	try {
    		updateProxyContent = JsonUtil.convertObj2JsonStr(proxyContent);
		} catch (Exception e) {
			logger.error("JsonUtil.convertObj2JsonStr Exception:",e);
		}
    	
    	bypassListOnRule = null;
    	blockedUrlList = null;
    	
    	return updateProxyContent;
	}
	
	private String generateDirectAccessJSON(){
		
		String updateProxyContent = "";
		ProxyContent proxyContent = new ProxyContent();
    	proxyContent.setMode("direct");
    	
    	try {
    		updateProxyContent = JsonUtil.convertObj2JsonStr(proxyContent);
		} catch (Exception e) {
			logger.error("JsonUtil.convertObj2JsonStr Exception:",e);
		}
    	
    	return updateProxyContent;
	}

	private void loopProcessMessages() throws LoopProcessMessagesException {
		
		Long localCurrentTime = curTime.getCurrentTime();
    	// 初始开始时间
    	this.start = System.currentTimeMillis();
        
    	SessionStatus sessionStatus = new SessionStatus();

		// 循环获取并处理
		while ( !this.stopThreadFlag ) {
			
			curTime.setCurrentTime(System.currentTimeMillis());
    		
			//测试本地MySQL数据库的联通性并汇报给monitor
    	    //testLocalDbConnection(this.localDbCon);
			if (isBrowserSession) {
				// 按设定时间重启浏览器
				long remainingMinutes = (System.currentTimeMillis() - start) / (60 * 1000);
				long restartBrowserIntervalMinutes = ConfigFacade.getInstance().getCloudsTTL(this.sessid);
				log("loopProcessMessages", 614, "getRestartBrowserIntervalMinutes", curTime, "-1",
						"restartBrowserIntervalMinutes=" + restartBrowserIntervalMinutes, " remainingMinutes=" + remainingMinutes);

				// 超过系统指定的重启时间,跳出循环,在外循环重启浏览器
				if (remainingMinutes > restartBrowserIntervalMinutes) {
					logger.info("restartBrowserIntervalMinutes in loopProcessMessages:= " + restartBrowserIntervalMinutes + " remainingMinutes:= "
							+ remainingMinutes);

					log("loopProcessMessages", 615, "remainingMinutes > restartBrowserIntervalMinutes", curTime);
					curTime.setCurrentTime(localCurrentTime);
					return;
				}
			}
			
			if (isCrawlerUsed() == false) {

				log("loopProcessMessages", 701, "isCrawlerUsed", curTime);
				
				// 还应该把webdriver关掉，防止时间过程，关联丢失引起未知异常（暂时不处理）
				logger.info("isCrawlerUsed() == false sleep: 60 seconds ");
				logger.info("!!!该爬虫机已不能工作!!!");
				
				sleepSeconds(60);

				log("loopProcessMessages", 702, "sleep", curTime, "-1", "60");
				continue;
			}
	    	log( "loopProcessMessages", 600, "isCrawlerUsed", curTime, "true");
	 	    
		 	sessionStatus.setPausedFetchMessage(CrawlerManager.isPauseFetchMsg());
		 	Date currentDate = new Date();
		 	sessionStatus.setUpdatedTime(currentDate);
		 	
 		    ReportHostStatusTool.setSessionStatusMap(sessid, sessionStatus);
			logger.debug("session["+ sessid + "] set the setPausedFetchMessage ->" +CrawlerManager.isPauseFetchMsg() + " current date time: " + currentDate);

 		    if(CrawlerManager.isPauseFetchMsg()){
 		    	logger.info("isPauseFetchMsg value is: " + CrawlerManager.isPauseFetchMsg() + ". stop processing next message!");
 		    	logger.info("isPauseFetchMsg sleep: " + (int) ConfigFacade.getInstance().getCrawlerRunnerSleepDuration());
 	    		this.sleepSeconds((int) ConfigFacade.getInstance().getCrawlerRunnerSleepDuration());
 		    }else{

 		    	logger.debug("isPauseFetchMsg value is: " + CrawlerManager.isPauseFetchMsg() + ". continue processing next message!");

	 	    	// 随机获取一个APP
	 	    	//this.selectedApp = getRandomAppSeq();
	 		    this.selectedCloud = getRandomCloud();
	 		    // 机器没有分配在云里
	 	    	if (this.selectedCloud == -1) {
	 	    		if (this.isBrowserSession) {
			 		    logger.info("selectedCloud = -1" + " session没有分配在云里.");
	 	    		}
 	    			this.sleepSeconds((int) ConfigFacade.getInstance().getProcessPauseSeconds());
	 	    		continue;
	 	    	}
	 	    	
	 	    	this.selectedUser = getRandomUser(selectedCloud);
	 	    	 // 没有用户分配在云里
	 	    	if (this.selectedUser == -1) {

	 	    		if (this.isBrowserSession) {
			 		    logger.info("selectedUser = -1" + " 没有用户分配在云里.");
	 	    		}

 	    			this.sleepSeconds((int) ConfigFacade.getInstance().getProcessPauseSeconds());
	 	    		continue;
	 	    	}
	 	    	
//	 	    	this.selectedApp = getRandomAppByUser(selectedUser);
	 	    	this.selectedApp = getRandomAppFromScheduler(this.selectedUser, this.selectedCloud);
	 	    	// 选择的APP没有任务
	 	    	if (this.selectedApp == -1) {
	 	    		if (this.isBrowserSession) {
	 	    			logger.info("selectedApp = -1" + " 没有有任务的APP.");
	 	    		}
 	    			this.sleepSeconds((int) ConfigFacade.getInstance().getProcessPauseSeconds());
//		 		    logger.info("process pause time:" + (int) ConfigFacade.getInstance().getProcessPauseSeconds());

	 	    		continue;
	 	    	}
	 	    	//默认还有可用的Session
	 	    	int availableSessionNumber = 1;
				availableSessionNumber = AppFacade.getInstance().getUserAvailableSessions(selectedApp, selectedUser, selectedCloud, this.isBrowserSession);
				
				if(availableSessionNumber <= 0 ){
					//如果还可利用的Session数量 等于 0，则退出以下处理
					logger.warn("user:  " + this.selectedUser + " Cloud: " + this.selectedCloud + " 超过最大的Session数限制!!!");
 	    			this.sleepSeconds((int) ConfigFacade.getInstance().getProcessPauseSeconds());
					continue;
				}
	 		    
	 	    	log( "loopProcessMessages", 601, "getRandomAppSeq", curTime);
	 	    	
	 	    	
	 	    	// 使用新的方式来判断是否使用代理
	 	    	checkProxyUsage();
	 	    	log( "loopProcessMessages", 602, "checkProxyUsage", curTime);

				if (isBrowserSession) {
					if (this.needHttpProxy) {

						findProxy();
						if (this.proxy == null) {
							log("loopProcessMessages", 703, "findProxy is failed,this.proxy == null", curTime);
							logger.info("loopProcessMessages findProxy is failed,this.proxy == null!! ");

							this.sleepSeconds((int) ConfigFacade.getInstance().getCrawlerRunnerSleepDuration());
							continue;
						}

						log("loopProcessMessages", 603, "findProxy", curTime);
					}

					String webSiteSeq = ConfigFacade.getInstance().getApp(this.selectedApp).getWebsite_seq();
					log("loopProcessMessages", 604, "getApp", curTime);

					String ipAddress = gethostIp(this.needHttpProxy);
					log("loopProcessMessages", 605, "gethostIp", curTime);

					PolicyParameter policyParameter = new PolicyParameter();
					policyParameter.setWebsiteSeq(Integer.parseInt(webSiteSeq));
					policyParameter.setIp(ipAddress);
					policyParameter.setCrawlerHostSeq(this.crawlerid);
					policyParameter.setSessionIndex(this.sessid);

					boolean violatePolicy = PolicyFacade.getInstance().checkWebsiteIpCrawlerSessionPolicy(policyParameter);
					log("loopProcessMessages", 606, "checkWebsiteIpCrawlerSessionPolicy", curTime);
					if (violatePolicy) {

						logger.info("违反Policy：" + " webSiteSeq: " + Integer.parseInt(webSiteSeq) + "　crawlerid　" + crawlerid + " sessid " + sessid
								+ " IP Address " + ipAddress);
						logger.info("loopProcessMessages violatePolicy sleep: 5 seconds. ");

						this.sleepSeconds((int) ConfigFacade.getInstance().getCrawlerRunnerSleepDuration());

						log("loopProcessMessages", 607, "sleep", curTime, "-1", "5s");
						continue;
					}
				}
				
	 	    	// 获取消息
	 	    	fetchMessage();
	 	    	if (this.message == null) {

	 	    		log( "loopProcessMessages", 704, "fetchMessage is failed,this.message == null", curTime);

	 	    		logger.info("loopProcessMessages fetchMessage sleep: " + (int) ConfigFacade.getInstance().getCrawlerRunnerSleepDuration());
	 	    		this.sleepSeconds((int) ConfigFacade.getInstance().getCrawlerRunnerSleepDuration());
	 	    		log("loopProcessMessages", 705, "sleep", curTime, "-1", ConfigFacade.getInstance().getCrawlerRunnerSleepDuration() + "");
	 	    		continue;
	 	    	}
	 	    	log( "loopProcessMessages", 608, "fetchMessage", curTime);

	 	    	try {
					if (isBrowserSession) {
						updateProxyUsage();
						log("loopProcessMessages", 609, "updateProxyUsage", curTime);

						// 测试浏览器
						boolean testNetwork = ConfigFacade.getInstance().getTestNetworkOnRule(this.messageObject.getAppSeq(),
																							  this.messageObject.getScenarioIndex(), this.messageObject.getRuleIndex());
						if (testNetwork) {
							testBrowser();
							log("loopProcessMessages", 610, "testBrowser", curTime);
						}
					}

	 	    		// 处理消息
	 	    		processMsg(); // 其中要把所有的异常抓住

	 	    		log( "loopProcessMessages", 611, "processMsg", curTime);
	 	    	} catch ( UpdateProxyException e) {
	 	    		log( "loopProcessMessages", 901, "UpdateProxyException", curTime, e.getMessage(), e.getCode() + "", e.getName());
	 	    		logger.error("UpdateProxyException in loopProcessMessages执行失败", e);

	 	    		if (this.messageObject != null && !"TEST".equals(this.messageObject.getScheduledType())) {

	 	    			try {
	 	    				logger.info("try to return the message returnMsg2 "+ this.message.getMessage() + " " +this.crawlerid+" : "+  this.sessid);
	 	    				//TODO 判断不同的return类别
	 	    				SchedulerFacade.getInstance().returnMsg2(this.message.getQueue(), this.message.getMessage(), this.crawlerid, this.sessid, gethostIp(this.needHttpProxy));
	 	    				log( "loopProcessMessages", 902, "returnMsg", curTime , "-1", this.message.getQueue(), this.message.getMessage());

	 	    			} catch (Exception ex) {
	 	    				log( "loopProcessMessages", 903, "returnMsg", curTime , ex.getMessage(), this.message.getQueue(), this.message.getMessage());
	 	    				logger.error("returnMsg2 exception "+ e);
	 	    			}
	 	    		}

	 	    		throw new LoopProcessMessagesException(
	 	    				Constant.SYSTEM_ERR.SYSTEM_ERR_CODE_912,
	 	    				Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_CHROME_PROXY_EXCEPTION,
	 	    				e);
	 	    	} catch (TestBrowserException e) {

	 	    		log( "loopProcessMessages", 911, "TestBrowserException", curTime, e.getMessage(), e.getCode() + "", e.getName());
	 	    		logger.error("TestBrowserException exception in loopProcessMessages method ", e);

	 	    		if (this.messageObject != null && !"TEST".equals(this.messageObject.getScheduledType())) {
	 	    			try {
	 	    				logger.info("try to return the message returnMsg2 "+ this.message.getMessage() + " " +this.crawlerid+" : "+  this.sessid);
	 	    				SchedulerFacade.getInstance().returnMsg2(this.message.getQueue(), this.message.getMessage(), this.crawlerid, this.sessid, gethostIp(this.needHttpProxy));
	 	    				log( "loopProcessMessages", 912, "returnMsg", curTime , "-1", this.message.getQueue(), this.message.getMessage());

	 	    			} catch (Exception ex) {
	 	    				log( "loopProcessMessages", 913, "returnMsg", curTime , ex.getMessage(), this.message.getQueue(), this.message.getMessage());
	 	    				logger.error("returnMsg2 exception "+ e);
	 	    			}
	 	    		}

	 	    		if (this.needHttpProxy && this.proxy != null) {
	 	    			ProxyFacade.getInstance().reportNGProxy(this.crawlerid, this.sessid, this.proxy.getIp(), this.proxy.getPort());
	 	    			logger.info("reportNGProxy: "+ e.getCode() + " " +this.crawlerid+" : "+  this.sessid+" " + this.proxy.getIp()+ ": "+ this.proxy.getPort());
	 	    			log( "loopProcessMessages", 914, "NotifyProxy", curTime , e.getMessage(), e.getCode() + "", e.getName());
	 	    		}
	 	    		if(e.getCode() == WEBDRIVER_ERR.WEBDRIVER_ERR_CODE_808){
	 	    			logger.error("NoSuchSession Exception in TestBrowser()!!",e);
	 	    			throw new LoopProcessMessagesException(
	 	    					WEBDRIVER_ERR.WEBDRIVER_ERR_CODE_808,
	 	    					SYSTEM_ERR.SYSTEM_ERR_NAME_NO_SUCH_SESSION_EXCEPTION,
	 	    					SYSTEM_ERR.SYSTEM_ERR_NAME_NO_SUCH_SESSION_EXCEPTION,
	 	    					e);

	 	    		}

	 	    	} catch(PrepareProcessMsgException preE){
	 	    		log( "loopProcessMessages", 921, "PrepareProcessMsgException", curTime, preE.getMessage(), preE.getCode() + "", preE.getName());
	 	    		logger.error("PrepareProcessMsgException in loopProcessMessages method ", preE);
	 	    		if (preE.getCode() == 913 || preE.getCode() == 914 ) {

	 	    			logger.error(preE.getMessage()  + " "+ this.message.getMessage() + " " +this.crawlerid+" : "+  this.sessid);

	 	    		} else {

	 	    			if (this.messageObject != null && !"TEST".equals(this.messageObject.getScheduledType())) {

	 	    				try {

	 	    					logger.info("try to return the message returnMsg2 due to PrepareProcessMsgException "+ this.message.getMessage() + " " +this.crawlerid+" : "+  this.sessid);
	 	    					SchedulerFacade.getInstance().returnMsg2(this.message.getQueue(), this.message.getMessage(), this.crawlerid, this.sessid, gethostIp(this.needHttpProxy));

	 	    					log( "loopProcessMessages", 922, "returnMsg2", curTime , "-1", this.message.getQueue(), this.message.getMessage());

	 	    				} catch (Exception ex) {
	 	    					log( "loopProcessMessages", 923, "returnMsg2", curTime, ex.getMessage(), this.message.getQueue(), this.message.getMessage());
	 	    					logger.error("returnMsg2 exception due to PrepareProcessMsgException "+ preE);
	 	    				}
	 	    			}

	 	    		}

	 	    	} catch (ProcessMsgException e) {

	 	    		log( "loopProcessMessages", 931, "ProcessMsgException", curTime, e.getMessage(), e.getCode() + "");
	 	    		logger.error("ProcessMsgException: in loopProcessMessages method: " + e);

	 	    		if (e.getCode() >= 800 && e.getCode() <= 899) {

	 	    			if (this.needHttpProxy && this.proxy != null) {
	 	    				ProxyFacade.getInstance().reportNGProxy(this.crawlerid, this.sessid, this.proxy.getIp(), this.proxy.getPort());
	 	    				log( "loopProcessMessages", 932, "NotifyProxy", curTime, e.getMessage(), e.getCode() + "", e.getName());
	 	    			}
	 	    		}

	 	    		// TODO get异常，如果是selenium相关操作导致的是否考虑向外抛出异常，重启浏览器。
	 	    		// get异常
	 	    		if (e.getCode() == 805) {

	 	    			throw new LoopProcessMessagesException(
	 	    					Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_CODE_805,
	 	    					Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_GET_ERR,
	 	    					Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_GET_ERR);
	 	    		}
	 	    		// no such session
	 	    		if (e.getCode() == 808) {

	 	    			throw new LoopProcessMessagesException(
	 	    					Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_CODE_808,
	 	    					Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_NO_SUCH_SESSION_EXCEPTION,
	 	    					Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_NO_SUCH_SESSION_EXCEPTION);
	 	    		}

	 	    	} catch ( Throwable e) {

	 	    		logger.error("loopProcessMessages执行失败", e);
	 	    		log( "loopProcessMessages", 941, "Throwable", curTime, e.getMessage());

	 	    		throw new LoopProcessMessagesException(
	 	    				Constant.SYSTEM_ERR.SYSTEM_ERR_CODE_900,
	 	    				Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_UNKNOWN_EXCEPTION,
	 	    				e);

	 	    	} finally {

					try {
						// 执行完任务后，清除cookie
						// 不是正常抓取数据或者抓取数据时发生网路异常的情况都不清除cookie
						// if (this.rxResult != null &&
						// (this.rxResult.getFinishCode() == 200 )) {

						if (isBrowserSession) {
							deleteCookieWithSelenium();
							log("loopProcessMessages", 612, "deleteCookieWithSelenium", curTime);
							// }

							resetBrowser();
							log("loopProcessMessages", 613, "resetBrowser", curTime);
						}
					} catch (Throwable e) {

						logger.error("loopProcessMessages finally 异常发生,crawlerid=" + crawlerid + ",sessid=" + sessid, e);
						log("loopProcessMessages", 951, "finallyException", curTime, e.getMessage(), 911 + "");
					}
	 	    		curTime.setCurrentTime(localCurrentTime);
	 	    	}
	 	    }
		}
    }


	private void deleteCookieWithSelenium() {
		
		Long localCurrentTime = curTime.getCurrentTime();
		
		MessageObject messageObject = this.getAppMessageObject();
		
		log( "deleteCookieWithSelenium", 601, "deleteCookieWithSeleniumStart", curTime);
		
		if (null != messageObject) {
			boolean inheritCookie = ConfigFacade.getInstance().getClearCookieFlgOnRule(messageObject.getAppSeq(),
					messageObject.getScenarioIndex(), messageObject.getRuleIndex());
			
			log( "deleteCookieWithSelenium", 602, "getInheritCookieOnRule", curTime);

			if (inheritCookie) {

				try {
					
					if (this.webDriver != null) {
						webDriver.get("chrome://settings/cookies");
						Thread.sleep(1000);
						
						log( "deleteCookieWithSelenium", 603, "sleep", curTime);
						
						// 切换到 frame
						webDriver.switchTo().frame("settings");
						
						WebElement clearCookiebutton = webDriver.findElement(By.xpath("//*[@id='cookies-view-page']/div[2]/div/div[3]/button"));
						Actions clearCookiebuttonAction = new Actions(webDriver);
						clearCookiebuttonAction.moveToElement(clearCookiebutton);
						clearCookiebuttonAction.click(clearCookiebutton).perform();
					}
				} catch (Throwable e) {
					log( "deleteCookieWithSelenium", 901, "deleteCookieWithSeleniumException", curTime);
					logger.error("delete Cookie Exception: ", e);
					e.printStackTrace();
				} finally {
					if (this.webDriver != null) {
						webDriver.switchTo().defaultContent(); // 退出frame, 切换到主页面
					}
					log( "deleteCookieWithSelenium", 604, "deleteCookieWithSeleniumEnd", curTime);
				}
			}
		}
		
	    curTime.setCurrentTime(localCurrentTime);
	}


	private int getRandomCloud() {

		int selectedCloudSeq = -1;

		//public String[] getCloudsBySessionId(int sessionId);
		List<Cloud_users> cloudUsersList = ConfigFacade.getInstance().getCloudUsersList(this.sessid);
//		logger.info("Crawler get the Cloud_users List: " + cloudUsersList.toArray());

		if (cloudUsersList.size() == 0) {
			return selectedCloudSeq;
		}

		CloudRandomSelector randomCloudSelector = new CloudRandomSelector();
		randomCloudSelector.initializeCloudWeightList(cloudUsersList);
		
		if (randomCloudSelector.selectCloud() != null) {
			selectedCloudSeq = randomCloudSelector.selectCloud().getCloudSeq();
		}
 		if (this.isBrowserSession) {
 			logger.info("===================随机获取Cloud:==================== " + selectedCloudSeq);
 		}
		
		return selectedCloudSeq;
	}
	
	private int getRandomUser(int cloudSeq) {

		int selectedUserSeq = -1;

		List<Cloud_users> cloudUsersList = ConfigFacade.getInstance().getCloudUsersListByCloudSeq(cloudSeq);
//		logger.info("getRandomUser get the Cloud_users List: " + cloudUsersList.toArray());

		if (cloudUsersList.size() == 0) {
			return selectedUserSeq;
		}
		//TODO添加查询Cache中的Key是否有APP
		
		UserRandomSelector randomUserSelector = new UserRandomSelector();
		randomUserSelector.initializeCloudUsersList(cloudUsersList);
		selectedUserSeq = randomUserSelector.selectUser(cloudSeq);
 		if (this.isBrowserSession) {
 			logger.info("===================随机获取User:==================== " + selectedUserSeq);
 		}
		
		return selectedUserSeq;
	}
    
	private int getRandomAppByUser(int userSeq) {

		int appSeq = -1;
		List<User_apps> userAppsList = ConfigFacade.getInstance().getUserAppsList();

		List<AppWeight> appWeights = new ArrayList<>();

		for (User_apps user_apps : userAppsList) {

			if (user_apps.getUser() == userSeq) {
				AppsPerUser[] apps = user_apps.getAppsPerUser();

				for (int i = 0; i < apps.length; i++) {
					AppWeight appWeight = new AppWeight();
					appWeight.setAppSeq(apps[i].getSeq());
					appWeight.setWeight(apps[i].getApp_weight_of_private_cloud_session());
					
					appWeights.add(appWeight);
				}
				
				AppRandomSelector randomAppSelector = new AppRandomSelector();
				if (appWeights.size() >= 1) {
					randomAppSelector.initializeAppWeightList(appWeights);
					appSeq = randomAppSelector.selectApp().getAppSeq();
				}
	
				break;
			}
		}
		if (this.isBrowserSession) {
			logger.info("===================随机获取APP:==================== " + appSeq);
		}

		return appSeq;
	}
	
	
	private int getRandomAppFromScheduler(int userSeq, int cloudSeq) {

		int appSeq = -1;
		List<User_apps_scheduler> userAppsList = SchedulerAppListCacheManager.getInstance().getActiveAppListByUser(userSeq, cloudSeq, isBrowserSession);
		List<AppWeight> appWeights = new ArrayList<>();

		if (userAppsList != null) {
			for (User_apps_scheduler user_apps : userAppsList) {

				AppWeight appWeight = new AppWeight();
				appWeight.setAppSeq(user_apps.getAppSeq());
				appWeight.setWeight(user_apps.getApp_weight_of_private_cloud_session());

				appWeights.add(appWeight);
			}
		}
		AppRandomSelector randomAppSelector = new AppRandomSelector();
		if (appWeights.size() >= 1) {
			randomAppSelector.initializeAppWeightList(appWeights);
			appSeq = randomAppSelector.selectApp().getAppSeq();
		}
		if (this.isBrowserSession) {
			logger.info("===================getRandomAppFromScheduler=>随机获取APP:==================== " + appSeq);
		}

		return appSeq;
	}
	
    
	// 从Message系统获取一条消息
    private void fetchMessage() {
    	
    	Long localCurrentTime = curTime.getCurrentTime();
    	
    	this.message = null;
	    boolean isBusy = ReportHostStatusTool.isCpuAndMemoryBusy();
	    log("fetchMessage", 601, "isCpuAndMemoryBusy", curTime, "-1", "isBusy=" + isBusy);
	   
	    if (!isBusy) {
	    	String ip = gethostIp(this.needHttpProxy);
	    	log("fetchMessage", 602, "gethostIp", curTime, "-1", "needHttpProxy=" + this.needHttpProxy);
	    	int accountIndex = ConfigFacade.getInstance().getAccountIndex(this.selectedApp, this.sessid);
	    	Result response = SchedulerFacade.getInstance().fetchMessage3(crawlerid, sessid, ip, this.selectedApp, accountIndex, isBrowserSession);

	    	// 取不到消息,sleep 3 s 继续循环取
	    	if (response == null || response.getMessage() == null || "".equals(response.getMessage())) {

	    		this.message = null;
	    		log("fetchMessage", 603, "this.message = null", curTime);
	    		logger.error("不能取到消息：" + "　crawlerid　" + crawlerid + " sessid " + sessid + " this.selectedApp " + this.selectedApp);

	    	} else {
	    		this.message = response;
	    		this.messageObject = this.getAppMessageObject();
	    		logger.debug("------------------------------------this.message: " + this.message.getMessage());
	    		log("fetchMessage", 604, "this.message != null", curTime, "this.message = " + this.message.getMessage());
	    	}

	    } else {

	    	logger.info("!!!HostStatus CPU or Memory is too high!!!");
	    	Runtime.getRuntime().gc();
	    	log("fetchMessage", 605, "CpuRate > configCpuRate || MemoryRate > configMemoryRate", curTime);
	    }

	    curTime.setCurrentTime(localCurrentTime);
	}
    
    
    // 获取ip
    private String gethostIp (boolean needHttpProxy) {
    	
    	Long localCurrentTime = curTime.getCurrentTime();
    	String hostIp = null;
    	
		if (needHttpProxy) {
			if (isBrowserSession) {
				hostIp = this.proxy.getIp();
				log("gethostIp", 601, "needHttpProxy", curTime, "-1", "hostIp:" + hostIp);
			} else {
				hostIp = ConfigFacade.getInstance().getLocalCrawlerFirstIp();
			}
		} else {
			hostIp = ConfigFacade.getInstance().getLocalCrawlerFirstIp();
			log("gethostIp", 602, "-1", curTime, "-1", "hostIp:" + hostIp);
		}
		curTime.setCurrentTime(localCurrentTime);
        return hostIp;
    }
    
    
    /**
     * 对取到的message进行处理
     * 
     * @param
     */
	private void processMsg() throws ProcessMsgException, PrepareProcessMsgException {
	    
		RxResult rxResult = null;
	    
	    Long localCurrentTime = curTime.getCurrentTime();
		
		isStop = false;
		boolean pythonHasException = false;
		String pythonErrorString = "";
	   	// 计费用 
	   	Accounting accounting = new Accounting();
	   	
		ProcessMsgParameter processMsgParameter = new ProcessMsgParameter();
		
		//插件執行前的準備工作,如果有異常發生捕獲異常,然後調用returnMsg
		prepareProcessMsg(processMsgParameter) ;
		log("processMsg", 601, "prepareProcessMsg", curTime);
		
		
		// 兼容python
		InputJsonForPython intputJson = new InputJsonForPython();
		if (isBrowserSession) {
			if (ScriptFile.SCRIPT_TYPE_PYTHON.equals(scriptType)) {

				String[] chromeInfo = getChromeInfo(webDriver);
				if (chromeInfo != null) {
					String chromeSessionId = chromeInfo[0];
					String chromeUrl = chromeInfo[1];
					intputJson.setChromeSessionID(chromeSessionId);
					intputJson.setChromeUrl(chromeUrl);
				}

				String appTaskHostIp = ConfigFacade.getInstance().getAppTaskHostIpByAppSeq(this.messageObject.getAppSeq());
				intputJson.setAppTaskHost(appTaskHostIp);
				intputJson.setDB(processMsgParameter.getConInfo());

			}
		}
		
		String crawlerIp = gethostIp(this.needHttpProxy);
		// 获取当前session对应的app账号和密码
		UserAndPassword sessionCredential = ConfigFacade.getInstance().getSessionUserAndPassword(this.selectedApp, this.sessid);
		String userName = "";
		String password = "";
		if (null != sessionCredential) {
			userName = sessionCredential.getUser();
			password = sessionCredential.getPass();
		}
					
        // 爬虫通知APP开始执行任务
		try {
			AppFacade.getInstance().notifyAppTaskStarted4(this.messageObject.getAppSeq(),
														  this.messageObject.getScenarioIndex(), 
														  this.messageObject.getRuleIndex(),
														  this.messageObject.getAppTaskSeq(), 
														  200, 
														  this.messageObject.getUserSeq(),
														  this.selectedCloud,
														  crawlerIp,
														  this.crawlerid,
														  this.sessid,
														  isBrowserSession);
			log("processMsg", 602, "notifyAppTaskStarted2", curTime);
			pushAppTaskLog(Constant.APPTASK_LOG_KEY.APP_LOG_STATE_START, Constant.APPTASK_LOG_KEY.APP_LOG_MSG_START);
		} catch (Exception e) {
			log("processMsg", 901, "notifyAppTaskStarted", curTime, e.getMessage(), 903 + "",Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_NOTIFYAPPTASKSTARTED_EXCEPTION);
			// 爬虫通知APP任务结束
			logger.error("notifyAppTaskStarted3异常", e);
			
			return;
		} finally {
			curTime.setCurrentTime(localCurrentTime);
		}
		
		if (isBrowserSession) {
			// 通知Policy访问频率
			try {
				PolicyParameter policyParameter = new PolicyParameter();
				policyParameter.setWebsiteSeq(Integer.parseInt(processMsgParameter.getApp().getWebsite_seq()));
				policyParameter.setIp(gethostIp(this.needHttpProxy));
				policyParameter.setCrawlerHostSeq(this.crawlerid);
				policyParameter.setSessionIndex(this.sessid);
				PolicyFacade.getInstance().notifyWebsiteIpCrawlerSessionActivity(policyParameter);
				log("processMsg", 604, "notifyWebsiteIpCrawlerSessionActivity", curTime);
			} catch (Throwable e) {
				log("processMsg", 911, "notifyWebsiteIpCrawlerSessionActivityException", curTime);
				logger.error(" notifyWebsiteIpCrawlerSessionActivity 失败", e);
			}
		}
		
		final RxTask rxTask = injectRxTask(processMsgParameter.getTaskInfo(), userName, password);
		//应该不用加isBrowserSession, 加上后应该也没有错
		if (isBrowserSession) {
			String inputJsonStr = "";
			if (ScriptFile.SCRIPT_TYPE_PYTHON.equals(scriptType)) {
				intputJson.setTask((RxTaskImpl) rxTask);
				try {
					inputJsonStr = JsonUtil.convertObj2JsonStr(intputJson);
				} catch (Exception e) {
					logger.error("python兼容------inputJson转换失败！");
				}

				logger.info("python兼容------转换后的json:" + inputJsonStr);
				String fileDir = WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.FLOOD + File.separator + WebDriverConfig.SESSION	+ this.sessid;
				boolean isWindowsHost = ConfigFacade.isWindowsHost();
				if (!isWindowsHost) {
					fileDir = File.separator + WebDriverConfig.FLOOD + File.separator + WebDriverConfig.SESSION + this.sessid;
				}

				FileUtil.checkPath(fileDir);
				String filename = "input";
				String filePath = fileDir + File.separator + filename + ScriptFile.FILE_SUFFIXES_JSON;

				FileUtil.write(filePath, inputJsonStr);

			}
		}
		
		// ==================================================================================================================================================
		// 当CrawlerRunner启动一个任务的时候，把该任务对应的Future<String>对象存储到RXRedisSubscribeThread的taskMap中，key值为Task_<appSeq>_<scenarioIndex>_<ruleIndex>_<taskSeq>
		String key = "Task_" + this.messageObject.getAppSeq() + "_" + this.messageObject.getScenarioIndex() 
  		       + "_" + this.messageObject.getRuleIndex() + "_" + this.messageObject.getAppTaskSeq();
		// ===================================================================================================================================================
		
		// 调用实例进行爬取任务
		try {

			log( "processMsg", 605, "getSessionUserAndPassword", curTime, "userName:" + userName, "passWord:" + password);
			
			// 获取绑定的ip
			String hostIp = gethostIp(this.needHttpProxy);
			log( "processMsg", 606, "gethostIp", curTime, "-1", "hostIp:" + hostIp );
			
			logger.info( "processMsg crawler.execute start:"
					  +  " apptask=" + this.messageObject.getAppTaskSeq()
					  +  " crawlerid "+ crawlerid
					  +  " sessid " + sessid
					  +  " ip= " + gethostIp(this.needHttpProxy));
			log( "processMsg", 607, "crawler.execute start",  curTime, "-1", "apptask=" + this.messageObject.getAppTaskSeq(),"ip=" + gethostIp(this.needHttpProxy));
		
			RxRule rxRule = processMsgParameter.getRxRule();
			//插件是Java代码
			if (rxRule != null) {
				// 把插件的运行挪到另外一个线程.
				Callable<RxResult> pluginThread = new Callable<RxResult>() {

					public RxResult call() throws Exception {

						final Thread currentThread = Thread.currentThread();
						currentThread.setName("crawler_session_" + sessid);
						logger.info("-----start execution from the plugin---");
						RxResult rxResultLocal = null;
						final RxCrawlerImpl rxCrawler = new RxCrawlerImpl(webDriver, accounting);
						try {
							rxCrawler.setSessionId(sessid);
							rxCrawler.setUsingProxy(needHttpProxy);
							rxCrawler.setCrawlerid(crawlerid);
							rxCrawler.setRuleVersion(ruleVersion);
							rxCrawler.setMessageObject(messageObject);
							rxTask.setRxCrawler(rxCrawler);
							final RxDatabaseImpl rxDatabase = new RxDatabaseImpl(processMsgParameter.getConInfo());
							//createAppDataTable 需要下边的信息
							rxDatabase.setUserSeq(messageObject.getUserSeq());
							rxDatabase.setAppSeq(messageObject.getAppSeq());
							rxDatabase.setScenarioIndex((messageObject.getScenarioIndex()));
							rxDatabase.setRuleIndex(messageObject.getRuleIndex());
							
							rxResultLocal = rxRule.execute(rxTask, rxCrawler, rxDatabase);

						} catch (RxCrawlerException e) {

							String fullStackTrace = ExceptionUtils.getFullStackTrace(e);
							logger.error("fullStackTrace inside Callable:" + fullStackTrace);

							if (fullStackTrace.contains("该任务已被用户请求强制终止")) {
								isStop = true;
							}
							if (rxResultLocal == null) {
								rxResultLocal = new RxResult(e.getCode());
								if (isStop) {
									rxResultLocal.setAbortedException("该任务已被用户请求强制终止!");
									logger.error("该任务已被用户请求强制终止!");
								} else {
									logger.error(fullStackTrace);
									
									String simpleExceptionMessage = fullStackTrace;
									rxResultLocal.setAbortedException(simpleExceptionMessage);
									pushAppTaskLog(Constant.APPTASK_LOG_KEY.APP_LOG_STATE_ERROR, "RxCrawlerException:" + simpleExceptionMessage);
								}
							}

							logger.error("RxCrawlerException:", e);
							// 浏览器启动问题
							if (e.getCode() > 800 && e.getCode() <= 810) {
								logger.error("RxCrawlerException-code:" + e.getCode());
								throw e;
							}
						} catch (Throwable e) {
							logger.error("Exception inside Callable<RxResult>: ", e);
							throw e;
						} finally {
							
							if ( rxCrawler.getHttpClient() != null ) {
								rxCrawler.getHttpClient().close();
							}

							if ( rxCrawler.getHtmlUnitWebClient() != null ) {
								rxCrawler.getHtmlUnitWebClient().close();
							}
						}
						logger.info("-----end execution from the plugin---");
						return rxResultLocal;
					}
				};

				Future<RxResult> future = null;
				//插件执行前取出所有的Log
				if (ConfigFacade.getInstance().isLogSwitchOn()) {
					if(isBrowserSession){
						this.webDriver.manage().logs().get(LogType.PERFORMANCE);
					}
				}
				try {
					logger.info("插件任务执行开始");
					future = executorService.submit(pluginThread);
					// ==========================================================
					// 添加到监控TasksMap
					RxRedisSubscribeThread.getInstance(this.crawlerid).putTasksMap(key, future);
					// ==========================================================
					// 任务处理超时时间设为 2h 根据2018.01.12号的讨论
					rxResult = future.get(2 * 60 * 60, TimeUnit.SECONDS);
//					临时测试改成1分钟
//					this.rxResult = future.get(1 * 60, TimeUnit.SECONDS);

					if (rxResult == null) {
						rxResult = new RxResult(999);
						rxResult.setAbortedException("应用异常:应用代码没有正常返回RxResult!");
						pushAppTaskLog(Constant.APPTASK_LOG_KEY.APP_LOG_STATE_ERROR, "应用异常:应用代码没有正常返回RxResult!");
					}

					logger.info("插件任务执行结束,FinishCode: " + rxResult.getFinishCode());

				} catch (TimeoutException ex) {

					future.cancel(true);

					logger.error("插件任务执行超时", ex);

					rxResult = new RxResult(999);
					rxResult.setAbortedException("您的任务长时间运行没有结束,已经被平台终止,如果有疑问,请联系瑞雪采集云团队.");

					pushAppTaskLog(Constant.APPTASK_LOG_KEY.APP_LOG_STATE_ERROR, "您的任务长时间运行没有结束,已经被平台终止,如果有疑问,请联系瑞雪采集云团队.");

					throw new ProcessMsgException(Constant.SYSTEM_ERR.SYSTEM_ERR_CODE_999, "应用任务执行超时", ex);

				}

				log("processMsg", 608, "crawler.execute", curTime);
				log("processMsg", 609, "crawler.execute end", curTime, "-1", "apptask=" + this.messageObject.getAppTaskSeq(),
						"ip=" + gethostIp(this.needHttpProxy));
			} else {
				if (isBrowserSession) {
					// Python执行
					try {
						String pythonPluginFolder = WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.FLOOD + File.separator
								+ WebDriverConfig.SESSION + this.sessid + File.separator;

						String shellCommand = "cmd";
						String commandIndicator = "/c";

						boolean isWindowsHost = ConfigFacade.isWindowsHost();
						if (!isWindowsHost) {
							pythonPluginFolder = File.separator + WebDriverConfig.FLOOD + File.separator + WebDriverConfig.SESSION + this.sessid
									+ File.separator;
							shellCommand = "/bin/sh";
							commandIndicator = "-c";
						}

						String pythonCmd = ScriptFile.SCRIPT_PYTHON_COMMAND + ScriptFile.SPACE + pythonPluginFolder + ScriptFile.MAIN_PY;
						String[] command = { shellCommand, commandIndicator, pythonCmd + " " + this.sessid };
						logger.info("command------------------------------:" + command);

						Process process = Runtime.getRuntime().exec(command);
						logger.info("getReturnMessage-InputStream------------------------------:" + getReturnMessage(process.getInputStream()));

						pythonErrorString = getReturnMessage(process.getErrorStream());
						logger.info("pythonErrorString------------------------------:" + pythonErrorString);
						int pythonExitValue = process.waitFor();
						logger.info("pythonExitValue------------------------------:" + pythonExitValue);

						// if (pythonExitValue == 1) {
						// //TODO 处理显示的错误信息
						// pushAppTaskLog(Constant.APPTASK_LOG_KEY.APP_LOG_STATE_RUNNING,
						// getReturnMessage(process.getInputStream()));
						// pushAppTaskLog(Constant.APPTASK_LOG_KEY.APP_LOG_STATE_ERROR,
						// pythonErrorString);
						//
						// }

						pythonExecutionResult = FileUtil.read(pythonPluginFolder + ScriptFile.OUTPUT_JSON);
						logger.info("pythonExecutionResult: " + pythonExecutionResult);

						// python发生异常
						if (pythonExitValue != 0 || !StringUtils.isEmpty(pythonErrorString)) {
							pythonHasException = true;
							if (!StringUtils.isEmpty(pythonErrorString)) {
								int index = StringUtils.indexOf(pythonErrorString, "Script.py");
								if (index != -1) {
									pythonErrorString = StringUtils.substring(pythonErrorString, index + 11);
								}
							} else {
								pythonErrorString = "平台发生系统异常！";
							}
						}
					} catch (Exception e) {
						logger.error("python plugin execution exception: ", e);
					}
				}
			}
		} catch (Throwable throwable) {
			
			if (throwable instanceof java.util.concurrent.CancellationException) {
				isStop = true;
			}
			
			logger.error("processMsg失败 - Throwable", throwable);
			
			Throwable cause = throwable.getCause();
			
			if ( cause instanceof RxCrawlerException) {
				

					RxCrawlerException rxCrawlerException = (RxCrawlerException) cause;
					logger.error("插件任务执行失败 - RxCrawlerException:" + rxCrawlerException.getCode());

					rxResult = new RxResult(rxCrawlerException.getCode());
					rxResult.setAbortedException(ExceptionUtils.getFullStackTrace(rxCrawlerException));
					if (isBrowserSession) {
						// 浏览器启动问题
						if (rxCrawlerException.getCode() > 800 && rxCrawlerException.getCode() <= 810) {
							throw new ProcessMsgException(rxCrawlerException.getCode(), rxCrawlerException.getMessage(), throwable);
						}
					}
			} else {
				
				logger.error("插件任务执行失败 - Throwable", throwable);
				// 20180108 插件超时异常（java.util.concurrent.TimeoutException）
				if (rxResult == null) {
					rxResult = new RxResult(999);
//					this.rxResult.setAbortedException("发生系统异常！");
					rxResult.setAbortedException(ExceptionUtils.getFullStackTrace(throwable));
				}

				if ( isStop ) {
					
					rxResult.setAbortedException("该任务已被用户请求强制终止!");
					
					logger.error("该任务已被用户请求强制终止!");
					
				}
			}
			
			if ( isStop ) {
				pushAppTaskLog(Constant.APPTASK_LOG_KEY.APP_LOG_STATE_ERROR, "该任务已被用户请求强制终止!");
				logger.error("该任务已被用户请求强制终止!");
				
			} else {
				pushAppTaskLog(Constant.APPTASK_LOG_KEY.APP_LOG_STATE_ERROR, "Throwable : " + ExceptionUtils.getFullStackTrace(throwable));
			}

			
			log( "processMsg", 951, "execute_Throwable", curTime, throwable.getMessage(), 999 + "",  Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_APP_UNKNOWN_EXCEPTION);
			
			throw new ProcessMsgException(
					Constant.SYSTEM_ERR.SYSTEM_ERR_CODE_999,
					Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_APP_UNKNOWN_EXCEPTION,
					throwable);
		} finally {
			
			// ==========================================================
			// taskMap中删除对应的Future<String>对象
            RxRedisSubscribeThread.getInstance(this.crawlerid).removeTasksMap(key);
			// ==========================================================
			
	        // 爬虫通知APP任务结束
			try {

				String status = "FINISHED";
				if (isStop) {
					status = "DELETE";
				}
				
				RxResult pythonExcutionFileResult = null;
				if(scriptType.equalsIgnoreCase(ScriptFile.SCRIPT_TYPE_PYTHON)){
					pythonExcutionFileResult = JsonUtil.convertJsonStr2Obj(pythonExecutionResult, RxResult.class);
				}
				
				String finallyExceptionMessage = "-1";
				int finallyFinishCode = 999;
				int finallyPages = -1;
				int finallyRecords = -1;
				String finallyresult1 = "-1";
				String finallyresult2 = "-1";
				String finallyresult3 = "-1";
				int finallyVerifyCodeTimes = -1;
				int finallyLastLogIndex = -1;
				
				if(scriptType.equalsIgnoreCase(ScriptFile.SCRIPT_TYPE_JAVA)){
					finallyExceptionMessage = rxResult.getAbortedException();
					if (rxResult.getAbortedException().length() >= 1000) {
						finallyExceptionMessage = rxResult.getAbortedException().substring(0, 1000);
					}
					
					finallyFinishCode = rxResult.getFinishCode();
					finallyPages = accounting.getPages();
					finallyRecords = rxResult.getRecords();
					finallyresult1 = rxResult.getResult1();
					finallyresult2 = rxResult.getResult2();
					finallyresult3 = rxResult.getResult3();
					finallyVerifyCodeTimes = accounting.getVerifyCodeTimes();
					finallyLastLogIndex = rxResult.getLastLogIndex();
				} else {

					if (pythonExcutionFileResult != null) {

						finallyExceptionMessage = pythonExcutionFileResult.getAbortedException();
						
						if (!"-1".equals(finallyExceptionMessage) && !StringUtils.isEmpty(finallyExceptionMessage)) {
							pushAppTaskLog(Constant.APPTASK_LOG_KEY.APP_LOG_STATE_ERROR, finallyExceptionMessage);
						}
						
						finallyFinishCode = pythonExcutionFileResult.getFinishCode();
						finallyresult1 = pythonExcutionFileResult.getResult1();
						finallyresult2 = pythonExcutionFileResult.getResult2();
						finallyresult3 = pythonExcutionFileResult.getResult3();
					}
					
					if (pythonHasException) {
						finallyFinishCode = 999;
						finallyExceptionMessage = pythonErrorString;
						pushAppTaskLog(Constant.APPTASK_LOG_KEY.APP_LOG_STATE_ERROR, pythonErrorString);
					}
				}
				
				
				AppFacade.getInstance().notifyAppTaskFinished9(
						this.messageObject.getAppSeq(),
						this.messageObject.getScenarioIndex(),
						this.messageObject.getRuleIndex(),
						this.messageObject.getAppTaskSeq(),
						finallyFinishCode,
						this.messageObject.getUserSeq(),
						this.selectedCloud,
						status,
						finallyPages,
						finallyRecords,
						finallyresult1,
						finallyresult2,
						finallyresult3,
						finallyLastLogIndex,
						finallyExceptionMessage,
						finallyVerifyCodeTimes,
						this.messageObject.getScheduledType(),
						this.crawlerid,
						this.sessid,
						isBrowserSession);
				
				log( "processMsg", 610, "notifyAppTaskFinished9", curTime, "-1", "FinishCode:" + finallyFinishCode);
				pushAppTaskLog(Constant.APPTASK_LOG_KEY.APP_LOG_STATE_FINISHED, Constant.APPTASK_LOG_KEY.APP_LOG_MSG_FINISHED);
				// 插件结束时清除Log
				if (ConfigFacade.getInstance().isLogSwitchOn()) {
					if (isBrowserSession) {
						this.webDriver.manage().logs().get(LogType.PERFORMANCE);
					}
				}

			} catch (Exception e) {
				
				pushAppTaskLog(Constant.APPTASK_LOG_KEY.APP_LOG_STATE_ERROR, "平台异常:通知APP任务结束异常！" );
				
				logger.error("processMsg失败  - notifyAppTaskFinishedException", e); 
				
				log( "processMsg", 961, "notifyAppTaskFinishedException", curTime, e.getMessage(), 908 + "", Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_NOTIFYAPPTASKFINISHED_EXCEPTION);
				
				throw new ProcessMsgException(
						Constant.SYSTEM_ERR.SYSTEM_ERR_CODE_908,
						Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_NOTIFYAPPTASKFINISHED_EXCEPTION, e);
			} finally {
				curTime.setCurrentTime(localCurrentTime);
			}
        }

	}

	/**
	 * 
	 */
	private void pushAppTaskLog(String logType, String logContent) {
		
		logger.info("pushAppTaskLog:" + logContent);
		
		if ("TEST".equals(this.messageObject.getScheduledType())) {
			// 开发平台log记入redis
			String log = logType + "|" + new SimpleDateFormat("HH:mm:ss").format(new Date())
					             + "|" +  logContent;
			try {
				AppFacade.getInstance().pushAppTaskLog(this.messageObject.getAppSeq(),
						                               this.messageObject.getScenarioIndex(),
						                               this.messageObject.getRuleIndex(),
						                               ruleVersion,
						                               this.messageObject.getScheduledType(),
						                               log);	
			} catch (Exception e) {
				logger.error("pushAppTaskLog异常", e);
			}
		}
		
	}
	
	private void prepareProcessMsg(ProcessMsgParameter  processMsgParameter) throws PrepareProcessMsgException {
		Long localCurrentTime = curTime.getCurrentTime();
		//Apps app, Connection con, BusinessCrawler crawler, app_task_info taskInfo
		// 测试时，实时获取配置信息。
		if ("TEST".equals(this.messageObject.getScheduledType())) {
			ConfigFacade.getInstance().refreshConfig();
		}
		
		String crawlerIp = gethostIp(this.needHttpProxy);
		
		Rules rule = ConfigFacade.getInstance().getRules(this.messageObject.getAppSeq(), this.messageObject.getScenarioIndex(), this.messageObject.getRuleIndex());
        ruleVersion = rule.getVersion();
		Apps app = ConfigFacade.getInstance().getApp(this.messageObject.getAppSeq());
		if ( app == null ) {
	    	
			log("prepareProcessMsg", 901, "-1", curTime, Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_GETAPP_EXCEPTION, 904 + "");
			
			pushAppTaskLog(Constant.APPTASK_LOG_KEY.APP_LOG_STATE_ERROR, "平台异常:" + Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_GETAPP_EXCEPTION);
			
			if ("TEST".equals(this.messageObject.getScheduledType())) {
				
				AppFacade.getInstance().notifyAppTaskFinished9(
						this.messageObject.getAppSeq(),
						this.messageObject.getScenarioIndex(),
						this.messageObject.getRuleIndex(),
						this.messageObject.getAppTaskSeq(),
						999,
						this.messageObject.getUserSeq(),
						this.selectedCloud,
						"FINISHED",
						0,
						0,
						crawlerIp,
						String.valueOf(this.crawlerid),
						String.valueOf(this.sessid),
						-1,
						Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_GETAPP_EXCEPTION,
						0,
						this.messageObject.getScheduledType(),
						this.crawlerid,
						this.sessid,
						isBrowserSession);
			} else {
				
				AppFacade.getInstance().notifyAppTaskFinished9(
						this.messageObject.getAppSeq(),
						this.messageObject.getScenarioIndex(),
						this.messageObject.getRuleIndex(),
						this.messageObject.getAppTaskSeq(),
						-1,
						this.messageObject.getUserSeq(),
						this.selectedCloud,
						"SCHEDULED",
						0,
						0,
						crawlerIp,
						String.valueOf(this.crawlerid),
						String.valueOf(this.sessid),
						-1,
						Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_GETAPP_EXCEPTION,
						0,
						this.messageObject.getScheduledType(),
						this.crawlerid,
						this.sessid,
						isBrowserSession);
				
			}
	    	
	    	curTime.setCurrentTime(localCurrentTime);

			throw new PrepareProcessMsgException(
					Constant.SYSTEM_ERR.SYSTEM_ERR_CODE_904,
					Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_GETAPP_EXCEPTION,
					Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_GETAPP_EXCEPTION);
		}
		processMsgParameter.setApp(app);
		log( "prepareProcessMsg", 601, "getApp", curTime, "-1", "App: " + app.getSeq());
		
		
		// 下载jar包，把jar包加入classpath，生成crawler实例
		try {
			// 从http服务器下载jar包
			String jarUrl = null;
			
			if(rule != null && rule.getClass_file_url() != null && !rule.getClass_file_url().equals("") && !rule.getClass_file_url().equals("-1"))
			{
			    jarUrl = rule.getClass_file_url();
			}
			
			logger.info("jar url ------------------" + jarUrl);
			log( "prepareProcessMsg", 602, "getClass_file_url", curTime, "jarUrl: " + jarUrl);
			
			String jarfilePath = HttpDownload.downloadJar(jarUrl);
			log( "prepareProcessMsg", 603, "downloadJar", curTime, "jarfp: " + jarfilePath);
			
			//Java插件处理
			if (jarUrl.endsWith("jar")) {

				scriptType = ScriptFile.SCRIPT_TYPE_JAVA;
				// 把jar包加入classpath，生成实例
				String classnm = null;
				if (rule != null && rule.getClass_full_name() != null && !rule.getClass_full_name().equals("") && !rule.getClass_full_name().equals("-1")) {
					classnm = rule.getClass_full_name();
				}

				logger.info("class name ------------------" + classnm);
				log("prepareProcessMsg", 604, "getClass_full_name", curTime, "classnm: " + classnm);

				RxRule rxRule = DynamicJarLoader.load(jarfilePath, classnm);
				processMsgParameter.setRxRule(rxRule);

				log("prepareProcessMsg", 605, "load", curTime);
			} else {
				//client Session只可能为Java类型,对于Python脚本不用做特殊判断
				scriptType = ScriptFile.SCRIPT_TYPE_PYTHON;
				// Python插件处理
				// put the py file into flood/sessionx/
				FileUtil.copyPythonFile(this.sessid, jarfilePath);
				
				// python各个文件copy
				String mainPy = ScriptFile.MAIN_PY;
				String rxCrawlerPy = ScriptFile.RXCRAWLER_PY;
				String rxNodePy = ScriptFile.RXNODE_PY;
				String rxSelectNodePy = ScriptFile.RXSELECT_NODE_PY;
				String rxDatabasePy = ScriptFile.RXDATABASE_PY;
				String rxTaskPy = ScriptFile.RXTASK_PY;
				String rxResultPy = ScriptFile.RXRESULT_PY;
				String rxCrawlerExceptionPy = ScriptFile.RXCRAWLEREXCEPTION_PY;
				String pyPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "python";
				pyPath = URLDecoder.decode(pyPath,"UTF-8");
				if (ConfigFacade.isWindowsHost()) {
					pyPath = StringUtils.substring(pyPath, 1);
					logger.info("pyPath ------------------" + pyPath);
				}
				FileUtil.copyPythonFile(this.sessid, pyPath + "/" + mainPy, ScriptFile.MAIN_PY, true);
				FileUtil.copyPythonFile(this.sessid, pyPath + "/" + rxCrawlerPy, ScriptFile.RXCRAWLER_PY, true);
				FileUtil.copyPythonFile(this.sessid, pyPath + "/" + rxNodePy, ScriptFile.RXNODE_PY, true);
				FileUtil.copyPythonFile(this.sessid, pyPath + "/" + rxSelectNodePy, ScriptFile.RXSELECT_NODE_PY, true);
				FileUtil.copyPythonFile(this.sessid, pyPath + "/" + rxDatabasePy, ScriptFile.RXDATABASE_PY, true);
				FileUtil.copyPythonFile(this.sessid, pyPath + "/" + rxTaskPy, ScriptFile.RXTASK_PY, true);
				FileUtil.copyPythonFile(this.sessid, pyPath + "/" + rxResultPy, ScriptFile.RXRESULT_PY, true);
				FileUtil.copyPythonFile(this.sessid, pyPath + "/" + rxCrawlerExceptionPy, ScriptFile.RXCRAWLEREXCEPTION_PY, true);
			}
		} catch (Exception e) {
			
			logger.error("download error", e);
			
			pushAppTaskLog(Constant.APPTASK_LOG_KEY.APP_LOG_STATE_ERROR, "平台异常:获取APP执行jar包或者Python包失败!");
			
			log( "prepareProcessMsg", 911, "downloadJar", curTime, Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_DOWNLOADJAR_EXCEPTION, 905 + "");
			
			if ("TEST".equals(this.messageObject.getScheduledType())) {
				
				AppFacade.getInstance().notifyAppTaskFinished9(
						this.messageObject.getAppSeq(),
						this.messageObject.getScenarioIndex(),
						this.messageObject.getRuleIndex(),
						this.messageObject.getAppTaskSeq(),
						999,
						this.messageObject.getUserSeq(),
						this.selectedCloud,
						"FINISHED",
						0,
						0,
						crawlerIp,
						String.valueOf(this.crawlerid),
						String.valueOf(this.sessid),
						-1,
						"获取APP执行jar包或者Python包失败!",
						0,
						this.messageObject.getScheduledType(),
						this.crawlerid,
						this.sessid,
						isBrowserSession);
			} else {
				
				AppFacade.getInstance().notifyAppTaskFinished9(
						this.messageObject.getAppSeq(),
						this.messageObject.getScenarioIndex(),
						this.messageObject.getRuleIndex(),
						this.messageObject.getAppTaskSeq(),
						-1,
						this.messageObject.getUserSeq(),
						this.selectedCloud,
						"SCHEDULED",
						0,
						0,
						crawlerIp,
						String.valueOf(this.crawlerid),
						String.valueOf(this.sessid),
						-1,
						"获取APP执行jar包或者Python包失败!",
						0,
						this.messageObject.getScheduledType(),
						this.crawlerid,
						this.sessid,
						isBrowserSession);
				
			}
			
			throw new PrepareProcessMsgException(
					Constant.SYSTEM_ERR.SYSTEM_ERR_CODE_905,
					Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_DOWNLOADJAR_EXCEPTION,
					e);
		} finally {
			curTime.setCurrentTime(localCurrentTime);
		}

		// 获取app tsakInfo 和 app_data_host info
		AppTaskAndDBInfoResponse response = null;
		try {
			response = AppFacade.getInstance().getAppTaskInfoAndDataSchemaInfo2(this.messageObject.getAppSeq(),
						                                                        this.messageObject.getScenarioIndex(),
						                                                        this.messageObject.getRuleIndex(),
						                                                        this.messageObject.getAppTaskSeq(),
						                                                        this.messageObject.getUserSeq());
			log( "prepareProcessMsg", 606, "getAppTaskInfoAndDataSchemaInfo2", curTime);
	
		} catch (Exception e) {
			
			log( "prepareProcessMsg", 921, "getAppTaskInfoAndDataSchemaInfoExcption", curTime, e.getMessage() , 906 + "", Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_NOTIFYAPPTASKSTARTED_EXCEPTION);
			logger.error("getAppTaskInfoAndDataSchemaInfo", e);
			
			pushAppTaskLog(Constant.APPTASK_LOG_KEY.APP_LOG_STATE_ERROR, "平台异常:获取APP的当前任务详细信息和Database连接信息失败！");
			
			if ("TEST".equals(this.messageObject.getScheduledType())) {
				
				AppFacade.getInstance().notifyAppTaskFinished9(
						this.messageObject.getAppSeq(),
						this.messageObject.getScenarioIndex(),
						this.messageObject.getRuleIndex(),
						this.messageObject.getAppTaskSeq(),
						999,
						this.messageObject.getUserSeq(),
						this.selectedCloud,
						"FINISHED",
						0,
						0,
						crawlerIp,
						String.valueOf(this.crawlerid),
						String.valueOf(this.sessid),
						-1,
						"获取APP的当前任务详细信息和Database连接信息失败！",
						0,
						this.messageObject.getScheduledType(),
						this.crawlerid,
						this.sessid,
						isBrowserSession);
			} else {
				
				AppFacade.getInstance().notifyAppTaskFinished9(
						this.messageObject.getAppSeq(),
						this.messageObject.getScenarioIndex(),
						this.messageObject.getRuleIndex(),
						this.messageObject.getAppTaskSeq(),
						-1,
						this.messageObject.getUserSeq(),
						this.selectedCloud,
						"SCHEDULED",
						0,
						0,
						crawlerIp,
						String.valueOf(this.crawlerid),
						String.valueOf(this.sessid),
						-1,
						"获取APP的当前任务详细信息和Database连接信息失败！",
						0,
						this.messageObject.getScheduledType(),
						this.crawlerid,
						this.sessid,
						isBrowserSession);
				
			}
			
			throw new PrepareProcessMsgException(
					Constant.SYSTEM_ERR.SYSTEM_ERR_CODE_906,
					Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_GETAPPTASKINFOANDDATASCHEMAINFO_EXCEPTION,
					e);
			
		} finally {
			curTime.setCurrentTime(localCurrentTime);
		}
		
		app_task_info taskInfo = response.getResult().getTask_info();
		
		String message = "";
		
		if ( taskInfo == null ) {

			message = "当前任务详细信息获取失败！";
			
			if ("TEST".equals(this.messageObject.getScheduledType())) {
				
				AppFacade.getInstance().notifyAppTaskFinished9(
						this.messageObject.getAppSeq(),
						this.messageObject.getScenarioIndex(),
						this.messageObject.getRuleIndex(),
						this.messageObject.getAppTaskSeq(),
						999,
						this.messageObject.getUserSeq(),
						this.selectedCloud,
						"FINISHED",
						0,
						0,
						crawlerIp,
						String.valueOf(this.crawlerid),
						String.valueOf(this.sessid),
						-1,
						message,
						0,
						this.messageObject.getScheduledType(),
						this.crawlerid,
						this.sessid,
						isBrowserSession);
			} else {
				
				AppFacade.getInstance().notifyAppTaskFinished9(
						this.messageObject.getAppSeq(),
						this.messageObject.getScenarioIndex(),
						this.messageObject.getRuleIndex(),
						this.messageObject.getAppTaskSeq(),
						-1,
						this.messageObject.getUserSeq(),
						this.selectedCloud,
						"SCHEDULED",
						0,
						0,
						crawlerIp,
						String.valueOf(this.crawlerid),
						String.valueOf(this.sessid),
						-1,
						message,
						0,
						this.messageObject.getScheduledType(),
						this.crawlerid,
						this.sessid,
						isBrowserSession);
				
			}
			
			pushAppTaskLog(Constant.APPTASK_LOG_KEY.APP_LOG_STATE_ERROR, "平台异常:" + message);
			
			throw new PrepareProcessMsgException(Constant.SYSTEM_ERR.SYSTEM_ERR_CODE_906, Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_GETAPPTASKINFOANDDATASCHEMAINFO_EXCEPTION);
				
		}
			
		if( !Constant.TASK_STATUS.SCHEDULED.equalsIgnoreCase(taskInfo.getStatus())  && !Constant.TASK_STATUS.SCHEDULING.equalsIgnoreCase(taskInfo.getStatus()) ) {
			
			// 暂停5秒再获取一次，保证状态
			sleepSeconds(5);
			
			try {
				response = AppFacade.getInstance().getAppTaskInfoAndDataSchemaInfo2(this.messageObject.getAppSeq(),
							                                                        this.messageObject.getScenarioIndex(),
							                                                        this.messageObject.getRuleIndex(),
							                                                        this.messageObject.getAppTaskSeq(),
							                                                        this.messageObject.getUserSeq());
		
			} catch (Exception e) {
				
				logger.error("getAppTaskInfoAndDataSchemaInfo", e);
				
				pushAppTaskLog(Constant.APPTASK_LOG_KEY.APP_LOG_STATE_ERROR, "平台异常:获取APP的当前任务详细信息和Database连接信息失败！");
				
				if ("TEST".equals(this.messageObject.getScheduledType())) {
					
					AppFacade.getInstance().notifyAppTaskFinished9(
							this.messageObject.getAppSeq(),
							this.messageObject.getScenarioIndex(),
							this.messageObject.getRuleIndex(),
							this.messageObject.getAppTaskSeq(),
							999,
							this.messageObject.getUserSeq(),
							this.selectedCloud,
							"FINISHED",
							0,
							0,
							crawlerIp,
							String.valueOf(this.crawlerid),
							String.valueOf(this.sessid),
							-1,
							"获取APP的当前任务详细信息和Database连接信息失败！",
							0,
							this.messageObject.getScheduledType(),
							this.crawlerid,
							this.sessid,
							isBrowserSession);
				}
				
				throw new PrepareProcessMsgException(
						Constant.SYSTEM_ERR.SYSTEM_ERR_CODE_906,
						Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_GETAPPTASKINFOANDDATASCHEMAINFO_EXCEPTION,
						e);
				
			} finally {
				curTime.setCurrentTime(localCurrentTime);
			}
			
			taskInfo = response.getResult().getTask_info();
			
			if ( taskInfo == null ) {

				message = "当前任务详细信息获取失败！";
				
				if ("TEST".equals(this.messageObject.getScheduledType())) {
					
					AppFacade.getInstance().notifyAppTaskFinished9(
							this.messageObject.getAppSeq(),
							this.messageObject.getScenarioIndex(),
							this.messageObject.getRuleIndex(),
							this.messageObject.getAppTaskSeq(),
							999,
							this.messageObject.getUserSeq(),
							this.selectedCloud,
							"FINISHED",
							0,
							0,
							crawlerIp,
							String.valueOf(this.crawlerid),
							String.valueOf(this.sessid),
							-1,
							message,
							0,
							this.messageObject.getScheduledType(),
							this.crawlerid,
							this.sessid,
							isBrowserSession);
				}
				
				pushAppTaskLog(Constant.APPTASK_LOG_KEY.APP_LOG_STATE_ERROR, "平台异常:" + message);
				
				throw new PrepareProcessMsgException(Constant.SYSTEM_ERR.SYSTEM_ERR_CODE_906, Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_GETAPPTASKINFOANDDATASCHEMAINFO_EXCEPTION);
					
			}
			
			if( !Constant.TASK_STATUS.SCHEDULED.equalsIgnoreCase(taskInfo.getStatus()) && !Constant.TASK_STATUS.SCHEDULING.equalsIgnoreCase(taskInfo.getStatus())) {
				
				if (Constant.TASK_STATUS.NEW.equalsIgnoreCase(taskInfo.getStatus()) 
						|| Constant.TASK_STATUS.READY.equalsIgnoreCase(taskInfo.getStatus())
						|| Constant.TASK_STATUS.DELETE.equalsIgnoreCase(taskInfo.getStatus())
						|| Constant.TASK_STATUS.FINISHED.equalsIgnoreCase(taskInfo.getStatus()) ) {
					
					message = "当前状态为:【" + taskInfo.getStatus() + "】 " + ",状态不合法,不执行(该任务调度后状态由【SCHEDULED】改为了" + "【" + taskInfo.getStatus() + "】)";
					if (Constant.TASK_STATUS.DELETE.equalsIgnoreCase(taskInfo.getStatus())) {
						message = "当前状态为:【" + taskInfo.getStatus() + "】" + "，当前任务已删除或用户停止了当前任务!";
					}
					
					pushAppTaskLog(Constant.APPTASK_LOG_KEY.APP_LOG_STATE_ERROR, message);
					
					throw new PrepareProcessMsgException(914, "-1", message);
					
				}
				
				message = "当前状态为:【" + taskInfo.getStatus() + "】,【SCHEDULED】以外的状态不能执行！";
				
				AppFacade.getInstance().notifyAppTaskFinished9(
						this.messageObject.getAppSeq(),
						this.messageObject.getScenarioIndex(),
						this.messageObject.getRuleIndex(),
						this.messageObject.getAppTaskSeq(),
						999,
						this.messageObject.getUserSeq(),
						this.selectedCloud,
						"FINISHED",
						0,
						0,
						crawlerIp,
						String.valueOf(this.crawlerid),
						String.valueOf(this.sessid),
						-1,
						message,
						0,
						this.messageObject.getScheduledType(),
						this.crawlerid,
						this.sessid,
						isBrowserSession);
				
				pushAppTaskLog(Constant.APPTASK_LOG_KEY.APP_LOG_STATE_ERROR, "平台异常:" + message);
				
				throw new PrepareProcessMsgException(913, "-1", message);
				
			}
		}
		
		processMsgParameter.setTaskInfo(taskInfo);
		log( "prepareProcessMsg", 608, "getTask_info", curTime);
		
		
		// 创建数据库连接
		app_data_host appDbInfo = response.getResult().getApp_data_host();
		log( "prepareProcessMsg", 609, "getApp_data_host", curTime );
		
		ConnectionInfo conInfo = ConnectionManager.getConnectionInfo(appDbInfo);
		processMsgParameter.setConInfo(conInfo);
		log( "prepareProcessMsg", 610, "getConnectionInfo", curTime );
	}
	
//	// 测试本地连接的联通性
//    private void testLocalDbConnection(Connection con) {
//    	
//		String testSql = "SELECT NOW()";
//		try {
//			
//			DBOperation.find(con, testSql, String.class, null);
//			log( "testLocalDbConnection", 601, "find", curTime);
//			
//			Constant.MONITOR_INFO.MYSQL_STATUS = "ON";
//			
//		} catch (Throwable e) {
//			
//			Constant.MONITOR_INFO.MYSQL_STATUS = "OFF";
//			
//			log( "testLocalDbConnection", 901, "-1", curTime, "数据库连接无效!");
//		}
//    }

    private void closeBrowser() {
    	
    	Long localCurrentTime = curTime.getCurrentTime();
		logger.info("closeBrowser start---");
		logger.info("this.webDriver: ---" + this.webDriver);

    	if (this.webDriver != null) {
    		// 关闭浏览器
    		int res = WebDriverManager.getIns().closeWebDriver(this.crawlerid, this.sessid);
    		if ( res == 0 ) {
        		log( "closeBrowser", 601, "closeWebDriver", curTime, "res == 0", "webdrver 不存在");
    		} else if (res == 1 )  {
    			log( "closeBrowser", 602, "closeWebDriver", curTime, "res == 1", "webdrver 正常关闭");
    		} else if (res == 2 )  {
    			log( "closeBrowser", 603, "closeWebDriver", curTime, "res == 2", "webdrver 异常关闭");
    		}
    		// null webDriver
    		this.webDriver = null;
    	}
    	
		log( "closeBrowser", 604, "this.webDriver = null", curTime);
		curTime.setCurrentTime(localCurrentTime);
		
		logger.info("closeBrowser end---");

    }
    
    
    private void resetBrowser() {
    	Long localCurrentTime = curTime.getCurrentTime();
    	if (this.webDriver != null) {
    		
            this.webDriver.navigate().to("http://localhost:8080/FloodServer2/");
            this.webDriver.manage().window().maximize();
    		log( "resetBrowser", 601, "this.webDriver.navigate().to", curTime);
    	}
		curTime.setCurrentTime(localCurrentTime);
    }
    
    // 睡眠指定秒数
    private void sleepSeconds( int arg ) {
    	try {
            Thread.sleep(arg * 1000);
        } catch (InterruptedException e) {
            logger.info(e.getMessage(), e);
        }
    }
    
    
    // 睡眠随机秒数
    private void sleepRandomSeconds( int max ) {
    	int randomNum = new Random().nextInt(max);
    	logger.info("I am going to sleep random seconds: " +randomNum);
    	sleepSeconds(randomNum);
        return;
    }
    
    private MessageObject getAppMessageObject() {
    	
    	MessageObject appMessageObject = null;
    	try {
			if (null != this.message) {
				appMessageObject = SchedulerFacade.getInstance().convertMsgToObj(this.message.getMessage());
			}
		} catch (Throwable e) {
			logger.error("getAppMessageObject Exception: ", e);
			throw e;
		}
    	
    	return appMessageObject;
    }
    
    //TODO move out to a util class
    private void getChromeVersion() {
    	
    	Long localCurrentTime = curTime.getCurrentTime();
		
		String chromeVersionText = "NA";
		try {
			webDriver.get("chrome://version");
			Thread.sleep(2000);
			WebElement chromeVersionElement = webDriver.findElement(By.xpath("//*[@id='version']"));
			chromeVersionText = chromeVersionElement.getText();
			logger.info("chrome version: " + chromeVersionText);

			Thread.sleep(2000);
			
			log( "getChromeVersion", 603, "sleep", curTime, "-1", "2");
			
			webDriver.get("about:blank");
			
	    	MONITOR_INFO.CHROME_VERSION = chromeVersionText;
			
		} catch (Exception e) {
			
			logger.error("getChromeVersion: ", e);
		}

		log( "getChromeVersion", 604, "webDriver.get", curTime);
		
    	curTime.setCurrentTime(localCurrentTime);
    }
    
    
    private void log( String methodName, int code, String name,  TimeWrapper millseconds) {
    	log( methodName, code, name, millseconds, "-1", "-1", "-1", "-1", "-1", "-1" );
    }
    
    private void log( String methodName, int code, String name, TimeWrapper millseconds, String comment ) {
    	log( methodName, code, name, millseconds, comment, "-1", "-1", "-1", "-1", "-1" );
    }
    
    private void log( String methodName, int code, String name, TimeWrapper millseconds, String comment, String ref1 ) {
    	log( methodName, code, name, millseconds, comment, ref1, "-1", "-1", "-1", "-1" );
    }
    
    private void log( String methodName, int code, String name, TimeWrapper millseconds, String comment, String ref1, String ref2 ) {
    	log( methodName, code, name, millseconds, comment, ref1, ref2, "-1", "-1", "-1" );
    }
    
        
    private void log( String methodName, int code, String name, TimeWrapper millseconds, String comment, String ref1, String ref2, String ref3, String ref4, String ref5 ) {
    	LogSession.log(this.crawlerid, this.sessid, this.fileName, methodName, code, name, millseconds, comment, ref1, ref2, ref3, ref4, ref5, this.localDbCon);
    }
    
    public boolean isStopFlag() {
        return stopThreadFlag;
    }

    
    public void setStopFlag(boolean flag) {
        this.stopThreadFlag = flag;
    }
    
    
    public int getSessid() {
		return sessid;
	}

    
	public void setSessid(int sessid) {
		this.sessid = sessid;
	}

	
	public int getCrawlerid() {
		return crawlerid;
	}

	
	public void setCrawlerid(int crawlerid) {
		this.crawlerid = crawlerid;
	}
	
	private RxTask injectRxTask(app_task_info taskInfo, String userName, String password) {
		RxTaskImpl rxTask = new RxTaskImpl();
		rxTask.setUserSeq(this.messageObject.getUserSeq());
		rxTask.setAppSeq(this.messageObject.getAppSeq());
		rxTask.setScenarioIndex(this.messageObject.getScenarioIndex());
		rxTask.setRuleIndex(this.messageObject.getRuleIndex());
		rxTask.setScheduleType(this.messageObject.getScheduledType());
		rxTask.setCrawlerSeq(this.crawlerid);
		rxTask.setSessionIndex(this.sessid);
		rxTask.setCrawlerHostIP(this.gethostIp(this.needHttpProxy));
		rxTask.setTaskSeq(this.messageObject.getAppTaskSeq());
		rxTask.setSourceTaskSeq(this.messageObject.getAppTaskSeq());
		rxTask.setUserName(userName);
		rxTask.setPassword(password);
		rxTask.setV1(taskInfo.getV1());
		rxTask.setV2(taskInfo.getV2());
		rxTask.setV3(taskInfo.getV3());
		rxTask.setV4(taskInfo.getV4());
		rxTask.setV5(taskInfo.getV5());
		rxTask.setV6(taskInfo.getV6());
		rxTask.setV7(taskInfo.getV7());
		rxTask.setV8(taskInfo.getV8());
		rxTask.setV9(taskInfo.getV9());
		rxTask.setRuleVersion(this.ruleVersion);
		return rxTask;
	}
	
    private String [] getChromeInfo(WebDriver webDriver) {
        
        String [] chromeInfo = new String[2];
        // driver.
        if ( webDriver instanceof RemoteWebDriver ) {
            RemoteWebDriver rWebDriver = (RemoteWebDriver) webDriver;
            // rWebDriver.
            chromeInfo[0] = String.valueOf(rWebDriver.getSessionId());
            
            logger.info("当前chrome的sessionID" + chromeInfo[0]);
            
            HttpCommandExecutor executor = (HttpCommandExecutor) rWebDriver.getCommandExecutor();
            chromeInfo[1] = executor.getAddressOfRemoteServer().toString();
            
            logger.info("当前chrome的URL" + chromeInfo[1]);
        }
        
        return chromeInfo;
    }
    
	    
    private static String getReturnMessage(final InputStream input) {

		StringBuffer buffer = new StringBuffer();
		Reader reader = new InputStreamReader(input);
		BufferedReader bf = new BufferedReader(reader);
		String line = null;
		try {
			while ((line = bf.readLine()) != null) {
				buffer.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}    
    
	private void deleteTempdataFolderBySessionID(String chromeid) {
		
		boolean isWindowsHost = ConfigFacade.isWindowsHost();
		if (isWindowsHost) {
			// 删除c://tempdata
			logger.info("deleteTempdataFolderBySessionID--" + chromeid);
			FileUtil.deleteFiles(WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.CHROME_DATA + File.separator + "chrome" + chromeid);
		} else {
			// 删除/var/tempdata
			FileUtil.deleteFiles(File.separator + WebDriverConfig.DATA_DIR + File.separator + WebDriverConfig.CHROME_DATA + File.separator + "chrome" + chromeid);
		}

		sleepRandomSeconds(30);
	}
}
