package com.ruixuesoft.crawler.open;

import java.util.Map;

import com.ruixuesoft.crawler.open.impl.RxHttpUtilImpl;

public abstract class RxHttpUtil {

	public static RxHttpUtil getInstance() {
		return new RxHttpUtilImpl();
	}

	/**
	 * http get
	 * 
	 * @param url
	 * @return response
	 * @exception RxCrawlerException
	 */
	public abstract String httpGet(String url) throws RxCrawlerException;
	
	
	/**
	 * http get
	 * 
	 * @param url
	 * @param cookie
	 * @param isProxy 是否使用代理
	 * @return response
	 * @exception RxCrawlerException
	 */
	public abstract String httpGet(String url, Map<String, String> cookie, boolean isProxy) throws RxCrawlerException;
	

	/**
	 * http post
	 * 
	 * @param url
	 * @param params
	 * @return response
	 * @exception RxCrawlerException
	 */
	public abstract String httpPost(String url,  Map<String, String> params) throws RxCrawlerException;
	
	
	/**
	 * http post
	 * 
	 * @param url
	 * @param header
	 * @param params
	 * @return response
	 * @exception RxCrawlerException
	 */
	public abstract String httpPost(String url, Map<String, String> header,  Map<String, String> params) throws RxCrawlerException;
	
	/**
	 * generateFeiZhuCookie
	 * 
	 * @return response
	 * @exception RxCrawlerException
	 */
	public abstract Map<String, String> generateFeiZhuCookie() throws RxCrawlerException;
	
}
