/**
 * Title: Yanzhengma.java
 * Package test
 * Description: TODO
 * 
 * @author Administrator
 * @date 2017年8月31日 下午5:34:09
 * @version V1.0
 */
package com.ruixuesoft.crawler.open.uurecognition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.ruixuesoft.crawler.open.impl.GetWebElementService;


/**
 * ClassName: Yanzhengma
 * Description: TODO
 * @author Administrator
 * @date 2017年8月31日 下午5:34:09
 */
public class Yanzhengma {

    
    public static void main(String[] args) throws Exception {
        
        System.setProperty("webdriver.chrome.driver", "c:\\chromedriver_v2.27.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        
        //driver.navigate ().to ( "https://www.atobo.com.cn/Companys/667/14lc8s.html" );
        driver.navigate ().to ( "https://www.atobo.com.cn/Companys/366/15cbhv.html" );
        
        WebElement imangeE = GetWebElementService.getElementByXpath ( driver , "/html/body/div[5]/div[3]/ul/li[1]/div[2]/div[4]/div/table/tbody/tr[6]/td/img" , true );
        String src = imangeE.getAttribute ( "src" );
        System.out.print ( src );
        
        TelPictureRecognition tpr = new TelPictureRecognition();
//        String tel = tpr.recognise ( driver , src );
//        String tel = tpr.recogniseOnLinux(driver, src);
        
//        System.out.print ( tel );
    
    }
}
