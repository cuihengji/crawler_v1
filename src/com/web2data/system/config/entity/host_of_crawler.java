package com.web2data.system.config.entity;

import com.rkylin.crawler.engine.flood.model.IsUsedInterface;

public class host_of_crawler implements IsUsedInterface{
	private int seq;
	private String is_used;
	private int max_sessions;
	private int weight_of_api_task;
	private int weight_of_slot_task;
	private int weight_of_rulex;
	private String localtion;
	private String ip1;
	private String ip2;
	private String ip_local;
	private String user_name;
	private String pass_word;
	private String create_time;
	
	private int company_seq;
	private int site_seq;
	
	private String log_switch;
	
	private int browser_sessions;
	private int client_sessions;
	private int developer_threads;
	
	public int getCompany_seq() {
		return company_seq;
	}

	public void setCompany_seq(int company_seq) {
		this.company_seq = company_seq;
	}

	public int getSite_seq() {
		return site_seq;
	}

	public void setSite_seq(int site_seq) {
		this.site_seq = site_seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;
	
	public host_of_crawler() {
		// TODO Auto-generated constructor stub
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getIs_used() {
		return is_used;
	}

	public void setIs_used(String is_used) {
		this.is_used = is_used;
	}

	public int getMax_sessions() {
		return max_sessions;
	}

	public void setMax_sessions(int max_sessions) {
		this.max_sessions = max_sessions;
	}

	public int getWeight_of_api_task() {
		return weight_of_api_task;
	}

	public void setWeight_of_api_task(int weight_of_api_task) {
		this.weight_of_api_task = weight_of_api_task;
	}

	public int getWeight_of_slot_task() {
		return weight_of_slot_task;
	}

	public void setWeight_of_slot_task(int weight_of_slot_task) {
		this.weight_of_slot_task = weight_of_slot_task;
	}

	public int getWeight_of_rulex() {
		return weight_of_rulex;
	}

	public void setWeight_of_rulex(int weight_of_rulex) {
		this.weight_of_rulex = weight_of_rulex;
	}

	public String getLocaltion() {
		return localtion;
	}

	public void setLocaltion(String localtion) {
		this.localtion = localtion;
	}

	public String getIp1() {
		return ip1;
	}

	public void setIp1(String ip1) {
		this.ip1 = ip1;
	}

	public String getIp2() {
		return ip2;
	}

	public void setIp2(String ip2) {
		this.ip2 = ip2;
	}

	public String getIp_local() {
		return ip_local;
	}

	public void setIp_local(String ip_local) {
		this.ip_local = ip_local;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPass_word() {
		return pass_word;
	}

	public void setPass_word(String pass_word) {
		this.pass_word = pass_word;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getLog_switch() {
		return log_switch;
	}

	public void setLog_switch(String log_switch) {
		this.log_switch = log_switch;
	}
	
	public int getBrowser_sessions() {
		return browser_sessions;
	}

	public void setBrowser_sessions(int browser_sessions) {
		this.browser_sessions = browser_sessions;
	}

	public int getClient_sessions() {
		return client_sessions;
	}

	public void setClient_sessions(int client_sessions) {
		this.client_sessions = client_sessions;
	}

	public int getDeveloper_threads() {
		return developer_threads;
	}

	public void setDeveloper_threads(int developer_threads) {
		this.developer_threads = developer_threads;
	}
}
