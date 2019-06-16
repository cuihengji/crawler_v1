package com.rkylin.crawler.engine.flood.policy.client;

import org.apache.log4j.Logger;

import com.rkylin.crawler.engine.flood.common.CallMqApiResponse;
import com.rkylin.crawler.engine.flood.common.Constant;
import com.rkylin.crawler.engine.flood.exception.SystemException;
import com.rkylin.crawler.engine.flood.util.HTTP;
import com.rkylin.crawler.engine.flood.util.JsonUtil;
import com.web2data.__main.LocalConfigLoader;
import com.web2data.system.config._main.ConfigFacade;

public class PolicyFacade {
	
    private static final transient Logger logger = Logger.getLogger(PolicyFacade.class);

    private static PolicyFacade instance = null;

    private PolicyFacade() {
    	
    }

    public static PolicyFacade getInstance() {
        if (instance == null) {
        	instance = new PolicyFacade();
        }
        return instance;
    }
    
    
    /**
     * 爬虫机通知policy系统违规发生~ POL003
     * @param policyParameter TODO
     * 
     */
     public void notifyWebsiteAntiRobotHint(PolicyParameter policyParameter) {
         try {
             String uri = getPolicyUri(Constant.POLICY_API_KEY.NOTIFY_WEBSITE_ANTI_ROBOT_HINT);
             String php = String.format(uri, 
            		 					policyParameter.getHintType(),
            		 					policyParameter.getWebsiteSeq(),
            		 					policyParameter.getIp(),
            		 					policyParameter.getCrawlerHostSeq(), 
            		 					policyParameter.getSessionIndex(),
            		 					policyParameter.getAppSeq(), 
            		 					policyParameter.getScenarioIndex(), 
            		 					policyParameter.getRuleIndex(),
            		 					policyParameter.getAppTaskTableName(), 
            		 					policyParameter.getAppTaskSeq());
             logger.info("notifyWebsiteAntiRobotHint php URL: " +php);

             HTTP.doGet(php);
         } catch (Exception ex) {
             logger.error("notifyWebsiteAntiRobotHint网络异常", ex);
         }
     }
     
     
     /**
     * 爬虫机通知policy系统session的抓取频率~ POL017
     * 
     */
     public void notifyWebsiteIpCrawlerSessionActivity(PolicyParameter policyParameter) {
         try {
             String uri = getPolicyUri(Constant.POLICY_API_KEY.NOTIFY_WEBSITE_IP_CRAWLER_SESSION_ACTIVITY);

             String php = String.format(uri, 
            		 	policyParameter.getWebsiteSeq(),
            		 	policyParameter.getIp(),
            		 	policyParameter.getCrawlerHostSeq(),
            		 	policyParameter.getSessionIndex());
             logger.info("notifyWebsiteIpCrawlerSessionActivity php URL: " +php);
             HTTP.doGet(php);
         } catch (Exception ex) {
             logger.error("notifyWebsiteIpCrawlerSessionActivity网络异常", ex);
         }
     }
     
     
     /**
      * 爬虫机从policy系统检查自己是否违规 POL018
      * /policy/POL018_CheckWebsiteIpCrawlerSessionPolicy.php?websiteSeq=%s&ip=%s&crawlerHostSeq=%s&sessionIndex=%s
      */
      public boolean checkWebsiteIpCrawlerSessionPolicy(PolicyParameter policyParameter) {
          
		boolean violatePolicy = false;

		try {
			
			String uri = getPolicyUri(Constant.POLICY_API_KEY.CHECK_WEBSITE_IP_CRAWLER_SESSION_POLICY);
			String php = String.format(uri, 
         		 	policyParameter.getWebsiteSeq(),
         		 	policyParameter.getIp(),
         		 	policyParameter.getCrawlerHostSeq(),
         		 	policyParameter.getSessionIndex());
			logger.info("checkWebsiteIpCrawlerSessionPolicy php URL: " + php);
			String policyResponseString = HTTP.doGet(php);
			CallMqApiResponse policyResult = JsonUtil.convertJsonStr2Obj(policyResponseString, CallMqApiResponse.class);

			if ((policyResult.getCode() == 501) || (policyResult.getCode() == 502)) {
				violatePolicy = true;
				logger.error("checkWebsiteIpCrawlerSessionPolicy业务方法异常 " + policyResult.getCode());
			}

		} catch (Exception ex) {
			logger.error("checkWebsiteIpCrawlerSessionPolicy网络调用异常", ex);
			violatePolicy = true;
		}

		return violatePolicy;
      }

      
	private String getPolicyUri(String apiKey) {

		String uri = null;

		try {
			String policyHost = ConfigFacade.getInstance().getPolicyHost();
			if (policyHost == null) {
				throw new SystemException("没有找到提供Policy服务的机器！");
			}

			String apiUrl = ConfigFacade.getInstance().getApiPolicyUriByKey(apiKey);
			if (apiUrl == null) {
				throw new SystemException("Policy API 配置不正确！");
			}

			uri = String.format(Constant.API_URI_FORMAT.MQ_API_URI, policyHost, apiUrl);
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
        
        PolicyParameter policyParameter = new PolicyParameter();
        policyParameter.setHintType("1111");
        policyParameter.setWebsiteSeq(1);
        policyParameter.setIp("127.0.0.1");	
        PolicyFacade.getInstance().notifyWebsiteAntiRobotHint(policyParameter);
        PolicyFacade.getInstance().checkWebsiteIpCrawlerSessionPolicy(policyParameter);
        PolicyFacade.getInstance().notifyWebsiteIpCrawlerSessionActivity(policyParameter);
    }
}
