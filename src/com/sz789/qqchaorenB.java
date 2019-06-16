package com.sz789;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

import org.apache.log4j.Logger;

public class qqchaorenB {
    
    private static final transient Logger log = Logger.getLogger ( qqchaorenB.class );
    
	static
	{
	    try {
//	        log.error ( System.getProperty ("java.library.path"));
	        System.loadLibrary("BJDC");
	    }catch(Throwable e) {
            log.error ( e,e );
            throw e;
        }
	}
	public native static void BJInit();
	//初始化插件,打开端口,如使用多线程,只需在主线程中初始化一次即可
	public native static String BJRecYZM_A(String strYZMPath,String strVcodeUser,String strVcodePass,String strsoftkey);
	//按本地图片路径识别
	public native static String BJRecByte_A(byte[] img,int len,String strVcodeUser,String  strVcodePass,String strSoftkey);
	//上传图片字节数组识别
	public native static String BJGetUserInfo(String strUser,String strPass);
	//查询剩余点数
	public native static void BJReportError(String strVcodeUser,String strYzmId);
	//报告错误验证码
	public static byte[] toByteArrayFromFile(String imageFile) throws Exception
	{
		InputStream is = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try
		{
			is = new FileInputStream(imageFile);
			byte[] b = new byte[1024];
			int n;
			while ((n = is.read(b)) != -1)
			{
				out.write(b, 0, n);

			}// end while
		} catch (Exception e)
		{
			throw new Exception("System error,SendTimingMms.getBytesFromFile", e);
		} finally
		{
			if (is != null)
			{
				try
				{
					is.close();
				} catch (Exception e)
				{}// end try
			}// end if

		}// end try
		return out.toByteArray();
	}
	
	
	  /**
     * 得到验证
     * 
     * @param driver
     * @param webElement
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String selectValidationCode (String fileName , int imgXOffset , int imgYOffset ) throws Exception {
        try {
    
            // 打码配置信息结束
            // 初始化打码,打开端口
            //打码配置信息开始
            String user = "oipiyenc";
            String pass = "dieutgcn7";
            String softId = "54490";//软件ID,作者帐号后台添加
            BJInit ();
            String info = BJGetUserInfo ( user , pass );
            log.info ( "剩余点数:" + info );
    
            // String yzmPath = "d:\\8g5k.jpg";
            byte[] img = toByteArrayFromFile ( fileName );
            String s = BJRecByte_A ( img , img.length , user , pass , softId );
            
            String[] result = s.split ( "\\|!\\|" );
            if ( result.length == 2 ) {
                log.info ( "识别结果:" + result[ 0 ] );
                log.info ( "验证码ID:" + result[ 1 ] );
                // 提交验证码,提交目标返回验证码对错
                //BJReportError ( USER , result[ 1 ] );// 如果验证码错误,则报告错误,返回积分
            }
            else {
                log.error ( "识别失败,失败原因为:" + s );
            }
    
            return result[ 0 ];
            
        }catch(Throwable e) {
            log.error ( e,e );
            throw e;
        }
    }
    
    
	
	  /**
   * 得到验证
   * 
   * @param driver
   * @param webElement
   * @param fileName
   * @return
   * @throws Exception
   */
  public static String inputValidationCode (String fileName , int imgXOffset , int imgYOffset ) throws Exception {
      try {
  
          // 打码配置信息结束
          // 初始化打码,打开端口
          //打码配置信息开始
          String user = "oipiyenc";
          String pass = "dieutgcn7";
          String softId = "55233";//软件ID,作者帐号后台添加54490
          BJInit ();
          String info = BJGetUserInfo ( user , pass );
          log.info ( "剩余点数:" + info );
  
          // String yzmPath = "d:\\8g5k.jpg";
          byte[] img = toByteArrayFromFile ( fileName );
          String s = BJRecByte_A ( img , img.length , user , pass , softId );
          
          String[] result = s.split ( "\\|!\\|" );
          if ( result.length == 2 ) {
              log.info ( "识别结果:" + result[ 0 ] );
              log.info ( "验证码ID:" + result[ 1 ] );
              // 提交验证码,提交目标返回验证码对错
              //BJReportError ( USER , result[ 1 ] );// 如果验证码错误,则报告错误,返回积分
          }
          else {
              log.error ( "识别失败,失败原因为:" + s );
          }
  
          return result[ 0 ];
          
      }catch(Throwable e) {
          log.error ( e,e );
          throw e;
      }
  }
    
    
	public static void main(String[] args) throws Exception
	{
	    System.out.println(new Date());
		//打码配置信息开始
		String user = "oipiyenc";
		String pass = "dieutgcn7";
		String softId = "54490";//软件ID,作者帐号后台添加
		BJInit();
		String info = BJGetUserInfo(user, pass);
		System.out.println("剩余点数"+info);
		
		//String s = BJRecYZM_A(yzmPath,user,pass,softId); 
		//System.out.println(s);
		
		String yzmPath = "d:\\8g5k.jpg";
		byte[] img = toByteArrayFromFile(yzmPath);
		String s = BJRecByte_A(img,img.length,user,pass,softId);
		String[] result = s.split("\\|!\\|");
		if (result.length==2) {
			System.out.println("识别结果:"+result[0]);
			System.out.println("验证码ID::"+result[1]);
		}
		else {
		    log.info ( "识别失败,失败原因为:" + s );
		}
		System.out.println(new Date());
	}
}
