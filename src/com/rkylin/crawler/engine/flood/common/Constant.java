package com.rkylin.crawler.engine.flood.common;

public final class Constant {
	
    public final static class MONITOR_INFO {

    	public static String WAR_VERSION = "V999";
    	public static String MYSQL_STATUS = "NA";
    	public static String CHROME_VERSION = "NA"; 
    }
    
    public final static class HTTPCode {
        public static final String ERROR_600 = "{\"code\":\"600\",\"message\":\"System Error-600.\"}";
        public static final String ERROR_601 = "{\"code\":\"601\",\"message\":\"System Error-601.\"}";
       
    }

    public final static class LocalConfig {
        public static String CONFIG_PHP_URL;
        public static String ENV;
        public static String SYSTEM;
        public static String CONFIG_IP;
        public static String DB_IP;
        public static String DB_PORT;
        public static String DB_SCHEMA;
        public static String DB_USERNAME;
        public static String DB_PASSWORD;
        public static boolean TEST_LOCAL;
    }
    

    public final static class WebDriverConfig {
    	
    	public static final String WINDOWS_PREFIX = "C:";
    	
    	public static final String FLOOD = "flood";
    	public static final String SESSION = "session";
    	public static final String CHROME_DATA = "chromeData";
    	public static final String DATA_DIR = "data";
      	public static final String PROPERTY_FILE_PATH = "flood/config/flood_crawler_2.properties";
      	public static final String USER_DATA_DIR = "userdatadir/";
      	public static final String DISC_CACHE_DIR = "diskcachedir/";
      	
    	public static final String CHROMEDRIVER_V2_27x = "chromedriver_v2.27";
    	public static final String CHROMEDRIVER_V2_28x = "chromedriver_v2.28";
    	public static final String CHROMEDRIVER_V2_43 = "chromedriver_v2.43";
      	public static final String CHROMEDRIVER_V72 = "chromedriver_v2.43"; //"chromedriver72";
    	
    	//public static final String LINUX_CHROMEDRIVER = "cdriver_2.29";
        public static final String FLOOD_CONFIG = "flood/config/";
        public static final String FRONTEND = "frontend";
        public static final String PROXY_ZIP_FILE_NAME = "proxy_session_";
        public static final String FRONT_ZIP_FILE_NAME = "front_session_";

		public static final String EXE = ".exe";
		public static final String ZIP = ".zip";
		public static final String PY = ".py";

		public static final String CHROME_DRIVERS_FOLDER = "chromeDrivers";
		public static final String WWW = "www";

    }
    
    public final static class ScriptFile {
    	
    	public static final String SCRIPT_TYPE_JAVA = "java";
    	public static final String SCRIPT_TYPE_PYTHON = "python";
    	public static final String SCRIPT_PYTHON_COMMAND = "python";
    	public static final String FILE_SUFFIXES_PY = ".py";
    	public static final String FILE_SUFFIXES_JSON = ".json";
    	public static final String SCRIPT_PY = "Script.py";
    	public static final String MAIN_PY = "Main.py";
    	public static final String RXCRAWLER_PY = "RxCrawler.py";
    	public static final String RXDATABASE_PY = "RxDatabase.py";
    	public static final String RXTASK_PY = "RxTask.py";
    	public static final String RXRESULT_PY = "RxResult.py";
    	public static final String RXNODE_PY = "RxNode.py";
    	public static final String RXCRAWLEREXCEPTION_PY = "RxCrawlerException.py";
    	public static final String RXSELECT_NODE_PY = "RxSelectNode.py";
    	public static final String OUTPUT_JSON = "output.json";
    	public static final String SPACE = " ";
    }

    public final static class MQ_API_KEY {
        public static final String FETCH_MESSAGE3 = "FETCH_MESSAGE3";
        public static final String GET_ACTIVE_APPLIST_BY_USER = "GET_ACTIVE_APPLIST_BY_USER";
        public static final String RETURN_MESSAGE2 = "RETURN_MESSAGE2";
        public static final String GET_APP_WEBSITE_ACCOUNT = "GET_APP_WEBSITE_ACCOUNT";
        public static final String DISABLE_APP_WEBSITE_ACCOUNT = "DISABLE_APP_WEBSITE_ACCOUNT";
        public static final String APP_NOTIFY_EVENT = "APP_NOTIFY_EVENT";
    }

    public final static class ConfigResponse {
        public static final int CODE_OK = 200;
        public static final int CODE_NO_MACHINE = 400;
        public static final int CODE_PARAM_NO_TENOUGH = 500;
        public static final int CODE_SYSTEM_ERROR = 600;
    }

