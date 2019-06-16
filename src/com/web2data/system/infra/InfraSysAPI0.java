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

public class InfraSysAPI0 {

	
	public long lastCallingAPI = 0;
	
	public long lastUpdateTime = 0;
	
	// 全局配置，或者是org-level的配置
//	public static List<infra_config> infra_config_LIST = null;
//	public static List<infra_api> infra_api_LIST = null;
//	public static List<infra_host> infra_host_LIST = null;

	
	
	// 获取 all task task host related
	// select c.*
	// from team_factory_worker a, user_task_host b, org_task_host c  
	// where a.factory_host_id = ? and a._userid = b.user_id and b.task_host_id = c.id and c.is_used = 'Y';
	
	// 与user关联的信息
	public static Map<Integer, org_task_host> org_task_host_id_2_object_MAP = new HashMap<Integer, org_task_host>();
	
//	public static List<team_factory_worker> team_factory_worker_LIST = null;
//	
//	public static List<team> team_LIST = null;
//	
//	public static List<user_team> user_team_LIST = null;
//	
//	public static List<user> user_LIST = null;
//
//	public static List<user_database_host> user_database_host_LIST = null;
//	public static List<org_database_host> org_database_host_LIST = null;
//
//	public static List<user_proxy_provider> user_proxy_provider_LIST = null;
//
//	public static List<user_verify_provider> user_verify_provider_LIST = null;
//	
//	public static List<org_proxy_account> org_proxy_account_LIST = null;
//
//	public static List<user_app_host> user_app_host_LIST = null;
//	public static List<org_task_host> org_app_host_LIST = null;

	
	
	// 先检查 sinceLastUpdateTime之后是否有有记录被修改, 如果没有记录被修改，则返回304

	
	
	// 获取 all task task host related
	// select c.*
	// from team_factory_worker a, user_task_host b, org_task_host c  
	// where a.factory_host_id = ? and a._userid = b.user_id and b.task_host_id = c.id and c.is_used = 'Y';
	
	
	//public static Map<Integer, int[]> senior_worker_2_active_user_ids_MAP = null;
	//public static Map<Integer, int[]> junior_worker_2_active_user_ids_MAP = null;
	
	//public static Map<Integer, int[]> user_2_task_host_ids_MAP = null;
	
	//public static Map<Integer, org_task_host> org_task_host_id_2_object_MAP = null;
	
	public static void call() {
		// call Infra-API-1
		
		// INFRA_HOST: 1.2.3.4:8080
		// FACTORY_HOST_ID: 123
		
		// 请求参数，必须要包括一个sinceLastUpdateTime，如果Infra端的数据自lastUpdateTime后没有修改，则不返回数据，返回code=304
		
		// 返回的json有
		
		
		// 获取 all task task host related
		// select c.*
		// from team_factory_worker a, user_task_host b, org_task_host c  
		// where a.factory_host_id = ? and a._userid = b.user_id and b.task_host_id = c.id and c.is_used = 'Y';

		//org_factory_host_id_2_object_MAP = new HashMap<Integer, org_task_host>();
		org_task_host_id_2_object_MAP.put(1, new org_task_host());
		org_task_host_id_2_object_MAP.put(2, new org_task_host());
		org_task_host_id_2_object_MAP.put(3, new org_task_host());
		
	}
	
	
	public static org_task_host getOrgTaskHostById( int id ) {
		
		call();
		
		return org_task_host_id_2_object_MAP.get(id);
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
