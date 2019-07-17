package com.web2data.engine.stage;


import com.rkylin.crawler.engine.flood.util.DynamicJarLoader;
import com.web2data.open.RxStep;
import com.web2data.open.RxTask;
import com.web2data.system.entity.step;

import user123.app123.recipe3.MyStep2;
import user123.app123.recipe3.Test;


public class RxStepManager {
 
	//
	public static RxStep getStepForTheTask( RxTask task ) {
		
		// 根据 Task 中的 app,scenario,stage, 获得带版本号的类全名，
		
		// 内存中不存在，则实例化
		// 内存中存在，则直接只用

//		RxStep result = new MyStep1();
//		
//		if ( task._env == RxTask._TEST ) {
//			result._IN_TEST_ENVIRONMENT = true;
//			result._IN_PRODUCTION_ENVIRONMENT = false;
//		}
//		if ( task._env == RxTask._PRODUCTION ) {
//			result._IN_TEST_ENVIRONMENT = false;
//			result._IN_PRODUCTION_ENVIRONMENT = true;
//		}
		
		RxStep result = null;
		
		try {
			result = DynamicJarLoader.load2("D:\\_web2data\\MyStep2.jar", "user123.app123.recipe3.MyStep2");
		} catch (Exception e) {
			System.out.println( "RxStepManager.getStepForTheTask = " + e.getMessage() );
			task.setFinished_code( 990 );
		}
		
		return result;
	}
	
	
	public static void main(String[] args) throws Exception {

		try {
			
	        try {
	        	
	        	RxStep result3 = getStepForTheTask(null);
	        	result3._execute(null, null);
	        	
	        } catch (Exception ex) {
	            //logger.error(ex, ex);
	            throw ex;
	        }
			
		} catch (Exception e) {
			//
			System.out.println( "RxStepManager.getStepForTheTask = " + e.getMessage() );
		}
	}

}
