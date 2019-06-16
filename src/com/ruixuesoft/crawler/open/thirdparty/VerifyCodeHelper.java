package com.ruixuesoft.crawler.open.thirdparty;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;

import com.rkylin.crawler.engine.flood.util.FileUtil;
import com.sz789.QQchaorenHttp;
import com.sz789.qqchaorenB;
import com.web2data.system.config._main.ConfigFacade;

public class VerifyCodeHelper {

    private static final transient Logger log = Logger.getLogger ( VerifyCodeHelper.class );

    private static final String FILE_PATH = FileUtil.getImageFolderPath();
    
    private static final String FILE_PATH_LINUX = File.separator + "var" + File.separator + "verifyimge" + File.separator;

    public static void selectVerifyCode ( WebDriver webDriver , String imageXpath , String buttonXpath , int imgXOffset , int imgYOffset ) throws Exception {

        try {
        	
            boolean isWindowsHost = ConfigFacade.isWindowsHost();
            
            // download验证码图片的路径
            StringBuffer filePathBuf = null;
            if ( isWindowsHost ) {
            	
            	filePathBuf = new StringBuffer ( FILE_PATH );
            	
            } else {
            	
            	filePathBuf = new StringBuffer ( FILE_PATH_LINUX );
            	
            }
            
            String curTimeStr = new SimpleDateFormat ( "yyyyMMddHHmmss" ).format ( new Date () );
            filePathBuf.append ( "img" ).append ( curTimeStr ).append ( ".jpg" );
            String fileName = filePathBuf.toString ();

            // 远程服务返回的验证码
            String position = "";

            WebElement webElement = getElementByXpath ( webDriver , imageXpath , true );

            Actions act = new Actions ( webDriver );
            act.moveToElement ( webElement ).perform ();
            // 保存验证码图
            savePicture ( webDriver , webElement , fileName , imgXOffset , imgYOffset );
            
            if ( isWindowsHost ) {
            	
                position = qqchaorenB.selectValidationCode (fileName , imgXOffset , imgYOffset );
                
            } else {
            	
            	position = QQchaorenHttp.getSelectValidationCode ( fileName );

            }


            String[] positionArray = StringUtils.split ( position , ";" );
            int xOffset = 0;
            int yOffset = 0;
            
            webDriver.switchTo ().defaultContent ();
            
            for ( int i = 0 ; i < positionArray.length ; i++ ) {
                String[] arr = StringUtils.split ( positionArray[ i ] , "," );

                if ( arr != null ) {
                    if ( arr.length == 2 ) {
                        xOffset = Integer.parseInt ( arr[ 0 ] );
                        yOffset = Integer.parseInt ( arr[ 1 ] );
                    }
                }
                
                act.moveToElement ( webElement , xOffset , yOffset );
                act.click ();
                act.build ().perform ();
                Thread.sleep ( 1000 );
            }

            webDriver.switchTo ().defaultContent ();

            webElement = getElementByXpath ( webDriver , buttonXpath , true );
            Thread.sleep ( 1000 );
            webElement.click ();
        } catch ( Exception e ) {
            log.info ( e , e );
            throw e;
        }
    }


    public static void inputVerifyCode ( WebDriver webDriver , String imageXpath , String textXpath , int imgXOffset , int imgYOffset ) throws Exception {

        try {
            // download验证码图片的路径
            boolean isWindowsHost = ConfigFacade.isWindowsHost();
            
            // download验证码图片的路径
            StringBuffer filePathBuf = null;
            if ( isWindowsHost ) {
            	
            	filePathBuf = new StringBuffer ( FILE_PATH );
            	
            } else {
            	
            	filePathBuf = new StringBuffer ( FILE_PATH_LINUX );
            	
            }
            
            String curTimeStr = new SimpleDateFormat ( "yyyyMMddHHmmss" ).format ( new Date () );
            filePathBuf.append ( "img" ).append ( curTimeStr ).append ( ".jpg" );
            String fileName = filePathBuf.toString ();

            WebElement webElement = getElementByXpath ( webDriver , imageXpath , true );

            Actions act = new Actions ( webDriver );
            act.moveToElement ( webElement ).perform ();
            // 保存验证码图
            savePicture ( webDriver , webElement , fileName , imgXOffset , imgYOffset );
            
            String validationCode = "";
            if ( isWindowsHost ) {
            	
                validationCode = qqchaorenB.inputValidationCode (fileName , imgXOffset , imgYOffset );
                
            } else {
            	
            	validationCode = QQchaorenHttp.getInputValidationCode ( fileName );
            	
            }

            log.info ("=验证码=" + validationCode );
            WebElement textElement = getElementByXpath ( webDriver , textXpath , true );
            
            textElement.clear ();
            textElement.sendKeys ( validationCode );

            webDriver.switchTo ().defaultContent ();
 
        } catch ( Exception e ) {
            log.info ( e , e );
            throw e;
        }
    }

    /**
     * 使用WebDriver对页面截
     * 
     * @param driver
     * @return
     * @throws IOException
     */
    public static byte[] takeScreenshot ( WebDriver driver ) throws IOException {
        WebDriver augmentedDriver = new Augmenter ().augment ( driver );
        return ( ( TakesScreenshot ) augmentedDriver ).getScreenshotAs ( OutputType.BYTES );
    }

