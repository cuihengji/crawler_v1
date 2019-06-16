package com.web2data.system.config.entity;

public class app_data_host {
	private int seq;
	private String name;
	private String ip_from_app_task_host;
	private String ip_from_crawler_host;
	private int mysql_port;
	private String mysql_schema;
	private String mysql_username;
	private String mysql_password;
	
	private String database_type;
	private String oracle_service_value;
	private String oracle_service_type;
	
	
	public app_data_host() {
        // TODO Auto-generated constructor stub
    }
	
	public int getSeq() {
		return seq;
	}
	
	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getIp_from_app_task_host() {
		return ip_from_app_task_host;
	}
	
	public void setIp_from_app_task_host(String ip_from_app_task_host) {
		this.ip_from_app_task_host = ip_from_app_task_host;
	}
	
	public String getIp_from_crawler_host() {
		return ip_from_crawler_host;
	}
	
	public void setIp_from_crawler_host(String ip_from_crawler_host) {
		this.ip_from_crawler_host = ip_from_crawler_host;
	}
	
	public int getMysql_port() {
		return mysql_port;
	}
	
	public void setMysql_port(int mysql_port) {
		this.mysql_port = mysql_port;
	}
	
	public String getMysql_schema() {
		return mysql_schema;
	}
	
	public void setMysql_schema(String mysql_schema) {
		this.mysql_schema = mysql_schema;
	}
	
	public String getMysql_username() {
		return mysql_username;
	}
	
	public void setMysql_username(String mysql_username) {
		this.mysql_username = mysql_username;
	}
	
	public String getMysql_password() {
		return mysql_password;
	}
	
	public void setMysql_password(String mysql_password) {
		this.mysql_password = mysql_password;
	}

	public String getDatabase_type() {
		return database_type;
	}

	public void setDatabase_type(String database_type) {
		this.database_type = database_type;
	}

	public String getOracle_service_value() {
		return oracle_service_value;
	}

	public void setOracle_service_value(String oracle_service_value) {
		this.oracle_service_value = oracle_service_value;
	}

	public String getOracle_service_type() {
		return oracle_service_type;
	}

	public void setOracle_service_type(String oracle_service_type) {
		this.oracle_service_type = oracle_service_type;
	}

	
}
