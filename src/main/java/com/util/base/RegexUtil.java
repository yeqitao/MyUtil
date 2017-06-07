package com.util.base;

import java.util.regex.Pattern;

public class RegexUtil {
	
	
	/**
	 * 判断该字符串是否符合该正则表达式
	 * @param str	需要判断的字符串
	 * @param regex		正则
	 * @return
	 */
	public static boolean isRegex(String str,String regex){
		Pattern p = Pattern.compile(regex);
		boolean b = str.matches(regex);
		return b;
	}
	
	/**
	 * 判断是否是手机号码(手机号码格式是否正确)
	 * 
	 * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
	 * 联通号码段:130、131、132、136、185、186、145
	 * 电信号码段:133、153、180、189
	 * 
	 * @param phoneNumber	手机号码
	 * @return
	 */
	public static boolean isPhone(String phoneNumber){
		if(phoneNumber == null || phoneNumber.length() != 11){
			return false;
		}
		Pattern p = Pattern.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$");
		return p.matcher(phoneNumber).matches();
	}
	
	
	/**
	 * 判断是否是邮箱(邮箱格式是否正确)
	 * @param email  邮箱
	 * @return
	 */
	public static boolean isEmail(String email){
		if(email == null || !email.contains("@")){
			return false;
		}
		Pattern p = Pattern.compile("^([a-zA-Z0-9]*\\.?[a-zA-Z0-9]+@[\\w]+\\.\\w{2,3})$");
		return p.matcher(email).matches();
	}
	
	/**
	 * 判断该字符串是否是整数
	 * @param value		值
	 * @return
	 */
	public static boolean isInt(String value){
		if(value == null || value.contains(".")){
			return false;
		}
		Pattern p = Pattern.compile("^\\d+$");
		return p.matcher(value).matches();
	}
	
	/**
	 * 判断该字符串是否是小数
	 * @param value		值
	 * @return
	 */
	public static boolean isDouble(String value){
		if(value == null || !value.contains(".")){
			return false;
		}
		Pattern p = Pattern.compile("^[0-9]+\\.[0-9]+$");
		return p.matcher(value).matches();
	}
	
	/**
	 * 验证是否是纯中文
	 * @param value		值
	 * @return
	 */
	public static boolean isChinese(String value){
		if(value == null){
			return false;
		}
		Pattern p = Pattern.compile("^[\u0391-\uFFE5]+$");
		return p.matcher(value).matches();
	}
	
	
	/**
	 * 检验身份证号码
	 * @param value		值
	 * @return
	 */
	public static boolean isID(String value){
		if(value == null || value.length() != 18 || value.length() != 15){
			return false;
		}
		if(value.length() == 18){
			Pattern p = Pattern.compile("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$");
			return p.matcher(value).matches();
		}
		if(value.length() == 15){
			Pattern p = Pattern.compile("^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$");
			return p.matcher(value).matches();
		}
		
		return false;
	}
	
	
	public static void main(String[] args) {
		String str = "去去去";
		
		System.out.println(isChinese(str));
	}
}
