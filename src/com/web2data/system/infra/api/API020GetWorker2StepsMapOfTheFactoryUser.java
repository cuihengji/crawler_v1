package com.web2data.system.infra.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rkylin.crawler.engine.flood.util.FileUtil;
import com.rkylin.crawler.engine.flood.util.HTTP;
import com.rkylin.crawler.engine.flood.util.JsonUtil;
import com.web2data._global.G2;
import com.web2data._global.SessionType;
import com.web2data.utility.U;

public class API020GetWorker2StepsMapOfTheFactoryUser {

	private static long lastReloadTime = 0;
	
	public static int _code = -1; // 200 - OK
	private static Map<String, Map<String, List<String>>> _senior_map = new HashMap<String, Map<String, List<String>>>();
	private static Map<String, Map<String, List<String>>> _junior_map = new HashMap<String, Map<String, List<String>>>();
	
	
	// U.getRandomString( List<String> list );
	
	public static synchronized List<String> findStepListForTheUserFactoryWorker(
			int userId, int factoryId, int workerType, int workerIndex ) {
		
		if ( (System.currentTimeMillis() - lastReloadTime) > 15*1000 ) {
			reload( userId, factoryId, workerType, workerIndex );
			lastReloadTime = System.currentTimeMillis();
		}
		
		if ( _code != 200 ) {
			return null;
		}
		
		Map<String, List<String>> temp = null;
		
    	if ( workerType == SessionType.SENIOR ) {
    		temp = _senior_map.get(userId+"");
    	} else {
    		temp = _junior_map.get(userId+"");
    	}
    	
    	if ( temp == null ) return null;
    	
    	return temp.get(workerIndex+"");
	}

	
	private static void reload( int userId, int factoryId, int workerType, int workerIndex ) {
		
		String file = null;
		
		try {
		
			if ( G2.factory_host_env.equals( G2.LOCAL ) ) {
				file = FileUtil.read( G2.factory_host_data_root 
						+ String.format(G2.EMULATE_INFRA_USER_WORKER_2_STEPS_JSON, userId) );
			} else {
				file = HTTP.doGet("");
			}
		
			API020GetWorker2StepsMapOfTheFactoryUser json 
				= JsonUtil.convertJsonStr2Obj(file, API020GetWorker2StepsMapOfTheFactoryUser.class);
		
			_code = json.getCode();
			_senior_map.put( userId+"", json.getSeniorworker_steps_map() );
			_junior_map.put( userId+"", json.getJuniorworker_steps_map() );
			
		} catch (Exception e) {
			//
			System.out.println( "Exception: GetWorker2UserTaskHostsMapAPI - reload(): " + e.getMessage() );
		}
	}
	
	
	// -----------------------------------------------------------------------------
	public int code = -1;
	public String message = null;

	public Map<String, List<String>> seniorworker_steps_map = null;
	public Map<String, List<String>> juniorworker_steps_map = null;

	public API020GetWorker2StepsMapOfTheFactoryUser() {}


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





	public Map<String, List<String>> getSeniorworker_steps_map() {
		return seniorworker_steps_map;
	}


	public void setSeniorworker_steps_map(Map<String, List<String>> seniorworker_steps_map) {
		this.seniorworker_steps_map = seniorworker_steps_map;
	}


	public Map<String, List<String>> getJuniorworker_steps_map() {
		return juniorworker_steps_map;
	}


	public void setJuniorworker_steps_map(Map<String, List<String>> juniorworker_steps_map) {
		this.juniorworker_steps_map = juniorworker_steps_map;
	}


	public static void main(String[] args) {

		List<String> temp = findStepListForTheUserFactoryWorker( 123, 123, SessionType.SENIOR, 3 );
		
		
		// int userId, int factoryId, int workerType, int workerIndex
		
		System.out.println("temp1 = " + temp.size());
		
	}
}
