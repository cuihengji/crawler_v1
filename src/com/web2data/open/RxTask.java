package com.web2data.open;


public class RxTask {
 
	
	// 可以在任务级别改变这个参数，用于更细粒度的控制, 最长是3个小时 
	private int executionTimeoutSeconds = 3 * 60 * 60;

	
	public int env = -1;
	public static int _TEST = 0;
	public static int _PROD = 1;
	
	
	private int appId = -1;
	private int recipeIndex = -1;
	private int stepIndex = -1;
	
	private int finished_code = -1;
	
	private int jar_version = -1;
	
	private String x1 = null;
	private String x2 = null;
	private String x3 = null;
	private String x4 = null;
	private String x5 = null;
	private String x6 = null;
	private String x7 = null;
	private String x8 = null;
	private String x9 = null;

	public RxTask() {
		//
	}
	
	public RxTask( String x1, String x2, String x3 ) {
		this.x1 = x1;
		this.x2 = x2;
		this.x3 = x3;
	}
	
	public RxTask( String x1, String x2, String x3, String x4, String x5, String x6, String x7, String x8, String x9 ) {
		this.x1 = x1;
		this.x2 = x2;
		this.x3 = x3;
		this.x4 = x4;
		this.x5 = x5;
		this.x6 = x6;
		this.x7 = x7;
		this.x8 = x8;
		this.x9 = x9;
	}
	
	public String getX1() {
		return x1;
	}


	public String getX2() {
		return x2;
	}


	public String getX3() {
		return x3;
	}


	public String getX4() {
		return x4;
	}


	public String getX5() {
		return x5;
	}


	public String getX6() {
		return x6;
	}


	public String getX7() {
		return x7;
	}


	public String getX8() {
		return x8;
	}


	public String getX9() {
		return x9;
	}


//	public int getFinishedCode() {
//		return finishedCode;
//	}
//
//	public void setFinishedCode(int finishedCode) {
//		this.finishedCode = finishedCode;
//	}

//	public int getTimeoutSeconds() {
//		return timeoutSeconds;
//	}
//
//	public void setTimeoutSeconds(int timeoutSeconds) {
//		this.timeoutSeconds = timeoutSeconds;
//	}
	
	
	
	// 写日志，这些日志与Task是相绑定的，
	public void log(String message) {
		// 发布给开发者的 RxTask类，把该方法留空， 实际调用的RxTask类有该方法的实现
		// RxLog.getInstance().log(message);
	}

	public int getExecutionTimeoutSeconds() {
		return executionTimeoutSeconds;
	}

	public RxTask setTimeoutSeconds(int executionTimeoutSeconds) {
		this.executionTimeoutSeconds = executionTimeoutSeconds;
		return this;
	}

	public int getEnv() {
		return env;
	}

	public void setEnv(int env) {
		this.env = env;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public int getRecipeIndex() {
		return recipeIndex;
	}

	public void setRecipeIndex(int recipeIndex) {
		this.recipeIndex = recipeIndex;
	}

	public int getStepIndex() {
		return stepIndex;
	}

	public void setStepIndex(int stepIndex) {
		this.stepIndex = stepIndex;
	}

	public void setExecutionTimeoutSeconds(int executionTimeoutSeconds) {
		this.executionTimeoutSeconds = executionTimeoutSeconds;
	}

	public void setX1(String x1) {
		this.x1 = x1;
	}

	public void setX2(String x2) {
		this.x2 = x2;
	}

	public void setX3(String x3) {
		this.x3 = x3;
	}

	public void setX4(String x4) {
		this.x4 = x4;
	}

	public void setX5(String x5) {
		this.x5 = x5;
	}

	public void setX6(String x6) {
		this.x6 = x6;
	}

	public void setX7(String x7) {
		this.x7 = x7;
	}

	public int getJar_version() {
		return jar_version;
	}

	public void setJar_version(int jar_version) {
		this.jar_version = jar_version;
	}

	public int getFinished_code() {
		return finished_code;
	}

	public void setFinished_code(int finished_code) {
		this.finished_code = finished_code;
	}
	
	
	
}

