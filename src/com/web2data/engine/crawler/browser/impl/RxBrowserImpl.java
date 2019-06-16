package com.web2data.engine.crawler.browser.impl;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.web2data._global.SessionThreadLocal;
import com.web2data.engine.crawler.browser.impl.a.RxCrawlerImpl_10_open;
import com.web2data.engine.crawler.browser.impl.a.RxCrawlerImpl_15_get;
import com.web2data.open.RxBrowser;
import com.web2data.open.RxNode;

public class RxBrowserImpl implements RxBrowser {

	//public WebDriver webDriver = null;
	
	
	
	private RxBrowserImpl() {
		//
	}
	
	public static RxBrowser getRxBrowserForCurrentSession() {
		return new RxBrowserImpl();
	}
	
	private WebDriver getWebDriver() {
		//return SessionThreadLocal.getBrowserSession()._browserInfra._webDriver;
		return null;
	}
	
	// ---------------------- 实现 ----------------------------------------
	@Override
	public void open(String url) throws RxCrawlerException {
		RxCrawlerImpl_10_open.open(url, "-1", -1, getWebDriver());
	}
	    
	@Override
	public void open(String url, int waitSeconds) throws RxCrawlerException {
		RxCrawlerImpl_10_open.open(url, "-1", waitSeconds, getWebDriver());
	}

	@Override
	public void open(String url, String targetKeyword) throws RxCrawlerException {
		RxCrawlerImpl_10_open.open(url, targetKeyword, -1, getWebDriver());
	}
	
	@Override
	public void open(String url, String targetKeyword, int waitSeconds) throws RxCrawlerException {
		RxCrawlerImpl_10_open.open(url, targetKeyword, waitSeconds, getWebDriver());
	}
	
	@Override
	public void close() {
		RxCrawlerImpl_10_open.close( getWebDriver() );
	}

	@Override
	public RxNode getNodeByXpath(String xpath) throws RxCrawlerException {
		return RxCrawlerImpl_15_get.getNodeByXpath(xpath, getWebDriver());
	}
	
	
	@Override
	public void openTabWindow() throws RxCrawlerException {
		//
	}
	
	@Override
	public void closeTabWindow() throws RxCrawlerException {
		//RxCrawlerImpl_60_tab.closeCurrentTabWindow(browser);
		//SessionThreadLocal.getBrowserSession()._browserInfra.closeCurrentTabWindow();
	}
	
    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxBrowserImpl.class);
    
}
