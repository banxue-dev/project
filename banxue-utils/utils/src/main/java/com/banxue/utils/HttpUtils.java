package com.banxue.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.banxue.utils.pay.wex.WxPayConfig;

public class HttpUtils {
	
	public static String CONTENTYPE_APPLICTIONJSON="application/json;charset=utf-8";
	
	public static String post(String url,JSONObject jsonParam) throws Exception {

        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        String respContent = null;

        StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");//解决中文乱码问题    
        httpPost.addHeader("Content-type",CONTENTYPE_APPLICTIONJSON);  
        httpPost.setHeader("Accept", "application/json");  
        httpPost.setEntity(entity);

        HttpResponse resp = client.execute(httpPost);
        if(resp.getStatusLine().getStatusCode() == 200) {
            HttpEntity he = resp.getEntity();
            respContent = EntityUtils.toString(he,"UTF-8");
        }
        return respContent;
    }
	public static String test(String url,JSONObject jsonParam) throws Exception {
		
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;
		
		//StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");//解决中文乱码问题    
		httpPost.addHeader("Content-type",CONTENTYPE_APPLICTIONJSON);  
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
		
		//httpPost.setEntity(entity);
		
		HttpResponse resp = client.execute(httpPost);
		if(resp.getStatusLine().getStatusCode() == 200) {
			HttpEntity he = resp.getEntity();
			respContent = EntityUtils.toString(he,"UTF-8");
		}
		return respContent;
	}
	public static String sendGET(String url, String param) {
        String result = "";// 访问返回结果
        BufferedReader read = null;// 读取访问结果
 
        try {
            // 创建url
        	URL realurl ;
        	if(StringUtils.isNullString(param)) {
        		realurl= new URL(url);
        	}else {
        		
        		realurl= new URL(url + "?" + param);
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
	public static String getReString(String url) throws Exception {
		
		HttpGet httpGet=new HttpGet(url);
		
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;
		
		
		HttpResponse resp = client.execute(httpGet);
		if(resp.getStatusLine().getStatusCode() == 200) {
			HttpEntity he = resp.getEntity();
			respContent = EntityUtils.toString(he,"UTF-8");
		}
		return respContent;
	}
	public static JSONObject getReObject(String url) throws Exception {
		JSONObject result=new JSONObject();
		HttpGet httpGet=new HttpGet(url);
		
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;
		
		
		HttpResponse resp = client.execute(httpGet);
		if(resp.getStatusLine().getStatusCode() == 200) {
			HttpEntity he = resp.getEntity();
			respContent = EntityUtils.toString(he,"UTF-8");
			result=JSONObject.parseObject(respContent);
		}
		
		return result;
	}
	
}
