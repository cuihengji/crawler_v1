package com.youtu;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;

import com.rkylin.crawler.engine.flood.util.FileUtil;
import com.ruixuesoft.crawler.open.Website;

public class TencentRecognition {

    private static Map<BufferedImage, String> trainMap = null;  

	// appid, secretid secretkey请到http://open.youtu.qq.com/获取
	// 请正确填写把下面的APP_ID、SECRET_ID和SECRET_KEY
	public static final String APP_ID = "10097839";
	public static final String SECRET_ID = "AKIDYAd0BEisPjrmVKGcfrshMp6Dd1ElAQUL";
	public static final String SECRET_KEY = "nOn486wb4MSL3aTDxVr1zvjLpBdzA9vY";
	public static final String USER_ID = "";

	private static final Logger logger = Logger.getLogger(TencentRecognition.class);

	public static String imageRecognize(WebDriver webDriver, String imageXpath, int imgXOffset, int imgYOffset) throws Exception {

		String imageString = "NA";

		try {
			// download验证码图片的路径
			StringBuffer filePathBuf = new StringBuffer(FileUtil.getImageFolderPath());
			String currentTimeString = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			filePathBuf.append("img").append(currentTimeString).append(".png");
			String imageFileName = filePathBuf.toString();

			WebElement webElement = getElementByXpath(webDriver, imageXpath, true);

			// 保存验证码图
			savePicture(webDriver, webElement, imageFileName);

			// https://api.youtu.qq.com/youtu/ocrapi/generalocr
			Youtu faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, Youtu.API_YOUTU_END_POINT, USER_ID);
			// get respose
			JSONObject respose = faceYoutu.GeneralOcr(imageFileName);
			
			logger.info(respose);
			if (respose.getString("errormsg").equalsIgnoreCase("OK")) {
				imageString = respose.getJSONArray("items").getJSONObject(0).getString("itemstring");
			} else {
				imageString = imageString + " : " + respose.getString("errormsg");
			}

		} catch (Exception e) {
			logger.error("imageRecognize :", e);
			throw e;
		}

