package com.rkylin.crawler.engine.flood.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

public class RuleTaskInputParameter {

    private static final transient Logger logger = Logger.getLogger(RuleTaskInputParameter.class);

	private String env = "TEST";
	private String userSeq = "-1";
	private int appSeq = -1;
	private int scenarioIndex = -1;
	private int ruleIndex = -1;
	private int accountIndex = -1;
	private int sourceTaskSeq = -1;
	private String sourceDataTableName = "-1";
	private String sourceDataSeq = "-1";
	private String scheduledType = "TEST";
	private String v1 = "-1";
	private String v2 = "-1";
	private String v3 = "-1";
	private String v4 = "-1";
	private String v5 = "-1";
	private String v6 = "-1";
	private String v7 = "-1";
	private String v8 = "-1";
	private String v9 = "-1";

	private String[] taskValues;
	private int batchSize = 0;

	
	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(String userSeq) {
		this.userSeq = userSeq;
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

	public int getAccountIndex() {
		return accountIndex;
	}

	public void setAccountIndex(int accountIndex) {
		this.accountIndex = accountIndex;
	}

	public int getSourceTaskSeq() {
		return sourceTaskSeq;
	}

	public void setSourceTaskSeq(int sourceTaskSeq) {
		this.sourceTaskSeq = sourceTaskSeq;
	}

	public String getSourceDataTableName() {
		return sourceDataTableName;
	}

	public void setSourceDataTableName(String sourceDataTableName) {
		this.sourceDataTableName = sourceDataTableName;
	}

	public String getSourceDataSeq() {
		return sourceDataSeq;
	}

	public void setSourceDataSeq(String sourceDataSeq) {
		this.sourceDataSeq = sourceDataSeq;
	}

	
	public String getScheduledType() {
		return scheduledType;
	}

	public void setScheduledType(String scheduledType) {
		this.scheduledType = scheduledType;
	}

	public String getV1() {

		return encodeHttpUrl(this.v1);

	}

	public void setV1(String v1) {
		this.v1 = v1;
	}

	public String getV2() {
		return encodeHttpUrl(this.v2);

	}

	public void setV2(String v2) {
		this.v2 = v2;
	}

	public String getV3() {
		return encodeHttpUrl(this.v3);

	}

	public void setV3(String v3) {
		this.v3 = v3;
	}

	public String getV4() {

		return encodeHttpUrl(this.v4);
	}

	public void setV4(String v4) {
		this.v4 = v4;
	}

	public String getV5() {

		return encodeHttpUrl(this.v5);
	}

	public void setV5(String v5) {
		this.v5 = v5;
	}

	public String getV6() {

		return encodeHttpUrl(this.v6);

	}

	public void setV6(String v6) {
		this.v6 = v6;
	}

	public String getV7() {
		return encodeHttpUrl(this.v7);

	}

	public void setV7(String v7) {
		this.v7 = v7;
	}

	public String getV8() {

		return encodeHttpUrl(this.v8);
	}

	public void setV8(String v8) {
		this.v8 = v8;
	}

	public String getV9() {
		return encodeHttpUrl(this.v9);

	}

	public void setV9(String v9) {
		this.v9 = v9;
	}
	
	 
	public String[] getTaskValues() {
		return taskValues;
	}

	public void setTaskValues(String[] taskValues) {
		this.taskValues = taskValues;
	}

		
	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	private String encodeHttpUrl(String inputString) {
		try {
			return URLEncoder.encode(inputString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("encodeHttpUrl", e);
		}
		return "";
	}
}
