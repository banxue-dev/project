package com.banxue.utils;
/**
作者：fengchase
时间：2018年7月11日
文件：Constants.java
项目：banxue-backend
*/
public class Constants {
	
	public static String[] ORDERSTATE= {"初始","支付中","已支付","完成","失效"};
	public static String ValidteCodeName="me_s_code";//短信验证码session字段名
	public static String CODETIMEKEY="me_s_time";//短信验证码超时时间的字段名
	public static long VCODTIMEOUT=60;//短信验证码超时时间
	public static int VCODELENGTH=6;//短信验证码长度
	public static String HeadUrlHead="http://127.0.0.1:8085/user/getHead?headName=";//头像地址头
}

