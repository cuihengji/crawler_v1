package com.web2data.system.infra;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web2data._global.G;
import com.web2data._global.SessionThreadLocal;
import com.web2data._global.SessionType;

public class InfraSysAPI2 {

	
	public long lastCallingAPI = 0;
	
	public long lastUpdateTime = 0;
	
	
	
	private static Map<Integer, HashMap<Integer, String[]>> 
		user_id_2_senior_worker_design_test_step_ids_MAP = new HashMap<Integer, HashMap<Integer, String[]>>();
	
	private static Map<Integer, HashMap<Integer, String[]>> 
		user_id_2_senior_worker_design_prod_step_ids_MAP = new HashMap<Integer, HashMap<Integer, String[]>>();
	
	private static Map<Integer, HashMap<Integer, String[]>> 
		user_id_2_junior_worker_design_test_step_ids_MAP = new HashMap<Integer, HashMap<Integer, String[]>>();

	private static Map<Integer, HashMap<Integer, String[]>> 
		user_id_2_junior_worker_design_prod_step_ids_MAP = new HashMap<Integer, HashMap<Integer, String[]>>();
	

	private static void call( int userId ) {
		// 判断是否过期，超过60秒则调用 API，不超过则直接使用缓存
		// 注意要把 factoryId传过去
		//G.FACTORY_HOST_ID;
		
			HashMap<Integer, String[]> temp1 = new HashMap<Integer, String[]>();
			temp1.put(1, new String[]{"1-2-1","1-2-2","1-2-3"}); 					// senior worker 1 - test 
			temp1.put(2, new String[]{"1-2-1","1-2-2","1-2-3"}); 					// senior worker 2 - test 
			temp1.put(3, new String[]{"1-2-1","1-2-2","1-2-3"}); 					// senior worker 3 - test 
			user_id_2_senior_worker_design_test_step_ids_MAP.put(userId, temp1); 	// senior worker 4 - test 
			
			//
			HashMap<Integer, String[]> temp3 = new HashMap<Integer, String[]>();
			temp3.put(1, new String[]{"1-2-1","1-2-2","1-2-3"});
			temp3.put(2, new String[]{"1-2-1","1-2-2","1-2-3"});
			temp3.put(3, new String[]{"1-2-1","1-2-2","1-2-3"});
			user_id_2_senior_worker_design_prod_step_ids_MAP.put(userId, temp3);
			
			//
			HashMap<Integer, String[]> temp2 = new HashMap<Integer, String[]>();
			temp2.put(1, new String[]{"1-2-1","1-2-2","1-2-3"});
			temp2.put(2, new String[]{"1-2-1","1-2-2","1-2-3"});
			temp2.put(3, new String[]{"1-2-1","1-2-2","1-2-3"});
			user_id_2_junior_worker_design_test_step_ids_MAP.put(userId, temp2);
			
			//
			HashMap<Integer, String[]> temp4 = new HashMap<Integer, String[]>();
			temp4.put(1, new String[]{"1-2-1","1-2-2","1-2-3"});
			temp4.put(2, new String[]{"1-2-1","1-2-2","1-2-3"});
			temp4.put(3, new String[]{"1-2-1","1-2-2","1-2-3"});
			user_id_2_junior_worker_design_prod_step_ids_MAP.put(userId, temp4);
	}
	

	public static String[] getTheWorkerUserDesignTestSteps( int userId ) {
		
		call( userId );
		
		
		int sessionType = SessionThreadLocal.getSessionType();
		int sessionIndex = SessionThreadLocal.getSessionIndex();
		
		Map<Integer, HashMap<Integer, String[]>> temp1 = null;
		if ( sessionType == SessionType.SENIOR ) temp1 = user_id_2_senior_worker_design_test_step_ids_MAP;
		if ( sessionType == SessionType.JUNIOR ) temp1 = user_id_2_junior_worker_design_test_step_ids_MAP;
		
		HashMap<Integer, String[]> temp2 = null;
		temp2 = temp1.get(userId);
		
		// Design
		return temp2.get( sessionIndex );
	}
	
	
	public static String[] getTheWorkerUserDesignProdSteps( int userId ) {
		
		call( userId );
		
		
		int sessionType = SessionThreadLocal.getSessionType();
		int sessionIndex = SessionThreadLocal.getSessionIndex();
		
		Map<Integer, HashMap<Integer, String[]>> temp1 = null;
		if ( sessionType == SessionType.SENIOR ) temp1 = user_id_2_senior_worker_design_prod_step_ids_MAP;
		if ( sessionType == SessionType.JUNIOR ) temp1 = user_id_2_junior_worker_design_prod_step_ids_MAP;
		
		HashMap<Integer, String[]> temp2 = null;
		temp2 = temp1.get(userId);
		
		// Design
		return temp2.get( sessionIndex );
	}
	
	
	
//	{
//		  "code": 200,
//		  "data": {
//		    
//		    "user_id": "1",
//		    
//		    "factory_id": "1",
//		    
//		    "senior_worker_index_2_user_design_step_ids_MAP": [
//		      {
//		        "index": "1",
//		        "step_ids": "1-1-1,1-1-2,1-1-3,1-1-4"
//		      },
//		      {
//		        "index": "2",
//		        "step_ids": "1-1-1,1-1-2,1-1-3,1-1-4"
//		      },
//		      {
//		        "index": "3",
//		        "step_ids": "1-1-1,1-1-2,1-1-3,1-1-4"
//		      }
//		    ],
//		    
//		    "junior_worker_index_2_user_design_step_ids_MAP": [
//		      {
//		        "index": "1",
//		        "step_ids": "1-1-1,1-1-2,1-1-3,1-1-4"
//		      },
//		      {
//		        "index": "2",
//		        "step_ids": "1-1-1,1-1-2,1-1-3,1-1-4"
//		      },
//		      {
//		        "index": "3",
//		        "step_ids": "1-1-1,1-1-2,1-1-3,1-1-4"
//		      }
//		    ]
//		    
//		  }
//		}
	
}
