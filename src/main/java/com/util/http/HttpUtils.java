package com.util.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.net.httpserver.HttpExchange;

import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;


/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: http utils
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author LiLu
 * @version 1.0
 */
public class HttpUtils {

	private static final String URL_PARAM_CONNECT_FLAG = "&";

	private HttpUtils() {
	}

	/**
	 * GET METHOD
	 * 
	 * @param strUrl
	 *            String
	 * @param map
	 *            Map
	 * @throws IOException
	 * @return List
	 */
	public static List<String> URLGet(String strUrl, Map<String, String> map,
			int timeOut) throws IOException {
		String strtTotalURL = "";
		List<String> result = new ArrayList<String>();
		if (strtTotalURL.indexOf("?") == -1) {
			strtTotalURL = strUrl + "?" + getUrl(map);
		} else {
			strtTotalURL = strUrl + "&" + getUrl(map);
		}
		System.out.println("strtTotalURL:" + strtTotalURL);
		URL url = new URL(strtTotalURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setUseCaches(false);
		HttpURLConnection.setFollowRedirects(true);
		con.setConnectTimeout(timeOut);
		con.setReadTimeout(timeOut);
		BufferedReader in = new BufferedReader(new InputStreamReader(con
				.getInputStream(), "utf8"));
		int i = 0;
		while (true) {
			i++;
			String line = in.readLine();
			if (line == null || i == 100) {
				break;
			} else {
				result.add(line);
				System.out.println(line);
			}
		}
		in.close();
		con.disconnect();
		return result;
	}

	/**
	 * POST METHOD
	 * 
	 * @param strUrl
	 *            String
	 * @param content
	 *            Map
	 * @throws IOException
	 * @return List
	 */
	public static List<String> URLPost(String strUrl, Map<String, String> map,
			String decodeCharset, int timeOut) {
		List<String> result = new ArrayList<String>();
		String content = "";
		content = getUrl(map);
		String totalURL = "";
		if (strUrl.indexOf("?") == -1) {
			totalURL = strUrl + "?" + content;
		} else {
			totalURL = strUrl + "&" + content;
		}
		System.out.println("strtTotalURL:" + totalURL);
		URL url;
		try {
			url = new URL(strUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setAllowUserInteraction(false);
			con.setUseCaches(false);
			con.setRequestMethod("POST");
			con.setConnectTimeout(timeOut);// 30秒超时
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset="
							+ decodeCharset);
			BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con
					.getOutputStream()));
			bout.write(new String(content.getBytes(decodeCharset)));
			bout.flush();
			bout.close();
			BufferedReader bin = new BufferedReader(new InputStreamReader(con.getInputStream()));
			int i = 0;
			while (true) {
				String line = bin.readLine();
				i++;
				if (line == null || i == 100) {
					break;
				}else {
					result.add(new String(line.getBytes("UTF-8")));
					System.out.println(line);
				}
			}
			bin.close();
			con.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	/**
	 * ���URL
	 * 
	 * @param map
	 *            Map
	 * @return String
	 */
	public static String getUrl(Map<String, String> map) {
		if (null == map || map.keySet().size() == 0) {
			return ("");
		}
		StringBuffer url = new StringBuffer();
		Set keys = map.keySet();
		for (Iterator i = keys.iterator(); i.hasNext();) {
			String key = String.valueOf(i.next());
			if (map.containsKey(key)) {
				Object val = map.get(key);
				String str = val != null ? val.toString() : "";
				try {
					str = URLEncoder.encode(str, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				url.append(key).append("=").append(str).append(
						URL_PARAM_CONNECT_FLAG);
			}
		}
		String strURL = "";
		strURL = url.toString();
		if (URL_PARAM_CONNECT_FLAG.equals(""
				+ strURL.charAt(strURL.length() - 1))) {
			strURL = strURL.substring(0, strURL.length() - 1);
		}
		return (strURL);
	}

	public static void parseGetParameters(HttpExchange exchange,
			String decodeCharset) throws UnsupportedEncodingException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		URI requestedUri = exchange.getRequestURI();
		String query = requestedUri.getRawQuery();
		System.out.println("getData = " + query);
		parseQuery(query, parameters, decodeCharset);
		exchange.setAttribute("parameters", parameters);
	}

	public static void parsePostParameters(HttpExchange exchange,
			String decodeCharset) throws IOException {
		if ("post".equalsIgnoreCase(exchange.getRequestMethod())) {
			@SuppressWarnings("unchecked")
			Map<String, Object> parameters = (Map<String, Object>) exchange
					.getAttribute("parameters");
			InputStreamReader isr = new InputStreamReader(exchange
					.getRequestBody(), decodeCharset);
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			System.out.println("postData = " + query);
			parseQuery(query, parameters, decodeCharset);
		}
	}

	public static void parseQuery(String query, Map<String, Object> parameters,
			String decodeCharset) throws UnsupportedEncodingException {
		if (query != null) {
			String pairs[] = query.split("[&]");
			for (String pair : pairs) {
				String param[] = pair.split("[=]");

				String key = null;
				String value = null;
				if (param.length > 0) {
					key = URLDecoder.decode(param[0], decodeCharset);
				}
				if (param.length > 1) {
					value = URLDecoder.decode(param[1], decodeCharset);
				}
				if (parameters.containsKey(key)) {
					Object obj = parameters.get(key);
					if (obj instanceof List<?>) {
						List<String> values = (List<String>) obj;
						values.add(value);
					} else if (obj instanceof String) {
						List<String> values = new ArrayList<String>();
						values.add((String) obj);
						values.add(value);
						parameters.put(key, values);
					}
				} else {
					parameters.put(key, value);
				}
			}
		}
	}

	/**
	 * 
	 * Function name:getRequestUriValue Description: 解析uri字串，获得数据
	 * 
	 * @param uri
	 *            ：字串
	 * @return：数据对
	 */
	public static HashMap<String, String> getRequestUriValue(String uri) {
		HashMap<String, String> attributes = new HashMap<String, String>();
		int beginIndex = uri.indexOf("?");
		if (beginIndex != -1) {
			String body = uri.substring(beginIndex + 1);
			String[] strs = body.split("&");
			for (String keyValues : strs) {
				String[] attribute = keyValues.split("=");
				if (attribute.length > 2) {
					continue;
				}
				if (attribute.length == 1) {
					String key = attribute[0];
					String value = "";
					attributes.put(key, value);
				}
				if (attribute.length == 2) {
					String key = attribute[0];
					String value = attribute[1];
					attributes.put(key, value);
				}
			}
		}
		return attributes;
	}

	/*private static HostnameVerifier hv = new HostnameVerifier() {
		@Override
		public boolean verify(String urlHostName, SSLSession session) {
			System.out.println("Warning: URL Host: " + urlHostName + " vs. "
					+ session.getPeerHost());
			return true;
		}
	};

	public static List<String> URLPostSSL(String strUrl, String content,
			String decodeCharset) {
		HttpsURLConnection con = null;
		try {
			trustAllHttpsCertificates();
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			URL url = new URL(strUrl);
			con = (HttpsURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setAllowUserInteraction(false);
			con.setUseCaches(false);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"
					+ decodeCharset);
			con.setRequestProperty("Content-Length", ""
					+ Integer.toString(content.getBytes().length));
			BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con
					.getOutputStream()));
			bout.write(content);
			bout.flush();
			bout.close();
			int responseCode = con.getResponseCode();
			List<String> result = null;
			if (responseCode == 200) {
				result = new ArrayList<String>();
				BufferedReader bin = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				int i = 0;
				while (true) {
					i++;
					String line = bin.readLine();
					if (line == null || i == 100) {
						break;
					} else {
						System.out.println(line);
						result.add(line);
					}
				}
				bin.close();
				con.disconnect();
			} else {
				logger.error("AppStoreManage urlpost 返回错误状态 responseCode= "
						+ responseCode);
				System.out.println("错误号:"+ responseCode);
			}
			return result;
		} catch (Exception ex) {
			logger.error("AppStoreManage URLPost 出错了", ex);
			return null;
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
	}*/

	/*public static List<String> URLPost(String strUrl, String content,
			String decodeCharset) {
		HttpURLConnection con = null;
		System.out.println("POST 请求:"+strUrl);
		System.out.println("POST 请求,count:"+content);
		try {
			URL url = new URL(strUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setAllowUserInteraction(false);
			con.setUseCaches(false);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json;charset="
					+ decodeCharset);
			con.setRequestProperty("Content-Length", ""
					+ Integer.toString(content.getBytes().length));
			BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con
					.getOutputStream()));
			bout.write(content);
			bout.flush();
			bout.close();
			int responseCode = con.getResponseCode();
			List<String> result = null;
			if (responseCode == 200) {
				result = new ArrayList<String>();
				BufferedReader bin = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String[] probableSet = CharsetDetector.detectChineseCharset(url
						.openStream());
				StringBuffer sb = new StringBuffer();

				for (String charset : probableSet) {
					sb.append(charset);
				}
				int i = 0;
				while (true) {
					i++;
					String line = bin.readLine();
					if (line == null || i == 100) {
						break;
					} else {
						result.add(new String(line.getBytes()));
					}
				}
				bin.close();
			} else {
				System.out.println("urlpost 返回错误状态 responseCode= "
						+ responseCode);
				
			}
			return result;
		} catch (Exception ex) {
			System.out.println("URLPost异常");
			return null;
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
	}*/

	
	public static List<String> URLPost(String strUrl, String content,
			String decodeCharset, int timeout) {
		HttpURLConnection con = null;
		System.out.println("POST 请求:"+strUrl);
		System.out.println("POST 请求,count:"+content);
		try {
			URL url = new URL(strUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setAllowUserInteraction(false);
			con.setUseCaches(false);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json;charset="
					+ decodeCharset);
			con.setRequestProperty("Content-Length", ""
					+ Integer.toString(content.getBytes().length));
			con.setConnectTimeout(timeout);// 30秒超时
			BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con
					.getOutputStream()));
			bout.write(content);
			bout.flush();
			bout.close();
			int responseCode = con.getResponseCode();
			List<String> result = null;
			if (responseCode == 200) {
				result = new ArrayList<String>();
				BufferedReader bin = new BufferedReader(new InputStreamReader(
						con.getInputStream(), "UTF-8"));
				int i = 0;
				while (true) {
					i++;
					String line = bin.readLine();
					if (line == null || i == 100) {
						break;
					} else {
						result.add(new String(line.getBytes("UTF-8")));
					}
				}
				bin.close();
			} else {
				System.out.println("urlpost 返回错误状态 responseCode= "+ responseCode);
			}
			return result;
		} catch (Exception ex) {
			System.out.println("URLPost异常");
			return null;
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
	}
	
	public static String URLPost(String strUrl, Map<String, String> map,
			int timeOut) {
		HttpURLConnection con = null;
		String content = getUrl(map);
		String totalURL = "";
		if (strUrl.indexOf("?") == -1) {
			totalURL = strUrl + "?" + content;
		} else {
			totalURL = strUrl + "&" + content;
		}
		System.out.println("POST DATA :"+URLDecoder.decode(content));
		System.out.println("strtTotalURL:" + totalURL);
		try {
			URL url = new URL(strUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setConnectTimeout(timeOut);// 30秒超时
			connection.setReadTimeout(timeOut);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			connection.connect();
			// POST请求
			DataOutputStream out = new DataOutputStream(connection
					.getOutputStream());
			out.write(content.getBytes("utf-8"));
			out.flush();
			out.close();
			// 读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer("");
			int i = 0;
			while (true) 
			{
				i++;
				String lines = reader.readLine();
				if (lines == null || i == 100){
					break;
				}else{
					sb.append(lines);
				}
			}
			reader.close();
			// 断开连接

			return sb.toString();
		} catch (Exception ex) {
			System.out.println("URLPost异常");
			return null;
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
	}

	/*private static void trustAllHttpsCertificates() throws Exception {
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
				.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc
				.getSocketFactory());
	}*/

	/*@Entity
	static class miTM implements javax.net.ssl.TrustManager,
			javax.net.ssl.X509TrustManager {
		@Override
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean isServerTrusted(
				java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public boolean isClientTrusted(
				java.security.cert.X509Certificate[] certs) {
			return true;
		}

		@Override
		public void checkServerTrusted(
				java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}

		@Override
		public void checkClientTrusted(
				java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}
	}
	
	public static void main(String [] s){
		Base64.decode("eyJVSUQiOjI5NDA5NDk2NjZ9");
		String data = "%7B%22money%22%3A10%2C%22roleId%22%3A1%2C%22userId%22%3A%22kS9WubDzTkCYfwF93gO-Dg%22%7D";
		data = URLDecoder.decode(data);
		System.out.println(new String(Base64.decode("eyJVSUQiOjI5NDA5NDk2NjZ9")));
	}*/
}
