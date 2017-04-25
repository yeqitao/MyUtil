package com.util.base;

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
		if(!value.toLowerCase().equals(value)){
			return false;
		}
		return true;
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
		if(!value.toUpperCase().equals(value)){
			return false;
		}
		return true;
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
	
	
	
	public static void main(String[] args) {
		/*String str = repeatString("abcd,",3);
		String[]arr = str.split(",");
		System.out.println(arr.length);*/
		System.out.println(reverse("你是谁!"));
	}
}
