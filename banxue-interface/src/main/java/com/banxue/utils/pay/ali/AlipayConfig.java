package com.banxue.utils.pay.ali;
/**
作者：fengchase
时间：2018年7月17日
文件：AlipayConfig.java
项目：banxue-backend
*/
public class AlipayConfig {
    // 1.商户appid
    public static String APPID = "20170812********";    
    
    // 2.私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY ="";
   
    
    // 3.支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "";
    
    // 4.服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://www.xxx.com/alipay/notify_url.do";
    
    // 5.页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://www.xxx.com/alipay/return_url.do";
    
    // 6.请求网关地址
    public static String URL = "https://openapi.alipay.com/gateway.do";    
    
    // 7.编码
    public static String CHARSET = "UTF-8";
    
    // 8.返回格式
    public static String FORMAT = "json";
    
    // 9.加密类型
    public static String SIGNTYPE = "RSA2";
    
    
}

