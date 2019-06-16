package com.web2data.engine.crawler.browser.node.impl.a;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.web2data.engine.crawler.browser.node.impl.RxNodeImpl;
import com.web2data.open.RxCrawlerException;

public class RxNodeImpl_move {

	
	public static void moveToNode(RxNodeImpl arg) 
			throws RxCrawlerException {
		
		//checkInterrupted();

		try {
			Actions actions = new Actions(arg.webDriver);
			actions.moveToElement(arg.element).perform();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e.getMessage());
		}
	}
	
	
	public static void move(int xOffset, int yOffset, RxNodeImpl arg) 
			throws RxCrawlerException {

		//checkInterrupted();

		try {
			Actions actions = new Actions(arg.webDriver);
//			new Actions(webDriver).clickAndHold(element).perform();
			actions.moveToElement(arg.element, xOffset, yOffset).perform();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e.getMessage());
		}
	}
	
	
    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxNodeImpl_move.class);
    
}
