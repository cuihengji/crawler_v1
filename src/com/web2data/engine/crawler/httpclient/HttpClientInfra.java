package com.web2data.engine.crawler.httpclient;

import com.rkylin.crawler.engine.flood.exception.OpenBrowserException;
import com.web2data._global.SessionThreadLocal;
import com.web2data.engine.core.SessionManager;
import com.web2data.engine.crawler.browser.BrowserInfra;

public class HttpClientInfra {

	private HttpClientInfra httpClientInfra = null;
	
	private HttpClientInfra() {}
	
	
	public static HttpClientInfra getInstance() {
		HttpClientInfra result = SessionManager.getTheSession(SessionThreadLocal.getSessionType(), 
				SessionThreadLocal.getSessionIndex())._HttpClientInfra;
		if ( result == null ) {
			//result = new HttpClientInfra().newInstance();
			result = new HttpClientInfra();
		}
		return result;
	}
}
