package com.ruixuesoft.crawler.open.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.rkylin.crawler.engine.flood.util.StringUtil;
import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.RxNode;

public class RxNodeImpl implements RxNode {

	private static final Logger logger = Logger.getLogger(RxNodeImpl.class);

	private WebDriver webDriver;
	private WebElement element;
	private String xpath;
	private Accounting accounting;
	private TabWindow newTabWindow;

	public RxNodeImpl(WebDriver webDriver, WebElement element, String xpath, Accounting accounting, TabWindow newTabWindow) {
		super();
		this.webDriver = webDriver;
		this.element = element;
		this.xpath = xpath;
		this.accounting = accounting;
		this.newTabWindow = newTabWindow;
	}

	@Override
	public void click() {
		
		checkInterrupted();
		
		try{
		int windowHandleSize = 0;
        if (newTabWindow.getTabWindowHandleList() == null) {
        	List<String> tabWindowHandleList = new ArrayList<String> (webDriver.getWindowHandles());
        	windowHandleSize  = tabWindowHandleList.size();
        }
		if (element != null && element.isEnabled()) {
			((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", element);
//
//				WebDriverWait wait = new WebDriverWait(webDriver, 30);
//				wait.until(ExpectedConditions.elementToBeClickable(element));
//				
//				element.click();
		}
		
		Thread.sleep(3000);

		List<String> tabWindowHandleListAfterClick = new ArrayList<String> (webDriver.getWindowHandles());
    	newTabWindow.setTabWindowHandleList(tabWindowHandleListAfterClick);
    	int newWindowHandleSize = tabWindowHandleListAfterClick.size();
    	
		if (newWindowHandleSize > windowHandleSize) {
        	newTabWindow.setNewTabWindowHandleIndex(newWindowHandleSize - 1);
		} else {
        	newTabWindow.setNewTabWindowHandleIndex(newWindowHandleSize > 1 ? (tabWindowHandleListAfterClick.size() - 1) : 0);
		}
		
		accounting.setPages(accounting.getPages() + 1);
		}catch(Exception e){
			logger.error("click() exception: ", e);
//			RxCrawlerImpl crawler = new RxCrawlerImpl(webDriver, accounting);
//			RxNode restoredNode = crawler.getNodeByXpath(getXpath());
//			restoredNode.click();
//			logger.info("---------------------------------- restoredNode" + restoredNode);
			throw new RxCrawlerException(e);
		}
	}

	
	@Override
	public void click(int waitInSeconds) {
		
		checkInterrupted();
		
		try{
		int windowHandleSize = 0;
        if (newTabWindow.getTabWindowHandleList() == null) {
        	List<String> tabWindowHandleList = new ArrayList<String> (webDriver.getWindowHandles());
        	windowHandleSize  = tabWindowHandleList.size();
        }
        
		if (element != null && element.isEnabled()) {
				WebDriverWait wait = new WebDriverWait(webDriver, 10);
				wait.until(ExpectedConditions.elementToBeClickable(element));
				element.click();
		}
		
		Thread.sleep(waitInSeconds*1000);
		List<String> tabWindowHandleListAfterClick = new ArrayList<String> (webDriver.getWindowHandles());
    	newTabWindow.setTabWindowHandleList(tabWindowHandleListAfterClick);
    	int newWindowHandleSize = tabWindowHandleListAfterClick.size();
    	
		if (newWindowHandleSize > windowHandleSize) {
        	newTabWindow.setNewTabWindowHandleIndex(newWindowHandleSize - 1);
		} else {
        	newTabWindow.setNewTabWindowHandleIndex(newWindowHandleSize > 1 ? (tabWindowHandleListAfterClick.size() - 1) : 0);
		}
		
		accounting.setPages(accounting.getPages() + 1);
		}catch(Exception e){
			logger.error("click() exception: ", e);
			throw new RxCrawlerException(e);
		}
	}
	
	
	@Override
	public void click(String xPath) {
		
		checkInterrupted();
		
		try{
		int windowHandleSize = 0;
        if (newTabWindow.getTabWindowHandleList() == null) {
        	List<String> tabWindowHandleList = new ArrayList<String> (webDriver.getWindowHandles());
        	windowHandleSize  = tabWindowHandleList.size();
        }
        
		if (element != null && element.isEnabled()) {
				WebDriverWait wait = new WebDriverWait(webDriver, 30);
				wait.until(ExpectedConditions.elementToBeClickable(element));
				element.click();
				
				//最多等待30秒,跳转到下一个页面后,等待指定的xPath元素出现
		        WebDriverWait xPathElementait = new WebDriverWait(this.webDriver, 30);
		        xPathElementait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
		}
		
		List<String> tabWindowHandleListAfterClick = new ArrayList<String> (webDriver.getWindowHandles());
    	newTabWindow.setTabWindowHandleList(tabWindowHandleListAfterClick);
    	int newWindowHandleSize = tabWindowHandleListAfterClick.size();
    	
		if (newWindowHandleSize > windowHandleSize) {
        	newTabWindow.setNewTabWindowHandleIndex(newWindowHandleSize - 1);
		} else {
        	newTabWindow.setNewTabWindowHandleIndex(newWindowHandleSize > 1 ? (tabWindowHandleListAfterClick.size() - 1) : 0);
		}
		
		accounting.setPages(accounting.getPages() + 1);
		}catch(Exception e){
			logger.error("click() exception: ", e);
			throw new RxCrawlerException(e);
		}
	}
	
	
	@Override
	public void input(String text) throws RxCrawlerException {
	
		checkInterrupted();

		try {
			WebDriverWait wait = new WebDriverWait(this.webDriver, 30);
			WebElement element = wait.until(new ElementPresenceCondition(xpath,	false));
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

	@Override
	public void clear() throws RxCrawlerException {

		checkInterrupted();

		try {
			
			WebDriverWait wait = new WebDriverWait(this.webDriver, 10);
			WebElement e = wait.until(new ElementPresenceCondition(xpath, false));
			e.clear();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e.getMessage());
		}
	}

	@Override
	public String getAttribute(String attribute) throws RxCrawlerException {
		
		checkInterrupted();

		try {
			return StringUtil.removeSpecialChar(this.element.getAttribute(attribute));
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e);
//			try {
//				logger.error("getText() exception: ", e);
//				RxCrawlerImpl crawler = new RxCrawlerImpl(webDriver, accounting);
//				RxNode restoredNode = crawler.getNodeByXpath(getXpath());
//				return StringUtil.removeSpecialChar(restoredNode.getAttribute(attribute));
//			} catch (Exception e2) {
//				throw new RxCrawlerException(e.getMessage());
//			}
		}
	}

	@Override
	public String getText() throws RxCrawlerException {

		checkInterrupted();

		try {
			return StringUtil.removeSpecialChar(this.element.getText());
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e);
//			try {
//				logger.error("getText() exception: ", e);
//				RxCrawlerImpl crawler = new RxCrawlerImpl(webDriver, accounting);
//				RxNode restoredNode = crawler.getNodeByXpath(getXpath());
//				return StringUtil.removeSpecialChar(restoredNode.getText());
//			} catch (Exception e2) {
//				
//			}
		}
	}
	
	@Override
	public String getRawText() throws RxCrawlerException {

		checkInterrupted();

		try {
			return this.element.getText();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e);
//			try {
//				logger.error("getText() exception: ", e);
//				RxCrawlerImpl crawler = new RxCrawlerImpl(webDriver, accounting);
//				RxNode restoredNode = crawler.getNodeByXpath(getXpath());
//				return StringUtil.removeSpecialChar(restoredNode.getText());
//			} catch (Exception e2) {
//				
//			}
		}
	}
	

