package com.web2data.system.config._main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.rkylin.crawler.engine.flood.common.Constant;
import com.rkylin.crawler.engine.flood.model.Config;
import com.rkylin.crawler.engine.flood.model.ConfigResponse;
import com.rkylin.crawler.engine.flood.model.IsUsedInterface;
import com.rkylin.crawler.engine.flood.model.UserAndPassword;
import com.rkylin.crawler.engine.flood.tools.ThreadPoolManager4Tool;
import com.rkylin.crawler.engine.flood.util.HTTP;
import com.rkylin.crawler.engine.flood.util.JsonUtil;
import com.web2data.__main.LocalConfigLoader;
import com.web2data.system.config.entity.Accounts;
import com.web2data.system.config.entity.App_accounts;
import com.web2data.system.config.entity.Apps;
import com.web2data.system.config.entity.Cloud_users;
import com.web2data.system.config.entity.Clouds;
import com.web2data.system.config.entity.Rules;
import com.web2data.system.config.entity.Session_account;
import com.web2data.system.config.entity.Session_apps;
import com.web2data.system.config.entity.Session_clouds;
import com.web2data.system.config.entity.User_apps;
import com.web2data.system.config.entity.api_app_task;
import com.web2data.system.config.entity.api_config;
import com.web2data.system.config.entity.api_monitor;
import com.web2data.system.config.entity.api_policy;
import com.web2data.system.config.entity.api_proxy;
import com.web2data.system.config.entity.api_scheduler;
import com.web2data.system.config.entity.config_crawler;
import com.web2data.system.config.entity.config_scheduler;
import com.web2data.system.config.entity.host_of_app_task;
import com.web2data.system.config.entity.host_of_crawler;
import com.web2data.system.config.entity.host_of_monitor;
import com.web2data.system.config.entity.host_of_policy;
import com.web2data.system.config.entity.host_of_proxy;
import com.web2data.system.config.entity.host_of_scheduler;

public class ConfigFacade {
    private static final transient Logger logger = Logger.getLogger(ConfigFacade.class);

    private static ConfigFacade cf = null;
    
    private static Properties prop = System.getProperties();
    
    private HashMap<String, String> hmCrawlerConf;
    private HashMap<String, String> hmSchedulerConf;
    private HashMap<String, String> hmMsgApi;
    private HashMap<String, String> hmSchedulerApi;
    private HashMap<String, String> hmPolicyApi;
    private HashMap<String, String> hmMonitorApi;
    private HashMap<String, String> hmApiConfigApi;
    private HashMap<String, String> hmAppTaskApi;
    private HashMap<String,String>  hmProxyApi;
    
    private List<host_of_policy> listPolicyHost;
    private List<host_of_proxy> listProxyHost;
    private List<host_of_scheduler> listSchedulerHost;
    private List<host_of_monitor> listMonitorHost;

    private Config conf;

    
    private ConfigFacade() {
        hmCrawlerConf = new HashMap<String, String>();
        hmSchedulerConf = new HashMap<String, String>();
        hmMsgApi = new HashMap<String, String>();
        hmSchedulerApi = new HashMap<String, String>();
        hmPolicyApi = new HashMap<String, String>();
        hmMonitorApi = new HashMap<String, String>();
        hmApiConfigApi = new HashMap<String, String>();
        hmAppTaskApi = new HashMap<String, String>();
        hmProxyApi = new HashMap<String, String>();
    }

    public static ConfigFacade getInstance() {
        if (cf == null) {
            cf = new ConfigFacade();
        }
        return cf;
    }
    
    public void initializeConf() throws Exception {
    	//repeated, remove?
        LocalConfigLoader.load();
        
    	System.out.println( "CONFIG_PHP_URL2 = " + Constant.LocalConfig.CONFIG_PHP_URL );
        
        String response = HTTP.doGet(Constant.LocalConfig.CONFIG_PHP_URL);
        System.out.println( "response = " + response );
        
        ConfigResponse configResponse = JsonUtil.convertJsonStr2Obj(response, ConfigResponse.class);
        System.out.println( "configResponse = " + configResponse.getMessage() );
        
        if (Constant.ConfigResponse.CODE_OK != configResponse.getCode()) {
            throw new Exception("Config get error!" + configResponse.getMessage());
        }

        conf = configResponse.getResult();
        
        preProcessConf();
    }

   
    /**
     * 取得本机系统类型
     * 
     * @return 本机系统类型
     */
    public String getSystemType() {
        return conf.getSystem_name();
    }

