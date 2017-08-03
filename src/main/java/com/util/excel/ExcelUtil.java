package com.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;









import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class ExcelUtil {
	
	private static String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
	
	/**
	 * JSON格式通用导出Excel
	 * @param fileName		文件名称
	 * @param filePath 		文件保存路径
	 * @param title			列名
	 * @param property		jsonArray的key
	 * @param jsonArray		要导出的内容
	 * @param request
	 */
	public static void exportJsonToExcel(String fileName,String filePath,String [] title,String [] property,JSONArray jsonArray,HttpServletRequest request){
		File file = new File(filePath, fileName+".xls");
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
	        request.setAttribute("msg","导出 "+fileName+" 操作成功!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error","导出 "+fileName+" 操作有误请找开发人员!");
		}finally{
			try {
				fout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * List格式通用导出Excel(格式化日期)小数保留两位
	 * @param <T>
	 * @param fileName		文件名称
	 * @param filePath 		文件保存路径
	 * @param title			列名
	 * @param list			要导出的内容
	 * @param request
	 */
	public static <T> void exportListToExcel(String fileName,String filePath,String [] title,List<T> list){
		File file = new File(filePath, fileName+".xls");
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
				fields = obj.getClass().getDeclaredFields();			//利用反射根据javabean的先后顺序,动态获取属性值;
				row = sheet.createRow(++a);								//先++   从第一行开始(0行是列名) 
				for (Field field : fields) {							
					field.setAccessible(true);
					Object value = field.get(obj);						//获取值
					if(value == null){								
						value="";
					}
					if(field.getType() == Date.class){
						SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
						HSSFCell cell = row.createCell(i++);
						cell.setCellStyle(style2);							//设置样式
						HSSFRichTextString text = new HSSFRichTextString(sdf.format(value));
						cell.setCellValue(text);;						//填充值
					}else if((field.getType() == double.class) || (field.getType() == Double.class) ){
						DecimalFormat df = new DecimalFormat( "0.00");		//保留两位小数
						HSSFCell cell = row.createCell(i++);
						cell.setCellStyle(style2);							//设置样式
						HSSFRichTextString text = new HSSFRichTextString(df.format(value));
						cell.setCellValue(text);;	
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
	        //request.setAttribute("msg","导出 "+fileName+" 操作成功!");
		} catch (Exception e) {
			e.printStackTrace();
			//request.setAttribute("error","导出 "+fileName+" 操作有误请找开发人员!");
		}finally{
			try {
				fout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 导入有列名的excel(返回一个JSONArray) 日期格式yyyy-MM-dd HH:mm:ss
	 * @param filePath			文件全名
	 * @param clazz
	 */
	public static JSONArray importExcel(String filePath,Class<?> clazz){
		JSONArray jsonArray = new JSONArray();
		
		InputStream inStream = null;

		try {
			inStream = new FileInputStream(filePath);
			HSSFWorkbook wb = new HSSFWorkbook(inStream);
			HSSFSheet sheet = wb.getSheetAt(0);//获得表
			int lastRowNum = sheet.getLastRowNum();//最后一行
			Field[]fields = clazz.getDeclaredFields();
			for (int i = 0; i < lastRowNum; i++) {
				HSSFRow row = sheet.getRow(i+1); //获取行,从第二行开始获取(第一行标题)
				JSONObject obj = new JSONObject();
				for (int j = 0; j < fields.length; j++) {
					HSSFCell cell = row.getCell(j);
					String value = cell.getStringCellValue();		//属性值
					String name = fields[j].getName();			//属性名称
					if(fields[j].getType() == Date.class){
						SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
						obj.put(name, sdf.parse(value));
					}else if(fields[j].getType() == String.class){
						obj.put(name, value);
					}else if((fields[j].getType() == int.class) || (fields[j].getType() == Integer.class) ){
						obj.put(name, Integer.parseInt(value));
					}else if((fields[j].getType() == long.class) || (fields[j].getType() == Long.class) ){
						obj.put(name, Long.parseLong(value));
					}else if((fields[j].getType() == double.class) || (fields[j].getType() == Double.class) ){
						obj.put(name, Double.parseDouble(value));
					}
					
					System.out.println(obj);
				}
				jsonArray.add(obj);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			//request.setAttribute("error","导出 "+fileName+" 操作有误请找开发人员!");
		}
		return jsonArray;
		
	}
	
	/**
	 * 导入无列名的excel(返回一个JSONArray) 日期格式yyyy-MM-dd HH:mm:ss
	 * @param filePath			文件
	 * @param clazz
	 */
	public static JSONArray importExcel2(String filePath,Class<?> clazz){
		JSONArray jsonArray = new JSONArray();
		
		InputStream inStream = null;

		try {
			inStream = new FileInputStream(filePath);
			HSSFWorkbook wb = new HSSFWorkbook(inStream);
			HSSFSheet sheet = wb.getSheetAt(0);//获得表
			int lastRowNum = sheet.getLastRowNum();//最后一行
			Field[]fields = clazz.getDeclaredFields();
			for (int i = 0; i < lastRowNum; i++) {
				HSSFRow row = sheet.getRow(i); //无列名 直接导入
				JSONObject obj = new JSONObject();
				for (int j = 0; j < fields.length; j++) {
					HSSFCell cell = row.getCell(j);
					String value = cell.getStringCellValue();		//属性值
					String name = fields[j].getName();			//属性名称
					if(fields[j].getType() == Date.class){
						SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
						obj.put(name, sdf.parse(value));
					}else if(fields[j].getType() == String.class){
						obj.put(name, value);
					}else if((fields[j].getType() == int.class) || (fields[j].getType() == Integer.class) ){
						obj.put(name, Integer.parseInt(value));
					}else if((fields[j].getType() == long.class) || (fields[j].getType() == Long.class) ){
						obj.put(name, Long.parseLong(value));
					}else if((fields[j].getType() == double.class) || (fields[j].getType() == Double.class) ){
						obj.put(name, Double.parseDouble(value));
					}
					
					System.out.println(obj);
				}
				jsonArray.add(obj);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			//request.setAttribute("error","导出 "+fileName+" 操作有误请找开发人员!");
		}
		return jsonArray;
		
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
		//JSONArray array = importExcel("F:/ex/测试2xls", pojo.class);
		//List<pojo> li = JSONObject.parseArray(array.toJSONString(),pojo.class);
		/*for (int i = 0; i < array.size(); i++) {
			JSONObject obj = array.getJSONObject(i);
			String str = obj.getString("name");
			Date date = obj.getDate("date");
			li.add(new pojo(str, date));
		}*/
		//System.out.println(li);
		
		//System.out.println(li);
	}
}
