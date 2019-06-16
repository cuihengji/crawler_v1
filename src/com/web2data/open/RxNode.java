package com.web2data.open;

import java.util.List;

public interface RxNode {

	/**
     * 单击页面元素
     * @param
     * @return
     * @exception
     */
	public void click();
	
	/**
     * 单击页面元素,等待指定的秒数后
     * @param
     * @return
     * @exception
     */
	public void click(int waitInSeconds);
	
	
	/**
     * 单击页面元素,等待指定的xPath的Node出现, 默认等待30秒
     * @param
     * @return
     * @exception
     */
	public void click(String xPath);
	
	/**
     * 输入文本
     * @param text
     * @return
     * @exception RxCrawlerException
     */
	public void input(String text) throws RxCrawlerException;

	/**
     * 清空指定页面元素的数据
     * @param
     * @return
     * @exception RxCrawlerException
     */
	public  void clear() throws RxCrawlerException;  
	
	/**
     * 获取页面元素的text,这个方法会过滤掉Text中包含的特殊字符
     * @param
     * @return
     * @exception RxCrawlerException
     */
	public  String getText() throws RxCrawlerException;
	
	
	/**
     * 获取页面元素的text,这个方法不会过滤掉Text中包含的特殊字符
     * @param
     * @return
     * @exception RxCrawlerException
     */
	public  String getRawText() throws RxCrawlerException;
	
	
	/**
     * 获取页面元素的attribute
     * @param attribute
     * @return
     * @exception RxCrawlerException
     */
	public  String getAttribute(String attribute) throws RxCrawlerException;
	
	
	/**
     * 根据指定xpath，获取该节点下xpath定位的所有元素
     * @param xpath
     * @return
     * @exception RxCrawlerException
     */
	public  List<RxNode> getNodeListByXpath(String xpath) throws RxCrawlerException;
	
	/**
     * 根据指定xpath，获取该节点下xpath定位的元素
     * @param xpath
     * @return
     * @exception RxCrawlerException
     */
	public RxNode getNodeByXpath(String xpath) throws RxCrawlerException;
	
	/**
	 * 判断得到的RxNode是否在页面显示
	 * 
	 * @return true or false
	 * @throws RxCrawlerException
	 */
	public boolean isDisplayed() throws RxCrawlerException;
	
	
	/**
     * 移动鼠标到指定的位置
     * @param int xOffset, int yOffset
     * @return
     * @exception RxCrawlerException
     */
	public void move(int xOffset, int yOffset) throws RxCrawlerException;

	
	/**
	 * 使鼠标悬浮在指定的元素上
	 * @throws RxCrawlerException
	 */
	public void moveToNode() throws RxCrawlerException;


	/**
	 * 获得位置X
	 * @return
	 * @throws RxCrawlerException
	 */
	public int getLocationX() throws RxCrawlerException;


	/**
	 * 获得位置Y
	 * @return
	 * @throws RxCrawlerException
	 */
	public int getLocationY() throws RxCrawlerException;
	
}
