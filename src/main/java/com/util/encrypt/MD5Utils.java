package com.util.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MD5Utils {
	/**
	 * MD5加密 (默认大写)(失败返回NULL)
	 * @param value		需要加密的字符串
	 * @return
	 */
	public static String toMD5(String value){
		try {
			//得到一个转换器
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			//字符串转换成字节数组
			byte [] valueArray = value.getBytes("UTF-8");
			//使用指定的 byte 数组更新摘要。
			messageDigest.update(valueArray);
			//使用指定的 byte 数组对摘要进行最后更新，然后完成摘要计算。
			byte [] resultArr =  messageDigest.digest();
			
			return byteArrayToHex(resultArr);
		} catch (Exception e) {
			return null;
		}
	}
	
	private static String byteArrayToHex(byte[] byteArray) {

		// 首先初始化一个字符数组，用来存放每个16进制字符
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		// new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
		char[] resultCharArray = new char[byteArray.length * 2];
		// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		// 字符数组组合成字符串返回
		return new String(resultCharArray);
		
		/*StringBuffer sb = new StringBuffer();
		int i;
		for (int offset = 0; offset < byteArray.length; offset++) {
			i = byteArray[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				sb.append("0");
			sb.append(Integer.toHexString(i));
		}
		return sb.toString().toUpperCase();*/
	}
	
	
	public static void main(String[] args) {
		String s = "1234564646546";
		System.out.println(toMD5(s));
	}
}
