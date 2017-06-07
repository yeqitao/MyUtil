package com.util.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ExcelUtil {
	
	/**
	 * JSON格式通用导出Excel
	 * @param fileName		文件名称
	 * @param filePath 		文件保存路径
	 * @param title			列名
	 * @param property		jsonArray的key
	 * @param jsonArray		要导出的内容
	 * @param request
	 */
	public static void exportJsonToExcel(String fileName,String filePath,String [] title,String [] property,JSONArray jsonArray){
		File file = new File(filePath, fileName+"xls");
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			//创建一个工作簿
			HSSFWorkbook wb = new HSSFWorkbook();
			//创建表格
			HSSFSheet sheet = wb.createSheet(fileName);
			//设置表格的默认列宽
			sheet.setDefaultColumnWidth(15);
			//生成样式
			HSSFCellStyle style = wb.createCellStyle();
				//设置样式
			 style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
			 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
			 style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
			 style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
			 style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
			 style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
			 style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
			 
			//生成字体
			HSSFFont font = wb.createFont();
			 font.setColor(HSSFColor.VIOLET.index);  
			 font.setFontHeightInPoints((short) 12);  
			 font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
			 
			 //把字体应用到样式中
			 style.setFont(font);
			 
			 // 生成并设置另一个样式  
			 HSSFCellStyle style2 = wb.createCellStyle();  
			 style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);  
			 style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
			 style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
			 style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
			 style2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
			 style2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
			 style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
			 style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
			 
			 // 生成另一个字体  
			 HSSFFont font2 = wb.createFont();  
			 font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
			 // 把字体应用到当前的样式  
			 style2.setFont(font2);  
			 
			 //产生表格的标题行
			 HSSFRow row = sheet.createRow(0);
			 for (int i = 0; i < title.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style);
				HSSFRichTextString text = new HSSFRichTextString(title[i]);
				cell.setCellValue(text);
			}
			
			//填充数据
			for (int i = 0; i < jsonArray.size(); i++) {
				row = sheet.createRow(i+1);
				JSONObject data = jsonArray.getJSONObject(i);
				for (int j = 0; j < property.length; j++) {
					HSSFCell cell = row.createCell(j);
					HSSFRichTextString text = new HSSFRichTextString(data.getString(property[j]));
					cell.setCellStyle(style2);
					cell.setCellValue(text);
				}
			}
			
	        wb.write(fout);
	        System.out.println("导出成功");
		} catch (Exception e) {
			e.printStackTrace();
			//request.setAttribute("error","导出"+fileName+"统计出错请找开发人员!");
		}finally{
			try {
				fout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * List格式通用导出Excel(格式化日期)
	 * @param <T>
	 * @param fileName		文件名称
	 * @param filePath 		文件保存路径
	 * @param title			列名
	 * @param list			要导出的内容
	 * @param request
	 */
	public static <T> void exportListToExcel(String fileName,String filePath,String [] title,List<T> list){
		File file = new File(filePath, fileName+"xls");
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			//创建一个工作簿
			HSSFWorkbook wb = new HSSFWorkbook();
			//创建表格
			HSSFSheet sheet = wb.createSheet(fileName);
			//设置表格的默认列宽
			sheet.setDefaultColumnWidth(15);
			//生成样式
			HSSFCellStyle style = wb.createCellStyle();
				//设置样式
			 style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
			 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
			 style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
			 style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
			 style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
			 style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
			 style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
			 
			//生成字体
			HSSFFont font = wb.createFont();
			 font.setColor(HSSFColor.VIOLET.index);  
			 font.setFontHeightInPoints((short) 12);  
			 font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
			 
			 //把字体应用到样式中
			 style.setFont(font);
			 
			 // 生成并设置另一个样式  
			 HSSFCellStyle style2 = wb.createCellStyle();  
			 style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);  
			 style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
			 style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
			 style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
			 style2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
			 style2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
			 style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
			 
			 style2.setDataFormat(wb.createDataFormat().getFormat("yyyy年MM月dd日hh时mm分ss秒"));
			 // 生成另一个字体  
			 HSSFFont font2 = wb.createFont();  
			 font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
			 // 把字体应用到当前的样式  
			 style2.setFont(font2);  
			 
			 //产生表格的标题行
			 HSSFRow row = sheet.createRow(0);
			 for (int i = 0; i < title.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style);
				HSSFRichTextString text = new HSSFRichTextString(title[i]);
				cell.setCellValue(text);
			}
			
			//填充数据
			Field[]fields = null;
			int i = 0;		//列计数器
			int a = 0;		//行计数器
			for (Object obj : list) {
				fields = obj.getClass().getDeclaredFields();			//利用反射根据javabean的先后顺序,动态调用getXXX获取属性值;
				row = sheet.createRow(++a);								//先++   从第一行开始(0行是列名) 
				for (Field field : fields) {							
					field.setAccessible(true);
					Object value = field.get(obj);						//获取值
					if(value == null){								
						value="";
					}
					if(field.getType() == Date.class){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						HSSFCell cell = row.createCell(i++);
						cell.setCellStyle(style2);							//设置样式
						HSSFRichTextString text = new HSSFRichTextString(sdf.format(value));
						cell.setCellValue(text);;						//填充值
					}else{
						HSSFCell cell = row.createCell(i++);
						cell.setCellStyle(style2);							//设置样式
						HSSFRichTextString text = new HSSFRichTextString(value.toString());
						cell.setCellValue(text);;						//填充值
					}
					
				}
				i = 0;			//列计数器归0
			}
			
	        wb.write(fout);
	        System.out.println("导出成功");
		} catch (Exception e) {
			e.printStackTrace();
			//request.setAttribute("error","导出"+fileName+"统计出错请找开发人员!");
		}finally{
			try {
				fout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) {
		/*JSONArray array = new JSONArray();
		array.add(new pojo("333",new Date()));
		array.add(new pojo("456",new Date()));
		
		String[]arr = {"name","date"};
		String[]title = {"名字","日期"};
		exportJsonToExcel("测试", "F:/ex", title, arr, array);*/
		/*List<pojo> array = new ArrayList<pojo>();
		array.add(new pojo("的杰卡斯",new Date()));
		array.add(new pojo("第三",new Date()));
		
		String[]title = {"名字","日期"};
		exportListToExcel("测试2", "F:/ex", title , array);*/
	}
}
