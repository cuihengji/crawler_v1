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

public class API010GetWorker2UserTaskHostsMapOfTheFactory {

	public static int _code = -1; // 200 - OK
	private static Map<String, List<String>> _seniorworker_usertaskhosts_map = null;
	private static Map<String, List<String>> _juniorworker_usertaskhosts_map = null;
	

	private static Map<Integer, Long> seniorworker_timeMillis_map = new HashMap<Integer, Long>();
	private static Map<Integer, Long> juniorworker_timeMillis_map = new HashMap<Integer, Long>();
	
	public static synchronized String findOneRandomUserTaskHostForTheWorker(
			int workerType, int workerIndex ) {
		
		Map<Integer, Long> temp = null;
    	if ( workerType == SessionType.SENIOR ) {
    		temp = seniorworker_timeMillis_map;
    	} else {
    		temp = juniorworker_timeMillis_map;
    	}
		
    	Long lastReloadTime = temp.get(workerIndex);
    	if ( lastReloadTime == null ) {
    		lastReloadTime = 0L;
    	}
    	
		if ( (System.currentTimeMillis() - lastReloadTime) > 15*1000 ) {
			reload();
			temp.put(workerIndex, System.currentTimeMillis());
		}
		
		if ( _code != 200 ) {
			return null;
		}
		
		Map<String, List<String>> temp2 = null;
		
    	if ( workerType == SessionType.SENIOR ) {
    		temp2 = _seniorworker_usertaskhosts_map;
    	} else {
    		temp2 = _juniorworker_usertaskhosts_map;
    	}
    	
    	List<String> temp3 = temp2.get( workerIndex+"" );
    	if ( temp3==null || temp3.size()==0 ) {
    		return null;
    	}
    	
    	return U.getRandomElement( temp3 );
	}

	
	private static void reload() {
		
		String file = null;
		
		try {
		
			if ( G2.factory_host_env.equals( G2.LOCAL ) ) {
				file = FileUtil.read( G2.factory_host_data_root + G2.EMULATE_INFRA_WORKER_2_USERTASKHOST_JSON );
			} else {
				file = HTTP.doGet("");
			}
		
			API010GetWorker2UserTaskHostsMapOfTheFactory json 
				= JsonUtil.convertJsonStr2Obj(file, API010GetWorker2UserTaskHostsMapOfTheFactory.class);
		
			_code = json.getCode();
			_seniorworker_usertaskhosts_map = json.getSeniorworker_usertaskhosts_map();
			_juniorworker_usertaskhosts_map = json.getJuniorworker_usertaskhosts_map();
			
		} catch (Exception e) {
			//
			System.out.println( "Exception: GetWorker2UserTaskHostsMapAPI - reload(): " + e.getMessage() );
		}
	}
	
	
	// -----------------------------------------------------------------------------
	public int code = -1;
	public String message = null;

	public Map<String, List<String>> seniorworker_usertaskhosts_map = null;
	public Map<String, List<String>> juniorworker_usertaskhosts_map = null;

	public API010GetWorker2UserTaskHostsMapOfTheFactory() {}


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


	public Map<String, List<String>> getSeniorworker_usertaskhosts_map() {
		return seniorworker_usertaskhosts_map;
	}


	public void setSeniorworker_usertaskhosts_map(Map<String, List<String>> seniorworker_usertaskhosts_map) {
		this.seniorworker_usertaskhosts_map = seniorworker_usertaskhosts_map;
	}


	public Map<String, List<String>> getJuniorworker_usertaskhosts_map() {
		return juniorworker_usertaskhosts_map;
	}


	public void setJuniorworker_usertaskhosts_map(Map<String, List<String>> juniorworker_usertaskhosts_map) {
		this.juniorworker_usertaskhosts_map = juniorworker_usertaskhosts_map;
	}
	


	public static void main(String[] args) {

		String temp = findOneRandomUserTaskHostForTheWorker( SessionType.JUNIOR, 2 );
		System.out.println("temp1 = " + temp);
		
		temp = findOneRandomUserTaskHostForTheWorker( SessionType.JUNIOR, 2 );
		System.out.println("temp2 = " + temp);
		
		temp = findOneRandomUserTaskHostForTheWorker( SessionType.JUNIOR, 2 );
		System.out.println("temp3 = " + temp);
		
		temp = findOneRandomUserTaskHostForTheWorker( SessionType.JUNIOR, 2 );
		System.out.println("temp4 = " + temp);
		
		temp = findOneRandomUserTaskHostForTheWorker( SessionType.JUNIOR, 2 );
		System.out.println("temp5 = " + temp);
		
		temp = findOneRandomUserTaskHostForTheWorker( SessionType.JUNIOR, 2 );
		System.out.println("temp6 = " + temp);
		
		temp = findOneRandomUserTaskHostForTheWorker( SessionType.JUNIOR, 2 );
		System.out.println("temp7 = " + temp);
		
		temp = findOneRandomUserTaskHostForTheWorker( SessionType.JUNIOR, 2 );
		System.out.println("temp8 = " + temp);
		
		temp = findOneRandomUserTaskHostForTheWorker( SessionType.JUNIOR, 2 );
		System.out.println("temp9 = " + temp);
	}
}
