package com.web2data.system.config.entity;

public class log_session_master {
	private int crawler_host_seq = -1;
	private int session_index = -1;
	private String table_name = "-1";
	private int last_transfered_seq = -1;
	private String create_time = "-1";
	private String update_time = "-1";
	
	
	public int getCrawler_host_seq() {
		return crawler_host_seq;
	}


	public void setCrawler_host_seq(int crawler_host_seq) {
		this.crawler_host_seq = crawler_host_seq;
	}


	public int getSession_index() {
		return session_index;
	}


	public void setSession_index(int session_index) {
		this.session_index = session_index;
	}


	public String getTable_name() {
		return table_name;
	}


	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}


	public int getLast_transfered_seq() {
		return last_transfered_seq;
	}


	public void setLast_transfered_seq(int last_transfered_seq) {
		this.last_transfered_seq = last_transfered_seq;
	}


	public String getCreate_time() {
		return create_time;
	}


	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}


	public String getUpdate_time() {
		return update_time;
	}


	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	
	public log_session_master() {
		// TODO Auto-generated constructor stub
	}
}
