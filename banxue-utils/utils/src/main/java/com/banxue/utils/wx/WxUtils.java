package com.banxue.utils.wx;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;
import java.util.Random;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.banxue.utils.HttpUtils;
import com.banxue.utils.log.FileLog;
import com.banxue.utils.pay.wex.pojo.WxTokenDO;

/**
作者：fengchase
时间：2018年7月11日
文件：WxUtils.java
项目：banxue-backend
*/
public class WxUtils {
	
	public static final String appId="wx24afd57c69514a36";
	public static final String appsecret = "65279fdbde15b25e3129fcc70037b1a0";// 应用秘钥
	public static final String token_out_time = "7200000";// token超时时间单位：MS
	public static final String wxservicetoken="nuocar";//微信服务器交互的凭证token
	public static final String wxaeskey="nBzjNWYZam1g8j6u7KgrwJaoQ9Ld4EbT7xsvXfoSK97";//参与消息加解密的aeskey
	public static final String MesModelKey1="7XgP7C8HczbgAK-c_uK7tNWBkzW8nSNqawpjxgaAJLA";//挪车提醒消息模板
    // 各种消息类型,除了扫带二维码事件
    
	/**
     * 验证Token
     * @param msgSignature 签名串，对应URL参数的signature
     * @param timeStamp 时间戳，对应URL参数的timestamp
     * @param nonce 随机串，对应URL参数的nonce
     *
     * @return 是否为安全签名
     * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
     */
    public static boolean verifyUrl(String msgSignature, String timeStamp, String nonce)
            throws AesException {
        // 这里的 WXPublicConstants.TOKEN 填写你自己设置的Token就可以了
        String signature = SHA1.getSHA1(WxUtils.wxservicetoken, timeStamp, nonce);
        if (!signature.equals(msgSignature)) {
            throw new AesException(AesException.ValidateSignatureError);
        }
        return true;
    }
    
	/**
	 * 获取js_api的access_token
	 * @param appId
	 * @param appsecret
	 * @return  
	 * openId = openJson.getString("openid");
			access_token = openJson.getString("access_token");
	 * 2018年7月12日
	 * 作者：fengchase
	 */
	public static JSONObject geWxtAccessToken() {
		try {
			return HttpUtils
			.getReObject("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId
					+ "&secret=" + appsecret + "");
		} catch (Exception e) {
			// TODO 打印输出日志
			e.printStackTrace();
		}
		return new JSONObject();
	}
	/**
	 * 获取js调用凭据
	 * @param access_token
	 * @return ticket
	 * @throws Exception
	 * 2018年10月23日
	 * 作者：fengchase
	 */
	public static String getJsapiTicket(String access_token) throws Exception {
		String getticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=";// 接口凭据

		String jsonData = HttpUtils.getReString(getticket_url + access_token + "&type=jsapi");
		JSONObject jsonObj = JSONObject.parseObject(jsonData);
		System.out.println("ticket:"+jsonObj.toString());
		String errcode = jsonObj.getString("errcode");
		String ticket = null;
		if (errcode.equals("0")) {
			ticket = jsonObj.getString("ticket");
		}
		return ticket;
	}
	/***
	 * 获取界面调用jsapi的所需参数
	 * 
	 * @param jsapi_ticket
	 *            凭据
	 * @param url
	 *            界面请求地址
	 * @return 
	 */
	public static JSONObject sign(String jsapi_ticket, String url) {
		try {
			url=java.net.URLDecoder.decode(url,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO 打印输出日志
			e1.printStackTrace();
		}
		JSONObject ret = new JSONObject();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		System.out.println("生成sign的参数为："+string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
			FileLog.debugLog("signature:"+signature);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
//		FileLog.debugLog("jsapi_ticket:"+jsapi_ticket);
//		FileLog.debugLog("nonce_str:"+nonce_str);
//		FileLog.debugLog("timestamp:"+timestamp);
//		ret.put("url", url);
//		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("noncestr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		ret.put("appId", appId);
		

		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
	/**
	 * 验证token是否超时
	 * 
	 * 2018年7月12日
	 * 作者：fengchase
	 */
	public static Boolean validTokenOut(WxTokenDO tokenDo) {
		try{
			if(tokenDo==null || tokenDo.getAcessToken()==null) {
				return false;
			}
			//起始时间
			Long startTime=tokenDo.getCreateTime().getTime();
			//超时时间
			Long outTime=Long.parseLong(tokenDo.getOutTime());
			//当前时间
			Long nowTime=new Date().getTime();
			//如果当前时间-起始时间>=超时时间，表示超时，需要重新获取
			if(nowTime-startTime>=outTime) {
				return false;
			}
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// 随机生成16位字符串
	public static String getRandomStr() {
			String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
			Random random = new Random();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < 16; i++) {
				int number = random.nextInt(base.length());
				sb.append(base.charAt(number));
			}
			return sb.toString();
		}

}

