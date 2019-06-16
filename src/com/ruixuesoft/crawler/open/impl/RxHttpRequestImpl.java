package com.ruixuesoft.crawler.open.impl;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.logging.LogEntry;

import com.ruixuesoft.crawler.open.RxHttpRequest;

public class RxHttpRequestImpl implements RxHttpRequest {

	public static final Logger logger = Logger.getLogger(RxHttpRequestImpl.class);

	private JSONObject requestJson;
	private JSONObject requestMessage;
	private JSONObject requestParams;
	private JSONObject request;
	
	private JSONObject responseJson;
	private JSONObject responseMessage;
	private JSONObject responseParams;
	private JSONObject response;
	
	public RxHttpRequestImpl(LogEntry requestLog, LogEntry responseLog) {
		super();
		
		if (requestLog != null) {
			try {
				this.requestJson = new JSONObject(requestLog.getMessage());
				this.requestMessage = requestJson.getJSONObject("message");
				this.requestParams = requestMessage.getJSONObject("params");
				this.request = requestParams.getJSONObject("request");
			} catch (JSONException e) {
				logger.error("Request Json初始化错误:" + e);
			}
		}
		
		if (responseLog != null) {
			try {
				this.responseJson = new JSONObject(responseLog.getMessage());
				this.responseMessage = responseJson.getJSONObject("message");
				this.responseParams = responseMessage.getJSONObject("params");
				this.response = responseParams.getJSONObject("response");
			} catch (JSONException e) {
				logger.error("Response Json初始化错误:" + e);
			}
		}

	}

	
	@Override
	public String getURL() {
		String url = "NA";
		try {
			url = this.request.getString("url");
		} catch (Exception e) {
			logger.error("getURL:" + e);
		}
		return url;
	}

	
	@Override
	public String getMethod() {
		String method = "NA";
		try {
			method = this.request.getString("method");
		} catch (Exception e) {
			logger.error("getMethod:" + e);
		}
		return method;
	}

	
	@Override
	public String getReferrerPolicy() {
		String referer = "NA";
		try {
			referer = request.getString("referrerPolicy");
		} catch (Exception e) {
			logger.error("getReferrerPolicy:" + e);
		}
		return referer;
	}

	
	@Override
	public JSONObject getAllHeaders() {
		JSONObject headers = null;
		try {
			headers = request.getJSONObject("headers");
		} catch (Exception e) {
			logger.error("getAllHeaders:" + e);
		}
		return headers;
	}

	
	@Override
	public String getHeaderByName(String name) {
		String headerName = "NA";
		try {
			headerName = request.getString(name);
		} catch (Exception e) {
			logger.error("getHeaderByName:" + e);
		}
		return headerName;
	}

	
	@Override
	public void setHeader(String name, String value) {

		JSONObject headers = null;
		try {
			headers = request.getJSONObject("headers");
			headers.put(name, value);
		} catch (Exception e) {
			logger.error("setHeader:" + e);
		}
	}

	
	@Override
	public void deleteHeader(String name) {
		JSONObject headers = null;
		try {
			headers = request.getJSONObject("headers");
			headers.remove(name);
		} catch (Exception e) {
			logger.error("deleteHeader:" + e);
		}
	}

	
	@Override
	public JSONObject getAllParameters() {
		return requestParams;
	}

	
	@Override
	public String getParameterByName(String name) {
		String parameter = "NA";
		try {
			parameter = requestParams.getString(name);
		} catch (Exception e) {
			logger.error("getParameterByName:" + e);
		}
		return parameter;
	}

	
	@Override
	public void setParameter(String name, String value) {
		try {
			requestParams.put(name, value);
		} catch (Exception e) {
			logger.error("setParameter:" + e);
		}
	}

	
	@Override
	public void deleteParameter(String name) {
		try {
			requestParams.remove(name);
		} catch (Exception e) {
			logger.error("deleteParameter:" + e);
		}
	}

	
	@Override
	public JSONObject getAllCookies() {
		JSONObject cookies = new JSONObject();
		try {
			JSONObject requestHeaders = response.getJSONObject("requestHeaders");
			cookies = requestHeaders.getJSONObject("Cookie");
		} catch (Exception e) {
			logger.error("getAllCookies:" + e);
		}
		return cookies;
	}

	
	@Override
	public String getCookieByName(String name) {
		String cookie = "NA";
		try {
			JSONObject requestHeaders = response.getJSONObject("requestHeaders");
			JSONObject cookies = requestHeaders.getJSONObject("Cookie");
			cookie = cookies.getString(name);
		} catch (Exception e) {
			logger.error("getCookieByName:" + e);
		}
		return cookie;
	}

	
	@Override
	public void setCookie(String name, String value) {
		try {
			JSONObject requestHeaders = response.getJSONObject("requestHeaders");
			JSONObject cookies = requestHeaders.getJSONObject("Cookie");
			cookies.put(name, value);
		} catch (Exception e) {
			logger.error("setCookie:" + e);
		}
	}

	
	@Override
	public void deleteCookie(String name) {
		try {
			JSONObject requestHeaders = response.getJSONObject("requestHeaders");
			JSONObject cookies = requestHeaders.getJSONObject("Cookie");
			cookies.remove(name);
		} catch (Exception e) {
			logger.error("setCookie:" + e);
		}
	}
	
}
