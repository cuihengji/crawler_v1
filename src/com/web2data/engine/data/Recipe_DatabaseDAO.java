package com.web2data.engine.data;

import java.util.HashMap;
import java.util.Map;

public class Recipe_DatabaseDAO {

	private static long lastCallingAPITime = 0;
	
	
	// key = recipeID
	
	private static HashMap<String,DatabaseInfraData> 
		map = new HashMap<String,DatabaseInfraData>();
	
	private static long mapLatestChangedTime = 0;
	
}
