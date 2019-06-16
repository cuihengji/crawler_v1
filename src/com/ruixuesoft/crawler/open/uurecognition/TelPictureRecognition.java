/**
 * Title: TelPictureRecognition.java
 * Package test
 * Description: TODO
 * 
 * @author Administrator
 * @date 2017年9月1日 上午9:54:56
 * @version V1.0
 */
package com.ruixuesoft.crawler.open.uurecognition;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.openqa.selenium.WebDriver;

import com.rkylin.crawler.engine.flood.util.FileUtil;

public class TelPictureRecognition {
    
    private static final String  FILE_PATH = FileUtil.getImageFolderPath();
    
    /**
     *  识别
      * @param webDriver
      * @param imageXpath
      * @param imageUrl
      * @return
      * @throws Exception
     */
    public String  recognise(WebDriver webDriver ,String imageUrl) throws Exception {
        
        // download验证码图片的路径
        StringBuffer filePathBuf = new StringBuffer ( FILE_PATH );
        String curTimeStr = new SimpleDateFormat ( "yyyyMMddHHmmss" ).format ( new Date () );
        filePathBuf.append ( "img" ).append ( curTimeStr ).append ( ".jpg" );
        String fileName = filePathBuf.toString ();
        
        byte[] btImg = getImageFromNetByUrl ( imageUrl );
        
        if(null != btImg && btImg.length > 0){  
            System.out.println("读取到：" + btImg.length + " 字节");  
            writeImageToDisk(btImg, fileName);  
        }else{  
            System.out.println("没有从该连接获得内容");  
        }  
        
        String code = UUAPI.getSecurityCode ( fileName,8002 );
        
        return code;
    }
    
//    /**
//     *  识别
//      * @param webDriver
//      * @param imageXpath
//      * @param imageUrl
//      * @return
//      * @throws Exception
//     */
//    public String  recogniseOnLinux(WebDriver webDriver ,String imageUrl) throws Exception {
//        
//        // download验证码图片的路径
//        StringBuffer filePathBuf = new StringBuffer ( FILE_PATH );
//        String curTimeStr = new SimpleDateFormat ( "yyyyMMddHHmmss" ).format ( new Date () );
//        filePathBuf.append ( "img" ).append ( curTimeStr ).append ( ".jpg" );
//        String fileName = filePathBuf.toString ();
//        
//        byte[] btImg = getImageFromNetByUrl ( imageUrl );
//        
//        if(null != btImg && btImg.length > 0){  
//            System.out.println("读取到：" + btImg.length + " 字节");  
//            writeImageToDisk(btImg, fileName);  
//        }else{  
//            System.out.println("没有从该连接获得内容");  
//        }  
//        
//        String code = UUAPIOnLiniux.getRecogniseResult(fileName);
//        
//        return code;
//    }
    
    /** 
     * 将图片写入到磁盘 
     * @param img 图片数据流 
     * @param fileName 文件保存时的名称 
     */  
    private  void writeImageToDisk(byte[] img, String fileName){  
        try {  
            File file = new File( fileName);  
            FileOutputStream fops = new FileOutputStream(file);  
            fops.write(img);  
            fops.flush();  
            fops.close();  
            System.out.println("图片已经写入到C盘");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    /** 
     * 根据地址获得数据的字节流 
     * @param strUrl 网络连接地址 
     * @return 
     */  
    private  byte[] getImageFromNetByUrl(String strUrl){
        
    	if(strUrl.contains("base64")){
    		
    		String[] splitedRawCssData = strUrl.split(",");
    		//data:image/png;base64,
    		String metaDataPart = splitedRawCssData[0];
    		String imagePart = splitedRawCssData[1];
    		String fileformat = metaDataPart.replace("data:image/", "").replace(";base64", "");
    		
    		final Base64.Decoder decoder = Base64.getDecoder();
    		byte[] imageBytes = decoder.decode(imagePart);
    		
    		return imageBytes;
    	}
    	
    	try {  
            URL url = new URL(strUrl);  
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
            conn.setRequestMethod("GET");  
            conn.setConnectTimeout(5 * 1000);  
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据  
            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据  
            return btImg;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    /** 
     * 从输入流中获取数据 
     * @param inStream 输入流 
     * @return 
     * @throws Exception 
     */  
    private  byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1 ){  
            outStream.write(buffer, 0, len);  
        }  
        inStream.close();  
        return outStream.toByteArray();  
    }  
}
