package com.rkylin.crawler.engine.flood.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    /**
     * 验证IP地址
     * 
     * @param str
     *            待验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean isIP(String str) {
        String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
        String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num
                + "$";
        return match(regex, str);
    }

    /**
     * @param regex
     *            正则表达式字符串
     * @param str
     *            要匹配的字符串
     * @return 如果 str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    
    
    public static boolean contains( String content, String word ) {
    	if ( content == null || !content.contains(word) )
    		return false;
    	else
    		return true;
    }
    
    public static String preZeroAdd(int num)
    {    	
    	if(num < 10)
    	{
    		return "0" + num;
    	}
    	
    	return "" + num;
    }
    
    
	/**
	 * 去除字符串中的【代理对高位字,代理对私用区高位字,代理对低位字,私用区】
	 * 
	 * @param content 对象字符串
	 * @return 去除特殊字符后的字符串
	 */
	public static String removeSpecialChar(String content) {
		
		if (content == null || content.length() == 0) {
			return "";
		}

		char[] contentchars = new char[content.length()];
		content.getChars(0, content.length(), contentchars, 0);

		StringBuffer result = new StringBuffer();
		for (char eachChar : contentchars) {
			if ((eachChar >= 0xD800 && eachChar <= 0xF8FF) // 代理对高位字,代理对私用区高位字,代理对低位字,私用区
					|| (eachChar >= 0xFFF0 && eachChar <= 0xFFFF) // 特殊
			) {
				// ignore these char
			} else {
				result.append(eachChar);
			}
		}

		return result.toString();
	}
	
}
