package com.web2data.system.infra;

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

public class InfraSysAPI1 {

	
	public long lastCallingAPI = 0;
	
	public long lastUpdateTime = 0;
	
	// 全局配置，或者是org-level的配置
	public static List<infra_config> infra_config_LIST = null;
	public static List<infra_api> infra_api_LIST = null;
	public static List<infra_host> infra_host_LIST = null;

	// 与user关联的信息
	// select a.* from org_factory_host a where a.id = ?;
	public static org_factory_host org_factory_host_OBJECT = null;
	
	public static List<team_factory_worker> team_factory_worker_LIST = null;
	
	public static List<team> team_LIST = null;
	
	public static List<user_team> user_team_LIST = null;
	
	public static List<user> user_LIST = null;

	public static List<user_database_host> user_database_host_LIST = null;
	public static List<org_database_host> org_database_host_LIST = null;

	public static List<user_proxy_provider> user_proxy_provider_LIST = null;

	public static List<user_verify_provider> user_verify_provider_LIST = null;
	
	public static List<org_proxy_account> org_proxy_account_LIST = null;

	public static List<user_app_host> user_app_host_LIST = null;
	public static List<org_task_host> org_app_host_LIST = null;

	
	
	// 先检查 sinceLastUpdateTime之后是否有有记录被修改, 如果没有记录被修改，则返回304
	//select max(b.factory_team_info_update_time) as x1 
	//from org_factory_host a, org b 
	//where a.id = 1 and a._orgid = b.id;
	
	//if ( x1 > sinceLastUpdateTime ) {
	//	// 做以下搜索！
	//}
	
	// user表记录，被删除的话，是逻辑删除，删除时把所有 factory_team_update_time 都更新一遍

	
	// 获取 senior_worker_2_active_user_ids_MAP
	
	// select distinct(e.id) as x1
	// from org_factory_host a, team_factory_worker b, step_team c, app d, user e
	// where a.id = <?> and a.is_used = 'Y' and a.id = b.factory_host_id
	// and b.<s1> = 1 and b.team_id = c.team_id
	// and c.app_id = d.id
	// and d.user_id = e.id
	// and e.is_used = 'Y';
	
	
	
	// 获取 junior_worker_2_active_user_ids_MAP
	
	// select distinct(c.id) as x1 
	// from org_factory_host a, team_factory_worker b, user c 
	// where a.id = ? and a.is_used = 'Y' and a.id = b.factory_host_id 
	// and b.j1 = 1 and b._userid = c.id 
	// and c.is_used = 'Y' and c.active_start_date < now() and now() < c.active_end_date;
	
	// select distinct(c.id) as x1 
	// from org_factory_host a, team_factory_worker b, user c 
	// where a.id = ? and a.is_used = 'Y' and a.id = b.factory_host_id 
	// and b.j2 = 1 and b._userid = c.id 
	// and c.is_used = 'Y' and c.active_start_date < now() and now() < c.active_end_date;
	

	// 获取 user's all task host	
	// select d.user_id as _user_id, d.task_host_id as _task_host_id
	// from team_factory_worker a, user_team b, user c, user_task_host d, org_task_host e  
	// where a.factory_host_id = ? and a.team_id = b.team_id and b.user_id = c.id and is_used = 'Y' and c.active_start_date < now() and c.active_end_date > now() 
	// and c.id = d.user_id and d.task_host_id = e.id and e.is_used = 'Y'
	// group by _user_id, _task_host_id;
	
	// select e.*
	// from team_factory_worker a, user_team b, user c, user_task_host d, org_task_host e  
	// where a.factory_host_id = ? and a.team_id = b.team_id and b.user_id = c.id and is_used = 'Y' and c.active_start_date < now() and c.active_end_date > now() 
	// and c.id = d.user_id and d.task_host_id = e.id and e.is_used = 'Y';

	
	public static Map<Integer, int[]> senior_worker_2_active_user_ids_MAP = null;
	public static Map<Integer, int[]> junior_worker_2_active_user_ids_MAP = null;
	