		return imageString;
	}
	
	
	public static String phoneImageRecognize(WebDriver webDriver, String imageXpath, int imgXOffset, int imgYOffset) throws Exception {

		String imageString = "NA";

		try {
			// download验证码图片的路径
			StringBuffer filePathBuf = new StringBuffer(FileUtil.getImageFolderPath());
			String currentTimeString = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			filePathBuf.append("img").append(currentTimeString).append(".jpg");
			String imageFileName = filePathBuf.toString();

			WebElement webElement = getElementByXpath(webDriver, imageXpath, true);

			// 保存验证码图
			saveAtoboPhonePicture(webDriver, webElement, imageFileName);
//			BufferedImage cvImage = getOpenCVImage(imageFileName);
//			
//			saveOpenCVImage(imageFileName, cvImage);
			
			// https://api.youtu.qq.com/youtu/ocrapi/generalocr
			Youtu faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, Youtu.API_YOUTU_END_POINT, USER_ID);
			// get respose
			JSONObject respose = faceYoutu.GeneralOcr(imageFileName);
			
			logger.info(respose);
			if (respose.getString("errormsg").equalsIgnoreCase("OK")) {
				imageString = respose.getJSONArray("items").getJSONObject(0).getString("itemstring");
				imageString = imageString.replaceAll("T", "7").replace("B", "8").replaceAll(" ", "");
				
			} else {
				imageString = imageString + " : " + respose.getString("errormsg");
			}

		} catch (Exception e) {
			logger.error("phoneImageRecognize :", e);
			throw e;
		}

		return imageString;
	}

	public static String phoneImageRecognize(WebDriver webDriver, String imageXpath, Website website) throws Exception {

		String imageString = "NA";

		try {
			// download验证码图片的路径
			StringBuffer filePathBuf = new StringBuffer(FileUtil.getImageFolderPath());
			String currentTimeString = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			filePathBuf.append("img").append(currentTimeString).append(".png");
			String imageFileName = filePathBuf.toString();

			WebElement webElement = getElementByXpath(webDriver, imageXpath, true);

			// 保存验证码图
			savePhonePicture(webDriver, webElement, imageFileName);

			loadTrainData();

			imageString = getAllOcr(imageFileName);

		} catch (Exception e) {
			logger.error("phoneImageRecognize :", e);
			throw e;
		}

		return imageString;
	}
	
	public static Map<BufferedImage, String> loadTrainData() throws Exception {
		if (trainMap == null) {
			Map<BufferedImage, String> map = new HashMap<BufferedImage, String>();
			
			String imageFolder = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "sqimage";
			imageFolder = URLDecoder.decode(imageFolder,"UTF-8");
			
			logger.info("imageFolder : " + imageFolder);
			File dir = new File(imageFolder);
			
			File[] files = dir.listFiles();
			for (File file : files) {
				map.put(ImageIO.read(file), file.getName().charAt(0) + "");
			}
			trainMap = map;
		}
		return trainMap;
	}
	
	public static String getAllOcr(String file) throws Exception {

		BufferedImage img = ImageIO.read(new File(file));
		List<BufferedImage> listImg = splitImage(img);
		Map<BufferedImage, String> map = loadTrainData();
		String result = "";
		for (BufferedImage bi : listImg) {
			result += getSingleCharOcr(bi, map);
		}
		//ImageIO.write(img, "JPG", new File("c:///www/" + result + ".jpg"));
		return result;
	}
	
	public static List<BufferedImage> splitImage(BufferedImage img) throws Exception {  
        List<BufferedImage> subImgs = new ArrayList<BufferedImage>();  
        
        for (int i = 0; i < 11; i++) {
			
			if (i == 0) {
				subImgs.add(img.getSubimage(2, 0, 37, 80));
			} else {
				subImgs.add(img.getSubimage(i*35, 0, 37, 80));
			}
		}
        return subImgs;  
    }
	
	public static String getSingleCharOcr(BufferedImage img, Map<BufferedImage, String> map) {
		
		String result = "";
		
		int width = img.getWidth();
		int height = img.getHeight();
		int min = width * height;
		for (BufferedImage bi : map.keySet()) {
			int count = 0;
			int widthmin = width < bi.getWidth() ? width : bi.getWidth();
			int heightmin = height < bi.getHeight() ? height : bi.getHeight();
			Label1: for (int x = 0; x < widthmin; ++x) {
				for (int y = 0; y < heightmin; ++y) {
					if (isWhite(img.getRGB(x, y)) != isWhite(bi.getRGB(x, y))) {
						count++;
						if (count >= min)
							break Label1;
					}
				}
			}
			if (count < min) {
				min = count;
				result = map.get(bi);
			}
		}
		return result;
	} 
	 
	public static int isWhite(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() > 100) {
			return 1;
		}
		return 0;
	}
	 
