package com.web2data.engine.crawler.browser.impl.a;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.thirdparty.VerifyCodeHelper;

public class RxCrawlerImpl_30_VerifyCode {

	
    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxCrawlerImpl_30_VerifyCode.class);
    
    
	
	public static void inputVerifyCode(String verifyImageXpath, String verifyInputTextXpath, WebDriver webDriver)  throws RxCrawlerException{
		
		//checkInterrupted();

		try {
			
			int x = 0, y = 0;
			
			Point iframeLocation = getIframeLocationByVerifyImageXpath(verifyImageXpath, webDriver);
			if( iframeLocation != null )
			{
				x = iframeLocation.getX();
				y = iframeLocation.getY();
			}
			
			logger.info("inputVerifyCode-iframeNode.LocationX:" + x);
			logger.info("inputVerifyCode-iframeNode.LocationY:" + y);
			
			VerifyCodeHelper.inputVerifyCode(webDriver, verifyImageXpath, verifyInputTextXpath, x, y);
			
			//accounting.setVerifyCodeTimes(accounting.getVerifyCodeTimes() + 1);

		} catch (Throwable t) {
			logger.error("selectVerifyCode异常！", t);
			throw new RxCrawlerException(t.getMessage());
		}
	}
	
//	@Override
//	public void selectVerifyCode(String verifyImageXpath, String verifyButtonXpath, String iframeXpath)  throws RxCrawlerException {
//		try {
//			int x = 0, y = 0;
//			
//			if(iframeXpath != null && iframeXpath.trim().length() > 0)
//			{
//				RxNodeImpl iframeNode = (RxNodeImpl) getNodeByXpath(iframeXpath);
//				x = iframeNode.getLocationX();
//				y = iframeNode.getLocationY();
//			}
//			
//			VerifyCodeHelper.selectVerifyCode(webDriver, verifyImageXpath, verifyButtonXpath, x, y);
//		} catch (Throwable t) {
//			logger.error("selectVerifyCode异常！", t);
//			throw new RxCrawlerException(t.getMessage());
//		}
//	}
	

	public static void selectVerifyCode(String verifyImageXpath, String verifyButtonXpath, WebDriver webDriver)  throws RxCrawlerException{
		
		//checkInterrupted();

		try {
			
			int x = 0, y = 0;
			
			Point iframeLocation = getIframeLocationByVerifyImageXpath(verifyImageXpath, webDriver);
			if( iframeLocation != null )
			{
				x = iframeLocation.getX();
				y = iframeLocation.getY();
			}
			
			logger.info("iframeNode.LocationX:" + x);
			logger.info("iframeNode.LocationY:" + y);
			
			VerifyCodeHelper.selectVerifyCode(webDriver, verifyImageXpath, verifyButtonXpath, x, y);
			
			//accounting.setVerifyCodeTimes(accounting.getVerifyCodeTimes() + 1);

		} catch (Throwable t) {
			logger.error("selectVerifyCode异常！", t);
			throw new RxCrawlerException(t.getMessage());
		}
		
	}
	
	
	private static Point getIframeLocationByVerifyImageXpath(String verifyImageXpath, WebDriver webDriver) {
		
		//checkInterrupted();

        boolean isFindElement = false;
        
		try {
			Point iframeLocation = null;
			webDriver.switchTo().defaultContent();
			
			List<WebElement> iframes = webDriver.findElements(By.tagName("iframe"));
			// 没有iframe
			if (iframes == null || iframes.size() == 0) {
				iframeLocation = new Point(0, 0);
				return iframeLocation;
			}
			
			for (WebElement iframe : iframes) {
				
				webDriver.switchTo().defaultContent();
				iframeLocation = iframe.getLocation();
				
				logger.info("getIframeLocationByVerifyImageXpath-iframeNode.LocationX:" + iframeLocation.getX());
				
				logger.info("getIframeLocationByVerifyImageXpath-iframeNode.LocationY:" + iframeLocation.getY());
				
				try {
	                // 在当前frame查找验证码图片
					webDriver.switchTo().frame(iframe);
					List<WebElement> elementList = webDriver.findElements(By.xpath(verifyImageXpath));
					if (elementList != null && elementList.size() > 0) {
						for (WebElement webElement : elementList) {
							if (webElement.isDisplayed()) {
								isFindElement = true;
								break;
							}
						}
					} else {
						List<WebElement> subIframes = webDriver.findElements(By.tagName("iframe"));
						if (subIframes == null || subIframes.size() == 0) {
							continue;
						}
						for (WebElement subIframe : subIframes) {
							
							webDriver.switchTo().defaultContent();
							iframeLocation = iframe.getLocation();
							
							logger.info("getIframeLocationByVerifyImageXpath-subIframes-iframeNode.LocationX:" + iframeLocation.getX());
							
							logger.info("getIframeLocationByVerifyImageXpath-subIframes-iframeNode.LocationY:" + iframeLocation.getY());
							
							
							try {
								webDriver.switchTo().frame(subIframe);
								elementList = webDriver.findElements(By.xpath(verifyImageXpath));
								if (elementList != null && elementList.size() > 0) {
									for (WebElement webElement : elementList) {
										if (webElement.isDisplayed()) {
											isFindElement = true;
											break;
										}
									}
									
									break;
								}
							} catch (Exception ex) {
								continue;
							}
						}
					}
					
				} catch (Exception e) {
					continue;
				}
				
				if (iframeLocation != null) {
					break;
				}
			}
			
			if (!isFindElement) {
				iframeLocation = new Point(0, 0);
			}
			
			return iframeLocation;
				
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(999, t.getMessage());
		}
	}
	
}
