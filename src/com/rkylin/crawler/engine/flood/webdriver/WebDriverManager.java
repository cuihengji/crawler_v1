package com.rkylin.crawler.engine.flood.webdriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.rkylin.crawler.engine.flood.common.Constant;
import com.rkylin.crawler.engine.flood.common.Constant.WebDriverConfig;
import com.rkylin.crawler.engine.flood.crawler.CrawlerManager;
import com.rkylin.crawler.engine.flood.exception.WebDriverGetException;
import com.rkylin.crawler.engine.flood.pausecrawler.RxRedisPauseFetchThread;
import com.rkylin.crawler.engine.flood.pausecrawler.SessionStatus;
import com.rkylin.crawler.engine.flood.tools.ReportHostStatusTool;
import com.rkylin.crawler.engine.flood.util.FileUtil;
import com.rkylin.crawler.engine.flood.util.StringUtil;
import com.web2data.system.config._main.ConfigFacade;

public class WebDriverManager {
   
	private static final transient Logger logger = Logger.getLogger(WebDriverManager.class);

    private static WebDriverManager wdm = null;
    
    private WebDriverManager() {
        wdpMap = new ConcurrentHashMap<String, WebDriverProxy>();
    }

    public static WebDriverManager getIns() {
        if (wdm == null) {
            wdm = new WebDriverManager();
        }
        return wdm;
    }

    private ConcurrentHashMap <String, WebDriverProxy> wdpMap = null;
    private String keyFormat = "%d:%d";

    /**
     * 根据session id取得一个WebDriver
     * 
     * @param sesssid
     *            session id
     * @return WebDriver
     */
    public  WebDriver  getDriver(int crawlerid, int sesssid) {
        String key = String.format(keyFormat, crawlerid, sesssid);
        
        if (wdpMap.containsKey(key)) {
        	logger.info("---------getDriver inside wdpMap.containsKey---------: "+key);
            return wdpMap.get(key).getWd();
        }

        return null;
    }

    /**
     * 关闭所有的WebDriver
     */
    public void closeAllWebDriver() {
        if (wdpMap == null || wdpMap.size() == 0) {
            return;
        }

        wdpMap.forEach((k, v) -> {
            WebDriver wd = v.getWd();
            logger.info("closeAllWebDriver -> " + "  wdpMap.forEach: " + wd);
            try {
//                wd.close();
                wd.quit();
            logger.info("closeAllWebDriver -> after wd.quit() " + wd);

            } catch (Throwable t) {
                logger.error(t);
            }
        });

        wdpMap.clear();
    }