//	private static BufferedImage getOpenCVImage(String imageFileName) {
//
//		//System.out.println("---------------------------------------" +System.getProperty("java.library.path")); 
//
//		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//		Mat originalImage = Imgcodecs.imread(imageFileName);
//
//		Mat converted = new Mat(200, 200, CvType.CV_8UC1, new Scalar(0));
//		Imgproc.cvtColor(originalImage, converted, Imgproc.COLOR_RGB2GRAY, 1);
//		
//		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
//        Imgproc.findContours(converted, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_NONE);
//        
//        MatOfPoint2f approxCurve = new MatOfPoint2f();
//
//        //For each contour found
//        for (int i=0; i<contours.size(); i++)
//        {
//            //Convert contours(i) from MatOfPoint to MatOfPoint2f
//            MatOfPoint2f contour2f = new MatOfPoint2f( contours.get(i).toArray() );
//            //Processing on mMOP2f1 which is in type MatOfPoint2f
//            double approxDistance = Imgproc.arcLength(contour2f, true)*0.01;
//            Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);
//
//            //Convert back to MatOfPoint
//            MatOfPoint points = new MatOfPoint( approxCurve.toArray() );
//
//            // Get bounding rect of contour
//            Rect rect = Imgproc.boundingRect(points);
//             // draw enclosing rectangle (all same color, but you could use variable i to make them unique)
//            Imgproc.rectangle(converted, new org.opencv.core.Point(rect.x,rect.y), new org.opencv.core.Point(rect.x+rect.width,rect.y+rect.height), new Scalar(255, 0, 0, 255), 3); 
//
//        }
//           
//        BufferedImage convertedImage = toBufferedImage( converted );
//		
//		return convertedImage;
//	}
//
//	  public static BufferedImage toBufferedImage(Mat m) {
//	        int type = BufferedImage.TYPE_BYTE_GRAY;
//	        if ( m.channels() > 1 ) {
//	            type = BufferedImage.TYPE_3BYTE_BGR;
//	        }
//	        int bufferSize = m.channels()*m.cols()*m.rows();
//	        byte [] b = new byte[bufferSize];
//	        m.get(0,0,b); // get all the pixels
//	        BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
//	        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
//	        System.arraycopy(b, 0, targetPixels, 0, b.length);
//	        return image;
//	    }

	/**
	 * 将验证码图片保存
	 * 
	 * @param driver
	 * @param webElement
	 * @param fileName
	 * @throws IOException
	 */
	private static void savePicture(WebDriver driver, WebElement webElement, String fileName) throws IOException {
		try {
			BufferedImage inputbig = createElementImage(driver, webElement);
			File file = new File(fileName);
			String filePath = file.getParent();
			File fileParent = new File(filePath);
			if (!fileParent.exists()) {
				fileParent.mkdirs();
			}
			ImageIO.write(inputbig, "png", file);
		} catch (IOException e) {
			throw e;
		}
	}
	
	private static void saveAtoboPhonePicture(WebDriver driver, WebElement webElement, String fileName) throws IOException {
		try {
			BufferedImage inputbig = createAtoboElementImage(driver, webElement);
			File file = new File(fileName);
			String filePath = file.getParent();
			File fileParent = new File(filePath);
			if (!fileParent.exists()) {
				fileParent.mkdirs();
			}
			ImageIO.write(inputbig, "jpg", file);
		} catch (IOException e) {
			throw e;
		}
	}
	
	private static void savePhonePicture(WebDriver driver, WebElement webElement, String fileName) throws IOException {
		try {
			BufferedImage inputbig = createPhoneElementImage(driver, webElement);
			File file = new File(fileName);
			String filePath = file.getParent();
			File fileParent = new File(filePath);
			if (!fileParent.exists()) {
				fileParent.mkdirs();
			}
			ImageIO.write(inputbig, "png", file);
		} catch (IOException e) {
			throw e;
		}
	}
	
	private static void saveOpenCVImage(String fileName, BufferedImage bufferedImage) throws IOException {
		try {
			File file = new File(fileName);
			String filePath = file.getParent();
			File fileParent = new File(filePath);
			if (!fileParent.exists()) {
				fileParent.mkdirs();
			}
			ImageIO.write(bufferedImage, "png", file);
		} catch (IOException e) {
			throw e;
		}
	}

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

	/**
	 * 使用WebDriver对页面截图
	 * 
	 * @param driver
	 * @return
	 * @throws IOException
	 */
	private static byte[] takeScreenshot(WebElement webElement) throws IOException {
		
		WrapsDriver wrapsDriver = (WrapsDriver) webElement;
		// 截图整个页面
		return ((TakesScreenshot) wrapsDriver.getWrappedDriver()).getScreenshotAs(OutputType.BYTES);

	}

	/**
	 * 生成验证码图
	 * 
	 * @param driver
	 * @param webElement
	 * @return
	 * @throws IOException
	 */
	private static BufferedImage createElementImage(WebDriver driver, WebElement webElement) throws IOException {

		// 获得webElement的位置和大小
		Point elementLocation = webElement.getLocation();
		logger.info("originalImage.getWidth(): " + driver.manage().window().getSize().width);
		logger.info("originalImage.getHeight(): " + driver.manage().window().getSize().height);

		logger.info("getX: " + elementLocation.getX());
		logger.info("getY: " + elementLocation.getY());
		logger.info("webElement.getSize().getWidth(): " + webElement.getSize().getWidth());
		logger.info("webElement.getSize().getHeight(): " + webElement.getSize().getHeight());

        //driver.manage().window().maximize();  

		int scrollDistance = elementLocation.getY() - driver.manage().window().getSize().height / 2;

		try {
			String javaScripit = "window.scrollTo(0, " + scrollDistance + ")";
			((JavascriptExecutor) driver).executeScript(javaScripit);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		// 创建全屏截图
		BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(takeScreenshot(webElement)));

		BufferedImage croppedImage = originalImage.getSubimage(
				elementLocation.getX(), 
				elementLocation.getY() - scrollDistance, 
				webElement.getSize().getWidth(),
				webElement.getSize().getHeight());
		
		BufferedImage x2Image = new BufferedImage(croppedImage.getWidth()*2, croppedImage.getHeight()*2, BufferedImage.TYPE_INT_RGB);   
		x2Image.getGraphics().drawImage(croppedImage, 0, 0, croppedImage.getWidth()*2, croppedImage.getHeight()*2, null);   
		
		float[] data = { 
		        -1.0f, -1.0f, -1.0f,
		        -1.0f, 9.0f, -1.0f,
		        -1.0f, -1.0f, -1.0f 
		};
		
		Kernel kernel = new Kernel(3, 3, data);
		ConvolveOp imageOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null); // 创建卷积变换操作对象
		BufferedImage filteredBufImage = new BufferedImage(x2Image.getWidth(), x2Image.getHeight(), BufferedImage.TYPE_INT_ARGB); // 过滤后的缓冲区图像
		imageOp.filter(x2Image, filteredBufImage);// 过滤图像，目标图像在filteredBufImage
		
		return filteredBufImage;
	}
	
	
	/**
	 * 生成验证码图
	 * 
	 * @param driver
	 * @param webElement
	 * @return
	 * @throws IOException
	 */
	private static BufferedImage createAtoboElementImage(WebDriver driver, WebElement webElement) throws IOException {

		// 获得webElement的位置和大小
		Point elementLocation = webElement.getLocation();
		logger.info("originalImage.getWidth(): " + driver.manage().window().getSize().width);
		logger.info("originalImage.getHeight(): " + driver.manage().window().getSize().height);

		logger.info("getX: " + elementLocation.getX());
		logger.info("getY: " + elementLocation.getY());
		logger.info("webElement.getSize().getWidth(): " + webElement.getSize().getWidth());
		logger.info("webElement.getSize().getHeight(): " + webElement.getSize().getHeight());

        //driver.manage().window().maximize();  

		int scrollDistance = elementLocation.getY() - driver.manage().window().getSize().height / 2;

		try {
			String javaScripit = "window.scrollTo(0, " + scrollDistance + ")";
			((JavascriptExecutor) driver).executeScript(javaScripit);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		// 创建全屏截图
		BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(takeScreenshot(webElement)));

		BufferedImage croppedImage = originalImage.getSubimage(
				elementLocation.getX(), 
				elementLocation.getY() - scrollDistance, 
				webElement.getSize().getWidth(),
				webElement.getSize().getHeight());
		
		BufferedImage x2Image = new BufferedImage(croppedImage.getWidth()*2, croppedImage.getHeight()*2, BufferedImage.TYPE_INT_RGB);   
		x2Image.getGraphics().drawImage(croppedImage, 0, 0, croppedImage.getWidth()*2, croppedImage.getHeight()*2, null);   
		
		
		int h = x2Image.getHeight();  
        int w = x2Image.getWidth();  
  
        // 灰度化  
        int[][] gray = new int[w][h];  
        for (int x = 0; x < w; x++){  
            for (int y = 0; y < h; y++){  
                int argb = x2Image.getRGB(x, y);  
                // 图像加亮（调整亮度识别率非常高）  
                int r = (int) (((argb >> 16) & 0xFF) * 1.1 + 30);  
                int g = (int) (((argb >> 8) & 0xFF) * 1.1 + 30);  
                int b = (int) (((argb >> 0) & 0xFF) * 1.1 + 30);  
                if (r >= 255){  
                    r = 255;  
                }  
                if (g >= 255){  
                    g = 255;  
                }  
                if (b >= 255){  
                    b = 255;  
                }  
                gray[x][y] = (int) Math.pow((Math.pow(r, 2.2) * 0.2973 + Math.pow(g, 2.2)* 0.6274 + Math.pow(b, 2.2) * 0.0753), 1 / 2.2);  
            }  
        }  
  
        // 二值化  
        int threshold = ostu(gray, w, h);  
        BufferedImage binaryBufferedImage = new BufferedImage(w, h,BufferedImage.TYPE_BYTE_BINARY);  
        for (int x = 0; x < w; x++){  
            for (int y = 0; y < h; y++){  
                if (gray[x][y] > threshold){  
                    gray[x][y] |= 0x00FFFF;  
                } else{  
                    gray[x][y] &= 0xFF0000;  
                }  
                binaryBufferedImage.setRGB(x, y, gray[x][y]);  
            }  
        }  
		
        BlockingQueue<Boolean> blankArea = new ArrayBlockingQueue<Boolean>(40);
		int cutWidthPixel = binaryBufferedImage.getWidth();

		for (int i = binaryBufferedImage.getWidth() / 3; i < binaryBufferedImage.getWidth(); i++) {

			int[] fullRgb = new int[3];
			int blankLineCount = 0;
			
			for (int j = 0; j < binaryBufferedImage.getHeight(); j++) {

				fullRgb[0] = binaryBufferedImage.getRGB(i, j);
				fullRgb[1] = binaryBufferedImage.getRGB(i, j);
				fullRgb[2] = binaryBufferedImage.getRGB(i, j);
				 
				if (fullRgb[0] + fullRgb[1] + fullRgb[2] == -3) {
					blankLineCount++;
				}
			}
			
			if (blankLineCount == binaryBufferedImage.getHeight()) {
				blankArea.add(true);
			} else {
				blankArea.add(false);
			}

			if (blankArea.size() == 40) {
				blankArea.remove();
				int blankCount = 0;

				for (Boolean booleanValue : blankArea) {
					if (booleanValue) {
						blankCount++;
					}
				}
				//连续39条线是空白的,可以认为没有数字啦,同时要留一定的空白, 否则腾讯识别会少位数.	
				if (blankCount == 39) {
					cutWidthPixel = i;
					break;
				}
			}
		}
		
		
		BufferedImage cuttedImage = binaryBufferedImage.getSubimage(0,0,cutWidthPixel, binaryBufferedImage.getHeight());
		return cuttedImage;
	}
	
	
	/**
	 * 生成验证码图
	 * 
	 * @param driver
	 * @param webElement
	 * @return
	 * @throws IOException
	 */
	private static BufferedImage createPhoneElementImage(WebDriver driver, WebElement webElement) throws IOException {
		
		//TODO refactor later 
		// 获得webElement的位置和大小
		Point elementLocation = webElement.getLocation();
		logger.info("originalImage.getWidth(): " + driver.manage().window().getSize().width);
		logger.info("originalImage.getHeight(): " + driver.manage().window().getSize().height);

		logger.info("getX: " + elementLocation.getX());
		logger.info("getY: " + elementLocation.getY());
		logger.info("webElement.getSize().getWidth(): " + webElement.getSize().getWidth());
		logger.info("webElement.getSize().getHeight(): " + webElement.getSize().getHeight());

        //driver.manage().window().maximize();  

		int scrollDistance = elementLocation.getY() - driver.manage().window().getSize().height / 2;

		try {
			String javaScripit = "window.scrollTo(0, " + scrollDistance + ")";
			((JavascriptExecutor) driver).executeScript(javaScripit);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		// 创建全屏截图
		BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(takeScreenshot(webElement)));

		BufferedImage croppedImage = originalImage.getSubimage(
				elementLocation.getX(), 
				elementLocation.getY() - scrollDistance, 
				webElement.getSize().getWidth(),
				webElement.getSize().getHeight());
		
		BufferedImage x2Image = new BufferedImage(croppedImage.getWidth()*5, croppedImage.getHeight()*5, BufferedImage.TYPE_INT_RGB);   
		x2Image.getGraphics().drawImage(croppedImage, 0, 0, croppedImage.getWidth()*5, croppedImage.getHeight()*5, null);   
		
		
		float[] data = { 
		        -1.0f, -1.0f, -1.0f,
		        -1.0f, 9.0f, -1.0f,
		        -1.0f, -1.0f, -1.0f 
		};
		
		Kernel kernel = new Kernel(3, 3, data);
		ConvolveOp imageOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null); // 创建卷积变换操作对象
		BufferedImage filteredBufImage = new BufferedImage(x2Image.getWidth(), x2Image.getHeight(), BufferedImage.TYPE_INT_ARGB); // 过滤后的缓冲区图像
		imageOp.filter(x2Image, filteredBufImage);// 过滤图像，目标图像在filteredBufImage
		
		int h = filteredBufImage.getHeight();  
        int w = filteredBufImage.getWidth();  
  
        // 灰度化  
        int[][] gray = new int[w][h];  
        for (int x = 0; x < w; x++){  
            for (int y = 0; y < h; y++){  
                int argb = filteredBufImage.getRGB(x, y);  
                // 图像加亮（调整亮度识别率非常高）  
                int r = (int) (((argb >> 16) & 0xFF) * 1.1 + 30);  
                int g = (int) (((argb >> 8) & 0xFF) * 1.1 + 30);  
                int b = (int) (((argb >> 0) & 0xFF) * 1.1 + 30);  
                if (r >= 255){  
                    r = 255;  
                }  
                if (g >= 255){  
                    g = 255;  
                }  
                if (b >= 255){  
                    b = 255;  
                }  
                gray[x][y] = (int) Math.pow((Math.pow(r, 2.2) * 0.2973 + Math.pow(g, 2.2)* 0.6274 + Math.pow(b, 2.2) * 0.0753), 1 / 2.2);  
            }  
        }  
  
        // 二值化  
        int threshold = ostu(gray, w, h);  
        BufferedImage binaryBufferedImage = new BufferedImage(w, h,BufferedImage.TYPE_BYTE_BINARY);  
        for (int x = 0; x < w; x++){  
            for (int y = 0; y < h; y++){  
                if (gray[x][y] > threshold){  
                    gray[x][y] |= 0x00FFFF;  
                } else{  
                    gray[x][y] &= 0xFF0000;  
                }  
                binaryBufferedImage.setRGB(x, y, gray[x][y]);  
            }  
        }  
		
