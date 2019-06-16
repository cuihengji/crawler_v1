package com.web2data.engine.crawler.browser.impl.a;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ruixuesoft.crawler.open.RxCrawlerException;

public class RxCrawlerImpl_80_iframe {

	
    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxCrawlerImpl_80_iframe.class);
    
	
//	private static Point getIframeLocationByVerifyImageXpath(String verifyImageXpath, WebDriver webDriver) {
//		
//		checkInterrupted();
//
//        boolean isFindElement = false;
//        
//		try {
//			Point iframeLocation = null;
//			webDriver.switchTo().defaultContent();
//			
//			List<WebElement> iframes = webDriver.findElements(By.tagName("iframe"));
//			// 没有iframe
//			if (iframes == null || iframes.size() == 0) {
//				iframeLocation = new Point(0, 0);
//				return iframeLocation;
//			}
//			
//			for (WebElement iframe : iframes) {
//				
//				webDriver.switchTo().defaultContent();
//				iframeLocation = iframe.getLocation();
//				
//				logger.info("getIframeLocationByVerifyImageXpath-iframeNode.LocationX:" + iframeLocation.getX());
//				
//				logger.info("getIframeLocationByVerifyImageXpath-iframeNode.LocationY:" + iframeLocation.getY());
//				
//				try {
//	                // 在当前frame查找验证码图片
//					webDriver.switchTo().frame(iframe);
//					List<WebElement> elementList = webDriver.findElements(By.xpath(verifyImageXpath));
//					if (elementList != null && elementList.size() > 0) {
//						for (WebElement webElement : elementList) {
//							if (webElement.isDisplayed()) {
//								isFindElement = true;
//								break;
//							}
//						}
//					} else {
//						List<WebElement> subIframes = webDriver.findElements(By.tagName("iframe"));
//						if (subIframes == null || subIframes.size() == 0) {
//							continue;
//						}
//						for (WebElement subIframe : subIframes) {
//							
//							webDriver.switchTo().defaultContent();
//							iframeLocation = iframe.getLocation();
//							
//							logger.info("getIframeLocationByVerifyImageXpath-subIframes-iframeNode.LocationX:" + iframeLocation.getX());
//							
//							logger.info("getIframeLocationByVerifyImageXpath-subIframes-iframeNode.LocationY:" + iframeLocation.getY());
//							
//							
//							try {
//								webDriver.switchTo().frame(subIframe);
//								elementList = webDriver.findElements(By.xpath(verifyImageXpath));
//								if (elementList != null && elementList.size() > 0) {
//									for (WebElement webElement : elementList) {
//										if (webElement.isDisplayed()) {
//											isFindElement = true;
//											break;
//										}
//									}
//									
//									break;
//								}
//							} catch (Exception ex) {
//								continue;
//							}
//						}
//					}
//					
//				} catch (Exception e) {
//					continue;
//				}
//				
//				if (iframeLocation != null) {
//					break;
//				}
//			}
//			
//			if (!isFindElement) {
//				iframeLocation = new Point(0, 0);
//			}
//			
//			return iframeLocation;
//				
//		} catch (Throwable t) {
//			logger.error(t.getMessage(), t);
//			throw new RxCrawlerException(999, t.getMessage());
//		}
//	}
	
}
