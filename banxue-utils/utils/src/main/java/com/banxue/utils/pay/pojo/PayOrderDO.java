package com.banxue.utils.pay.pojo;


import java.io.Serializable;
import java.util.Date;

import com.banxue.utils.Constants;
import com.banxue.utils.DateUtils;
import com.banxue.utils.StringUtils;
/**
作者：fengchase
时间：2018年9月14日
文件：PayOrderDO.java
项目：banxue
*/
public class PayOrderDO  implements Serializable{


	private static final long serialVersionUID = 1L;
	
	
	private String wxCode;
	private String wxState;
	private String openid;
	//订单号
	private String orderNo;
	//金额
	private String payPrice;
	private String orderDesc;
	public String getWxCode() {
		return wxCode;
	}
	public void setWxCode(String wxCode) {
		this.wxCode = wxCode;
	}
	public String getWxState() {
		return wxState;
	}
	public void setWxState(String wxState) {
		this.wxState = wxState;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPayPrice() {
		return payPrice;
	}
	/**
	 * 传入原价，设置的是以分为单位的整数
	 * eg：0.01==>1
	 * @param payPrice
	 * 2019年2月28日
	 * 作者：fengchase
	 */
	public void setPayPrice(Double payPrice) {
		payPrice=payPrice*100;
		this.payPrice = payPrice.intValue()+"";
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	
	

}