package user123.app123.recipe3;


import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.web2data.open.RxResult;
import com.web2data.open.RxSeniorStep;
import com.web2data.open.RxTask;
import com.web2data.utility.U;


/**
 * @author iamstanley
 * @version 123
 *
 */
public class MyStep1 extends RxSeniorStep {

	@Override
	protected RxTask createTestTask() {
		return new RxTask("https://www.baidu.com", "零壹光年", "3").setExecutionTimeoutSeconds(5*60);
	}
	
	
	@Override
	public void execute( RxTask task, RxResult result ) {
		
		// Running in an Isolated Environment - Windows 7 Tutorial
		
		if ( _IN_TEST_ENVIRONMENT ) {
			//
		}
		
		if ( _IN_PRODUCTION_ENVIRONMENT ) {
			//
		}
		
		
//		// 可以直接使用 CAPTCHACODE 的方法实现一些常见简单的验证码服务, 采用这种方式，选择的是 默认的序号最小的 Service
//		CAPTCHACODE.xxx(...);
//		CAPTCHACODE.yyy(...);
//		CAPTCHACODE.zzz(...);
//		
//		// 每家的验证码可能会有很大不同，所以建议，先分后合
//		XxxService xxxService1 = CAPTCHACODE.getDefaultXxxService();
//		XxxService xxxService2 = CAPTCHACODE.getXxxServiceByIndex(2); //在后台配置了多个
//		
//		YyyService yyyService1 = CAPTCHACODE.getDefaultYyyService();
//		YyyService yyyService2 = CAPTCHACODE.getYyyServiceByIndex(2); //在后台配置了多个
//		
////		java.sql.Connection connection = _DATABASE.getDefaultMySQLDatabaseConnection();			// import java.sql.Connection;
////		java.sql.Connection connection = _DATABASE.getDefaultOracleDatabaseConnection();		// import java.sql.Connection;
////		java.sql.Connection connection = _DATABASE.getDefaultSQLServerDatabaseConnection();		// import java.sql.Connection;
////		com.mongodb.client.MongoDatabase mongoDatabase = _DATABASE.getDefaultMongoDatabase();	// import com.mongodb.client.MongoDatabase;
//
//		// 如果开发者有多个 Proxy Service,默认的情况下自动选择了缺省的proxy Service, 如果需要手工切换到其他的服务
//		PROXYPOOL.switchToServiceByIndex(2);
//		PROXYPOOL.switchToServiceByIndex(3);
//		
//		// 可能存在多种代理，这个需要慢慢细化和总结，然后设计不同的方法名
//		PROXYPOOL.getOneProxy(); //  
//		PROXYPOOL.getOneStaticProxy(); 	// 类型Static
//		PROXYPOOL.getOneDynamicProxy(); // 类型Dynamic
//		PROXYPOOL.getOneXxxxProxy(); 	// 类型Xxxx
//		PROXYPOOL.getOneYxxxProxy(); 	// 类型Yxxx
//		PROXYPOOL.getOneZxxProxy(); 	// 类型Zxx
//		
//		// 利用环境或Task的环境类型，来判断具体
//		if ( _IN_TEST_ENVIRONMENT ) {
//			BROWSER.useProxy( PROXYPOOL.getOneProxy() );
//			// PROXYPOOL.getOneProxy(); 找到则返回具体Proxy对象，找不到则返回null，如果没有配置service provider,则要抛出异常 NoProxyServiceProviderConfigured
//			// BROWSER.useDynamicProxy( PROXYPOOL.getOneProxy() ); 成功返回 true, 失败范围false
//		} else if ( task.isProductionTask() ) {
//			BROWSER.useStaticDirect();
//		}
//		
//		
//		// 开发人员利用 某个值来判断 是否使用代理
//		if ( _IN_PRODUCTION_ENVIRONMENT && task.getX1() > 100 && task.getX1() < 200 ) {
//			BROWSER.useProxy( PROXYPOOL.getOneProxy() );
//		} else {
//			BROWSER.useStaticDirect();
//			// BROWSER.useFactoryStaticIP(); // BROWSER.useFactoryDirectIP();
//		}
		
		
		//System.out.println(" ---------------------- Step1.execute() 1 ---------------------------------" + new Date() );
		
		String x1 = task.getX1();

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
			
			
			java.sql.Connection connection = DATABASE.getDefaultDatabaseConnection();
			
			String sql = "insert into test(name) values('cvcvcv')";
		    PreparedStatement pstmt;
		    try {
		        pstmt = (PreparedStatement) connection.prepareStatement(sql);
		        //pstmt.setString(0, "test1212");
		        
		        System.out.println( "=" + pstmt.toString() + "=");
		        
		        pstmt.execute(sql);
		        pstmt.close();
		        //connection.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
			
		    
		    org.apache.commons.dbutils.QueryRunner queryRunner = DATABASE.getDefaultDatabaseQueryRunner();
		    
            int rows = queryRunner.update("INSERT INTO test(name) VALUES(?)", "阡陌");
		    
		} catch ( Exception e ) {
			System.out.println( e.getMessage() + e.getLocalizedMessage() );
		}
		
		
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
		

		// 使用包装的方法，只针对关系型数据库针对的数据库的顺序： RDB ( 选择默认数据库中index最小的一个，因为每种数据库都可以有一个默认。所以可以存在多个默认 )
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
