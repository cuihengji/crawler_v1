package com.web2data.engine.crawler.browser.node.impl.a;

import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.web2data.engine.crawler.browser.impl.a.ElementPresenceCondition;
import com.web2data.engine.crawler.browser.node.impl.RxNodeImpl;
import com.web2data.open.RxCrawlerException;

public class RxNodeImpl_input {


	public static void input(String text, RxNodeImpl arg) throws RxCrawlerException {
	
//		checkInterrupted();

		try {
			WebDriverWait wait = new WebDriverWait(arg.webDriver, 30);
			WebElement element = wait.until(new ElementPresenceCondition(arg.xpath,	false));
			element.clear();
			//取消null判断,这样使用户知道问题出来Input方法			
//			if (text != null) {
				char[] stringArrayList = text.toCharArray();
				
				
				for (int i = 0; i < stringArrayList.length; i++) {
					int sleepTime = new Random().nextInt(10);
					Thread.sleep(sleepTime);
					element.sendKeys(String.valueOf(stringArrayList[i]));
				}
//			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e);
		}
	}


	public static void clear(RxNodeImpl arg) throws RxCrawlerException {

//		checkInterrupted();

		try {
			
			WebDriverWait wait = new WebDriverWait(arg.webDriver, 10);
			WebElement e = wait.until(new ElementPresenceCondition(arg.xpath, false));
			e.clear();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e.getMessage());
		}
	}
	
    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxNodeImpl_input.class);
}
