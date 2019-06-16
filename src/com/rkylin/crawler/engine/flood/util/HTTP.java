package com.rkylin.crawler.engine.flood.util;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
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
import org.apache.log4j.Logger;

public class HTTP {

    private static final Logger logger =  Logger.getLogger(HTTP.class);

    public static String doGet(String url) throws Exception {
        
        try ( CloseableHttpClient client = HttpClients.custom().setRetryHandler(getRetryHandler()).build() ) {

			HttpGet get = new HttpGet(url);
			
			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
			get.setConfig(requestConfig);

			try ( CloseableHttpResponse response = client.execute(get) ) {
				
				int code = response.getStatusLine().getStatusCode();
				if ( code >= 200 && code <= 299 ) {
					return EntityUtils.toString(response.getEntity(), "utf-8");
				}

				throw new Exception("Get Error in HTTP GET because StatusCodecode <= 200 || code >= 299!");

			} catch (Exception e) {
				logger.error("Get Error in HTTP GET! URL: " + url, e);
			}
        }
        
		return null;
    }

	public static String doGet(String url, Map<String, String> header) throws Exception {
    	
        try ( CloseableHttpClient client = HttpClients.custom().setRetryHandler(getRetryHandler()).build() ) {

            HttpGet get = new HttpGet(url);
            
            if ( header != null && header.size() > 0 ) {
            	
                for (Entry<String, String> entry : header.entrySet()) {
                	get.addHeader(entry.getKey(), entry.getValue());
                }
                
            }

            // 设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
            get.setConfig(requestConfig);
            
            try ( CloseableHttpResponse response = client.execute(get) ) {
            	
                int code = response.getStatusLine().getStatusCode();
                if ( code >= 200 && code <= 299 ) {
                    return EntityUtils.toString(response.getEntity(), "utf-8");
                }
                
                throw new Exception("Get Error in HTTP GET because StatusCodecode <= 200 || code >= 299!");
                
            } catch(Exception e) {
                logger.error("Get Error in HTTP GET! URL: " +url, e );
            }
        }
        
		return null;
    }
    
	public static String doGet(String url, Map<String, String> header, Map<String, String> cookie) throws Exception {
    	
        try ( CloseableHttpClient client = HttpClients.custom().setRetryHandler(getRetryHandler()).build() ) {

            HttpGet get = new HttpGet(url);
            
            if ( header != null && header.size() > 0 ) {
            	
                for (Entry<String, String> entry : header.entrySet()) {
                	get.addHeader(entry.getKey(), entry.getValue());
                }
                
            }
            
    		if (cookie != null && cookie.size() > 0) {
    			
    	        StringBuffer cookies = new StringBuffer();
    	        for (Entry<String, String> entry : cookie.entrySet()) {
    	        	
    	        	cookies.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
    	        }
    	        
    	        get.addHeader("Cookie", cookies.toString());
    		}

            // 设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
            get.setConfig(requestConfig);
            
            try ( CloseableHttpResponse response = client.execute(get) ) {
            	
                int code = response.getStatusLine().getStatusCode();
                if ( code >= 200 && code <= 299 ) {
                    return EntityUtils.toString(response.getEntity(), "utf-8");
                }
                
                throw new Exception("Get Error in HTTP GET because StatusCodecode <= 200 || code >= 299!");
                
            } catch(Exception e) {
                logger.error("Get Error in HTTP GET! URL: " +url, e );
            }
        }
        
		return null;
    }
	
