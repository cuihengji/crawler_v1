package com.ruixuesoft.crawler.open.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *APP组原始的封装类
 *
 */
public class GetWebElementService {

    private static final transient Logger log = Logger.getLogger ( GetWebElementService.class );

    /**
     * 通过xpath找到显示的网页元素
     * 
     * @param webDriver
     * @param xpath
     * @param isDisplayed 元素是否要求在页面显示
     * @return
     */
    public static final WebElement getElementByXpath ( WebDriver webDriver , String xpath , boolean isDisplayed ) {
        WebElement element = null;
        webDriver.switchTo ().defaultContent ();
        List<WebElement> webElementList = webDriver.findElements ( By.xpath ( xpath ) );
        if ( webElementList != null && webElementList.size () > 0 ) {
            for ( WebElement webElement : webElementList ) {
                // 不要求在页面显示
                if ( ! isDisplayed ) {
                    element = webElement;
                    break;
                }
                else {
                    // 要求在页面显示的时候，要判断该元素是否是被显示的
                    if ( webElement.isDisplayed () ) {
                        element = webElement;
                        break;
                    }
                }
            }
        }
        if ( element == null ) {
            // 如果得不到页面元素，再在所有iframe标签下寻找
            List<WebElement> iframes = webDriver.findElements ( By.tagName ( "iframe" ) );
            if ( iframes == null || iframes.size () == 0 ) {
                return element;
            }
            for ( WebElement iframe : iframes ) {
                try {
                    webDriver.switchTo ().defaultContent ();
                    log.info ( "iframe.id = " + iframe.getAttribute ( "id" ) );
                    webDriver.switchTo ().frame ( iframe );
                    List<WebElement> elementList = webDriver.findElements ( By.xpath ( xpath ) );
                    if ( elementList != null && elementList.size () > 0 ) {
                        for ( WebElement webElement : elementList ) {
                            if ( webElement.isDisplayed () ) {
                                element = webElement;
                                break;
                            }
                        }
                    }
                    else {
                        List<WebElement> subIframes = webDriver.findElements ( By.tagName ( "iframe" ) );
                        if ( subIframes == null || subIframes.size () == 0 ) {
                            continue;
                        }
                        for ( WebElement subIframe : subIframes ) {
                            try {
                                log.info ( "iframe.id = " + subIframe.getAttribute ( "id" ) );
                                webDriver.switchTo ().frame ( subIframe );
                                elementList = webDriver.findElements ( By.xpath ( xpath ) );
                                if ( elementList != null && elementList.size () > 0 ) {
                                    for ( WebElement webElement : elementList ) {
                                        if ( webElement.isDisplayed () ) {
                                            element = webElement;
                                            break;
                                        }
                                    }
                                }
                            }catch( Exception ex ) {
                                log.info ( ex , ex );
                                continue;
                            }
                        }
                    }
                } catch ( Exception e ) {
                    log.info ( e , e );
                    continue;
                }
                if ( element != null ) {
                    break;
                }
            }
        }
        return element;
    }

    /**
     * 通过xpath找到显示的网页元素
     * 
     * @param webDriver
     * @param xpath
     * @return
     */
    public static final List<WebElement> getElementsByXpath ( WebDriver webDriver , String xpath ) {
        List<WebElement> elements = null;
        webDriver.switchTo ().defaultContent ();
        List<WebElement> webElementList = webDriver.findElements ( By.xpath ( xpath ) );
        if ( webElementList != null && webElementList.size () > 0 ) {
            elements = webElementList;
            return elements;
        }
        List<WebElement> iframes = webDriver.findElements ( By.tagName ( "iframe" ) );
        if ( iframes == null || iframes.size () == 0 ) {
            return elements;
        }
        for ( WebElement iframe : iframes ) {
            try {
                webDriver.switchTo ().defaultContent ();
                //log.info ( "iframe.id = " + iframe.getAttribute ( "id" ) );
                webDriver.switchTo ().frame ( iframe );
                List<WebElement> elementList = webDriver.findElements ( By.xpath ( xpath ) );
                if ( elementList != null && elementList.size () > 0 ) {
                    elements = elementList;
                    break;
                }                    
                else {
                    List<WebElement> subIframes = webDriver.findElements ( By.tagName ( "iframe" ) );
                    for ( WebElement subIframe : subIframes ) {
                        try {
                            //log.info ( "iframe.id = " + subIframe.getAttribute ( "id" ) );
                            webDriver.switchTo ().frame ( subIframe );
                            elementList = webDriver.findElements ( By.xpath ( xpath ) );
                            if ( elementList != null && elementList.size () > 0 ) {
                                elements = elementList;
                                break;
                            }
                        }catch( Exception ex ) {
                            log.info ( ex , ex );
                            continue;
                        }
                    }
                }
            } catch ( Exception e ) {
                log.info ( e , e );
                continue;
            }
        }
        return elements;
    }

    /**
     * 通过CSS找到显示的网页元素
     * 
     * @param webDriver
     * @param css
     * @return
     */
    public static final WebElement getElementByCssSelector ( WebDriver webDriver , String css ) {
        WebElement element = null;
        List<WebElement> webElementList = webDriver.findElements ( By.cssSelector ( css ) );
        if ( webElementList != null && webElementList.size () > 0 ) {
            for ( WebElement webElement : webElementList ) {
                if ( webElement.isDisplayed () ) {
                    element = webElement;
                    break;
                }
            }
        }
        return element;
    }
    
    public static List<String> captureArray(WebDriver webDriver,String xpath,String attribute){
    	List<String> result = new ArrayList<String>();
    	List<WebElement> webElementlist = GetWebElementService.getElementsByXpath ( webDriver , xpath );
    	if(webElementlist == null || webElementlist.size()==0){
    		return result;	
    	}
    	for(WebElement webElement : webElementlist){
    		String value = getElementAttibute(webElement,attribute);
    		result.add(value);
    	}
    	return result;
    }
    
    public static List<Map<String,String>> captureArray(WebDriver webDriver,String xpath,String[] attributes){
    	List<Map<String,String>> result = new ArrayList<Map<String,String>>();
    	List<WebElement> webElementlist = GetWebElementService.getElementsByXpath ( webDriver , xpath );
    	if(webElementlist == null || webElementlist.size()==0){
    		return result;	
    	}
    	for(WebElement webElement : webElementlist){
    		Map<String,String> map = new HashMap<String,String>();
    		for(String name : attributes){
    			String value = getElementAttibute(webElement,name);
    			map.put(name, value);
    		}
    		result.add(map);
    	}
    	return result;
    }
    
    public static String getElementAttibute(WebElement webElement,String attribute){
    	String value = null;
		try {
			if("text".equalsIgnoreCase(attribute)){
				value = webElement.getText();
			}
			else{
				value = webElement.getAttribute(attribute);	
			}
		} catch (Exception e) {
			log.info ( "获取元素属性时发生错误" , e );
			e.printStackTrace();
		}
		return value;	
    }
    
    public static String captureSingle(WebDriver webDriver,String xpath,String attribute){
    	 WebElement element = getElementByXpath(webDriver, xpath, true);
    	 if(element == null){
    		 return "";
    	 }
    	 String result = getElementAttibute(element,attribute);
    	 if(result == null){
    		 result = "";
    	 }
    	 return result;	
    }

}
