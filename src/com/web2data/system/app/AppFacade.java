package com.web2data.system.app;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.rkylin.crawler.engine.flood.common.AppCreateRuleTaskResponse;
import com.rkylin.crawler.engine.flood.common.AvailableSessionResponse;
import com.rkylin.crawler.engine.flood.common.CallPhpApiSimpleResponse;
import com.rkylin.crawler.engine.flood.common.Constant;
import com.rkylin.crawler.engine.flood.common.Constant.APPTASK_API_KEY;
import com.rkylin.crawler.engine.flood.common.Constant.APPTASK_API_STATUS;
import com.rkylin.crawler.engine.flood.common.ScheduleTaskResponse;
import com.rkylin.crawler.engine.flood.model.AppTaskAndDBInfoResponse;
import com.rkylin.crawler.engine.flood.model.RuleTaskInputParameter;
import com.rkylin.crawler.engine.flood.util.HTTP;
import com.rkylin.crawler.engine.flood.util.JsonUtil;
import com.web2data.__main.LocalConfigLoader;
import com.web2data.system.config._main.ConfigFacade;

import sun.misc.BASE64Encoder;

public class AppFacade {
    private static final transient Logger logger = Logger.getLogger(AppFacade.class);

    private static AppFacade instance = null;

    private AppFacade() {
    	
    }

    public static AppFacade getInstance() {
        if (instance == null) {
        	instance = new AppFacade();
        }
        return instance;
    }
    
   /**
    * APP501_NotifyTaskStarted4.php
    * 爬虫通知APP开始执行任务
    * http://103.249.252.21/app/APP501_NotifyTaskStarted4.php?appSeq=76&scenarioIndex=1&ruleIndex=5&taskSeq=212387&startedCode=200&userSeq=17&cloudSeq=53&crawlerIp=118.122.188.48&crawlerSeq=173&sessionIndex=1

    */
	public void notifyAppTaskStarted4(int appSeq, int scenarioIndex,
			int ruleIndex, int taskSeq, int startedCode, int userSeq, int cloudSeq, String crawlerIp, int crawlerSeq, int sessionIndex, boolean isBrowserSession) throws Exception {
       
		try {

			String sessionType = "client";
			if (isBrowserSession) {
				sessionType = "browser";
			}
			
			String uri = ConfigFacade.getInstance().getApiAppTaskUriByKey(
					Constant.APPTASK_API_KEY.APP_NOTIFY_TASK_STARTED4);

			String appTaskHostIp = ConfigFacade.getInstance()
					.getAppTaskHostIpByAppSeq(appSeq);

			String php = String.format(String.format(
					Constant.API_URI_FORMAT.MQ_API_URI, appTaskHostIp, uri),
					appSeq, scenarioIndex, ruleIndex, taskSeq, startedCode, userSeq, cloudSeq, crawlerIp, crawlerSeq, sessionIndex, sessionType);
			
			logger.info("notifyAppTaskStarted4 php:" + php);

			CallPhpApiSimpleResponse response = null;
			
			String result = HTTP.doGet(php);
			
			response = JsonUtil.convertJsonStr2Obj(result, CallPhpApiSimpleResponse.class);
		
			if (response == null || response.getCode() != 200) {
				if (response != null) {
					logger.error("调用notifyAppTaskStarted4业务异常, resultCode:" + response.getCode() + ", message:" + response.getMessage());
					//不再抛出异常,否则浏览器重启.
//					throw new Exception("调用notifyAppTaskStarted4业务异常,"	+ response.getMessage());
				} else {
					logger.error("调用notifyAppTaskStarted4业务异常, response == null");
//					throw new Exception("调用notifyAppTaskStarted4业务异常");
				}
			}
		} catch (Exception ex) {
			logger.error("调用notifyAppTaskStarted4网络异常" + ex);
//			throw new Exception("调用notifyAppTaskStarted4网络异常", ex);
		}
	}
	
