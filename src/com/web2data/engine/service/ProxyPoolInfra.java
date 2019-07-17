package com.web2data.engine.service;

import com.web2data._global.SessionThreadLocal;
import com.web2data.engine.core.SessionManager;

public class ProxyPoolInfra {

	private ProxyPoolInfra() {}
	
	
	public static ProxyPoolInfra newInstance(int sessionType, int sessionIndex) {
		return new ProxyPoolInfra();
	}
	
	
	public static ProxyPoolInfra getInstance() {

		ProxyPoolInfra result = SessionManager.getTheSession(SessionThreadLocal.getSessionType(), 
				SessionThreadLocal.getSessionIndex())._ProxyPoolInfra;
		if ( result == null ) {
			result = new ProxyPoolInfra();
		}
		return result;
	}
}
