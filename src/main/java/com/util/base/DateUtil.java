package com.util.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	
	/**
	 * 日期转换为对应格式的字符串
	 * @param date	需要转换的日期
	 * @param format	指定格式
	 * @return
	 */
	public static String dateToStr(Date date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if(date == null || date.equals("")){
			return "";				//返回一个空串
		}
		
		return sdf.format(date);		//返回指定格式
	}
	
	/**
	 * 日期字符串转换为data类型
	 * @param date	需要转换的字符串
	 * @param format	字符串的格式
	 * @return
	 */
	public static Date strToDate(String date,String format){
		Date da = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			if(date == null || date.equals("")){
				return new Date();				//返回一个空串
			}
			da = sdf.parse(date);		//返回指定格式
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return da;
	}
	
	
	
	public static void main(String[] args) {
		Date date = strToDate("","yyyy-MM-dd HH:mm:ss");
		System.out.println(dateToStr(date,"yyyy/MM/dd HH:mm:ss"));
	}
}
