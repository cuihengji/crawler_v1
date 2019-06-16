package com.web2data.engine.crawler.browser.impl.a;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.ruixuesoft.crawler.open.RxHttpNode;
import com.ruixuesoft.crawler.open.impl.RxHttpNodeImpl;
import com.web2data.utility.U;

public class RxCrawlerImpl_99 {

	
//    // -------------------------------------------------------
//    private static final transient Logger logger = Logger.getLogger(RxCrawlerImpl_99.class);
//   
//    
//	public static boolean verifyQichacha( WebDriver webDriver ) {
//		
//		boolean verifyResult = false;
//		
//		WebElement sliderElement = webDriver.findElement(By.xpath("//*[@id=\"nc_1_n1z\"]"));
//		WebElement widthElement = webDriver.findElement(By.xpath("//*[@id=\"nc_1__scale_text\"]/span"));
//
//		int slideDistance =  widthElement.getSize().getWidth()-40;
//
//		Actions actions = new Actions(webDriver);
//		new Actions(webDriver).clickAndHold(sliderElement).perform();
//
//		int movedDistance = 0;
//		
//		while ((movedDistance - slideDistance) <= 0) {
//			
//			int step = 0;
//			if (movedDistance > slideDistance * 0.9) {
//				step = getRandom(1, 10);
//			} else {
//				step = getRandom(1, 20);
//			}
//			
//			movedDistance += step;
//			actions.moveByOffset(step, getRandom(1, 3)).perform();
//	       
//			try {
//				Thread.sleep(new Random().nextInt(11));
//			} catch (InterruptedException e) {
//				logger.error(e);
//			}
//		}
//		actions.release(sliderElement).perform();
//		
//		U.sleepSeconds(1);
//		
//		try {
//			WebElement textElement = webDriver.findElement(By.xpath("//*[@id=\"nc_1__scale_text\"]"));
//			logger.info("input text: -----------" + textElement.getText());
//
//			//有可能一次通过,没有后续的选择
//			if(textElement.getText().contains("验证通过")){
//				verifyResult = true;
//				return verifyResult;
//			}
//			
//			// 选择某个字
//			if (textElement.getText().contains("请点击")) {
//			
//				for (int i = 0; i < 5; i++) {
//
//					logger.info("select the text.........");
//
////					this.selectVerifyCode("//*[@id=\"nc_1_clickCaptcha\"]/div[2]", "//*[@id=\"nc_1__scale_text\"]");
//					this.selectVerifyCode("//*[@id=\"normalLoginPanel\"]", "//*[@id=\"nc_1__scale_text\"]");
//					
//					try {
//						
//						WebElement errorElement = webDriver.findElement(By.xpath("//*[@id=\"nc_1__captcha_text\"]/span"));
//						logger.info("error Message:" + errorElement.getText());
//						// 第二次或者以后的验证通过,textElement没有错误的提示信息
//						if (errorElement.getText().trim().equals("")) {
//							verifyResult = true;
//							break;
//						}
//					} catch (NoSuchElementException e) {
//						verifyResult = true;
//						break;
//					}
//					U.sleepSeconds(1);
//				}
//				
//				
//				
//			} else {// 输入字符
//
//				for (int i = 0; i < 5; i++) {
//
//					logger.info("input the text.........");
//
//					this.inputVerifyCode("//*[@id=\"nc_1_imgCaptcha\"]", "//*[@id=\"nc_1_captcha_input\"]");
//					U.sleepSeconds(2);
//
//					WebElement submitElement = webDriver.findElement(By.xpath("//*[@id=\"nc_1_scale_submit\"]/span"));
//					submitElement.click();
//					U.sleepSeconds(1);
//
//					try {
//						WebElement errorElement = webDriver.findElement(By.xpath("//*[@id=\"nc_1__captcha_img_text\"]/span"));
//						logger.info("error Message:" + errorElement.getText());
//						// 第二次或者以后的验证通过,存在errorElement但是没有错误的提示信息
//						if (errorElement.getText().trim().equals("")) {
//							verifyResult = true;
//							break;
//						}
//					} catch (NoSuchElementException e) {
//						verifyResult = true;
//						break;
//					}
//				}
//
//			}
//			
//		} catch (NoSuchElementException e) {//提示信息不出现,直接通过
//
//			verifyResult = true;
//		}
//		
//		return verifyResult;
//	}
//	
//	
//	public static List<RxHttpNode> getRxHttpNodeBySelector(String selector) {
//
//		List<RxHttpNode> rxHttpNode = new ArrayList<>();
//
//		if (this.httpDocument != null) {
//			Elements elementList = this.httpDocument.select(selector);
//
//			for (Element element : elementList) {
//				rxHttpNode.add(new RxHttpNodeImpl(element));
//			}
//		}
//		return rxHttpNode;
//	}
//
//	private static int getRandom(int min, int max)
//	{
//		Random random = new Random();
//		return random.nextInt(max) % (max - min + 1) + min;
//	}
	
}
