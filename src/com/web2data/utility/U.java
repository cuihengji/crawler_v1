package com.web2data.utility;

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

}
