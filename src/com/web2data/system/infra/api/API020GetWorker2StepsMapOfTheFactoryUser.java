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

	public static int _code = -1; // 200 - OK
	private static Map<Integer, Map<String, List<String>>> _senior_map_in_test = new HashMap<Integer, Map<String, List<String>>>();
	private static Map<Integer, Map<String, List<String>>> _junior_map_in_test = new HashMap<Integer, Map<String, List<String>>>();
	private static Map<Integer, Map<String, List<String>>> _senior_map_in_prod = new HashMap<Integer, Map<String, List<String>>>();
	private static Map<Integer, Map<String, List<String>>> _junior_map_in_prod = new HashMap<Integer, Map<String, List<String>>>();
	
	
	private static Map<Integer, Long> seniorworker_timeMillis_map_in_test = new HashMap<Integer, Long>();
	private static Map<Integer, Long> juniorworker_timeMillis_map_in_test = new HashMap<Integer, Long>();
	private static Map<Integer, Long> seniorworker_timeMillis_map_in_prod = new HashMap<Integer, Long>();
	private static Map<Integer, Long> juniorworker_timeMillis_map_in_prod = new HashMap<Integer, Long>();

	
	
	public static List<String> findStepListForTheUserWorkerInTestEnv( 
			int userId, int workerType, int workerIndex ) {
    	return findStepListForTheUserWorker( G2.TEST, userId, workerType, workerIndex );
	}
	
	public static List<String> findStepListForTheUserWorkerInProdEnv( 
			int userId, int workerType, int workerIndex ) {
    	return findStepListForTheUserWorker( G2.PROD, userId, workerType, workerIndex );
	}
	
	
	public static synchronized List<String> findStepListForTheUserWorker(
			int env, int userId, int workerType, int workerIndex ) {
		
		Map<Integer, Long> temp = null;
    	if ( workerType == SessionType.SENIOR && env == G2.TEST) {
    		temp = seniorworker_timeMillis_map_in_test;
    	} else if ( workerType == SessionType.JUNIOR && env == G2.TEST) {
    		temp = juniorworker_timeMillis_map_in_test;
    	} else if ( workerType == SessionType.SENIOR && env == G2.PROD ) {
    		temp = seniorworker_timeMillis_map_in_prod;
    	} else if ( workerType == SessionType.JUNIOR && env == G2.PROD ) {
    		temp = juniorworker_timeMillis_map_in_prod;
    	}
    	
    	Long lastReloadTime = temp.get(workerIndex);
    	if ( lastReloadTime == null ) {
    		lastReloadTime = 0L;
    	}
		
		if ( (System.currentTimeMillis() - lastReloadTime) > 15*1000 ) {
			// temp.put(workerIndex, System.currentTimeMillis()); // 如果 reload出错，则进行详细调查
			reload( env, userId, workerType, workerIndex );
			temp.put(workerIndex, System.currentTimeMillis());
		}
		
		if ( _code != 200 ) {
			return null;
		}
		
		Map<String, List<String>> temp2 = null;
    	if (workerType == SessionType.SENIOR && env == G2.TEST) {
    		temp2 = _senior_map_in_test.get(userId);;
    	} else if (workerType == SessionType.JUNIOR && env == G2.TEST) {
    		temp2 = _junior_map_in_test.get(userId);;
    	} else if (workerType == SessionType.SENIOR && env == G2.PROD) {
    		temp2 = _senior_map_in_prod.get(userId);;
    	} else if (workerType == SessionType.JUNIOR && env == G2.PROD) {
    		temp2 = _junior_map_in_prod.get(userId);;
    	}
    	if ( temp2 == null ) return null;
    	
    	return temp2.get(workerIndex+"");
	}

	
	private static void reload( int env, int userId, int workerType, int workerIndex ) {
		
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
			//_senior_map.put( userId, json.getSeniorworker_steps_map() );
			//_junior_map.put( userId, json.getJuniorworker_steps_map() );
			
	    	if (workerType == SessionType.SENIOR && env == G2.TEST) {
	    		_senior_map_in_test.put(userId, json.getSeniorworker_steps_map_in_test() );;
	    	} else if (workerType == SessionType.JUNIOR && env == G2.TEST) {
	    		_junior_map_in_test.put(userId, json.getJuniorworker_steps_map_in_test());;
	    	} else if (workerType == SessionType.SENIOR && env == G2.PROD) {
	    		_senior_map_in_prod.put(userId, json.getSeniorworker_steps_map_in_prod());;
	    	} else if (workerType == SessionType.JUNIOR && env == G2.PROD) {
	    		_junior_map_in_prod.put(userId, json.getJuniorworker_steps_map_in_prod());;
	    	}
			
		} catch (Exception e) {
			//
			System.out.println( "Exception: GetWorker2UserTaskHostsMapAPI - reload(): " + e.getMessage() );
		}
	}
	
	
	// -----------------------------------------------------------------------------
	public int code = -1;
	public String message = null;

	public Map<String, List<String>> seniorworker_steps_map_in_test = null;
	public Map<String, List<String>> juniorworker_steps_map_in_test = null;
	public Map<String, List<String>> seniorworker_steps_map_in_prod = null;
	public Map<String, List<String>> juniorworker_steps_map_in_prod = null;

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



	public Map<String, List<String>> getSeniorworker_steps_map_in_test() {
		return seniorworker_steps_map_in_test;
	}

	public void setSeniorworker_steps_map_in_test(Map<String, List<String>> seniorworker_steps_map_in_test) {
		this.seniorworker_steps_map_in_test = seniorworker_steps_map_in_test;
	}

	public Map<String, List<String>> getJuniorworker_steps_map_in_test() {
		return juniorworker_steps_map_in_test;
	}

	public void setJuniorworker_steps_map_in_test(Map<String, List<String>> juniorworker_steps_map_in_test) {
		this.juniorworker_steps_map_in_test = juniorworker_steps_map_in_test;
	}

	public Map<String, List<String>> getSeniorworker_steps_map_in_prod() {
		return seniorworker_steps_map_in_prod;
	}

	public void setSeniorworker_steps_map_in_prod(Map<String, List<String>> seniorworker_steps_map_in_prod) {
		this.seniorworker_steps_map_in_prod = seniorworker_steps_map_in_prod;
	}

	public Map<String, List<String>> getJuniorworker_steps_map_in_prod() {
		return juniorworker_steps_map_in_prod;
	}

	public void setJuniorworker_steps_map_in_prod(Map<String, List<String>> juniorworker_steps_map_in_prod) {
		this.juniorworker_steps_map_in_prod = juniorworker_steps_map_in_prod;
	}

	public static void main(String[] args) {

		List<String> temp = findStepListForTheUserWorkerInTestEnv( 123, SessionType.SENIOR, 1 );
		
		
		// int userId, int factoryId, int workerType, int workerIndex
		
		System.out.println("temp1 = " + temp.size());
		
	}
}
