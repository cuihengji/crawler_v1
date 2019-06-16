package com.web2data.engine.crawler.browser.impl.a;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ruixuesoft.crawler.open.geetest.GeeTestCracker;

public class RxCrawlerImpl_91_GeeTest {

	
    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxCrawlerImpl_91_GeeTest.class);
   
	
	
	public static boolean verifyGeeTest(WebDriver webDriver) {
		
		//checkInterrupted();

		boolean verifyResult = false;
		try {
			verifyResult = GeeTestCracker.verifyGeeTest(webDriver);
		} catch (Exception e) {
			logger.error("verifyGeeTest Exception 异常！", e);
		}
		
//		accounting.setVerifyCodeTimes(accounting.getVerifyCodeTimes() + 1);
		
		return verifyResult;
	}
	

	public static boolean verifyGeeTest2(WebDriver webDriver) {
		
		//checkInterrupted();

		boolean verifyResult = false;
		try {
			verifyResult = GeeTestCracker.verifyQixinGeeTest(webDriver);
		} catch (Exception e) {
			logger.error("verifyGeeTest Exception 异常！", e);
		}

//		accounting.setVerifyCodeTimes(accounting.getVerifyCodeTimes() + 1);
		
		return verifyResult;
	}
	
}
