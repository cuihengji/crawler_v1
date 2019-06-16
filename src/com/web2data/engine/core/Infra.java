package com.web2data.engine.core;

import com.rkylin.crawler.engine.flood.exception.OpenBrowserException;
import com.web2data._global.SessionType;
import com.web2data.engine.crawler.browser.BrowserInfra;
import com.web2data.engine.crawler.httpclient.HttpClientInfra;
import com.web2data.engine.crawler.jsoup.JsoupInfra;
import com.web2data.open.RxCrawlerException;

public class Infra {

	
	// 看一些特殊情况，可以为一个Session准备两个Browser,用于一些特殊任务，暂时Crawler只包含一个Browser
	
	public BrowserInfra _browserInfra = null;
	
	public HttpClientInfra _httpClientInfra = null; // com.web2data.engine.crawler.httpClient
	
	public JsoupInfra _jsoupInfra = null; // com.web2data.engine.crawler.jsoupInfra
	
//	public BrowserInfra _browserInfra2 = null;
//	
//	public Chrome60BrowserInfra _chrome60BrowserInfra = null;
//	
//	public IE10BrowserInfra _ie10BrowserInfra = null;
//	
//	public Firefox30BrowserInfra _firefox30BrowserInfra = null;
//	
//	public ClientInfra _clientInfra = null;
	
	
	private Infra() {
		//
	}
	
	public static Infra newInfra( int sessionType, int sessionIndex ) {
		Infra result = new Infra();
		
		try {
			
			if ( sessionType == SessionType.SENIOR ) {
				
				// Browser.killBrowser(); // 把之前启动的浏览器删掉
				
				result._browserInfra = BrowserInfra.getInstance();
				result._httpClientInfra = HttpClientInfra.getInstance();
				result._jsoupInfra = JsoupInfra.getInstance();
				
			} 
//			else if ( sessionType == SessionType.CHROME_60_BROWSER  ) {
//				
//			} else if ( sessionType == SessionType.IE_10_BROWSER  ) {
//				
//			} else if ( sessionType == SessionType.FIREFOX_30_BROWSER  ) {
//				
//			} 
			else if ( sessionType == SessionType.JUNIOR  ) {
				
				result._httpClientInfra = HttpClientInfra.getInstance();
				result._jsoupInfra = JsoupInfra.getInstance();
				
			}
			
			return result;
			
		} catch (RxCrawlerException e) {
			//LOG.error( e.getMessage() );
		} 
		//catch (OpenBrowserException e) {
		//	//LOG.error( e.getMessage() );
		//}
		
		return null;
	}
	
}
