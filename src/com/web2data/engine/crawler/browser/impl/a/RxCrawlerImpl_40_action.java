package com.web2data.engine.crawler.browser.impl.a;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.web2data.open.RxCrawlerException;
import com.web2data.open.RxNode;


public class RxCrawlerImpl_40_action {

	
    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxCrawlerImpl_40_action.class);
    
	
	public static void input(String xpath, String text, WebDriver webDriver) throws RxCrawlerException {
		
		//checkInterrupted();

		try {
			RxNode rxnode = RxCrawlerImpl_15_get.getNodeByXpath(xpath, webDriver);
			rxnode.input(text);
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(999, t.getMessage());
		}
	}


	public static void clear(String xpath, WebDriver webDriver) throws RxCrawlerException {
		
		//checkInterrupted();

		try {
			RxNode rxnode = RxCrawlerImpl_15_get.getNodeByXpath(xpath, webDriver);
			rxnode.clear();
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(999, t.getMessage());
		}
	}
	
	
	public static void refresh(WebDriver webDriver) {
		
		try {
			webDriver.navigate().refresh();
			} catch (Exception e) {
			logger.error("webDriver.navigate().refresh()！", e);
		}
		
	}
	

	public static void back(WebDriver webDriver) {
		
		try {
			webDriver.navigate().back();
			} catch (Exception e) {
			logger.error("webDriver.navigate().back()！", e);
		}
		
	}
	
    public static void pressKeys(WebDriver webDriver, CharSequence... keys) {
    	
    	Actions action=new Actions(webDriver);

		action.sendKeys(keys).perform();
		
    }
    
    public static void pressKeys(WebDriver webDriver, int times, CharSequence... keys) {
    	
    	Actions action=new Actions(webDriver);

    	for (int i=0; i < times; i++ ) {
    		
    		action.sendKeys(keys).perform();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}

    }
    
	
}
