package com.web2data.open;

import com.ruixuesoft.crawler.open.RxCrawlerException;

public interface RxBrowser {

    /**
     * 根据指定url,打开一个网页，默认180秒超时，超时抛出 RxCrawlerException
     * @param url
     * @return
     * @exception RxCrawlerException
     */
	public void open(String url) throws RxCrawlerException;
	
	
    /**
     * 根据指定url,打开一个网页
     * @param url
     * @param waitSeconds 指定时间内打不开网页报 timeout exception
     * @return
     * @exception RxCrawlerException
     */
	public void open(String url, int waitSeconds) throws RxCrawlerException;
	
   
	/**
     * 根据指定url,打开一个网页，并检测标题中是否含有指定关键字，以判断网页是否正常加载。不检测时传-1
     * @param url
     * @param targetKeyword 标题中的关键字或者指定的XPath
     * @return
     * @exception RxCrawlerException
     */
	public void open(String url, String targetKeyword) throws RxCrawlerException;
	
	
	/**
	 * 根据指定url,打开一个网页，并检测标题中是否含有指定关键字，以判断网页是否正常加载。不检测时传-1
	 * @param url
	 * @param targetKeyword 标题中的关键字或者指定的XPath
	 * @param timeout 指定时间内打不开网页报 timeout exception
	 * @return
	 * @exception RxCrawlerException
	 */
	public void open(String url, String targetKeyword, int timeout) throws RxCrawlerException;
	
	
	public void close();
	
	
	public RxNode getNodeByXpath(String xpath) throws RxCrawlerException;
	
	
	public void openTabWindow() throws RxCrawlerException;
	
	public void closeTabWindow() throws RxCrawlerException;
}