	//public static Map<Integer, int[]> user_2_task_host_ids_MAP = null;
	
	//public static Map<Integer, org_task_host> org_task_host_id_2_object_MAP = null;
	
	public static void call() {
		// call Infra-API-1
		
		// INFRA_HOST: 1.2.3.4:8080
		// FACTORY_HOST_ID: 123
		
		// 请求参数，必须要包括一个sinceLastUpdateTime，如果Infra端的数据自lastUpdateTime后没有修改，则不返回数据，返回code=304
		
		// 返回的json有
		
		senior_worker_2_active_user_ids_MAP = new HashMap<Integer, int[]>();
		senior_worker_2_active_user_ids_MAP.put(1, new int[]{1,2,3,4,5});
		senior_worker_2_active_user_ids_MAP.put(2, new int[]{1,2,3,4,5});
		senior_worker_2_active_user_ids_MAP.put(3, new int[]{1,2,3,4,5});
		
		junior_worker_2_active_user_ids_MAP = new HashMap<Integer, int[]>();
		junior_worker_2_active_user_ids_MAP.put(1, new int[]{1,2,3,4,5});
		junior_worker_2_active_user_ids_MAP.put(2, new int[]{1,2,3,4,5});
		junior_worker_2_active_user_ids_MAP.put(3, new int[]{1,2,3,4,5});
		
//		user_2_task_host_ids_MAP = new HashMap<Integer, int[]>();
//		user_2_task_host_ids_MAP.put(4, new int[]{1,2,3,4});
//		user_2_task_host_ids_MAP.put(5, new int[]{1,2,5});
//		
//		org_task_host_id_2_object_MAP = new HashMap<Integer, org_task_host>();
//		org_task_host_id_2_object_MAP.put(1, new org_task_host());
//		org_task_host_id_2_object_MAP.put(2, new org_task_host());
//		org_task_host_id_2_object_MAP.put(3, new org_task_host());
		
	}
	
	
	public static Integer getRandomUserIdForTheWorker() {
		
		call();
		
		int sessionType = SessionThreadLocal.getSessionType();
		int sessionIndex = SessionThreadLocal.getSessionIndex();
		
		Map<Integer, int[]> temp1 = null;
		if ( sessionType == SessionType.SENIOR ) temp1 = senior_worker_2_active_user_ids_MAP;
		if ( sessionType == SessionType.JUNIOR ) temp1 = junior_worker_2_active_user_ids_MAP;
		
		int[] temp2 = temp1.get(sessionIndex);
		if (temp2.length == 0) return null;
		
		return new Random().nextInt( temp2.length );
	}
	
	
//	public static Integer getRandomTaskHostIdOfUser( int userId ) {
//		
//		call();
//		
//		int[] temp = user_2_task_host_ids_MAP.get(userId);
//		if (temp.length == 0) return null;
//		
//		return new Random().nextInt( temp.length ); 
//	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
//	{
//		  "code": 200,
//		  "data": {
//		    
//		    "senior_worker_to_user_ids_MAP": [
//		      {
//		        "index": 1,
//		        "users": "1,2,3,4,5"
//		      },
//		      {
//		        "index": 2,
//		        "users": "1,2,3,4,5"
//		      },
//		      {
//		        "index": 3,
//		        "users": "1,2,3,4,5"
//		      }
//		    ],
//		    
//		    "junior_worker_to_user_ids_MAP": [
//		      {
//		        "index": 1,
//		        "users": "1,2,3,4,5"
//		      },
//		      {
//		        "index": 2,
//		        "users": "1,2,3,4,5"
//		      },
//		      {
//		        "index": 3,
//		        "users": "1,2,3,4,5"
//		      }
//		    ],
//		    
//		    "user_to_task_host_ids_MAP": [
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
