package com.web2data.engine.crawler.browser.impl.a;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import com.rkylin.crawler.engine.flood.common.IpProxyResult;
import com.rkylin.crawler.engine.flood.proxy.client.ProxyFacade;
import com.rkylin.crawler.engine.flood.util.HTTP;
import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.RxHttpNetwork;
import com.ruixuesoft.crawler.open.RxHttpRequest;
import com.ruixuesoft.crawler.open.RxHttpResponse;
import com.ruixuesoft.crawler.open.impl.RxHttpNetworkImpl;
import com.ruixuesoft.crawler.open.impl.RxHttpResponseImpl;

public class RxCrawlerImpl_94_Network {

	
    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxCrawlerImpl_94_Network.class);
   
	
	public static RxHttpNetwork getHttpNetworkByURL(String url, WebDriver webDriver) throws RxCrawlerException {
		// TODO 清除Log open前和open后
		LogEntries logEntries = null;

		LogEntry requestLog = null;
		LogEntry responseLog = null;

		int loopTime = 60;
		for (int i = 0; i < loopTime; i++) {

			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				logger.error("TimeUnit.SECONDS", e);
			}
			try {
				logEntries = webDriver.manage().logs().get(LogType.PERFORMANCE);
			} catch (Exception e) {
				throw new RxCrawlerException("请联系瑞雪采集云调整爬虫机参数");
			}
			for (LogEntry logEntry : logEntries) {
				String logMessage = logEntry.getMessage();

				if ((requestLog == null) && logMessage.contains("XHR") && logMessage.contains("Network.requestWillBeSent") && logMessage.contains(url)) {
					requestLog = logEntry;
					logger.info("Network.requestWillBeSent" + " : " + logEntry);
				}

				if ((responseLog == null) && logMessage.contains("XHR") && logMessage.contains("Network.responseReceived") && logMessage.contains(url)) {
					responseLog = logEntry;
					logger.info("Network.responseReceived" + " : " + logEntry);
				}
			}
			
			logger.info(new Date() + " --- requestLog: "+ requestLog + "|" + "responseLog: " + responseLog);
			if ((requestLog != null) && (responseLog != null)) {
				return new RxHttpNetworkImpl(requestLog, responseLog);
			}
		}
		return null;
	}

	
	public static RxHttpRequest copyRxHttpRequest(RxHttpRequest rxHttpRequest) {
		//this.rxHttpRequest = rxHttpRequest;
		return rxHttpRequest;
	}


	public static RxHttpResponse send(RxHttpRequest rxHttpRequest) {

		// 新版，暂时
		boolean isUsingProxy = false;
		
		
		
		RxHttpResponseImpl rxHttpResponseImpl = null;
		String responseText = "NA";

		if (rxHttpRequest != null) {
			
			String url = rxHttpRequest.getURL();
			JSONObject params = rxHttpRequest.getAllParameters();
			String method = rxHttpRequest.getMethod();

			JSONObject headers = rxHttpRequest.getAllHeaders();
			HashMap headersHashmap = jsonToHashmap(headers);
			
			JSONObject cookies = rxHttpRequest.getAllCookies();
			HashMap cookiesHashmap = jsonToHashmap(cookies);
			
			IpProxyResult ipProxyInfo = null;
			if (isUsingProxy) {
				ipProxyInfo = ProxyFacade.getInstance().fetchIpProxy(1, 1);
			}

			if (method.equalsIgnoreCase("GET")) {
				try {
					if(isUsingProxy){
						responseText = HTTP.doGet(url, ipProxyInfo.getIp(), ipProxyInfo.getPort(), ipProxyInfo.getUser_name(), ipProxyInfo.getPass_word(), headersHashmap, cookiesHashmap);
					}
					else{
						responseText = HTTP.doGet(url, headersHashmap, cookiesHashmap);
					}
				} catch (Exception e) {
					logger.error("send HTTP.doGet:", e);
				}
			} else {
				try {
					if(isUsingProxy){
						responseText = HTTP.doPost(url, ipProxyInfo.getIp(), ipProxyInfo.getPort(), ipProxyInfo.getUser_name(), ipProxyInfo.getPass_word(),jsonToHashmap(params), headersHashmap, cookiesHashmap);
					}else{
						responseText = HTTP.doPost(url, jsonToHashmap(params), headersHashmap, cookiesHashmap);
					}
				} catch (Exception e) {
					logger.error("send HTTP.doPost:", e);
				}
			}

			rxHttpResponseImpl = new RxHttpResponseImpl(responseText);
		}

		return rxHttpResponseImpl;
	}
	
	private static HashMap jsonToHashmap(JSONObject params){
		HashMap paramHasMap = new HashMap();
		if (params != null) {
			for (Iterator<String> keys = params.keys(); keys.hasNext();) {
				String paramKey = keys.next();
				String paramValue = "";
				try {
					paramValue = params.getString(paramKey);
				} catch (JSONException e) {
					logger.error("jsonToHashmap:", e);
				}
				paramHasMap.put(paramKey, paramValue);
			}
		}
		return paramHasMap;
	}
}
