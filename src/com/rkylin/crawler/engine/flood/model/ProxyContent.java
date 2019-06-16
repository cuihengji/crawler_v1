package com.rkylin.crawler.engine.flood.model;


/**
 * 		@author Administrator
 *
 *		"mode": "fixed_servers",
		"ip": "172.20.6.253",
		"port": "1080"
 */
public class ProxyContent {

	private String mode;
	private String ip;
	private int port;
	private String user_name;
	private String pass_word;
	private String bypassList;
	private String blockedUrlList;
	
	
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
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
	public String getBypassList() {
		return bypassList;
	}
	public void setBypassList(String bypassList) {
		this.bypassList = bypassList;
	}
	public String getBlockedUrlList() {
		return blockedUrlList;
	}
	public void setBlockedUrlList(String blockedUrlList) {
		this.blockedUrlList = blockedUrlList;
	}
	
}
