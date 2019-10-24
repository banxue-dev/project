package com.banxue.utils;

import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    public static boolean validateData(String data){
		try {
			JSONArray array = JSONArray.parseArray(URLDecoder.decode(data,"utf-8"));
			for(int i=0;i<array.size();i++){
				JSONObject json = array.getJSONObject(i);
				if(json.containsKey("x") 
					&& json.containsKey("y")
					&& json.containsKey("width")
					&& json.containsKey("height")
					&& json.containsKey("rotation")
					&& json.containsKey("rightAngel")
					&& json.containsKey("id")){
					continue;
				}else{
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
    public static void test211(String[] args) {
    	String json="{'data': [{'billPickNo': '101CBDO190800003-002-01','billModelCode': [{'busModuleCode': 'B00600000000', 'busModuleName': '照片', 'billModelId': 123}],'errCode': '0'}]}";
    	JSONObject jb=JSONObject.parseObject(json);
    	JSONArray ja = jb.getJSONArray("data");
		for (int i = 0; i < ja.size(); i++) {
			JSONObject tjb = ja.getJSONObject(i);
			Iterator it= tjb.keySet().iterator();
			while(it.hasNext()) {
				System.out.println(it.next());
			}
			String model=tjb.getString("billModelCode");
			System.out.println("拿到了"+model);
		}
    }
    public static void main(String[] args) {
    	
    	/*Random ra =new Random();
    	for (int i=0;i<30;i++)
    	{System.out.println(ra.nextInt(2));}*/
    	Integer[] s=getTargetRandomNumber(5, 19, 0);
    	for(Integer t:s) {
    		System.out.println(t);
    	}
    }
    public static Integer[] getTargetRandomNumber(int sum,int max,int min) {
		Integer[] res=new Integer[sum];
		List<Integer> lst=new ArrayList<Integer>();
		Random ra =new Random();
		int c=max-min;
		int s=0;
		if(max<min || c<sum) {
			for(int i=0;i<sum;i++) {
				res[i]=max;
			}
		}else if(max>min && c>sum) {
			while(lst.size()<sum) {
				/**
				 * 由于nextInt（s）得到的数字是0~s-1之间的数字，所有这里要c+1
				 */
				int x=min+ra.nextInt(c+1);
				if(lst.indexOf(x)==-1) {
					/**
					 * 不在里面才添加
					 */
					lst.add(x);
					res[s]=x;
					s++;
				}
			}
			
		}
		return res;
	}
    public static int  create(List<Integer> whe,Integer max) {
    	for(Integer i:whe) {
    		if(i==2 || i==6) {
    			List<Integer> we=new ArrayList<Integer> ();
    			we.add(1);
    			we.add(3);
    			create(we,max);
    			max+=we.size();
    		}else {
    			System.out.println(++max);
    		}
    	}
    	return max;
    }
    public void grepd() {
//    	"([0-9A-Za-z]*-([S]?|[0-9]){3}-[0-9]{2})"
    	 Pattern p = Pattern.compile("(group[0-9]/[0-9A-Za-z/]{0,}[0-9A-Za-z]*[-0-9A-Za-z]{0,}.[A-Za-z]{3,4})");// 匹配票据编号正则表达式
//    	 Pattern p = Pattern.compile("(group[0-9]/[0-9A-Za-z]*/[0-9A-Za-z]*/[0-9A-Za-z]*/[0-9A-Za-z]*.[A-Za-z]{3,4})");// 匹配票据编号正则表达式
//    	 Pattern p = Pattern.compile("(group[0-9]/[0-9A-Za-z]*/[0-9A-Za-z]*/[0-9A-Za-z]*/[0-9A-Za-z]*.pdf)");// 匹配票据编号正则表达式
    	 String result="[{'name':'成都市科技金融资助管理办法.pdf','url':'group1/M00/44/CgREB109WjqAGIN-AAI6Z8ksnmI103.pdf'},{'name':'贷款审核待审核.xls','url':'group1/M00/4D/44/CgREB109YlCADIh9AAAkANBaShQ385.xls'},{'name':'成都市科技金融资助管理办法.pdf','url':'group1/M00/4D/44/CgREB109Yw-AUDYlAAI6Z8ksnmI551.pdf'}]";
    	Matcher m = p.matcher(result);
    	while (m.find()) { 
    		System.out.println(m.group());
    	}
    }
    public static void ocrtest() {
    	try {
	    	String base64data="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";
			JSONObject jsonParam=new JSONObject();
			JSONObject imgList=new JSONObject();
			imgList.put("imgName", base64data);
			jsonParam.put("request_uuid", UUID.randomUUID());
			jsonParam.put("image_list", imgList);
			String url = "http://119.3.1.246:7790/ocr/v1/textrecognise/";
//			String url = "http://10.4.208.53:7790/recognise/";
			HttpPost httpPost = new HttpPost(url);
			CloseableHttpClient client = HttpClients.createDefault();
			String respContent = null;
	
			/**
			 * 确认是否有参数
			 */
			StringEntity postingString;
				postingString = new StringEntity(jsonParam.toString());
			httpPost.setEntity(postingString);
			httpPost.setHeader("Content-type", "application/json");
	
			HttpResponse resp = client.execute(httpPost);
			if (resp.getStatusLine().getStatusCode() == 200) {
				HttpEntity he = resp.getEntity();
				respContent = EntityUtils.toString(he, "UTF-8");
			}
			client.close();
			System.out.println("返回状态：" + respContent);
			httpPost.abort();
		} catch (Exception e) {
			// TODO 打印输出日志
			e.printStackTrace();
		}// json传递
    }
    public static void toptest() {
    	try {
    		String ids="1";
			JSONObject jsonParam=new JSONObject();
			jsonParam.put("orderId", 135573);
			jsonParam.put("addr", "orderToTop");
			jsonParam.put("token", "ba83fb8d93f10ec34249bf01febb9992");
			jsonParam.put("date", "Tue, 24 Sep 2019 10:56:19 GMT");
			String url = "https://ysx.sunscloud.com/csc-administration/ServiceServlet";
//			String url = "http://10.4.208.53:7790/recognise/";
			HttpPost httpPost = new HttpPost(url);
			HttpClient client = HttpClients.createDefault();
			sslClient(client);
			String respContent = null;
	
			/**
			 * 确认是否有参数
			 */
			StringEntity postingString;
				postingString = new StringEntity(jsonParam.toString());
			httpPost.setEntity(postingString);
			httpPost.setHeader("Content-type", "application/json");
	
			HttpResponse resp = client.execute(httpPost);
			if (resp.getStatusLine().getStatusCode() == 200) {
				HttpEntity he = resp.getEntity();
				respContent = EntityUtils.toString(he, "UTF-8");
			}
			System.out.println("返回状态：" + respContent);
			httpPost.abort();
		} catch (Exception e) {
			// TODO 打印输出日志
			e.printStackTrace();
		}// json传递
    }
    private static void sslClient(HttpClient httpClient) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] xcs, String str) {
                	
                }
                public void checkServerTrusted(X509Certificate[] xcs, String str) {
                	
                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry registry = ccm.getSchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
        	throw new RuntimeException(ex);
        }
    }
}
