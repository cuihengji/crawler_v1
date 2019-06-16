package com.web2data.system.config.entity;

public class Session_apps {
	
	private int session;

	private String[] apps;

	public void setSession(int session) {
		this.session = session;
	}

	public int getSession() {
		return this.session;
	}

	public String[] getApps() {
		return apps;
	}

	public void setApps(String[] apps) {
		this.apps = apps;
	}

}