    /**
     * 取得crawler的BrowserSessions
     * 
     * @return BrowserSessions
     */
    public int getBrowserSessions() {
    	
    	host_of_crawler hoc = conf.getHost_of_crawler();
    	
    	return hoc.getBrowser_sessions();
    }
    
    /**
     * 取得crawler的ClientSessions
     * 
     * @return ClientSessions
     */
    public int getClientSessions() {
    	
    	host_of_crawler hoc = conf.getHost_of_crawler();
    	
    	return hoc.getClient_sessions();
    }

    
    /**
     * 取得crawler的DeveloperThreads
     * 
     * @return DeveloperThreads
     */
    public int getDeveloperThreads() {
    	
    	host_of_crawler hoc = conf.getHost_of_crawler();
    	
    	return hoc.getDeveloper_threads();
    }
    
    /**
     * 取得crawler id和max session
     * 
     * @return crawler id和max session
     */
	public int[] getCrawlerSeqAndSessNum() {
		int[] cs = new int[2];
		host_of_crawler hoc = conf.getHost_of_crawler();
		cs[0] = hoc.getSeq();
		cs[1] = hoc.getMax_sessions();
		return cs;
	}

    /**
     * 取得本机的IP(Crawler机专用)
     * 
     * @return Crawler机IP
     */
    public String getLocalCrawlerFirstIp() {
        if (Constant.LocalConfig.SYSTEM.equalsIgnoreCase(conf.getSystem_name())) {
            host_of_crawler hoc = conf.getHost_of_crawler();
            return hoc.getIp1();
        }

        return null;
    }

    /**
     * 取得本机的使用状态(Crawler机专用)
     * 
     * @return Crawler机的使用状态
     */
    public boolean getLocalCrawlerIsUsedFlag() {
        if (Constant.LocalConfig.SYSTEM.equalsIgnoreCase(conf.getSystem_name())) {
            host_of_crawler hoc = conf.getHost_of_crawler();
            String iuf = hoc.getIs_used();
            return Constant.ISUSED.IN_USE.equalsIgnoreCase(iuf) ? true : false;
        }

        return false;
    }
    
    /**
     * 取得本机的信息(Crawler机专用)
     * 
     * @return Crawler机的信息
     */
    public host_of_crawler getLocalCrawler() {
    	host_of_crawler hoc = new host_of_crawler();
        if (Constant.LocalConfig.SYSTEM.equalsIgnoreCase(conf.getSystem_name())) {
            hoc = conf.getHost_of_crawler();
        }

        return hoc;
    }

