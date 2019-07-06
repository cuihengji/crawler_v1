package com.web2data.engine.stage.xxx;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.web2data.open.RxResult;
import com.web2data.open.RxAdvancedStep;
import com.web2data.open.RxTask;
import com.web2data.utility.U;


public class Step1 extends RxAdvancedStep {

	
	@Override
	protected RxTask createTestTask() {
		//
		return null;
	}
	
	@Override
	public void execute( RxTask task, RxResult result ) {
		
		//System.out.println(" ---------------------- Step1.execute() 1 ---------------------------------" + new Date() );
		
		String x1 = task.getX1();
		
		System.out.println(" ==================================x1 = " + x1);
		
		//for (int i = 0; i < 10; i++ ) {
		//	System.out.print(" Step1 ");
		//	U.sleepSeconds(1);
		//}
		
		
		// _BROWSER提供两种方式操作浏览器：使用原生的WebDriver,或者使用包装的方法
		
		try {
			WebDriver webDriver = BROWSER.getWebDriver();
			
			webDriver.get( x1 );
			
			// //*[@id="u1"]/a[1]
			
			System.out.println(" test1 = " + webDriver.findElement( By.xpath("//*[@id=\'u1\']/a[1]") ).getText() );
			
			U.sleepSeconds(10);
			
			System.out.println(" test2 = " + webDriver.findElement( By.xpath("//*[@id=\'u1\']/a[1]") ).getText() );
			
			
			java.sql.Connection connection = DATABASE.getDefaultMySQLDatabaseConnection();
			
			
		} catch ( Exception e ) {
			System.out.println( e.getMessage() + e.getLocalizedMessage() );
		}
		
		
		
		
		
		//System.out.println(" x11 = " + x1);
		
		//_BROWSER.open( x1 );
		//_BROWSER.getNodeByXpath();
		
		
		// _DATABASE提供两种方式操作数据库：使用原生的Connection/MongoDatabase,或者使用包装的方法
		// 使用包装的方法针对的数据库的顺序： DefaultMySQL > DefaultSQLServer > DefaultSQLServer > DefaultMongoDatabase
		
//		java.sql.Connection connection = _DATABASE.getDefaultMySQLDatabaseConnection();			// import java.sql.Connection;
//		java.sql.Connection connection = _DATABASE.getDefaultOracleDatabaseConnection();		// import java.sql.Connection;
//		java.sql.Connection connection = _DATABASE.getDefaultSQLServerDatabaseConnection();		// import java.sql.Connection;
//		com.mongodb.client.MongoDatabase mongoDatabase = _DATABASE.getDefaultMongoDatabase();	// import com.mongodb.client.MongoDatabase;

//		org.apache.commons.dbutils.QueryRunner queryRunner = _DATABASE.getDefaultDatabaseQueryRunner();				// org.apache.commons.dbutils.QueryRunner
//		org.apache.commons.dbutils.QueryRunner queryRunner = _DATABASE.getDefaultMySQLDatabaseQueryRunner();		// org.apache.commons.dbutils.QueryRunner
//		org.apache.commons.dbutils.QueryRunner queryRunner = _DATABASE.getDefaultOracleDatabaseQueryRunner();		// org.apache.commons.dbutils.QueryRunner
//		org.apache.commons.dbutils.QueryRunner queryRunner = _DATABASE.getDefaultSQLServerDatabaseQueryRunner();	// org.apache.commons.dbutils.QueryRunner
//		
//		java.sql.Connection connection = _DATABASE.getMySQLDatabaseConnectionByIndex(2);		// import java.sql.Connection;
//		java.sql.Connection connection = _DATABASE.getOracleDatabaseConnectionByIndex(3);		// import java.sql.Connection;
//		
//		com.mongodb.client.MongoDatabase mongoDatabase = _DATABASE.getMongoDatabaseByIndex(4);	// import com.mongodb.client.MongoDatabase;
//		
//		org.apache.commons.dbutils.QueryRunner queryRunner = _DATABASE.getMySQLDatabaseQueryRunnerByIndex(2);		// org.apache.commons.dbutils.QueryRunner
//		org.apache.commons.dbutils.QueryRunner queryRunner = _DATABASE.getOracleDatabaseQueryRunnerByIndex(3);		// org.apache.commons.dbutils.QueryRunner
//		org.apache.commons.dbutils.QueryRunner queryRunner = _DATABASE.getSQLServerDatabaseQueryRunnerByIndex(4);	// org.apache.commons.dbutils.QueryRunner
		

		// 使用包装的方法，只针对关系型数据库针对的数据库的顺序： RDB ( DefaultMySQL > DefaultSQLServer > DefaultSQLServer )
		//_DATABASE.insert( "" ); _DATABASE.batch( "" ); _DATABASE.batchInsert( "" );
		//_DATABASE.delete( "" );
		//_DATABASE.update( "" );
		//_DATABASE.select( "" );
		
//		long startTime = System.currentTimeMillis();
//		
//		for (int i = 1; i <= 100; i++) {
//			
//			   String sql = " insert into test(name) values(?) ";
//			   String companyName = "大连瑞雪科技 " + i;
//			   System.out.print(i);
//			   Object[] hotelTypeRoomParams = new Object[]{companyName};
//               try {
//            	   Integer compayId = myDatabase.insert(sql, hotelTypeRoomParams);
//            	   System.out.println(compayId);
//               } catch (Exception e) {
//            	   e.printStackTrace();
//               }
//		}
//		
//		long endTime = System.currentTimeMillis();
//		long times = endTime - startTime;
//		System.out.println("times: " + times);
//		
//		//SessionThreadLocal.getBrowserSession()._browser.refreshTabWindows();
//		
//		RxBrowser myBrowser = myCrawler.getRxBrowser();
//		
//		
//		myBrowser.open( "http://www.baidu.com/" );
//		
//		U.sleepSeconds( 1 * 3 );
//		
//		RxNode node1 = myBrowser.getNodeByXpath("//*[@id='jgwab']");
//		System.out.println("百度备案号1：" + node1.getText());
//		node1.click(); // 打开新的TabWindow
//		U.sleepSeconds( 1 * 3 );
//		
//				myBrowser.openTabWindow();
//				RxNode node2 = myBrowser.getNodeByXpath("/html/body/div[2]/div/div/div/div[2]/div[1]/a/p");
//				//RxNode node2 = crawler.getNodeByXpath("/html/body/div[1]/div[1]/div/div/ul/li[2]/a");
//				System.out.println("备案号2：" + node2.getText());
//				node2.click(); // 打开新的TabWindow
//				U.sleepSeconds( 1 * 3 );
//				
//						//myBrowser.openTabWindow();
//						RxNode node3 = myBrowser.getNodeByXpath("/html/body/div[2]/div/div/div/div[2]/div[1]/a/p");
//						System.out.println("备案号3：" + node3.getText());
//						//node3.click(); // 打开新的TabWindow
//						U.sleepSeconds( 1 * 3 );
//						myBrowser.closeTabWindow();
//				
//				RxNode node22 = myBrowser.getNodeByXpath("/html/body/div[2]/div/div/div/div[2]/div[1]/a/p");
//				System.out.println("备案号22：" + node22.getText());
//				U.sleepSeconds( 1 * 3 );
//				myBrowser.closeTabWindow();
//		
//		RxNode node11 = myBrowser.getNodeByXpath("//*[@id='jgwab']");
//		System.out.println("百度备案号11：" + node11.getText());
//		
//		U.sleepSeconds( 1 * 3 );
//		myBrowser.close();
//		
//		myTask.setFinishedCode( 200 );
//		
		//System.out.println(" ---------------------- Step1.execute() 2 ---------------------------------" + new Date() );
		
		return;
	}
	
}
