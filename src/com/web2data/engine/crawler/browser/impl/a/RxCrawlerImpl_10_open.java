package com.web2data.engine.crawler.browser.impl.a;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.rkylin.crawler.engine.flood.common.Constant;
import com.rkylin.crawler.engine.flood.exception.WebDriverGetException;
import com.rkylin.crawler.engine.flood.util.StringUtil;
import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.web2data._global.R;
import com.web2data._global.SessionThreadLocal;
import com.web2data.utility.U;


public class RxCrawlerImpl_10_open {

	
	public static void open(String url, String targetKeyword, int waitSeconds, WebDriver webDriver) throws RxCrawlerException {
		
		//checkInterrupted();
		
		//打开网页http://www.baidu.com, 在开发平台控制台显示打开的URL
		//pushAppTaskLog(url);

		if ((url == null) || (url.equalsIgnoreCase(""))) {
			throw new RxCrawlerException("输入的URL为空或者等于null!");
		}
		
		try {
			
			RxCrawlerImpl_10_open.get(webDriver, url, targetKeyword, waitSeconds);
			
		} catch (WebDriverGetException e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e.getCode(), "不能打开指定的URL");
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(999, t.getMessage());
		}
		
		//accounting.setPages(accounting.getPages() + 1);
	}
	
	
    public static boolean get(WebDriver driver, String url, String expectedItem, int waitSeconds) 
    		throws WebDriverGetException { 
    	
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
				throw new WebDriverGetException( R.CODE_810, "driver is null", "driver is null" );
			}
    	    driver.get(url);
    		logger.info("------------------url加载完成: " + url);
    		
    	}catch(NoSuchSessionException e){
    		logger.error("webDriver.get() NoSuchSessionException 异常", e);
    		throw new WebDriverGetException( R.CODE_811, "NoSuchSessionException", e.getMessage() );

    	}catch ( WebDriverException e ) {
    		logger.error("webDriver.get()异常", e);
    		throw new WebDriverGetException( R.CODE_812, "WebDriverException", e.getMessage() );
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
    		
    		U.sleepSeconds( waitSeconds );
    	}
    	
    	String title = null;
		try {
			title = StringUtil.removeSpecialChar(driver.getTitle());
		} catch (WebDriverException e) {
    		
	    	logger.error("webDriver.getTitle()异常", e);
	    	
			throw new WebDriverGetException( R.CODE_813,
					Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_GETTITLE_ERR,
					Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_GETTITLE_ERR);
		}
		
		if(!isXpath){
			if (title != null && !"".equals(title) && !expectedItem.equals("-1")) {
				if (!StringUtil.contains(title, expectedItem)) {
					throw new WebDriverGetException( R.CODE_814,
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
	    	
			throw new WebDriverGetException( R.CODE_815,
					Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_GETPAGESOURCE_ERR,
					Constant.WEBDRIVER_ERR.WEBDRIVER_ERR_NAME_GETPAGESOURCE_ERR);
		}
    	
		if (StringUtil.contains(pageSource,
				Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_EMPTY_RESPONSE)) {
			
			throw new WebDriverGetException( R.CODE_816,
					Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_EMPTY_RESPONSE,
					Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_EMPTY_RESPONSE);
			
		}
		
		if (StringUtil.contains(pageSource,
				Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_PROXY_CONNECTION_FAILED)) {
			
			throw new WebDriverGetException( R.CODE_817,
					Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_PROXY_CONNECTION_FAILED,
					Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_PROXY_CONNECTION_FAILED);
			
		}
		
		if (StringUtil.contains(pageSource,
				Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_CONNECTION_RESET)) {
			
			throw new WebDriverGetException( R.CODE_818,
					Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_CONNECTION_RESET,
					Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_CONNECTION_RESET);
			
		}
		
		if (StringUtil.contains(pageSource,
				Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_TUNNEL_CONNECTION_FAILED)) {
			
			throw new WebDriverGetException( R.CODE_819,
					Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_TUNNEL_CONNECTION_FAILED,
					Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_TUNNEL_CONNECTION_FAILED);
			
		}
		
		// ERR_RESPONSE_HEADERS_MULTIPLE_CONTENT_LENGTH
		
		if (StringUtil.contains(pageSource,
				Constant.NETWORK_ERR.ERR_RESPONSE_HEADERS_MULTIPLE_CONTENT_LENGTH)) {
			
			throw new WebDriverGetException( R.CODE_820,
					Constant.NETWORK_ERR.ERR_RESPONSE_HEADERS_MULTIPLE_CONTENT_LENGTH,
					Constant.NETWORK_ERR.ERR_RESPONSE_HEADERS_MULTIPLE_CONTENT_LENGTH);
			
		}
		
		if (StringUtil.contains(pageSource,
				Constant.NETWORK_ERR.NETWORK_ERR_NAME_ERR_START_WITH_ERR)) {
	    	
			logger.error("pageSource=" + pageSource);
			
			throw new WebDriverGetException( R.CODE_829,
					Constant.NETWORK_ERR.NETWORK_ERR_NAME_UNKNOWN_ERROR,
					Constant.NETWORK_ERR.NETWORK_ERR_NAME_UNKNOWN_ERROR);
			
		}
    	
		return true;
    }
    
    
    public static void close(WebDriver driver) {
    	//
    	try {
			
    		driver.get("about:blank?sessionIndex="+SessionThreadLocal.getSessionIndex());
			
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(999, t.getMessage());
		}
    }
    
    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxCrawlerImpl_10_open.class);
}
