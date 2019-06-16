package com.ruixuesoft.crawler.open;

import com.ruixuesoft.crawler.open.impl.RxCrawlerImpl;

public class RxTask {
	
	protected int appSeq;
	protected int scenarioIndex;
	protected int ruleIndex;
	protected int taskSeq;
	protected int accountIndex;
	protected String userName;
	protected String password;
	
	protected int userSeq;
	protected String scheduleType;
	protected int crawlerSeq;
	protected int sessionIndex;
	protected String crawlerHostIP;
	protected int sourceTaskSeq;
	
	protected String v1;
	protected String v2;
	protected String v3;
	protected String v4;
	protected String v5;
	protected String v6;
	protected String v7;
	protected String v8;
	protected String v9;
	
	protected int ruleVersion;
	
	protected RxCrawlerImpl rxCrawler;

	
	public void createNextRuleTask(RxTask task) throws RxCrawlerException {}
	
	public void createNextRuleTasks(RxTask[] tasks)throws RxCrawlerException{}
	
	public void log(String logMessage) {}
	
	public void logScreen(){}
	
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

	public int getTaskSeq() {
		return taskSeq;
	}

	public void setTaskSeq(int taskSeq) {
		this.taskSeq = taskSeq;
	}
	
	public int getAccountIndex() {
		return accountIndex;
	}

	public void setAccountIndex(int accountIndex) {
		this.accountIndex = accountIndex;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}
	
	public int getSourceTaskSeq() {
		return sourceTaskSeq;
	}

	public void setSourceTaskSeq(int sourceTaskSeq) {
		this.sourceTaskSeq = sourceTaskSeq;
	}

	public int getCrawlerSeq() {
		return crawlerSeq;
	}

	public void setCrawlerSeq(int crawlerSeq) {
		this.crawlerSeq = crawlerSeq;
	}

	public int getSessionIndex() {
		return sessionIndex;
	}

	public void setSessionIndex(int sessionIndex) {
		this.sessionIndex = sessionIndex;
	}

	public String getCrawlerHostIP() {
		return crawlerHostIP;
	}

	public void setCrawlerHostIP(String crawlerHostIP) {
		this.crawlerHostIP = crawlerHostIP;
	}

	public int getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	
	public String getV1() {
		return v1;
	}
	
	public void setV1(String v1) {
		this.v1 = v1;
	}
	
	public String getV2() {
		return v2;
	}
	
	public void setV2(String v2) {
		this.v2 = v2;
	}

	
	public String getV3() {
		return v3;
	}
	
	public void setV3(String v3) {
		this.v3 = v3;
	}
	
	public String getV4() {
		return v4;
	}
	
	public void setV4(String v4) {
		this.v4 = v4;
	}
	
	public String getV5() {
		return v5;
	}
	
	public void setV5(String v5) {
		this.v5 = v5;
	}
	
	public String getV6() {
		return v6;
	}

	public void setV6(String v6) {
		this.v6 = v6;
	}
	
	public String getV7() {
		return v7;
	}
	
	public void setV7(String v7) {
		this.v7 = v7;
	}
	
	public String getV8() {
		return v8;
	}
	
	public void setV8(String v8) {
		this.v8 = v8;
	}
	
	public String getV9() {
		return v9;
	}
	
	public void setV9(String v9) {
		this.v9 = v9;
	}

	public int getRuleVersion() {
		return ruleVersion;
	}

	public void setRuleVersion(int ruleVersion) {
		this.ruleVersion = ruleVersion;
	}
	
	public RxCrawlerImpl getRxCrawler() {
		return rxCrawler;
	}

	public void setRxCrawler(RxCrawlerImpl rxCrawler) {
		this.rxCrawler = rxCrawler;
	}
}