//        BufferedImage smallImage = new BufferedImage(binaryBufferedImage.getWidth(), binaryBufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);   
//        smallImage.getGraphics().drawImage(binaryBufferedImage, 0, 0, binaryBufferedImage.getWidth()*2, binaryBufferedImage.getHeight()*2, null);   
        
		
		return binaryBufferedImage;
	}
	
	
	 public static int ostu(int[][] gray, int w, int h){  
	        int[] histData = new int[w * h];  
	        // Calculate histogram  
	        for (int x = 0; x < w; x++){  
	            for (int y = 0; y < h; y++){  
	                int red = 0xFF & gray[x][y];  
	                histData[red]++;  
	            }  
	        }  
	  
	        // Total number of pixels  
	        int total = w * h;  
	  
	        float sum = 0;  
	        for (int t = 0; t < 256; t++)  
	            sum += t * histData[t];  
	  
	        float sumB = 0;  
	        int wB = 0;  
	        int wF = 0;  
	  
	        float varMax = 0;  
	        int threshold = 0;  
	  
	        for (int t = 0; t < 256; t++){  
	            wB += histData[t]; // Weight Background  
	            if (wB == 0)  
	                continue;  
	  
	            wF = total - wB; // Weight Foreground  
	            if (wF == 0)  
	                break;  
	  
	            sumB += (float) (t * histData[t]);  
	  
	            float mB = sumB / wB; // Mean Background  
	            float mF = (sum - sumB) / wF; // Mean Foreground  
	  
	            // Calculate Between Class Variance  
	            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);  
	  
	            // Check if new maximum found  
	            if (varBetween > varMax){  
	                varMax = varBetween;  
	                threshold = t;  
	            }  
	        }  
	  
	        return threshold;  
	    }  
	    //图片灰度，黑白  
	    public static void gray(String srcImageFile, String destImageFile) {  
	        try {  
	            BufferedImage src = ImageIO.read(new File(srcImageFile));  
	            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);  
	            ColorConvertOp op = new ColorConvertOp(cs, null);  
	            src = op.filter(src, null);  
	            ImageIO.write(src, "JPEG", new File(destImageFile));  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	      

}
