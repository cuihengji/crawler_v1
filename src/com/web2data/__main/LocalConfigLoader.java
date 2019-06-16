package com.web2data.__main;

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
import com.web2data.system.config._main.ConfigFacade;

public class LocalConfigLoader {

    private static final  Logger logger = Logger.getLogger(LocalConfigLoader.class);

    public static void load() throws Exception {
    	
        Properties props = new Properties();

		String propertyFilePath = WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.PROPERTY_FILE_PATH;
		
		boolean isWindowsHost = ConfigFacade.isWindowsHost();
		
		if (!isWindowsHost) {
			propertyFilePath = File.separator + WebDriverConfig.PROPERTY_FILE_PATH;
		}
        
        try (InputStream in = Files.newInputStream(Paths.get(propertyFilePath))) {

            props.load(in);

            String url = props.getProperty("_GET_CONFIG");

            String companySeq = props.getProperty("_COMPANY_SEQ");
            String siteSeq = props.getProperty("_SITE_SEQ");
            String ip = props.getProperty("_CRAWLER_IP");
            
            String dbIp = props.getProperty("_DB_IP");
            String dbPort = props.getProperty("_DB_PORT");
            String dbSchema = props.getProperty("_DB_SCHEMA");
            String dbUsername = props.getProperty("_DB_USERNAME");
            String dbPassword = props.getProperty("_DB_PASSWORD");
            boolean testLocalWang = Boolean.valueOf(props.getProperty("_TEST_LOCAL_WANG"));
            boolean testLocalQiu = Boolean.valueOf(props.getProperty("_TEST_LOCAL_QIU"));
            boolean testLocal = Boolean.valueOf(props.getProperty("_TEST_LOCAL"));
            String apiUserToken = props.getProperty("_API_USER_TOKEN");
            InetAddress ia = SystemUtil.getFirstNonLoopbackAddress(true, false);
            if (ia == null) {
                throw new SystemException("不能取得本机IP!!");
            }
           
            
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
            url = String.format(url, companySeq, siteSeq, ip, apiUserToken);
            
            logger.info("CrawlerGetConfig.php URL: --:" + url);
            
            Constant.LocalConfig.CONFIG_PHP_URL = url;
            Constant.LocalConfig.DB_IP = dbIp;
            Constant.LocalConfig.DB_PORT = dbPort;
            Constant.LocalConfig.DB_SCHEMA = dbSchema;
            Constant.LocalConfig.DB_USERNAME = dbUsername;
            Constant.LocalConfig.DB_PASSWORD = dbPassword;
            Constant.LocalConfig.SYSTEM = "CRAWLER";
            
        }
    }
}
