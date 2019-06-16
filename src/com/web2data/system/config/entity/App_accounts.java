package com.web2data.system.config.entity;

import java.util.List;

public class App_accounts {
	private String app;

	private List<Accounts> accounts;

	public void setApp(String app) {
		this.app = app;
	}

	public String getApp() {
		return this.app;
	}

	public void setAccounts(List<Accounts> accounts) {
		this.accounts = accounts;
	}

	public List<Accounts> getAccounts() {
		return this.accounts;
	}

}
