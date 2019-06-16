package com.web2data.engine.crawler.browser.node.impl.a;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.web2data.engine.crawler.browser.node.impl.RxNodeImpl;
import com.web2data.open.RxCrawlerException;
import com.web2data.open.RxNode;

public class RxNodeImpl_xpath {

	
	public static List<RxNode> getNodeListByXpath(WebDriver webDriver, WebElement element, String xpath) throws RxCrawlerException {
		
		//checkInterrupted();

		List<RxNode> elements = new ArrayList<>();
		try {
			List<WebElement> webElementList = element.findElements(By.xpath(xpath));
			if (webElementList != null && webElementList.size() > 0) {

				for (WebElement webElement : webElementList) {
					elements.add(new RxNodeImpl(webDriver, webElement, xpath));
				}
				return elements;
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e.getMessage());
		}
		
		return elements;
	}


	public static RxNode getNodeByXpath(WebDriver webDriver, WebElement element, String xpath) throws RxCrawlerException {
		
		//checkInterrupted();

		WebElement result = null;
		try {
			List<WebElement> webElementList = element.findElements(By.xpath(xpath));
			if (webElementList != null && webElementList.size() > 0) {
				for (WebElement webElement : webElementList) {
					result = webElement;
					break;
				}
			}
		}catch (StaleElementReferenceException se) {
			logger.error("StaleElementReferenceException in getNodeByXpath", se);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				logger.error(e);
			}
			List<WebElement> webElementList = element.findElements(By.xpath(xpath));
			
			if (webElementList != null && webElementList.size() > 0) {
				for (WebElement webElement : webElementList) {
					result = webElement;
					break;
				}
			}
		}
		
		catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e.getMessage());
		}
		
		if (result == null) {
			return null;
		} else {
			return new RxNodeImpl(webDriver, result, xpath);
		}
		
	}
	
	
    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxNodeImpl_xpath.class);
    
}
