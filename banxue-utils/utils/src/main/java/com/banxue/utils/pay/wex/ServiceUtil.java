package com.banxue.utils.pay.wex;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.banxue.utils.HttpUtils;
import com.banxue.utils.log.FileLog;
import com.banxue.utils.wx.StrXmlToMap;
import com.banxue.utils.wx.WxUtils;

/**
作者：fengchase
时间：2018年7月24日
文件：ServiceUtil.java
项目：banxue-backend
*/
/**
 * 自定义类，在官方文档中没有
 * 
 *
 */
public class ServiceUtil {

	/**
	 * 第二步：通过code换取网页授权access_token
	 * 根据授权码code获取access_token，参考：http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html#.E7.AC.AC.E4.BA.8C.E6.AD.A5.EF.BC.9A.E9.80.9A.E8.BF.87code.E6.8D.A2.E5.8F.96.E7.BD.91.E9.A1.B5.E6.8E.88.E6.9D.83access_token
	 */
	public static JSONObject getOpenId(String code) {
		String openParam = "appid=" + WxUtils.appId + "&secret=" + WxUtils.appsecret + "&code=" + code
				+ "&grant_type=authorization_code";
		String openJsonStr = HttpUtils.sendGET("https://api.weixin.qq.com/sns/oauth2/access_token", openParam);
		FileLog.debugLog("openJsonStr:" + openJsonStr);
		
		// 获取openid
		JSONObject openJson = JSONObject.parseObject(openJsonStr);
//		String errcode=openJson.getString("errcode");
		// String openid = openJson.getString("openid");
		// String access_token = openJson.getString("access_token");
		return openJson;
	}

	/**
	 * 获取微信u用户信息
	 * @param acc_token
	 * @param openid
	 * @param code 
	 * @param count 获取的次数，避免多次无效获取
	 * @return
	 * 2018年8月6日
	 * 作者：fengchase
	 */
	public static JSONObject getUserInfo(String acc_token, String openid,String code,int count) {
		/*if(count>1) {
			//最多重新获取一次
			return null;
		}else {
			count++;
		}*/
		String openParam = "access_token=" + acc_token + "&openid=" + openid + "&lang=zh_CN";
		String openJsonStr = HttpUtils.sendGET("https://api.weixin.qq.com/sns/userinfo", openParam);
		System.out.println("openJsonStr1:" + openJsonStr);
		JSONObject openJson = JSONObject.parseObject(openJsonStr);
		String errcode=openJson.getString("errcode");
		if(errcode!="0" || !"0".equals(errcode)) {
			return null;
		}
		/*if(errcode=="41001" || "41001".equals(errcode)) {
			//再此获取。
			JSONObject temp=getOpenId(code);
			if(temp==null) {
				return null;
			}
			getUserInfo(temp.getString("access_token"),temp.getString("openid"),code,count);
		}*/
		return openJson;
	}

	/**
	 * 统一下单接口 参考文档：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String unifiedorder(String requestXml) throws Exception {
		// 统一下单接口提交 xml格式
		URL orderUrl = new URL(WxPayConfig.UNIFIED_ORDER_URL);
		HttpURLConnection conn = (HttpURLConnection) orderUrl.openConnection();
		conn.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒)
		conn.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒)
		conn.setDoOutput(true); // post请求参数要放在http正文内，顾设置成true，默认是false
		conn.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true
		conn.setUseCaches(false); // Post 请求不能使用缓存
		// 设定传送的内容类型是可序列化的java对象(如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestMethod("POST");// 设定请求的方法为"POST"，默认是GET
		conn.setRequestProperty("Content-Length", requestXml.length() + "");
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), WxPayConfig.CHARTSET);
		out.write(requestXml.toString());
		out.flush();
		out.close();
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			return null;
		}
		// 获取响应内容体
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), WxPayConfig.CHARTSET));
		String line = "";
		StringBuffer strBuf = new StringBuffer();
		while ((line = in.readLine()) != null) {
			strBuf.append(line).append("\n");
		}
		in.close();
		System.out.println("result=========返回的xml=============" + strBuf.toString());
		Map<String, String> orderMap = StrXmlToMap.strXmltoMap(strBuf.toString());
		System.out.println("orderMap===========================" + orderMap);

		// 获取
		String prepay_id = orderMap.get("prepay_id");
		return prepay_id;
	}
}
