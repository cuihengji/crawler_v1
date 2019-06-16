package com.ruixuesoft.crawler.open;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONObject;
import org.sikuli.script.Screen;

import com.gargoylesoftware.htmlunit.WebClient;

public interface RxCrawler {

    /**
     * 根据指定url,打开一个网页，默认180秒超时，报timeout exception
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
     * 根据指定的xpath获取页面上该xpath指定的所有节点元素
     * @param xpath
     * @return List<RxNode>
     * @exception RxCrawlerException
     */
	public List<RxNode> getNodeListByXpath(String xpath) throws RxCrawlerException;
	
	/**
     * 根据指定的xpath获取页面上该xpath指定的所有节点元素
     * @param xpath
     * @param timeoutSeconds
     * @return List<RxNode>
     * @exception RxCrawlerException
     */
	public List<RxNode> getNodeListByXpath(String xpath, int timeoutSeconds) throws RxCrawlerException;

	/**
     * 滚动到顶部
     * @param 
     * @return void
     * @exception RxCrawlerException
     */
	public void scrollToTop() throws RxCrawlerException;

	/**
     * 滚动到底部
     * @param 
     * @return void
     * @exception RxCrawlerException
     */
	public void scrollToBottom() throws RxCrawlerException;

	/**
     * 滚动到指定位置
     * @param pixels
     * @return void
     * @exception RxCrawlerException
     */
	public void scroll(int pixels) throws RxCrawlerException;

	/**
     * 滚动到指定节点处
     * @param node
     * @return void
     * @exception RxCrawlerException
     */
	public void scrollTo(RxNode node) throws RxCrawlerException;
	
//	/**
//     * 输入验证码，目前只支持windows系统
//     * @param verifyImageXpath 验证码图片的xpath
//     * @param verifyInputTextXpath 验证码输入框的xpath
//     * @param iframeXpath 验证码图片所在iframe的Xpath
//     * @return void
//     * @exception RxCrawlerException
//     */
//	public void inputVerifyCode(String verifyImageXpath, String verifyInputTextXpath, String iframeXpath)  throws RxCrawlerException;

	/**
     * 输入验证码，目前只支持windows系统
     * @param verifyImageXpath 验证码图片的xpath
     * @param verifyInputTextXpath 验证码输入框的xpath
     * @return void
     * @exception RxCrawlerException
     */
	public void inputVerifyCode(String verifyImageXpath, String verifyInputTextXpath)  throws RxCrawlerException;

//	/**
//     * 根据验证码图片显示的验证码，选择正确的验证码
//     * @param verifyImageXpath 验证码图片的xpath
//     * @param verifyButtonXpath 验证码按钮的xpath
//     * @param iframeXpath 验证码图片所在iframe的Xpath
//     * @return void
//     * @exception RxCrawlerException
//     */
//	public void selectVerifyCode(String verifyImageXpath, String verifyButtonXpath, String iframeXpath)  throws RxCrawlerException;

	/**
     * 根据验证码图片显示的验证码，选择正确的验证码
     * @param verifyImageXpath 验证码图片的xpath
     * @param verifyButtonXpath 验证码输入框的xpath
     * @return void
     * @exception RxCrawlerException
     */
	public void selectVerifyCode(String verifyImageXpath, String verifyButtonXpath)  throws RxCrawlerException;
	
	
	/**
     * 在使用open函数打开网页后，基于指定的xpath为对应的html元素输入文本。
     * @param xpath
     * @param text
     * @return
     * @exception RxCrawlerException
     */
	public void input(String xpath, String text) throws RxCrawlerException;
	
	/**
     * 在使用open函数打开网页后，基于指定的xpath为对应的html元素清除文本。
     * @param xpath
     * @return
     * @exception RxCrawlerException
     */
	public void clear(String xpath) throws RxCrawlerException;

	
	/**
     * 当网页弹出警告框口,使用这个方法关闭
	 *
     * @exception RxCrawlerException
     */
	
	public void closeAlert() throws RxCrawlerException;
	