    public final static class ISUSED {
        public static final String IN_USE = "Y";
        public static final String OUT_OF_USE = "N";
    }

    public final static class API_URI_FORMAT {
        public static final String MQ_API_URI = "http://%s%s";
    }

    public final static class CONFIG_API_KEY {
        public static final String GET_APP_WEBSITE_ACCOUNT = "GET_APP_WEBSITE_ACCOUNT";
    }

    public final static class APPTASK_API_KEY {
        public static final String GET_APP_TASK_HOST_AND_DATA_HOST_INFO = "GET_APP_TASK_HOST_AND_DATA_HOST_INFO";
        public static final String APP_NOTIFY_TASK_STARTED4 = "APP_NOTIFY_TASK_STARTED4";
        public static final String APP_NOTIFY_TASK_FINISHED9 = "APP_NOTIFY_TASK_FINISHED9";
        public static final String APP_GET_TASKINFO_AND_DATASCHEMAINFO2 = "APP_GET_TASKINFO_AND_DATASCHEMAINFO2";
        public static final String APP_CREATE_RULE_TASK = "APP_CREATE_RULE_TASK";
        public static final String APP_CREATE_RULE_TASK2 = "APP_CREATE_RULE_TASK2";
        public static final String APP_CREATE_BATCH_RULE_TASK = "APP_CREATE_BATCH_RULE_TASKS";
        public static final String APP_PUSH_APP_TASK_LOG = "APP_PUSH_APP_TASK_LOG";
        public static final String SCHEDULE_TASK = "SCHEDULE_TASK";
        public static final String APP_UPLOAD_IMAGE_FILE = "APP_UPLOAD_IMAGE_FILE";
		public static final String APP_GET_USER_AVAILABLE_SESSIONS = "APP_GET_USER_AVAILABLE_SESSIONS";
		public static final String CREATE_APP_DATA_TABLE = "CREATE_APP_DATA_TABLE";
    }
    
    public final static class APPTASK_API_STATUS {
        public static final String READY = "READY";
        public static final String API = "API";
    }
    
    public final static class TASK_STATUS {
    	
        public static final String NEW = "NEW";
        public static final String READY = "READY";
        public static final String DELETE = "DELETE";
        public static final String SCHEDULED = "SCHEDULED";
        public static final String SCHEDULING = "SCHEDULING";
        public static final String FINISHED = "FINISHED";

    }
    
    public final static class APPTASK_LOG_KEY {
    	// PREPARING,START,RUNNING,FINISHED
        public static final String APP_LOG_STATE_PREPARING = "PREPARING";
        public static final String APP_LOG_STATE_START = "START";
        public static final String APP_LOG_STATE_RUNNING = "RUNNING";
        public static final String APP_LOG_STATE_ERROR = "ERROR";
        public static final String APP_LOG_STATE_FINISHED = "FINISHED";
        
        public static final String APP_LOG_MSG_START = "任务执行开始";
        public static final String APP_LOG_MSG_FINISHED = "任务执行结束";
        public static final int APP_LOG_MSG_LEN = 28000;
    }
    

    public final static class POLICY_API_KEY {
        public static final String NOTIFY_WEBSITE_ANTI_ROBOT_HINT = "NOTIFY_WEBSITE_ANTI_ROBOT_HINT";
        public static final String NOTIFY_WEBSITE_IP_CRAWLER_SESSION_ACTIVITY = "NOTIFY_WEBSITE_IP_CRAWLER_SESSION_ACTIVITY";
        public static final String CHECK_WEBSITE_IP_CRAWLER_SESSION_POLICY =   "CHECK_WEBSITE_IP_CRAWLER_SESSION_POLICY";
    }
    
    public final static class PROXY_API_KEY {
        public static final String FETCH_PROXY2 = "FETCH_PROXY2";
        public static final String REPORT_NGPROXY = "REPORT_NGPROXY";
    }
    
    public final static class PROXY_SWITCH_KEY {
        public static final String PROXY_SWITCH_ON = "ON";
        public static final String PROXY_SWITCH_OFF = "OFF";
    }
    
    public final static class USE_PROXY_KEY {
        public static final String USE_PROXY_YES = "YES";
        public static final String USE_PROXY_NO = "NO";
    }
    
    public final static class INHERIT_COOKIE_KEY {
        public static final String INHERIT_COOKIE_YES = "YES";
        public static final String INHERIT_COOKIE_NO = "NO";
    }
    
