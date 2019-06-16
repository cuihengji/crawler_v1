//package uptime;

// https://docs.aws.amazon.com/zh_cn/lambda/latest/dg/java-tracing.html


//import java.io.IOException;
//import java.time.Instant;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
//import com.amazonaws.services.dynamodbv2.model.AttributeValue;
//import com.amazonaws.services.lambda.runtime.Context;
//import com.amazonaws.xray.AWSXRay;
//import com.amazonaws.xray.proxies.apache.http.HttpClientBuilder;
//
//public class Hello {
//    private static final Log logger = LogFactory.getLog(Hello.class);
//
//    private static final AmazonDynamoDB dynamoClient;
//    private static final HttpClient httpClient;
//
//    static {
//        dynamoClient = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
//        httpClient = HttpClientBuilder.create().build();
//    }
//    public void checkUptime(Context context) {
//        AWSXRay.createSubsegment("makeRequest", (subsegment) -> {
//
//            HttpGet request = new HttpGet("https://aws.amazon.com/");
//            boolean is2xx = false;
//
//            try {
//                HttpResponse response = httpClient.execute(request);
//                is2xx = (response.getStatusLine().getStatusCode() / 100) == 2;
//                subsegment.putAnnotation("responseCode", response.getStatusLine().getStatusCode());
//            } catch (IOException ioe) {
//                logger.error(ioe);
//            }
//            Map<String, AttributeValue> item = new HashMap<>();
//            item.put("Timestamp", new AttributeValue().withN("" + Instant.now().getEpochSecond()));
//            item.put("2xx", new AttributeValue().withBOOL(is2xx));
//            dynamoClient.putItem("amazon-2xx", item);
//        });
//    }
//}



package user123.app123.recipe3;


import java.util.Date;

import com.web2data.open.RxResult;
import com.web2data.open.RxSeniorStep;
import com.web2data.open.RxTask;

public class MyStep extends RxSeniorStep {

	@Override
	private RxTask createTestTask() {
		//					v1						v2			v3
		return new RxTask( "https://www.baidu.com", "零壹光年", "3");
	}
	
	
	@Override
	public void handle( RxTask task, RxResult result ) {
		//RxResult result = new RxResult();
		
		
//	    // Initialize the Log4j logger.
//	    static final Logger logger = LogManager.getLogger(Hello.class);
//
//	    public String myHandler(String name, Context context) {
//	        logger.error("log data from log4j err.");
//
//	        // Return will include the log stream name so you can look
//	        // up the log later.
//	        return String.format("Hello %s. log stream = %s", name, context.getLogStreamName());
//	    }
		
		
		// Logger logger = CONTEXT.getLogger();
		//logger.error();
		//logger.warn();
		//logger.info();
		//logger.fatal();
		
		
		
		//LambdaLogger logger = CONTEXT.getLogger();
		//logger.log("Log data from LambdaLogger \n with multiple lines");
		
		System.out.println("Hello World!!!"); // 与 CONSOLE.log()的作用相同
		CONSOLE.log("Hello World!!!");  
		// CONSOLE 也算是一个工具，对于测试和生产的log, 保存到本地的一个专属文件中，
		// 然后上传到OSS(建议不要自己搭建)中，把链接URL保存到Task中的console_log列中
		
		CONTEXT.  // 根据需求，能够获得一些奇奇怪怪的环境信息
//		上下文方法
//
//		getRemainingTimeInMillis() – 返回在执行超时以前剩余的毫秒数。
//
//		getFunctionName() – 返回 Lambda 函数的名称。
//
//		getFunctionVersion() – 返回函数的版本。
//
//		getInvokedFunctionArn() – 返回用于调用函数的 Amazon 资源名称 (ARN)。指示调用方是否已指定版本或别名。
//
//		getMemoryLimitInMB() – 返回为函数配置的内存量。
//
//		getAwsRequestId() – 返回调用请求的标识符。
//
//		getLogGroupName() – 返回函数的日志组。
//
//		getLogStreamName() – 返回函数实例的日志流。
//
//		getIdentity() – （移动应用程序）返回有关授权请求的 Amazon Cognito 身份的信息。
//
//		getClientContext() – （移动应用程序）返回客户端应用程序为 Lambda 调用方提供的客户端上下文。
//
//		getLogger() – 返回函数的记录器对象。
		
		result.setFinishedCode(100); //  100 <= FinishedCode <= 699
		result.setY1(100); //
		result.setY2(200); //
		result.setY3(300); //
		result.setStringY1(100); //
		result.setStringY2(200); //
		result.setStringY3(300); //
		
		return result;
		return 
		
		System.out.println(" ---------------------- MyStage.execute() ---------------------------------" + new Date() );
		

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
		myTask.log( "============== Hello World!!! ===============" );
		
		return;
	}
	
}