	/**
     * 当网页弹出警告框口,等待指定的时间后, 使用这个方法关闭
	 *
	 * @param waitSeconds 等待的时间
	 * 
     * @exception RxCrawlerException
     */
	public void closeAlert(int waitSeconds) throws RxCrawlerException;

	/**
     * 睡眠指定秒数
	 *
	 * @param seconds 等待的时间
     */
	
	/**
     * 检查是否有Alert警告框弹出
	 *
     * @exception RxCrawlerException
     */
	
	public boolean isAlertDisplayed() throws RxCrawlerException;
	
	
	/**
     * 当网页弹出警告框口,得到弹出框的文本
	 *
     * @exception RxCrawlerException
     */
	
	public String getAlertText() throws RxCrawlerException;
	
	
	/**
	 * 制定插件在执行下一个操作前暂停多少秒
	 * 
	 * @param seconds
	 */
	public void sleepSeconds( int seconds );

	/**
     * 执行Javascript代码
	 *
	 * @param jsScript Javascript代码
	 * 
     * @exception RxCrawlerException
     */
	public Object executeJsScript(String jsScript) throws RxCrawlerException;

	
	/**
     * 判断是否有新的tab页打开
     * @param 
     * @return 是否有新的tab页打开
     * @exception RxCrawlerException
     */
	public boolean isNewTabOpened() throws RxCrawlerException;
	
	
	/**
     * 打开新的tab页
     * @param 
     * @return 
     * @exception RxCrawlerException
     */
	public void switchToNewTab() throws RxCrawlerException;
	
	
	/**
     * 关闭新的tab页
     * @param 
     * @return 
     * @exception RxCrawlerException
     */
	public void closeNewTab() throws RxCrawlerException;
	
	/**
     * 得到页面的源文件
     * @param 
     * @return 
     * @exception RxCrawlerException
     */
	public String getPageSource() throws RxCrawlerException;
	
	
	/**
     * 得到页面的Title
     * @return 页面的Title
     * @exception RxCrawlerException
     */
	public String getTitle() throws RxCrawlerException;
	
	
	/**
	 * @param imageSrc
	 * @return 识别图像字符串
	 * @throws RxCrawlerException
	 * @depricated 推荐使用phoneImageRecogize(String imageXpath, Website website)识别顺企网的电话号码
	 */
	public String uuPictureRecognition(String imageSrc) throws RxCrawlerException;
	
	
	/**
	 * @return GeeTest滑动验证码是否破解成功,请注意如果verifyGeeTest返回false,请重新刷新页面, 输入查询的内容, 然后再次调用verifyGeeTest.
	 * 一般建议用户在for循环中调用多次直到通过滑动验证码校验.请注意每一次的循环中请重新输入查询内容后点击查询.这个方法适用于http://www.gsxt.gov.cn/
	 * @throws RxCrawlerException
	 */
	public boolean verifyGeeTest();
	
	/**
	 * @return GeeTest滑动验证码是否破解成功,请注意如果verifyGeeTest返回false,请重新刷新页面, 输入查询的内容, 然后再次调用verifyGeeTest.
	 * 一般建议用户在for循环中调用多次直到通过滑动验证码校验.请注意每一次的循环中请重新输入查询内容后点击查询.这个方法仅适用于http://www.qixin.com
	 * @throws RxCrawlerException
	 */
	public boolean verifyGeeTest2();
	
	
	/**
	 * @param imageSrc or picture div xpath
	 * @return 识别图像字符串
	 * @throws RxCrawlerException
	 */
	public String imageRecogize(String imageXpath) throws RxCrawlerException;
	
	
	/**
	 * @param imageSrc or picture div xpath
	 * @return 识别图像字符串
	 * @throws RxCrawlerException
	 */
	public String imageRecognize(String imageXpath) throws RxCrawlerException;
	
	
	/**
	 * @param imageSrc or picture div xpath
	 * @return 识别电话号码图像
	 * @throws RxCrawlerException
	 */
	public String phoneImageRecogize(String imageXpath) throws RxCrawlerException;
	
	
	/**
	 * @param imageSrc or picture div xpath
	 * @return 识别电话号码图像
	 * @throws RxCrawlerException
	 */
	public String phoneImageRecognize(String imageXpath) throws RxCrawlerException;
	
