package com.banxue.utils.log;

import java.util.Date;
import java.util.Random;

import com.banxue.utils.DateUtils;

/**
作者：fengchase
时间：2018年9月26日
文件：TheardLogidutil.java
项目：banxue-backend
*/

public class TheardLogidutil {
	
	private TheardLogidutil(){		
	}
	
	/**
	 * 生成ID，时间yyyyMMddHHmmssSSS加上3位随机数
	 * 
	 * @return
	 */
	private static String getLogid()
	{
		Random r = new Random();
		String rs = r.nextInt(1000) + "";
		int len = rs.length();
		//�?��数据上长，不足够3位时前面拼装0		
		//只有1位时
		if(len == 1)
		{
			rs = "00" + rs;
			//只有两位�?
		}
		else if(len == 2)
		{
			rs = "0" + rs;
		}
		return DateUtils.format(new Date(), DateUtils.DATE_TIME_STAMP) + rs;
	}

	/**
	 * 设置线程ID
	 */
	public static void set()
	{
		Thread.currentThread().setName(getLogid());
	}

	/**
	 * 获取当前线程ID
	 * 
	 * @return
	 */
	public static String getId()
	{
		return "Logid=" + Thread.currentThread().getName() + " ";
	}
}