    /**
     * 为了config方便易用，进行一下预处理
     */
    private void preProcessConf() {
        if (conf == null) {
            return;
        }

        // 对config_crawler进行预处理
        for (config_crawler cc : conf.getConfig_crawler()) {
            if (hmCrawlerConf.containsKey(cc.getKey())) {
                continue;
            }
            hmCrawlerConf.put(cc.getKey(), cc.getValue());
        }
        
        // 对config_scheduler进行预处理
        for (config_scheduler cs : conf.getConfig_scheduler()) {
            if (hmSchedulerConf.containsKey(cs.getKey())) {
                continue;
            }
            hmSchedulerConf.put(cs.getKey(), cs.getValue());
        }
        
        // 对api_policy进行预处理，拿到API
        for (api_policy am : conf.getApi_policy()) {
            if (hmPolicyApi.containsKey(am.getApi_key())) {
                continue;
            }

            int port = am.getPort();
            if (port > 0) {
            	hmPolicyApi.put(am.getApi_key(), String.format(":%s%s", port, am.getUri()));
            } else {
            	hmPolicyApi.put(am.getApi_key(), am.getUri());
            }
        }
        
        // 对api_proxy进行预处理，拿到API
        for (api_proxy ap : conf.getApi_proxy()) {
            if (hmProxyApi.containsKey(ap.getApi_key())) {
                continue;
            }

            int port = ap.getPort();
            if (port > 0) {
            	hmProxyApi.put(ap.getApi_key(), String.format(":%s%s", port, ap.getUri()));
            } else {
            	hmProxyApi.put(ap.getApi_key(), ap.getUri());
            }
        }

        for (api_scheduler as : conf.getApi_scheduler()) {
            if (hmSchedulerApi.containsKey(as.getApi_key())) {
                continue;
            }

            int port = as.getPort();
            if (port > 0) {
                hmSchedulerApi.put(as.getApi_key(), String.format(":%s%s", port, as.getUri()));
            } else {
                hmSchedulerApi.put(as.getApi_key(), as.getUri());
            }
        }

        for (api_monitor as : conf.getApi_monitor()) {
            if (hmMonitorApi.containsKey(as.getApi_key())) {
                continue;
            }

            int port = as.getPort();
            if (port > 0) {
            	hmMonitorApi.put(as.getApi_key(), String.format(":%s%s", port, as.getUri()));
            } else {
            	hmMonitorApi.put(as.getApi_key(), as.getUri());
            }
        }
        
        for (api_config as : conf.getApi_config()) {
            if (hmApiConfigApi.containsKey(as.getApi_key())) {
                continue;
            }

            int port = as.getPort();
            if (port > 0) {
            	hmApiConfigApi.put(as.getApi_key(), String.format(":%s%s", port, as.getUri()));
            } else {
            	hmApiConfigApi.put(as.getApi_key(), as.getUri());
            }
        }
        
        for (api_app_task as : conf.getApi_app_task()) {
            if (hmAppTaskApi.containsKey(as.getApi_key())) {
                continue;
            }

            int port = as.getPort();
            if (port > 0) {
            	hmAppTaskApi.put(as.getApi_key(), String.format(":%s%s", port, as.getUri()));
            } else {
            	hmAppTaskApi.put(as.getApi_key(), as.getUri());
            }
        }
        
        // 对Host_of_policy进行预处理
        listPolicyHost = Arrays.asList(conf.getHost_of_policy());
        listProxyHost = Arrays.asList(conf.getHost_of_proxy());

        // 对host_of_scheduler进行预处理
        listSchedulerHost = Arrays.asList(conf.getHost_of_scheduler());
        
        listMonitorHost = Arrays.asList(conf.getHost_of_monitor());
    }

    /**
     * 根据API Key取得Uri
     * 
     * @param key
     *            API Key
     * @return Uri
     */
    public String getApiMsgUriByKey(String key) {
        if (hmMsgApi.containsKey(key)) {
            return hmMsgApi.get(key);
        }

        return null;
    }

    public String getApiSchedulerUriByKey(String key) {
        if (hmSchedulerApi.containsKey(key)) {
            return hmSchedulerApi.get(key);
        }

        return null;
    }

    public String getApiUriByKey(String key) {
        if (hmApiConfigApi.containsKey(key)) {
            return hmApiConfigApi.get(key);
        }

        return null;
    }

    public String getApiPolicyUriByKey(String key) {
        if (hmPolicyApi.containsKey(key)) {
            return hmPolicyApi.get(key);
        }

        return null;
    }
    
    public String getApiProxyUriByKey(String key) {
        if (hmProxyApi.containsKey(key)) {
            return hmProxyApi.get(key);
        }

        return null;
    }

    public String getApiMonitorUriByKey(String key) {
        if (hmMonitorApi.containsKey(key)) {
            return hmMonitorApi.get(key);
        }

        return null;
    }
    
    public String getApiAppTaskUriByKey(String key) {
        if (hmAppTaskApi.containsKey(key)) {
            return hmAppTaskApi.get(key);
        }

        return null;
    }
    
    
    public List<host_of_scheduler> getAllSchedulerHosts() {
        return listSchedulerHost;
    }
    
    
    public List<host_of_policy> getAllPolicyHosts() {
        return listPolicyHost;
    }
    
    public List<host_of_monitor> getAllMonitorHosts() {
        return listMonitorHost;
    }
    
    public List<host_of_proxy> getAllProxyHosts() {
        return listProxyHost;
    }
    
