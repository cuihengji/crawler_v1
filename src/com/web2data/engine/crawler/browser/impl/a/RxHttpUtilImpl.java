package com.web2data.engine.crawler.browser.impl.a;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.log4j.Logger;

import com.rkylin.crawler.engine.flood.common.IpProxyResult;
import com.rkylin.crawler.engine.flood.proxy.client.ProxyFacade;
import com.rkylin.crawler.engine.flood.util.HTTP;
import com.rkylin.crawler.engine.flood.util.StringUtil;
import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.RxHttpUtil;

public class RxHttpUtilImpl extends RxHttpUtil {

	private static final Logger logger = Logger.getLogger(RxHttpUtilImpl.class);
	
	@Override
	public String httpGet(String url) throws RxCrawlerException {
		String response = null;
		try {
			response = HTTP.doGet(url);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e.getMessage());
		}
		return StringUtil.removeSpecialChar(response);
	}
	
	@Override
	public String httpGet(String url, Map<String, String> cookie, boolean isProxy) throws RxCrawlerException {
		
		String response = null;
		
		//
		// httpget.addHeader("Proxy-Connection", "keep-alive");
		// httpget.addHeader("Cache-Control", "max-age=0");
		// httpget.addHeader("Upgrade-Insecure-Requests", "1");
		// httpget.addHeader("User-Agent",
		// "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		// httpget.addHeader("Accept",
		// "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		// httpget.addHeader("Accept-Encoding", "gzip, deflate, sdch");
		// httpget.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		// httpget.addHeader("Cookie",
		// "t=df5fbbassd2sdsasdes0w5asasdfe; _tb_token_=fb3e36ee5sddb; cookie2=11e39dsd3easa3b1d561722alf97dd5f");
		
		Map<String, String> header = new HashMap<String, String>();
//		header.put( "Proxy-Connection", "keep-alive");
//		header.put( "Cache-Control", "max-age=0");
//		header.put( "Upgrade-Insecure-Requests", "1");
		header.put( "User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		header.put( "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		header.put( "Accept-Encoding", "gzip, deflate, sdch");
		header.put( "Accept-Language", "zh-CN,zh;q=0.8");
		
		if (cookie != null && cookie.size() > 0) {
			
	        StringBuffer cookies = new StringBuffer();
	        for (Entry<String, String> entry : cookie.entrySet()) {
	        	
	        	cookies.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
	        }
	        
			header.put( "Cookie", cookies.toString());	
		}

		
		if (!isProxy) {
			
			try {
				
				response = HTTP.doGet(url, header);
				
			} catch (Exception e) {
				
				logger.error(e.getMessage(), e);
				throw new RxCrawlerException(e.getMessage());
				
			}
			
		} else {
			
			// 获取代理
			IpProxyResult ipProxy = ProxyFacade.getInstance().fetchIpProxy(-1, -1);
			
			try {
				
				response = HTTP.doGet(url, ipProxy.getIp(), ipProxy.getPort(), ipProxy.getUser_name(), ipProxy.getPass_word(), header);
				
			} catch (Exception e) {
				
				logger.error(e.getMessage(), e);
				throw new RxCrawlerException(e.getMessage());
				
			}
		}

		
		return StringUtil.removeSpecialChar(response);
		
	}
	
	@Override
	public String httpPost(String url, Map<String, String> params) throws RxCrawlerException {
		
		String response = null;
		
		try {
			response = HTTP.doPost(url, params);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e.getMessage());
		}
		return response;
	}

	@Override
	public String httpPost(String url, Map<String, String> header, Map<String, String> params) throws RxCrawlerException {
	
		String response = null;
		
		try {
			response = HTTP.doPost(url, header, params);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e.getMessage());
		}
		
		return response;
	}
	
	
	@Override
	public Map<String, String> generateFeiZhuCookie() throws RxCrawlerException {
		
		// "t=df5fbbassd2sdsasdes0w5asasdfe; _tb_token_=fb3e36ee5sddb; cookie2=11e39dsd3easa3b1d561722alf97dd5f"
		Map<String, String> cookie = new HashMap<String, String>();
		cookie.put("t", getRandomCharAndNumr(30));
//		cookie.put("_tb_token_", getRandomCharAndNumr(13));
//		cookie.put("cookie2", getRandomCharAndNumr(32));
		cookie.put("_tb_token_", "336cb71781605");
		cookie.put("cookie2", "18ef6c2bc289deb389364ce2e4be9c55");
//		t=9085dacb330f2efbad58c61ab58af2bf; _tb_token_=336cb71781605; cookie2=18ef6c2bc289deb389364ce2e4be9c55
		
		return cookie;
	}
	
	
	/** 
	 * 获取随机字母数字组合 
	 *  
	 * @param length 
	 *            字符串长度 
	 * @return 
	 */  
	private String getRandomCharAndNumr(Integer length) {  
		
		StringBuffer str = new StringBuffer();

		Random random = new Random();  
	    
		for (int i = 0; i < length; i++) {
			
	        boolean b = random.nextBoolean();
	        
	        if (b) { // 字符串  
	            
	        	// int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母  
	            str.append((char) (97 + random.nextInt(26)));
	            
	        } else { // 数字
	        	
	        	str.append(String.valueOf(random.nextInt(10)));
	        	
	        }  
	    }
	    
	    return str.toString();  
	}  
	
//	public static void main(String[] args) {
//		// 取得所有的配置，必须在业务代码之前进行！
//		ConfigFacade cf = ConfigFacade.getInstance();
//		try {
//			cf.initializeConf();
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		RxHttpUtilImpl rxhttp = new RxHttpUtilImpl();
//		int j = 0;
//		int k = 0;
//		for (int i= 1; i <= 10; i ++ ) {
//			System.out.println(i);
//			String url = "http://hotel.fliggy.com/ajax/hotelList.htm?pageSize=20&currentPage=1&city=540100&cityName=%E6%8B%89%E8%90%A8";
//			String respose = rxhttp.httpGet(url, rxhttp.generateFeiZhuCookie(), true);
//			System.out.println(respose);
//			
//			if (respose.contains("http://sec.taobao.com/query.htm")) {
//				System.out.println("error:" + (++j));
//			}
//			else {
//				System.out.println("ok:" + (++k));
//			}
//		}
//
//	}
	  
}
