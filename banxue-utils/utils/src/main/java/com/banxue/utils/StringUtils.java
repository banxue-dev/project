package com.banxue.utils;

/**
 * @author Admin
 */
public class StringUtils {
	/**
	 * 是否是空已经空字符串
	 * 
	 * @param str
	 * @return 2018年8月1日 作者：fengchase
	 */
	public static boolean isNullString(String str) {
		if (str == null || str == "" || "".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 校验string列表
	 * 
	 * @param str
	 * @return 2018年8月1日 作者：fengchase
	 */
	public static boolean isNullString(String... strs) {
		for (String str : strs) {
			if(isNullString(str)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 校验请求参数,返回true表示不合格
	 * 
	 * @param str
	 * @return 2018年8月1日 作者：fengchase
	 */
	public static boolean RequestParmsV(String... strs) {
		if(isNullString(strs) || StrFilter(strs)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 过滤非法字符
	 * false就是没有非法字符
	 * @param strs
	 * @return 2018年10月10日 作者：fengchase
	 */
	public static boolean StrFilter(String... strs) {
		for (String str : strs) {
			if(str==null) {
				return false;
			}
			if (str.contains(",")) {
				return true;
			} else if (str.contains("'")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 对比两个字符串是否相等，有任一为空则返回false
	 * 
	 * @param str1
	 * @param str2
	 * @return 2018年10月15日 作者：fengchase
	 */
	public static boolean twoStrMatch(String str1, String str2) {
		
		if (str1 == null || str2 == null) {
			return false;
		}
		if (str1 == str2 || str1.equals(str2)) {
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @param str1 比对值
	 * @param str2  参考值
	 * @return
	 * 2018年11月8日
	 * 作者：fengchase
	 */
	public static boolean strIsContansStr2(String str1, String str2) {
		if(str1==null || str2==null) {
			return false;
		}
		String[] temp= str2.split(",");
		for(String t:temp) {
			if(str1==t || str1.equals(t)) {
				return true;
			}
		}
		return false;
	}
	public static String HiddenPhone(String phone) {
		if (phone == null || phone.length() != 11) {
			return "";
		}
		String str1 = phone.substring(0, 3);
		String str2 = phone.substring(7);
		return str1 + "****" + str2;
	}
	/**
	 * 判断str1是否包含了str2
	 * @param str1
	 * @param str2
	 * @return
	 * 2018年11月9日
	 * 作者：fengchase
	 */
	public static Boolean Str1ConstansStr2(String str1, String str2) {
		if(isNullString(str1,str2)) {
			return false;
		}
		if(str1.indexOf(str2)==-1 || !str1.contains(str2)) {
			return false;
		}
		return true;
	}

}
