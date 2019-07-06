package com.web2data.system.infra.api;

import java.util.HashMap;
import java.util.Map;

import com.web2data._global.SessionThreadLocal;
import com.web2data._global.SessionType;
import com.web2data.system.entity.task_control;

public class InfraSysAPI4 {

	public long lastCallingAPI = 0;
	
	public long lastUpdateTime = 0;
	
	
	
	private static Map<String, task_control> 
		step_id_2_task_control_object_MAP = new HashMap<String, task_control>();
	
	
	//public static Map<String, String[]> senior_worker_index_and_user_id_2_design_step_ids_MAP = null;
	
	//public static Map<String, String[]> junior_worker_index_and_user_id_2_design_step_ids_MAP = null;
	
	
	private static void call( String stepId ) {
		
		// 如果lastCallingAPI超过15秒，则重新调用API
		
		task_control temp = new task_control();
		temp.setH0( 10 ); temp.setH1( 10 ); temp.setH2( 10 ); temp.setH3( 10 ); temp.setH4( 10 );
		temp.setH5( 10 ); temp.setH6( 10 ); temp.setH7( 10 ); temp.setH8( 10 ); temp.setH9( 10 );
		temp.setH10( 10 ); temp.setH11( 10 ); temp.setH12( 10 ); temp.setH13( 10 ); temp.setH14( 10 );
		temp.setH15( 10 ); temp.setH16( 10 ); temp.setH17( 10 ); temp.setH18( 10 ); temp.setH19( 10 );
		temp.setH20( 10 ); temp.setH21( 10 ); temp.setH22( 10 ); temp.setH23( 10 );
		
		step_id_2_task_control_object_MAP.put(stepId, temp);
		
	}
	
	
	public static task_control getTaskPolicy( String stepId ) {
		
		call( stepId );
		
		return step_id_2_task_control_object_MAP.get(stepId);
	}
	
//	public static String[] getUserPolicyedStepList( int userId ) {
//		
//		// 判断是否过期，超过15秒则调用 API，不超过则直接使用缓存
//		// 注意要把 factoryId传过去
//		//G.FACTORY_HOST_ID;
//		{
//			HashMap<Integer, String[]> temp = new HashMap<Integer, String[]>();
//			temp.put(1, new String[]{"1-2-1","1-2-2","1-2-3"});
//			temp.put(2, new String[]{"1-2-1","1-2-2","1-2-3"});
//			temp.put(3, new String[]{"1-2-1","1-2-2","1-2-3"});
//			user_id_2_design_step_ids_MAP_senior.put(userId, temp);
//			
//			//
//			HashMap<Integer, String[]> temp2 = new HashMap<Integer, String[]>();
//			temp2.put(1, new String[]{"1-2-1","1-2-2","1-2-3"});
//			temp2.put(2, new String[]{"1-2-1","1-2-2","1-2-3"});
//			temp2.put(3, new String[]{"1-2-1","1-2-2","1-2-3"});
//			user_id_2_design_step_ids_MAP_junior.put(userId, temp2);
//		}
//		
//		
//		int sessionType = SessionThreadLocal.getSessionType();
//		int sessionIndex = SessionThreadLocal.getSessionIndex();
//		
//		Map<Integer, HashMap<Integer, String[]>> temp1 = null;
//		if ( sessionType == SessionType.SENIOR ) temp1 = user_id_2_design_step_ids_MAP_senior;
//		if ( sessionType == SessionType.JUNIOR ) temp1 = user_id_2_design_step_ids_MAP_junior;
//		
//		HashMap<Integer, String[]> temp2 = null;
//		temp2 = temp1.get(userId);
//		
//		// Design
//		return temp2.get( sessionIndex );
//	}
	
}
