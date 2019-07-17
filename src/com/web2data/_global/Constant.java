package com.web2data._global;

public class Constant {
	
	
	// 
	public static final boolean SUCCESS = true;

	// 启动 浏览器 Session 的最大个数
	public static final int MAX_NUMBER_OF_SENIOR_SESSION = 10;
	
	// 启动HttpClient Session的最大个数
	public static final int MAX_NUMBER_OF_JUNIOR_SESSION = 50;
	
	// 启动浏览器 Session的 时间间隔
	public static final int SENIOR_SESSION_STARTUP_INTERVAL_SCECONDS = 5;
	
	// 启动 HttpClient Session的 时间间隔
	public static final int JUNIOR_SESSION_STARTUP_INTERVAL_SCECONDS = 5;
	
	// 没有任务或处理任务失败的Session休息时间
	public static final int TASK_UNAVAILABLE_OR_EXECUTION_ERROR_SLEEP_SECONDS = 3;
	
	// 暂停状态的Session休息时间
	public static final int SESSION_PAUSED_SLEEP_SECONDS = 3;
	
}
