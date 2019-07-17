package com.web2data._global;

import java.io.File;
import java.io.InputStream;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.rkylin.crawler.engine.flood.common.Constant;
import com.rkylin.crawler.engine.flood.common.Constant.WebDriverConfig;
import com.rkylin.crawler.engine.flood.exception.SystemException;
import com.rkylin.crawler.engine.flood.util.SystemUtil;
import com.web2data.__main.LocalConfigLoader;
import com.web2data.system.config._main.ConfigFacade;

public class G2 {

	private static final  Logger logger = Logger.getLogger(G2.class);
	
	public static int TEST = 0;
	public static int PROD = 1;
	
	public static final String WINDOWS_ROOT = "C:/_web2data/";
	public static final String LINUX_ROOT = "/_web2data/";
	public static final String CONFIG_FILE = "config.properties";
	
	public static String factory_host_id = null; // 从 config.properties文件 中获取
	public static String factory_host_env = null; // LOCAL, CLOUD // 从 config.properties文件 中获取
	public static String cloud_infra_entry_ip = null; // 从 config.properties文件 中获取
	
	public static String LOCAL = "LOCAL";
	public static String CLOUD = "CLOUD";
	
	public static String factory_host_data_root = null; // 从 config.properties文件 中获取
	public static final String EMULATE_INFRA_WORKER_2_USERTASKHOST_JSON = "emulate/infra/worker-2-usertaskhosts.json";
	public static final String EMULATE_INFRA_USER_WORKER_2_STEPS_JSON = "emulate/infra/user%s_worker-2-steps.json";
	public static final String EMULATE_TASK_USER_TASKHOST_QUEUE_JSON = "emulate/task/user%s-taskhost%s_queue.json";
	public static final String EMULATE_INFRA_GLOBAL_JSON = "emulate/infra/global.json";
	
	public static final String EMULATE_INFRA_APP_JSON = "emulate/infra/app%s.json";
	
//	public static final String FLOOD = "flood";
//	public static final String SESSION = "session";
//	public static final String CHROME_DATA = "chromeData";
//	public static final String DATA_DIR = "data";
//  	public static final String PROPERTY_FILE_PATH2 = "flood/config/flood_crawler_2.properties";
//  	public static final String USER_DATA_DIR = "userdatadir/";
//  	public static final String DISC_CACHE_DIR = "diskcachedir/";
//  	
//	public static final String CHROMEDRIVER_V2_27x = "chromedriver_v2.27";
//	public static final String CHROMEDRIVER_V2_28x = "chromedriver_v2.28";
//	public static final String CHROMEDRIVER_V2_43 = "chromedriver_v2.43";
//  	public static final String CHROMEDRIVER_V72 = "chromedriver_v2.43"; //"chromedriver72";
//	
//	//public static final String LINUX_CHROMEDRIVER = "cdriver_2.29";
//    public static final String FLOOD_CONFIG = "flood/config/";
//    public static final String FRONTEND = "frontend";
//    public static final String PROXY_ZIP_FILE_NAME = "proxy_session_";
//    public static final String FRONT_ZIP_FILE_NAME = "front_session_";
//
//	public static final String EXE = ".exe";
//	public static final String ZIP = ".zip";
//	public static final String PY = ".py";
//
//	public static final String CHROME_DRIVERS_FOLDER = "chromeDrivers";
//	public static final String WWW = "www";
	
	
    static {
    	
		//String propertyFilePath = WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.PROPERTY_FILE_PATH;
        String propertyFile = null;
		
		boolean isWindowsHost = ConfigFacade.isWindowsHost();
		
		if (isWindowsHost) {
			propertyFile = WINDOWS_ROOT + File.separator + CONFIG_FILE;
		} else {
			propertyFile = LINUX_ROOT + File.separator + CONFIG_FILE;
		}
        
        try {
        	
        	InputStream in = Files.newInputStream(Paths.get(propertyFile));
        	
        	Properties props = new Properties();
        	
            props.load(in);

            factory_host_id = props.getProperty("factory_host_id");
            factory_host_env = props.getProperty("factory_host_env");
            factory_host_data_root = props.getProperty("factory_host_data_root");
            cloud_infra_entry_ip = props.getProperty("cloud_infra_entry_ip");
            
//            String ip = props.getProperty("_CRAWLER_IP");
//            
//            String dbIp = props.getProperty("_DB_IP");
//            String dbPort = props.getProperty("_DB_PORT");
//            String dbSchema = props.getProperty("_DB_SCHEMA");
//            String dbUsername = props.getProperty("_DB_USERNAME");
//            String dbPassword = props.getProperty("_DB_PASSWORD");
//            boolean testLocalWang = Boolean.valueOf(props.getProperty("_TEST_LOCAL_WANG"));
//            boolean testLocalQiu = Boolean.valueOf(props.getProperty("_TEST_LOCAL_QIU"));
//            boolean testLocal = Boolean.valueOf(props.getProperty("_TEST_LOCAL"));
//            String apiUserToken = props.getProperty("_API_USER_TOKEN");
//            InetAddress ia = SystemUtil.getFirstNonLoopbackAddress(true, false);
//            if (ia == null) {
//                throw new SystemException("不能取得本机IP!!");
//            }
           
            
//            String ip = ia.getHostAddress();
//            
////            ip = "118.89.229.100";//腾讯云8 SITE_SEQ=16
////            ip = "118.89.240.170";
////            ip = "103.37.162.198";// 美团11 fang.com SITE_SEQ=22
//
//			if (testLocalWang) {
//				ip = "103.202.127.109";
//				Constant.LocalConfig.TEST_LOCAL = testLocalWang;
//			}
//			if (testLocalQiu) {
//				ip = "120.52.176.32";
//				Constant.LocalConfig.TEST_LOCAL = testLocalQiu;
//			}
//			if (testLocal) {
//				ip = "120.52.176.32";
//				Constant.LocalConfig.TEST_LOCAL = testLocal;
//			}
			
			// ******************************* 用于测试 *********************
            //ip = "10.34.4.67";
//            url = String.format(url, companySeq, siteSeq, ip, apiUserToken);
//            
//            logger.info("CrawlerGetConfig.php URL: --:" + url);
//            
//            Constant.LocalConfig.CONFIG_PHP_URL = url;
//            Constant.LocalConfig.DB_IP = dbIp;
//            Constant.LocalConfig.DB_PORT = dbPort;
//            Constant.LocalConfig.DB_SCHEMA = dbSchema;
//            Constant.LocalConfig.DB_USERNAME = dbUsername;
//            Constant.LocalConfig.DB_PASSWORD = dbPassword;
//            Constant.LocalConfig.SYSTEM = "CRAWLER";
            
            System.out.println("factory_host_id = " + factory_host_id );
            System.out.println("factory_host_env = " + factory_host_env );
            System.out.println("factory_host_data_root = " + factory_host_data_root );
            System.out.println("cloud_infra_entry_ip = " + cloud_infra_entry_ip );
            
        } catch ( Exception e ) {
        	//
        	System.out.println( e.getMessage() );
        }
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
}