    /*
     * 关闭指定的WebDriver
     */
    public int closeWebDriver(int crawlerid, int sessid) {
    	
    	logger.info("close WebDriver-> " + " crawlerid: " + crawlerid + " sessid: "+sessid);
    	logger.info("close WebDriver-> wdpMap" + wdpMap);
        if (wdpMap == null || wdpMap.size() == 0) {
            return 0;
        }
        String key = String.format(keyFormat, crawlerid, sessid);
        logger.info("close WebDriver->  key: " + key);
        
        try {
            WebDriver wd = wdpMap.get(key).getWd();
        	logger.info("close WebDriver->  wd: before quit: " + wd);
            wd.quit();
            logger.info("close WebDriver->  wd: after quit: " + wd);
            wdpMap.remove(key);
        	logger.info("close WebDriver-> wdpMap.remove(key): " + wdpMap);
            return 1;
        } catch (Throwable t) {
            logger.error("关闭chrome异常发生，" + t);
            return 2;
        }finally{
        	logger.info("close WebDriver->  FileUtil.killChromeProcessOnly()." + " sessid: "+sessid);
//            FileUtil.killChromeProcessOnly(sessid);       
        }
       
    }
    
   
    /**
     * 初始化WebDriver和Thread
     * 
     * @param sessNum
     *            session总数
     * @param needHttpProxy 是否用代理           
     */
    public synchronized void initializeCrawler(int crawlerid, int sessid, boolean isExtentionNeeded) {
    	logger.info("initializeCrawler ---start----" + sessid);
    	
        WebDriverProxy wdp = null;
        wdp = openWebDriver(crawlerid, sessid, isExtentionNeeded);
        putWebDriver(wdp);
             
    	logger.info("initializeCrawler ---end----"  + sessid);
    }

    
    private synchronized WebDriverProxy openWebDriver(int crawlerId, int sessionId, boolean isExtentionNeeded) {
    	
    	logger.info("openWebDriver ---start----");
    	
        String rootDirectory = WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.CHROME_DATA;
		boolean isWindowsHost = ConfigFacade.isWindowsHost();
		if (!isWindowsHost) {
			rootDirectory = File.separator + WebDriverConfig.DATA_DIR + File.separator + WebDriverConfig.CHROME_DATA;
		}
        
        String userDir = WebDriverConfig.USER_DATA_DIR;
        String diskCahceDir = WebDriverConfig.DISC_CACHE_DIR;

        String diskCahcePath = rootDirectory + File.separator + "chrome" + sessionId + File.separator + diskCahceDir;
        FileUtil.checkPath(diskCahcePath);
        
        String userDirPath = rootDirectory + File.separator + "chrome" + sessionId + File.separator + userDir;
        FileUtil.checkPath(userDirPath);
        
        ChromeOptions options = new ChromeOptions();
        // 自定义缓存大小
        options.addArguments("--disk-cache-size=" + "500000000");
        // 自定义缓存目录
        options.addArguments("--disk-cache-dir=" + diskCahcePath);
        // 自定义用户数据目录
        options.addArguments("--user-data-dir=" + userDirPath);
        //options.addArguments("--enable-easy-off-store-extension-install");
        //options.addArguments("--allow-file-access-from-files");
        
        options.addArguments("--homepage about:blank");
        options.addArguments("--disable-gpu");
        //options.addArguments("--disable-impl-side-painting");
        
        // 弹出框限制 
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        
        options.addArguments("--verbose");
        options.addArguments("--enable-logging");
        
        
        //options.addArguments("--headless");
        
		if (isWindowsHost) {
			options.setBinary("C:\\GreenChrome\\App\\Chrome-bin\\chrome.exe");
		}
        
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
		
        WebDriver driver = null;
		
		int retryTimes = 0;
		while (true) {
			
			String originalChromeDriver = WebDriverConfig.WINDOWS_PREFIX + File.separator +  WebDriverConfig.CHROMEDRIVER_V2_43 + WebDriverConfig.EXE;
			String chromeDriverPathPrefix = WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.CHROME_DRIVERS_FOLDER + File.separator +  WebDriverConfig.CHROMEDRIVER_V2_43;
			String targetChromeDriver = chromeDriverPathPrefix + "_" + StringUtil.preZeroAdd(sessionId) + WebDriverConfig.EXE;
			
			if (!isWindowsHost) {
				originalChromeDriver = File.separator + WebDriverConfig.CHROMEDRIVER_V2_43;
				chromeDriverPathPrefix = File.separator + WebDriverConfig.CHROME_DRIVERS_FOLDER + File.separator + WebDriverConfig.CHROMEDRIVER_V2_43;
				targetChromeDriver = chromeDriverPathPrefix + "_" + StringUtil.preZeroAdd(sessionId);
			}
			logger.info("targetChromeDriver :-->" + targetChromeDriver);
			
			try {
				FileUtil.killChromeProcessOnly(sessionId);
//				FileUtil.deleteChromeDriverExe(sessionId);
				FileUtil.copyFile(originalChromeDriver, targetChromeDriver);

			} catch (Exception e) {
				logger.error("FileUtil.file operation exception:", e);
			}
			
			try {
				logger.info("new ChromeDriver ---start----");
				System.setProperty("webdriver.chrome.driver", targetChromeDriver);
				
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				capabilities.setCapability("pageLoadStrategy", "none");
				
				if (ConfigFacade.getInstance().isLogSwitchOn()) {
					LoggingPreferences logPreferences = new LoggingPreferences();
					logPreferences.enable(LogType.PERFORMANCE, Level.ALL);
					capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPreferences);
				}
				
				driver = new ChromeDriver(capabilities);
				//driver = new ChromeDriver(options);
				
				if (driver.toString().contains("on ANY")) {
					
					logger.warn("Web Driver is not initialized successfully !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + driver);
					if (null != driver) {
						driver.quit();
					}
					
//					FileUtil.killChromeProcessOnly(sessionId);
//					FileUtil.deleteChromeDriverExe(sessionId);
//					FileUtil.deleteChromeData(sessionId);
					retryTimes++;
					logger.warn("Web Driver retried times: --> " + retryTimes);

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
					
					Thread.sleep(10000);
					continue;
				}
				logger.info("new ChromeDriver ---end----");
				//跳出while循环
				break;
				
			} catch (Throwable e) {
				logger.error("初始化WebDriver失败", e);
				
//				FileUtil.killChromeProcessOnly(sessionId);
				FileUtil.deleteChromeData(sessionId);
				continue;
				
//				throw e;
			}
		}
		
        logger.info("driver information == "+ driver);
    	logger.info("new ChromeDriver ---end----");
    	logger.info("openWebDriver ---end----");
    	
        return new WebDriverProxy(driver, crawlerId, sessionId);
    }

