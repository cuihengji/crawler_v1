package com.web2data.system.task.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rkylin.crawler.engine.flood.util.FileUtil;
import com.rkylin.crawler.engine.flood.util.HTTP;
import com.rkylin.crawler.engine.flood.util.JsonUtil;
import com.web2data._global.G2;
import com.web2data.utility.U;

public class API310GetQueueListInTheUserTaskHost {

	public static int _code = -1; // 200 - OK
	private static Map<String, List<String>> _key_testqueues_map = new HashMap<String, List<String>>();
	private static Map<String, List<String>> _key_prodqueues_map = new HashMap<String, List<String>>();
	
	
	private static Map<String, Long> test_timeMillis_map = new HashMap<String, Long>();
	private static Map<String, Long> prod_timeMillis_map = new HashMap<String, Long>();
	
	

	public static List<String> findTestQueueListInTheUserTaskHost( 
			int userId, int taskHostId ) {
    	return findQueueListInTheUserTaskHost( userId, taskHostId, G2.TEST );
	}
	
	public static List<String> findProdQueueListInTheUserTaskHost( 
			int userId, int taskHostId ) {
    	return findQueueListInTheUserTaskHost( userId, taskHostId, G2.PROD );
	}

	
	public static synchronized List<String> findQueueListInTheUserTaskHost( 
			int userId, int taskHostId, int queueType ) {
		
		String key = userId+"-"+taskHostId;
		
		Map<String, Long> temp = null;
    	if ( queueType == G2.TEST ) {
    		temp = test_timeMillis_map;
    	} else {
    		temp = prod_timeMillis_map;
    	}
    	
    	Long lastReloadTime = temp.get(key);
    	if ( lastReloadTime == null ) 
    		lastReloadTime = 0L;
		
		if ( (System.currentTimeMillis() - lastReloadTime) > 5*1000 ) {
			reload(key);
			temp.put(key, System.currentTimeMillis());
		}
		
		if ( _code != 200 ) {
			return null;
		}
		
		if ( G2.TEST == queueType )
			return _key_testqueues_map.get(key); // 应该复制一份
		
		return _key_prodqueues_map.get(key); // 应该复制一份
	}
	
	
	private static void reload( String userIdTaskHostId ) {
		
		String file = null;
		
		try {
			
			int[] ints = U.convertStringToInts( userIdTaskHostId );
		
			if ( G2.factory_host_env.equals( G2.LOCAL ) ) {
				file = FileUtil.read( G2.factory_host_data_root 
						+ String.format(G2.EMULATE_TASK_USER_TASKHOST_QUEUE_JSON, ints[0], ints[1]) );
			} else {
				file = HTTP.doGet("");
			}
		
			API310GetQueueListInTheUserTaskHost json 
				= JsonUtil.convertJsonStr2Obj(file, API310GetQueueListInTheUserTaskHost.class);
		
			_code = json.getCode();
			_key_testqueues_map.put(userIdTaskHostId, json.getTest_queue_list());
			_key_prodqueues_map.put(userIdTaskHostId, json.getProd_queue_list());
			
		} catch (Exception e) {
			//
			System.out.println( "userIdTaskHostId: " + userIdTaskHostId );
			System.out.println( "Exception: API310GetQueueListInTheUserTaskHost - reload(): " + e.getMessage() );
		}
	}
	
	
	// -----------------------------------------------------------------------------
	public int code = -1;
	public String message = null;

	public List<String> test_queue_list = null;
	public List<String> prod_queue_list = null;

	public API310GetQueueListInTheUserTaskHost() {}


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getTest_queue_list() {
		return test_queue_list;
	}

	public void setTest_queue_list(List<String> test_queue_list) {
		this.test_queue_list = test_queue_list;
	}

	public List<String> getProd_queue_list() {
		return prod_queue_list;
	}

	public void setProd_queue_list(List<String> prod_queue_list) {
		this.prod_queue_list = prod_queue_list;
	}

	public static void main(String[] args) {

		List<String> temp1 = findTestQueueListInTheUserTaskHost( 123, 1 );
		System.out.println("temp1 = " + temp1.size());
		
		List<String> temp2 = findProdQueueListInTheUserTaskHost( 123, 1 );
		System.out.println("temp2 = " + temp2.size());
		
	}
}
