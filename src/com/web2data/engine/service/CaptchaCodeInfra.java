package com.web2data.engine.service;

import com.web2data._global.SessionThreadLocal;
import com.web2data.engine.core.SessionManager;

public class CaptchaCodeInfra {

	private CaptchaCodeInfra() {}
	
	public static CaptchaCodeInfra getInstance() {

		CaptchaCodeInfra result = SessionManager.getTheSession(SessionThreadLocal.getSessionType(), 
				SessionThreadLocal.getSessionIndex())._CaptchaCodeInfra;
		if ( result == null ) {
			result = new CaptchaCodeInfra();
		}
		return result;
	}
}
