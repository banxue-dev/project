package com.banxue.utils;

/**
 * @author Admin
 */
public class StringUtils {
	/**
	 * 是否是空已经空字符串
	 * @param str
	 * @return
	 * 2018年8月1日
	 * 作者：fengchase
	 */
	public static boolean isNullString(String str) {
		if(str=="" || "".equals(str)) {
			return true;
		}
		return false;
	}
	/**
	 * 过滤非法字符
	 * @param strs
	 * @return
	 * 2018年10月10日
	 * 作者：fengchase
	 */
	public static boolean StrFilter(String... strs) {
		for(String str:strs) {
			if(str.contains(",")) {
				return true;
			}else if(str.contains("'")) {
				return true;
			}
		}
		return false;
	}
}
