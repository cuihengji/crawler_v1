package com.web2data.engine.crawler.browser.impl.a;

import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.rkylin.crawler.engine.flood.common.IpProxyResult;
import com.rkylin.crawler.engine.flood.proxy.client.ProxyFacade;
import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.RxSimpleCrawler;
import com.ruixuesoft.crawler.open.impl.RxSimpleCrawlerImpl;

public class RxCrawlerImpl_00_test {

//	@Override
//	public RxSimpleCrawler getSimpleCrawler() throws RxCrawlerException {
//		
//		if ( this.webClient == null ) {
//    		
//        	this.webClient = new WebClient();
//    		
//        	if ( this.isUsingProxy ) {
//        		
//        		IpProxyResult ipProxyInfo = ProxyFacade.getInstance().fetchIpProxy(1, 1);
//            	ProxyConfig proxyConfig = new ProxyConfig(ipProxyInfo.getIp(), ipProxyInfo.getPort());
//            	final DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
//            	credentialsProvider.addCredentials(ipProxyInfo.getUser_name(), ipProxyInfo.getPass_word());
//            	this.webClient.getOptions().setProxyConfig(proxyConfig);
//        	}
//        	
//        	// 支持js
//        	this.webClient.getOptions().setJavaScriptEnabled(true);
//        	this.webClient.getOptions().setThrowExceptionOnScriptError(false);
//        	this.webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//        	
//        	// 非200code
//        	this.webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
//        	
//        	// 屏蔽css
//        	this.webClient.getOptions().setCssEnabled(true);
//        	
//        	// 支持https
//        	this.webClient.getOptions().setUseInsecureSSL(true);
//        	
//        	// 设置超时时间
//        	this.webClient.getOptions().setTimeout(30000);
//        	
//        	// 支持cookie
//        	this.webClient.getCookieManager().setCookiesEnabled(true);
//        	
//    	}
//
//    	
//    	return new RxSimpleCrawlerImpl(this.webClient);
//	}
}
