package com.web2data.engine.crawler.browser;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.rkylin.crawler.engine.flood.common.Constant;
import com.rkylin.crawler.engine.flood.common.Constant.WebDriverConfig;
import com.rkylin.crawler.engine.flood.crawler.CrawlerManager;
import com.rkylin.crawler.engine.flood.exception.OpenBrowserException;
import com.rkylin.crawler.engine.flood.pausecrawler.RxRedisPauseFetchThread;
import com.rkylin.crawler.engine.flood.pausecrawler.SessionStatus;
import com.rkylin.crawler.engine.flood.tools.ReportHostStatusTool;
import com.rkylin.crawler.engine.flood.util.FileUtil;
import com.rkylin.crawler.engine.flood.util.StringUtil;
import com.web2data._global.G;
import com.web2data._global.SessionThreadLocal;
import com.web2data.engine.core.SessionManager;
import com.web2data.system.config._main.ConfigFacade;
import com.web2data.utility.U;

public class BrowserInfra {

	public WebDriver _webDriver = null;
	
	public WebDriver getWebDriver() {
		return _webDriver;
	}
	
	private List<String> _tabWindowList = null;
   	
	private int _activeTabWindowIndex = -1;
   	
	public List<String> getTabWindowList() {
		return _tabWindowList;
	}
	
	public void setTabWindowList(List<String> arg) {
		this._tabWindowList = arg;
	}
	
	public int getActiveTabWindowIndex() {
		return _activeTabWindowIndex;
	}
	
	public void setActiveTabWindowIndex(int arg) {
		this._activeTabWindowIndex = arg;
	}
	
	
	private BrowserInfra() {
		//
	}
	
	
	
