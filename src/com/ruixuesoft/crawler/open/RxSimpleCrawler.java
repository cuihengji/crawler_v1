package com.ruixuesoft.crawler.open;

import java.util.List;

import org.json.JSONObject;

/**
 * 新的Crawler接口,这个接口可以快速返回查询的信息
 *
 */
public interface RxSimpleCrawler {

	 /**
     * 根据指定url,打开一个网页
     * @param url
     * @return
     * @exception RxCrawlerException
     */
	public void open(String url) throws RxCrawlerException; 
	
	
	/**
     * 根据指定url,打开一个网页,可以设定打开网页的页面高度.
     * @param url 打开网页的链接
     * @param pageHeight 页面的高度
     * @return
     * @exception RxCrawlerException
     */
	public void open(String url,int pageHeight) throws RxCrawlerException; 
	
	
	/**
     * 设定打开网页的页面高度.
     * @param pageHeight 页面的高度
     * @return
     * @exception RxCrawlerException
     */
	public void setPageHeight(int pageHeight) throws RxCrawlerException; 
	
	
	/**
     * 得到页面的Title
     * @return 页面的Title
     * @exception RxCrawlerException
     */
	public String getTitle() throws RxCrawlerException;
	
	
	/**
     * 得到页面的源文件
     * @param 
     * @return 
     * @exception RxCrawlerException
     */
	public String getPageSource() throws RxCrawlerException; 
	
	
	/**
	 * 制定插件在执行下一个操作前暂停多少秒
	 * 
	 * @param seconds
	 */
	public void sleepSeconds(int seconds);

	
	/**
     * 根据指定的Id获取页面上的所有节点元素
     * @param id
     * @return List<RxNode>
     * @exception RxCrawlerException
     */
	public List<RxNode> getNodeListById(String id) throws RxCrawlerException;
	
	
	/**
     * 根据指定的name获取页面上的所有节点元素
     * @param name
     * @return List<RxNode>
     * @exception RxCrawlerException
     */
	public List<RxNode> getNodeListByName(String name) throws RxCrawlerException;
	
	
	/**
     * 根据指定的xpath获取页面上的所有节点元素
     * @param xpath
     * @return List<RxNode>
     * @exception RxCrawlerException
     */
	public List<RxNode> getNodeListByXpath(String xpath) throws RxCrawlerException;
	
	
	/**
     * 根据指定的xpath获取页面上的节点元素
     * @param xpath
     * @return RxNode
     * @exception RxCrawlerException
     */
	public RxNode getNodeByXpath(String xpath) throws RxCrawlerException;
	
	
	/**
     * 根据指定的xpath获取页面上下拉列表
     * @param xpath
     * @return RxSelectNode
     * @exception RxCrawlerException
     */
	public RxSelectNode getSelectNodeByXpath(String xpath) throws RxCrawlerException; 
	
	
	/**
     * 设定页面JavaScript加载需要等待的时间
     * @param timeoutSeconds
     * @return int
     * @exception RxCrawlerException
     */
	public int waitForBackgroundJavaScript(int timeoutSeconds);
	
	
	/**
     * 在使用open函数打开网页后，基于指定的xpath为对应的html元素输入文本。
     * @param xpath
     * @param text
     * @return
     * @exception RxCrawlerException
     */
	public void input(String xpath, String text) throws RxCrawlerException;
	
	
	/**
     * 单击页面元素
     * @param xpath
     * @exception RxCrawlerException
     */
	public void click(String xpath) throws RxCrawlerException;
	
	
	/**
     * 清空指定页面元素的数据
     * @param
     * @return
     * @exception RxCrawlerException
     */
	public void clear(String xpath) throws RxCrawlerException;
	
	
	/**
     * 得到页面上所有的JSONObject对象
     * @return JSONObject
     * @exception RxCrawlerException
     */
	public JSONObject getAllCookies() throws RxCrawlerException;
	
	
	/**
     * 得到页面上所指定cookie的值
     * @param name
     * @return JSONObject
     * @exception RxCrawlerException
     */
	public String getCookieByName( String name );
}
