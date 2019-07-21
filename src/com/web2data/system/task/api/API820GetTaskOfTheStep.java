package com.web2data.system.task.api;

import com.web2data._global.G2;
import com.web2data.open.RxTask;

public class API820GetTaskOfTheStep {

	public static RxTask fetchTestTask(int userId, int taskHostId, String step ) {
		return fetchTask(userId, taskHostId, G2.TEST, step);
	}
	
	public static RxTask fetchProdTask(int userId, int taskHostId, String step ) {
		return fetchTask(userId, taskHostId, G2.PROD, step);
	}
	
	private static RxTask fetchTask(
			int userId, int taskHostId, int queueType, String step ) {
		
		//if (step.equals("900-3-1") || step.equals("900-3-2") ) return null;
		
		RxTask result = new RxTask();
		
		result.setEnv( queueType );
		result.setAppId( Integer.valueOf( step.split("-")[0] ) );
		result.setRecipeIndex( Integer.valueOf( step.split("-")[1] ) );
		result.setStepIndex( Integer.valueOf( step.split("-")[2] ) );
		
		result.setJar_version(789);
		
		result.setX1( step );

		return result;
	}
}
