package com.ruixuesoft.crawler.open;

public interface RxSelectNode {

	/**
	 * 根据下拉列表下标获取下拉列表下拉项
	 * @param index 下拉列表的下标
	 * @return
	 * @exception RxCrawlerException
	 */
	public void selectByIndex(int index) throws RxCrawlerException;

	/**
	 * 根据下拉列表选项的文字获取下拉列表下拉项
	 * @param text 下拉列表的选项文字
	 * @return
	 * @exception RxCrawlerException
	 */
	public void selectByVisibleText(String text) throws RxCrawlerException;

	/**
	 * 根据下拉列表选项的值获取下拉列表下拉项
	 * @param value 下拉列表的选项值
	 * @return
	 * @exception RxCrawlerException
	 */
	public void selectByValue(String value) throws RxCrawlerException;
	
	
	/**
	 * @return 获得下拉列表选项的长度
	 * @throws RxCrawlerException
	 */
	public int getSelectLength() throws RxCrawlerException;
	
	
	/**根据下拉列表下标获取下拉列表的选项值
	 * @param index 下拉列表的下标
	 * @throws RxCrawlerException
	 */
	public String getTextByIndex(int index) throws RxCrawlerException;


}
