package com.ruixuesoft.crawler.open;

import com.rkylin.crawler.engine.flood.util.StringUtil;

public class RxUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(removeSpecialChar("环境不错，很干净…😊"));
	}

	
	/**
	 * 去除字符串中的特殊字符【表情符号等】
	 * 
	 * @param content 对象字符串
	 * @return 去除特殊字符后的字符串
	 */
	public static String removeSpecialChar(String content) {

		return StringUtil.removeSpecialChar(content);
		
	}
}
