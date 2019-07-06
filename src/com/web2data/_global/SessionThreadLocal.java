package com.web2data._global;

import com.web2data.engine.core.Session;
import com.web2data.engine.core.SessionManager;
import com.web2data.engine.crawler.httpclient.HttpClientInfra;

public class SessionThreadLocal {

	
	public static boolean isBROWSER() {
		if ( SessionThreadLocal.getSessionType() == SessionType.SENIOR ) {
			return true;
		}
		return false;
	}
	
	public static boolean isCLIENT() {
		if ( SessionThreadLocal.getSessionType() == SessionType.JUNIOR ) {
			return true;
		}
		return false;
	}
	
	public static boolean isSENIOR() {
		if ( SessionThreadLocal.getSessionType() == SessionType.SENIOR ) {
			return true;
		}
		return false;
	}
	
	public static boolean isJUNIOR() {
		if ( SessionThreadLocal.getSessionType() == SessionType.JUNIOR ) {
			return true;
		}
		return false;
	}
	
	
	// -------------------------------------------------------
	
	private static ThreadLocal<Integer> sessionType = new ThreadLocal<Integer>();
	
    public static void setSessionType( int arg ) {
    	sessionType.set( arg );
    }
    
    public static int getSessionType() {
        return sessionType.get().intValue();
    }
    
    
	private static ThreadLocal<Integer> sessionIndex = new ThreadLocal<Integer>();
	
    public static void setSessionIndex( int arg ) {
    	sessionIndex.set( arg );
    }
    
    public static int getSessionIndex() {
        return sessionIndex.get().intValue();
    }

    
	public static Session getCurrentSession() {
		return SessionManager.getTheSession(SessionThreadLocal.getSessionType(), 
				SessionThreadLocal.getSessionIndex());
	}
    
//    public static Session getBrowserSession() {
//		if ( SessionThreadLocal.getSessionType() == SessionType.SENIOR )
//			return getCurrentSession();
//		return null;
//    }
//    
//    public static Session getClientSession() {
//		if ( SessionThreadLocal.getSessionType() == SessionType.JUNIOR )
//			return getCurrentSession();
//		return null;
//    }
    
}
