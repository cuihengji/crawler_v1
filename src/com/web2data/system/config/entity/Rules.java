package com.web2data.system.config.entity;

public class Rules {
	
	private String env;

	private int app_seq;

	private int scenario_index;

	private int rule_index;

	private String rule_name;

	private String inherit_cookie;

	private String use_proxy;

	private String test_network;

	private String block_url_list;

	private String bypass_domain_list;

	private String queuing_task_limit;

	private String class_full_name;

	private String class_file_url;

	private String url_template;

	private String comment;
	
	private int version;

	public void setEnv(String env) {
		this.env = env;
	}

	public String getEnv() {
		return this.env;
	}

	public void setApp_seq(int app_seq) {
		this.app_seq = app_seq;
	}

	public int getApp_seq() {
		return this.app_seq;
	}

	public void setScenario_index(int scenario_index) {
		this.scenario_index = scenario_index;
	}

	public int getScenario_index() {
		return this.scenario_index;
	}

	public void setRule_index(int rule_index) {
		this.rule_index = rule_index;
	}

	public int getRule_index() {
		return this.rule_index;
	}

	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}

	public String getRule_name() {
		return this.rule_name;
	}

	public void setInherit_cookie(String inherit_cookie) {
		this.inherit_cookie = inherit_cookie;
	}

	public String getInherit_cookie() {
		return this.inherit_cookie;
	}

	public void setUse_proxy(String use_proxy) {
		this.use_proxy = use_proxy;
	}

	public String getUse_proxy() {
		return this.use_proxy;
	}

	public void setTest_network(String test_network) {
		this.test_network = test_network;
	}

	public String getTest_network() {
		return this.test_network;
	}

	public void setBlock_url_list(String block_url_list) {
		this.block_url_list = block_url_list;
	}

	public String getBlock_url_list() {
		return this.block_url_list;
	}

	public void setBypass_domain_list(String bypass_domain_list) {
		this.bypass_domain_list = bypass_domain_list;
	}

	public String getBypass_domain_list() {
		return this.bypass_domain_list;
	}

	public void setQueuing_task_limit(String queuing_task_limit) {
		this.queuing_task_limit = queuing_task_limit;
	}

	public String getQueuing_task_limit() {
		return this.queuing_task_limit;
	}

	public void setClass_full_name(String class_full_name) {
		this.class_full_name = class_full_name;
	}

	public String getClass_full_name() {
		return this.class_full_name;
	}

	public void setClass_file_url(String class_file_url) {
		this.class_file_url = class_file_url;
	}

	public String getClass_file_url() {
		return this.class_file_url;
	}

	public void setUrl_template(String url_template) {
		this.url_template = url_template;
	}

	public String getUrl_template() {
		return this.url_template;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return this.comment;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
