package com.web2data.engine.crawler.browser.node.impl.a;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.web2data._global.SessionThreadLocal;
import com.web2data.engine.crawler.browser.node.impl.RxNodeImpl;
import com.web2data.open.RxCrawlerException;

public class RxNodeImpl_click {

	
	public static void click(RxNodeImpl arg) {
		
		//checkInterrupted();
		
		try{
			//int windowHandleSize = 0;
	        //if (newTabWindow.getTabWindowHandleList() == null) {
	        //	List<String> tabWindowHandleList = new ArrayList<String> (webDriver.getWindowHandles());
	        //	windowHandleSize  = tabWindowHandleList.size();
	        //}
			if (arg.element != null && arg.element.isEnabled()) {
				((JavascriptExecutor) arg.webDriver).executeScript("arguments[0].click();", arg.element);
	//
	//				WebDriverWait wait = new WebDriverWait(webDriver, 30);
	//				wait.until(ExpectedConditions.elementToBeClickable(element));
	//				
	//				element.click();
			}
			
			
			// 建议休息几秒，等新tab页面能完全的渲染出来
			//Thread.sleep(3000);
			
			
	//
	//		List<String> tabWindowHandleListAfterClick = new ArrayList<String> (webDriver.getWindowHandles());
	//    	newTabWindow.setTabWindowHandleList(tabWindowHandleListAfterClick);
	//    	int newWindowHandleSize = tabWindowHandleListAfterClick.size();
	//    	
	//		if (newWindowHandleSize > windowHandleSize) {
	//        	newTabWindow.setNewTabWindowHandleIndex(newWindowHandleSize - 1);
	//		} else {
	//        	newTabWindow.setNewTabWindowHandleIndex(newWindowHandleSize > 1 ? (tabWindowHandleListAfterClick.size() - 1) : 0);
	//		}
			
			// 重新取得浏览器的 TabWindowList
			SessionThreadLocal.getCurrentSession()._BrowserInfra.refreshTabWindows();;
			
			//accounting.setPages(accounting.getPages() + 1);
		}catch(Exception e){
			//logger.error("click() exception: ", e);
//			RxCrawlerImpl crawler = new RxCrawlerImpl(webDriver, accounting);
//			RxNode restoredNode = crawler.getNodeByXpath(getXpath());
//			restoredNode.click();
//			logger.info("---------------------------------- restoredNode" + restoredNode);
			throw new RxCrawlerException(e);
		}
	}

	
	public static void click(int waitInSeconds, RxNodeImpl arg) {
		
//		checkInterrupted();
//		
//		try{
//		int windowHandleSize = 0;
//        if (newTabWindow.getTabWindowHandleList() == null) {
//        	List<String> tabWindowHandleList = new ArrayList<String> (webDriver.getWindowHandles());
//        	windowHandleSize  = tabWindowHandleList.size();
//        }
//        
//		if (element != null && element.isEnabled()) {
//				WebDriverWait wait = new WebDriverWait(webDriver, 10);
//				wait.until(ExpectedConditions.elementToBeClickable(element));
//				element.click();
//		}
//		
//		Thread.sleep(waitInSeconds*1000);
//		List<String> tabWindowHandleListAfterClick = new ArrayList<String> (webDriver.getWindowHandles());
//    	newTabWindow.setTabWindowHandleList(tabWindowHandleListAfterClick);
//    	int newWindowHandleSize = tabWindowHandleListAfterClick.size();
//    	
//		if (newWindowHandleSize > windowHandleSize) {
//        	newTabWindow.setNewTabWindowHandleIndex(newWindowHandleSize - 1);
//		} else {
//        	newTabWindow.setNewTabWindowHandleIndex(newWindowHandleSize > 1 ? (tabWindowHandleListAfterClick.size() - 1) : 0);
//		}
//		
//		//accounting.setPages(accounting.getPages() + 1);
//		}catch(Exception e){
//			logger.error("click() exception: ", e);
//			throw new RxCrawlerException(e);
//		}
	}
	
	
	public static void click(String xPath, RxNodeImpl arg) {
		
//		checkInterrupted();
//		
//		try{
//		int windowHandleSize = 0;
//        if (newTabWindow.getTabWindowHandleList() == null) {
//        	List<String> tabWindowHandleList = new ArrayList<String> (webDriver.getWindowHandles());
//        	windowHandleSize  = tabWindowHandleList.size();
//        }
//        
//		if (element != null && element.isEnabled()) {
//				WebDriverWait wait = new WebDriverWait(webDriver, 30);
//				wait.until(ExpectedConditions.elementToBeClickable(element));
//				element.click();
//				
//				//最多等待30秒,跳转到下一个页面后,等待指定的xPath元素出现
//		        WebDriverWait xPathElementait = new WebDriverWait(this.webDriver, 30);
//		        xPathElementait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
//		}
//		
//		List<String> tabWindowHandleListAfterClick = new ArrayList<String> (webDriver.getWindowHandles());
//    	newTabWindow.setTabWindowHandleList(tabWindowHandleListAfterClick);
//    	int newWindowHandleSize = tabWindowHandleListAfterClick.size();
//    	
//		if (newWindowHandleSize > windowHandleSize) {
//        	newTabWindow.setNewTabWindowHandleIndex(newWindowHandleSize - 1);
//		} else {
//        	newTabWindow.setNewTabWindowHandleIndex(newWindowHandleSize > 1 ? (tabWindowHandleListAfterClick.size() - 1) : 0);
//		}
//		
//		accounting.setPages(accounting.getPages() + 1);
//		}catch(Exception e){
//			logger.error("click() exception: ", e);
//			throw new RxCrawlerException(e);
//		}
	}
	
	
    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxNodeImpl_click.class);
    
}
