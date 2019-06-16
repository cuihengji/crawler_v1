package com.web2data.system.config.entity;

import com.rkylin.crawler.engine.flood.model.IsUsedInterface;

public class host_of_app_data implements IsUsedInterface{
	private int seq;
	private String is_used;
	private String ip1;
	private String ip2;
	private String ip_local;
	private int mysql_port;
	private String  mysql_schema;
	private String mysql_username;
	private String mysql_password;
	private String comment;

	public host_of_app_data() {
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
