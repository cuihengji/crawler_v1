package com.rkylin.crawler.engine.flood.scheduler.client;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.rkylin.crawler.engine.flood.model.CachedAppList;
import com.web2data.system.config._main.ConfigFacade;
import com.web2data.system.config.entity.User_apps_scheduler;

public class SchedulerAppListCacheManager {

	private static final Logger logger = Logger.getLogger(SchedulerAppListCacheManager.class);

	private static SchedulerAppListCacheManager instance = null;
	
	private static ConcurrentHashMap<String, CachedAppList> appListCache = new ConcurrentHashMap<String, CachedAppList>();

	private SchedulerAppListCacheManager() {

	}

	public static SchedulerAppListCacheManager getInstance() {

		if (instance == null) {
			instance = new SchedulerAppListCacheManager();
			logger.info("初始化SchedulerApplistCache.");
		}
		return instance;
	}

	public List<User_apps_scheduler> getActiveAppListByUser(int userSeq, int cloudSeq, boolean isBrowserSession) {

		List<User_apps_scheduler> appList = null;

		int schedulerSeq = ConfigFacade.getInstance().getSchedulerHostSeq();
		String appListKey = schedulerSeq + "_" + userSeq + "_" + cloudSeq + "_" + isBrowserSession;

		Lock lock = new ReentrantLock();  
		
		
		try {
			lock.lock();  
			CachedAppList cachedAppList = appListCache.get(appListKey);
			// Key不存在, 调用SCH015_GetActiveAppListByUser.php
			if (cachedAppList == null) {
				appList = retrieveLastestAppList(appListKey, userSeq, cloudSeq, isBrowserSession);
			} else {
				// 取出Key,检查时间时候超时,如果不超时则返回,否则重新调用SCH015_GetActiveAppListByUser.php
				long cachedTime = cachedAppList.getSavedTime();
				logger.info("cachedTime: " + cachedTime );
				long currentTime = System.currentTimeMillis();
				logger.info("currentTime: " + currentTime );
				long differenceTime = (currentTime - cachedTime);
				logger.info("differenceTime: " + differenceTime );
				long livedSeconds =  differenceTime/ 1000;
				long maxCachedSeconds = ConfigFacade.getInstance().getAppListCacheSeconds();
				logger.info("livedSeconds: " + livedSeconds + " -- maxCachedSeconds: " + maxCachedSeconds);

				if (livedSeconds < maxCachedSeconds) {
					appList = cachedAppList.getAppList();
				} else {
					// 重新调用SCH015_GetActiveAppListByUser.php
					appList = retrieveLastestAppList(appListKey, userSeq, cloudSeq, isBrowserSession);
				}
			}

			return appList;
			
		} finally {
			lock.unlock();
		}
	}

	private List<User_apps_scheduler> retrieveLastestAppList(String appListKey, int userSeq, int cloudSeq, boolean isBrowserSession) {
		
		List<User_apps_scheduler> appList = SchedulerFacade.getInstance().getActiveAppListByUser(userSeq, cloudSeq, isBrowserSession);
		
		if (appList != null) {
			CachedAppList cachedAppList = new CachedAppList(appList, System.currentTimeMillis());
			appListCache.put(appListKey, cachedAppList);
//			logger.info("appListCache.put(appListKey, firstCachedAppList): " + appListKey + "-" + cachedAppList);
			return appList;
		}
		return null;
	}

}
