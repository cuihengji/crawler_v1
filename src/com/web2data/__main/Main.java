package com.web2data.__main;

import org.apache.log4j.Logger;


public class Main {
	
	
    public static void main(String[] args) throws Throwable {

    	System.out.println( " ================= Hello World ! =================== " );
    	
    	
    	Thread thread = new Thread() { 
    		
    		public void run() {
    			
    			try {
    				
	    	    	// 读取本地配置文件 
	    	    	loadLocalProperties();
	    	    	
	    	    	// 从 Config系统获得配置
	    	    	com.web2data.system.config._main.Main.start();
	    	    	
	    	    	// 启动系统监控
	    	    	com.web2data._monitor._main.Main.start();
	    	    	
	    	    	// 启动主进程
	    	    	com.web2data.engine.core.SessionManager.start();
	    	    	
    			} catch ( Throwable e ) {
    				System.out.println( " com.web2data.__main.main(): " + e.getMessage() );
    	    	}
    		} 
    	}; 
    	
    	//System.out.println( " ================= Hello World !! =================== " );
    	
    	thread.run();
    	
    	//System.out.println( " ================= Hello World !!! =================== " );
    	
    	//while (true) {
    		//U.sleepSeconds( 15 );
    		//System.out.println( " ================= terminateOngoingTask =================== " );
    		//SessionManager._seniorSessionList.get(1).terminateOngoingTask();
    	//}

    }

    
    private static boolean loadLocalProperties() throws Throwable {
        //LocalConfigLoader.load();
    	return true;
    }
    
    
    // ========================================================
    
	private static Logger LOG = Logger.getLogger(Main.class);

}
