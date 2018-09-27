package com.banxue.utils;
/**
作者：fengchase
时间：2018年7月6日
文件：BaseResultModel.java
项目：banxue-backend
*/
public class BaseResultModel {
	private String code;
	private String msg;
	private Object data;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public BaseResultModel() {
		
	}
	public BaseResultModel(String code,String msg) {
		this.code=code;
		this.msg=msg;
	}
	public BaseResultModel(String code,String msg,Object data) {
		this.code=code;
		this.msg=msg;
		this.data=data;
	}
	/**
	 * 通用的成功返回
	 * @return
	 * 2018年7月17日
	 * 作者：fengchase
	 */
	public static BaseResultModel success() {
		return new BaseResultModel(ResultCode.SUCCESS_CODE,ResultCode.SUCCESS_MSG);
	}
	/**
	 * 通用的成功返回
	 * @return
	 * 2018年7月17日
	 * 作者：fengchase
	 */
	public static BaseResultModel success(Object data) {
		return new BaseResultModel(ResultCode.SUCCESS_CODE,ResultCode.SUCCESS_MSG,data);
	}
	/**
	 * 通用的失败返回
	 * @return
	 * 2018年7月17日
	 * 作者：fengchase
	 */
	public static BaseResultModel error() {
		return new BaseResultModel(ResultCode.ERROR_CODE,ResultCode.ERROR_MSG);
	}
	/**
	 * 定制的失败返回
	 * @return
	 * 2018年7月17日
	 * 作者：fengchase
	 */
	public static BaseResultModel Tailor(String code,String msg) {
		return new BaseResultModel(code,msg);
	}

}

