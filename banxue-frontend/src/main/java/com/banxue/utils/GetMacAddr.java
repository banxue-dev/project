package com.banxue.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 作者：fengchase 时间：2018年8月6日 文件：GetMacAddr.java 项目：banxue-backend 通过ip获取mac地址
 */
public class GetMacAddr {

	public static String callCmd(String[] cmd) {
		String result = "";
		String line = "";
		try {
			Process proc = Runtime.getRuntime().exec(cmd);
			InputStreamReader is = new InputStreamReader(proc.getInputStream());
			BufferedReader br = new BufferedReader(is);
			while ((line = br.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @param cmd
	 *            第一个命令
	 * @param another
	 *            第二个命令
	 * @return 第二个命令的执行结果
	 */
	public static String callCmd(String[] cmd, String[] another) {
		String result = "";
		String line = "";
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(cmd);
			proc.waitFor(); // 已经执行完第一个命令，准备执行第二个命令
			proc = rt.exec(another);
			InputStreamReader is = new InputStreamReader(proc.getInputStream());
			BufferedReader br = new BufferedReader(is);
			while ((line = br.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @param ip
	 *            目标ip,一般在局域网内
	 * @param sourceString
	 *            命令处理的结果字符串
	 * @param macSeparator
	 *            mac分隔符号
	 * @return mac地址，用上面的分隔符号表示
	 */
	public static String filterMacAddress(final String ip, final String sourceString, final String macSeparator) {
		String result = "";
		String regExp = "((([0-9,A-F,a-f]{1,2}" + macSeparator + "){1,5})[0-9,A-F,a-f]{1,2})";
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(sourceString);
		while (matcher.find()) {
			result = matcher.group(1);
			if (sourceString.indexOf(ip) <= sourceString.lastIndexOf(matcher.group(1))) {
				break; // 如果有多个IP,只匹配本IP对应的Mac.
			}
		}

		return result;
	}

	/**
	 * 
	 * @param ip
	 *            目标ip
	 * @return Mac Address
	 * 
	 */
	public static String getMacInWindows(final String ip) {
		String result = "";
		String[] cmd = { "cmd", "/c", "ping " + ip };
		String[] another = { "cmd", "/c", "arp -a" };

		String cmdResult = callCmd(cmd, another);
		result = filterMacAddress(ip, cmdResult, "-");

		return result;
	}

	/**
	 * @param ip
	 *            目标ip
	 * @return Mac Address
	 * 
	 */
	public static String getMacInLinux(final String ip) {
		String result = "";
		String[] cmd = { "/bin/sh", "-c", "ping " + ip + " -c 2 && arp -a" };
		String cmdResult = callCmd(cmd);
		result = filterMacAddress(ip, cmdResult, ":");

		return result;
	}

	/**
	 * 获取MAC地址
	 * 
	 * @return 返回MAC地址
	 */
	public static String getMacAddress(String ip) {
		String macAddress = "";
		macAddress = getMacInWindows(ip).trim();
		if (macAddress == null || "".equals(macAddress)) {
			macAddress = getMacInLinux(ip).trim();
		}
		return macAddress;
	}

	// 做个测试
	public static void main(String[] args) {
		System.out.println(getMacAddress("220.181.111.148"));
	}

	/**
	 * 自定义访问对象工具类
	 * 
	 * 获取对象的IP地址等信息
	 * 
	 * @author X-rapido
	 * 
	 * 
	 *         /** 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * 
	 *         可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
	 *         答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * 
	 *         如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
	 *         192.168.1.100
	 * 
	 *         用户真实IP为： 192.168.1.110
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}