   /**
	* notifyAppTaskFinished9.php
	* 爬虫通知APP开始任务结束
	* http://103.249.252.21/app/APP502_NotifyTaskFinished9.php?appSeq=76&scenarioIndex=1&ruleIndex=5&taskSeq=212387&finishedCode=491&userSeq=17&cloudSeq=53
	* &status=FINISHED&pages=0&records=0&result1=-1&result2=-1&result3=-1&lastLogIndex=-1&abortedException=-1&verifyCodeTimes=0&scheduledType=TEST&crawlerSeq=173&sessionIndex=1
    */
	public void notifyAppTaskFinished9( int appSeq,
										int scenarioIndex,
										int ruleIndex,
										int taskSeq,
										int finishedCode,
										int userSeq,
										int cloudSeq,
										String status,
										int pages,
										int records,
										String result1,
										String result2,
										String result3,
										int lastLogIndex,
										String abortedException,
										int verifyCodeTimes,
										String scheduledType,
										int crawlerSeq,
										int sessionIndex,
										boolean isBrowserSession
									  ) throws RuntimeException {
				
		try {
			
			String sessionType = "client";
			if (isBrowserSession) {
				sessionType = "browser";
			}

			String uri = ConfigFacade.getInstance().getApiAppTaskUriByKey( Constant.APPTASK_API_KEY.APP_NOTIFY_TASK_FINISHED9 );

			String appTaskHostIp = ConfigFacade.getInstance().getAppTaskHostIpByAppSeq( appSeq );
		
		    String phpUrl = String.format(String.format( Constant.API_URI_FORMAT.MQ_API_URI, appTaskHostIp, uri ),
									appSeq,
									scenarioIndex,
									ruleIndex,
									taskSeq,
									finishedCode,
									userSeq,
									cloudSeq,
									status,
									pages,
									records,
									encodeHttpUrl( result1 ),
									encodeHttpUrl( result2 ),
									encodeHttpUrl( result3 ),
									lastLogIndex,
									encodeHttpUrl( abortedException ),
									verifyCodeTimes,
									scheduledType,
									crawlerSeq,
									sessionIndex,
									sessionType);

			logger.info("notifyAppTaskFinished9 phpUrl:" + phpUrl);
			
			CallPhpApiSimpleResponse response = null;
			
			String result = HTTP.doGet( phpUrl );
			
			response = JsonUtil.convertJsonStr2Obj( result, CallPhpApiSimpleResponse.class );
			
			if (response == null || response.getCode() != 200) {
				
				if (response != null ) {
					logger.error( "调用notifyAppTaskFinished9业务异常, resultCode:" + response.getCode() + ", message:" + response.getMessage() );
//					throw new RuntimeException( "调用notifyAppTaskFinished9业务异常," + response.getMessage() );
				}
				else {
					logger.error("调用notifyAppTaskFinished9业务异常, response == null");
//					throw new RuntimeException("调用notifyAppTaskFinished9业务异常");
				}
			}
			
		} catch (Exception ex) {
			logger.error("调用notifyAppTaskFinished9网络异常" + ex);
//			throw new RuntimeException("调用notifyAppTaskFinished9网络异常", ex);
		}
	}
		
