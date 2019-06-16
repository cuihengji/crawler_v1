package com.ruixuesoft.crawler.open.impl;

import org.apache.log4j.Logger;

import com.ruixuesoft.crawler.open.RxCrawler;
import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.RxDatabase;
import com.ruixuesoft.crawler.open.RxNode;
import com.ruixuesoft.crawler.open.RxResult;
import com.ruixuesoft.crawler.open.RxRule;
import com.ruixuesoft.crawler.open.RxTask;

public class Rule implements RxRule {

	private static final Logger logger = Logger.getLogger("OpenPlatform");

	@Override
	public RxResult execute(RxTask task, RxCrawler crawler, RxDatabase database) throws RxCrawlerException {

		
		logger.info("Hello platform!  V107 Mr Q @ 20170719");
//		logger.info("app capture start!");
	
//		crawler.open("https://www.baidu.com");
//
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
//		crawler.open("https://www.baidu.com", "百度");
//
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
//		crawler.open("https://www.tianyancha.com/search?key=%E7%99%BE%E5%BA%A6&checkFrom=searchBox");
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		RxNode tianyanchaNode = crawler.getNodeByXpath("/html/body/div[2]/div[1]/div/div/div[1]/div[3]");
//		RxNode tianyanchaSubnode = crawler.getNodeByXpath("/html/body/div[2]/div[1]/div/div/div[1]/div[3]/div[3]");
//		logger.info("capture data:" + tianyanchaSubnode.getText());
//		task.log("capture data:" + tianyanchaSubnode.getText());
		
//		RxNode node = crawler.getNodeByXpath("//*[@id=\"u1\"]/a[1]");
//		logger.info("capture data:" + node.getText());
//		logger.info(crawler.getNodeByXpath("//*[@id=\"u1\"]/a[1]").toString());
//		logger.info("app capture end!");
	
//		crawler.input("//*[@id=\"kw\"]", "呵呵");
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		crawler.clear("//*[@id=\"kw\"]");
//		
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
//		RxNode node = crawler.getNodeByXpath("//*[@id=\"kw\"]");
//		node.input("哈哈哈哈");
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		node.clear();
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
//		RxNode nodeAttribute = crawler.getNodeByXpath("//*[@id=\"su\"]");
//		String value = nodeAttribute.getAttribute("value");
//		logger.info("nodeAttribute:" + value);
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		//*[@id="form"]/input[6]
		
//		RxNode hiddenNode = crawler.getNodeByXpath("//*[@id=\"form\"]/input[6]", false);
//		
//		String hiddenValue = hiddenNode.getAttribute("value");
//		logger.info("hiddenValue:" + hiddenValue);
//		crawler.open("http://www.zhaopin.com/");
//		List<RxNode> nodes =  crawler.getNodesByXpath("//input");
//		for (RxNode node1 : nodes) {
//			String nodeValue = node1.getAttribute("value");
//			logger.info("nodeValue:" + nodeValue);
//		}
//		
//		
//		List<RxNode> hiddenNodes =  crawler.getNodesByXpath("//input", false);
//		for (RxNode node : hiddenNodes) {
//			String hiddenNodeValue = node.getAttribute("value");
//			logger.info("hiddenNodeValue:" + hiddenNodeValue);
//		}
//		
//
//		crawler.open("http://www.zhaopin.com/");
//		crawler.scrollToBottom();
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		crawler.scrollToTop();
//		
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		
//		crawler.scroll(800);
//		
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		
//		//*[@id="rmzw"]/div[2]/ul/li[1]/a
//		
//		RxNode scrollToNode  = crawler.getNodeByXpath("//*[@id=\"rmzw\"]/div[2]/ul/li[1]/a");
//		crawler.scrollTo(scrollToNode);
//		
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		crawler.open("http://www.ctrip.com/?allianceid=1630&sid=1911&ouid=000401app-&utm_medium=&utm_campaign=&utm_source=&isctrip=");
//		
//		//*[@id="searchHotelLevelSelect"]
//		RxSelectNode rxSelectNode = crawler.getSelectNodeByXpath("//*[@id=\"searchHotelLevelSelect\"]");
//		rxSelectNode.selectByIndex(1);
//		
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		rxSelectNode.selectByValue("3");
//		
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		rxSelectNode.selectByVisibleText("二星级以下/经济");
//		
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		logger.info("app capture end!");
		
		// 20170726 VerifyCodeHelper test
//		   task.log("VerifyCodeHelper test start");
//				crawler.open("http://shixin.court.gov.cn/");
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				
//				RxNode nameNode = crawler.getNodeByXpath("//*[@id='pName']");
//				nameNode.input("张三");
//				
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				
//		        for ( int i = 0 ; i < 3 ; i++) {
//		            
//		            // 点击换一张验证码
//		            if ( i != 0 ) {
//		            	
//		                RxNode changeVerifyCode = crawler.getNodeByXpath("//*[@id='searchForm']/div/table/tbody/tr[4]/td/a");
//		                changeVerifyCode.click();
//		                
//		            }
//		            
//		    		try {
//		    			Thread.sleep(5000);
//		    		} catch (InterruptedException e) {
//		    			e.printStackTrace();
//		    		}
//
//		 
//		    		crawler.inputVerifyCode("//*[@id='captchaImg']", "//*[@id='pCode']", "//*[@id='divcss5']/iframe");
//		            
//		    		RxNode seachBtnNode = crawler.getNodeByXpath("//*[@id='searchForm']/div/table/tbody/tr[5]/td/div/img");
//		    		seachBtnNode.click();
//		    		
//		    		try {
//		    			Thread.sleep(5000);
//		    		} catch (InterruptedException e) {
//		    			e.printStackTrace();
//		    		}
//		    		
//		    		RxNode errorElement = crawler.getNodeByXpath("//*[@id='ResultlistBlock']/h4/span" ,true);
//		            if ( errorElement != null) {
//		                if ( StringUtils.contains ( errorElement.getText (),"验证码") ) {
//		                    if ( i == 4 ) {
//		                        //验证码尝试5次失败。
//		                    	task.log("验证码尝试5次失败。");
//		                        return;
//		                    }
//		                    continue;
//		                }else {
//		                    break;
//		                }
//		            }else {
//		                break;
//		            }
//		        }
//		        
//				try {
//					Thread.sleep(5000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//
//		crawler.open("https://passport.zhaopin.com/org/login");
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		RxNode userNameNode = crawler.getNodeByXpath("//*[@id='LoginName']");
//		userNameNode.input("zjxm");
//		
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		
//		RxNode pwdNode = crawler.getNodeByXpath("//*[@id='Password']");
//		pwdNode.input("hnzj2015");
//		
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		//点击完成验证
//		RxNode verifyBtnNode = crawler.getNodeByXpath("//*[@id='CheckCodeCapt']");
//		verifyBtnNode.click();
//		
//		
//		crawler.selectVerifyCode("//*[@id='captcha']" , "//*[@id='captcha-submitCode']" ,0 ,0);
//
//		
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		task.log("VerifyCodeHelper test end");

		
//		
//	  logger.info("db test start!");
	  String sql = "";
	  Object[] params;
      sql = "INSERT INTO app75 ("
	              + "website, "
	              + "element, "
	              + "elementValue "  
	              + " )VALUES(?,?,?)";
      
       params = new Object[] {
              "www.baidu.com",
              "ele1",
              "百度一下"};

		database.insert(sql, params);
//		
//        sql  = "update app75 set elementValue = ? where seq = ?";
//        params = new Object[] {"200", 1};
//		database.update(sql, params);
//	    sql  = "delete from app75 where seq = ?";
//	    params = new Object[] {1};
//	    database.delete(sql, params);
//	    sql  = "select website, element, elementValue from app75 where seq = ?";
//	    params = new Object[] {2};
//	    List<App75data2> app75dataList = database.query(sql, App75data2.class, params);
//	    for (App75data2 data: app75dataList) {
//	    	logger.info("data.website:" + data.getWebsite());
//	    	logger.info("data.element:" + data.getElement());
//	    	logger.info("data.elementValue:" + data.getElementValue());
//	    }
		
//	    database.createTableFromTemplateTable("app75_1", "app75");
//	    Object[][] batchParams = {{"www.baidu.com", "ele1", "1"},{"www.baidu.com", "ele2", "2"}};
//	    database.batch(sql, batchParams);
	    
//		logger.info("db test end!");
	  
//		logger.info("Rxtask test start!");
//
//		logger.info("task.getAppSeq() :"+ task.getAppSeq());
//		task.log("appseq:" + task.getAppSeq());
//		logger.info("task.getScenarioIndex() :"+ task.getScenarioIndex());
//		logger.info("task.getRuleIndex() :"+ task.getRuleIndex());
//		logger.info("task.getScheduleType() :"+ task.getScheduleType());
//		logger.info("task.getCrawlerSeq() :"+ task.getCrawlerSeq());
//		logger.info("task.getSessionIndex() :"+ task.getSessionIndex());
//		logger.info("task.getCrawlerHostIP() :"+ task.getCrawlerHostIP());
//		logger.info("task.getTaskSeq() :"+ task.getTaskSeq());
//		logger.info("task.getUserName() :"+ task.getUserName());
//		logger.info("task.getUserName() :"+ task.getUserName());
//		logger.info("task.getSourceTaskSeq() :"+ task.getSourceTaskSeq());
//		logger.info("task.getSourceDataTableName() :"+ task.getSourceDataTableName());
//		logger.info("task.getV1() :"+ task.getV1());
////	    throw new RxCrawlerException(999, "异常了!!!");
//		TaskModel taskModel = new TaskModel();
//		taskModel.setScenarioIndex(1);
//		taskModel.setRuleIndex(1);
//		taskModel.setSourceDataTableName("app75");
//		taskModel.setSourceDataSeq(11);
//		taskModel.setV1("1");
//		taskModel.setV2("2");
//		taskModel.setV3("3");
//		task.createNextRuleTask(task.getV1(), task.getV2(), task.getV3(), task.getV4(), task.getV5(), task.getV6(), task.getV7(), task.getV8(), task.getV9());
//		
//		result.setFinishCode(200);
//		result.setPages(1);
//		result.setRecords(1);
//		result.setResult1("1xx");
//		result.setResult2("2xx");
//		result.setResult3("3xx");
//		
//		logger.info("Rxtask test end!");
		
		
//		task.log("alert test start");
//		crawler.open("http://172.20.5.11:8080/testws/alerttest.html");
//		RxNode alertNode = crawler.getNodeByXpath("//input[@class='alert']");
//		alertNode.click();
//		crawler.closeAlert();
//		task.log("alert test end");
		
		
		
		task.log("alert test start");
		crawler.open("http://www.w3school.com.cn/tiy/t.asp?f=hdom_alert");

		RxNode alertNode = crawler.getNodeByXpath("/html/body/input");
		alertNode.click();
		crawler.closeAlert();
		task.log("alert test end");	
		
		
		task.log("pages test end");
		
		task.log("tab test start");
		// 点击后没有新的tab打开
		crawler.open("https://www.baidu.com/");
		boolean hasNewTab = crawler.isNewTabOpened();
		task.log("hasNewTab:" + hasNewTab);
		
		crawler.input("//*[@id='kw']", "呵呵");
		RxNode tabNode = crawler.getNodeByXpath("//*[@id='su']");
		tabNode.click();
		
		hasNewTab = crawler.isNewTabOpened();
		task.log("hasNewTab:" + hasNewTab);
		
		// 点击后有新的tab打开
		crawler.open("http://www.163.com/");
		crawler.sleepSeconds(3);
		RxNode tabNode163 = crawler.getNodeByXpath("//*[@id='js_index2017_wrap']/div[2]/div[2]/div[1]/div[2]/ul/li[1]/a[2]");
		tabNode163.click();
		crawler.sleepSeconds(3);
		hasNewTab = crawler.isNewTabOpened();
		task.log("hasNewTab:" + hasNewTab);
		if (hasNewTab) {
			crawler.sleepSeconds(3);
			task.log("switchToNewTab start");
			crawler.switchToNewTab();
			task.log("switchToNewTab end");
			
			task.log("blabla......");
			crawler.sleepSeconds(3);
			task.log("closeNewTab start");
			crawler.closeNewTab();
			task.log("closeNewTab end");
			
		}
		task.log("tab test end");
//		
//		task.log("pages test start");
//		crawler.open("https://www.baidu.com/");
//		crawler.input("//*[@id='kw']", "呵呵");
//		RxNode clickNode = crawler.getNodeByXpath("//*[@id='su']");
//		clickNode.click();
//		try {
//		    Thread.sleep(5000);
//	    } catch (InterruptedException e) {
//		    e.printStackTrace();
//	    }

//        
////        task.log("抓取页数:" + String.valueOf(result.getPages()));
//		task.log("pages test end");
		
		// 图像字符串识别
		crawler.open("https://www.atobo.com.cn/Companys/366/15cbhv.html");
		String picString = crawler.uuPictureRecognition("/html/body/div[5]/div[3]/ul/li[1]/div[2]/div[4]/div/table/tbody/tr[6]/td/img");
		task.log(picString);
		
		
		RxResult result = new RxResult();
      result.setFinishCode(200);
		return result;
	}
	
	
	public static class App75data2 {
		
	    private String website;
	    private String element;
	    private String elementValue;
		public String getWebsite() {
			return website;
		}
		public void setWebsite(String website) {
			this.website = website;
		}
		public String getElement() {
			return element;
		}
		public void setElement(String element) {
			this.element = element;
		}
		public String getElementValue() {
			return elementValue;
		}
		public void setElementValue(String elementValue) {
			this.elementValue = elementValue;
		}
	}
}
