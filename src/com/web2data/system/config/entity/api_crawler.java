package com.web2data.system.config.entity;

public class api_crawler {
	private int seq;
	private String api_key = "-1";
	private int port = -1;
	private String uri = "-1";
	private String coment;
	
	public api_crawler() {
		// TODO Auto-generated constructor stub
	}
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getApi_key() {
		return api_key;
	}
	public void setQpi_key(String api_key) {
		this.api_key = api_key;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getComent() {
		return coment;
	}
	public void setComent(String coment) {
		this.coment = coment;
	}
}
