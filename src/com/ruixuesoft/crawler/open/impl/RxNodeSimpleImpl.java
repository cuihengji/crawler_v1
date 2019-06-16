package com.ruixuesoft.crawler.open.impl;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.RxNode;

public class RxNodeSimpleImpl  implements RxNode {
	
	private DomElement element;
	
//	private HtmlPage htmlPage;
	private  RxSimpleCrawlerImpl crawler;
	
	public RxNodeSimpleImpl() {
		super();
    }
    
	public RxNodeSimpleImpl(DomElement element, RxSimpleCrawlerImpl crawler) {
		super();
		this.element = element;
		this.crawler = crawler;
	}

	@Override
	public void click() {
		
		try {
			HtmlPage htmlPage = this.element.click();
			crawler.setPage(htmlPage);
//			System.out.println(this.htmlPage.asText());
		} catch (Exception e) {
			throw new RxCrawlerException("click发生异常！"
					+ e.getMessage());
		}
		
	}

	@Override
	public void click(int waitInSeconds) {
		
		try {
			Thread.sleep(waitInSeconds*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			HtmlPage htmlPage = this.element.click();
			crawler.setPage(htmlPage);
		} catch (Exception e) {
			throw new RxCrawlerException("click发生异常！"
					+ e.getMessage());
		}
		
	}

	@Override
	public void click(String xPath) {
		
		List<DomElement> elments = this.crawler.getPage().getByXPath(xPath);
		if (null != elments && elments.size() > 0) {
			try {
				HtmlPage htmlPage = this.element.click();
				crawler.setPage(htmlPage);
			} catch (Exception e) {
				throw new RxCrawlerException("click发生异常！"
						+ e.getMessage());
			}
		}
		
	}

	@Override
	public void input(String text) throws RxCrawlerException {
		try {
//			this.element.setTextContent(text);
			HtmlInput inputField = (HtmlInput) this.element;
			inputField.setValueAttribute(text);
		} catch (Exception e) {
			throw new RxCrawlerException("input发生异常！"
					+ e.getMessage());
		}
	}

	@Override
	public void clear() throws RxCrawlerException {
		try {
//			this.element.setTextContent("");
			HtmlInput inputField = (HtmlInput) this.element;
			inputField.setValueAttribute("");
		} catch (Exception e) {
			throw new RxCrawlerException("clear发生异常！"
					+ e.getMessage());
		}
	}

	@Override
	public String getText() throws RxCrawlerException {
		try {
//			return this.element.getAttribute("value");
			return this.element.asText();
		} catch (Exception e) {
			throw new RxCrawlerException("getText发生异常！"
					+ e.getMessage());
		}
	}

	@Override
	public String getRawText() throws RxCrawlerException {
		return null;
	}

	@Override
	public String getAttribute(String attribute) throws RxCrawlerException {
		try {
			return this.element.getAttribute(attribute);
		} catch (Exception e) {
			throw new RxCrawlerException("isDisplayed发生异常！" + e.getMessage());
		}
	}

	@Override
	public List<RxNode> getNodeListByXpath(String xpath)
			throws RxCrawlerException {
		try {
			List<DomElement> elments = this.element.getByXPath(xpath);
			List<RxNode> nodes = new ArrayList<RxNode>();
			for (DomElement e : elments) {
				nodes.add(new RxNodeSimpleImpl(e, this.crawler));
			}
			return nodes;
		} catch (Exception e) {
			throw new RxCrawlerException("getNodeListByXpath发生异常！" + e.getMessage());
		}
	}

	@Override
	public RxNode getNodeByXpath(String xpath) throws RxCrawlerException {
		try {
			DomElement e = (DomElement) this.element.getFirstByXPath(xpath);
			return new RxNodeSimpleImpl(e, this.crawler);
		} catch (Exception e) {
			throw new RxCrawlerException("getNodeByXpath发生异常！" + e.getMessage());
		}
	}

	@Override
	public boolean isDisplayed() throws RxCrawlerException {
		
		try {
			return this.element.isDisplayed();
		} catch (Exception e) {
			throw new RxCrawlerException("isDisplayed发生异常！" + e.getMessage());
		}
	}

	@Override
	public void move(int xOffset, int yOffset) throws RxCrawlerException {
		
	}

	@Override
	public void moveToNode() throws RxCrawlerException {
		try {
			this.element.mouseMove();
		} catch (Exception e) {
			throw new RxCrawlerException("moveToNode发生异常！" + e.getMessage());
		}
	}
}
