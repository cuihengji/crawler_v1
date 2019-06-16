package com.rkylin.crawler.engine.flood.policy.client;

public class PolicyParameter {
	
	private String hintType;
	private int websiteSeq;
	private String ip;
	private int crawlerHostSeq;
	private int sessionIndex;
	private int appSeq;
	private int scenarioIndex;
	private int ruleIndex;
	private String appTaskTableName;
	private int appTaskSeq;
	
	
	public String getHintType() {
		return hintType;
	}

	public void setHintType(String hintType) {
		this.hintType = hintType;
	}

	public int getWebsiteSeq() {
		return websiteSeq;
	}

	public void setWebsiteSeq(int websiteSeq) {
		this.websiteSeq = websiteSeq;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getCrawlerHostSeq() {
		return crawlerHostSeq;
	}

	public void setCrawlerHostSeq(int crawlerHostSeq) {
		this.crawlerHostSeq = crawlerHostSeq;
	}

	public int getSessionIndex() {
		return sessionIndex;
	}

	public void setSessionIndex(int sessionIndex) {
		this.sessionIndex = sessionIndex;
	}

	public int getAppSeq() {
		return appSeq;
	}

	public void setAppSeq(int appSeq) {
		this.appSeq = appSeq;
	}

	public int getScenarioIndex() {
		return scenarioIndex;
	}

	public void setScenarioIndex(int scenarioIndex) {
		this.scenarioIndex = scenarioIndex;
	}

	public int getRuleIndex() {
		return ruleIndex;
	}

	public void setRuleIndex(int ruleIndex) {
		this.ruleIndex = ruleIndex;
	}

	public String getAppTaskTableName() {
		return appTaskTableName;
	}

	public void setAppTaskTableName(String appTaskTableName) {
		this.appTaskTableName = appTaskTableName;
	}

	public int getAppTaskSeq() {
		return appTaskSeq;
	}

	public void setAppTaskSeq(int appTaskSeq) {
		this.appTaskSeq = appTaskSeq;
	}
	
}