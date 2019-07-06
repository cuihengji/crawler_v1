package com.web2data.system.infra.api;

import java.util.HashMap;
import java.util.Map;

import com.web2data._global.SessionThreadLocal;
import com.web2data._global.SessionType;

public class InfraSysAPI3 {

	
	public long lastCallingAPI = 0;
	
	public long lastUpdateTime = 0;
	
	
	
	private static Map<Integer, HashMap<Integer, String[]>> 
		user_id_2_design_step_ids_MAP_senior = new HashMap<Integer, HashMap<Integer, String[]>>();
	
	private static Map<Integer, HashMap<Integer, String[]>> 
		user_id_2_design_step_ids_MAP_junior = new HashMap<Integer, HashMap<Integer, String[]>>();
	
	
	//public static Map<String, String[]> senior_worker_index_and_user_id_2_design_step_ids_MAP = null;
	
	//public static Map<String, String[]> junior_worker_index_and_user_id_2_design_step_ids_MAP = null;
	
	
	
	
	public static String[] getUserPolicyedStepList( int userId ) {
		
		// 判断是否过期，超过15秒则调用 API，不超过则直接使用缓存
		// 注意要把 factoryId传过去
		//G.FACTORY_HOST_ID;
		{
			HashMap<Integer, String[]> temp = new HashMap<Integer, String[]>();
			temp.put(1, new String[]{"1-2-1","1-2-2","1-2-3"});
			temp.put(2, new String[]{"1-2-1","1-2-2","1-2-3"});
			temp.put(3, new String[]{"1-2-1","1-2-2","1-2-3"});
			user_id_2_design_step_ids_MAP_senior.put(userId, temp);
			
			//
			HashMap<Integer, String[]> temp2 = new HashMap<Integer, String[]>();
			temp2.put(1, new String[]{"1-2-1","1-2-2","1-2-3"});
			temp2.put(2, new String[]{"1-2-1","1-2-2","1-2-3"});
			temp2.put(3, new String[]{"1-2-1","1-2-2","1-2-3"});
			user_id_2_design_step_ids_MAP_junior.put(userId, temp2);
		}
		
		
		int sessionType = SessionThreadLocal.getSessionType();
		int sessionIndex = SessionThreadLocal.getSessionIndex();
		
		Map<Integer, HashMap<Integer, String[]>> temp1 = null;
		if ( sessionType == SessionType.SENIOR ) temp1 = user_id_2_design_step_ids_MAP_senior;
		if ( sessionType == SessionType.JUNIOR ) temp1 = user_id_2_design_step_ids_MAP_junior;
		
		HashMap<Integer, String[]> temp2 = null;
		temp2 = temp1.get(userId);
		
		// Design
		return temp2.get( sessionIndex );
	}
	
	
}
