package com.web2data.system.infra.api;

import com.rkylin.crawler.engine.flood.util.FileUtil;
import com.rkylin.crawler.engine.flood.util.HTTP;
import com.rkylin.crawler.engine.flood.util.JsonUtil;
import com.web2data._global.G2;
import com.web2data._global.SessionType;
import com.web2data.system.entity.org_factory_host;

public class API005GetInfraInfo {

	public static int _code = -1; // 200 - OK
	public static org_factory_host _org_factory_host = null;
	
	
	public static int getNumberOfSession(int workerType) {
		if ( _org_factory_host == null ) 
			load();
		if ( _org_factory_host != null ) {
			if (workerType == SessionType.SENIOR) return _org_factory_host.getSenior_workers();
			if (workerType == SessionType.JUNIOR) return _org_factory_host.getJunior_workers();
		}
		return -1;
	}
	

	private static void load() {
		
		String file = null;
		
		try {
		
			if ( G2.factory_host_env.equals( G2.LOCAL ) ) {
				file = FileUtil.read( G2.factory_host_data_root + G2.EMULATE_INFRA_GLOBAL_JSON );
			} else {
				file = HTTP.doGet("");
			}
		
			API005GetInfraInfo json 
				= JsonUtil.convertJsonStr2Obj(file, API005GetInfraInfo.class);
		
			_code = json.getCode();
			_org_factory_host = json.getOrg_factory_host();
			//_senior_map.put( userId, json.getSeniorworker_steps_map() );
			//_junior_map.put( userId, json.getJuniorworker_steps_map() );
			
		} catch (Exception e) {
			//
			System.out.println( "Exception: GetWorker2UserTaskHostsMapAPI - reload(): " + e.getMessage() );
		}
	}
	
	
	// -----------------------------------------------------------------------------
	public int code = -1;
	public String message = null;

	public org_factory_host org_factory_host = null;

	public API005GetInfraInfo() {}


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


	public static int get_code() {
		return _code;
	}


	public static void set_code(int _code) {
		API005GetInfraInfo._code = _code;
	}


	public static org_factory_host get_org_factory_host() {
		return _org_factory_host;
	}


	public static void set_org_factory_host(org_factory_host _org_factory_host) {
		API005GetInfraInfo._org_factory_host = _org_factory_host;
	}


	public org_factory_host getOrg_factory_host() {
		return org_factory_host;
	}


	public void setOrg_factory_host(org_factory_host org_factory_host) {
		this.org_factory_host = org_factory_host;
	}


	public static void main(String[] args) {

		//List<String> temp = findTestStepListForTheUserWorker( 1, 123, SessionType.SENIOR );
		
		// int userId, int factoryId, int workerType, int workerIndex
		
		System.out.println("API005GetInfraInfo.getNumberOfSeniorSession() = " + API005GetInfraInfo.getNumberOfSession(SessionType.SENIOR) );
		System.out.println("API005GetInfraInfo.getNumberOfJuniorSession() = " + API005GetInfraInfo.getNumberOfSession(SessionType.JUNIOR) );
		
	}
    
}
