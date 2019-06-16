package com.rkylin.crawler.engine.flood.proxy.client;

import org.apache.log4j.Logger;

import com.rkylin.crawler.engine.flood.common.CallProxyApiResponse;
import com.rkylin.crawler.engine.flood.common.Constant;
import com.rkylin.crawler.engine.flood.common.IpProxyResult;
import com.rkylin.crawler.engine.flood.exception.SystemException;
import com.rkylin.crawler.engine.flood.util.HTTP;
import com.rkylin.crawler.engine.flood.util.JsonUtil;
import com.web2data.__main.LocalConfigLoader;
import com.web2data.system.config._main.ConfigFacade;

public class ProxyFacade {
	
    private static final transient Logger logger = Logger.getLogger(ProxyFacade.class);

    private static ProxyFacade instance = null;

    private ProxyFacade() {
    	
    }

    public static ProxyFacade getInstance() {
        if (instance == null) {
        	instance = new ProxyFacade();
        }
        return instance;
    }
    
    
    public IpProxyResult fetchIpProxy (int crawlerHostSeq, int sessionIndex) {
    	
    	CallProxyApiResponse response = null;
    	
        try {
        	
        	String uri = getProxyUri(Constant.PROXY_API_KEY.FETCH_PROXY2);
            String php = String.format(uri, crawlerHostSeq, sessionIndex);
        	logger.info("fetchIpProxy PHP: " +php);

            String res = HTTP.doGet(php);
            response = JsonUtil.convertJsonStr2Obj(res, CallProxyApiResponse.class);
            
        } catch (Exception ex) {
        	logger.error("fetchIpProxy网络调用异常", ex);
        }

        if (response != null) {
            if (response.getCode() != 200) {
            	logger.error("fetchIpProxy业务异常" +response.getMessage());
                return null;
            }
            
            return response.getResult();
        }
        
        return null;
    }
    
    public void reportNGProxy (int crawlerHostSeq, int sessionIndex, String ip, int port) {
        
    	try {
        	
        	String uri = getProxyUri(Constant.PROXY_API_KEY.REPORT_NGPROXY);
            String php = String.format(uri, crawlerHostSeq, sessionIndex, ip, port);
            HTTP.doGet(php);
            
        } catch (Exception ex) {
        	logger.error("reportNGProxy网络异常", ex);
        }

        return ;
    }
    
    
	private String getProxyUri(String apiKey) {

		String uri = null;

		try {
			String proxyHost = ConfigFacade.getInstance().getProxyHost();
			if (proxyHost == null) {
				throw new SystemException("没有找到提供Proxy服务的机器！");
			}

			String apiUrl = ConfigFacade.getInstance().getApiProxyUriByKey(apiKey);
			if (apiUrl == null) {
				throw new SystemException("Proxy API 配置不正确！");
			}

			uri = String.format(Constant.API_URI_FORMAT.MQ_API_URI, proxyHost, apiUrl);
		} catch (Exception ex) {
			logger.error(ex);
		}

		return uri;
	}

	
    public static void main(String[] args) {  
  	
    	
        try {
			LocalConfigLoader.load();
	        // 取得所有的配置，必须在业务代码之前进行！
	        ConfigFacade cf = ConfigFacade.getInstance();
	        cf.initializeConf();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	ProxyFacade.getInstance().fetchIpProxy(18,1);
    	
    	ProxyFacade.getInstance().reportNGProxy(18,1, "127.0.0.1", 8888);
    	
    }
}
