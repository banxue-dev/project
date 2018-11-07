package com.banxue.utils;
/**
作者：fengchase
时间：2018年11月7日
文件：ResultEntity.java
项目：utils
*/
public class ResultEntity {
	private boolean state;
	private String desc;
	private Object data;
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public ResultEntity(boolean state,String desc,Object data) {
		this.state=state;
		this.desc=desc;
		this.data=data;
	}
	public static ResultEntity successdesc(String desc) {
		return new ResultEntity(true,desc,null);
	}
	public static ResultEntity successdesc(String desc,Object data) {
		return new ResultEntity(true,desc,data);
	}
	public static ResultEntity errordesc(String desc) {
		return new ResultEntity(false,desc,null);
	}

}

