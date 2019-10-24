package com.banxue.utils;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;
/**
作者：fengchase
时间：2019年5月14日
文件：MyX509TrustManager.java
项目：utils
6用于https访问时，忽略证书
*/
public class MyX509TrustManager implements X509TrustManager {
 
    @Override
    public void checkClientTrusted(X509Certificate certificates[],String authType) throws CertificateException {
    }
 
    @Override
    public void checkServerTrusted(X509Certificate[] ax509certificate,String s) throws CertificateException {
    }
 
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        // TODO Auto-generated method stub
        return null;
    }
}