package com.web2data.engine.crawler.browser.node.impl.a;

import org.apache.log4j.Logger;

import org.openqa.selenium.WebElement;

import com.rkylin.crawler.engine.flood.util.StringUtil;
import com.web2data.engine.crawler.browser.node.impl.RxNodeImpl;
import com.web2data.open.RxCrawlerException;

public class RxNodeImpl_get {


	public static String getAttribute(String attribute, RxNodeImpl arg) throws RxCrawlerException {
		
		//checkInterrupted();

		try {
			return StringUtil.removeSpecialChar( arg.element.getAttribute(attribute) );
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e);
//			try {
//				logger.error("getText() exception: ", e);
//				RxCrawlerImpl crawler = new RxCrawlerImpl(webDriver, accounting);
//				RxNode restoredNode = crawler.getNodeByXpath(getXpath());
//				return StringUtil.removeSpecialChar(restoredNode.getAttribute(attribute));
//			} catch (Exception e2) {
//				throw new RxCrawlerException(e.getMessage());
//			}
		}
	}


	public static String getText(RxNodeImpl arg) throws RxCrawlerException {

		//checkInterrupted();

		try {
			return StringUtil.removeSpecialChar( arg.element.getText() );
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e);
//			try {
//				logger.error("getText() exception: ", e);
//				RxCrawlerImpl crawler = new RxCrawlerImpl(webDriver, accounting);
//				RxNode restoredNode = crawler.getNodeByXpath(getXpath());
//				return StringUtil.removeSpecialChar(restoredNode.getText());
//			} catch (Exception e2) {
//				
//			}
		}
	}
	

	public static String getRawText(RxNodeImpl arg) throws RxCrawlerException {

		//checkInterrupted();

		try {
			return arg.element.getText();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e);
//			try {
//				logger.error("getText() exception: ", e);
//				RxCrawlerImpl crawler = new RxCrawlerImpl(webDriver, accounting);
//				RxNode restoredNode = crawler.getNodeByXpath(getXpath());
//				return StringUtil.removeSpecialChar(restoredNode.getText());
//			} catch (Exception e2) {
//				
//			}
		}
	}
	
	
    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxNodeImpl_get.class);
    
}
