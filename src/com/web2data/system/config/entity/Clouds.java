package com.web2data.system.config.entity;

public class Clouds {
	
	private String env;

	private String seq;

	private String is_used;

	private String name;

	private int window_ttl_mins;

	private String http_proxy;

	private String https_proxy;

	private String create_time;

	private String update_time;

	
	public void setEnv(String env) {
		this.env = env;
	}

	public String getEnv() {
		return this.env;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getSeq() {
		return this.seq;
	}

	public void setIs_used(String is_used) {
		this.is_used = is_used;
	}

	public String getIs_used() {
		return this.is_used;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setWindow_ttl_mins(int window_ttl_mins) {
		this.window_ttl_mins = window_ttl_mins;
	}

	public int getWindow_ttl_mins() {
		return this.window_ttl_mins;
	}

	public void setHttp_proxy(String http_proxy) {
		this.http_proxy = http_proxy;
	}

	public String getHttp_proxy() {
		return this.http_proxy;
	}

	public void setHttps_proxy(String https_proxy) {
		this.https_proxy = https_proxy;
	}

	public String getHttps_proxy() {
		return this.https_proxy;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getCreate_time() {
		return this.create_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getUpdate_time() {
		return this.update_time;
	}

}