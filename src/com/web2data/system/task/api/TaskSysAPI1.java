package com.web2data.system.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskSysAPI1 {

	
	public long lastCallingAPI = 0;
	
	public long lastUpdateTime = 0;
	
	
	private static Map<String, String[]> user_id_and_task_host_id_to_active_test_step_ids_MAP = null;
	private static Map<String, String[]> user_id_and_task_host_id_to_active_prod_step_ids_MAP = null;
	
	
	public static void call( int userId, int taskHostId ) {
		
		//
		// 根据 lastCallingAPI 判断需要再次调用API的时机
		
		//
		user_id_and_task_host_id_to_active_test_step_ids_MAP = new HashMap<String, String[]>();
		user_id_and_task_host_id_to_active_test_step_ids_MAP.put("1-1", new String[]{"1-2-1","1-2-2","1-2-3"});
		
		//
		user_id_and_task_host_id_to_active_prod_step_ids_MAP = new HashMap<String, String[]>();
		user_id_and_task_host_id_to_active_prod_step_ids_MAP.put("1-1", new String[]{"1-2-1","1-2-2","1-2-3"});

	}
	
	
	public static String[] getUserActiveTestStepsFromTaskHost( int userId, int taskHostId ) {
		
		call( userId, taskHostId );
		
		return user_id_and_task_host_id_to_active_test_step_ids_MAP.get( userId+"-"+taskHostId );
	}
	
	
	public static String[] getUserActiveProdStepsFromTaskHost( int userId, int taskHostId ) {
		
		call( userId, taskHostId );
		
		return user_id_and_task_host_id_to_active_prod_step_ids_MAP.get( userId+"-"+taskHostId );
	}
	
}
