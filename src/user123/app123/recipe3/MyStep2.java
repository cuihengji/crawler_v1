package user123.app123.recipe3;


import com.web2data.open.RxResult;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.web2data.open.RxAdvancedStep;
import com.web2data.open.RxTask;


/**
 * @author iamstanley
 * @version 123
 *
 */
public class MyStep2 extends RxAdvancedStep {

	@Override
	protected RxTask createTestTask() {
		return new RxTask("https://www.baidu.com", "零壹光年", "3").setTimeoutSeconds(5*60);
	}
	
	
	@Override
	public void execute( RxTask task, RxResult result ) {
		
		System.out.println(">>>>>>>>>> execute >>>>>>>>>> begin ");
		
		System.out.println("task.getX1()	= " + task.getX1() );
		
		System.out.println("BROWSER = " + BROWSER);
		System.out.println("BROWSER._webDriver = " + BROWSER._webDriver);
		
		WebDriver driver = BROWSER._webDriver;
		
		driver.get( "https://cn.bing.com/" );
		
		String title = driver.getTitle();
		
		WebElement element = driver.findElement( By.xpath("//*[@id=\"sb_form_q\"]") );
		if ( element != null ) {
			//
			element.sendKeys("Hello World!!!");
			element = driver.findElement( By.xpath("//*[@id=\"sb_form_go\"]") );
			element.click();
		}
		
		element = driver.findElement( By.xpath("//*[@id=\"b_header\"]/nav/ul/li[1]/a") );
		
		
		System.out.println("getText = " + element.getText());
		
		result.setFinishedCode( 201 );
		
		//System.out.println("<<<<<<<< end ");
		return;
	}
	
}