	/**
	 * @param imageXpath or picture div xpath
	 * @param website 目前仅支持顺企网的电话号码图片识别 Website.ShunQi
	 * @return 识别电话号码图像
	 * @throws RxCrawlerException
	 */
	public String phoneImageRecogize(String imageXpath, Website website) throws RxCrawlerException;
	
	
	/**
	 * @param imageXpath or picture div xpath
	 * @param website 目前仅支持顺企网的电话号码图片识别 Website.ShunQi
	 * @return 识别电话号码图像
	 * @throws RxCrawlerException
	 */
	public String phoneImageRecognize(String imageXpath, Website website) throws RxCrawlerException;

	
//    /**
//     * 根据指定坐标截取图片
//     * @param appSeq app的seq
//     * @param x 图片的x坐标值
//     * @param y 图片的y坐标值
//     * @param w 图片的宽度
//     * @param h 图片的高度
//     */
//    public String takeScreenshot ( int appSeq, int x, int y, int w, int h);
    
    
    /**
     * 截取当前屏幕图片
     * @param appSeq app的seq
     * @param isWatermark 是否有水印
     */
    public String takeScreenshot ( int appSeq, boolean isWatermark );
    
    
    /**
     * 截取屏幕图片,这个方法会滚动到屏幕底部截取图片
     * @param isWatermark 是否有水印
     * @param maskIds 个别的DIV Id在截图时不想显示
     */
    public String takeFullScreenshot(boolean isWatermark, List<String> divIds);
    
    /**
     * 截取全屏图片
     * @param appSeq app的seq
     * @param isWatermark 是否有水印
     */
    public String takeScreenshotByJava ( int appSeq, boolean isWatermark );
    
    
    /**
     * 得到指定URL的图片
     * @param appSeq app的seq
     * @param url 下载图片的URL
     */
    public String getImageByUrl ( int appSeq, String url);
    
    
    
    /**
     * 重新加载网页URL,刷新页面
     */
    public void refresh();
    
    /**
     * 网页返回功能
     */
    public void back();
    
//	/**
//	 * @param elementXpath
//	 * @throws RxCrawlerException
//	 */
//    public void moveToElement(String elementXpath) throws RxCrawlerException;
    
	/**
	 * @param 获取screen实例
	 * @throws RxCrawlerException
	 */
    public Screen getScreenInstance() throws RxCrawlerException;
    
    
    /**
	 * @param 执行原生的JavaScript指令,这些指令不使用Selenium的通讯协议和方法
	 */
    public void performJSAction(String actionName, String actionValue, String selector);
    
    /**
	 * @param 
	 * @return 返回当前打开的tab的个数
	 */
    public int getCurTabs();
    
    /**
     * 获取打开的url的所有Cookies
     * 
     * @return JSONObject Cookies的json格式
     */
    public JSONObject getAllCookies() throws RxCrawlerException;
    
    /**
     * 根据name获取Cookie的值
     * @param Cookie的Name
     * @return String Cookie的值
     */
    public String getCookieByName( String name );
    
    /**
     * 根据name删除Cookie
     * @param Cookie的Name
     */
    public void deleteCookieByName( String name );
    
    /**
     * 删除所有的Cookie
     * @param Cookie
     */
    public void deleteAllCookies( );
    
    /**
   	 * @param 要检测的AJAX请求的Url
   	 * @return 返回RxHttpNetwork的信息,RxHttpNetwork类用来获取直接发送HTTPClient的参数.请不要用这个方法打开网站然后做进一步的抓取.
   	 */
    public RxHttpNetwork getHttpNetworkByURL(String url) throws RxCrawlerException;
    
    
    /**
   	 * @return 返回一个轻量级的Crawler,RxSimpleCrawler可以快速返回需要抓取的信息
   	 */
    public RxSimpleCrawler getSimpleCrawler() throws RxCrawlerException;
    
    
    
