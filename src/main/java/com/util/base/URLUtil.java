package com.util.base;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class URLUtil {
	
	/**
	 * URL解码
	 * @param str		需要解码的字符串
	 * @param code		编码
	 * @return
	 */
	public static String decode(String value,String code){
		if(value == null && value.trim().equals("")){
			return value;
		}
		String data = "";
		try {
			data = URLDecoder.decode(value, code);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return data;
	}
}
