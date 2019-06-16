package com.web2data.engine.crawler.browser.impl.a;

import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.Rop.api.ApiException;
import com.Rop.api.DefaultRopClient;
import com.Rop.api.request.PsPictureStreamUploadRequest;
import com.Rop.api.response.PsPictureStreamUploadResponse;
import com.rkylin.crawler.engine.flood.util.ImageMerger;
import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.impl.DefaultRopClientFacade;
import com.ruixuesoft.crawler.open.thirdparty.VerifyCodeHelper;
import com.web2data.engine.crawler.httpclient.RxCrawlerImpl_95_HttpClient;
import com.web2data.system.app.AppFacade;
import com.web2data.utility.U;

public class RxCrawlerImpl_92_Screenshot {

	
//    // -------------------------------------------------------
//    private static final transient Logger logger = Logger.getLogger(RxCrawlerImpl_92_Screenshot.class);
//   
//	
//    public static String takeScreenshot ( int appSeq, boolean isWatermark, WebDriver webDriver, int crawlerid ) {
//		
//		//checkInterrupted();
//
////		滑动码验证的时候导致截图的尺寸变小,临时的补丁让窗口最大化.
//		webDriver.manage().window().maximize();
//		org.openqa.selenium.Dimension myDimension = webDriver.manage().window().getSize();
//		logger.info("-------------verifyGeeTest takeScreenshot Width:" + myDimension.getWidth() + "<--> Height: " + myDimension.getHeight());
//		
//		String fileName = "";
//		byte[] imgBytes = null;
//
//		byte[] screenshotBytes = null;
//		try {
//			screenshotBytes = VerifyCodeHelper.takeScreenshot( webDriver );
//		} catch (Exception e) {
//			
//			logger.error("截图发生异常，", e);
//			new RxCrawlerException(999, e.getMessage());
//		}
//
//		
//        StringBuffer filePathBuf = new StringBuffer ( );
//		String curTimeStr = new SimpleDateFormat ( "yyyyMMddHHmmssSSS" ).format ( new Date () );
//		
//		if ( isWatermark ) {
//
//	        InputStream in = new ByteArrayInputStream( screenshotBytes );
//	        imgBytes = markImageByIcon ( in , "Arial", 30, Color.RED );
//			
//	        filePathBuf.append( "app" ).append( appSeq ).append( "_"+crawlerid).append( "_mark" ).append ( "_img_" ).append ( curTimeStr ).append ( ".jpg" );
//
//		} else {
//			
//			filePathBuf.append( "app" ).append( appSeq ).append( "_"+crawlerid).append( "_img_" ).append ( curTimeStr ).append ( ".jpg" );
//			imgBytes = screenshotBytes;
//		}
//		
//		fileName = filePathBuf.toString();
//		logger.info("takeScreenshot: " + fileName);
//		
////		// 调用php写图片文件到taskhost
////		try {
////			hostDownloadUrl = AppFacade.getInstance().uploadImageFile(appSeq, imgBytes, fileName);
////			if (hostDownloadUrl == null) {
////				throw new RuntimeException("不能到获取TaskHost Server");
////			}
////		} catch (Exception e) {
////			logger.error("截图上传发生异常，", e);
////			new RxCrawlerException(999, e.getMessage());
////		}
//		
//		return uploadToRop(fileName, imgBytes);
//
//		//		return hostDownloadUrl + "/" + fileName;
//    }
//
//
//	public static String takeFullScreenshot (boolean isWatermark, List<String> divIds, WebDriver webdriver) {
//		
//		//checkInterrupted();
//		
//		Long windowHeight = (Long) RxCrawlerImpl_70_javascript.executeJsScript("return document.documentElement.clientHeight;", webdriver);
//		logger.info("windowHeight:" + windowHeight);
//
//		Long totalScrollHeight = (Long) RxCrawlerImpl_70_javascript.executeJsScript("return document.body.scrollHeight;", webdriver);
//		logger.info("totalScrollHeight:" + totalScrollHeight);
//		int windowHeightInt = windowHeight.intValue();
//		int totalScrollHeightInt = totalScrollHeight.intValue();
//		
//		String fileName = "";
//		byte[] imgBytes = null;
//		
//		StringBuffer filePathBuf = new StringBuffer ( );
//		String curTimeStr = new SimpleDateFormat ( "yyyyMMddHHmmssSSS" ).format ( new Date () );
//
//		filePathBuf.append( "_img_" ).append ( curTimeStr ).append ( ".png" );
//	
//		fileName = filePathBuf.toString();
//		logger.info("takeFullScreenshot: " + fileName);
//		
//		int lastPageHeight = totalScrollHeightInt%windowHeightInt;
//		int finalTotalHeight = totalScrollHeightInt;
//
//		 // 读取待合并的文件 
//        BufferedImage bufferImage = null; 
//        
//        // 调用mergeImage方法获得合并后的图像 
//        BufferedImage targetImage = null; 
//        
//
//		totalScrollHeightInt = totalScrollHeightInt - windowHeightInt;
//		targetImage = getScreenBufferedImage(true, webdriver);
//		
//		//先滚动到底部,预防客户外部操作的影响
//		RxCrawlerImpl_30_scroll.scrollToTop(webdriver);
//
//
//		//先让屏幕滚动一遍,使所有的图片显示
//		int dryRunTotalScrollHeightInt	= totalScrollHeightInt;
//		int dryRunFinalTotalHeight = finalTotalHeight;
//		int dryRunScrollTimes = 1;
//		while (dryRunTotalScrollHeightInt >= 0) {
//
//			if (dryRunTotalScrollHeightInt >= windowHeightInt) {
//				RxCrawlerImpl_30_scroll.scroll(windowHeightInt * dryRunScrollTimes, webdriver);
//			}else{
//				RxCrawlerImpl_30_scroll.scroll(dryRunFinalTotalHeight, webdriver);
//
//			}
//			
//			dryRunTotalScrollHeightInt = dryRunTotalScrollHeightInt - windowHeightInt;
//			dryRunScrollTimes++;
//		}
//		
//		RxCrawlerImpl_30_scroll.scrollToTop(webdriver);
//		U.sleepSeconds(1);
//		
//		if(divIds!=null){
//			for (String id : divIds) {
//				U.sleepSeconds(1);
//				RxCrawlerImpl_70_javascript.executeJsScript("document.getElementById('" +id.trim() + "').style.display='none'; return 0;", webdriver);
//			}
//		}
//		
//		int scrollTimes = 1;
//
//		while (totalScrollHeightInt >= 0) {
//
//			if (totalScrollHeightInt >= windowHeightInt) {
//				RxCrawlerImpl_30_scroll.scroll(windowHeightInt * scrollTimes, webdriver);
//			}else{
//				RxCrawlerImpl_30_scroll.scroll(finalTotalHeight, webdriver);
//
//			}
//			U.sleepSeconds(1);
//			bufferImage = getScreenBufferedImage(true, webdriver);
//
//			// 调用mergeImage方法获得合并后的图像
//			try {
//
//				if (totalScrollHeightInt >= windowHeightInt) {
//					targetImage = ImageMerger.mergeImage(targetImage, bufferImage, false, windowHeightInt);					
//				}
//				else
//				{
//					targetImage = ImageMerger.mergeImage(targetImage, bufferImage, false, lastPageHeight);
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//			logger.info("scrollTimes:  " + scrollTimes);
//			logger.info("totalScrollHeightInt:  " + totalScrollHeightInt);
//			
//			totalScrollHeightInt = totalScrollHeightInt - windowHeightInt;
//			scrollTimes++;
//		}
//
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		try {
//			boolean flag = ImageIO.write(targetImage, "png", out);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		imgBytes = out.toByteArray();
//
//		return uploadToRop(fileName, imgBytes);
//		
//
//	}
//	
//    private static BufferedImage getScreenBufferedImage (boolean isWatermark, WebDriver webDriver ) {
//		
//		//checkInterrupted();
//
//		byte[] imgBytes = null;
//
//		byte[] screenshotBytes = null;
//		try {
//			screenshotBytes = VerifyCodeHelper.takeScreenshot( webDriver );
//		} catch (Exception e) {
//			
//			logger.error("截图发生异常，", e);
//			new RxCrawlerException(999, e.getMessage());
//		}
//		
//		if ( isWatermark ) {
//	        InputStream in = new ByteArrayInputStream( screenshotBytes );
//	        imgBytes = markImageByIcon ( in , "Arial", 30, Color.RED );
//		} else {
//			imgBytes = screenshotBytes;
//		}
//		
//		BufferedImage screenshotImage = null;
//		try {
//			screenshotImage = ImageIO.read(new ByteArrayInputStream( imgBytes ) );
//		} catch (Exception e) {
//			logger.error("截图读入发生异常，", e);
//			new RxCrawlerException(999, e.getMessage());
//		}
//		
//		return screenshotImage;
//    }
//
//	
//	private static String uploadToRop(String fileName, byte[] imgBytes) {
//		//新需求存储文件到ROP平台
//		DefaultRopClient ropClient = DefaultRopClientFacade.getInstance();
//		PsPictureStreamUploadRequest request = new PsPictureStreamUploadRequest();
//		request.setExtension("jpg");
//		request.setPicturefilename(fileName);
//		request.setPicturestream(compressPicture(imgBytes));
//		
//		String fileurl = "NA";
//		try {
//			PsPictureStreamUploadResponse response = ropClient.execute(request);
//			
//			String strError = null;
//			if (response != null) {
//			
//				fileurl = response.getFileurl();
//				logger.info("takeScreenshot fileUrl: " + fileurl);
//				if (response.isSuccess() != true) {
//					if (response.getSubMsg() != null && response.getSubMsg() != "") {
//						strError = response.getSubMsg();
//					} else {
//						strError = response.getMsg();
//					}
//				}
//				if (strError != null) {
//					throw new RxCrawlerException(999,"调用ROP平台发生错误: " + strError);
//				}
//			}
//		} catch (ApiException e) {
//			throw new RxCrawlerException(999,"调用ROP平台发生错误");
//		}
//		return fileurl;
//	}
//	
//
//	public static String takeScreenshotByJava ( int appSeq, boolean isWatermark ) {
//		
//		//checkInterrupted();
//
//		String fileName = "";
//		byte[] imgBytes = null;
//
//		byte[] screenshotBytes = null;
//
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        Rectangle screenRectangle = new Rectangle(screenSize);
//
//		try {
//			
//	        Robot robot = new Robot();
//	        ByteArrayOutputStream out = new ByteArrayOutputStream();
//	        
//	        BufferedImage image = robot.createScreenCapture(screenRectangle);
//			ImageIO.write(image,"jpg", out);
//			
//	        screenshotBytes = out.toByteArray();
//	        
//		} catch (IOException e) {
//			
//			logger.error("截图发生异常，", e);
//			new RxCrawlerException(999, "截图发生异常，" + e.getMessage());
//			
//		} catch (AWTException e) {
//			
//			logger.error("截图发生异常，", e);
//			new RxCrawlerException(999, "截图发生异常，" +  e.getMessage());
//			
//		}
//
//		
//        StringBuffer filePathBuf = new StringBuffer ( );
//		String curTimeStr = new SimpleDateFormat ( "yyyyMMddHHmmssSSS" ).format ( new Date () );
//		
//		if ( isWatermark ) {
//
//	        InputStream in = new ByteArrayInputStream( screenshotBytes );
//	        imgBytes = markImageByIcon ( in , "Arial", 30, Color.RED );
//			
//	        filePathBuf.append( "app" ).append( appSeq ).append( "_mark" ).append ( "_img_" ).append ( curTimeStr ).append ( ".jpg" );
//
//		} else {
//			
//			filePathBuf.append( "app" ).append( appSeq ).append( "_img_" ).append ( curTimeStr ).append ( ".jpg" );
//			imgBytes = screenshotBytes;
//		}
//		
//		fileName = filePathBuf.toString();
//		
//		// 调用php写图片文件到taskhost
//		try {
//			AppFacade.getInstance().uploadImageFile( appSeq, imgBytes, fileName);
//		} catch (Exception e) {
//			logger.error("截图上传发生异常，", e);
//			new RxCrawlerException(999, e.getMessage());
//		}
//		
//		return fileName;
//		
//	}
//	
//
//	public static String getImageByUrl(int appSeq, String url, int crawlerid) {
//		//checkInterrupted();
//
//		String fileName = "";
//		byte[] imgBytes = null;
//
//        StringBuffer filePathBuf = new StringBuffer ( );
//		String curTimeStr = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
//
//		filePathBuf.append("app").append(appSeq).append("_" + crawlerid).append("_img_").append(curTimeStr);
//		imgBytes = getImageData(url);
//		
//		if (imgBytes == null) {
//			throw new RxCrawlerException(999, "不能得到指定URL的图片信息");
//		}
//		
//		fileName = filePathBuf.toString();
//		
////		// 调用php写图片文件到taskhost
////		try {
////			hostDownloadUrl = AppFacade.getInstance().uploadImageFile(appSeq, imgBytes, fileName);
////			if (hostDownloadUrl == null) {
////				throw new RxCrawlerException(999, "不能到获取TaskHost Server");
////			}
////		} catch (Exception e) {
////			logger.error("截图上传发生异常，", e);
////			new RxCrawlerException(999, e.getMessage());
////
////		}
//		
//		return uploadToRop(fileName, imgBytes);
//	}
//	
//	
//	
//	public static ByteArrayInputStream compressPicture(byte[] imgBytes){
//        
//        BufferedImage src = null;
//        ByteArrayOutputStream out = null;
//        ImageWriteParam imgWriteParams;
// 
//        // 指定写图片的方式为 jpg
//        ImageWriter imgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
//        
//        imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);
//        // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
//        imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//        // 这里指定压缩的程度，参数qality是取值0~1范围内，
//        imgWriteParams.setCompressionQuality((float) 0.5);
//        imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
//        
//        ColorModel colorModel = ColorModel.getRGBdefault();
//        // 指定压缩时使用的色彩模式
//        imgWriteParams.setDestinationType(new ImageTypeSpecifier(colorModel, colorModel.createCompatibleSampleModel(16, 16)));
// 
//        try {
//        	//将imgBytes作为输入流；
//        	ByteArrayInputStream inputImageStream = new ByteArrayInputStream(imgBytes);    
//        	//将InputStream作为输入流，读取图片存入image中
//        	src = ImageIO.read(inputImageStream);     
//        	
//			out = new ByteArrayOutputStream();
//			imgWriter.reset();
//			// 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
//			// OutputStream构造
//			imgWriter.setOutput(ImageIO.createImageOutputStream(out));
//			// 调用write方法，就可以向输入流写图片
//			imgWriter.write(null, new IIOImage(src, null, null), imgWriteParams);
//
//			out.flush();
//			out.close();
//            
//        } catch (Exception e) {
//        	throw new RxCrawlerException(999,"压缩图片出现错误:");
//        }
//        return new ByteArrayInputStream(out.toByteArray());
//    }
//	
//	
//    public static String logScreen( int appSeq, WebDriver webDriver ) {
//		
//		//checkInterrupted();
//		String hostDownloadUrl = null;
//
//		String fileName = "";
//		byte[] imgBytes = null;
//
//		byte[] screenshotBytes = null;
//		try {
//			screenshotBytes = VerifyCodeHelper.takeScreenshot( webDriver );
//		} catch (Exception e) {
//			
//			logger.error("截图发生异常，", e);
//			new RxCrawlerException(999, e.getMessage());
//		}
//
//		StringBuffer filePathBuf = new StringBuffer();
//		String curTimeStr = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
//
//		filePathBuf.append("img_").append(curTimeStr).append(".jpg");
//		imgBytes = screenshotBytes;
//		
//		
//		fileName = filePathBuf.toString();
//		
//		// 调用php写图片文件到taskhost
//		try {
//			hostDownloadUrl = AppFacade.getInstance().uploadImageFile(appSeq, imgBytes, fileName);
//			if (hostDownloadUrl == null) {
//				throw new RuntimeException("不能到获取TaskHost Server");
//			}
//		} catch (Exception e) {
//			logger.error("截图上传发生异常，", e);
//			throw new RxCrawlerException(999, e.getMessage());
//		}
//		
//		int downloadIndex = hostDownloadUrl.indexOf("download_images");
//		String pureHostUrl = hostDownloadUrl.substring(0, downloadIndex);
//
//		//http://taskhost/App713_Download_App_LogScreen.php?userUuid={userUuid}&app=111&imageName=20180227101021321.png
//		return pureHostUrl + "app/APP713_Download_App_LogScreen.php?userUuid={userUuid}&appSeq=" + appSeq + "&imageFileName="+fileName;
//    }
//
////	private String [] writeScreenshotImageToLocal( int appSeq, BufferedImage subScreenshotImage ) throws IOException {
////		
////		String [] fileNameArr = new String [2];
////		
////        StringBuffer filePathBuf = new StringBuffer ( FILE_PATH );
////        String curTimeStr = new SimpleDateFormat ( "yyyyMMddHHmmssSSS" ).format ( new Date () );
////        filePathBuf.append( "app" ).append( appSeq ).append ( "_img_" ).append ( curTimeStr ).append ( ".jpg" );
////        String fileName = filePathBuf.toString ();
////        fileNameArr[0] = fileName;
////        
////        File file = new File ( fileName );
////        String filePath = file.getParent ();
////        File fileParent = new File ( filePath );
////        if ( ! fileParent.exists () ) {
////            fileParent.mkdirs ();
////        }
////        
////		ImageIO.write ( subScreenshotImage , "jpg" , file );
////        
////        fileNameArr[1] = file.getName();
////        
////        return fileNameArr;
////
////	}
//	
//	private static byte [] markImageByIcon ( InputStream srcImgInputStream, String fontType, int fontSize, Color fontColor ) {
//      
//		//checkInterrupted();
//
//		ByteArrayOutputStream os = null;
//       
//		try {
//			
//            Image srcImg = ImageIO.read(srcImgInputStream);
//            
//            int srcImgWidth = srcImg.getWidth ( null );
//            int srcImgHeight = srcImg.getHeight ( null );
//
//            BufferedImage buffImg = new BufferedImage ( srcImgWidth , srcImgHeight , BufferedImage.TYPE_INT_RGB );
//            // 得到画笔对象
//            Graphics2D g = buffImg.createGraphics ();
//
//            // 设置对线段的锯齿状边缘处理
//            g.setRenderingHint ( RenderingHints.KEY_INTERPOLATION , RenderingHints.VALUE_INTERPOLATION_BILINEAR );
//
//            g.drawImage ( srcImg.getScaledInstance ( srcImg.getWidth ( null ) , srcImg.getHeight ( null ) , Image.SCALE_SMOOTH ) , 0 , 0 , null );
//
//            float alpha = 1f; // 透明度
//            g.setComposite ( AlphaComposite.getInstance ( AlphaComposite.SRC_ATOP , alpha ) );
//
//            g.setColor ( fontColor);
//
//            Font font = new Font ( fontType , Font.PLAIN , fontSize );
//            g.setFont ( font );
//
//            // 表示水印图片的位置
//            SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss" );
//            java.util.Date date = new java.util.Date ();
//
//            g.drawString ( sdf.format ( date ) , (int)Math.floor( srcImgWidth * 0.8 ), (int)Math.floor( srcImgHeight * 0.8 ) );
//
//            g.setComposite ( AlphaComposite.getInstance ( AlphaComposite.SRC_OVER ) );
//
//            g.dispose ();
//
//            os = new ByteArrayOutputStream();
//            
//            // 生成图片
//            ImageIO.write ( buffImg , "JPG" , os );
//            
//        } catch ( Exception e ) {
//        	
//            e.printStackTrace ();
//            
//        } finally {
//        	
//            try {
//                if ( null != os ) os.close ();
//            } catch ( Exception e ) {
//                e.printStackTrace ();
//            }
//            
//        }
//        
//        return os.toByteArray();
//    }
//	
//	
////	private byte[] getImageData(String src) {
////		
////		byte[] data = null;
////
////		for (int i = 0; i < 5; i++) {
////
////			try {
////				URL url = new URL(src);
////				URLConnection urlConn = url.openConnection();
////				urlConn.setConnectTimeout(600 * 1000);
////				InputStream is = urlConn.getInputStream();
////				data = streamToByte(is);
////			} catch (IOException e) {
////				logger.error("不能得到指定URL的图片信息");
////			}
////			
////			if ((data != null) && (data.length >= 0)) {
////				break;
////			}
////		}
////
////		return data;
////	}
//	
//	private static byte[] getImageData(String src) {
//		
//		byte[] data = null;
//
//		for (int i = 0; i < 5; i++) {
//			CloseableHttpResponse response = null;
//			try {
//				CloseableHttpClient httpclient = RxCrawlerImpl_95_HttpClient.getHttpClient();
//				HttpGet get = new HttpGet(src);
//				response = httpclient.execute(get);
//				HttpEntity entity = response.getEntity();
//				InputStream is = entity.getContent();
//				data = streamToByte(is);
//
//			} catch (IOException e) {
//				logger.error("不能得到指定URL的图片信息:" + e.getMessage());
//			} finally {
//				if (response != null) {
//					try {
//						response.close();
//					} catch (IOException e) {
//						logger.error("关闭CloseableHttpResponse出现异常:" + e.getMessage());
//					}
//				}
//			}
//
//			if ((data != null) && (data.length >= 0)) {
//				break;
//			}
//		}
//
//		return data;
//	}
//	
//
//	private static byte[] streamToByte(InputStream is) throws IOException {
//		ByteArrayOutputStream stream = new ByteArrayOutputStream();
//		int ch;
//		while ((ch = is.read()) != -1) {
//			stream.write(ch);
//		}
//		byte imgData[] = stream.toByteArray();
//		stream.close();
//
//		return imgData;
//	}
	
	
}
