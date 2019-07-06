package com.web2data.system.policy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web2data.open.RxTask;
import com.web2data.system.entity.task_control;
import com.web2data.system.infra.api.InfraSysAPI4;

public class PolicySys {

	
	private static Map<String, Long> 
		step_id_2_last_execution_time_MAP = new HashMap<String, Long>();
	
	//
	public static void keep( String stepId, Long time ) {
		step_id_2_last_execution_time_MAP.put(stepId, time);
	}
	
	
	//
	public static List<String> getPolicyQualifiedStepsFrom( List<String> arg ) {
		//
		List<String> result = new ArrayList<String>();
		
		for ( int i = 0; i < arg.size(); i++ ) {
			
			String stepId = arg.get(i);
			
			int invervalSeconds = getStepTaskExecutionIntevalInCurrentHour(stepId);
			long lastTimeMills = getStepTaskLastExectuionTimeMills(stepId);
			long nowMills = new Date().getTime();
			
			if ( ((nowMills - lastTimeMills)/1000) > invervalSeconds ) {
				result.add(stepId);
			}
		}
		
		return result;
	}
	
	//
	public static long getStepTaskLastExectuionTimeMills( String stepId ) {
		Long temp = step_id_2_last_execution_time_MAP.get( stepId );
		if ( temp == null ) return 0;
		return temp;
	}
	
	
	//
	public static int getStepTaskExecutionIntevalInCurrentHour(String stepId) {
		
		task_control temp = InfraSysAPI4.getTaskPolicy(stepId);
		
		Calendar calendar = Calendar.getInstance();
        calendar.setTime( new Date() );
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        
        if ( hour == 0 ) return temp.getH0();
        if ( hour == 1 ) return temp.getH1();
        if ( hour == 2 ) return temp.getH2();
        if ( hour == 3 ) return temp.getH3();
        if ( hour == 4 ) return temp.getH4();
        if ( hour == 5 ) return temp.getH5();
        if ( hour == 6 ) return temp.getH6();
        if ( hour == 7 ) return temp.getH7();
        if ( hour == 8 ) return temp.getH8();
        if ( hour == 9 ) return temp.getH9();
        if ( hour == 10 ) return temp.getH10();
        if ( hour == 11 ) return temp.getH11();
        if ( hour == 12 ) return temp.getH12();
        if ( hour == 13 ) return temp.getH13();
        if ( hour == 14 ) return temp.getH14();
        if ( hour == 15 ) return temp.getH15();
        if ( hour == 16 ) return temp.getH16();
        if ( hour == 17 ) return temp.getH17();
        if ( hour == 18 ) return temp.getH18();
        if ( hour == 19 ) return temp.getH19();
        if ( hour == 20 ) return temp.getH20();
        if ( hour == 21 ) return temp.getH21();
        if ( hour == 22 ) return temp.getH22();
        if ( hour == 23 ) return temp.getH23();
        
        return 123456789;
	}
}
