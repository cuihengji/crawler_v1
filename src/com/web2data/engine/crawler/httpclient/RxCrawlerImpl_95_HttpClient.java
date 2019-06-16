package com.web2data.engine.crawler.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;

import com.rkylin.crawler.engine.flood.common.IpProxyResult;
import com.rkylin.crawler.engine.flood.proxy.client.ProxyFacade;
import com.ruixuesoft.crawler.open.RxHttpNode;
import com.ruixuesoft.crawler.open.impl.RxHttpNodeImpl;

public class RxCrawlerImpl_95_HttpClient {

//    public CloseableHttpClient getHttpClient(){
//    	
//    	if ( this.httpClient == null) {
//    		
//    		HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
//
//    			public boolean retryRequest(IOException exception,
//    					int executionCount, HttpContext context) {
//    				// 重试三次,网络连接失败
//    				if (executionCount > 3) {
//    					return false;
//    				}
//
//    				try {
//    					Thread.sleep(2000);
//    				} catch (InterruptedException e) {
//    					e.printStackTrace();
//    				}
//    				return true;
//    			}
//    		};
//      
//        	
//        	if ( this.isUsingProxy ) {
//        		
//        		IpProxyResult ipProxyInfo = ProxyFacade.getInstance().fetchIpProxy(1, 1);
//    			CredentialsProvider credsProvider = new BasicCredentialsProvider();
//    			credsProvider.setCredentials(new AuthScope(ipProxyInfo.getIp(), ipProxyInfo.getPort()), new UsernamePasswordCredentials(ipProxyInfo.getUser_name(), ipProxyInfo.getPass_word()));
//    			this.httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).setRetryHandler(retryHandler).build();
//        	
//        	}
//
//        	this.httpClient = HttpClients.custom().setRetryHandler(retryHandler).build();
//        	
//    	}
//
//    	return this.httpClient;
//    }
//    
//    
//    
//	@Override
//	public RxHttpNode doHttpGet(String url) {
//		
//		return doHttpGet(url, null, null);
//	}
//
//
//	@Override
//	public RxHttpNode doHttpGet(String url, Map<String, String> header, Map<String, String> cookie) {
//		
//		RxHttpNodeImpl rxHttpNode = null;
//		
//		CloseableHttpClient httpclient = this.getHttpClient();
//		HttpGet get = new HttpGet(url);
//		logger.info("doHttpGet: " + url);
//
//		if ( header != null && header.size() > 0 ) {
//            for (Entry<String, String> entry : header.entrySet()) {
//            	get.addHeader(entry.getKey(), entry.getValue());
//            }
//        }
//        
//		if (cookie != null && cookie.size() > 0) {
//	        StringBuffer cookies = new StringBuffer();
//	        for (Entry<String, String> entry : cookie.entrySet()) {
//	        	cookies.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
//	        }
//	        get.addHeader("Cookie", cookies.toString());
//		}
//		
//		try (CloseableHttpResponse response = httpclient.execute(get)) {
//			String res = EntityUtils.toString(response.getEntity(), "utf-8");
//			response.close();
//
//			this.pageSource = res;
//			// 解析网页 得到文档对象
//			this.httpDocument = Jsoup.parse(res);
//			
//			rxHttpNode = new RxHttpNodeImpl(httpDocument);
//			rxHttpNode.setPageSource(res);
//			
//		} catch (Exception e) {
//			logger.error("doHttpGet", e);
//		}
//		
//		return rxHttpNode;
//	}
//
//	
//	@Override
//	public RxHttpNode doHttpPost(String url, Map<String, String> params) {
//
//		return doHttpPost(url, params, null, null);
//	}
//	
//	
//	@Override
//	public RxHttpNode doHttpPost(String url, Map<String, String> params, Map<String, String> header, Map<String, String> cookie) {
//		RxHttpNodeImpl rxHttpNode = null;
//
//		CloseableHttpClient httpclient = this.getHttpClient();
//		HttpPost post = new HttpPost(url);
//		logger.info("doHttpPost: " + url);
//		
//		// 建立一个NameValuePair数组，用于存储欲传送的参数
//		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
//		// 遍历参数 map,添加参数
//		for (String key : params.keySet()) {
//			paramsList.add(new BasicNameValuePair(key, params.get(key)));
//		}
//
//		 if ( header != null && header.size() > 0 ) {
//             for (Entry<String, String> entry : header.entrySet()) {
//             	post.addHeader(entry.getKey(), entry.getValue());
//             }
//         }
//         
// 		if (cookie != null && cookie.size() > 0) {
// 	        StringBuffer cookies = new StringBuffer();
// 	        for (Entry<String, String> entry : cookie.entrySet()) {
// 	        	cookies.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
// 	        }
// 	        post.addHeader("Cookie", cookies.toString());
// 		}
//		
//		try {
//			post.setEntity(new UrlEncodedFormEntity(paramsList, "utf-8"));
//		} catch (UnsupportedEncodingException e1) {
//			logger.error("UrlEncodedFormEntity", e1);
//		}
//
//		try (CloseableHttpResponse response = httpclient.execute(post)) {
//
//			int code = response.getStatusLine().getStatusCode();
//			if (code >= 200 && code <= 299) {
//				
//				String res = EntityUtils.toString(response.getEntity(), "utf-8");
//				this.httpDocument = Jsoup.parse(res);
//				rxHttpNode = new RxHttpNodeImpl(httpDocument);
//			}
//			throw new Exception("Post Error in HTTP POST because StatusCodecode <= 200 || code >= 299!");
//
//		} catch (Exception e) {
//			logger.error("Post Error in HTTP POST! URL: " + url, e);
//		}
//
//		return rxHttpNode;
//	}
	
	
	
}
