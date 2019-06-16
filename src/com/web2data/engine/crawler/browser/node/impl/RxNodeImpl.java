package com.web2data.engine.crawler.browser.node.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.web2data.engine.crawler.browser.node.impl.a.RxNodeImpl_click;
import com.web2data.engine.crawler.browser.node.impl.a.RxNodeImpl_get;
import com.web2data.engine.crawler.browser.node.impl.a.RxNodeImpl_input;
import com.web2data.engine.crawler.browser.node.impl.a.RxNodeImpl_move;
import com.web2data.engine.crawler.browser.node.impl.a.RxNodeImpl_xpath;
import com.web2data.open.RxCrawlerException;
import com.web2data.open.RxNode;


public class RxNodeImpl implements RxNode {

	public WebDriver webDriver;
	public WebElement element;
	public String xpath;
	//private Accounting accounting;
	//private TabWindow newTabWindow;

	public RxNodeImpl(WebDriver webDriver, WebElement element, String xpath
			//, Accounting accounting, TabWindow newTabWindow
			) {
		super();
		this.webDriver = webDriver;
		this.element = element;
		this.xpath = xpath;
		//this.accounting = accounting;
		//this.newTabWindow = newTabWindow;
	}

	@Override
	public void click() {
		RxNodeImpl_click.click(this);
	}

	
	@Override
	public void click(int waitInSeconds) {
		RxNodeImpl_click.click(waitInSeconds, this);
	}
	
	
	@Override
	public void click(String xPath) {
		RxNodeImpl_click.click(xPath, this);
	}
	
	
	@Override
	public void input(String text) throws RxCrawlerException {
		RxNodeImpl_input.input(text, this);
	}

	@Override
	public void clear() throws RxCrawlerException {
		RxNodeImpl_input.clear(this);
	}

	@Override
	public String getAttribute(String attribute) throws RxCrawlerException {
		return RxNodeImpl_get.getAttribute(attribute, this);
	}

	@Override
	public String getText() throws RxCrawlerException {
		return RxNodeImpl_get.getText(this);
	}
	
	@Override
	public String getRawText() throws RxCrawlerException {
		return RxNodeImpl_get.getRawText(this);
	}
	
	@Override
	public List<RxNode> getNodeListByXpath(String xpath) throws RxCrawlerException {
		return RxNodeImpl_xpath.getNodeListByXpath(webDriver, element, xpath);
	}


	@Override
	public RxNode getNodeByXpath(String xpath) throws RxCrawlerException {
		return RxNodeImpl_xpath.getNodeByXpath(webDriver, element, xpath);
	}

	
	public int getLocationX() throws RxCrawlerException {
		return this.element.getLocation().getX();
	}

	public int getLocationY() throws RxCrawlerException {
		return this.element.getLocation().getY();
	}

	@Override
	public boolean isDisplayed() throws RxCrawlerException {
		
		//checkInterrupted();

		boolean result  = false;
		
		try {
			result = this.element.isDisplayed();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return result;
	}

	@Override
	public void moveToNode() throws RxCrawlerException {
		RxNodeImpl_move.moveToNode(this);
	}
	
	@Override
	public void move(int xOffset, int yOffset) throws RxCrawlerException {
		RxNodeImpl_move.move(xOffset, yOffset, this);
	}
	
	
	
	public WebElement getElement() {
		return element;
	}

	public void setElement(WebElement element) {
		this.element = element;
	}
	
	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	
	public void checkInterrupted() {

		if (Thread.currentThread().isInterrupted()) {
			throw new RxCrawlerException("该任务已被用户请求强制终止！");
		}
	}

	
    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxNodeImpl.class);
    
}
