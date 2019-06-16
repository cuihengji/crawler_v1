package com.web2data.engine.core;

import java.util.ArrayList;
import java.util.List;

import com.web2data._global.Constant;
import com.web2data._global.SessionThreadLocal;
import com.web2data._global.SessionType;
import com.web2data.system.config.api.CONFIG;
import com.web2data.utility.U;


// com.rkylin.crawler.engine.flood.crawler.CrawlerManager.java

public class SessionManager {

	// -------------------------------------------------------
	
	private static List<Session> _seniorSessionList = new ArrayList<Session>();
	private static List<Session> _juniorSessionList = new ArrayList<Session>();
	
    
    // -------------------------------------------------------
	public static Session getTheSession( int sessionType, int sessionIndex ) {
		if ( sessionType == SessionType.SENIOR )
			return _seniorSessionList.get( sessionIndex );
		return _juniorSessionList.get( sessionIndex );
	}
	
    public static void start() {
    	
    	// ----------------------- 启动 SeniorSession --------------------------------
    	
    	// 调用 CONFIG 系统的API，也可能已经缓存了
    	int seniorSessions = CONFIG.getNumberOfSeniorSession();
    	
    	// 防止启动过多 Browser
    	if (seniorSessions > Constant.MAX_NUMBER_OF_SENIOR_SESSION) {
    		seniorSessions = Constant.MAX_NUMBER_OF_SENIOR_SESSION;
    	}
    	
    	// 占位
    	_seniorSessionList.add(0, null);
    	
    	// 启动多个BrowserSession
    	for ( int index = 1; index <= seniorSessions; index++ ) {
    		
    		// 新生成 一个 SeniorSession
    		Session seniorSession = new Session( SessionType.SENIOR, index );
    		
    		// 启动
    		seniorSession.start();
    		
    		// 加入到 SessionManager._seniorSessionList 进行管理
    		_seniorSessionList.add(index, seniorSession);
    		
    		// 加入间隔以使当前Session启动完全，防止启动过多同时争夺CPU造成 freeze
    		U.sleepSeconds( Constant.SENIOR_SESSION_STARTUP_INTERVAL_SCECONDS );
    		
    		// 测试专用
    		// U.sleepSeconds( 5 );
    	}
    	
    	
    	// ----------------------- 启动 JuniorSession --------------------------------
    	
    	int juniorSessions = CONFIG.getNumberOfJuniorSession();
    	
    	if (juniorSessions > Constant.MAX_NUMBER_OF_JUNIOR_SESSION) {
    		juniorSessions = Constant.MAX_NUMBER_OF_JUNIOR_SESSION;
    	}
    	
    	// 占位
    	_juniorSessionList.add(0, null);
    	
    	for ( int index = 1; index <= juniorSessions; index++ ) {
    		Session juniorSession = new Session( SessionType.JUNIOR, index );
    		juniorSession.start();
    		_juniorSessionList.add(index, juniorSession);
    		U.sleepSeconds( Constant.JUNIOR_SESSION_STARTUP_INTERVAL );
    		
    		// 测试专用
    		// U.sleepSeconds( 5 );
    	}

        return;
    }
    
    
    // 在主线程中，判断Session的存活和状态变化
	// 检查当前的 SessionIndex 是不是取消了，比如： 原来这台爬虫机是6个线程，现在设置为3个线程
	// 如果当前是 4,5,6, 则停止该线程
	// mySession._stop();
    
}
