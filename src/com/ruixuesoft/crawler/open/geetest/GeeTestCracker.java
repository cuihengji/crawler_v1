package com.ruixuesoft.crawler.open.geetest;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.rkylin.crawler.engine.flood.util.FileUtil;
import com.ruixuesoft.crawler.open.impl.RxCrawlerImpl;

public class GeeTestCracker {

	public static final Logger logger = Logger.getLogger(GeeTestCracker.class);
	
    public static String basePath = FileUtil.getImageFolderPath();
   	public static String FULL_IMAGE_NAME = "full-image";
   	public static String BG_IMAGE_NAME = "bg-image";
   	private static int[][] moveArray = new int[52][2];
   	private static boolean moveArrayInit = false;
    
	public static boolean verifyQixinGeeTest(WebDriver webDriver){

		webDriver.manage().window().setSize(new Dimension(800,600));
		boolean verifyResult = false;
   		
		try {
			By moveButton = By.cssSelector(".geetest_slider_button");

			waitForLoad(webDriver, moveButton);
			WebElement moveElemet = webDriver.findElement(moveButton);

			int distance = getQixinMoveDistance(webDriver);
			qixinMove(webDriver, moveElemet, distance);

			By gtTypeBy = By.cssSelector(".geetest_result_title");
			waitForLoad(webDriver, gtTypeBy);
			//必须Sleep后才能拿到验证的信息.
			Thread.sleep(1000);

			String gtType = webDriver.findElement(gtTypeBy).getAttribute("innerHTML");
			logger.info("gtType getAttribute(innerHTML)----------------------:" + gtType);

			if (gtType.equals("验证通过:")) {
				logger.info("--------------passed the geetest");
				verifyResult = true;
				Thread.sleep(4000);
			}
		} catch (Exception e) {
			logger.error("verifyQixinGeeTest Exception 异常！", e);
		} finally {
			webDriver.manage().window().maximize();
		}
		return verifyResult;
   	}
   	
   	
   	
