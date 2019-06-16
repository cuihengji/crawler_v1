package com.web2data.engine.crawler.browser.impl.a;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.rkylin.crawler.engine.flood.util.JsonUtil;
import com.rkylin.crawler.engine.flood.websocket.ProxyAddressProducer;
import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.impl.JSAction;

public class RxCrawlerImpl_70_javascript {

    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxCrawlerImpl_70_javascript.class);
    
    
	
	public static Object executeJsScript(String jsScript, WebDriver webDriver) throws RxCrawlerException {

		//checkInterrupted();
		
		try {
    			Object o = ((JavascriptExecutor) webDriver).executeScript(jsScript);	
	    		Thread.sleep(1000);
	    		return o;
			} catch (Throwable t) {
				logger.error(t.getMessage(), t);
				throw new RxCrawlerException(t.getMessage());
			}
	}
	

	public static void performJSAction( int sessionId, String actionName, String actionValue, String selector) {
		
		JSAction jsAction = new JSAction();
		jsAction.setActionName(actionName);
		jsAction.setActionValue(actionValue);
		jsAction.setSelector(selector);
		
		String javaScriptMessage = "";
		try {
			javaScriptMessage = JsonUtil.convertObj2JsonStr(jsAction);
			//chrome frontend extension websocket Id like 10001, 10002, etc... 
			ProxyAddressProducer.sendMessage(String.valueOf(10000+sessionId), javaScriptMessage);
		} catch (Exception e) {
			logger.error("performJSAction", e);
		}
		
	}
}
