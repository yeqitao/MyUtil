package com.util.base;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IdUtil {
	
	/**
	 * 获取以当前时间组成的id(精确到秒)
	 * @return
	 */
	public static String getDateId(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		return sdf.format(date);
	}
	
	/**
	 * 获取以当前时间组成的id(精确到日)
	 * @return
	 */
	public static String getDateDay(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		return sdf.format(date);
	}
	
	public static void main(String[] args) {
		System.out.println(getDateDay());
	}
}	
