package com.banxue.utils.pay.pojo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.banxue.utils.MD5Utils;
import com.banxue.utils.pay.wex.WxPayConfig;

/**
作者：fengchase
时间：2018年9月14日
文件：RequestHander.java
项目：banxue
*/
public class RequestHandler {


    
   /** ���url��ַ */
   private String gateUrl;
    
   /** ��Կ */
   private String key;
    
   /** ����Ĳ��� */
   private SortedMap parameters;
    
   /** debug��Ϣ */
   private String debugInfo;
    
    
   /**
    * ���캯��
    * @param request
    * @param response
    */
   public RequestHandler() {
      
       this.gateUrl = "https://gw.tenpay.com/gateway/pay.htm";
       this.key = "";
       this.parameters = new TreeMap();
       this.debugInfo = "";
   }
    
   /**
   *
   */
   public void init() {
       //nothing to do
   }

   /**
   *ֵ
   */
   public String getGateUrl() {
       return gateUrl;
   }

   /**
   *ֵ
   */
   public void setGateUrl(String gateUrl) {
       this.gateUrl = gateUrl;
   }

   /**
   *
   */
   public String getKey() {
       return key;
   }

   /**
   *
   */
   public void setKey(String key) {
       this.key = key;
   }
    
   /**
    * ֵ
    * @param parameter 
    * @return String 
    */
   public String getParameter(String parameter) {
       String s = (String)this.parameters.get(parameter); 
       return (null == s) ? "" : s;
   }
    
   /**
    * ֵ
    * @param parameter 
    * @param parameterValue ֵ
    */
   public void setParameter(String parameter, String parameterValue) {
       String v = "";
       if(null != parameterValue) {
           v = parameterValue.trim();
       }
       this.parameters.put(parameter, v);
   }
    
   /**
    * 
    * @return SortedMap
    */
   public SortedMap getAllParameters() {       
       return this.parameters;
   }

   /**
   *��ȡdebug��Ϣ
   */
   public String getDebugInfo() {
       return debugInfo;
   }
    
   /**
    * ��ȡ����������URL
    * @return String
    * @throws UnsupportedEncodingException 
    */
   public String getRequestURL() throws UnsupportedEncodingException {
        
       this.createSign();
        
       StringBuffer sb = new StringBuffer();
       Set es = this.parameters.entrySet();
       Iterator it = es.iterator();
       while(it.hasNext()) {
           Map.Entry entry = (Map.Entry)it.next();
           String k = (String)entry.getKey();
           String v = (String)entry.getValue();
            
           if(!"spbill_create_ip".equals(k)) {
               sb.append(k + "=" + URLEncoder.encode(v, WxPayConfig.CHARTSET) + "&");
           } else {
               sb.append(k + "=" + v.replace("\\.", "%2E") + "&");
           }
       }
        
       //ȥ�����һ��&
       String reqPars = sb.substring(0, sb.lastIndexOf("&"));
        
       return this.getGateUrl() + "?" + reqPars;
        
   }
    
    
   /**
    * ����md5ժҪ,������:���������a-z����,������ֵ�Ĳ���μ�ǩ��
    */
   protected void createSign() {
       StringBuffer sb = new StringBuffer();
       Set es = this.parameters.entrySet();
       Iterator it = es.iterator();
       while(it.hasNext()) {
           Map.Entry entry = (Map.Entry)it.next();
           String k = (String)entry.getKey();
           String v = (String)entry.getValue();
           if(null != v && !"".equals(v) 
                   && !"sign".equals(k) && !"key".equals(k)) {
               sb.append(k + "=" + v + "&");
           }
       }
       sb.append("key=" + WxPayConfig.KEY);
       System.out.println(sb.toString());
       String sign = MD5Utils.MD5Encode(sb.toString(), WxPayConfig.CHARTSET).toUpperCase();
        
       this.setParameter("sign", sign);
        
       //debug
       this.setDebugInfo(sb.toString() + " => sign:" + sign);
        
   }
    
   /**
   *
   */
   protected void setDebugInfo(String debugInfo) {
       this.debugInfo = debugInfo;
   }
    
    
   /**
    * 自定义函数，官方没有
    * @param return_code
    * @param return_msg
    * @return
    */
   public static String setXML(String return_code, String return_msg) {
       return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
               + "]]></return_msg></xml>";
   }
    
   /**
    * 自定义函数，在官方文档中没有
    * @return
    * @throws UnsupportedEncodingException
    */
   public String getRequestXml() throws UnsupportedEncodingException {
       this.createSign();
        
       StringBuffer sb = new StringBuffer();
       sb.append("<xml>");
       Set es = this.parameters.entrySet();
       Iterator it = es.iterator();
       while (it.hasNext()) {
           Map.Entry entry = (Map.Entry) it.next();
           String k = (String) entry.getKey();
           String v = (String) entry.getValue();
           if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
               sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
           } else {
               sb.append("<" + k + ">" + v + "</" + k + ">");
           }
       }

       sb.append("</xml>");
       return sb.toString();
   }
}

