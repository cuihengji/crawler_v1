package com.rkylin.crawler.engine.flood.common;


public class IpProxyResult {
    private int port;
    private String ip;
    private String user_name;
    private String pass_word;
    
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public String getIp() {
		return ip;
	}
	
	
	public void setIp(String ip) {
		this.ip = ip;
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
}
