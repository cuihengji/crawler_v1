/**
 * 
 */
package com.ruixuesoft.crawler.open;

/**
 * RxHttpNetwork类用来获取发送HTTPClient的参数
 *
 */
public interface RxHttpNetwork {

	public RxHttpRequest getRxHttpRequest();
	
	public RxHttpResponse getRxHttpResponse();
	
}
