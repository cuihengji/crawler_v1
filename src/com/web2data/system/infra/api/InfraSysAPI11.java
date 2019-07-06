package com.web2data.system.infra.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.web2data._global.SessionThreadLocal;
import com.web2data._global.SessionType;
import com.web2data.system.entity.infra_api;
import com.web2data.system.entity.infra_config;
import com.web2data.system.entity.infra_host;
import com.web2data.system.entity.org_database_host;
import com.web2data.system.entity.org_factory_host;
import com.web2data.system.entity.org_proxy_account;
import com.web2data.system.entity.org_task_host;
import com.web2data.system.entity.team;
import com.web2data.system.entity.team_factory_worker;
import com.web2data.system.entity.user;
import com.web2data.system.entity.user_app_host;
import com.web2data.system.entity.user_database_host;
import com.web2data.system.entity.user_proxy_provider;
import com.web2data.system.entity.user_team;
import com.web2data.system.entity.user_verify_provider;

public class InfraSysAPI11 {

	
	public long lastCallingAPI = 0;
	
	public long lastUpdateTime = 0;
	
	
	private static Map<Integer, HashMap<Integer, int[]>> 
		user_id_2_senior_worker_22_task_host_ids_MAP = new HashMap<Integer, HashMap<Integer, int[]>>();

	private static Map<Integer, HashMap<Integer, int[]>> 
		user_id_2_junior_worker_22_task_host_ids_MAP = new HashMap<Integer, HashMap<Integer, int[]>>();

	private static Map<Integer, org_task_host> 
		org_task_host_id_2_object_MAP = new HashMap<Integer, org_task_host>();

		
	
	// 获取 senior_worker_22_task_host_ids_MAP
	// select distinct(e.id) as x1
	// from org_factory_host a, team_factory_worker b, step_team c, app d, org_task_host e
	// where a.id = <?> and a.is_used = 'Y' and a.id = b.factory_host_id
	// and b.<s1> = 1 and b.team_id = c.team_id
	// and c.user_id = <?> and c.app_id = d.id
	// and d.task_host_id = e.id
	// and e.is_used = 'Y';
	
	// select distinct(e.id) as x1
	// from org_factory_host a, team_factory_worker b, step_team c, app d, org_task_host e
	// where a.id = <?> and a.is_used = 'Y' and a.id = b.factory_host_id
	// and b.<s1> = 1 and b.team_id = c.team_id
	// and c.user_id = <?> and c.app_id = d.id
	// and d.task_host_id = e.id
	// and e.is_used = 'Y';
		
	// 获取 junior_worker_22_task_host_ids_MAP

	
	// 获得该 user 在 所有的 worker 上可以执行的app,隶属的 task host list
	public static void call( int userId ) {
		// call Infra-API-1
		
		// INFRA_HOST: 1.2.3.4:8080
		// FACTORY_HOST_ID: 123
		
		// 请求参数，必须要包括一个sinceLastUpdateTime，如果Infra端的数据自lastUpdateTime后没有修改，则不返回数据，返回code=304
		
		// 返回的json有
		
		HashMap<Integer, int[]> senior_worker_22_task_host_ids_MAP = new HashMap<Integer, int[]>();
		senior_worker_22_task_host_ids_MAP.put(1, new int[]{1,2,3,4,5});
		senior_worker_22_task_host_ids_MAP.put(2, new int[]{1,2,3,4,5});
		senior_worker_22_task_host_ids_MAP.put(3, new int[]{1,2,3,4,5});
		
		user_id_2_senior_worker_22_task_host_ids_MAP.put(userId, senior_worker_22_task_host_ids_MAP);
		
		HashMap<Integer, int[]> junior_worker_22_task_host_ids_MAP = new HashMap<Integer, int[]>();
		junior_worker_22_task_host_ids_MAP.put(1, new int[]{1,2,3,4,5});
		junior_worker_22_task_host_ids_MAP.put(2, new int[]{1,2,3,4,5});
		junior_worker_22_task_host_ids_MAP.put(3, new int[]{1,2,3,4,5});
		
		user_id_2_junior_worker_22_task_host_ids_MAP.put(userId, junior_worker_22_task_host_ids_MAP);
		
		
		org_task_host_id_2_object_MAP.put(1, new org_task_host());
		org_task_host_id_2_object_MAP.put(2, new org_task_host());
		org_task_host_id_2_object_MAP.put(3, new org_task_host());
	}
	
	
	
	public static Integer getUserRandomTaskHostIdForTheWorker( int userId ) {
		
		call( userId );
		
		int sessionType = SessionThreadLocal.getSessionType();
		int sessionIndex = SessionThreadLocal.getSessionIndex();
		
		Map<Integer, HashMap<Integer, int[]>> temp1 = null;
		if ( sessionType == SessionType.SENIOR ) temp1 = user_id_2_senior_worker_22_task_host_ids_MAP;
		if ( sessionType == SessionType.JUNIOR ) temp1 = user_id_2_junior_worker_22_task_host_ids_MAP;
		
		HashMap<Integer, int[]> temp2 = temp1.get(userId);
		if (temp2.get( sessionIndex ) == null) return null;
		
		int[] temp3 = temp2.get( sessionIndex );
		
		return temp3[ new Random().nextInt( temp3.length ) ];
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
//	{
//		  "code": 200,
//		  "data": {
//		    
//		    "senior_worker_task_host_ids_MAP": [
//		      {
//		        "index": 1,
//		        "task_host_ids": "1,2,3,4,5"
//		      },
//		      {
//		        "index": 2,
//		        "task_host_ids": "1,2,3,4,5"
//		      },
//		      {
//		        "index": 3,
//		        "task_host_ids": "1,2,3,4,5"
//		      }
//		    ],
//		    
//		    "junior_worker_task_host_ids_MAP": [
//		      {
//		        "index": 1,
//		        "task_host_ids": "1,2,3,4,5"
//		      },
//		      {
//		        "index": 2,
//		        "task_host_ids": "1,2,3,4,5"
//		      },
//		      {
//		        "index": 3,
//		        "task_host_ids": "1,2,3,4,5"
//		      }
//		    ],
//		    
//		    "org_task_host_id_2_object_MAP": [
//		      {
//		        "id": 1,
//		        "object": {
//		          "prop1": "1111",
//		          "prop2": "2222",
//		          "prop3": "3333"
//		        }
//		      },
//		      {
//		        "id": 2,
//		        "object": {
//		          "prop1": "1111",
//		          "prop2": "2222",
//		          "prop3": "3333"
//		        }
//		      },
//		      {
//		        "id": 3,
//		        "object": {
//		          "prop1": "1111",
//		          "prop2": "2222",
//		          "prop3": "3333"
//		        }
//		      }
//		    ]
//		    
//		  }
//		}
	
	
}