	/**
	 * APP503_GetTaskInfoAndDataSchemaInfo2.php 爬虫通知APP开始任务结束
	 * http://103.235.224.167app/APP503_GetTaskInfoAndDataSchemaInfo.php?appSeq=100&scenarioIndex=1&ruleIndex=1&taskSeq=100&userSeq=1
	 */
	public AppTaskAndDBInfoResponse getAppTaskInfoAndDataSchemaInfo2(
			int appSeq, int scenarioIndex, int ruleIndex, int taskSeq, int userSeq)
			throws Exception {

		AppTaskAndDBInfoResponse response = null;

		try {
			
			String uri = ConfigFacade
					.getInstance()
					.getApiAppTaskUriByKey(
							Constant.APPTASK_API_KEY.APP_GET_TASKINFO_AND_DATASCHEMAINFO2);

			String appTaskHostIp = ConfigFacade.getInstance()
					.getAppTaskHostIpByAppSeq(appSeq);

			String php = String.format(String.format(
					Constant.API_URI_FORMAT.MQ_API_URI, appTaskHostIp, uri),
					appSeq, scenarioIndex, ruleIndex, taskSeq, userSeq);

			logger.info("getAppTaskInfoAndDataSchemaInfo2 php:" + php);
			
			String result = HTTP.doGet(php);

			response = JsonUtil.convertJsonStr2Obj(result,
					AppTaskAndDBInfoResponse.class);
			
			if (response == null || response.getCode() != 200) {
              
				if (response != null ) {
					
					logger.error("调用getAppTaskInfoAndDataSchemaInfo2业务异常, resultCode:"
							+ response.getCode() + ", message:"
							+ response.getMessage());
					
					throw new Exception("调用getAppTaskInfoAndDataSchemaInfo2业务异常,"
							+ response.getMessage());
				}
				else {
					logger.error("调用getAppTaskInfoAndDataSchemaInfo2业务异常, response == null");
					
					throw new Exception("调用getAppTaskInfoAndDataSchemaInfo2业务异常");
				}
			}
			
		} catch (Exception ex) {

			logger.error("调用getAppTaskInfoAndDataSchemaInfo2网络异常", ex);
			
			throw new Exception("调用getAppTaskInfoAndDataSchemaInfo2网络异常");
		}

		return response;
	}
	

