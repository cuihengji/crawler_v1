package com.web2data.system.config.entity;

public class Scenarios {
	
	private String env;

	private String app_seq;

	private String scenario_index;

	private String name;

	private String create_time;

	private String comment;

	public void setEnv(String env) {
		this.env = env;
	}

	public String getEnv() {
		return this.env;
	}

	public void setApp_seq(String app_seq) {
		this.app_seq = app_seq;
	}

	public String getApp_seq() {
		return this.app_seq;
	}

	public void setScenario_index(String scenario_index) {
		this.scenario_index = scenario_index;
	}

	public String getScenario_index() {
		return this.scenario_index;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getCreate_time() {
		return this.create_time;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return this.comment;
	}

}