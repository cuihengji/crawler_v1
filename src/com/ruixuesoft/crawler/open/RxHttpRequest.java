/**
 * 
 */
package com.ruixuesoft.crawler.open;

import org.json.JSONObject;

/**
 * RxHttpResponse是从selenium 取得的AJAX request log映射.
 *
 */
public interface RxHttpRequest {

	public String getURL();
	
	public String getMethod();
	
	public String getReferrerPolicy();
	
	public JSONObject getAllHeaders();
	
	public String getHeaderByName(String name);
	
	public void setHeader(String name, String value);
	
	public void deleteHeader(String name);
	
	public JSONObject getAllParameters();
	
	public String getParameterByName(String name);
	
	public void setParameter(String name, String value);
	
	public void deleteParameter(String name);
	
	public JSONObject getAllCookies();
	
	public String getCookieByName(String name);
	
	public void setCookie(String name, String value);
	
	public void deleteCookie(String name);

}
