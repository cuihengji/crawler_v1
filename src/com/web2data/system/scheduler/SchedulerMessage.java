package com.web2data.system.scheduler;

public class SchedulerMessage {

	String message = null;

	
	
	public String getMessage() {
		return message;
	}

	
	// 从Scheduler获得一条消息，最好把字符串中的字段分解开，保存到类属性中
	public void setMessage(String message) {
		this.message = message;
	}
	
}