    public final static class TEST_NETWORK_KEY {
        public static final String TEST_NETWORK_YES = "YES";
        public static final String TEST_NETWORK__NO = "NO";
    }
    
    public final static class MONITOR_API_KEY {
        public static final String CRAWLER_REPORT3 = "CRAWLER_REPORT3";
    }
    
    public final static class APP_NOTIFY_EVENT {
        public static final String EVENT_ID1 = "CAPTCHA_TOO_MANY";
        public static final String EVENT_ID2 = "USER_PASSORD_INCORRECT";
        public static final String EVENT_ID3 = "LOGIN_TOO_FREQUENT";
        public static final String EVENT_ID4 = "IP_FROZEN";
        public static final String EVENT_ID5 = "ACCOUNT_EXPIRED";
        public static final String EVENT_LEVEL1 = "CRAWLER";
        public static final String EVENT_LEVEL2 = "SESSION";
    }
    
    public final static class BUSINESS_ERR {
        public static final int BUSINESS_ERR_CODE_700 = 700;
        public static final int BUSINESS_ERR_CODE_701 = 701;
        
        public static final String BUSINESS_ERR_NAME_UNKNOWN_ERROR = "UNKNOWN_ERROR";
        
        public static final String BUSINESS_ERR_NAME_HAS_VERIFICATION_CODE = "HAS_VERIFICATION_CODE";
    }
    
    public final static class WEBDRIVER_ERR {
        public static final int WEBDRIVER_ERR_CODE_800 = 800;
        public static final int WEBDRIVER_ERR_CODE_801 = 801; // Timed out receiving message from renderer:
        public static final int WEBDRIVER_ERR_CODE_802 = 802; // chrome not reachable
        public static final int WEBDRIVER_ERR_CODE_803 = 803; // UnreachableBrowserException 
        public static final int WEBDRIVER_ERR_CODE_804 = 804; // Chrome failed to start: crashed
        public static final int WEBDRIVER_ERR_CODE_805 = 805; // get() 异常
        public static final int WEBDRIVER_ERR_CODE_806 = 806; // getTitle() 异常
        public static final int WEBDRIVER_ERR_CODE_807 = 807; // getPageSource()异常
        public static final int WEBDRIVER_ERR_CODE_808 = 808; // NoSuchSessionException
        public static final int WEBDRIVER_ERR_CODE_809 = 809; // 
        public static final int WEBDRIVER_ERR_CODE_810 = 810; // 
        
        public static final String WEBDRIVER_ERR_NAME_TIMED_OUT = "Timed out receiving message from renderer:";
        public static final String WEBDRIVER_ERR_NAME_UN_REACHABLE_BROWSER_EXCEPTION = "UnreachableBrowserException";
        public static final String WEBDRIVER_ERR_NAME_NOT_REACHABLE = "chrome not reachable";
        public static final String WEBDRIVER_ERR_NAME_CHROME_FAILED_TO_START = "Chrome failed to start: crashed";
        public static final String WEBDRIVER_ERR_NAME_GET_ERR = "get()异常";
        public static final String WEBDRIVER_ERR_NAME_GETTITLE_ERR = "getTitle()异常";
        public static final String WEBDRIVER_ERR_NAME_GETPAGESOURCE_ERR = "getPageSource()异常";
        public static final String WEBDRIVER_ERR_NAME_NO_SUCH_SESSION_EXCEPTION = "no such session";
        public static final String WEBDRIVER_ERR_NAME_UNKNOWN_ERROR = "unknown error";
    }
    
    public final static class NETWORK_ERR {
        public static final int NETWORK_ERR_CODE_850 = 850;
        public static final int NETWORK_ERR_CODE_851 = 851; // ERR_EMPTY_RESPONSE
        public static final int NETWORK_ERR_CODE_852 = 852; // ERR_PROXY_CONNECTION_FAILED
        public static final int NETWORK_ERR_CODE_853 = 853; // ERR_CONNECTION_RESET
        public static final int NETWORK_ERR_CODE_854 = 854; // ERR_TUNNEL_CONNECTION_FAILED
        public static final int NETWORK_ERR_CODE_855 = 855; // EMPTY_PAGE
        public static final int NETWORK_ERR_CODE_856 = 856; // ERR_RESPONSE_HEADERS_MULTIPLE_CONTENT_LENGTH
        
