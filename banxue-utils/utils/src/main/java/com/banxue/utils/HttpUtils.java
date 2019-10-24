package com.banxue.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.banxue.utils.log.FileLog;
import com.banxue.utils.pay.wex.WxPayConfig;

public class HttpUtils {

	public static String CONTENTYPE_APPLICTIONJSON = "application/json;charset=utf-8";

	public static String post(String url, JSONObject jsonParam) throws Exception {

		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;

		Iterator<String> it = jsonParam.keySet().iterator();
		/**
		 * 确认是否有参数
		 */
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		// 遍历获取参数key名
		while (it.hasNext()) {
			String tkey = it.next();
			nvps.add(new BasicNameValuePair(tkey, jsonParam.get(tkey).toString()));// 参数
		}
		UrlEncodedFormEntity u = new UrlEncodedFormEntity(nvps, "UTF-8");
		httpPost.setEntity(u);

		HttpResponse resp = client.execute(httpPost);
		if (resp.getStatusLine().getStatusCode() == 200) {
			HttpEntity he = resp.getEntity();
			respContent = EntityUtils.toString(he, "UTF-8");
		}
		client.close();
		FileLog.debugLog("返回状态：" + resp.getStatusLine().getStatusCode());
		return respContent;
	}

	public static String postByJSON(String url, JSONObject jsonParam) throws Exception {

		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;

		/**
		 * 确认是否有参数
		 */
		StringEntity postingString = new StringEntity(jsonParam.toString());// json传递
		httpPost.setEntity(postingString);
		httpPost.setHeader("Content-type", "application/json");

		HttpResponse resp = client.execute(httpPost);
		if (resp.getStatusLine().getStatusCode() == 200) {
			HttpEntity he = resp.getEntity();
			respContent = EntityUtils.toString(he, "UTF-8");
		}
		client.close();
		FileLog.debugLog("返回状态：" + resp.getStatusLine().getStatusCode());
		return respContent;
	}

	/**
	 * json格式的参数请求
	 * 
	 * @param url
	 * @param jsonParam
	 * @return
	 * @throws Exception
	 *             2019年5月8日 作者：fengchase
	 */
	public static String postBySortMap(String url, SortedMap<Object, Object> jsonParam) throws Exception {

		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;
		/**
		 * 确认是否有参数
		 */
		StringEntity postingString = new StringEntity(jsonParam.toString(), "utf-8");// json传递
		httpPost.setEntity(postingString);
		httpPost.setHeader("Content-type", "application/json");
		HttpResponse resp = client.execute(httpPost);
		if (resp.getStatusLine().getStatusCode() == 200) {
			HttpEntity he = resp.getEntity();
			respContent = EntityUtils.toString(he, "UTF-8");
		}
		client.close();
		FileLog.debugLog("返回状态：" + resp.getStatusLine().getStatusCode());
		return respContent;
	}

	public static String postByJson(String url, JSONObject jsonParam) throws Exception {

		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;
		/**
		 * 确认是否有参数
		 */
		StringEntity entity = new StringEntity(jsonParam.toJSONString(), "utf-8");// 解决中文乱码问题
		httpPost.addHeader("Content-type", CONTENTYPE_APPLICTIONJSON);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setEntity(entity);
		HttpResponse resp = client.execute(httpPost);
		HttpEntity he = resp.getEntity();
		respContent = EntityUtils.toString(he, "UTF-8");
		if (resp.getStatusLine().getStatusCode() == 200) {
		}
		return respContent;
	}

	public static String callpost(String url, JSONObject jsonParam, String Authorization) throws Exception {

		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;

		StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
		httpPost.addHeader("Content-type", CONTENTYPE_APPLICTIONJSON);
		httpPost.addHeader("Authorization", Authorization);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setEntity(entity);

		HttpResponse resp = client.execute(httpPost);
		if (resp.getStatusLine().getStatusCode() == 200) {
			HttpEntity he = resp.getEntity();
			respContent = EntityUtils.toString(he, "UTF-8");
		}
		return respContent;
	}

	public static String test(String url, JSONObject jsonParam) throws Exception {

		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;

		// StringEntity entity = new
		// StringEntity(jsonParam.toString(),"utf-8");//解决中文乱码问题
		httpPost.addHeader("Content-type", CONTENTYPE_APPLICTIONJSON);
		httpPost.setHeader("Accept", "*/*");
		httpPost.setHeader("X-Requested-With", "XMLHttpRequest");

		httpPost.setHeader("Accept-Encoding", "gzip, deflate");
		httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
		httpPost.setHeader("Cache-Control", "no-cache");
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Cookie", "PHPSESSID=k8r9196sov90tmk2jlp2bad5s0");
		httpPost.setHeader("Host", "47.93.119.90");
		httpPost.setHeader("Pragma", "no-cache");
		httpPost.setHeader("Refererh", "http://47.93.119.90/Mobile/index.html");
		httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
		httpPost.setHeader("X-Requested-With", "XMLHttpRequest");

		// httpPost.setEntity(entity);

		HttpResponse resp = client.execute(httpPost);
		if (resp.getStatusLine().getStatusCode() == 200) {
			HttpEntity he = resp.getEntity();
			respContent = EntityUtils.toString(he, "UTF-8");
		}
		return respContent;
	}

