package com.banxue;

import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



//@RunWith(SpringRunner.class)
//@SpringBootTest
public class BanxueBackendApplicationTests {

	@Test
	public void contextLoads() {
		Map<String, String[]> parameterMap = new HashMap<String, String[]>();
		parameterMap.put("id", new String[]{"100000620181011689675617"});
//		parameterMap.put("token", new String[]{"82be9fb928ffb21c4e563a88f77db367"});
//		parameterMap.put("fileType", new String[]{"1"});
		parameterMap.put("loginName", new String[]{"15281063580"});
		parameterMap.put("platform", new String[]{"1000006"});
		parameterMap.put("pwd", new String[]{"Fwoeq5IWC+vaYSjo5oeSdQAAAAs="});
//		parameterMap.put("sign", new String[]{"36430286320475bf83258074f50ad44e"});

//		String ss = buildQueryString(parameterMap);
//		String sign=vaildSign(ss,"2a246987299bc240ed462cfe4eeec922");
////		String sign=vaildSign("id=100000620181011748801195&loginName=15281063580&platform=1000006&pwd=Fwoeq5IWC+vaYSjo5oeSdQAAAAs=","2a246987299bc240ed462cfe4eeec922");
//		System.out.println("----"+sign);
		String t="Fwoeq5IWC+vaYSjo5oeSdQAAAAs=";
		try {
//			System.out.println(MD5Encode(t, "utf-8"));
		} catch (Exception e) {
			// TODO 打印输出日志
			e.printStackTrace();
		}
	}
	public void contextLoads2() {
		Map<String, String[]> parameterMap = new HashMap<String, String[]>();
		parameterMap.put("id", new String[]{"100000620181011285237747"});
//		parameterMap.put("token", new String[]{"82be9fb928ffb21c4e563a88f77db367"});
//		parameterMap.put("fileType", new String[]{"1"});
		parameterMap.put("loginName", new String[]{"13880680467"});
		parameterMap.put("pwd", new String[]{"%2FG4cwnTCbPeleuvURFBPLQAAAAg%3D"});
//		parameterMap.put("sign", new String[]{"36430286320475bf83258074f50ad44e"});
		parameterMap.put("platform", new String[]{"1000006"});
		String ss = buildQueryString(parameterMap);
		String sign=vaildSign(ss,"2a246987299bc240ed462cfe4eeec922");
		System.out.println(sign);
	}
	  /**
     * request的ParameterMap中读取参数并按ASCII由小到大排序拼接成参数字符串
     * @param parameterMap 参数Map
     * @return 参数字符串
     */
    public static String buildQueryString(Map<String, String[]> parameterMap){
        String sign = "";
        StringBuilder sb = new StringBuilder();
        //有序参数Map
        SortedMap<String, String> map = new TreeMap<String, String>();
        try{
            //request中无序ParameterMap存入有序Map中
            Set<Entry<String, String[]>> es = parameterMap.entrySet();
            Iterator<Entry<String, String[]>> it = es.iterator();
            while(it.hasNext()) {
                Map.Entry<String, String[]> entry = it.next();
                String k = entry.getKey();
                String v = entry.getValue()[0];
                if(v==null){
                    v="";
                }
                if(!"sign".equals(k)){
                    map.put(k, v);
                }else{
                    sign = v;
                }               
            }
            
            //拼接参数字符串
            Set<Entry<String, String>> set = map.entrySet();
            Iterator<Entry<String, String>> ite = set.iterator();
            while(ite.hasNext()) {
                Map.Entry<String, String> entry = ite.next();
                String k = entry.getKey();
                String v = entry.getValue();
                //TODO,编码修改
                sb.append(k + "=" + v+ "&");
            }
            
            sb.append("sign=");
            sb.append(sign); 
        } catch (Exception e) {
        }
        return sb.toString();
    }
	  /**
     * 获取验证签名字符串
     * @param requestPars 请求参数
     * @param key 秘钥
     * @return 验证签名字符串
     */
    public static String vaildSign(String requestPars,String key){
        String newSign = null;
        try{
            //去除sign参数（uri格式：xxx?id=xxx&sign=xxx）
            int end = requestPars.indexOf("&sign=");
            if(end > 0){
                requestPars = requestPars.substring(0, end);
            }else{
                //uri格式：xxx?sign=xxx&id=xxx
                end = requestPars.indexOf("sign=");
                requestPars = requestPars.substring(38);
            }
            requestPars = requestPars.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
            requestPars = requestPars.replaceAll("\\+", "%2B");
            String str = URLDecoder.decode(requestPars, "UTF-8").replaceAll("\\+", "%2B")+"&key="+key;
            System.out.println("--"+str);
//          String str = requestPars +"&key="+key;
            newSign = MD5Encode(str,"UTF-8");
        } catch (Exception e) {
        }
        return newSign;
    }
    
    public static String MD5Encode(String src, String charset) throws Exception {
        MessageDigest md5 = null;
        md5 = MessageDigest.getInstance("MD5");

        byte[] byteArray = null;
        byteArray = src.getBytes(charset);
    
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder(32);
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
