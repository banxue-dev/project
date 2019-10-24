package com.banxue.utils.pay.wex;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;


import com.alibaba.fastjson.JSONObject;
import com.banxue.utils.DateUtils;
import com.banxue.utils.HttpUtils;
import com.banxue.utils.MD5Utils;

/**
作者：fengchase
时间：2018年7月23日
文件：WeixinUtils.java
项目：banxue-backend
*/
public class WeixinUtils {

   
    /**
     * 获取微信签名
     * @param map 请求参数集合
     * @return 微信请求签名串
     */
    @SuppressWarnings("rawtypes")
	public static String createSign(SortedMap<Object, Object> parameters, String key) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
       System.out.println("加密之前的sign"+sb.toString());
        String sign =  MD5Utils.MD5Encode(sb.toString(), WxPayConfig.CHARTSET).toUpperCase();
        return sign;
    }
    public static void main(String[] args) {
    	testhttp();
		
	}
    public static void testhttp() {
		JSONObject jb=new JSONObject();
		jb.put("loginName", "test");
		jb.put("password", "123456");
		String result="";
		try {
			result = HttpUtils.sendGetByHttps("https://orion-ticket-check.changhong.com/ticket/getTicketInfo", "name="+URLEncoder.encode("漆尧", "UTF-8")+"&ticketNum=8762116150653");
//			result = HttpUtils.sendHtpps("aaa", "https://orion-ticket-check.changhong.com/ticket/getTicketInfo?name=漆尧&ticketNum=8762116150653");
//			result = HttpUtils.postByJson("http://www.banxue.fun:10100/api/login", jb);
		} catch (Exception e) {
			// TODO 打印输出日志
			e.printStackTrace();
		}
		System.out.println(result);
	}
    public static void testposthttp(){
    	SortedMap<Object, Object> signMap = new TreeMap<Object, Object>();
		signMap.put("invoiceType", "12");
		signMap.put("invoiceCode", "12");
		signMap.put("invoiceNumber", "12");
		signMap.put("printDate", "2018-05-06");
		signMap.put("totalAmount", "12");
		signMap.put("checkDigit", "12");
		signMap.put("requestId", getRequestId("3653425"));
		String str=createSign(signMap, "641d44c935c3700d5a25959eefbc7ef9");
		signMap.put("sign", str);
		signMap.put("alg","md5");
		System.out.println(str);
		JSONObject jsonParam=new JSONObject();
		Iterator<Object> it = signMap.keySet().iterator();
		// 遍历获取参数key名
		while (it.hasNext()) {
			String tkey = (String) it.next();
			jsonParam.put(tkey, signMap.get(tkey).toString());
		}
		System.out.println(jsonParam.toString());
		try {
			String result=HttpUtils.postByJson("https://bills.chfcloud.com/openapi/FaasPublic/Invoice/CheckResult", jsonParam);
			System.out.println(result);
		} catch (Exception e) {
			// TODO 打印输出日志
			e.printStackTrace();
		}
    }
    /**
     * 发票云电子数据源获取requestid
     * @param appKey
     * @return
     * 2019年5月8日
     * 作者：fengchase
     */
    public static String getRequestId(String appKey) {
    	String requestId=appKey+DateUtils.format(new Date(), "yyyyMMdd")+DateUtils.format(new Date(), "ddHHmmsss");
    	return requestId;
    }
	
}
