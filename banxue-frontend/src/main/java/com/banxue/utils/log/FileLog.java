package com.banxue.utils.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

public class FileLog {
	/**
	 * 系统使用的loger
	 */
	private static Logger debugLogger = null;
	private static Logger errorLogger = null;
	private static Logger infoLogger = null;
	static {
		loadLogger();
	}

	/**
	 * 
	 */
	public FileLog() {
		super();
	}

	/**
	 * 装载系统使用的 loger
	 * 
	 */
	static void loadLogger() {
		debugLogger = Logger.getLogger("debug");
		infoLogger = Logger.getLogger("");// info级已配置为log4j.rootLogger
		errorLogger = Logger.getLogger("error");
	}

	/**
	 * 记录操作过程中的错误信息记录，对应log4j中的error级别的log
	 * 
	 * @param msg
	 *            信息
	 */
	public static void errorLog(Object msg) {
		errorLogger.error(TheardLogidutil.getId() + msg);
	}

	/**
	 * 记录操作过程中的错误的异常信息记录，对应log4j中的error级别的log
	 * 
	 * @param e
	 *            要记录的异常信息
	 */
	public static void errorLog(Exception e) {
		errorLogger.error(getExceptionTrace(e));
	}

	/**
	 * 记录操作过程中的错误的异常信息记录，对应log4j中的error级别的log
	 * 
	 * @param e
	 *            要记录的异常信息
	 * @param msg
	 *            要记录的信息
	 */
	public static void errorLog(Exception e, Object msg) {
		errorLogger.error(TheardLogidutil.getId() + msg + "\n" + getExceptionTrace(e));
	}

	/**
	 * 记录操作过程中的调试信息，对应log4j中的debug级别的log
	 * 
	 * @param msg
	 *            要记录信息
	 */
	public static void debugLog(Object msg) {
		debugLogger.debug(TheardLogidutil.getId() + msg);
	}

	/**
	 * 记录调试中的异常信息，对应log4j中的debug级别的log
	 * 
	 * @param e
	 *            要记录的异常信息
	 */
	public static void debugLog(Exception e) {
		debugLogger.debug(getExceptionTrace(e));
	}

	/**
	 * 记录调试中的异常信息，对应log4j中的debug级别的log
	 * 
	 * @param e
	 *            要记录的异常信息
	 * @param msg
	 *            要记录的信息
	 */
	public static void debugLog(Exception e, Object msg) {
		debugLogger.debug(TheardLogidutil.getId() + msg + "\n" + getExceptionTrace(e));
	}

	/**
	 * 系统信息日志纪录，对应log4j中是info级别的log
	 * 
	 * @param msg
	 *            信息
	 */
	public static void systemLog(Object msg) {
		infoLogger.info(TheardLogidutil.getId() + msg);
	}

	/**
	 * 系统信息异常日志纪录，对应log4j中是info级别的log
	 * 
	 * @param e
	 *            要记录的异常信息
	 */
	public static void systemLog(Exception e) {
		infoLogger.info(getExceptionTrace(e));
	}

	/**
	 * 系统信息异常日志纪录，对应log4j中是info级别的log
	 * 
	 * @param e
	 *            要记录的异常信息
	 * @param msg
	 *            要记录的信息
	 */
	public static void systemLog(Exception e, Object msg) {
		infoLogger.info(TheardLogidutil.getId() + msg + "\n" + getExceptionTrace(e));
	}

	/**
	 * 输出异常信息 替代 e.printStackTrace();
	 * 
	 * @param e
	 *            异常
	 */
	public static void exOut(Exception e) {
		String s = getExceptionTrace(e);
		errorLogger.error(s);
	}

	/**
	 * 异常信息输出
	 * 
	 * @param e
	 *            异常
	 * @return 异常信息
	 */
	private static String getExceptionTrace(Exception e) {
		String s = null;
		ByteArrayOutputStream bout = null;
		PrintWriter wrt = null;
		try {
			bout = new ByteArrayOutputStream();
			wrt = new PrintWriter(bout,true);
			e.printStackTrace(wrt);
			s = bout.toString();
			wrt.close();
			bout.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (wrt != null) {
				try {
					wrt.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if (bout != null) {
				try {
					bout.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		return s;
	}
	

}
