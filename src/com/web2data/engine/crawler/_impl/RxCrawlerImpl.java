package com.web2data.engine.crawler._impl;


import com.rkylin.crawler.engine.flood.exception.OpenBrowserException;
import com.web2data._global.SessionThreadLocal;
import com.web2data._global.SessionType;
import com.web2data.engine.crawler.browser.impl.RxBrowserImpl;
import com.web2data.open.RxBrowser;
import com.web2data.open.RxCrawler;

public class RxCrawlerImpl implements RxCrawler {

//	private Browser browser = null;
//	private WebDriver webDriver = null;
//	
//	
//	// --------------------- static 方法，工厂模式 -------------------------
//	public static RxCrawler getCrawlerOfCurrentSession() {
//		return new RxCrawlerImpl();
//	}
//	
//	private RxCrawlerImpl() {
//		this.browser = SessionThreadLocal.getBrowserSession()._browser;
//		this.webDriver = SessionThreadLocal.getBrowserSession()._browser._webDriver;
//	}
	
//	
//    // 作为一个基础设施工具使用
//    public Browser _browser = null;
//    
//    public Chrome60Browser _chrome60Browser = null;
//    
//    public FirefoxBrowser _firefox38Browser = null;
//    
//    public IE11Browser _ie11Browser = null;  
//	
//    //protected Client client = null;
//	
//	

	public RxCrawlerImpl() {
		//this.browser = SessionThreadLocal.getBrowserSession()._browser;
		//this.webDriver = SessionThreadLocal.getBrowserSession()._browser._webDriver;
	}
	
	
	// 
	private RxBrowser _rxBrowser = null;
	
	
	public static RxCrawlerImpl getRxCrawlerForCurrentSession(){
		RxCrawlerImpl result = new RxCrawlerImpl();
		
		if ( SessionThreadLocal.getSessionType() == SessionType.SENIOR ) {
			result._rxBrowser = RxBrowserImpl.getRxBrowserForCurrentSession();
			return result;
		}
		
//		if ( SessionThreadLocal.getSessionType() == SessionType.CHROME_60_BROWSER ) {
//			WebDriver webDriver = SessionThreadLocal.getBrowserSession()._crawler._chrome60Browser._webDriver;
//			return Chrome60BrowserImpl.getChrome60Browser( webDriver );
//		}
//		
//		if ( SessionThreadLocal.getSessionType() == SessionType.FIREFOX_30_BROWSER ) {
//			WebDriver webDriver = SessionThreadLocal.getBrowserSession()._crawler._firefox30Browser._webDriver;
//			return Firefox30BrowserImpl.getFirefox30Browser( webDriver );
//		}
		
		return null;
	}
	
	
	@Override
	public RxBrowser getRxBrowser() {
		// TODO Auto-generated method stub
		return _rxBrowser;
	}
	
}
