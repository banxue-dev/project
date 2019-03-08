package com.banxue.utils;

import java.util.Date;

/**
作者：fengchase
时间：2019年2月27日
文件：OrderNoCreater.java
项目：utils
*/
public class OrderNoCreater {
	
	public static synchronized String CreateOrderNo() {

	    return "BX00"+DateUtils.format(new Date(), DateUtils.DATE_TIME_STAMP_MS);
	}

}

