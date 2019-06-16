/**
 * 
 */
package com.ruixuesoft.crawler.open.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import com.ruixuesoft.crawler.open.RxHttpNetwork;
import com.ruixuesoft.crawler.open.RxHttpRequest;
import com.ruixuesoft.crawler.open.RxHttpResponse;

/**
 * @author Administrator
 *
 */
public class RxHttpNetworkImpl implements RxHttpNetwork {

	private LogEntry requestLog;
	private LogEntry responseLog;
	
	public RxHttpNetworkImpl(LogEntry request, LogEntry response) {
		super();
		this.requestLog = request;
		this.responseLog = response;
	}


	@Override
	public RxHttpRequest getRxHttpRequest() {
		return new RxHttpRequestImpl(this.requestLog, this.responseLog);
	}

	@Override
	public RxHttpResponse getRxHttpResponse() {
		return new RxHttpResponseImpl(responseLog);
	}

}
