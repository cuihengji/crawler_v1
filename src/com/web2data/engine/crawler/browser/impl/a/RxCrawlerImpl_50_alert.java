package com.web2data.engine.crawler.browser.impl.a;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;

import com.ruixuesoft.crawler.open.RxCrawlerException;

public class RxCrawlerImpl_50_alert {

    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxCrawlerImpl_50_alert.class);
    
	

	public static void closeAlert(WebDriver webDriver) throws RxCrawlerException {

		//checkInterrupted();

		//TODO dismiss or accept方法? 测试看看效果
		try {
			//Alert alert = this.webDriver.switchTo().alert();
			Thread.sleep(2000);
			//alert.dismiss();
			webDriver.switchTo().alert().accept();
			webDriver.switchTo().defaultContent();

		} catch (Throwable t) {
			logger.error("closeAlert异常！", t);
			throw new RxCrawlerException(t.getMessage());
		}

	}


	public static void closeAlert(int waitSeconds, WebDriver webDriver) throws RxCrawlerException {
		
		//checkInterrupted();

		try {
			//Alert alert = this.webDriver.switchTo().alert();
			Thread.sleep(waitSeconds*1000);
			//alert.dismiss();
			webDriver.switchTo().alert().accept();
			webDriver.switchTo().defaultContent();
			
		} catch (Throwable t) {
			logger.error("closeAlert异常！", t);
			throw new RxCrawlerException(t.getMessage());
		}
	}
	
	
	public static boolean isAlertDisplayed(WebDriver webDriver) throws RxCrawlerException {
		
		//checkInterrupted();

		boolean isAlertDisplayed = false;
		
		try {
			Thread.sleep(1*1000);
			webDriver.getTitle();
		} catch (UnhandledAlertException e) {
			isAlertDisplayed = true;
			logger.error("UnhandledAlertException异常! ,alert window is displayed.", e);
		} catch (InterruptedException e) {
			logger.error("InterruptedException异常! ,alert window is displayed.", e);
		}
		
		return isAlertDisplayed;
	}


	public static String getAlertText(WebDriver webDriver) throws RxCrawlerException {
		
		//checkInterrupted();

		String message = "NA";
		
		try {
			Alert alert = webDriver.switchTo().alert();
			message = alert.getText();
		} catch (Throwable t) {
			logger.error("getAlertText异常！", t);
			throw new RxCrawlerException(t.getMessage());
		}
		return message;
	}
	
}
