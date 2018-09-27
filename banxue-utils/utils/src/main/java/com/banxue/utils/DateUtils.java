package com.banxue.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理
 */
public class DateUtils {
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间戳格式
     */
    public final static String DATE_TIME_STAMP = "yyyyMMddHHmmss";
    public final static String DATE_TIME_STAMP_MS = "yyyyMMddHHmmssSSS";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
    public static String getOrderNo() {
    	return "QC01"+format(new Date(), DATE_TIME_STAMP_MS);
    }
    
    public static Date stringToDate(String sdate) {
    	SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_PATTERN);
    	try {
			return df.parse(sdate);
		} catch (ParseException e) {
			// TODO 打印输出日志
			e.printStackTrace();
		}
    	return null;
    }

    /**
     * 计算距离现在多久，非精确
     *
     * @param date
     * @return
     */
    public static String getTimeBefore(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        } else if (hour > 0) {
            r += hour + "小时";
        } else if (min > 0) {
            r += min + "分";
        } else if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }

    /**
     * 计算距离现在多久，精确
     *
     * @param date
     * @return
     */
    public static String getTimeBeforeAccurate(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        }
        if (hour > 0) {
            r += hour + "小时";
        }
        if (min > 0) {
            r += min + "分";
        }
        if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }
    /**
     * 比较两个时间相差的毫秒数是否在参数的时间内。
     * @param start 起始时间
     * @param end 结束时间
     * @param much 秒
     * 2018年7月17日
     * 作者：fengchase
     */
    public static boolean compareTwoTimeDifferMuch(Date start,Date end,int much) {
    	long l=much*1000;//转为毫秒
    	long diff=end.getTime()-start.getTime();
    	//如果差值小于设定值，说明在可控时间内。
    	if(diff<l) {
    		return true; 
    	}
    	return false;
    }
    /**
     * 获取当前时间的毫秒书
     * @return
     * 2018年7月24日
     * 作者：fengchase
     */
    public static String getNowTimestammp() {
    	 return String.valueOf(System.currentTimeMillis() / 1000);
    }
}