	// 把浏览器过多的TabWindows给关闭
	public static void reset(){
		//
	}
	
	
    // 如果webdriver没有准备好的话，进行准备
    public static BrowserInfra getInstance() {
    	
    	//Long localCurrentTime = curTime.getCurrentTime();

    	try {
    		
    		// 打开浏览器
    		//if (this._webDriver == null) {
    			
    			//log( "openBrowser", 601, "if (this.webDriver == null)", curTime,  "-1");
//  				WebDriverManager.getIns().initializeCrawler(this.crawlerid, this.sessid, true);

  				//log( "openBrowser", 602, "initializeCrawler", curTime, "-1");
  				
//    			this.webDriver = WebDriverManager.getIns().getDriver(crawlerid, sessid);
    			//log( "openBrowser", 603, "getDriver", curTime, "-1");

    		
    			BrowserInfra result = SessionManager.getTheSession(SessionThreadLocal.getSessionType(), 
    					SessionThreadLocal.getSessionIndex())._BrowserInfra;
    			if ( result == null ) {
        			result = new BrowserInfra().newWebDriver();
    			}
    		
    		
    			//BrowserInfra result = new BrowserInfra();
    			//result.newWebDriver();
    			return result;
    		//}
    		
    	} catch (WebDriverException e ) {
    		
//    		if ( StringUtil.contains(e.getMessage(), Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_TIMED_OUT) ) {
//				
//    			//log( "openBrowser", 801, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_TIMED_OUT,  curTime, e.getMessage());	
//				throw new OpenBrowserException( 801, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_TIMED_OUT, e);
//				
//    		} else if ( StringUtil.contains(e.getMessage(), Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_UN_REACHABLE_BROWSER_EXCEPTION) ) {
//    			
//				//log( "openBrowser", 802, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_UN_REACHABLE_BROWSER_EXCEPTION, curTime, e.getMessage());
//				throw new OpenBrowserException( 802, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_UN_REACHABLE_BROWSER_EXCEPTION, e);
//    			
//    		} else if ( StringUtil.contains(e.getMessage(), Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_NOT_REACHABLE) ) {
//				
//    			//log( "openBrowser", 803, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_NOT_REACHABLE, curTime, e.getMessage());
//				throw new OpenBrowserException( 803, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_NOT_REACHABLE, e);
//    			
//    		} else if ( StringUtil.contains(e.getMessage(), Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_CHROME_FAILED_TO_START) ) {
//				
//    			//log( "openBrowser", 804, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_CHROME_FAILED_TO_START, curTime, e.getMessage());
//				throw new OpenBrowserException( 804, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_CHROME_FAILED_TO_START, e);
//    			
//    		} else if (StringUtil.contains(e.getMessage(), Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_NO_SUCH_SESSION_EXCEPTION)) {
//    			
//    			//log( "openBrowser", 808, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_NO_SUCH_SESSION_EXCEPTION, curTime, e.getMessage());
//				throw new OpenBrowserException( 805, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_NO_SUCH_SESSION_EXCEPTION, e);
//				
//    		} else {
//    			
//    			//log( "openBrowser", 800, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_UNKNOWN_ERROR, curTime, e.getMessage());
//    			throw new OpenBrowserException( 800, Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_UNKNOWN_ERROR, e );
//    		}
    		
    		return null;
    		
    	} catch ( Throwable e) {
    		
    		//log( "openBrowser", 900, "Throwable", curTime, e.getMessage());
			//logger.error("openBrowser 执行失败",e);
			//throw new OpenBrowserException( 809, Constant.SYSTEM_ERR.SYSTEM_ERR_NAME_UNKNOWN_EXCEPTION, e );
    		
    		return null;
    		
    	} finally {
    		//curTime.setCurrentTime(localCurrentTime);
    	}
    }
    
    
    private BrowserInfra newWebDriver() {
    	
    	logger.info("openWebDriver ---start----");
		
        //WebDriver driver = null;
        
        int sessionId = SessionThreadLocal.getSessionIndex();
		
			String originalChromeDriver = WebDriverConfig.WINDOWS_PREFIX + File.separator +  WebDriverConfig.CHROMEDRIVER_V72 + WebDriverConfig.EXE;
			String chromeDriverPathPrefix = WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.CHROME_DRIVERS_FOLDER + File.separator +  WebDriverConfig.CHROMEDRIVER_V72;
			String targetChromeDriver = chromeDriverPathPrefix + "_" + StringUtil.preZeroAdd(sessionId) + WebDriverConfig.EXE;
			
			boolean isWindowsHost = ConfigFacade.isWindowsHost();
			logger.info("isWindowsHost :-->" + isWindowsHost);
			
			if (!isWindowsHost) {
				originalChromeDriver = File.separator + WebDriverConfig.CHROMEDRIVER_V72;
				chromeDriverPathPrefix = File.separator + WebDriverConfig.CHROME_DRIVERS_FOLDER + File.separator + WebDriverConfig.CHROMEDRIVER_V72;
				targetChromeDriver = chromeDriverPathPrefix + "_" + StringUtil.preZeroAdd(sessionId);
				logger.info("targetChromeDriver :-->" + targetChromeDriver);
			}
			
			
			
			try {
				FileUtil.killChromeProcessOnly(sessionId);
				U.sleepSeconds( 5 );
					
//				FileUtil.deleteChromeDriverExe(sessionId);
				FileUtil.copyFile(originalChromeDriver, targetChromeDriver);

			} catch (Exception e) {
				logger.error("FileUtil.file operation exception:", e);
			}
			
			try {
				logger.info("new ChromeDriver ---start----");
				System.setProperty("webdriver.chrome.driver", targetChromeDriver);
				
				System.out.println("chromeDriverPathPrefix = " + chromeDriverPathPrefix);
				
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				//capabilities.setCapability( ChromeOptions.CAPABILITY, getChromeOptions( sessionId, true ) );
				//capabilities.setCapability("pageLoadStrategy", "none");

/*
 * 暂时先注释掉，因为没有连接上 Config系统，所以 isLogSwitchOn()会出错！
				if (ConfigFacade.getInstance().isLogSwitchOn()) {
					LoggingPreferences logPreferences = new LoggingPreferences();
					logPreferences.enable(LogType.PERFORMANCE, Level.ALL);
					capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPreferences);
				}
*/
				
				//driver = new ChromeDriver(capabilities);
				//driver = new ChromeDriver(options);
				
				_webDriver = new ChromeDriver(capabilities);
				
				System.out.println( "newWebDriver()._webDriver = " + _webDriver );
				
				if (_webDriver.toString().contains("on ANY")) {
					
					logger.warn("Web Driver is not initialized successfully !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + _webDriver);
					if (null != _webDriver) {
						_webDriver.quit();
					}
					
//					FileUtil.killChromeProcessOnly(sessionId);
//					FileUtil.deleteChromeDriverExe(sessionId);
//					FileUtil.deleteChromeData(sessionId);
					
///					retryTimes++;
///					logger.warn("Web Driver retried times: --> " + retryTimes);

					
/*
					if (retryTimes == 30) {
						
						//设置这个Session的状态为停止取消息,等待其他Session结束任务后重启爬虫机
				    	SessionStatus sessionStatus = new SessionStatus();
				    	sessionStatus.setPausedFetchMessage(true);
			    	 	Date currentDate = new Date();
			    	 	sessionStatus.setUpdatedTime(currentDate);
			        	ReportHostStatusTool.setSessionStatusMap(sessionId, sessionStatus);
			        	
			    		logger.info("session["+ sessionId + "] set the setPausedFetchMessage true in WebDriverProxy openWebDriver method." + " current date time: " + currentDate);
			    		
			    		CrawlerManager.setPauseFetchMsg(true);
						RxRedisPauseFetchThread.restartComputer();
			    		logger.info("RxRedisPauseFetchThread.restartComputer() current date time: " + currentDate);

					}
*/

					
					U.sleepSeconds( 5 * 60 ); // 留有时间查询问题
///					continue;
				}
				
				
				_webDriver.get( "about:blank?index=" + sessionId + "&time=" + new Date() );
				
				_webDriver.get( "http://www.bing.com" );
				
				System.out.println("new ChromeDriver ---end----");
				
				logger.info("new ChromeDriver ---end----");
				//跳出while循环
///				break;
				
			} catch (Throwable e) {
				logger.error("初始化WebDriver失败", e);
				
//				FileUtil.killChromeProcessOnly(sessionId);
				FileUtil.deleteChromeData(sessionId);
///				continue;
				
				throw e;
			}
		
        logger.info("driver information == "+ _webDriver);

/*
        return new WebDriverProxy(driver, crawlerId, sessionId);
*/
    	return this;
    }
    
    
    private static ChromeOptions getChromeOptions( int sessionIndex, boolean isExtentionNeeded ) {
    	ChromeOptions options = new ChromeOptions();
    	
        //String rootDirectory = WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.CHROME_DATA;
		
        //boolean isWindowsHost = ConfigFacade.isWindowsHost();
		//if (!isWindowsHost) {
		//	rootDirectory = File.separator + WebDriverConfig.DATA_DIR + File.separator + WebDriverConfig.CHROME_DATA;
		//}
        
        //String diskCahcePath = rootDirectory + File.separator + "chrome" + sessionId + File.separator + WebDriverConfig.DISC_CACHE_DIR;
        FileUtil.checkPath( G.getBrowserSessionDiskCacheRoot(sessionIndex) );
        
        //String userDirPath = rootDirectory + File.separator + "chrome" + sessionIndex + File.separator + WebDriverConfig.USER_DATA_DIR;
        FileUtil.checkPath( G.getBrowserSessionUserDataRoot(sessionIndex) );
        
        
        // 自定义缓存大小
        options.addArguments("--disk-cache-size=" + "500000000");
        // 自定义缓存目录
        options.addArguments("--disk-cache-dir=" + G.getBrowserSessionDiskCacheRoot(sessionIndex) );
        // 自定义用户数据目录
        options.addArguments("--user-data-dir=" + G.getBrowserSessionUserDataRoot(sessionIndex) );
        //options.addArguments("--enable-easy-off-store-extension-install");
        //options.addArguments("--allow-file-access-from-files");
        
        options.addArguments("--homepage about:blank?sessionIndex="+sessionIndex);
        options.addArguments("--disable-gpu");
        //options.addArguments("--disable-impl-side-painting");
        
        // 弹出框限制 
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        
        options.addArguments("--verbose");
        options.addArguments("--enable-logging");
        
        
        //options.addArguments("--headless");
        
		//if (isWindowsHost) {
			//options.setBinary("C:\\GreenChrome\\App\\Chrome-bin\\chrome.exe");
			options.setBinary( G.CHROME_SOFTWARE_ROOT_DIR );
		//}

/*
        //不管任何情况下都使用 extension, 在扩展中决定是否使用代理
		if (isExtentionNeeded) {
			setbackgroundZipFile(sessionId);
			
			// 设置代理ip和端口
			String zipTargetDir = WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.FLOOD + File.separator;
			if (!isWindowsHost) {
				zipTargetDir = File.separator + WebDriverConfig.FLOOD + File.separator;
			}
			
			String proxyZipFileName = WebDriverConfig.PROXY_ZIP_FILE_NAME + sessionId + WebDriverConfig.ZIP;
			String proxyzipFilePath = zipTargetDir + proxyZipFileName;
			options.addExtensions(new File(proxyzipFilePath));
		
//			//设置前台控制插件的扩展
//			setFrontendZipFile(sessionId);
//			
//			String frontZipFileName = WebDriverConfig.FRONT_ZIP_FILE_NAME + sessionId + WebDriverConfig.ZIP;
//			String frontZipFilePath = zipTargetDir + frontZipFileName;
//			options.addExtensions(new File(frontZipFilePath));
			
		} else {
        	// 禁用扩展插件
        	options.addArguments("--disable-extensions");
        }
*/
		// 禁用扩展插件
		options.addArguments("--disable-extensions");
		
    	return options;
    }
    
    
    public void refreshTabWindows() {
		List<String> temp = new ArrayList<String> (_webDriver.getWindowHandles());
		setTabWindowList(temp);
		setActiveTabWindowIndex(temp.size() - 1);
		
		for ( int i = 0; i < temp.size(); i++ ) {
			System.out.println( i+" = " + temp.get(i) );
		}
		
		// 自动把转到新弹出的TabWindow上
		_webDriver.switchTo().window( temp.get( temp.size() - 1 ) );
		
		
		// 如果已经打开5个TabWindow，则进行异常处理，设置一个特殊的 finished_code
		if ( temp.size() >= 5 ) {
			//
		}
		
    	//int newWindowHandleSize = tabWindowHandleListAfterClick.size();
    	//
		//if (newWindowHandleSize > windowHandleSize) {
        //	newTabWindow.setNewTabWindowHandleIndex(newWindowHandleSize - 1);
		//} else {
        //	newTabWindow.setNewTabWindowHandleIndex(newWindowHandleSize > 1 ? (tabWindowHandleListAfterClick.size() - 1) : 0);
		//}
    }
    
    public void closeCurrentTabWindow() {
    	if ( _tabWindowList == null || _tabWindowList.size() == 1 )
    		return;
    	
    	int currentTabWindowIndex = _tabWindowList.size() - 1;
    	_webDriver.switchTo().window( _tabWindowList.get( currentTabWindowIndex ) ).close();
    	_tabWindowList.remove( currentTabWindowIndex );
    	_webDriver.switchTo().window( _tabWindowList.get( currentTabWindowIndex - 1 ) );
    }
    
    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(BrowserInfra.class);
    
}
