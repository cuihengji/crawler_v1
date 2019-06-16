package com.web2data.engine.stage;

import com.web2data.open.RxStage;
import com.web2data.open.RxTask;

import app123.recipe3.MyStage;

public class RxStageManager {
 
	//
	public static RxStage getStageForTask( RxTask task ) {
		
		// 根据 Task 中的 app,scenario,stage, 获得带版本号的类全名，
		
		// 内存中不存在，则实例化
		// 内存中存在，则直接只用
		
		RxStage result = new MyStage();
		return result;
	}

}
