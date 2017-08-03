package com.util.base;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;


public class UploadUtil {
	
	
	/**
	 * 图片文件上传
	 * @param file 图片文件
	 * @param path 上传的路径
	 * @return
	 */
	public String imageUpload(MultipartFile file,String path) { 
		if (file.isEmpty()) { 
			return "文件不能为空"; 
		} // 获取文件名 
		String fileName = file.getOriginalFilename(); 
		System.out.println("上传的文件名为：" + fileName); 
		// 获取文件的后缀名 
		String suffixName = fileName.substring(fileName.lastIndexOf(".")); 
		System.out.println("上传的后缀名为：" + suffixName); 
		// 文件上传后的路径 
		String filePath = path;
		// 解决中文问题，liunx下中文路径，图片显示问题 
		// fileName = UUID.randomUUID() + suffixName; 
		File dest = new File(filePath + fileName); // 检测是否存在目录 
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs(); 
		} try {
			file.transferTo(dest); 
			return fileName; 
		} catch (IllegalStateException e) { 
			e.printStackTrace(); 
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return "文件上传失败"; 
	}
}