	/**
	 * APP701_CreateRuleTask.php 基于输入的参数生成任务
	 * //http://103.235.224.167/app/ APP701_CreateRuleTask.php
	 */
	public void createRuleTask(RuleTaskInputParameter ruleTaskInputParameter)
			throws Exception {

		try {

			String uri = ConfigFacade.getInstance().getApiAppTaskUriByKey(
					          Constant.APPTASK_API_KEY.APP_CREATE_RULE_TASK2);

			String appTaskHostIp = ConfigFacade.getInstance()
					.getAppTaskHostIpByAppSeq(ruleTaskInputParameter.getAppSeq());
			
			String php = String.format(String.format(
					Constant.API_URI_FORMAT.MQ_API_URI, appTaskHostIp, uri),
					ruleTaskInputParameter.getEnv(), 
					ruleTaskInputParameter.getUserSeq(), 
					ruleTaskInputParameter.getAppSeq(),
					ruleTaskInputParameter.getScenarioIndex(),
					ruleTaskInputParameter.getRuleIndex(),
					ruleTaskInputParameter.getAccountIndex(),
					ruleTaskInputParameter.getSourceTaskSeq(),
					ruleTaskInputParameter.getSourceDataTableName(),
					ruleTaskInputParameter.getSourceDataSeq(),
					ruleTaskInputParameter.getScheduledType(),
					ruleTaskInputParameter.getV1(),
					ruleTaskInputParameter.getV2(),
					ruleTaskInputParameter.getV3(),
					ruleTaskInputParameter.getV4(),
					ruleTaskInputParameter.getV5(),
					ruleTaskInputParameter.getV6(),
					ruleTaskInputParameter.getV7(),
					ruleTaskInputParameter.getV8(),
					ruleTaskInputParameter.getV9()					
					);
			
			logger.info("createRuleTask php:" + php);
			
			AppCreateRuleTaskResponse response = null;
			
			String result = HTTP.doGet(php);
			
			response = JsonUtil.convertJsonStr2Obj(result, AppCreateRuleTaskResponse.class);
			
			if (response == null || response.getCode() != 200) {
				
				if (response != null ) {
					
					logger.error("调用createRuleTask业务异常, resultCode:"
							+ response.getCode() + ", message:" + response.getMessage());
					throw new Exception("调用createRuleTask业务异常,"
							+ response.getMessage());
				}
				else {
					throw new Exception("调用createRuleTask业务异常");
				}
			}
			//如果ScheduledType是API类型,直接调用APP Host上的scheduleTask发送任务
			if (response.getCode() == 200) {
				if (ruleTaskInputParameter.getScheduledType().equalsIgnoreCase(APPTASK_API_STATUS.API)) {
					
					AppFacade.getInstance().scheduleTask(
							ruleTaskInputParameter.getAppSeq(),
							ruleTaskInputParameter.getScenarioIndex(),
							ruleTaskInputParameter.getRuleIndex(),
							response.getResult().getTaskSeq(),
							APPTASK_API_STATUS.READY,
							ruleTaskInputParameter.getUserSeq(),
							APPTASK_API_STATUS.API);
				}
			}
		} catch (Exception ex) {
			
			logger.error("调用createRuleTask网络异常" + ex);
			throw new Exception("调用createRuleTask异常:  " +ex.getMessage(), ex);
		}
	}

	
	/**
	 * APP725_CreateBatchRuleTasks.php 基于输入的参数批量生成任务
	 * //http://103.235.224.167/app/APP725_CreateBatchRuleTasks.php
	 */
	public void createRuleTasks(RuleTaskInputParameter ruleTaskInputParameter)	throws Exception {

		try {
			String uri = ConfigFacade.getInstance().getApiAppTaskUriByKey(Constant.APPTASK_API_KEY.APP_CREATE_BATCH_RULE_TASK);
			String appTaskHostIp = ConfigFacade.getInstance().getAppTaskHostIpByAppSeq(ruleTaskInputParameter.getAppSeq());
			String php = String.format(Constant.API_URI_FORMAT.MQ_API_URI, appTaskHostIp, uri);
			logger.info("createBatchRuleTasks php:" + php);
			
			AppCreateRuleTaskResponse response = null;
			
			Map<String, String> params = new HashMap<String, String>();
			
			params.put("env", ruleTaskInputParameter.getEnv());
			params.put("userSeq", ruleTaskInputParameter.getUserSeq());
			params.put("appSeq", "" +ruleTaskInputParameter.getAppSeq());
			params.put("scenarioIndex", "" +ruleTaskInputParameter.getScenarioIndex());
			params.put("ruleIndex", "" + ruleTaskInputParameter.getRuleIndex());
			params.put("accountIndex", "" + ruleTaskInputParameter.getAccountIndex());
			params.put("sourceTaskSeq", "" + ruleTaskInputParameter.getSourceTaskSeq());
			params.put("sourceDataTableName", "" + ruleTaskInputParameter.getSourceDataTableName());
			params.put("sourceDataSeq", "" + ruleTaskInputParameter.getSourceDataSeq());
			params.put("scheduleType", ruleTaskInputParameter.getScheduledType());
			params.put("batchSize", "" +ruleTaskInputParameter.getBatchSize());
			
			String[] taskValues = ruleTaskInputParameter.getTaskValues();
			
			for (int i = 0; i < taskValues.length; i++) {
				params.put("taskValues" + i, taskValues[i]);
			}
		
			String result = HTTP.doPost(php, params);
			response = JsonUtil.convertJsonStr2Obj(result, AppCreateRuleTaskResponse.class);
			
			if (response == null || response.getCode() != 200) {
				if (response != null ) {
					logger.error("调用createBatchRuleTask业务异常, resultCode:"
							+ response.getCode() + ", message:" + response.getMessage());
					throw new Exception("调用createBatchRuleTask业务异常,"
							+ response.getMessage());
				}
				else {
					throw new Exception("调用createBatchRuleTask业务异常");
				}
			}
		} catch (Exception ex) {
			
			logger.error("调用createBatchRuleTask网络异常" + ex);
			throw new Exception("调用createBatchRuleTask异常:  " +ex.getMessage(), ex);
		}
	}
	/**
	 * APP707_PushAppTaskLog.php 爬虫通知APP开始执行任务
	 * http://106.2.1.39/app/APP707_PushAppTaskLog
	 * .php?appSeq=100&scenarioIndex=1
	 * &ruleIndex=1&scheduleType=TEST&logMessage=START|12:11:55|任务开始执行
	 */
	public void pushAppTaskLog(int appSeq, int scenarioIndex, int ruleIndex, int ruleVersion,
			String scheduleType, String logMessage) throws Exception {
		
		try {

			String uri = ConfigFacade.getInstance().getApiAppTaskUriByKey(
					Constant.APPTASK_API_KEY.APP_PUSH_APP_TASK_LOG);

			String appTaskHostIp = ConfigFacade.getInstance()
					.getAppTaskHostIpByAppSeq(appSeq);

			String php = String.format(Constant.API_URI_FORMAT.MQ_API_URI, appTaskHostIp, uri);

			logger.info("pushAppTaskLog php:" + php);

			CallPhpApiSimpleResponse response = null;

//		    List<NameValuePair> params = new ArrayList<NameValuePair>();
//		    params.add(new BasicNameValuePair("appSeq", "" + appSeq));
//		    params.add(new BasicNameValuePair("scenarioIndex", "" + scenarioIndex));
//		    params.add(new BasicNameValuePair("ruleIndex", "" + ruleIndex));
//		    params.add(new BasicNameValuePair("ruleVersion", "" + ruleVersion));
//		    params.add(new BasicNameValuePair("scheduleType", scheduleType));
//		    params.add(new BasicNameValuePair("logMessage", logMessage));
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("appSeq", "" + appSeq);
			params.put("scenarioIndex", "" + scenarioIndex);
			params.put("ruleIndex", "" + ruleIndex);
			params.put("ruleVersion", "" + ruleVersion);
			params.put("scheduleType", scheduleType);
			params.put("logMessage", logMessage);
		    
			String result = HTTP.doPost(php, params);

			response = JsonUtil.convertJsonStr2Obj(result,
					CallPhpApiSimpleResponse.class);

			if (response == null || response.getCode() != 200) {
				if (response != null) {

					logger.error("调用pushAppTaskLog业务异常, resultCode:"
							+ response.getCode() + ", message:"
							+ response.getMessage());
					throw new Exception("调用pushAppTaskLog业务异常,"
							+ response.getMessage());
				} else {
					logger.error("调用pushAppTaskLog业务异常, response == null");

					throw new Exception("调用pushAppTaskLog业务异常");
				}
			}

		} catch (Exception ex) {

			logger.error("调用pushAppTaskLog网络异常" + ex);
			throw new Exception("调用pushAppTaskLog网络异常", ex);
		}
	}
	
