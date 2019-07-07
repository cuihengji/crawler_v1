package com.web2data.system.infra.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class API030GetRelatedTablesOfTheApp {

	// app
	// recipe
	// step
	// app_worker_account
	// recipe_database_host
	// step_team

//	private static Map<Integer, app> _appid_app_map = new HashMap<Integer, app>();
//	private static Map<Integer, List<recipe>> _appid_recipe_map = new HashMap<Integer, recipe>();
//	private static Map<Integer, List<step>> _appid_step_map = new HashMap<Integer, step>();
//	private static Map<Integer, List<app_worker_account>> _appid_appworkeaccount_map = new HashMap<Integer, app_worker_account>();
//	private static Map<Integer, List<recipe_database_host>> _appid_recipedatabasehost_map = new HashMap<Integer, recipe_database_host>();
//	private static Map<Integer, List<step_team>> _appid_stepteam_map = new HashMap<Integer, step_team>();
	
	public static int getPolicyIntervalOfTheStepInCurrentHour( String step ) {
		
		return 15;
	}
}