    /**
     * 更新config
     */
    public void refreshConfig() {
        ConfigFacade confFacade = new ConfigFacade();
        try {
            confFacade.initializeConf();

            this.conf = confFacade.conf;
            this.hmCrawlerConf = confFacade.hmCrawlerConf;
            this.hmMsgApi = confFacade.hmMsgApi;
            this.hmSchedulerApi = confFacade.hmSchedulerApi;
            this.hmPolicyApi = confFacade.hmPolicyApi;
            this.hmApiConfigApi = confFacade.hmApiConfigApi;
            this.hmAppTaskApi = confFacade.hmAppTaskApi;
            this.hmProxyApi = confFacade.hmProxyApi;

            this.listPolicyHost = confFacade.listPolicyHost;
            this.listProxyHost = confFacade.listProxyHost;
            this.listSchedulerHost = confFacade.listSchedulerHost;
            this.listMonitorHost = confFacade.listMonitorHost;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    

    /**
     * 开启config定期更新
     * 
     * @param delay
     *            延期执行时间
     * @param duration
     *            执行周期
     */
    public void scheduleRefresh2(long delay, long duration) {
        ThreadPoolManager4Tool.sexec(new Runnable() {
            @Override
            public void run() {
                logger.info("开始更新config");
                refreshConfig();
                logger.info("完成更新config");
            }
        }, delay, duration, TimeUnit.MILLISECONDS);
    }

    public Apps getApp(int appSeq) {
    	Apps[] apps = conf.getApps();
        if (apps == null || apps.length == 0) {
            return null;
        }

        for (Apps app : apps) {
            if (app.getSeq() == appSeq) {
                return app;
            }
        }

        return null;
    }

    /**
     * 获取设在rule上的use_proxy
     * 
     * @param appSeq
     * @param scenarioIndex
     * @param ruleIndex
     */
    public boolean getUseProxyOnRule(int appSeq, int scenarioIndex, int ruleIndex) {
    	
    	boolean proxyOnRule = false;
    	
    	Rules[] rules = conf.getRules();
    	
    	if (rules == null || rules.length == 0) {
    		
        	return false;
    	}
    	
    	for (Rules rule : rules) {
    		
			if (rule.getApp_seq() == appSeq
					&& rule.getScenario_index() == scenarioIndex
					&& rule.getRule_index() == ruleIndex) {
    			
    			if (Constant.USE_PROXY_KEY.USE_PROXY_YES.equals(rule.getUse_proxy())) {
    				
    				proxyOnRule = true;
    			}
    			
    			break;
    		}
    	}
    	
    	return proxyOnRule;
    }
    
    /**
     * 获取设在app上的use_proxy
     * 
     * @param appSeq
     */
    public boolean isAppUsingProxy(int appSeq) {
    	
    	boolean useProxyOnApp = false;
    	
    	Apps app = getApp(appSeq);
    	if (app != null) {
    		if (Constant.USE_PROXY_KEY.USE_PROXY_YES.equals(app.getUse_proxy())) {
    			useProxyOnApp = true;
    		}
    	}
    	
    	return useProxyOnApp;
    }
    
    /**
     * 获取设置在爬虫机上的log开关
     * 
     */
    public boolean isLogSwitchOn() {
    	
    	host_of_crawler crawler = conf.getHost_of_crawler();
    	if ( "ON".equals(crawler.getLog_switch()) ) {
    		return true;
    	}

    	return false;
    }
    

    /**
     * 获取设在rule上的ClearCookie
     * 
     * @param appSeq
     * @param scenarioIndex
     * @param ruleIndex
     */
    public boolean getClearCookieFlgOnRule(int appSeq, int scenarioIndex, int ruleIndex) {
    	
    	boolean isClearCookieOnRule = false;
    	
    	Rules[] rules = conf.getRules();

    	if (rules == null || rules.length == 0) {
    		
        	return false;
    	}
    	
    	for (Rules rule : rules) {
    		
			if (rule.getApp_seq() == appSeq
					&& rule.getScenario_index() == scenarioIndex
					&& rule.getRule_index() == ruleIndex) {
    			
    			if (Constant.INHERIT_COOKIE_KEY.INHERIT_COOKIE_YES.equals(rule.getInherit_cookie())) {
    				
    				isClearCookieOnRule = true;
    			}
    			
    			break;
    		}
    	}
    	
    	return isClearCookieOnRule;
    }
    
    
 public String getBypassListOnRule(int appSeq, int scenarioIndex, int ruleIndex) {
    	
	    String bypassList = "";
    	Rules[] rules = conf.getRules();

    	if (rules == null || rules.length == 0) {
    		
        	return "";
    	}
    	
    	for (Rules rule : rules) {
    		
			if (rule.getApp_seq() == appSeq
					&& rule.getScenario_index() == scenarioIndex
					&& rule.getRule_index() == ruleIndex) {
    			
				bypassList = rule.getBypass_domain_list();
				
				if("undefined".equalsIgnoreCase(bypassList)) {
					bypassList = "";
				}
				
    			break;
    		}
    	}
    	
    	return bypassList;
    }
 
 
	public String getBlockedUrlListOnRule(int appSeq, int scenarioIndex, int ruleIndex) {

		String blockedUrlList = "";
		Rules[] rules = conf.getRules();

		if (rules == null || rules.length == 0) {

			return "";
		}

		for (Rules rule : rules) {

			if (rule.getApp_seq() == appSeq && rule.getScenario_index() == scenarioIndex
					&& rule.getRule_index() == ruleIndex) {

				blockedUrlList = rule.getBlock_url_list();
				
				if("undefined".equalsIgnoreCase(blockedUrlList)) {
					blockedUrlList = "";
				}
				
				break;
			}
		}

		return blockedUrlList;
	}
    
    /**
     * 获取设在rule上的test_network
     * 
     * @param appSeq
     * @param scenarioIndex
     * @param ruleIndex
     */
    public boolean getTestNetworkOnRule(int appSeq, int scenarioIndex, int ruleIndex) {
    	
    	boolean testNetworkOnRule = false;
    	
    	Rules[] rules = conf.getRules();
    	
    	if (rules == null || rules.length == 0) {
    		
        	return false;
    	}
    	
    	for (Rules rule : rules) {
    		
			if (rule.getApp_seq() == appSeq
					&& rule.getScenario_index() == scenarioIndex
					&& rule.getRule_index() == ruleIndex) {
    			
    			if (Constant.TEST_NETWORK_KEY.TEST_NETWORK_YES.equals(rule.getTest_network())) {
    				
    				testNetworkOnRule = true;
    			}
    			
    			break;
    		}
    	}
    	
    	return testNetworkOnRule;
    }
    
    public Rules getRules(int appSeq, int scenarioIndex, int ruleIndex) {    	
        	
    	Rules[] rules = conf.getRules();
    	
    	if (rules == null || rules.length == 0) {
    		
        	return null;
    	}
    	
    	for (Rules rule : rules) {
    		
			if (rule.getApp_seq() == appSeq
					&& rule.getScenario_index() == scenarioIndex
					&& rule.getRule_index() == ruleIndex) {
    			
    			return rule;    		
    		}
    	}
    	
    	return null;
    }
    

    public String getAppTaskHostIpByAppSeq(int appSeq) {
		
    	String appTaskHostIp = null;
        
		Apps app = getApp(appSeq);
        
        int appTaskHostSeq = 0;
        
        if (app != null) {
        	appTaskHostSeq = app.getTask_host_seq();
        }
        
		host_of_app_task apptaskhost = getAppTaskHostBySeq(appTaskHostSeq);
    	
		if (apptaskhost.getIp1() != null
				&& !StringUtils.equals(apptaskhost.getIp1(), "")
				&& !StringUtils.equals(apptaskhost.getIp1(), "-1")) {
			
			appTaskHostIp = apptaskhost.getIp1();
		}
		else if (apptaskhost.getIp2() != null
				&& !StringUtils.equals(apptaskhost.getIp2(), "")
				&& !StringUtils.equals(apptaskhost.getIp2(), "-1")) {
			
			appTaskHostIp = apptaskhost.getIp2();
		}
		
		return appTaskHostIp;
    }
    
    private host_of_app_task getAppTaskHostBySeq(int appTaskHostSeq) {
        
    	if (conf == null) {
            return null;
        }

        for (host_of_app_task a : conf.getHost_of_app_task()) {
            if (a.getSeq() == appTaskHostSeq) {
                return a;
            }
        }

        return null;
    }

    public String getCrawlerConf(String key) {
        return hmCrawlerConf.get(key);
    }
    
    public String getSchedulerConf(String key) {
        return hmSchedulerConf.get(key);
    }

    public long getCrawlerRunnerSleepDuration() {
        long sd = 5L;
        try {
            sd = Long.parseLong(getCrawlerConf("NO_MESSAGE_SLEEP_SECONDS"));
        } catch (Exception ex) {
            logger.error("getCrawlerRunnerSleepDuration",ex);
        }
        return sd;
    }
    
    public long getRestartBrowserIntervalMinutes() {
    	long restartBrowserIntervalMinutes = 5L;
        try {
        	restartBrowserIntervalMinutes =  Integer.parseInt(getCrawlerConf("RESTART_BROWSER_INTERVAL_MINUTES"));
        } catch (Exception ex) {
            logger.error(ex);
        }
        return restartBrowserIntervalMinutes;
    }
    
	public long getAppListCacheSeconds() {

		long appListCacheSeconds = 5L;
		try {
			appListCacheSeconds = Long.parseLong(getCrawlerConf("USER_APPLIST_CACHE_SECONDS"));
		} catch (Exception ex) {
			logger.error("请在config_crawler表里配置USER_APPLIST_CACHE_SECONDS信息.", ex);
		}
		return appListCacheSeconds;
	}
    
	public long getProcessPauseSeconds() {

		long processPauseSeconds = 1L;
		try {
			processPauseSeconds = Long.parseLong(getCrawlerConf("PROCESS_PAUSE_SECONDS"));
		} catch (Exception ex) {
			logger.error("请在config_crawler表里配置PROCESS_PAUSE_SECONDS信息.", ex);
		}
		return processPauseSeconds;
	}
	
    public int getCpuRate() {
    	int cpuRate = 80;
        try {
        	cpuRate =  Integer.parseInt(getCrawlerConf("CPU_RATE"));
        } catch (Exception ex) {
        	logger.error(ex);
        }
        return cpuRate;
    }
    
    public int getMemoryRate() {
    	int memoryRate = 90;
        try {
        	memoryRate =  Integer.parseInt(getCrawlerConf("MEMORY_RATE"));
        } catch (Exception ex) {
        	 logger.error(ex);
        }
        return memoryRate;
    }
    

    
	public UserAndPassword getSessionUserAndPassword(int app, int sessionId) {

		int account_index = -1;
		UserAndPassword userAndPassword = null;
		
		account_index = this.getAccountIndex(app, sessionId);

		if (account_index == -1) {
			return userAndPassword;
		}

		App_accounts[] app_accounts = conf.getApp_accounts();

		for (App_accounts app_account : app_accounts) {

//			if (app_account.getApp().equals(equals(String.valueOf(app)))) {
			if ( app == Integer.parseInt(app_account.getApp()) ) {
				List<Accounts> accounts = app_account.getAccounts();
				for (Accounts account : accounts) {
					if (account.getApp_account_index() == account_index) {

						userAndPassword = new UserAndPassword();
						userAndPassword.setAppAccountIndex(account_index);
						userAndPassword.setUser(account.getUser_name());
						userAndPassword.setPass(account.getPass_word());
						break;
					}
				}
			}
		}

		return userAndPassword;
	}
    
	
	public int getAccountIndex(int app, int sessionId) {
		
		int account_index = -1;
		Session_account[] sessionAccountArray = conf.getSession_account();

		for (Session_account session_account : sessionAccountArray) {

			if (session_account.getApp().equals(String.valueOf(app)) && (session_account.getSession() == sessionId)) {
				account_index = session_account.getAccount_index();
				break;
			}
		}

		if (account_index == -1) {
			logger.debug("can't find the account index with app: app " + String.valueOf(app) + " sessionId: " + sessionId);
		}
		return account_index;
	}
	
	public String[] getCloudsBySessionId(int sessionId) {
		
		String[] clouds = null;
		
		Session_clouds[] session_clouds = conf.getSession_clouds();
		
		for (Session_clouds session_cloud : session_clouds) {
			
			if (session_cloud.getSession() == sessionId) {
					
				clouds = session_cloud.getClouds();
				break;
			}
		}
		
		return clouds;
	}
	
	public int getCloudsTTL(int sessionId) {
		
		int defaultRestartTimeInMin = 1; 
		
		String[] cloudsArray = getCloudsBySessionId(sessionId);

		Clouds[] allClouds = conf.getClouds();

		if ((cloudsArray != null) && (allClouds != null)) {
			
			for (String cloudQueriedBySessionId : cloudsArray) {
				for (Clouds singleCloud : allClouds) {

					if (singleCloud.getSeq().equalsIgnoreCase(cloudQueriedBySessionId)) {

						if (defaultRestartTimeInMin <= singleCloud.getWindow_ttl_mins()) {
							defaultRestartTimeInMin = singleCloud.getWindow_ttl_mins();
						}
						break;
					}
				}
			}
		}
		
		return defaultRestartTimeInMin;

	}
	
        
	public List<String> getRunnableAppSeqList(int sessionId) {

		List<String> runnableApps = new ArrayList<>();
		
		Session_apps[] sessionApps = conf.getSession_apps();

		for (Session_apps sessionApp : sessionApps) {

			if (sessionApp.getSession() == sessionId) {
				runnableApps = Arrays.asList(sessionApp.getApps());
				break;
			}
		}
		return runnableApps;
	}
	
	
	
	public int getAppWeightNumber(String appSeq) {

		int appWeight = 0;
		int appSeqInt = Integer.parseInt(appSeq);
		
		Apps[] apps = conf.getApps();
		for (Apps app : apps) {
			if (app.getSeq() == appSeqInt) {
				appWeight = app.getApp_weight_of_private_cloud_session();
				break;
			}
		}
		return appWeight;
	}
   
	public List<Cloud_users> getCloudUsersList(int sessionId) {

		List<Cloud_users> filteredCloudUsers = new ArrayList<Cloud_users>();
		String[] cloudsArray = getCloudsBySessionId(sessionId);
		List<Cloud_users> cloudUsers = Arrays.asList(conf.getCloud_users());

		for (Cloud_users cloud_users : cloudUsers) {

			String cloudSeq = String.valueOf(cloud_users.getCloud());
			if (cloudsArray != null) {
				for (int i = 0; i < cloudsArray.length; i++) {
					if (cloudSeq.equalsIgnoreCase(cloudsArray[i])) {
						filteredCloudUsers.add(cloud_users);
						break;
					}
				}
			}
		}
		return filteredCloudUsers;
	}
	
public List<Cloud_users> getCloudUsersListByCloudSeq(int cloudSeq) {
		
			List<Cloud_users> filteredCloudUsers = new ArrayList<Cloud_users>();
			List<Cloud_users> cloudUsers = Arrays.asList(conf.getCloud_users());

			for (Cloud_users cloud_users : cloudUsers) {

					if (cloudSeq ==cloud_users.getCloud()) {
						filteredCloudUsers.add(cloud_users);
					}
				}
			return filteredCloudUsers;
		
	}
	
	public List<User_apps> getUserAppsList() {
		//List<Cloud_users> cloudUsers = Arrays.asList(conf.getCloud_users());
		return Arrays.asList(conf.getUser_apps());
	}
	
    public String getSchedulerHost() {
    	
        return this.getPlatformHost(listSchedulerHost);
    }
    
    public int getSchedulerHostSeq() {
    	
        return this.getPlatformHostSeq(listSchedulerHost);
    }
    
    public String getProxyHost() {
    	
        return this.getPlatformHost(listProxyHost);
    }
    
	public String getPolicyHost() {

		return this.getPlatformHost(listPolicyHost);
	}
	
	public String getMonitorHost() {

		return this.getPlatformHost(listMonitorHost);
	}
	
	public String getPlatformHost(List<? extends IsUsedInterface> host) {

		String result = null;

		if (host != null) {

			for (int i = 0; i < host.size(); i++) {

				if (Constant.ISUSED.IN_USE.equalsIgnoreCase(host.get(i).getIs_used())) {
					result = host.get(i).getIp1();
					break;
				}
			}
		}

		return result;
	}
	
	
	public int getPlatformHostSeq(List<host_of_scheduler> host) {

		int result = -1;

		if (host != null) {
			for (int i = 0; i < host.size(); i++) {
				if (Constant.ISUSED.IN_USE.equalsIgnoreCase(host.get(i).getIs_used())) {
					result = host.get(i).getSeq();
					break;
				}
			}
		}
		return result;
	}
	
	
	public static boolean isWindowsHost() {

		String os = prop.getProperty("os.name");
		logger.info("os host: " + os);
		if (os != null && os.toLowerCase().indexOf("win") > -1) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(ConfigFacade.isWindowsHost());
	}
	
}
	