    /**
     * @param appSeq
     * @param scenarioIndex
     * @param ruleIndex
     * @param taskSeq
     * @param status
     * @param userSeq
     * @param scheduledType
     * 
     /app/APP201_ScheduleTheTask2.php?&appSeq=%s&scenarioIndex=%s&ruleIndex=%s&taskSeq=%s&status=%s&userSeq=%s&scheduledType=%s

     */
    public void scheduleTask(int appSeq, int scenarioIndex,	int ruleIndex, int taskSeq, String status, String userSeq, String scheduledType){

		try {
			String uri = ConfigFacade.getInstance().getApiAppTaskUriByKey(APPTASK_API_KEY.SCHEDULE_TASK);
			String appTaskHostIp = ConfigFacade.getInstance().getAppTaskHostIpByAppSeq(appSeq);
			String phpTemplate = String.format(Constant.API_URI_FORMAT.MQ_API_URI, appTaskHostIp, uri);
			String php = String.format(phpTemplate, appSeq, scenarioIndex, ruleIndex, taskSeq, status, userSeq, scheduledType);
			logger.info("scheduleTask php: " + php);

			ScheduleTaskResponse response = null;
			String result = HTTP.doGet(php);
			response = JsonUtil.convertJsonStr2Obj(result, ScheduleTaskResponse.class);

			if (response == null || response.getCode() != 200) {
				logger.error("调用scheduleTask业务异常, resultCode:" + response.getCode() + ", message:" + response.getMessage());
			}
		} catch (Exception ex) {
			logger.error("scheduleTask网络异常", ex);
		}
    }
	
	
	
