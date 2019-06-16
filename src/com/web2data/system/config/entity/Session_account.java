package com.web2data.system.config.entity;

public class Session_account {

	private String app;
	private int session;
	private int account_index;

	public void setApp(String app) {
		this.app = app;
	}

	public String getApp() {
		return this.app;
	}

	public void setSession(int session) {
		this.session = session;
	}

	public int getSession() {
		return this.session;
	}

	public void setAccount_index(int account_index) {
		this.account_index = account_index;
	}

	public int getAccount_index() {
		return this.account_index;
	}
}