   	public static boolean verifyGeeTest(WebDriver webDriver){
   		
		webDriver.manage().window().setSize(new Dimension(1024,768));
   		boolean verifyResult = false;
   		
   		int moveDistanceOffset = 6;
		try {
			while (!verifyResult) {
				// 通过[class=gt_slider_knob gt_show]
				By moveButton = By.cssSelector(".gt_slider_knob.gt_show");
				try {
					waitForLoad10Seconds(webDriver, moveButton);
				} catch (Exception e) {
					logger.error("--------------waitForLoad10Seconds", e);
					break;
				}
				WebElement moveElemet = webDriver.findElement(moveButton);

				int distance = getMoveDistance(webDriver);
				move(webDriver, moveElemet, distance - moveDistanceOffset);
				
				By gtTypeBy = By.cssSelector(".gt_info_type");

//				waitForLoad(webDriver, gtTypeBy);
//
//				// 等待几秒才能取到第二次的结果文本
				Thread.sleep(3000);
//				String gtType = webDriver.findElement(gtTypeBy).getText();
				String gtType = webDriver.findElement(gtTypeBy).getAttribute("innerHTML");
				logger.info("gtType ----------------------:" + gtType);
                
				if ( "".equals(gtType) || null == gtType ) {
					logger.error("gtType 为空，请重试!");
					return verifyResult;
				}
				
				if (gtType.equals("出现错误:")) {
					break;
				}

				if (!gtType.equals("再来一次:") && !gtType.equals("验证失败:")) {
					verifyResult = true;
					logger.info("--------------passed the geetest");
					Thread.sleep(4000);
				}

			}
		} catch (NoSuchElementException ne) {
			// no such element: Unable to locate element: {"method":"css selector","selector":".gt_info_type"}
			// 一次通过,页面已经跳转. 设置verifyResult = true;
			verifyResult = true;
			logger.info("no such element: Unable to locate element: {'method':'css selector','selector':'.gt_info_type'} 异常！ 页面应该已经正常跳转到下一页");
		} catch (Exception e) {
			logger.error("verifyGeeTest Exception 异常！", e);
		} finally {
			webDriver.manage().window().maximize();
			Dimension myfinalDimension  = webDriver.manage().window().getSize();
			logger.info("-------------verifyGeeTest finally Width:" + myfinalDimension.getWidth() + "<--> Height: " + myfinalDimension.getHeight());
		}
		return verifyResult;
   	}
   	
   	
	/**
	 * 等待元素加载，30s超时
	 * 
	 * @param driver
	 * @param by
	 */
	public static void waitForLoad(final WebDriver driver, final By by) {
		new WebDriverWait(driver, 30).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				WebElement element = driver.findElement(by);
				if (element != null) {
					return true;
				}
				return false;
			}
		});
	}
	//TODO refactor later
	/**
	 * 等待元素加载，30s超时
	 * 
	 * @param driver
	 * @param by
	 */
	public static void waitForLoad10Seconds(final WebDriver driver, final By by) {
		new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				WebElement element = driver.findElement(by);
				if (element != null) {
					return true;
				}
				return false;
			}
		});
	}
	
	
	/**
	 * 等待元素加载，30s超时
	 * 
	 * @param driver
	 * @param by
	 */
	public static WebElement waitForElementLoad(final WebDriver driver, final By by) {
		logger.info("waitForElementLoad 加载元素" + by);
		WebElement myDynamicElement = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return driver.findElement(by);
			}
		});
		
		return myDynamicElement;
	}
	

	/**
	 * 计算需要平移的距离
	 * 
	 * @param driver
	 * @return
	 * @throws IOException
	 */
	public static int getMoveDistance(WebDriver driver) throws IOException {
		String pageSource = driver.getPageSource();
		String fullImageUrl = getFullImageUrl(pageSource);

		String currentTimeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		FileUtils.copyURLToFile(new URL(fullImageUrl), new File(basePath + FULL_IMAGE_NAME + currentTimeStamp + ".jpg"));
		String getBgImageUrl = getBgImageUrl(pageSource);
		FileUtils.copyURLToFile(new URL(getBgImageUrl), new File(basePath + BG_IMAGE_NAME + currentTimeStamp + ".jpg"));
		initMoveArray(driver);
		restoreImage(FULL_IMAGE_NAME , currentTimeStamp);
		restoreImage(BG_IMAGE_NAME , currentTimeStamp);
		BufferedImage fullBI = ImageIO.read(new File(basePath  + "result/"+ FULL_IMAGE_NAME + currentTimeStamp + "result3.jpg"));
		BufferedImage bgBI = ImageIO.read(new File(basePath + "result/"+ BG_IMAGE_NAME + currentTimeStamp + "result3.jpg"));
		for (int i = 0; i < bgBI.getWidth(); i++) {
			for (int j = 0; j < bgBI.getHeight(); j++) {
				int[] fullRgb = new int[3];
				fullRgb[0] = (fullBI.getRGB(i, j) & 0xff0000) >> 16;
				fullRgb[1] = (fullBI.getRGB(i, j) & 0xff00) >> 8;
				fullRgb[2] = (fullBI.getRGB(i, j) & 0xff);
	
				int[] bgRgb = new int[3];
				bgRgb[0] = (bgBI.getRGB(i, j) & 0xff0000) >> 16;
				bgRgb[1] = (bgBI.getRGB(i, j) & 0xff00) >> 8;
				bgRgb[2] = (bgBI.getRGB(i, j) & 0xff);
				if (difference(fullRgb, bgRgb) > 255) {
					return i;
				}
			}
		}
		throw new RuntimeException("未找到需要平移的位置");
	}
	
	
	/**
	 * 计算需要平移的距离
	 * 
	 * @param driver
	 * @return
	 * @throws IOException
	 */
	public static int getQixinMoveDistance(WebDriver driver) throws IOException {
		
		String fullbgName = generateCanvasImage(driver, ".geetest_canvas_fullbg", FULL_IMAGE_NAME);
		String bgName = generateCanvasImage(driver, ".geetest_canvas_bg", BG_IMAGE_NAME);
			
		BufferedImage fullBI = ImageIO.read(new File(fullbgName));
		BufferedImage bgBI = ImageIO.read(new File(bgName));
		
		for (int i = 0; i < bgBI.getWidth(); i++) {
			for (int j = 0; j < bgBI.getHeight(); j++) {
				int[] fullRgb = new int[3];
				fullRgb[0] = (fullBI.getRGB(i, j) & 0xff0000) >> 16;
				fullRgb[1] = (fullBI.getRGB(i, j) & 0xff00) >> 8;
				fullRgb[2] = (fullBI.getRGB(i, j) & 0xff);
	
				int[] bgRgb = new int[3];
				bgRgb[0] = (bgBI.getRGB(i, j) & 0xff0000) >> 16;
				bgRgb[1] = (bgBI.getRGB(i, j) & 0xff00) >> 8;
				bgRgb[2] = (bgBI.getRGB(i, j) & 0xff);
				if (difference(fullRgb, bgRgb) > 255) {
					return i;
				}
			}
		}
		throw new RuntimeException("未找到需要平移的位置");
	}
	
	
	private static String generateCanvasImage(WebDriver driver, String cssCanvas, String imageType){
		
		String fileName = "";
		
		By canvasFull = By.cssSelector(cssCanvas);
		logger.info("canvasBackground ----------------------:" + canvasFull);
		WebElement canvasFullElemet = driver.findElement(canvasFull);
		logger.info("canvasElemet ----------------------:" + canvasFullElemet);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Object imageFullURL = jse.executeScript("return arguments[0].toDataURL('image/png');",canvasFullElemet);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("imageURL ----------------------:" + imageFullURL);
		
		String currentTimeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		fileName = basePath + currentTimeStamp + imageType + ".png";
		
        byte[] btImg = decodeBase64Image ( imageFullURL.toString());
		if (null != btImg && btImg.length > 0) {
			logger.info("读取到：" + btImg.length + " 字节");
			writeImageToDisk(btImg, fileName);
		}
		
		return fileName;
	}
	
	/** 
     * 根据地址获得数据的字节流 
     * @param strUrl 网络连接地址 
     * @return 
     */  
    private static byte[] decodeBase64Image(String strUrl){
        
    		String[] splitedRawCssData = strUrl.split(",");
    		//data:image/png;base64,
    		//String metaDataPart = splitedRawCssData[0];
    		String imagePart = splitedRawCssData[1];
    		final Base64.Decoder decoder = Base64.getDecoder();
    		byte[] imageBytes = decoder.decode(imagePart);
    		
    		return imageBytes;

    }  

    /** 
     * 将图片写入到磁盘 
     * @param img 图片数据流 
     * @param fileName 文件保存时的名称 
     */
    private static void writeImageToDisk(byte[] img, String fileName){  
        try {  
            File file = new File( fileName);  
            FileOutputStream fops = new FileOutputStream(file);  
            fops.write(img);  
            fops.flush();  
            fops.close();  
            logger.info("图片已经写入到C盘");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
    
    /**
	 * 移动
	 * 
	 * @param driver
	 * @param element
	 * @param distance
	 * @throws InterruptedException
	 */
    public static void qixinMove(WebDriver driver, WebElement element, int distance) throws InterruptedException {
    	driver.manage().window().setSize(new Dimension(800,600));
    	//启信宝2017-10-13改版
    	int xDis = distance + 13;
		logger.info("应平移距离：" + xDis);
		
		int moveX = new Random().nextInt(8) - 5;
		int moveY = 1;
		Actions actions = new Actions(driver);
		new Actions(driver).clickAndHold(element).perform();
		Thread.sleep(200);
		//printLocation(element);
		Point point = element.getLocation();
		logger.info("我的位置移动之前-----------move X:  "+ moveX + "move Y:  " + moveY);
		
		actions.moveToElement(element, moveX, moveY).perform();
		logger.info("我的位置移动之后--------------: " +point.getX() + " "+point.getY());
		
		for (int i = 0; i < 22; i++) {
			int s = 10;
			if (i % 2 == 0) {
				s = -10;
			}
			actions.moveToElement(element, s, 1).perform();
			Thread.sleep(new Random().nextInt(100) + 150);
		}
	
		logger.info("应平移距离：" + xDis + "---------------" + 1);

		printLocation(element);
		actions.moveByOffset((int)(xDis *0.5), getRandom(1, 5)).perform();
		printLocation(element);
		Thread.sleep(getRandom(1000, 3000));
		printLocation(element);
		actions.moveByOffset(-(int)(xDis *0.5), getRandom(1, 5)).perform();
		printLocation(element);
		Thread.sleep(getRandom(1000, 3000));
		
		int movedDistance = 0;
		
		while ((movedDistance - xDis) <= 0) {
			
			int step = 0;
			if (movedDistance > xDis * 0.9) {
				step = getRandom(1, 2);
			} else {
				step = getRandom(1, 5);
			}
			
			movedDistance += step;
			actions.moveByOffset(step, getRandom(1, 3)).perform();
	       	printLocation(element);
	        Thread.sleep(new Random().nextInt(11));
		}
		
		actions.release(element).perform();
	}
    
    
	/**
	 * 移动
	 * 
	 * @param driver
	 * @param element
	 * @param distance
	 * @throws InterruptedException
	 */
	public static void move(WebDriver driver, WebElement element, int distance) throws InterruptedException {
		//红盾2017-10-13改版
		//int xDis = distance + 11;
		//红盾2017-12-13改版
		int xDis = distance + 11;
		RxCrawlerImpl.logger.info("应平移距离：" + xDis);
		
		int moveX = new Random().nextInt(8) - 5;
		int moveY = getRandom(1, 5);
		Actions actions = new Actions(driver);
		new Actions(driver).clickAndHold(element).perform();
		Thread.sleep(200);
		printLocation(element);
		actions.moveToElement(element, moveX, moveY).perform();
		logger.info(moveX + "--" + moveY);
		printLocation(element);
		for (int i = 0; i < 10; i++) {
		//	for (int i = 0; i < 22; i++) {
			int s = 10;
			if (i % 2 == 0) {
				s = -10;
			}
			actions.moveToElement(element, s, 1).perform();
			// printLocation(element);
			Thread.sleep(new Random().nextInt(100) + 150);
		}
	
		RxCrawlerImpl.logger.info(xDis + "--:xDis" );
		//actions.moveByOffset(xDis, 1).perform();
		List<Integer> trackList = getTrack(xDis);
		int y = element.getLocation().getY();
		RxCrawlerImpl.logger.info("WWWWWWWWWWWW=" + y);
		for (Integer track : trackList) {
			//RxCrawlerImpl.logger.info("轨迹=" + track);
			actions.moveToElement(element, track+22, y);
			Thread.sleep(getRandom(1,  10));
		}
		actions.moveToElement(element, 21, y).perform();
		Thread.sleep(100);
		actions.moveToElement(element, 20, y).perform();
		Thread.sleep(100);
		actions.moveToElement(element, 18, y).perform();
		Thread.sleep(100);
		actions.moveToElement(element, 19, y).perform();
		Thread.sleep(100);
		actions.moveToElement(element, 19, y).perform();
		
		printLocation(element);
		Thread.sleep(200);
		actions.release(element).perform();
	}

	public static List<Integer> getTrack(int dis) {
		List<Integer> list = new ArrayList<>();
		int x = getRandom(1, 5);
		
		while ((dis - x) > 5) {
			list.add(x);
			dis = dis - x;
			x = getRandom(1, 5);
		}
		
		for (int i = 0; i < dis; i++) {
			list.add(1);
		}
		
		return list;
	}

	public static int getRandom(int min, int max)
	{
		Random random = new Random();
		return random.nextInt(max) % (max - min + 1) + min;
	}

	/**
		 * 获取原始图url
		 * 
		 * @param pageSource
		 * @return
		 */
		public static String getFullImageUrl(String pageSource) {
			String url = null;
			Document document = Jsoup.parse(pageSource);
			String style = document.select("[class=gt_cut_fullbg_slice]").first().attr("style");
	//		String style = document.select("[class=geetest_slicebg geetest_absolute]").first().attr("style");
	//		String style = document.select("geetest_canvas_fullbg geetest_fade geetest_absolute").first().attr("style");
			
			
			Pattern pattern = Pattern.compile("url\\(\"(.*)\"\\)");
			Matcher matcher = pattern.matcher(style);
			if (matcher.find()) {
				url = matcher.group(1);
			}
			url = url.replace(".webp", ".jpg");
			logger.info(url);
			return url;
		}

	/**
	 * 获取带背景的url
	 * 
	 * @param pageSource
	 * @return
	 */
	public static String getBgImageUrl(String pageSource) {
		String url = null;
		Document document = Jsoup.parse(pageSource);
		String style = document.select(".gt_cut_bg_slice").first().attr("style");
		Pattern pattern = Pattern.compile("url\\(\"(.*)\"\\)");
		Matcher matcher = pattern.matcher(style);
		if (matcher.find()) {
			url = matcher.group(1);
		}
		url = url.replace(".webp", ".jpg");
		logger.info(url);
		return url;
	}

	public static boolean cutPic(String srcFile, String outFile, int x, int y, int width, int height) {
		FileInputStream is = null;
		ImageInputStream iis = null;
		try {
			if (!new File(srcFile).exists()) {
				return false;
			}
			is = new FileInputStream(srcFile);
			String ext = srcFile.substring(srcFile.lastIndexOf(".") + 1);
			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(ext);
			ImageReader reader = it.next();
			iis = ImageIO.createImageInputStream(is);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(x, y, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			File tempOutFile = new File(outFile);
			if (!tempOutFile.exists()) {
				tempOutFile.mkdirs();
			}
			ImageIO.write(bi, ext, new File(outFile));
			return true;
		} catch (Exception e) {
			logger.error("cutPic:", e);
			return false;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (iis != null) {
					iis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
	}
	
	public static int difference(int[] a, int[] b) {
		return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]) + Math.abs(a[2] - b[2]);
	}
	
	/**
	 * 图片拼接 （注意：必须两张图片长宽一致哦）
	 * 
	 * @param files
	 *            要拼接的文件列表
	 * @param type
	 *            1横向拼接，2 纵向拼接
	 * @param targetFile
	 *            输出文件
	 */
	private static void mergeImage(String[] files, int type, String targetFile) {
		int length = files.length;
		File[] src = new File[length];
		BufferedImage[] images = new BufferedImage[length];
		int[][] ImageArrays = new int[length][];
		for (int i = 0; i < length; i++) {
			try {
				src[i] = new File(files[i]);
				images[i] = ImageIO.read(src[i]);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			int width = images[i].getWidth();
			int height = images[i].getHeight();
			ImageArrays[i] = new int[width * height];
			ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
		}
		int newHeight = 0;
		int newWidth = 0;
		for (int i = 0; i < images.length; i++) {
			// 横向
			if (type == 1) {
				newHeight = newHeight > images[i].getHeight() ? newHeight : images[i].getHeight();
				newWidth += images[i].getWidth();
			} else if (type == 2) {// 纵向
				newWidth = newWidth > images[i].getWidth() ? newWidth : images[i].getWidth();
				newHeight += images[i].getHeight();
			}
		}
		if (type == 1 && newWidth < 1) {
			return;
		}
		if (type == 2 && newHeight < 1) {
			return;
		}
		// 生成新图片
		try {
			BufferedImage ImageNew = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			int height_i = 0;
			int width_i = 0;
			for (int i = 0; i < images.length; i++) {
				if (type == 1) {
					ImageNew.setRGB(width_i, 0, images[i].getWidth(), newHeight, ImageArrays[i], 0, images[i].getWidth());
					width_i += images[i].getWidth();
				} else if (type == 2) {
					ImageNew.setRGB(0, height_i, newWidth, images[i].getHeight(), ImageArrays[i], 0, newWidth);
					height_i += images[i].getHeight();
				}
			}
			// 输出想要的图片
			ImageIO.write(ImageNew, targetFile.split("\\.")[1], new File(targetFile));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	


	public static void printLocation(WebElement element) {
		Point point = element.getLocation();
		logger.info("我的位置--------------: " +point.getX() + " "+point.getY());
	}
	
	/**
	 * 获取move数组
	 * 
	 * @param driver
	 */
	public static void initMoveArray(WebDriver driver) {
		if (moveArrayInit) {
			return;
		}
		Document document = Jsoup.parse(driver.getPageSource());
		Elements elements = document.select("[class=gt_cut_bg gt_show]").first().children();
		int i = 0;
		for (Element element : elements) {
			Pattern pattern = Pattern.compile(".*background-position: (.*?)px (.*?)px.*");
			Matcher matcher = pattern.matcher(element.toString());
			if (matcher.find()) {
				String width = matcher.group(1);
				String height = matcher.group(2);
				moveArray[i][0] = Integer.parseInt(width);
				moveArray[i++][1] = Integer.parseInt(height);
			} else {
				throw new RuntimeException("解析异常");
			}
		}
		moveArrayInit = true;
	}

	/**
	 * 还原图片
	 * 
	 * @param type
	 */
	public static void restoreImage(String type, String currentTimeStamp) throws IOException {
		// 把图片裁剪为2 * 26份
		for (int i = 0; i < 52; i++) {
			cutPic(basePath + type + currentTimeStamp+ ".jpg", basePath + "result/" + type + currentTimeStamp + i + ".jpg", -moveArray[i][0], -moveArray[i][1], 10, 58);
		}
		// 拼接图片
		String[] b = new String[26];
		for (int i = 0; i < 26; i++) {
			b[i] = String.format(basePath + "result/" + type + currentTimeStamp + "%d.jpg", i);
		}
		mergeImage(b, 1, basePath + "result/" + type + currentTimeStamp+ "result1.jpg");
		// 拼接图片
		String[] c = new String[26];
		for (int i = 0; i < 26; i++) {
			c[i] = String.format(basePath + "result/" + type + currentTimeStamp + "%d.jpg", i + 26);
		}
		mergeImage(c, 1, basePath + "result/" + type + currentTimeStamp + "result2.jpg");
		mergeImage(new String[] { basePath + "result/" + type + currentTimeStamp + "result1.jpg", basePath + "result/" + type + currentTimeStamp + "result2.jpg" }, 2,
				basePath + "result/" + type + currentTimeStamp + "result3.jpg");
		// 删除产生的中间图片
		for (int i = 0; i < 52; i++) {
			new File(basePath + "result/" + type + currentTimeStamp + i + ".jpg").deleteOnExit();
		}
		new File(basePath + "result/" + type + currentTimeStamp + "result1.jpg").deleteOnExit();
		new File(basePath + "result/" + type + currentTimeStamp + "result2.jpg").deleteOnExit();
	}

}
