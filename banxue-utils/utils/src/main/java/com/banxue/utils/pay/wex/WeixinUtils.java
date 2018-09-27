package com.banxue.utils.pay.wex;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

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
        String sign =  MD5Utils.MD5Encode(sb.toString(), WxPayConfig.CHARTSET).toUpperCase();
        return sign;
    }
	
}
