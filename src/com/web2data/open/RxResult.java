package com.web2data.open;

public class RxResult {

	
	//private int yFinishedCode = -1;
	private int finishedCode = -1;
	
	private String y1 = null;
	private String y2 = null;
	private String y3 = null;
	
	public RxResult() {}
	
	public int getFinishedCode() {
		return finishedCode;
	}

	public void setFinishedCode(int finishedCode) {
		this.finishedCode = finishedCode;
	}
	
	// 写日志，这些日志与Task是相绑定的，
	public void log(String message) {
		// 发布给开发者的 RxTask类，把该方法留空， 实际调用的RxTask类有该方法的实现
		// RxLog.getInstance().log(message);
	}

	public String getY1() {
		return y1;
	}

	public void setY1(String y1) {
		this.y1 = y1;
	}

	public String getY2() {
		return y2;
	}

	public void setY2(String y2) {
		this.y2 = y2;
	}

	public String getY3() {
		return y3;
	}

	public void setY3(String y3) {
		this.y3 = y3;
	}
	
	
}
