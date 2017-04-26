package com.util.base;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StringUtil {
	
	/**
	 * 判断字符串是否全部为小写
	 * @param value 需要判断的字符串
	 * @return  返回布尔类型
	 */
	public static boolean isAllLowerCase(String value){
		if(value == null || value.trim().equals("")){
			return false;
		}
		if(value.trim().toLowerCase().equals(value)){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断字符串是否全部为大写
	 * @param value 需要判断的字符串
	 * @return  返回布尔类型
	 */
	public static boolean isAllUpperCase(String value){
		if(value == null || value.trim().equals("")){
			return false;
		}
		if(value.trim().toUpperCase().equals(value)){
			return true;
		}
		return false;
	}
	
	/**
	 * 字符串反转
	 * @param value
	 * @return
	 */
	public static String reverse(String value){
		if(value == null || value.trim().equals("")){
			return null;
		}
		return new StringBuffer(value).reverse().toString();
	}
	
	/**
	 * 将一个字符重复N次
	 * @param value		需要重复的字符
	 * @param count		需要重复的次数
	 * @return
	 */
	public static String repeatChar(Character value , int count){
		if(value == null || value.equals("") || count <= 1){
			return value.toString();
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			sb.append(value);
		}
		return sb.toString();
	}
	
	/**
	 * 将一个字符串重复N次
	 * 		当value == null || value == "" return value;<br>
	 * 		当count <= 1 返回  value
	 * @param value		需要重复的字符串
	 * @param count		需要重复的次数
	 * @return
	 */
	public static String repeatString(String value , int count){
		if(value == null || value.trim().equals("") || count <= 1){
			return value;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			sb.append(value);
		}
		return sb.toString();
	}
	
	/**
	 * 判断该字符串数组是否存在该字符串
	 * @param values		字符串数组
	 * @param value			字符串
	 * @return
	 */
	public static boolean contains(String [] values , String value){
		if(values != null && values.length > 0 && value != null){
			for (String str : values) {
				if(str.equals(value)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 判断字符串是否为空(null,"","null")
	 * @param value		字符串
	 * @return 为空则返回true，否则返回false
	 */
	public static boolean isNullOrEmpty(String value) {
		return value == null || value.trim().equals("") || value.trim().equals("null");
	}
	
	/**
	 * 字符串转码
	 * @param value		字符串
	 * @param oldCoding		旧编码
	 * @param newCoding		新编码
	 * @return
	 */
	public static String transcoding(String value , String oldCoding , String newCoding){
		try {
			return new String(value.getBytes(oldCoding),newCoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("字符串转码出问题了");
		}
	}
	
	public static void main(String[] args) {
		
		System.out.println();
	}
}
