package com.web2data._global;

import java.util.Calendar;

public class T {

	
	
//	Calendar calendar = Calendar.getInstance();
//    int now_y = calendar.get(Calendar.YEAR);//得到年份
//    int now_m = calendar.get(Calendar.MONTH)+1;//得到月份
//    int now_d = calendar.get(Calendar.DATE);//得到月份中今天的号数
//    int now_h = calendar.get(Calendar.HOUR_OF_DAY);//得到一天中现在的时间，24小时制
//    int now_mm = calendar.get(Calendar.MINUTE);
//    
	
	/**
	 * @param arg
	 * @return
	 */
	public static boolean mintutesEquals( int arg ) {
		Calendar calendar = Calendar.getInstance();
		int now_mm = calendar.get(Calendar.MINUTE);
		if ( now_mm == arg)
			return true;
		return false;
	}
}
