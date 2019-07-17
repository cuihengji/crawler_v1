package com.web2data.system.infra.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rkylin.crawler.engine.flood.util.FileUtil;
import com.rkylin.crawler.engine.flood.util.HTTP;
import com.rkylin.crawler.engine.flood.util.JsonUtil;
import com.web2data._global.G2;
import com.web2data.system.entity.step;
import com.web2data.utility.U;

public class API030GetRelatedTablesOfTheApp {

//	private static Map<Integer, app> _appid_app_map = new HashMap<Integer, app>();
//	private static Map<Integer, List<recipe>> _appid_recipe_map = new HashMap<Integer, recipe>();
	private static Map<Integer, List<step>> _appid_steps_map = new HashMap<Integer, List<step>>();
//	private static Map<Integer, List<app_worker_account>> _appid_appworkeaccount_map = new HashMap<Integer, app_worker_account>();
//	private static Map<Integer, List<recipe_database_host>> _appid_recipedatabasehost_map = new HashMap<Integer, recipe_database_host>();
//	private static Map<Integer, List<step_team>> _appid_stepteam_map = new HashMap<Integer, step_team>();
	
	
	private static Map<Integer, Long> appid_timeMillis_map = new HashMap<Integer, Long>();
	
	public static int getTheStepPolicyIntervalInCurrentHour( int env, String step ) {
		
		step aStep = findStepbyId( step );
		if ( aStep == null ) return 300;
		
	    int currentHour = U.getCurrentHour();
	    
        if ( env == G2.TEST ) {
		    switch (currentHour) {
		    	case 0 : return aStep.getTh0();
		    	case 1 : return aStep.getTh1();
		    	case 2 : return aStep.getTh2();
		    	case 3 : return aStep.getTh3();
		    	case 4 : return aStep.getTh4();
		    	case 5 : return aStep.getTh5();
		    	case 6 : return aStep.getTh6();
		    	case 7 : return aStep.getTh7();
		    	case 8 : return aStep.getTh8();
		    	case 9 : return aStep.getTh9();
		    	case 10 : return aStep.getTh10();
		    	case 11 : return aStep.getTh11();
		    	case 12 : return aStep.getTh12();
		    	case 13 : return aStep.getTh13();
		    	case 14 : return aStep.getTh14();
		    	case 15 : return aStep.getTh15();
		    	case 16 : return aStep.getTh16();
		    	case 17 : return aStep.getTh17();
		    	case 18 : return aStep.getTh18();
		    	case 19 : return aStep.getTh19();
		    	case 20 : return aStep.getTh20();
		    	case 21 : return aStep.getTh21();
		    	case 22 : return aStep.getTh22();
		    	case 23 : return aStep.getTh23();
		    	default : ;
		    }
		    
        } else if ( env == G2.PROD ) {
		    switch (currentHour) {
		    	case 0 : return aStep.getPh0();
		    	case 1 : return aStep.getPh1();
		    	case 2 : return aStep.getPh2();
		    	case 3 : return aStep.getPh3();
		    	case 4 : return aStep.getPh4();
		    	case 5 : return aStep.getPh5();
		    	case 6 : return aStep.getPh6();
		    	case 7 : return aStep.getPh7();
		    	case 8 : return aStep.getPh8();
		    	case 9 : return aStep.getPh9();
		    	case 10 : return aStep.getPh10();
		    	case 11 : return aStep.getPh11();
		    	case 12 : return aStep.getPh12();
		    	case 13 : return aStep.getPh13();
		    	case 14 : return aStep.getPh14();
		    	case 15 : return aStep.getPh15();
		    	case 16 : return aStep.getPh16();
		    	case 17 : return aStep.getPh17();
		    	case 18 : return aStep.getPh18();
		    	case 19 : return aStep.getPh19();
		    	case 20 : return aStep.getPh20();
		    	case 21 : return aStep.getPh21();
		    	case 22 : return aStep.getPh22();
		    	case 23 : return aStep.getPh23();
		    	default : ;
		    }
        }
        
		return 300;
	}
	
	
	public static synchronized step findStepbyId( String step ) {
	
		int[] temps = U.convertStringToInts(step);
		int appId = temps[0], recipeIndex=temps[1], stepIndex= temps[2];
		
    	Long lastReloadTime = appid_timeMillis_map.get(appId);
    	if ( lastReloadTime == null ) {
    		lastReloadTime = 0L;
    	}
    	
		if ( (System.currentTimeMillis() - lastReloadTime) > 15*1000 ) {
			reload(appId);
			appid_timeMillis_map.put(appId, System.currentTimeMillis());
		}
		
		List<step> steps = _appid_steps_map.get(appId);
		
		for (step eachStep: steps ) {
			if ( eachStep.getApp_id() == appId && eachStep.getRecipe_index() == recipeIndex && eachStep.getStep_index() == stepIndex ) {
				return eachStep;
			}
		}
		
		return null;
	}
	
	
	
	private static void reload( int appId ) {
		
		String file = null;
		
		try {
		
			if ( G2.factory_host_env.equals( G2.LOCAL ) ) {
				file = FileUtil.read( G2.factory_host_data_root 
						+ String.format(G2.EMULATE_INFRA_APP_JSON, appId) );
			} else {
				file = HTTP.doGet("");
			}
		
			API030GetRelatedTablesOfTheApp json 
				= JsonUtil.convertJsonStr2Obj(file, API030GetRelatedTablesOfTheApp.class);
		
			_appid_steps_map.put(appId, json.getStep_list());
			
		} catch (Exception e) {
			//
			System.out.println( "Exception: GetWorker2UserTaskHostsMapAPI - reload(): " + e.getMessage() );
		}
	}
	
	
	
	// -----------------------------------------------------------------------------
	public int code = -1;
	public String message = null;

	public List<step> step_list = null;

	public API030GetRelatedTablesOfTheApp() {}


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


	public List<step> getStep_list() {
		return step_list;
	}


	public void setStep_list(List<step> step_list) {
		this.step_list = step_list;
	}
	
	
	public static void main(String[] args) {

		step temp1 = findStepbyId( "900-1-1" );
		System.out.println("temp1 = " + temp1.getName());
		
		//int hour = getPolicyIntervalOfTheStepInCurrentHour("900-1-1");
		//System.out.println("hour = " + hour);
	}
	
}