	private String encodeHttpUrl(String inputString) {
		try {
			return URLEncoder.encode(inputString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
//	/**
//	 * APP905_Upload_Image_File.php
//	 * 把爬虫机的截图传到apptaskhost
//	 */
//	public void uploadImageFile(int appSeq, String imgFileName) throws Exception {
//		try {
//
//			String uri = ConfigFacade.getInstance().getApiAppTaskUriByKey(
//					Constant.APPTASK_API_KEY.APP_UPLOAD_IMAGE_FILE);
//
//			String appTaskHostIp = ConfigFacade.getInstance()
//					.getAppTaskHostIpByAppSeq(appSeq);
//
//			String php = String.format(Constant.API_URI_FORMAT.MQ_API_URI, appTaskHostIp, uri);
//
//			logger.info("uploadImageFile php:" + php);
//
//			CallPhpApiSimpleResponse response = null;
//			
//			Map<String, String> params = new HashMap<String, String>();
//			params.put("appSeq", "" + appSeq);
//			params.put("imageFileData", getBase64FromFilePath(imgFileName));
//			params.put("imageFileName", new File(imgFileName).getName());
//		    
//			String result = HTTP.doPost(php, params);
//			logger.info("result:" + result);
//			response = JsonUtil.convertJsonStr2Obj(result, CallPhpApiSimpleResponse.class);
//
//			if (response == null || response.getCode() != 200) {
//				if (response != null) {
//
//					logger.error("调用uploadImageFile业务异常, resultCode:"
//							+ response.getCode() + ", message:"
//							+ response.getMessage());
//					throw new Exception("调用uploadImageFile业务异常,"
//							+ response.getMessage());
//				} else {
//					logger.error("调用uploadImageFile业务异常, response == null");
//
//					throw new Exception("调用uploadImageFile业务异常");
//				}
//			}
//
//		} catch (Exception ex) {
//
//			logger.error("调用uploadImageFile网络异常" + ex);
//			throw new Exception("调用uploadImageFile网络异常", ex);
//		}
//	}
	
	/**
	 * APP905_Upload_Image_File.php
	 * 把爬虫机的截图传到apptaskhost
	 * 
	 * http://103.235.222.216/download_images/app125/
	 */
	public String uploadImageFile(int appSeq, byte[] srcImagBytes, String imgFileName) throws Exception {
		
		String hostDownloadUrl = null;
		try {

			String uri = ConfigFacade.getInstance().getApiAppTaskUriByKey(Constant.APPTASK_API_KEY.APP_UPLOAD_IMAGE_FILE);

			String appTaskHostIp = ConfigFacade.getInstance().getAppTaskHostIpByAppSeq(appSeq);
			hostDownloadUrl = "http://" + appTaskHostIp + "/download_images/" + "app" + appSeq;
			String php = String.format(Constant.API_URI_FORMAT.MQ_API_URI, appTaskHostIp, uri);

			logger.info("uploadImageFile php:" + php);

			CallPhpApiSimpleResponse response = null;
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("appSeq", "" + appSeq);
			params.put("imageFileData", getBase64FromFileBytes(srcImagBytes));
			params.put("imageFileName", imgFileName);
		    
			String result = HTTP.doPost(php, params);
			logger.info("result:" + result);
			response = JsonUtil.convertJsonStr2Obj(result, CallPhpApiSimpleResponse.class);

			if (response == null || response.getCode() != 200) {
				if (response != null) {

					logger.error("调用uploadImageFile业务异常, resultCode:"
							+ response.getCode() + ", message:"
							+ response.getMessage());
					throw new Exception("调用uploadImageFile业务异常,"
							+ response.getMessage());
				} else {
					logger.error("调用uploadImageFile业务异常, response == null");

					throw new Exception("调用uploadImageFile业务异常");
				}
			}

		} catch (Exception ex) {

			logger.error("调用uploadImageFile网络异常" + ex);
			throw new Exception("调用uploadImageFile网络异常", ex);
		}
		
		return hostDownloadUrl;
	}

//	private String getBase64FromFilePath( String filePath ) throws IOException {
//		
//
//		String imgeFileBase64 = "";
//		
//		File imageFile = new File( filePath );
//		
//		if ( imageFile.exists() ) {
//			
//			InputStream in = new FileInputStream(imageFile);
//		
//			byte data[] = new byte[(int) imageFile.length()]; // 创建合适文件大小的数组
//			
//			in.read(data); // 读取文件中的内容到b[]数组
//			
//			in.close();
//
//			BASE64Encoder encoder = new BASE64Encoder();
//			imgeFileBase64 =  encoder.encode(data);
//
//		} else {
//			
//			throw new FileNotFoundException(filePath + " not exist");
//		}
//		
//		return imgeFileBase64;
//	}
	
	private String getBase64FromFileBytes( byte[] fileBytes ) throws IOException {
		

		String imgeFileBase64 = "";
		
		
		if ( fileBytes != null && fileBytes.length > 0 ) {
			
			BASE64Encoder encoder = new BASE64Encoder();
			imgeFileBase64 =  encoder.encode(fileBytes);

		}
		
		return imgeFileBase64;
	}
	
	public int getUserAvailableSessions(int appSeq, int userSeq, int cloudSeq, boolean isBrowserSession) {
		//默认还有可用的Session
		int availableSession = -1;
		
		try {
			
			String sessionType = "client";
			if (isBrowserSession) {
				sessionType = "browser";
			}
			
			String uri = ConfigFacade.getInstance().getApiAppTaskUriByKey(Constant.APPTASK_API_KEY.APP_GET_USER_AVAILABLE_SESSIONS);
			String appTaskHostIp = ConfigFacade.getInstance().getAppTaskHostIpByAppSeq(appSeq);
			
			String php = String.format(String.format(Constant.API_URI_FORMAT.MQ_API_URI, appTaskHostIp, uri), userSeq, cloudSeq, sessionType);
			logger.info("getUserAvailableSessions php:" + php);

			AvailableSessionResponse response = null;
			String result = HTTP.doGet(php);
			response = JsonUtil.convertJsonStr2Obj(result, AvailableSessionResponse.class);
		
			if (response == null || response.getCode() != 200) {
				if (response != null) {
					logger.error("调用getUserAvailableSessions业务异常, resultCode:" + response.getCode() + ", message:" + response.getMessage());
				} else {
					logger.error("调用getUserAvailableSessions业务异常, response == null");
				}
			} else {
			      availableSession = response.getResult().getAvailableSessionNum();
			}
			
		} catch (Exception ex) {
			logger.error("调用getUserAvailableSessions失败！" + "appSeq="+appSeq + ",userSeq=" + userSeq + ",cloudSeq=" + cloudSeq + ",isBrowserSession=" + isBrowserSession);
			logger.error("调用getUserAvailableSessions失败！" + ex.getMessage());
		}
		logger.info("getUserAvailableSessions availableSession:" + availableSession);
		return availableSession;
	}
	
	
	/**
	 * APP730_CreateAppDataTable.php 基于输入的参数生成数据表
	 * http://103.249.252.21/app/APP730_CreateAppDataTable.php?
	 * env=TEST&userSeq=30&appSeq=338&scenarioIndex=1&ruleIndex=4&tableName=testTable&fieldNames=firstName|lastName
	 */
	public String createAppDataTable(int userSeq, int appSeq, int scenarioIndex, int ruleIndex, String tableName, String fieldNames) throws Exception {

		try {
			
			String uri = ConfigFacade.getInstance().getApiAppTaskUriByKey(APPTASK_API_KEY.CREATE_APP_DATA_TABLE);

			String appTaskHostIp = ConfigFacade.getInstance().getAppTaskHostIpByAppSeq(appSeq);
			
			String php = String.format(String.format(
					Constant.API_URI_FORMAT.MQ_API_URI, appTaskHostIp, uri),
					"TEST", 
					userSeq, 
					appSeq,
					scenarioIndex,
					ruleIndex,
					encodeHttpUrl(tableName),
					encodeHttpUrl(fieldNames));
			
			logger.info("APP730_CreateAppDataTable php:" + php);
			
			AppCreateRuleTaskResponse response = null;
			
			String result = HTTP.doGet(php);
			
			response = JsonUtil.convertJsonStr2Obj(result, AppCreateRuleTaskResponse.class);
			
			if (response == null || response.getCode() != 200) {
				
				if (response != null ) {
					
					logger.error("调用createRuleTask业务异常, resultCode:"
							+ response.getCode() + ", message:" + response.getMessage());
					throw new Exception("调用createRuleTask业务异常,"
							+ response.getMessage());
				}
				else {
					throw new Exception("调用createRuleTask业务异常");
				}
			}

		} catch (Exception ex) {
			
			logger.error("调用createRuleTask网络异常" + ex);
			throw new Exception("调用createRuleTask异常:  " +ex.getMessage(), ex);
		}
		
		return "app"+ appSeq + "_scenario"+ scenarioIndex + "_rule" + ruleIndex +"_"+tableName;
	}

	
	public static void main(String[] args) {

		try {
			LocalConfigLoader.load();
			// 取得所有的配置，必须在业务代码之前进行！
			ConfigFacade cf = ConfigFacade.getInstance();
			//cf.initializeConf();

		} catch (Exception e) {
			e.printStackTrace();
		}
//        String imgFileName = "c:\\www\\Big.jpg";
//		try {
//			AppFacade.getInstance().uploadImageFile(52, imgFileName);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// http://103.235.222.216:80/app/APP701_CreateRuleTask.php?env=TEST&userSeq=5&appSeq=52&scenarioIndex=1&ruleIndex=2
//		// &accountIndex=0&sourceTaskSeq=0&sourceDataTableName=app52_scenario1_rule1_ref_city&sourceDataSeq=4
//		// &v1=4&v2=深圳&v3=http://newhouse.sz.fang.com/house/s/&v4=-1&v5=-1&v6=-1&v7=-1&v8=data_table12345&v9=-1
//
//		RuleTaskInputParameter ruleTaskInputParameter = new RuleTaskInputParameter();
//		ruleTaskInputParameter.setUserSeq("5");
//		ruleTaskInputParameter.setAppSeq(52);
//		ruleTaskInputParameter.setScenarioIndex(1);
//		ruleTaskInputParameter.setRuleIndex(2);
//		ruleTaskInputParameter.setAccountIndex(-1);
//		ruleTaskInputParameter.setSourceTaskSeq(0);
//		ruleTaskInputParameter
//				.setSourceDataTableName("app52_scenario1_rule1_ref_city");
//		ruleTaskInputParameter.setSourceDataSeq("4");
//		ruleTaskInputParameter.setV1("4");
//		ruleTaskInputParameter.setV2("深圳");
//		ruleTaskInputParameter.setV3("http://newhouse.sz.fang.com/house/s/");
//		ruleTaskInputParameter.setV4("-1");
//		ruleTaskInputParameter.setV5("-1");
//		ruleTaskInputParameter.setV6("-1");
//		ruleTaskInputParameter.setV7("-1");
//		ruleTaskInputParameter.setV8("data_table12345");
//		ruleTaskInputParameter.setV9("-1");
//
//		try {
//			AppFacade.getInstance().createRuleTask(ruleTaskInputParameter);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}
		
}
