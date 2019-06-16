package com.web2data.engine.crawler.browser.impl.a;

import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import com.ruixuesoft.crawler.open.RxCrawlerException;

public class RxCrawlerImpl_93_Cookie {

	
    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxCrawlerImpl_93_Cookie.class);
   
	
	public static JSONObject getAllCookies(WebDriver webDriver) throws RxCrawlerException {
		
		JSONObject jsonObject = new JSONObject();
		
		Set<Cookie> cookies = webDriver.manage().getCookies();
		try {

			for (Cookie c : cookies) {
				
				jsonObject.put(c.getName(), c.getValue());
				
			}
			
		} catch (JSONException e) {
			
			new RxCrawlerException(999, "json转换异常，" + e.getMessage());
			
		}
		return jsonObject;
	}


	public static String getCookieByName(String name, WebDriver webDriver) {
		
		Set<Cookie> cookies = webDriver.manage().getCookies();
		for ( Cookie c : cookies ) {
			
			if ( name.equalsIgnoreCase(c.getName()) ) {
				
				return c.getValue();

			}
		}
		
		return null;
	}


	public static void deleteCookieByName(String name, WebDriver webDriver) {
		
		webDriver.manage().deleteCookieNamed(name);
		
	}


	public static void deleteAllCookies(WebDriver webDriver) {
		
		webDriver.manage().deleteAllCookies();
		
	}
	
	
	public static void addCookie(String name, String value, WebDriver webDriver) {
		Cookie cookie = new Cookie(name, value, "/", null);
		webDriver.manage().addCookie(cookie);
	}


	public static void addCookie(String name, String value, String domainName, String path, Date expireDate, WebDriver webDriver) {
		Cookie cookie = new Cookie(name, value, domainName, path, expireDate);
		webDriver.manage().addCookie(cookie);
		
//		Set<Cookie> cookies = this.webDriver.manage().getCookies();
//		for (Cookie cookieInSet : cookies ) {  
//		      System.out.println(cookieInSet);  
//		}  
		
	}
	
	
}
