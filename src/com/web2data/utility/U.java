package com.web2data.utility;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class U {

    public static void sleepMillis( int millis ) {
    	try {
			Thread.sleep( millis );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	return;
    }
    
    public static void sleepSeconds( int seconds ) {
    	try {
			Thread.sleep( seconds * 1000 );
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
    	return;
    }

    public static String getRandomElement( List<String> list ) {
    	if ( list.size() == 0 ) return null;
    	int n = new Random().nextInt(list.size());
    	return list.get(n);
    }
    
    public static int[] convertStringToInts( String arg ) {
    	if ( arg == null ) return null;
    	
    	String[] temps = arg.split("-");
    	int[] result = new int[temps.length];
    	for (int i = 0; i < temps.length; i++) {
    		//
    		result[i] = Integer.valueOf(temps[i]).intValue();
    	}
    	return result;
    }
    
    
    public static int getCurrentHour() {
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(new Date());
	    return calendar.get(Calendar.HOUR_OF_DAY);
    }
}
