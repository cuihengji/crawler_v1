package com.web2data.engine.crawler.browser.impl.a;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.Website;
import com.youtu.TencentRecognition;

public class RxCrawlerImpl_90_image {

	
    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxCrawlerImpl_90_image.class);
    
	
	public static String imageRecognize(String imageXpath, WebDriver webDriver) throws RxCrawlerException {
		
		//checkInterrupted();

		String imageString = "NA";
		
		try {
			imageString = TencentRecognition.imageRecognize(webDriver, imageXpath, 0, 0);
		} catch (Exception e) {
			logger.error("tencentRecogize", e);
		}
		
//		accounting.setVerifyCodeTimes(accounting.getVerifyCodeTimes() + 1);
		return imageString;
	}
	
	
	public static String imageRecogize(String imageXpath, WebDriver webDriver) throws RxCrawlerException {
		return imageRecognize(imageXpath, webDriver);
	}
	
	
	public static String phoneImageRecognize(String imageXpath, WebDriver webDriver) throws RxCrawlerException {
		
		//checkInterrupted();

		String imageString = "NA";
		
		try {
			imageString = TencentRecognition.phoneImageRecognize(webDriver, imageXpath, 0, 0);
		} catch (Exception e) {
			logger.error("phoneImageRecogize:", e);
		}
		
//		accounting.setVerifyCodeTimes(accounting.getVerifyCodeTimes() + 1);
		
		return imageString;
	}
	

	public static String phoneImageRecogize(String imageXpath, WebDriver webDriver) throws RxCrawlerException {
		return phoneImageRecognize(imageXpath, webDriver);
	}
	
	
	public static String phoneImageRecognize(String imageXpath, Website website, WebDriver webDriver) throws RxCrawlerException {
		
		//checkInterrupted();

		String imageString = "NA";
		
		try {
			imageString = TencentRecognition.phoneImageRecognize(webDriver, imageXpath, website);
		} catch (Exception e) {
			logger.error("phoneImageRecogize", e);
		}
		
//		accounting.setVerifyCodeTimes(accounting.getVerifyCodeTimes() + 1);
		
		return imageString;
	}
	

	public static String phoneImageRecogize(String imageXpath, Website website, WebDriver webDriver) throws RxCrawlerException {
		return phoneImageRecognize(imageXpath, website, webDriver);
	}
	
	
	//目前UU打码已经迁移到自己研发的顺企网图片识别模块
	public static String uuPictureRecognition(String imageSrc, WebDriver webDriver) throws RxCrawlerException {

		//checkInterrupted();
		
		String imageString = "NA";
		
		try {
			imageString = TencentRecognition.phoneImageRecognize(webDriver, imageSrc, Website.ShunQi);
		} catch (Exception e) {
			logger.error("uuPictureRecognition异常", e);
			throw new RxCrawlerException(999, e.getMessage());
		}
		
		return imageString;
	}
	
}
