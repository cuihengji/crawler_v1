package com.web2data.engine.core;

import java.util.ArrayList;
import java.util.List;

import com.web2data._global.Constant;
import com.web2data._global.SessionType;
import com.web2data.system.infra.api.API005GetInfraInfo;
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
    	int seniorSessions = API005GetInfraInfo.getNumberOfSession(SessionType.SENIOR);
    	
    	// 防止启动过多 Browser
    	if (seniorSessions > Constant.MAX_NUMBER_OF_SENIOR_SESSION) {
    		seniorSessions = Constant.MAX_NUMBER_OF_SENIOR_SESSION;
    	}
    	
    	
    	boolean result = false;
    	
    	// 启动多个SeniorSession
    	for ( int index = 0; index < seniorSessions; index++ ) {
    		
    		
    		System.out.println( "SessionType.SENIOR, "+ index + " ---- 启动..." );
    		
    		// 新生成 一个 SeniorSession
    		Session seniorSession = new Session( SessionType.SENIOR, index );
    		
    		// 初始化
    		seniorSession._status = Session.INTIALIZING1;
    		result = seniorSession.INTIALIZE1(); if (result) { seniorSession._status = Session.INTIALIZING2; } else { return; }
    		result = seniorSession.INTIALIZE2(); if (result) { seniorSession._status = Session.RUNNING; } else { return; }
    		
    		// 启动，循环处理任务
    		seniorSession.start();  
    		
    		System.out.println( "SessionType.SENIOR, "+ index + " ---- 启动完成" );
    		
    		// 加入到 SessionManager._seniorSessionList 进行管理
    		_seniorSessionList.add(index, seniorSession);
    		
    		// 加入间隔以使当前Session启动完全，防止启动过多同时争夺CPU造成 freeze
    		U.sleepSeconds( Constant.SENIOR_SESSION_STARTUP_INTERVAL_SCECONDS );
    	}
    	
    	
    	// ----------------------- 启动 JuniorSession --------------------------------
    	
    	int juniorSessions = API005GetInfraInfo.getNumberOfSession(SessionType.JUNIOR);
    	
    	if (juniorSessions > Constant.MAX_NUMBER_OF_JUNIOR_SESSION) {
    		juniorSessions = Constant.MAX_NUMBER_OF_JUNIOR_SESSION;
    	}
    	
    	// 占位
    	//_juniorSessionList.add(0, null);
    	
    	for ( int index = 0; index < juniorSessions; index++ ) {
    		
    		System.out.println( "SessionType.JUNIOR, "+ index + " ---- 启动..." );
    		
    		Session juniorSession = new Session( SessionType.JUNIOR, index );

    		// 初始化
    		juniorSession._status = Session.INTIALIZING1;
    		result = juniorSession.INTIALIZE1(); if (result) { juniorSession._status = Session.INTIALIZING2; } else { return; }
    		result = juniorSession.INTIALIZE2(); if (result) { juniorSession._status = Session.RUNNING; } else { return; }
    		
    		// 启动，循环处理任务
    		juniorSession.start();
    		
    		System.out.println( "SessionType.JUNIOR, "+ index + " ---- 启动完成" );
    		
    		// 加入到 SessionManager._juniorSessionList 进行管理
    		_juniorSessionList.add(index, juniorSession);
    		
    		// 加入间隔以使当前Session启动完全，防止启动过多同时争夺CPU造成 freeze
    		U.sleepSeconds( Constant.JUNIOR_SESSION_STARTUP_INTERVAL_SCECONDS );
    	}

        return;
    }
    
    
    // 在主线程中，判断Session的存活和状态变化
	// 检查当前的 SessionIndex 是不是取消了，比如： 原来这台爬虫机是6个线程，现在设置为3个线程
	// 如果当前是 4,5,6, 则停止该线程
	// mySession._stop();
    
	public static void main(String[] args) {

		SessionManager.start();
		
		//System.out.println("temp1 = " + temp1.getName());
		
		//int hour = getPolicyIntervalOfTheStepInCurrentHour("900-1-1");
		//System.out.println("hour = " + hour);
	}
}
