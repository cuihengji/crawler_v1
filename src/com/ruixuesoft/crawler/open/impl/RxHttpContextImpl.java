package com.ruixuesoft.crawler.open.impl;

import java.util.Map;

import com.ruixuesoft.crawler.open.RxHttpContext;

public class RxHttpContextImpl implements RxHttpContext {

	public  Map<String, String> header;
	public  Map<String, String> cookie;
	public  String parameters;
	

	@Override
	public String getParameters() {
		return parameters;
	}

	@Override
	public Map<String, String> getCookies() {
		return cookie;
	}

	@Override
	public Map<String, String> getHeader() {
		return header;
	}

	public Map<String, String> getCookie() {
		return cookie;
	}

	public void setCookie(Map<String, String> cookie) {
		this.cookie = cookie;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	

}
