package com.web2data._global;

import java.io.File;

import com.rkylin.crawler.engine.flood.common.Constant.WebDriverConfig;
import com.web2data.system.config._main.ConfigFacade;

public class G {

	
	// 以下这些信息都应该从 Config.properties 获得
	
	public static final String CRAWKER_ENV = "TEST";
	
	public static final int CRAWLER_SEQ = 123;
	
	public static final String PLATFORM_IP = "123.123.123.123";
	
	public static final int PLATFORM_PORT = 80;
	
	public static final String CHROME_SOFTWARE_ROOT_DIR = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
	
	public static String rootDirectory = null;
	
	static {
		if ( ConfigFacade.isWindowsHost() ) {
			rootDirectory = WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.CHROME_DATA;
		} else {
			rootDirectory = File.separator + WebDriverConfig.DATA_DIR + File.separator + WebDriverConfig.CHROME_DATA;
		}
	}
    
	public static final String getBrowserSessionDiskCacheRoot( int sessionIndex ) {
		return rootDirectory + File.separator + "chrome" + sessionIndex + File.separator + WebDriverConfig.DISC_CACHE_DIR;
	}

	public static final String getBrowserSessionUserDataRoot( int sessionIndex ) {
		return rootDirectory + File.separator + "chrome" + sessionIndex + File.separator + WebDriverConfig.USER_DATA_DIR;
	}

}
