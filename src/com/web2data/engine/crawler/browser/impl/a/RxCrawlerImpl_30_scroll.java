package com.web2data.engine.crawler.browser.impl.a;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;

import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.RxNode;
import com.ruixuesoft.crawler.open.impl.RxNodeImpl;

public class RxCrawlerImpl_30_scroll {

	
    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxCrawlerImpl_30_scroll.class);
    
	

	public static void scrollToTop(WebDriver webDriver) throws RxCrawlerException {
		
		//checkInterrupted();

		try {
			((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, 0)");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(t.getMessage());
		}
	}


	public static void scrollToBottom(WebDriver webDriver) throws RxCrawlerException {

		//checkInterrupted();

		try {
			((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(t.getMessage());
		}
	}


	public static void scroll(int pixels, WebDriver webDriver) throws RxCrawlerException {
		
		//checkInterrupted();

		try {
			String javaScripit = "window.scrollTo(0, " + pixels + ")";
			((JavascriptExecutor) webDriver).executeScript(javaScripit);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(t.getMessage());
		}
	}


	public static void scrollTo(RxNode node) throws RxCrawlerException {
		
		//checkInterrupted();

		try {
			WebElement webElement = ((RxNodeImpl) node).getElement();

			Coordinates coordinate = ((Locatable) webElement).getCoordinates();
			coordinate.onPage();
			coordinate.inViewPort();

		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(t.getMessage());
		}
	}
	
//	@Override
//	public void inputVerifyCode(String verifyImageXpath, String verifyInputTextXpath, String iframeXpath)  throws RxCrawlerException{
//		try {
//			int x = 0, y = 0;
//			
//			if(iframeXpath != null && iframeXpath.trim().length() > 0)
//			{
//				RxNodeImpl iframeNode = (RxNodeImpl) getNodeByXpath(iframeXpath);
//				x = iframeNode.getLocationX();
//				y = iframeNode.getLocationY();
//
//			}
//			
//			logger.info("iframeNode.LocationX:" + x);
//			
//			logger.info("iframeNode.LocationY:" + y);
//			
//			VerifyCodeHelper.inputVerifyCode(webDriver, verifyImageXpath, verifyInputTextXpath, x, y);
//		} catch (Throwable t) {
//			logger.error("selectVerifyCode异常！", t);
//			throw new RxCrawlerException(t.getMessage());
//		}
//	}
	
}