    private void putWebDriver(WebDriverProxy wd) {
    	
    	logger.info("putWebDriver ---start----");
        String key = String.format(keyFormat, wd.getCrawlerid(), wd.getSessionid());
        wdpMap.put(key, wd);
        
        for (Map.Entry<String, WebDriverProxy> entry  : wdpMap.entrySet()) {  
        	logger.info("WebDriverProxy Map key: "+ entry.getKey() + " " + "WebDriverProxy Map value() : "+ entry.getValue());
        }  
    	logger.info("putWebDriver ---end----");
    }
    
    
    public void get(WebDriver driver, String url, String expectedItem, int waitSeconds) throws WebDriverGetException { 
    	// 监测cpu,忙的话就不执行。
//    	while (true) {
//    		boolean isBusy = ReportHostStatusTool.isCpuBusy();
//    		if (isBusy) {
//    			sleepSeconds(1);
//    			logger.info("sleepSeconds:" + "1s");
//    			continue;
//    		} else {
//    			break;
//    		}
//    	}
		

    	boolean isXpath = expectedItem.contains("/");
    	
    	try {
    		logger.info("------------------访问链接: " + url );
			if (driver == null) {
				logger.error(" ----------------driver is null  " + driver);
			}
    	    driver.get(url);
    		logger.info("------------------url加载完成: " + url);

    	}catch(NoSuchSessionException e){
    		logger.error("webDriver.get() NoSuchSessionException 异常", e);
    		throw new WebDriverGetException(
    				Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_CODE_808,
    				Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_NO_SUCH_SESSION_EXCEPTION,
    				Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_NO_SUCH_SESSION_EXCEPTION);

    	}catch ( WebDriverException e ) {
    		logger.error("webDriver.get()异常", e);
    		throw new WebDriverGetException(
    				Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_CODE_805,
    				Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_GET_ERR,
    				Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_GET_ERR);
    	}

    	if (isXpath) {
    		// 最多等待30秒或者用户等待的时间
    		int elementWaitTime = 30;
			if (waitSeconds >= 1) {
				elementWaitTime = waitSeconds;
			}
    		WebDriverWait wait = new WebDriverWait(driver, elementWaitTime);
    		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(expectedItem)));
    	} else {
    		if (waitSeconds <= 0) {
    			// 默认采用新的pageLoadStragy后等待3秒
    			waitSeconds = 3;
    		}
    		try {
    			Thread.sleep(waitSeconds * 1000);
    		} catch (InterruptedException e) {
    			logger.error("InterruptedException异常", e);
    		}
    	}
    	
    	String title = null;
		try {
			title = StringUtil.removeSpecialChar(driver.getTitle());
		} catch (WebDriverException e) {
    		
	    	logger.error("webDriver.getTitle()异常", e);
	    	
			throw new WebDriverGetException(
					Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_CODE_806,
					Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_GETTITLE_ERR,
					Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_GETTITLE_ERR);
		}
		
		if(!isXpath){
			if (title != null && !"".equals(title) && !expectedItem.equals("-1")) {
				if (!StringUtil.contains(title, expectedItem)) {
					throw new WebDriverGetException(
							Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_CODE_806,
							Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_GETTITLE_ERR,
							Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_GETTITLE_ERR);	 
				}
			}
		}

		
		String pageSource = null;
		try {
			pageSource = driver.getPageSource();
		} catch (WebDriverException e) {
    		
	    	logger.error("webDriver.getPageSource()异常", e);
	    	
			throw new WebDriverGetException(
					Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_CODE_807,
					Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_GETPAGESOURCE_ERR,
					Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_GETPAGESOURCE_ERR);
		}
    	
		if (StringUtil.contains(pageSource,
				Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_EMPTY_RESPONSE)) {
			
			throw new WebDriverGetException(
					Constant.NETWORK_ERR.NETWORK_ERR_CODE_851,
					Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_EMPTY_RESPONSE,
					Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_EMPTY_RESPONSE);
			
		}
		
		if (StringUtil.contains(pageSource,
				Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_PROXY_CONNECTION_FAILED)) {
			
			throw new WebDriverGetException(
					Constant.NETWORK_ERR.NETWORK_ERR_CODE_852,
					Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_PROXY_CONNECTION_FAILED,
					Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_PROXY_CONNECTION_FAILED);
			
		}
		
		if (StringUtil.contains(pageSource,
				Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_CONNECTION_RESET)) {
			
			throw new WebDriverGetException(
					Constant.NETWORK_ERR.NETWORK_ERR_CODE_853,
					Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_CONNECTION_RESET,
					Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_CONNECTION_RESET);
			
		}
		
		if (StringUtil.contains(pageSource,
				Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_TUNNEL_CONNECTION_FAILED)) {
			
			throw new WebDriverGetException(
					Constant.NETWORK_ERR.NETWORK_ERR_CODE_854,
					Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_TUNNEL_CONNECTION_FAILED,
					Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_TUNNEL_CONNECTION_FAILED);
			
		}
		
		if (StringUtil.contains(pageSource,
				Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_START_WITH_ERR)) {
	    	
			logger.error("pageSource=" + pageSource);
			
			throw new WebDriverGetException(
					Constant.NETWORK_ERR.NETWORK_ERR_CODE_850,
					Constant.NETWORK_ERR.NETWORK_ERR_NAME_UNKNOWN_ERROR,
					Constant.NETWORK_ERR.NETWORK_ERR_NAME_UNKNOWN_ERROR);
			
		}
    	
    }
    
    /**
     *从CrawlerRunner移动到这个类，现在不管程序是否使用代理，我们都添加extension 
     *
     */
    private synchronized void setbackgroundZipFile (int sessid) {
    	
    	
        String templateDir = WebDriverConfig.WINDOWS_PREFIX + File.separator +WebDriverConfig.FLOOD_CONFIG;
        String zipTargetDir =  WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.FLOOD;
        
		boolean isWindowsHost = ConfigFacade.isWindowsHost();
		if (!isWindowsHost) {
			templateDir = File.separator +WebDriverConfig.FLOOD_CONFIG;
			zipTargetDir = File.separator + WebDriverConfig.FLOOD;
		}
		
        String zipFileName = WebDriverConfig.PROXY_ZIP_FILE_NAME + sessid + WebDriverConfig.ZIP;
        
        // 根据模板生成插件配置文件
        try {
			FileUtil.zipProxyFile(sessid, templateDir, zipTargetDir, zipFileName);
			logger.debug( "setZipFile 601 zipProxyFile" );
		} catch (IOException e) {
			logger.error("根据模板生成插件配置文件失败");
			logger.error(e);
			logger.error( "setZipFile  901 生成zip文件失败" + e.getMessage());
		}
    }
    
    //这个插件用于前台操作Web页面
    private synchronized void setFrontendZipFile (int sessid) {
    	
    	
        String templateDir = WebDriverConfig.WINDOWS_PREFIX + File.separator +WebDriverConfig.FLOOD_CONFIG + File.separator + WebDriverConfig.FRONTEND;
        String zipTargetDir =  WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.FLOOD;
        
		boolean isWindowsHost = ConfigFacade.isWindowsHost();
		if (!isWindowsHost) {
			templateDir = File.separator +WebDriverConfig.FLOOD_CONFIG + File.separator + WebDriverConfig.FRONTEND;
			zipTargetDir = File.separator + WebDriverConfig.FLOOD;
		}
		
        String zipFileName = WebDriverConfig.FRONT_ZIP_FILE_NAME + sessid + WebDriverConfig.ZIP;
        
        // 根据模板生成插件配置文件
        try {
			FileUtil.zipFrontFile(sessid, templateDir, zipTargetDir, zipFileName);
			logger.debug( "zipFrontFile 601 zipProxyFile" );
		} catch (IOException e) {
			logger.error("根据模板生成插件配置文件失败");
			logger.error(e);
			logger.error( "zipFrontFile  901 生成zip文件失败" + e.getMessage());
		}
    }
    
    public static String getTimestampString() {
		String timestampString;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
		Date date = new Date(System.currentTimeMillis());
		timestampString = simpleDateFormat.format(date);
		return timestampString;
	}
    
    // 睡眠指定秒数
    private void sleepSeconds( int arg ) {
    	try {
            Thread.sleep(arg * 1000);
        } catch (InterruptedException e) {
            logger.info(e.getMessage(), e);
        }
    }
    
    private static String formatSessionId(int sessionId){
    	
		String numberPrifix = "";
		
		if (sessionId < 10) {
			numberPrifix = "0";
		}

		return numberPrifix + sessionId;
    }
    
    
	public static void main(String[] args) {
//		deleteCookie(1);
		
		System.out.println(formatSessionId(5));
		System.out.println(formatSessionId(15));
	}
}
