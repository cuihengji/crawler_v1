package com.web2data.engine.crawler.browser.impl.a;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.RxNode;
import com.ruixuesoft.crawler.open.RxSelectNode;
import com.ruixuesoft.crawler.open.RxSimpleCrawler;

public class RxSimpleCrawlerImpl implements RxSimpleCrawler {

	public static final Logger logger = Logger.getLogger(RxSimpleCrawlerImpl.class);
	
	private WebClient webClient;
	private HtmlPage page;
	
	
	public RxSimpleCrawlerImpl(WebClient webClient) {
		super();
		this.webClient = webClient;
	}
	

	@Override
	public void open(String url) throws RxCrawlerException {
		
		checkInterrupted();
		
		this.webClient.setAlertHandler(new AlertHandler() {
	        @Override
	        public void handleAlert(Page page, String message) {
	        	logger.info("handleAlert : "+message);

	        }
	    });
		
		try {
			page = (HtmlPage)this.webClient.getPage(url);
			System.out.println("getTitleText : " + page.getTitleText());
		} catch (Exception e) {
			throw new RxCrawlerException("不能打开指定的URL: " + e.getMessage());
		}
	}

	@Override
	public void open(String url, int pageHeight) throws RxCrawlerException {
		
		checkInterrupted();
		
		if (pageHeight <= 0) {
			throw new RxCrawlerException("请设置pageHeight的值为正");
		}
		try {
			this.webClient.getCurrentWindow().setInnerHeight(pageHeight);
			page = (HtmlPage)this.webClient.getPage(url);
			System.out.println("getTitleText : " + page.getTitleText());
		} catch (Exception e) {
			throw new RxCrawlerException("不能设置pageHeight: " + e.getMessage());
		}

	}

	@Override
	public void setPageHeight(int pageHeight) throws RxCrawlerException {
		checkInterrupted();
		
		if (pageHeight <= 0) {
			throw new RxCrawlerException("请设置pageHeight的值为正");
		}
		try {
			this.webClient.getCurrentWindow().setInnerHeight(pageHeight);
		} catch (Exception e) {
			throw new RxCrawlerException("不能设置pageHeight: " + e.getMessage());
		}

	}

	@Override
	public String getTitle() throws RxCrawlerException {
		checkInterrupted();
		return page.getTitleText();
	}

	@Override
	public String getPageSource() throws RxCrawlerException {
		checkInterrupted();
		return page.asText();
	}

	@Override
	public void sleepSeconds(int seconds) {
		checkInterrupted();
		try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
	}

	@Override
	public List<RxNode> getNodeListById(String id) throws RxCrawlerException {
		try {
			List<DomElement> elments = this.page.getElementsById(id);
			List<RxNode> nodes = new ArrayList<RxNode>();
			for (DomElement e : elments) {
				nodes.add(new RxNodeSimpleImpl(e, this));
			}
			return nodes;
		} catch (Exception e) {
			throw new RxCrawlerException("getNodeListById发生异常!: " + e.getMessage());
		}
	}

	@Override
	public List<RxNode> getNodeListByName(String name) throws RxCrawlerException {
		try {
			List<DomElement> elments = this.page.getElementsByName(name);
			List<RxNode> nodes = new ArrayList<RxNode>();
			for (DomElement e : elments) {
				nodes.add(new RxNodeSimpleImpl(e, this));
			}
			return nodes;
		} catch (Exception e) {
			throw new RxCrawlerException("getNodeListByName发生异常!: " + e.getMessage());
		}
	}

	@Override
	public List<RxNode> getNodeListByXpath(String xpath) throws RxCrawlerException {
		try {
			List<DomElement> elments = this.page.getByXPath(xpath);
			List<RxNode> nodes = new ArrayList<RxNode>();
			for (DomElement e : elments) {
				nodes.add(new RxNodeSimpleImpl(e, this));
			}
			return nodes;
		} catch (Exception e) {
			throw new RxCrawlerException("getNodeListByXpath发生异常!: " + e.getMessage());
		}
	}

	@Override
	public RxNode getNodeByXpath(String xpath) throws RxCrawlerException {
		try {
			DomElement e = this.page.getFirstByXPath(xpath);
			return new RxNodeSimpleImpl(e, this);
		} catch (Exception e) {
			throw new RxCrawlerException("getNodeByXpath发生异常!: " + e.getMessage());
		}
	}

	@Override
	public RxSelectNode getSelectNodeByXpath(String xpath) throws RxCrawlerException {
		HtmlSelect select = (HtmlSelect) this.page.getFirstByXPath(xpath);
		RxSelectNode selectNode = new RxSelectNodeSimpleImpl(select);
		return selectNode;
	}

	@Override
	public int waitForBackgroundJavaScript(int timeoutSeconds) {
		this.webClient.waitForBackgroundJavaScript(timeoutSeconds);
		return 0;
	}

	@Override
	public void input(String xpath, String text) throws RxCrawlerException {

		checkInterrupted();
		try {
			HtmlInput inputField = (HtmlInput) page.getFirstByXPath(xpath);
			inputField.setDefaultValue(text);
		} catch (Exception e) {
			throw new RxCrawlerException("input方法发生异常!: " + e.getMessage());
		}
	}

	@Override
	public void click(String xpath) throws RxCrawlerException {
		
		checkInterrupted();
		HtmlInput  clickNode = (HtmlInput)page.getFirstByXPath(xpath);
		try {
			page = clickNode.click();
		} catch (IOException e) {
			throw new RxCrawlerException("click方法发生异常!: " + e.getMessage());
		}
		
	}
	
	@Override
	public void clear(String xpath) throws RxCrawlerException {
		checkInterrupted();
		try {
			HtmlInput inputField = (HtmlInput) page.getFirstByXPath(xpath);
			inputField.setDefaultValue("");
			System.out.println(inputField.getDefaultValue());

		} catch (Exception e) {
			throw new RxCrawlerException("input方法发生异常!: " + e.getMessage());
		}
	}


	@Override
	public JSONObject getAllCookies() throws RxCrawlerException {
		
		checkInterrupted();
		
		JSONObject jsonObject = new JSONObject();
		Set<Cookie> cookies = this.webClient.getCookieManager().getCookies();
		try {
			for (Cookie c : cookies) {
				jsonObject.put(c.getName(), c.getValue());
			}
		} catch (JSONException e) {
			new RxCrawlerException(999, "json转换异常，" + e.getMessage());
		}
		
		return jsonObject;

	}

	@Override
	public String getCookieByName(String name) {
		
		Set<Cookie> cookies = this.webClient.getCookieManager().getCookies();
		for ( Cookie c : cookies ) {
			if ( name.equalsIgnoreCase(c.getName()) ) {
				return c.getValue();
			}
		}
		return null;
	}
	
	
	public void checkInterrupted() {

		if (Thread.currentThread().isInterrupted()) {
			throw new RxCrawlerException("该任务已被用户请求强制终止！");
		}
	}


	public HtmlPage getPage() {
		return page;
	}


	public void setPage(HtmlPage page) {
		this.page = page;
	}


}
