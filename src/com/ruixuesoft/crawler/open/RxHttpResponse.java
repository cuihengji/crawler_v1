/**
 * 
 */
package com.ruixuesoft.crawler.open;

import org.json.JSONObject;

/**
 *RxHttpResponse是从selenium 取得的AJAX Response log映射.
 */
public interface RxHttpResponse {

	public String getRemoteAddress();

	public String getPort();

	public JSONObject getAllHeaders();

	public String getHeaderByName(String name);

	public void setHeader(String name, String value);

	public void deleteHeader(String name);

	public int getStatusCode();

	public String body();

}
