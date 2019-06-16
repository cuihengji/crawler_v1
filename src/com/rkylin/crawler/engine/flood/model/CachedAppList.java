package com.rkylin.crawler.engine.flood.model;

import java.util.List;

import com.web2data.system.config.entity.User_apps_scheduler;

public class CachedAppList {

	private List<User_apps_scheduler> appList;
	private long savedTime;
	
	public CachedAppList(List<User_apps_scheduler> appList, long savedTime){
		this.appList = appList;
		this.savedTime = savedTime;
	}

	public List<User_apps_scheduler> getAppList() {
		return appList;
	}

	public void setAppList(List<User_apps_scheduler> appList) {
		this.appList = appList;
	}

	public long getSavedTime() {
		return savedTime;
	}

	public void setSavedTime(long savedTime) {
		this.savedTime = savedTime;
	}
	
}