    /**
   	 * @param 要拷贝的RxHttpRequest实例
   	 * @return 拷贝RxHttpRequest实例
   	 */
    public RxHttpRequest copyRxHttpRequest(RxHttpRequest rxHttpRequest);
    
    
    /**
   	 * @param 要发送的RxHttpRequest实例
   	 * @return 根据传入的RxHttpRequest实例发送一个新的AJAX请求
   	 */
    public RxHttpResponse send(RxHttpRequest rxHttpRequest);
    
    /**
   	 * @param 
   	 * @return 获取一个HttpClient对象
   	 */
    public CloseableHttpClient getHttpClient();
    
    
    /**
   	 * @param 
   	 * @return 获取HtmlUnit对象
   	 */
    public WebClient getHtmlUnitWebClient();

    
    /**
   	 * @param 校验企查查的滑动验证码:http://www.qichacha.com/user_login?back=/cinvestment_d33cbbc578913ee033fdcfd894c632cd
   	 * @return boolean 
   	 */
    public boolean verifyQichacha();
    
    
    /**
     * @param url 要访问过的url链接
     * @return RxHttpNode, 可以用这个借口查看返回的page source或者做进一步的操作例如getText(), getAttribute
     */
    public RxHttpNode doHttpGet(String url);
    
    
    /**
     * @param url 要访问过的url链接
     * @param Map<String, String> header 要传入的header信息
     * @param Map<String, String> cookie 要传入的cookie信息
     * @return RxHttpNode, 可以用这个借口查看返回的page source或者做进一步的操作例如getText(), getAttribute
     */
    public RxHttpNode doHttpGet(String url, Map<String, String> header, Map<String, String> cookie);

    
    /**
     * @param url 要访问过的url链接
     * @param Map<String, String> params 要传入的参数列表
     * 
     * @return RxHttpNode, 可以用这个借口查看返回的page source或者做进一步的操作例如getText(), getAttribute
     */
    public RxHttpNode doHttpPost(String url, Map<String, String> params);
    
    
    
    /**
     * @param url 要访问过的url链接
     * @param Map<String, String> params 要传入的参数列表
       @param Map<String, String> header 要传入的header信息
     * @param Map<String, String> cookie 要传入的cookie信息
     * @return RxHttpNode, 可以用这个借口查看返回的page source或者做进一步的操作例如getText(), getAttribute
     */
    public RxHttpNode doHttpPost(String url, Map<String, String> params, Map<String, String> header, Map<String, String> cookie);
    
    
    /**
     * @param selector 传入的Select名称 
     * @return RxHttpNode, 可以用这个借口查看返回的page source或者做进一步的操作例如getText(), getAttribute
     */
    public List<RxHttpNode> getRxHttpNodeBySelector(String selector);
    
    /**
     * @param url 传入的URL应该包含后续的AJAX请求 
     * @param ajaxUrl 例如我们想要获取这个AJAX请求的相关信息 http://wenshu.court.gov.cn/List/ListContent,请输入这个字符串
     * @return RxHttpContext, 这个对象可以查询到AJAX请求中发送的Cookie或者请求的参数内容
     */
    public RxHttpContext getRxHttpContext(String url, String ajaxUrl);
    
    /**
     * @param url 下载excel的url
     * @return 返回指定的url下载后的excel数据
     */
    public Map<String, ArrayList<ArrayList<String>>> readExcelDataByUrl(String url);
    
    /**
     * @param key 按下的key(tab,alt等)
     */
    public void pressKeys(CharSequence... keys);
    
    /**
     * @param key 指定的key被按下的次数
     * @param key 按下的key(tab,alt等)
     */
    public void pressKeys(int times, CharSequence... keys);
    
    /**
     * @param name cookie名字
     * @param value cookie的值
     * 
     *例如:  addCookie("tracknick","baggio75");
     */
    public void addCookie(String name, String value);
    
    /**
     * @param name cookie名字
     * @param value cookie的值
     * @param domainName 访问网站的名字, 例如:taobao.com
     * @param path 一般为根目录 /
     * @param expireDate 过期时间
     * 
     * 例如: addCookie("tracknick","baggio75",".taobao.com ","/",expireDate);
     */
    public void addCookie(String name, String value, String domainName, String path, java.util.Date expireDate);
    
    
    /**
     * @return 返回当前打开页面的url
     */
    public String getCurrentUrl();

    
}
