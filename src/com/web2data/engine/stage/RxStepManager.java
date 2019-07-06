package com.web2data.engine.stage;


import com.web2data.open.RxStep;
import com.web2data.open.RxTask;


public class RxStepManager {
 
	//
	public static RxStep getStepForTask( RxTask task ) {
		
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
		
		return null;
	}

}
