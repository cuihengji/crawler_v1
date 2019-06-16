package com.web2data.system.config.entity;

public class User_apps {

	private int user;
	private AppsPerUser[] appsPerUser;
	
	public int getUser() {
		return user;
	}
	public void setUser(int user) {
		this.user = user;
	}
	
	public AppsPerUser[] getAppsPerUser() {
		return appsPerUser;
	}
	public void setAppsPerUser(AppsPerUser[] appsPerUser) {
		this.appsPerUser = appsPerUser;
	}
	
}