	/**
	 * 通过代理和设定header去获取请求数据
	 * 
	 * @param url
	 * @param proxyIp
	 * @param proxyPort
	 * @param userName
	 * @param passWord
	 * @param header
	 * @return 返回响应结果
	 */
	public static String doGet(String url, String proxyIp, int proxyPort, String userName, String passWord, Map<String, String> header) throws Exception {
		
		logger.info("====================params=========================");
		logger.info("url:" + url);
		logger.info("proxyIp:" + proxyIp);
		logger.info("proxyPort:" + proxyPort);

		String result = null;
		
        CloseableHttpClient httpclient = null;
		
		try {
			
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(new AuthScope(proxyIp, proxyPort), new UsernamePasswordCredentials(userName, passWord));
	
			httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).setRetryHandler(getRetryHandler()).build();
			
			URL httpUrl = new URL(url);
			
			HttpHost target = new HttpHost(httpUrl.getHost(), 80);
			
			HttpHost proxy = new HttpHost(proxyIp, proxyPort);

			HttpGet httpget = new HttpGet(httpUrl.getPath() + "?" + httpUrl.getQuery());

			if ( header != null && header.size() > 0) {

				logger.info("====================header=========================");
				for (Entry<String, String> entry : header.entrySet()) {
					
					logger.info(entry.getKey() + ":"   + entry.getValue());
					
					httpget.addHeader(entry.getKey(), entry.getValue());

				}
			}
			
			RequestConfig config = RequestConfig.custom().setProxy(proxy).setSocketTimeout(30000).setConnectTimeout(30000).build();
			httpget.setConfig(config);

			CloseableHttpResponse response = httpclient.execute(target, httpget);

			try {
				
				int code = response.getStatusLine().getStatusCode();

				logger.info("httpcode:" + code);

				if (code >= 200 && code <= 299) {


					HttpEntity entity = response.getEntity();
					result = EntityUtils.toString(entity, "utf-8");

					return result;
				}
				
				throw new Exception("Get Error!01");

			} catch (Exception e) {

				e.printStackTrace();
				
				throw new Exception("Get Error!02", e);

			} finally {
				
				response.close();
			}

		} catch (Exception e) {
			
			e.printStackTrace();

			throw new Exception("Get Error!03", e);

		} finally {
			
			httpclient.close();

		}
	}
	
	/**
	 * 通过代理和设定header去获取请求数据
	 * 
	 * @param url
	 * @param proxyIp
	 * @param proxyPort
	 * @param userName
	 * @param passWord
	 * @param header
	 * @return 返回响应结果
	 */
	public static String doGet(String url, String proxyIp, int proxyPort, String userName, String passWord, Map<String, String> header, Map<String, String> cookie) throws Exception {
		
		logger.info("====================params=========================");
		logger.info("url:" + url);
		logger.info("proxyIp:" + proxyIp);
		logger.info("proxyPort:" + proxyPort);

		String result = null;
		
        CloseableHttpClient httpclient = null;
		
		try {
			
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(new AuthScope(proxyIp, proxyPort), new UsernamePasswordCredentials(userName, passWord));
	
			httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).setRetryHandler(getRetryHandler()).build();
			
			URL httpUrl = new URL(url);
			
			HttpHost target = new HttpHost(httpUrl.getHost(), 80);
			
			HttpHost proxy = new HttpHost(proxyIp, proxyPort);

			HttpGet httpget = new HttpGet(httpUrl.getPath() + "?" + httpUrl.getQuery());

			if ( header != null && header.size() > 0 ) {

				logger.info("====================header=========================");
				for (Entry<String, String> entry : header.entrySet()) {
					
					logger.info(entry.getKey() + ":"   + entry.getValue());
					
					httpget.addHeader(entry.getKey(), entry.getValue());

				}
			}
			
    		if (cookie != null && cookie.size() > 0) {
    			
    	        StringBuffer cookies = new StringBuffer();
    	        for (Entry<String, String> entry : cookie.entrySet()) {
    	        	
    	        	cookies.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
    	        }
    	        
    	        httpget.addHeader("Cookie", cookies.toString());
    		}
			
			RequestConfig config = RequestConfig.custom().setProxy(proxy).setSocketTimeout(30000).setConnectTimeout(30000).build();
			httpget.setConfig(config);

			CloseableHttpResponse response = httpclient.execute(target, httpget);

			try {
				
				int code = response.getStatusLine().getStatusCode();

				logger.info("httpcode:" + code);

				if (code >= 200 && code <= 299) {


					HttpEntity entity = response.getEntity();
					result = EntityUtils.toString(entity, "utf-8");

					return result;
				}
				
				throw new Exception("Get Error!01");

			} catch (Exception e) {

				e.printStackTrace();
				
				throw new Exception("Get Error!02", e);

			} finally {
				
				response.close();
			}

		} catch (Exception e) {
			
			e.printStackTrace();

			throw new Exception("Get Error!03", e);

		} finally {
			
			httpclient.close();

		}
	}
	
	public static String doPost(String url,  Map<String, String> params) throws Exception {

		try ( CloseableHttpClient client = HttpClients.custom().setRetryHandler(getRetryHandler()).build() ) {

			HttpPost post = new HttpPost(url);

			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
			post.setConfig(requestConfig);
			
			// 建立一个NameValuePair数组，用于存储欲传送的参数
	        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
	        // 遍历参数 map,添加参数
	        for (String key : params.keySet()) {
	        	paramsList.add(new BasicNameValuePair(key, params.get(key)));
	        }
	        
		    post.setEntity(new UrlEncodedFormEntity(paramsList, "utf-8"));

			try ( CloseableHttpResponse response = client.execute(post) ) {
				
				int code = response.getStatusLine().getStatusCode();
				if (code >= 200 && code <= 299) {
					return EntityUtils.toString(response.getEntity(), "utf-8");
				}

				throw new Exception("Post Error in HTTP POST because StatusCodecode <= 200 || code >= 299!");
				
			} catch (Exception e) {
				logger.error("Post Error in HTTP POST! URL: " + url, e);
			}
		}

		return null;
	}
	
	public static String doPost(String url, Map<String, String> header, Map<String, String> params) throws Exception {

		try ( CloseableHttpClient client = HttpClients.custom().setRetryHandler(getRetryHandler()).build() ) {

			HttpPost post = new HttpPost(url);

            if ( header != null && header.size() > 0 ) {

                for (Entry<String, String> entry : header.entrySet()) {
                	post.addHeader(entry.getKey(), entry.getValue());
                }

            }
            
			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
			post.setConfig(requestConfig);
			
			// 建立一个NameValuePair数组，用于存储欲传送的参数
	        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
	        // 遍历参数 map,添加参数
	        for (String key : params.keySet()) {
	        	paramsList.add(new BasicNameValuePair(key, params.get(key)));
	        }
	        
		    post.setEntity(new UrlEncodedFormEntity(paramsList, "utf-8"));

			try ( CloseableHttpResponse response = client.execute(post) ) {
				
				int code = response.getStatusLine().getStatusCode();
				if ( code >= 200 && code <= 299 ) {
					return EntityUtils.toString(response.getEntity(), "utf-8");
				}
				
				throw new Exception("Post Error in HTTP POST because StatusCodecode <= 200 || code >= 299!");
				
			} catch (Exception e) {
				logger.error("Post Error in HTTP POST! URL: " + url, e);
			}
		}
		return null;
	}
	
	
	public static String doPost(String url, Map<String, String> header, Map<String, String> cookie, Map<String, String> params) throws Exception {

		try ( CloseableHttpClient client = HttpClients.custom().setRetryHandler(getRetryHandler()).build() ) {

			HttpPost post = new HttpPost(url);

            if ( header != null && header.size() > 0 ) {

                for (Entry<String, String> entry : header.entrySet()) {
                	post.addHeader(entry.getKey(), entry.getValue());
                }

            }
            
    		if (cookie != null && cookie.size() > 0) {
    			
    	        StringBuffer cookies = new StringBuffer();
    	        for (Entry<String, String> entry : cookie.entrySet()) {
    	        	
    	        	cookies.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
    	        }
    	        
    	        post.addHeader("Cookie", cookies.toString());
    		}
            
			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
			post.setConfig(requestConfig);
			
			// 建立一个NameValuePair数组，用于存储欲传送的参数
	        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
	        // 遍历参数 map,添加参数
	        for (String key : params.keySet()) {
	        	paramsList.add(new BasicNameValuePair(key, params.get(key)));
	        }
	        
		    post.setEntity(new UrlEncodedFormEntity(paramsList, "utf-8"));

			try ( CloseableHttpResponse response = client.execute(post) ) {
				
				int code = response.getStatusLine().getStatusCode();
				if ( code >= 200 && code <= 299 ) {
					return EntityUtils.toString(response.getEntity(), "utf-8");
				}
				
				throw new Exception("Post Error in HTTP POST because StatusCodecode <= 200 || code >= 299!");
				
			} catch (Exception e) {
				logger.error("Post Error in HTTP POST! URL: " + url, e);
			}
		}
		return null;
	}
	
	
	public static String doPost(String url, String proxyIp, int proxyPort, String userName, String passWord, Map<String, String> header, Map<String, String> params) throws Exception {

		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(proxyIp, proxyPort), new UsernamePasswordCredentials(userName, passWord));
		
		try ( CloseableHttpClient client = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).setRetryHandler(getRetryHandler()).build() ) {

			HttpPost post = new HttpPost(url);

           if ( header != null && header.size() > 0) {

               for (Entry<String, String> entry : header.entrySet()) {
                   post.addHeader(entry.getKey(), entry.getValue());
               }

           }
           
			HttpHost proxy = new HttpHost(proxyIp, proxyPort);

			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).setSocketTimeout(30000).setConnectTimeout(30000).build();
			post.setConfig(requestConfig);
			
			// 建立一个NameValuePair数组，用于存储欲传送的参数
	        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
	        // 遍历参数 map,添加参数
	        for (String key : params.keySet()) {
	        	paramsList.add(new BasicNameValuePair(key, params.get(key)));
	        }
	        
		    post.setEntity(new UrlEncodedFormEntity(paramsList, "utf-8"));

			try ( CloseableHttpResponse response = client.execute(post) ) {
				
				int code = response.getStatusLine().getStatusCode();
				if ( code >= 200 && code <= 299 ) {
					return EntityUtils.toString(response.getEntity(), "utf-8");
				}
				
				throw new Exception("Post Error in HTTP POST because StatusCodecode <= 200 || code >= 299!");
				
			} catch (Exception e) {
				logger.error("Post Error in HTTP POST! URL: " + url, e);
			}
		}
		return null;
	}
	
	
	public static String doPost(String url, String proxyIp, int proxyPort, String userName, String passWord, Map<String, String> header, Map<String, String> cookie, Map<String, String> params) throws Exception {

		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(proxyIp, proxyPort), new UsernamePasswordCredentials(userName, passWord));
		
		try ( CloseableHttpClient client = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).setRetryHandler(getRetryHandler()).build() ) {

			HttpPost post = new HttpPost(url);

           if ( header != null && header.size() > 0) {

               for (Entry<String, String> entry : header.entrySet()) {
                   post.addHeader(entry.getKey(), entry.getValue());
               }

           }
           
	   		if (cookie != null && cookie.size() > 0) {
				
		        StringBuffer cookies = new StringBuffer();
		        for (Entry<String, String> entry : cookie.entrySet()) {
		        	
		        	cookies.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
		        }
		        
		        post.addHeader("Cookie", cookies.toString());
			}

			HttpHost proxy = new HttpHost(proxyIp, proxyPort);

			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).setSocketTimeout(30000).setConnectTimeout(30000).build();
			post.setConfig(requestConfig);
			
			// 建立一个NameValuePair数组，用于存储欲传送的参数
	        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
	        // 遍历参数 map,添加参数
	        for (String key : params.keySet()) {
	        	paramsList.add(new BasicNameValuePair(key, params.get(key)));
	        }
	        
		    post.setEntity(new UrlEncodedFormEntity(paramsList, "utf-8"));

			try ( CloseableHttpResponse response = client.execute(post) ) {
				
				int code = response.getStatusLine().getStatusCode();
				if ( code >= 200 && code <= 299 ) {
					return EntityUtils.toString(response.getEntity(), "utf-8");
				}
				
				throw new Exception("Post Error in HTTP POST because StatusCodecode <= 200 || code >= 299!");
				
			} catch (Exception e) {
				logger.error("Post Error in HTTP POST! URL: " + url, e);
			}
		}
		return null;
	}
	
	/**
	 * 获取重试handler
	 */
	private static HttpRequestRetryHandler getRetryHandler() {

		HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {

			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				//重试三次,网络连接失败
				if (executionCount > 3) {
					return false;
				}

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return true;
			}
		};
		
		return myRetryHandler;
	}

	
	public static void main(String[] args) {
//		// ==== get Test ====
//		String url = "http://106.2.3.21/config/app/APP111_GetApps.php?env=TEST&userUuid=1001&userSeq=80";
	
			
			String url = "http://test.web2data.com/apptask10020/app/APP304_CreateAppRuleTask_FromWebUI.php?env=TEST&userUuid=356b5a3792bdd749577ce3cc621e1077&userSeq=82&appSeq=322&scenarioIndex=4&ruleIndex=1&accountIndex=&v1=&v2=&v3=&v4=&v5=&v6=&v7=&v8=&v9=&scheduledType=JOB&status=READY&isRecursiveTask=N";

 for (int i=0; i<1000; i++) {
	try {
		String response = doGet(url);
		System.out.println(response);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


//		String hostName = "hotel.fliggy.com";
//		String testUrl = "/ajax/hotelList.htm?pageSize=20&currentPage=1&searchId=972e637d0d8c4de8986c2be04948494c&city=540100&cityName=拉萨";
//		String ipProxy = "112.235.165.240";
//		int portProxy = 51236;
//		String userName = "rongshu";
//		String passWord = "rongshu0411";
//		
//		try {
//			URL tirc = new URL("https://hotel.fliggy.com/ajax/hotelList.htm?pageSize=20&currentPage=1&city=540100&cityName=拉萨");
//			System.out.println(tirc.getHost());
//			System.out.println(tirc.getPath());
//			System.out.println(tirc.getQuery());
//		} catch (MalformedURLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}// 构建一URL对象  
//		
////        // 取得所有的配置，必须在业务代码之前进行！
////        ConfigFacade cf = ConfigFacade.getInstance();
////        try {
////			cf.initializeConf();
////		} catch (Exception e1) {
////			// TODO Auto-generated catch block
////			e1.printStackTrace();
////		}
////		IpProxyResult ipProxyInfo = ProxyFacade.getInstance().fetchIpProxy(196, 1);
//		try {
////			String msg = doGet(hostName, testUrl, ipProxyInfo.getIp(), ipProxyInfo.getPort(), ipProxyInfo.getUser_name(), ipProxyInfo.getPass_word());
////			String msg = doGet(hostName, testUrl, ipProxy, portProxy, userName, passWord);
////			System.out.println(msg);
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
//		// ==== post Test ====
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("appSeq", "" + 315);
//		params.put("scenarioIndex", "" + 6);
//		params.put("ruleIndex", "" + 1);
//		params.put("ruleVersion", "" + 6);
//		params.put("scheduleType", "TEST");
//		params.put("logMessage", "test post 5");
//		String postUrl = "http://106.2.3.52:80/app/APP707_PushAppTaskLog.php";
//		
//		String ipProxy = "27.44.239.127";
//		int portProxy = 51214;
//		String userName = "rongshu";
//		String passWord = "rongshu0411";
//		
//		try {
////			String postResponse = doPost(postUrl, null, params);
//			String postResponse = doPost(postUrl, ipProxy, portProxy, userName, passWord, null, params);
//			System.out.println(postResponse);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
