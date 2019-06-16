package com.web2data.engine.crawler.browser.impl.a;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.web2data.engine.crawler.browser.impl.RxBrowserImpl;

public class RxCrawlerImpl_60_tab {

	
    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxCrawlerImpl_60_tab.class);
    
    
    public static void closeTabWindow(RxBrowserImpl browser) throws RxCrawlerException {
    	browser.closeTabWindow();
    }
    
	
    public static int getCurTabs(WebDriver webDriver) {
    	
    	int tabs = 1;
    	List<String> tabWindowHandleList = new ArrayList<String> (webDriver.getWindowHandles());
    	
    	if ( tabWindowHandleList != null ) {
        	tabs = tabWindowHandleList.size();	
    	}
    	
    	return tabs;
    }
	

//	public static boolean isNewTabOpened(WebDriver webDriver) throws RxCrawlerException {
//		
//		//checkInterrupted();
//
//		try {
//			if (newTabWindow.getNewTabWindowHandleIndex() > 0) {
//				return true;
//			}
//			return false;
//		} catch (Throwable t) {
//			logger.error("isNewTabOpened异常！", t);
//			throw new RxCrawlerException(999, t.getMessage());
//		}
//	}
//
//
//	public static void switchToNewTab(WebDriver webDriver) throws RxCrawlerException {
//		
//		//checkInterrupted();
//
//		try {
//			int newTabWindowHandleIndex = newTabWindow.getNewTabWindowHandleIndex();
//
//			if (newTabWindowHandleIndex > 0) {
//				webDriver.switchTo().window(newTabWindow.getTabWindowHandleList().get(newTabWindowHandleIndex));
//			}
//		} catch (Throwable t) {
//			logger.error("switchToNewTab异常！", t);
//			throw new RxCrawlerException(999, t.getMessage());
//		}
//		
//	}
//
//
//	public static void closeNewTab(WebDriver webDriver) throws RxCrawlerException {
//		
//		//checkInterrupted();
//
//		try {
//			int newTabWindowHandleIndex = newTabWindow.getNewTabWindowHandleIndex();
//
//			if (newTabWindowHandleIndex > 0) {
//
//				WebDriver tab = webDriver.switchTo().window(newTabWindow.getTabWindowHandleList().get(newTabWindowHandleIndex));
//				tab.close();
//
//				// 回到打开该tab的tab页
//				webDriver.switchTo().window(newTabWindow.getTabWindowHandleList().get(newTabWindowHandleIndex - 1));
//
//				newTabWindow.getTabWindowHandleList().remove(newTabWindowHandleIndex);
//				newTabWindow.setNewTabWindowHandleIndex(newTabWindow.getNewTabWindowHandleIndex() - 1);
//			}
//		} catch (Throwable t) {
//			logger.error("closeNewTab异常！", t);
//			throw new RxCrawlerException(999, t.getMessage());
//		}
//	}

	
}
