package com.rkylin.crawler.engine.flood.pausecrawler;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.rkylin.crawler.engine.flood.crawler.CrawlerManager;
import com.rkylin.crawler.engine.flood.redis.RedisUtils;
import com.rkylin.crawler.engine.flood.tools.ReportHostStatusTool;
import com.rkylin.crawler.engine.flood.util.FileUtil;

import redis.clients.jedis.Jedis;

public class RxRedisPauseFetchThread extends Thread{

	private static final Logger logger = Logger.getLogger(RxRedisPauseFetchThread.class);
	private final String PAUSE_FETCH = "PAUSEFETCH_";
	
	private static RxRedisPauseFetchThread instance = null;
    private int crawlerSeq;
    
    
	private RxRedisPauseFetchThread(int crawlerSeq) {
		super();
		super.setName("RxRedisPauseFetchThread");
		this.crawlerSeq = crawlerSeq;
	}

	public static RxRedisPauseFetchThread getInstance(int crawlerSeq) {

		if (instance == null) {
			instance = new RxRedisPauseFetchThread(crawlerSeq);
		}
		return instance;
	}
	
	
	@Override
	public void run() {

		while (true) {
			logger.info("RxRedisPauseFetchThread while start");
			Jedis jedis = null;
			try {
				jedis = RedisUtils.getJedis();
				//循环取消息
				while (true) {
					String message = jedis.rpop(PAUSE_FETCH + this.crawlerSeq);
					processMsg(message);
					sleepSeconds(2);
				}
				
			} catch (Exception e) {
				sleepSeconds(1);
				logger.error("RxRedisPauseFetchThread redis 实例获取失败！" + e);
			} finally {
				logger.info("RxRedisPauseFetchThread redis finally");
				try {
					if (jedis != null) {
						jedis.close();
						logger.info("RxRedisPauseFetchThread redis close");
					}
				} catch (Exception e) {
					logger.error("RxRedisPauseFetchThread redis 实例关闭失败！" + e);
				}
			}
			logger.info("RxRedisPauseFetchThread while end");
		}
	}
	
	/** 根据获取到的消息暂停或者重启Crawler Session
	 * @param message
	 */
	private void processMsg( String message ) {
	
		try {
			if (message != null && !StringUtils.isEmpty(message)) {
				logger.info("RxRedisPauseFetchThread从redis服务器收到POP消息:" + message);

				// 格式为PAUSE_FETCH或者RESUME_FETCH
				if (message.equalsIgnoreCase("PAUSE_FETCH")) {
					CrawlerManager.setPauseFetchMsg(true);
				} else if(message.equalsIgnoreCase("RESTART_MACHINE")){
					CrawlerManager.setPauseFetchMsg(true);
					restartComputer();
				}
				else {
					CrawlerManager.setPauseFetchMsg(false);
				}
			}
		} catch (Exception e) {
			logger.error("redis 消息处理失败！" + e);
		}
	}

	// 睡眠指定秒数
	private static void sleepSeconds(int arg) {
		try {
			Thread.sleep(arg * 1000);
		} catch (InterruptedException e) {
			logger.info(e.getMessage(), e);
		}
	}
    
	public static void restartComputer() {

		logger.info("RxRedisPauseFetchThread将要重启计算机");

		Date startingTime = new Date();
		
		logger.info("RxRedisPauseFetchThread将要重启计算机: startingTime-> " +startingTime);

		while (true) {
			if (isAllSessionPaused() || isElapsed35Minutes(startingTime)) {
				FileUtil.restartComputer();
			}
			sleepSeconds(10);
		}
	}

	private static boolean isAllSessionPaused() {

		ConcurrentHashMap<Integer, SessionStatus> sessionStatusMap = ReportHostStatusTool.getSessionStatusMap();

//		for (Integer session : sessionStatusMap.keySet()) {
//			System.out.println("session Id: " + session);
//			System.out.println(sessionStatusMap.get(session).isPausedFetchMessage() + "|" + sessionStatusMap.get(session).getUpdatedTime() );
//		}
		
		for (SessionStatus status : sessionStatusMap.values()) {
			
			logger.info("RxRedisPauseFetchThread将要重启计算机: status.isPausedFetchMessage()->" + status.isPausedFetchMessage() +" last update data time->"+ status.getUpdatedTime() );
			if (!status.isPausedFetchMessage()) {
				return false;
			}
		}
		logger.info("RxRedisPauseFetchThread将要重启计算机: SessionStatus is all True" );

		return true;
	}
	
	private static boolean isElapsed35Minutes(Date startingTime) {
		
		logger.info("RxRedisPauseFetchThread将要重启计算机: startingTime-> " + startingTime + " currentTime:->" + new Date());

		long minutes = TimeUnit.MILLISECONDS.toMinutes(new Date().getTime()) - TimeUnit.MILLISECONDS.toMinutes(startingTime.getTime());

		if (minutes >= 35) {
			return true;
		}

		return false;
	}
}