	public WebElement getElement() {
		return element;
	}

	public void setElement(WebElement element) {
		this.element = element;
	}

	@Override
	public List<RxNode> getNodeListByXpath(String xpath) throws RxCrawlerException {
		
		checkInterrupted();

		List<RxNode> elements = new ArrayList<>();
		try {
			List<WebElement> webElementList = this.element.findElements(By.xpath(xpath));
			if (webElementList != null && webElementList.size() > 0) {

				for (WebElement webElement : webElementList) {
					elements.add(new RxNodeImpl(this.webDriver, webElement, xpath, accounting, newTabWindow));
				}
				return elements;
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e.getMessage());
		}
		
		return elements;
	}


	@Override
	public RxNode getNodeByXpath(String xpath) throws RxCrawlerException {
		
		checkInterrupted();

		WebElement element = null;
		try {
			List<WebElement> webElementList = this.element.findElements(By.xpath(xpath));
			if (webElementList != null && webElementList.size() > 0) {
				for (WebElement webElement : webElementList) {
					element = webElement;
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
			List<WebElement> webElementList = this.element.findElements(By.xpath(xpath));
			
			if (webElementList != null && webElementList.size() > 0) {
				for (WebElement webElement : webElementList) {
					element = webElement;
					break;
				}
			}
		}
		
		catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e.getMessage());
		}
		
		if (element == null) {
			return null;
		} else {
			return new RxNodeImpl(this.webDriver, element, xpath, accounting, newTabWindow);
		}
		
	}

	
	public int getLocationX() throws RxCrawlerException {
		
		
		return this.element.getLocation().getX();
	}

	public int getLocationY() throws RxCrawlerException {
		
		return this.element.getLocation().getY();
	}

	@Override
	public boolean isDisplayed() throws RxCrawlerException {
		
		checkInterrupted();

		boolean isDisplayed  = false;
		
		try {
			isDisplayed = this.element.isDisplayed();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return isDisplayed;
	}

	@Override
	public void moveToNode() throws RxCrawlerException {
		
		checkInterrupted();

		try {
			Actions actions = new Actions(webDriver);
			actions.moveToElement(this.element).perform();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e.getMessage());
		}
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	
	public void checkInterrupted() {

		if (Thread.currentThread().isInterrupted()) {
			throw new RxCrawlerException("该任务已被用户请求强制终止！");
		}
	}

	@Override
	public void move(int xOffset, int yOffset) throws RxCrawlerException {

		checkInterrupted();

		try {
			Actions actions = new Actions(webDriver);
//			new Actions(webDriver).clickAndHold(element).perform();
			actions.moveToElement(this.element, xOffset, yOffset).perform();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e.getMessage());
		}
	}

}
