package com.rkylin.crawler.engine.flood.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static long diffdate(String now, String date) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date d1 = new Date();
		Date d2 = new Date();

		try {
			d1 = df.parse(now);
			d2 = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long diff;
		long time1 = d1.getTime();
		long time2 = d2.getTime();
		if (time1 < time2) {
			diff = time2 - time1;
		} else {
			diff = time1 - time2;
		}

		long day = diff / (24 * 60 * 60 * 1000);
		long hour = (diff / (60 * 60 * 1000) - day * 24);

		return hour;
	}
}
