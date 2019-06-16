package com.web2data.open;

import com.web2data.engine.crawler.httpclient.HttpClientInfra;
import com.web2data.engine.crawler.jsoup.JsoupInfra;
import com.web2data.engine.database.DatabaseInfra;
import com.web2data.engine.service.CaptchaCodeInfra;
import com.web2data.engine.service.ProxyPoolInfra;

public abstract class RxStep {

	
	public boolean _IN_TEST_ENVIRONMENT;
	
	public boolean _IN_PRODUCTION_ENVIRONMENT;
	
	protected HttpClientInfra HTTPCLIENT = null; // com.web2data.engine.crawler.httpClient
	
	protected JsoupInfra JSOUP = null; // com.web2data.engine.crawler.jsoupInfra
	
	protected DatabaseInfra DATABASE = null;
	
	//protected MySQLDatabaseInfra MYSQLDATABASE = MySQLDatabaseInfra.getInstance();
	
	//protected MICROSOFTSQLServerDatabaseInfra MICROSOFTSQLSERVERDATABASE = MicrosoftSQLServerDatabaseInfra.getInstance();
	
	//protected OracleDatabaseInfra ORACLEDATABASE = OracleDatabaseInfra.getInstance();
	
	//protected MongoDatabaseInfra MONGODATABASE = MongoDatabaseInfra.getInstance();
	
	
	protected CaptchaCodeInfra CAPTCHACODE = null;
	
	protected ProxyPoolInfra PROXYPOOL = null;
	
	
	public void _execute( RxTask task, RxResult result ) throws Exception {
		createTestTask();
		_before( task, result );
		execute( task, result );
		_after( task, result );
	}
	
	//protected abstract void init();

	protected abstract RxTask createTestTask();
	
	protected void _before( RxTask task, RxResult result ) throws Exception {
		
		HTTPCLIENT = HttpClientInfra.getInstance();; // com.web2data.engine.crawler.httpClient
		
		JSOUP = JsoupInfra.getInstance(); // com.web2data.engine.crawler.jsoupInfra
		
		DATABASE = DatabaseInfra.getInstance();
		
		CAPTCHACODE = CaptchaCodeInfra.getInstance();
		
		PROXYPOOL = ProxyPoolInfra.getInstance();
	}
	
	
	protected abstract void execute( RxTask task, RxResult result ) throws Exception;
	
	
	protected void _after( RxTask task, RxResult result ) throws Exception {
		
		// 关闭数据库连接
		DATABASE.releaseDatabaseResource();
	}
	
	// 用于
	public RxTask _testingTask = null;
	
	protected void createTestingTask() { createTestingTask(null,null,null,null,null,null,null,null,null); }
	protected void createTestingTask( String x1 ) { createTestingTask(x1,null,null,null,null,null,null,null,null); }
	protected void createTestingTask( String x1, String x2 ) { createTestingTask(x1,x2,null,null,null,null,null,null,null); }
	protected void createTestingTask( String x1, String x2, String x3 ) { createTestingTask(x1,x2,x3,null,null,null,null,null,null); }
	protected void createTestingTask( String x1, String x2, String x3, String x4 ) { createTestingTask(x1,x2,x3,x4,null,null,null,null,null); }
	protected void createTestingTask( String x1, String x2, String x3, String x4, String x5 ) { createTestingTask(x1,x2,x3,x4,x5,null,null,null,null); }
	protected void createTestingTask( String x1, String x2, String x3, String x4, String x5, String x6 ) { createTestingTask(x1,x2,x3,x4,x5,x6,null,null,null); }
	protected void createTestingTask( String x1, String x2, String x3, String x4, String x5, String x6, String x7 ) { createTestingTask(x1,x2,x3,x4,x5,x6,x7,null,null); }
	protected void createTestingTask( String x1, String x2, String x3, String x4, String x5, String x6, String x7, String x8 ) { createTestingTask(x1,x2,x3,x4,x5,x6,x7,x8,null); }
	protected void createTestingTask( String x1, String x2, String x3, String x4, String x5, String x6, String x7, String x8, String x9 ) { 
		//createTestingTask(null,null,null,null,null,null,null,null,null);
		
		_testingTask = new RxTask( x1,x2,x3,x4,x5,x6,x7,x8,x9 );
	}
	
}
