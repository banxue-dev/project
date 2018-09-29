package com.banxue.utils.pay.wex;
/**
作者：fengchase
时间：2018年7月23日
文件：WxPayConfig.java
项目：banxue-backend
*/
public class WxPayConfig {

    /**加密方式*/
    public static final String SIGN_TYPE = "MD5";
 
    /**微信支付商户号*/
    public static final String MCH_ID = "1510530611";
    /**编码 */
    public static String CHARTSET = "UTF-8";
 
    /**微信支付API秘钥*/
    public static final String KEY = "2082493C24C9480EAB052C3FF110AF57";
 
    /**微信支付api证书路径*/
    public static final String CERT_PATH = "***/apiclient_cert.p12";
 
    /**微信统一下单url*/
    public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
 
    /**微信申请退款url*/
    public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
 
    /**微信支付通知url*/
    public static final String NOTIFY_URL = "http://app.cdqckj.com/pay/wex/callBack";
 
    /**微信交易类型:公众号支付*/
    public static final String TRADE_TYPE_JSAPI = "JSAPI";
 
    /**微信交易类型:原生扫码支付*/
    public static final String TRADE_TYPE_NATIVE = "NATIVE";
 
    /**微信甲乙类型:APP支付*/
    public static final String TRADE_TYPE_APP = "APP";
}

