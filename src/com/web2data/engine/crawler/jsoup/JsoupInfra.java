package com.web2data.engine.crawler.jsoup;

import com.web2data._global.SessionThreadLocal;
import com.web2data.engine.core.SessionManager;

public class JsoupInfra {

	private JsoupInfra jsoupInfra = null;
	
	private JsoupInfra() {}
	
	
	public static JsoupInfra getInstance() {
		JsoupInfra result = SessionManager.getTheSession(SessionThreadLocal.getSessionType(), 
				SessionThreadLocal.getSessionIndex())._JsoupInfra;
		if ( result == null ) {
			//result = new JsoupInfra().newInstance();
			result = new JsoupInfra();
		}
		return result;
	}
	
}
