package com.ruixuesoft.crawler.open.impl;

import java.awt.Rectangle;  
import java.awt.image.BufferedImage;  
import java.io.File;  
  
import javax.imageio.ImageIO;  
  
import org.apache.commons.io.FileUtils;  
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;  
import org.openqa.selenium.Point;  
import org.openqa.selenium.TakesScreenshot;  
import org.openqa.selenium.WebDriver;  
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsDriver;

import com.ruixuesoft.crawler.open.RxCrawlerException;  
  
public class Util {  
  
    //页面元素截图  
    public static File captureElement(WebElement element) throws Exception {  
        WrapsDriver wrapsDriver = (WrapsDriver) element;  
        // 截图整个页面  
        File screen = ((TakesScreenshot) wrapsDriver.getWrappedDriver()).getScreenshotAs(OutputType.FILE);  
        
        //element.getScreenshotAs(arg0)
        BufferedImage img = ImageIO.read(screen);  
        // 获得元素的高度和宽度  
        int width = element.getSize().getWidth();  
        int height = element.getSize().getHeight();  
        // 创建一个矩形使用上面的高度，和宽度  
        Rectangle rect = new Rectangle(width, height);  
        // 得到元素的坐标  
        Point p = element.getLocation();  
        BufferedImage dest = img.getSubimage(p.getX(), p.getY()-500, rect.width,rect.height);  
        //存为png格式  
        ImageIO.write(dest, "png", screen);  
        return screen;  
    }  
      
 
    public static void testCaptureElement(){  
    	
    	System.setProperty("webdriver.chrome.driver", "c:\\chromedriver_v2.27.exe");
		
//      ChromeOptions options = new ChromeOptions();
//      options.addArguments("--start-maximized");
//      driver = new ChromeDriver(options);
    	 WebDriver driver = new ChromeDriver();
        
        driver.manage().window().maximize();  
      
        driver.get("https://www.atobo.com.cn/Companys/366/15cbhv.html");  

        
        WebElement wb = driver.findElement(By.xpath("/html/body/div[5]/div[3]/ul/li[1]/div[2]/div[4]/div/table/tbody/tr[6]/td/img"));  
//        WebElement wb = driver.findElement(By.xpath("/html/body/div[5]/div[3]/ul/li[1]/div[2]/h2/div[1]/ul/li[1]/a"));  
        

//      		Coordinates coordinate = ((Locatable) wb).getCoordinates();
//      		coordinate.onPage();
//      		coordinate.inViewPort();
        
        Point wbPoint = wb.getLocation();
        
        System.out.println(wbPoint.getX());
        System.out.println(wbPoint.getY());
        
        String pixels = "500";
        try {
			String javaScripit = "window.scrollTo(0, " + pixels + ")";
			((JavascriptExecutor) driver).executeScript(javaScripit);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (Throwable t) {
			
			t.printStackTrace();
		}
        
         
        try {  
            FileUtils.copyFile(captureElement(wb), new File("c:\\333.png"));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        driver.quit();  
    }  
    
    public static void main(String[] args) {
		
    	testCaptureElement();
	}
}  
