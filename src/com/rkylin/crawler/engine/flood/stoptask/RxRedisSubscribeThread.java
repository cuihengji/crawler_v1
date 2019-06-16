package com.rkylin.crawler.engine.flood.stoptask;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPubSub;


import com.rkylin.crawler.engine.flood.redis.RedisUtils;
import com.ruixuesoft.crawler.open.RxResult;

public class RxRedisSubscribeThread extends Thread {
	
	
    private static RxRedisSubscribeThread instance = null;
    private int crawlerSeq;

//    public static RxRedisSubscribeThread getInstance() {
//    	
//        if (instance == null) {
//        	instance = new RxRedisSubscribeThread();
//        }
//        
//        return instance;
//    }
    
    public static RxRedisSubscribeThread getInstance(int crawlerSeq) {
    	
        if (instance == null) {
        	instance = new RxRedisSubscribeThread(crawlerSeq);
        }
        
        return instance;
    }
	
    private RxRedisSubscribeThread(int crawlerSeq) {
    	
		super();
		super.setName("RxRedisSubscribeThread");
		this.crawlerSeq = crawlerSeq;
	}
    
	private static final Logger logger = Logger.getLogger(RxRedisSubscribeThread.class);

//	private final Subscriber subscriber = new Subscriber();
//
//	private final String CHANNEL = "rxpushChannel";
	
	private final String STOPTASK = "STOPTASK_";
	
	private ConcurrentHashMap <String, Future <RxResult> > tasksMap = new ConcurrentHashMap <String, Future <RxResult> >();
	
	
	@Override
	public void run() {
		
		while ( true ) {
			
			logger.info( "RxRedisSubscribeThread while start");
			
			Jedis jedis = null;
			
			try {
				
				jedis = RedisUtils.getJedis();
//				jedis.subscribe( subscriber, CHANNEL );
				
				logger.info( "RxRedisSubscribeThread redis 实例获取成功！");
				
				while ( true ) {
					
					String message = jedis.rpop( STOPTASK + this.crawlerSeq );
				
					processMsg(message);
					
					sleepSeconds(2);
				}
				
			} catch (Exception e) {
				
				sleepSeconds(1);
				
				logger.error( "RxRedisSubscribeThread redis 实例获取失败！" + e.getMessage() );

			} finally {
				
				try {
					
					if (jedis != null) {

						jedis.close();
						
						logger.info( "RxRedisSubscribeThread redis close");
					}
					
				} catch (Exception e) {
					
					logger.error( "RxRedisSubscribeThread redis 实例关闭失败！" + e.getMessage() );
					
				}
				
				logger.info( "RxRedisSubscribeThread redis finally");
			}
			
			logger.info( "RxRedisSubscribeThread while end");
			
		}
		
	}

	/** 根据获取到的消息取消被停止的任务的执行
	 * @param message
	 */
	private void processMsg( String message ) {
	
		try {
			
			if ( message != null && !StringUtils.isEmpty(message) ) {
				
				logger.info("从redis服务器收到的订阅消息:" + message);

				// 消息格式为STOP|taskSeq|appSeq|scenarioIndex|ruleIndex
				String[] messageArr = StringUtils.split(message, "|");

				if ("STOP".equals(messageArr[0])) {

					// Task_<appSeq>_<scenarioIndex>_<ruleIndex>_<taskSeq>,
					String key = "Task_" + messageArr[2] + "_" + messageArr[3] + "_" + messageArr[4] + "_" + messageArr[1];

					Future<RxResult> future = tasksMap.get(key);
					if (future != null) {

						future.cancel(true);

						tasksMap.remove(key);
					}
				}
			}
			
		} catch (Exception e) {

			logger.error("redis 消息处理失败！" + e.getMessage());

		}
		
	}

	public void putTasksMap ( String key,  Future <RxResult> future) {
	
		tasksMap.put( key, future );
		
	}
	
	public void removeTasksMap ( String key) {
		
		tasksMap.remove( key );
		
	}
	
    // 睡眠指定秒数
    private void sleepSeconds( int arg ) {
    	try {
            Thread.sleep(arg * 1000);
        } catch (InterruptedException e) {
            logger.info(e.getMessage(), e);
        }
    }
	
//	public static void main(String[] args) {
//
//		
//		RxRedisSubscribeThread rxRedisSubscribeThread = RxRedisSubscribeThread.getInstance();
//		rxRedisSubscribeThread.start();
//
//	}
	
	
//	class Subscriber extends JedisPubSub {
//		
//		public Subscriber() {
//			
//	    }
//
//		@Override
//		public void onMessage(String channel, String message) {
//
//			logger.info("从redis服务器收到的订阅消息:" + message);
//			
//			try {
//				
//				if (message != null && !StringUtils.isEmpty(message)) {
//
//					// 消息格式为STOP|taskSeq|appSeq|scenarioIndex|ruleIndex
//					String[] messageArr = StringUtils.split(message, "|");
//
//					if ("STOP".equals(messageArr[0])) {
//
//						// Task_<appSeq>_<scenarioIndex>_<ruleIndex>_<taskSeq>,
//						String key = "Task_" + messageArr[2] + "_"
//								+ messageArr[3] + "_" + messageArr[4] + "_"
//								+ messageArr[1];
//
//						Future<RxResult> future = tasksMap.get(key);
//						if (future != null) {
//
//							future.cancel(true);
//
//							tasksMap.remove(key);
//
//						}
//					}
//
//				}
//				
//			} catch (Exception e) {
//
//				logger.error("redis 消息处理失败！" + e.getMessage());
//
//			}
//
//		}
//
//	}
}