    /**
     * 生成验证码图
     * 
     * @param driver
     * @param webElement
     * @return
     * @throws IOException
     */
    private static BufferedImage createElementImage ( WebDriver driver , WebElement webElement , int imgXOffset , int imgYOffset ) throws IOException {
      
    	// 获得webElement的位置和大小
        Point location = webElement.getLocation ();
        Dimension size = webElement.getSize ();
        
        // 创建全屏截图
        BufferedImage originalImage = ImageIO.read ( new ByteArrayInputStream ( takeScreenshot ( driver ) ) );
        
        int screenHeight = originalImage.getHeight();
        log.info ( "全屏截图的高度:" + screenHeight );
    	log.info ( "图片高度:" + size.getHeight() );
    	
        int X = location.getX () + imgXOffset;
        int y = location.getY () + imgYOffset;
        int scrollHeight = 0;
        if ( location.getY () + imgYOffset + size.getHeight () - screenHeight > 0 ) {
        
        	scrollHeight = location.getY () + imgYOffset + size.getHeight () - screenHeight;
        	
        	y = location.getY () + imgYOffset - scrollHeight;
        	
        	log.info ( "滚动高度:" + scrollHeight );
        	log.info ( "滚动后Y:" + y );
        	
        	scroll( driver ,  scrollHeight);
        }
        
        // 截取webElement在位置的子图
        BufferedImage croppedImage = originalImage.getSubimage (  X , y, size.getWidth () , size.getHeight ());
        return croppedImage;
    }
    
	
    /**
     * 将验证码图片保存
     * 
     * @param driver
     * @param webElement
     * @param fileName
     * @throws IOException
     */
    public static void savePicture ( WebDriver driver , WebElement webElement , String fileName , int imgXOffset , int imgYOffset ) throws IOException {
        try {
            BufferedImage inputbig = createElementImage ( driver , webElement , imgXOffset , imgYOffset );
            File file = new File ( fileName );
            String filePath = file.getParent ();
            File fileParent = new File ( filePath );
            if ( ! fileParent.exists () ) {
                fileParent.mkdirs ();
            }
            ImageIO.write ( inputbig , "jpg" , file );
        } catch ( IOException e ) {
            throw e;
        }
    }
    
    
    /**
	 * 通过xpath找到显示的网页元素
	 * 
	 * @param driver
	 * @param xpath
	 * @param isDisplayed
	 *            元素是否要求在页面显示
	 * @return
	 */
	public static final WebElement getElementByXpath(WebDriver driver, String xpath, boolean isDisplayed) {
		WebElement element = null;
		driver.switchTo().defaultContent();
		List<WebElement> webElementList = driver.findElements(By.xpath(xpath));
		if (webElementList != null && webElementList.size() > 0) {
			for (WebElement webElement : webElementList) {
				if (!isDisplayed) {
					element = webElement;
					break;
				} else {
					if (webElement.isDisplayed()) {
						element = webElement;
						break;
					}
				}
			}
		}
		if (element == null) {
			List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
			if (iframes == null || iframes.size() == 0) {
				return element;
			}
			for (WebElement iframe : iframes) {
				try {
					driver.switchTo().defaultContent();
					driver.switchTo().frame(iframe);
					List<WebElement> elementList = driver.findElements(By.xpath(xpath));
					if (elementList != null && elementList.size() > 0) {
						for (WebElement webElement : elementList) {
							if (webElement.isDisplayed()) {
								element = webElement;
								break;
							}
						}
					} else {
						List<WebElement> subIframes = driver.findElements(By.tagName("iframe"));
						if (subIframes == null || subIframes.size() == 0) {
							continue;
						}
						for (WebElement subIframe : subIframes) {
							try {
								driver.switchTo().frame(subIframe);
								elementList = driver.findElements(By.xpath(xpath));
								if (elementList != null && elementList.size() > 0) {
									for (WebElement webElement : elementList) {
										if (webElement.isDisplayed()) {
											element = webElement;
											break;
										}
									}
								}
							} catch (Exception ex) {
								continue;
							}
						}
					}
				} catch (Exception e) {
					continue;
				}
				if (element != null) {
					break;
				}
			}
		}

		return element;
	}
	
    private static void scroll( WebDriver driver , int pixels )  {
		try {
			String javaScripit = "window.scrollTo(0, " + pixels + ")";
			((JavascriptExecutor) driver).executeScript(javaScripit);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (Throwable t) {
			log.error(t.getMessage(), t);
		}
	}
    
    public  static void main (String[] args) throws InterruptedException {
//        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
//        WebDriver wd = new ChromeDriver();
//        wd.manage().window().maximize();
//        
//        OPEN.doOpen ( wd , "http://rdsearch.zhaopin.com/Home/SearchByCustom?source=rd");
//        Thread.sleep ( Constants.SLEEP_TIME_2000 );
//               
//        //https://passport.zhaopin.com/org/login
//        try {
//            
//            INPUT.doInput ( wd , "manpowersz22" , "//*[@id='LoginName']" );
//            INPUT.doInput ( wd , "941manpower" , "//*[@id='Password']" );
//            
//            inputCaptcha ( wd ,  "//*[@id='checkimg']" ,"//*[@id='CheckCode']"  , 0 , 0 );
//            
//            // 点击登陆
//            try {
//                CLICK.doClick ( wd , "//*[@id='loginbutton']" );
//            }catch(Exception e) {
//                CLICK.doClick ( wd , "//*[@id='login']" );
//            }
//        } catch ( Exception e ) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
}
