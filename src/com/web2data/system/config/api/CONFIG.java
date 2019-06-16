package com.web2data.system.config.api;

import com.web2data.__main.ENV;
import com.web2data.system.config._main.ConfigFacade;

public class CONFIG {

	//final static boolean TESTING = true;
	
	public static int getNumberOfSeniorSession() {
		if ( ENV.IS_TESTING ) return 1;
		
		ConfigFacade cf = ConfigFacade.getInstance();
        return cf.getBrowserSessions();
	}
	
	public static int getNumberOfJuniorSession() {
		if ( ENV.IS_TESTING ) return 0;
		
		ConfigFacade cf = ConfigFacade.getInstance();
		return cf.getClientSessions();
	}
	
//	public static int getMaxLifeSecondsOfBrowserSession() {
//		//return 30 * 60;
//		return 30 * 60; // 30分钟
//	}
//	
//	public static int getMaxLifeSecondsOfClientSession() {
//		return 5 * 60; // 5分钟
//	}
	
}
