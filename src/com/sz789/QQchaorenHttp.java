package com.sz789;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.rkylin.crawler.engine.flood.util.HTTP;
import com.rkylin.crawler.engine.flood.util.JsonUtil;
import com.ruixuesoft.crawler.open.RxCrawlerException;

public class QQchaorenHttp {
	
    private static final transient Logger logger = Logger.getLogger ( QQchaorenHttp.class );

	private final static String HEXSTR = "0123456789ABCDEF";
	private final static String USER = "oipiyenc";
	private final static String PASSWORD = "dieutgcn7";
	private final static String SOFTID_INPUT = "55233";
	private final static String SOFTID_SELECT = "54490";
	private final static String URL_GET_VALIDATION_CODE_INPUT = "http://api2.sz789.net:88/RecvByte.ashx";
	private final static String URL_GET_VALIDATION_CODE_SELECT = "http://apib.sz789.net:88/RecvByte.ashx";
	
    /**
     * 获取输入类型的验证码
     * @param fileName 验证码图片的文件名
     * @return 验证码
     * @exception RxCrawlerException
     */
	public static String getInputValidationCode( String fileName ) throws Exception {
		
		Map <String, String> params = new HashMap <String, String> ();
		params.put( "username", USER );
		params.put( "password", PASSWORD );
		params.put( "softId", SOFTID_INPUT );
		
		try {
			
			params.put( "imgdata", BinaryToHexString( toByteArrayFromFile( fileName ) ) );
			
		} catch ( Exception e ) {
			
			logger.error("图片转换失败！", e);
			
			throw e;
		}
		
		String result = "";
		try {
			
			result = HTTP.doPost( URL_GET_VALIDATION_CODE_INPUT, params );
			
		} catch ( Exception e ) {
			
			logger.error("doPost失败！", e);
			
			throw e;
		}
		
		logger.info("result:" + result);
		
		QQchaorenResult chaorenResult = new QQchaorenResult();
		try {
			
			chaorenResult = JsonUtil.convertJsonStr2Obj( result, QQchaorenResult.class );
			
		} catch ( Exception e ) {
			
			logger.error("convertJsonStr2Obj失败！", e);
			
			throw e;
			
		}
		
		result = chaorenResult.getResult();
		
		return result;
	}
	
    /**
     * 获取点选类型的验证码
     * @param fileName 验证码图片的文件名
     * @return 验证码图片的坐标
     * @exception RxCrawlerException
     */
	public static String getSelectValidationCode( String fileName )  throws Exception {
		
		Map <String, String> params = new HashMap <String, String> ();
		params.put( "username", USER );
		params.put( "password", PASSWORD );
		params.put( "softId", SOFTID_SELECT );
		
		try {
			
			params.put( "imgdata", BinaryToHexString( toByteArrayFromFile( fileName ) ) );
			
		} catch ( Exception e ) {
			
			logger.error("图片转换失败！", e);
			
			throw e;
			
		}
		
		String result = "";
		try {
			
			result = HTTP.doPost( URL_GET_VALIDATION_CODE_SELECT, params );
			
		} catch ( Exception e ) {
			
			logger.error("doPost失败！", e);
			
			throw e;
			
		}
		logger.info("result:" + result);
		
		QQchaorenResult chaorenResult = new QQchaorenResult();
		try {
			
			chaorenResult = JsonUtil.convertJsonStr2Obj( result, QQchaorenResult.class );
			
		} catch ( Exception e ) {
			
			logger.error("convertJsonStr2Obj失败！", e);
			
			throw e;
			
		}
		
		result = chaorenResult.getResult();
		
		return result;
	}
	

	private static String BinaryToHexString( byte[] bytes ) {

		String result = "";
		String hex = "";
		
		for ( int i = 0; i < bytes.length; i++ ) {
			// 字节高4位
			hex = String.valueOf(HEXSTR.charAt((bytes[i] & 0xF0) >> 4));
			// 字节低4位
			hex += String.valueOf(HEXSTR.charAt(bytes[i] & 0x0F));
			result += hex;
		}
		
		return result;
	}


	private static byte[] toByteArrayFromFile(String imageFile) throws Exception
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

	public static void main(String args[]) {
		String fileName = "c:\\www\\img20170309090553.jpg";
//		String fileName = "c:\\www\\img20170912141955.jpg";
		try {
			getSelectValidationCode( fileName );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		getInputValidationCode( fileName );
	}

}