        public static final String NETWORK_ERR_NAME_ERR_EMPTY_RESPONSE = "ERR_EMPTY_RESPONSE";
        public static final String NETWORK_ERR_NAME_ERR_PROXY_CONNECTION_FAILED = "ERR_PROXY_CONNECTION_FAILED";
        public static final String NETWORK_ERR_NAME_ERR_CONNECTION_RESET = "ERR_CONNECTION_RESET";
        public static final String NETWORK_ERR_NAME_ERR_TUNNEL_CONNECTION_FAILED = "ERR_TUNNEL_CONNECTION_FAILED";
        public static final String NETWORK_ERR_NAME_ERR_START_WITH_ERR = "ERR_";
        public static final String NETWORK_ERR_NAME_ERR_EMPTY_PAGE = "EMPTY_PAGE";
        public static final String NETWORK_ERR_NAME_UNKNOWN_ERROR = "UNKNOWN_ERROR";
        
        public static final String ERR_RESPONSE_HEADERS_MULTIPLE_CONTENT_LENGTH = "ERR_RESPONSE_HEADERS_MULTIPLE_CONTENT_LENGTH";
    }
    
    public final static class SYSTEM_ERR {
        public static final int SYSTEM_ERR_CODE_900 = 900; // UNKNOWN_EXCEPTION
        public static final int SYSTEM_ERR_CODE_901 = 901; // 消息格式错误
        public static final int SYSTEM_ERR_CODE_902 = 902; // NotifySchedulerTaskStarted Exception
        public static final int SYSTEM_ERR_CODE_903 = 903; // NotifyAppTaskStarted Exception
        public static final int SYSTEM_ERR_CODE_904 = 904; // 取得app失败
        public static final int SYSTEM_ERR_CODE_905 = 905; // 下载jar包失败
        public static final int SYSTEM_ERR_CODE_906 = 906; // GetAppTaskInfoAndDataSchemaInfo Exception
        public static final int SYSTEM_ERR_CODE_907 = 907; // NotifySchedulerTaskFinished Exception
        public static final int SYSTEM_ERR_CODE_908 = 908; // NotifyAppTaskFinished Exception
        public static final int SYSTEM_ERR_CODE_909 = 909; // 数据库连接异常
        public static final int SYSTEM_ERR_CODE_910 = 910; // 数据库连接无效
        public static final int SYSTEM_ERR_CODE_911 = 911; // clearBrowser异常
        public static final int SYSTEM_ERR_CODE_912 = 912; // updateProxy异常
        public static final int SYSTEM_ERR_CODE_999 = 999; // 无效爬取过程的未知异常
        
        public static final String SYSTEM_ERR_NAME_MESSAGE_FORMAT_ERR = "消息格式错误";
        public static final String SYSTEM_ERR_NAME_NOTIFYSCHEDULERTASKSTARTED_EXCEPTION = "NotifySchedulerTaskStarted Exception";
        public static final String SYSTEM_ERR_NAME_NOTIFYSCHEDULERTASKFINISHED_EXCEPTION = "NotifySchedulerTaskFinished Exception";
        public static final String SYSTEM_ERR_NAME_GETAPP_EXCEPTION = "取得app失败";
        public static final String SYSTEM_ERR_NAME_DOWNLOADJAR_EXCEPTION = "下载jar包失败";
        public static final String SYSTEM_ERR_NAME_APP_UNKNOWN_EXCEPTION = "爬取过程的未知异常";
        public static final String SYSTEM_ERR_NAME_UNKNOWN_EXCEPTION = "没有识别出具体原因的问题";
        public static final String SYSTEM_ERR_NAME_CHROME_PROXY_EXCEPTION = "插件代理设置失败";
        public static final String SYSTEM_ERR_NAME_GETAPPTASKINFOANDDATASCHEMAINFO_EXCEPTION = "GetAppTaskInfoAndDataSchemaInfo Exception";
        public static final String SYSTEM_ERR_NAME_DB_CONNECTION_EXCEPTION = "数据库连接异常";
        public static final String SYSTEM_ERR_NAME_DB_CONNECTION_INVALID_EXCEPTION = "数据库连接无效";
        public static final String SYSTEM_ERR_NAME_NOTIFYAPPTASKSTARTED_EXCEPTION = "NotifyAppTaskStarted Exception";
        public static final String SYSTEM_ERR_NAME_NOTIFYAPPTASKFINISHED_EXCEPTION = "NotifyAppTaskFinished Exception";
        public static final String SYSTEM_ERR_NAME_CLEARBROWSER_EXCEPTION = "clearBrowser异常";
        public static final String SYSTEM_ERR_NAME_NO_SUCH_SESSION_EXCEPTION = "NoSuchException 异常";
    }
}
