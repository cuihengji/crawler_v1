package com.web2data.engine.crawler.browser.impl.a;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.logging.LogEntry;

import com.ruixuesoft.crawler.open.RxHttpResponse;

/**
 * 即从底层的Log分析Response的信息, 同时也作为HTTP调用的Response类使用, 设计是这样的...
 *
 */
public class RxHttpResponseImpl implements RxHttpResponse {

	public static final Logger logger = Logger.getLogger(RxHttpResponseImpl.class);

	private JSONObject responseJson;
	private JSONObject responseMessage;
	private JSONObject responseParams;
	private JSONObject response;
	
	private String responseBody;
	
	public RxHttpResponseImpl(LogEntry responseLog) {
		super();

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
	
	public RxHttpResponseImpl(String responseBody) {
		super();
		this.responseBody = responseBody;
	}

	@Override
	public String getRemoteAddress() {
		
		String remoteAddress = "NA";
		try {
			remoteAddress = this.response.getString("remoteIPAddress");
		} catch (Exception e) {
			logger.error("getRemoteAddress:" + e);
		}
		return remoteAddress;
	}

	
	@Override
	public String getPort() {
		String remotePort = "NA";
		try {
			remotePort = this.response.getString("remotePort");
		} catch (Exception e) {
			logger.error("getPort:" + e);
		}
		return remotePort;
	}

	
	@Override
	public JSONObject getAllHeaders() {
		JSONObject headers = null;
		try {
			headers = response.getJSONObject("headers");
		} catch (Exception e) {
			logger.error("getAllHeaders:" + e);
		}
		return headers;
	}

	@Override
	public String getHeaderByName(String name) {
		
		String headerValue = "NA";
		try {
			JSONObject headers = response.getJSONObject("headers");
			headerValue = headers.getString(name);
		} catch (Exception e) {
			logger.error("getHeaderByName:" + e);
		}
		return headerValue;
	}

	
	@Override
	public void setHeader(String name, String value) {
		try {
			JSONObject headers = response.getJSONObject("headers");
			headers.put(name, value);
		} catch (Exception e) {
			logger.error("getHeaderByName:" + e);
		}
	}

	
	@Override
	public void deleteHeader(String name) {
		try {
			JSONObject headers = response.getJSONObject("headers");
			headers.remove(name);
		} catch (Exception e) {
			logger.error("deleteHeader:" + e);
		}
	}

	
	@Override
	public int getStatusCode() {
		int statusCode = 0;
		try {
			statusCode = response.getInt("status");
		} catch (Exception e) {
			logger.error("getHeaderByName:" + e);
		}
		return statusCode;		
	}

	
	@Override
	public String body() {
		return this.responseBody;
	}

}
