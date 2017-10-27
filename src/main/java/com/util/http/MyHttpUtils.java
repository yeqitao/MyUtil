package com.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class MyHttpUtils implements HttpHandler{
	
	public void handle(HttpExchange he) throws IOException {
		InputStreamReader is = null;
		BufferedReader br = null;
		try {
			is = new InputStreamReader(he.getRequestBody(),"UTF-8");
			br = new BufferedReader(is);
			StringBuilder sb = new StringBuilder();
			String tem = null;
			while((tem=br.readLine()) != null){
				sb.append(tem);
			}
			
			System.out.println(sb);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			br.close();
			is.close();
		}
	}
	
	public void respMessage(HttpExchange he,String content){
		OutputStream os = null;
		try {
			byte [] respContent = content.getBytes();
			he.sendResponseHeaders(200, respContent.length);
			os = he.getResponseBody();
			os.write(respContent);
			os.flush();
		} catch (Exception e) {
			System.out.println("**********返回消息失败");
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
