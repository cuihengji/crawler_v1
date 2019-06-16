package com.web2data.engine.crawler.htmlunit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;
import com.rkylin.crawler.engine.flood.common.IpProxyResult;
import com.rkylin.crawler.engine.flood.proxy.client.ProxyFacade;
import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.RxHttpContext;
import com.ruixuesoft.crawler.open.impl.RxHttpContextImpl;

import net.sourceforge.htmlunit.corejs.javascript.EvaluatorException;

public class RxCrawlerImpl_96_HtmlUnit {

//    public WebClient getHtmlUnitWebClient() {
//    	
//    	if ( this.webClient == null ) {
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
//    	return this.webClient;
//    }
//
//	@Override
//	public RxHttpContext getRxHttpContext(String url, String ajaxUrl) {
//
//		WebClient webClient = getHtmlUnitWebClient();
//		RxHttpContextImpl httpConext = new RxHttpContextImpl();
//		Map<String, String> cookiesMap = new HashMap<>();
//		try {
//			final URL ajaxURL = new URL(ajaxUrl);
//			
//			webClient.setWebConnection(new WebConnectionWrapper(webClient) {
//				
//				public WebResponse getResponse(WebRequest request) throws IOException {
//
//					WebResponse response = super.getResponse(request);
//					if (request.getUrl().getPath().contains(ajaxURL.getPath())) {
//						logger.info("request-url===" + request.getUrl());
//						logger.info("request-headers===" + request.getAdditionalHeaders());
//						logger.info("request-httpmethod===" + request.getHttpMethod());
//						logger.info("request-parameters===" + request.getRequestParameters());
//						logger.info("request-body===" + request.getRequestBody());
//						
//						httpConext.setHeader(request.getAdditionalHeaders());
//						
//						if (request.getHttpMethod().toString().equalsIgnoreCase("POST")) {
//							httpConext.setParameters(request.getRequestBody());
//						} else {// GET方法
//							httpConext.setParameters(request.getRequestParameters().toString());
//						}
//					}
//
//					//保留Response的部分,以防后续有些数据在Response中获得	
////					if (request.getUrl().getPath().contains(ajaxURL.getPath())) {
////						System.out.println("======================= response ======================");
////						System.out.println("response===" + response.toString());
////						System.out.println("response-contents===" + response.getContentAsString());
////						System.out.println("response-ontentType===" + response.getContentType());
////						System.out.println("response-statusCode===" + response.getStatusCode());
////						System.out.println("response-statusMessage===" + response.getStatusMessage());
////						System.out.println("response-Headers===" + response.getResponseHeaders());
////						System.out.println("response-request===" + response.getWebRequest());
////
////					}
//
//					return response;
//				}
//			});
//
//
//			webClient.getPage(url);
//			
//		
//			Set<com.gargoylesoftware.htmlunit.util.Cookie> htmlUnitCookies = webClient.getCookies(ajaxURL);
//			for (Object cookie : htmlUnitCookies) {
//				com.gargoylesoftware.htmlunit.util.Cookie c = (com.gargoylesoftware.htmlunit.util.Cookie) cookie;
//				cookiesMap.put(c.getName(), c.getValue());
//			}
//			
//			httpConext.setCookie(cookiesMap);
//			
//		} catch (MalformedURLException e1) {
//			logger.error("getRxHttpContext:", e1);
//			throw new RxCrawlerException(999, "请输入正确的AJAX请求URL");
//		} catch (FailingHttpStatusCodeException | IOException e) {
//			logger.error("getRxHttpContext", e);
//		} catch (EvaluatorException e1) {
//			logger.error("getRxHttpContext EvaluatorException:", e1);
//		}
//
//		return httpConext;
//	}
}
