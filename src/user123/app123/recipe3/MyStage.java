package user123.app123.recipe3;


import java.util.Date;

import com.ruixuesoft.crawler.open.impl.RxDatabaseImpl;
import com.web2data.engine.database.ConnectionInfo;
import com.web2data.open.RxBrowser;
import com.web2data.open.RxCrawler;
import com.web2data.open.RxDatabase;
import com.web2data.open.RxNode;
import com.web2data.open.RxStage;
import com.web2data.open.RxTask;
import com.web2data.utility.U;

public class MyStage implements RxStage {

	@Override
	public void execute( RxTask myTask ) {
		
		System.out.println(" ---------------------- MyStage.execute() ---------------------------------" + new Date() );
		

//		long startTime = System.currentTimeMillis(); // 123456-789
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
//		
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
		myTask.log( "=3============= Hello World!!! ===============" );
		
		return;
	}
	
}
