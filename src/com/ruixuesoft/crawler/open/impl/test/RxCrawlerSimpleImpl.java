package com.ruixuesoft.crawler.open.impl.test;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.rkylin.crawler.engine.flood.util.StringUtil;
import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.impl.Accounting;
import com.ruixuesoft.crawler.open.impl.RxCrawlerImpl;

public class RxCrawlerSimpleImpl extends RxCrawlerImpl {
	
	public RxCrawlerSimpleImpl( WebDriver webDriver ) {
		super();
		this.webDriver = webDriver;
		this.accounting = new Accounting();
	}
	
	@Override
	public void open(String url) throws RxCrawlerException {
		
		if ((url == null) || (url.equalsIgnoreCase(""))) {
			throw new RxCrawlerException("输入的URL为空或者等于null!");
		}
		
		try {
			
			this.webDriver.manage().window().maximize();
			
			this.webDriver.get(url);
			
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
//			throw new RxCrawlerException(999, t.getMessage());
		}
		
		accounting.setPages(accounting.getPages() + 1);

	}

	@Override
	public void open(String url, String titleKeyword) throws RxCrawlerException {
		
		if ((url == null) || (url.equalsIgnoreCase(""))) {
			throw new RxCrawlerException("输入的URL为空或者等于null!");
		}
		
		try {
			
			this.webDriver.manage().window().maximize();
			this.webDriver.get(url);

		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(999, t.getMessage());
		}
		
    	String title = null;
		try {
			title = StringUtil.removeSpecialChar(this.webDriver.getTitle());
		} catch (WebDriverException e) {
    		
	    	logger.error("webDriver.getTitle()异常", e);
	    	
	    	throw new RxCrawlerException(806,"getTitle()异常");
		}
		
		if (title != null && !"".equals(title) && !titleKeyword.equals("-1")) {
			if (!StringUtil.contains(title, titleKeyword)) {
	    		
		    	throw new RxCrawlerException(806,"getTitle()异常"); 
			}
		}
		
		accounting.setPages(accounting.getPages() + 1);
	}
}
