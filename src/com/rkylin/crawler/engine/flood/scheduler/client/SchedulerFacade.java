package com.rkylin.crawler.engine.flood.scheduler.client;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.rkylin.crawler.engine.flood.common.AppListResponse;
import com.rkylin.crawler.engine.flood.common.CallMqApiResponse;
import com.rkylin.crawler.engine.flood.common.Constant;
import com.rkylin.crawler.engine.flood.common.Result;
import com.rkylin.crawler.engine.flood.exception.BusinessException;
import com.rkylin.crawler.engine.flood.exception.SystemException;
import com.rkylin.crawler.engine.flood.model.MessageObject;
import com.rkylin.crawler.engine.flood.util.HTTP;
import com.rkylin.crawler.engine.flood.util.JsonUtil;
import com.web2data.system.config._main.ConfigFacade;
import com.web2data.system.config.entity.User_apps_scheduler;

public class SchedulerFacade {
    private static final transient Logger logger = Logger.getLogger(SchedulerFacade.class);

    private static SchedulerFacade instance = null;

    private SchedulerFacade() {
    	
    }

    public static SchedulerFacade getInstance() {
        if (instance == null) {
        	instance = new SchedulerFacade();
        }
        return instance;
    }
    
    
    /**
     * 
     * 获取一个Message,平台重构后的版本
     * 
     * @param cid
     * @param sid
     * @param ipParam
     * @param appSeq
     * @param accountIndex
     * @param isBrowerSession TODO
     * @return JSON字符串
     * @throws BusinessException
     */
    //Scheduler/MSG002_FetchMessage3.php?crawlerHostSeq=%s&sessionIndex=%s&ip=%s&appSeq=%s&accountIndex=%s&sessionType=%s（BROWSER or CLIENT）
    public Result fetchMessage3(int cid, int sid, String ipParam, int appSeq, int accountIndex, boolean isBrowserSession) {

		String sessionType = "CLIENT";
		if (isBrowserSession) {
			sessionType = "BROWSER";
		}
		
    	String apiUri = getSchedulerUri(Constant.MQ_API_KEY.FETCH_MESSAGE3);
        
    	String php = String.format(apiUri, cid, sid, ipParam, appSeq, accountIndex, sessionType);
        
        logger.info("SchedulerFacade pop3 URL: " + php);
        
        CallMqApiResponse response = null;
        
        try {
        	
        	String res = HTTP.doGet(php);
        	response = JsonUtil.convertJsonStr2Obj(res, CallMqApiResponse.class);
            
        } catch (Exception ex) {
        	
        	logger.error("pop3 网络异常，" + ex);
        }

        if (response != null) {
        	
            if (response.getCode() == 400) {
            	logger.error("pop3 业务异常，队列中没有消息" + response.getCode());
                return null;
            }
            
            if (response.getCode() != 200) {
            	logger.error("pop3 业务异常，" + response.getCode());
            	try {
					Thread.sleep(3*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
            
            return response.getResult();
        }

       return null;
    }
    
    
    /**
     * 归还一个Message
     * 
     * @param msg
     * @throws SystemException
     * @throws BusinessException
     */
    ///Scheduler/MSG015_ReturnMessage2.php?queueName=%s&&message=%s&crawlerHostSeq=%s&sessionIndex=%s&ip=%s
    public void returnMsg2(String qn, String msg, int crawlerHostSeq, int sessionIndex, String ip) throws BusinessException {
      
    	String apiUri = getSchedulerUri(Constant.MQ_API_KEY.RETURN_MESSAGE2);
       
    	try {
            
    		String encodedMsg = URLEncoder.encode(msg, "UTF-8");
            
            String php = String.format(apiUri, qn, encodedMsg, crawlerHostSeq, sessionIndex, ip);
            
            logger.info("returnMsg2 :"  + php);
           
            String res = HTTP.doGet(php);
            
            CallMqApiResponse response = JsonUtil.convertJsonStr2Obj(res, CallMqApiResponse.class);
            
            if (response.getCode() != 200) {
            	 logger.error("returnMsg2业务异常 :" + response.getCode());
                throw new BusinessException(response.getMessage());
            }
            
        } catch (Exception ex) {
        	
            logger.error("returnMsg2网络异常" ,ex);
            throw new BusinessException(ex);
        }
    }
    
    
    public MessageObject convertMsgToObj(String msg) {
        MessageObject obj = new MessageObject();
        if (msg == null) {
            return obj;
        }

        String[] ary = msg.split("~");
        if (ary.length == 13) {
        	// Message format:
        	// cloudType~taskPriority~websiteSeq~schedulerHostSeq~appTaskHostSeq~appDataHostSeq
        	//~schedulerTaskSeq~appSeq~scenarioIndex~ruleIndex~appTaskTableName~appTaskSeq~appAccountIndex
        	obj.setCloudType(ary[0]);
        	obj.setTaskPriority(ary[1]);
        	obj.setWebsiteSeq(new Integer(ary[2]));
        	obj.setSchHostSeq(new Integer(ary[3]));
        	obj.setAppTaskHostSeq(new Integer(ary[4]));
        	obj.setAppDataHostSeq(new Integer(ary[5]));
           	obj.setSchTaskSeq(new Integer(ary[6]));
            obj.setAppSeq(new Integer(ary[7]));
            obj.setScenarioIndex(new Integer(ary[8]));
            obj.setRuleIndex(new Integer(ary[9]));
            obj.setAppTaskTableName(ary[10]);
            obj.setAppTaskSeq(new Integer(ary[11]));
            obj.setAppAccountSeq(new Integer(ary[12]));
        } else {
        	
        	// new Message format:
        	// cloudType~taskPriority~websiteSeq~schedulerHostSeq~appTaskHostSeq~appDataHostSeq
        	//~schedulerTaskSeq~appSeq~scenarioIndex~ruleIndex~appTaskTableName~appTaskSeq~appAccountIndex~userSeq~scheduledType
        	obj.setCloudType(ary[0]);
        	obj.setTaskPriority(ary[1]);
        	obj.setWebsiteSeq(new Integer(ary[2]));
        	obj.setSchHostSeq(new Integer(ary[3]));
        	obj.setAppTaskHostSeq(new Integer(ary[4]));
        	obj.setAppDataHostSeq(new Integer(ary[5]));
           	obj.setSchTaskSeq(new Integer(ary[6]));
            obj.setAppSeq(new Integer(ary[7]));
            obj.setScenarioIndex(new Integer(ary[8]));
            obj.setRuleIndex(new Integer(ary[9]));
            obj.setAppTaskTableName(ary[10]);
            obj.setAppTaskSeq(new Integer(ary[11]));
            obj.setAppAccountSeq(new Integer(ary[12]));
            obj.setUserSeq(new Integer(ary[13]));
            obj.setScheduledType(ary[14]);
        }

        return obj;
    }
    
    
    /**
     * 
     * 得到一个user的所有队列中还有任务的的APP的列表
     * @param userSeq
     */
    //http://106.2.1.108/scheduler/SCH015_GetActiveAppListByUser.php?userSeq=76&cloud=76&sessionType=BROWSER
    public List<User_apps_scheduler> getActiveAppListByUser(int userSeq, int cloudSeq, boolean isBrowserSession) {

    	String sessionType = "CLIENT";
		if (isBrowserSession) {
			sessionType = "BROWSER";
		}
    	String apiUri = getSchedulerUri(Constant.MQ_API_KEY.GET_ACTIVE_APPLIST_BY_USER);
    	String php = String.format(apiUri, userSeq, cloudSeq, sessionType);
        logger.info("getActiveAppListByUser php: " + php);
        
        AppListResponse response = null;
        try {
        	String res = HTTP.doGet(php);
            logger.info("getActiveAppListByUser result: " + res);
        	response = JsonUtil.convertJsonStr2Obj(res, AppListResponse.class);
            
        } catch (Exception ex) {
        	logger.error("getActiveAppListByUser 网络异常，" + ex);
        }

        if (response != null) {
            if (response.getCode() == 400) {
            	logger.error("getActiveAppListByUser 业务异常，队列中没有消息" + response.getCode());
                return null;
            }
            
            if (response.getCode() != 200) {
            	logger.error("getActiveAppListByUser 业务异常，" + response.getCode());
            	try {
					Thread.sleep(3*1000);
				} catch (InterruptedException e) {
					logger.error("InterruptedException" + e);
				}
            	return null;
            }
            return Arrays.asList(response.getResult().getUser_apps_scheduler());
        }
       return null;
    }
    
    
	private String getSchedulerUri(String apiKey) {

		String uri = null;

		try {
			String schedulerHost = ConfigFacade.getInstance().getSchedulerHost();
			if (schedulerHost == null) {
				throw new SystemException("没有找到提供Scheduler服务的机器！");
			}

			String apiUrl = ConfigFacade.getInstance().getApiSchedulerUriByKey(apiKey);
			if (apiUrl == null) {
				throw new SystemException("Scheduler API 配置不正确！");
			}

			uri = String.format(Constant.API_URI_FORMAT.MQ_API_URI, schedulerHost, apiUrl);
		} catch (Exception ex) {
			logger.error(ex);
		}

		return uri;
	}
}
