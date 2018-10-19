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
		if( str==null ||str=="" || "".equals(str)) {
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
	/**
	 * 对比两个字符串是否相等，有任一为空则返回false
	 * @param str1
	 * @param str2
	 * @return
	 * 2018年10月15日
	 * 作者：fengchase
	 */
	public static boolean twoStrMatch(String str1,String str2) {
		if(str1==null || str2==null) {
			return false;
		}
		if(str1==str2 || str1.equals(str2)) {
			return true;
		}
		return false;
	}
	public static String HiddenPhone(String phone) {
		if(phone==null || phone.length()!=11) {
			return "";
		}
		String str1=phone.substring(0,3);
		String str2=phone.substring(7);
		return str1+"****"+str2;
	}
}
