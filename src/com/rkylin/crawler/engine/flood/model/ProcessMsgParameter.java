package com.rkylin.crawler.engine.flood.model;

import com.ruixuesoft.crawler.open.RxRule;
import com.web2data.engine.database.ConnectionInfo;
import com.web2data.system.config.entity.Apps;
import com.web2data.system.config.entity.app_task_info;

public class ProcessMsgParameter {

	private Apps app;
	private RxRule rxRule;
	private app_task_info taskInfo;
	
	ConnectionInfo conInfo;
	
	public Apps getApp() {
		return app;
	}
	
	public void setApp(Apps app) {
		this.app = app;
	}

	public RxRule getRxRule() {
		return rxRule;
	}
	
	public void setRxRule(RxRule rxRule) {
		this.rxRule = rxRule;
	}
	
	public app_task_info getTaskInfo() {
		return taskInfo;
	}
	
	public void setTaskInfo(app_task_info taskInfo) {
		this.taskInfo = taskInfo;
	}
	
	public ConnectionInfo getConInfo() {
		return conInfo;
	}
	
	public void setConInfo(ConnectionInfo conInfo) {
		this.conInfo = conInfo;
	}
	
}
