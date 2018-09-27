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
	
	//
	private Integer orderId;
	
	//这两个参数是微信网页授权的参数，用于获取openId
	private String wxCode;
	private String wxState;
	private String openid;
	private String payUserPhone;
	//订单号
	private String orderNo;
	//金额
	private String payPrice;
	//支付人，即创建人
	private String payUser;
	
	//支付人，支付类型
	private Integer payType;
	//手机号
	private Integer payPhone;
	private Integer deviceNo;
	
	//订单状态。0:初始，1：支付中，2，已支付,3。完成，4，失效
	private Integer orderState;
	private String orderStateToSts;
	private String createTimeToSts;
	private String priceToSts;
	//逻辑删除，1：删除，0：正常
	private Integer isDel;
	//
	private Date createTime;
	//
	private Date modTime;
	//备注
	private String orderRemark;
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	/**
	 * 设置：
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：
	 */
	public Integer getOrderId() {
		return orderId;
	}
	/**
	 * 设置：订单号
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	/**
	 * 获取：订单号
	 */
	public String getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置：金额
	 */
	public void setPayPrice(String payPrice) {
		this.payPrice = payPrice;
	}
	/**
	 * 获取：金额
	 */
	public String getPayPrice() {
		return this.payPrice;
	}
	/**
	 * 设置：支付人，即创建人
	 */
	public void setPayUser(String payUser) {
		this.payUser = payUser;
	}
	/**
	 * 获取：支付人，即创建人
	 */
	public String getPayUser() {
		return payUser;
	}
	/**
	 * 设置：获取：订单状态。"初始","支付中","待写入","完成","失效"
	 */
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}
	/**
	 * 获取：订单状态。0："初始",1："支付中",2："待写入",3："完成",4："失效"
	 */
	public Integer getOrderState() {
		return this.orderState;
	}
	/**
	 * 设置：逻辑删除，1：删除，0：正常
	 */
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	/**
	 * 获取：逻辑删除，1：删除，0：正常
	 */
	public Integer getIsDel() {
		return isDel;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return this.createTime;
	}
	/**
	 * 设置：
	 */
	public void setModTime(Date modTime) {
		this.modTime = modTime;
	}
	/**
	 * 获取：
	 */
	public Date getModTime() {
		return modTime;
	}
	/**
	 * 设置：备注
	 */
	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	/**
	 * 获取：备注
	 */
	public String getOrderRemark() {
		return orderRemark;
	}
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
	public Integer getPayPhone() {
		return payPhone;
	}
	public void setPayPhone(Integer payPhone) {
		this.payPhone = payPhone;
	}
	public Integer getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(Integer deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getPayUserPhone() {
		return payUserPhone;
	}
	public void setPayUserPhone(String payUserPhone) {
		this.payUserPhone = payUserPhone;
	}
	
	public String getOrderStateToSts() {
		if(orderState==null) {
			return "";
		}
		return Constants.ORDERSTATE[orderState];
	}
	public String getCreateTimeToSts() {
		if(createTime==null) {
			return "";
		}
		return DateUtils.format(createTime, DateUtils.DATE_TIME_PATTERN);
	}
	public String getPriceToSts() {
		if(StringUtils.isNullString(payPrice)) {
			return payPrice;
		}
		Integer pr=Integer.parseInt(payPrice);
		if(pr==null || pr<1) {
			return payPrice;
		}else {
			if(pr>=100) {
				return pr/100+"";
			}else {
				return pr/100.00+"";
			}
		}
	}
	
	
}