	/**
	 * 普通的get请求
	 */
	public static String sendGET(String url, String param) {
		String result = "";// 访问返回结果
		BufferedReader read = null;// 读取访问结果

		try {
			// 创建url
			URL realurl;
			if (StringUtils.isNullString(param)) {
				realurl = new URL(url);
			} else {

				realurl = new URL(url + "?" + param);
			}
			System.out.println(realurl);
			// 打开连接
			URLConnection connection = realurl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立连接
			connection.connect();
			// 获取所有响应头字段
			// Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段，获取到cookies等
			// for (String key : map.keySet()) {
			// System.out.println(key + "--->" + map.get(key));
			// }
			// 定义 BufferedReader输入流来读取URL的响应
			read = new BufferedReader(new InputStreamReader(connection.getInputStream(), WxPayConfig.CHARTSET));
			String line;// 循环读取
			while ((line = read.readLine()) != null) {
				result += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (read != null) {// 关闭流
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	/**
	 * https的get请求 会忽略证书
	 */
	public static String sendGetByHttps(String url, String param) {
		String result = "";// 访问返回结果
		BufferedReader read = null;// 读取访问结果

		try {
			// 创建url
			URL realurl;
			if (StringUtils.isNullString(param)) {
				realurl = new URL(url);
			} else {

				realurl = new URL(url + "?" + param);
			}
			SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
			sslcontext.init(null, new TrustManager[] {  new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return new java.security.cert.X509Certificate[] {};
				}

				public void checkClientTrusted(X509Certificate[] chain, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) {
				}
			}  }, new java.security.SecureRandom());
			HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
				public boolean verify(String s, SSLSession sslsession) {
					System.out.println("WARNING: Hostname is not matched for cert.");
					return true;
				}
			};
			HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
			HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
			System.out.println(realurl);
			HttpsURLConnection conn = (HttpsURLConnection) realurl.openConnection();
			conn.setSSLSocketFactory(sslcontext.getSocketFactory());
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			// 必须设置false，否则会自动redirect到重定向后的地址
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			result = getReturn(conn);

			return result;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (read != null) {// 关闭流
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	/* 请求url获取返回的内容 */
	public static String getReturn(HttpsURLConnection connection) throws IOException {
		StringBuffer buffer = new StringBuffer();
		// 将返回的输入流转换成字符串
		try (InputStream inputStream = connection.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			String result = buffer.toString();
			return result;
		}
	}

	/**
	 * get请求根据sordmap
	 */
	public static String getBySortMap(String url, SortedMap<Object, Object> signMap) {
		String result = "";// 访问返回结果
		BufferedReader read = null;// 读取访问结果

		try {
			// 创建url
			URL realurl;
			String param = "";
			Iterator<Object> it = signMap.keySet().iterator();
			// 遍历获取参数key名
			while (it.hasNext()) {
				String tkey = (String) it.next();
				String value = signMap.get(tkey).toString();
				param += tkey + "=" + value + "&";
			}
			if (StringUtils.isNullString(param)) {
				realurl = new URL(url);
			} else {
				param = param.substring(0, param.lastIndexOf("&"));
				realurl = new URL(url + "?" + param);
			}
			System.out.println(realurl);
			// 打开连接
			URLConnection connection = realurl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段，获取到cookies等
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			read = new BufferedReader(new InputStreamReader(connection.getInputStream(), WxPayConfig.CHARTSET));
			String line;// 循环读取
			while ((line = read.readLine()) != null) {
				result += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (read != null) {// 关闭流
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public static String getReString(String url) throws Exception {

		HttpGet httpGet = new HttpGet(url);

		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;

		HttpResponse resp = client.execute(httpGet);
		if (resp.getStatusLine().getStatusCode() == 200) {
			HttpEntity he = resp.getEntity();
			respContent = EntityUtils.toString(he, "UTF-8");
		}
		return respContent;
	}

	public static JSONObject getReObject(String url) throws Exception {
		JSONObject result = new JSONObject();
		HttpGet httpGet = new HttpGet(url);

		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;

		HttpResponse resp = client.execute(httpGet);
		if (resp.getStatusLine().getStatusCode() == 200) {
			HttpEntity he = resp.getEntity();
			respContent = EntityUtils.toString(he, "UTF-8");
			result = JSONObject.parseObject(respContent);
		}

		return result;
	}

	private final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};

	private static void trustAllHosts() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}

			public void checkClientTrusted(X509Certificate[] chain, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) {
			}
		} }; // Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String sendHtpps(String a, String url) {
		String result = "";
		OutputStreamWriter out = null;
		BufferedReader in = null;
		HttpURLConnection conn;
		try {
			trustAllHosts();
			URL realUrl = new URL(url); // 通过请求地址判断请求类型(http或者是https)
			if (realUrl.getProtocol().toLowerCase().equals("https")) {
				HttpsURLConnection https = (HttpsURLConnection) realUrl.openConnection();
				https.setHostnameVerifier(DO_NOT_VERIFY);
				conn = https;
			} else {
				conn = (HttpURLConnection) realUrl.openConnection();
			} // 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "text/plain;charset=utf-8"); // 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true); // 获取URLConnection对象对应的输出流

			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			out = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
			out.write(a); 
			// flush输出流的缓冲
			out.flush(); // 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {// 使用finally块来关闭输出流、输入流
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 获取HttpClient
	 * @return
	 */
	private static CloseableHttpClient getHttpClient(){
		HttpClientBuilder httpBuilder = HttpClientBuilder.create();
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(300*1000).setConnectTimeout(300*1000)
				.setSocketTimeout(300*1000)
				.build();
		httpBuilder.setDefaultRequestConfig(requestConfig);
		
		return httpBuilder.build();
	}
}
