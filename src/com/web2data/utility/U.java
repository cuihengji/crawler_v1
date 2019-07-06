package com.web2data.utility;

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
    
}
