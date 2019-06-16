package com.ruixuesoft.crawler.open.python.model;

import com.ruixuesoft.crawler.open.impl.RxTaskImpl;
import com.web2data.engine.database.ConnectionInfo;

public class InputJsonForPython {
	
	public String getChromeUrl() {
		return chromeUrl;
	}


	public void setChromeUrl(String chromeUrl) {
		this.chromeUrl = chromeUrl;
	}


	public String getChromeSessionID() {
		return chromeSessionID;
	}


	public void setChromeSessionID(String chromeSessionID) {
		this.chromeSessionID = chromeSessionID;
	}


	public String getAppTaskHost() {
		return appTaskHost;
	}


	public void setAppTaskHost(String appTaskHost) {
		this.appTaskHost = appTaskHost;
	}


	public String getApiPort() {
		return apiPort;
	}


	public void setApiPort(String apiPort) {
		this.apiPort = apiPort;
	}


	public ConnectionInfo getDB() {
		return DB;
	}


	public void setDB(ConnectionInfo dB) {
		DB = dB;
	}


	public RxTaskImpl getTask() {
		return task;
	}


	public void setTask(RxTaskImpl task) {
		this.task = task;
	}


	private	String	chromeUrl;
	
	private String  chromeSessionID;
	
	private String  appTaskHost;

	private String  apiPort = "80";
	
	private ConnectionInfo DB = null;

	
	private RxTaskImpl task = null;
}
