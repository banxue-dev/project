package com.banxue.utils.pay.wex.pojo;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author tangjie
 * @email lg932740579@163.com
 * @date 2018-07-12 15:08:49
 */
public class WxTokenDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//token值
	private String acessToken;
	//超时时间
	private String outTime;
	//创建时间
	private Date createTime;
	//应用id
	private String appid;
	//ticket
	private String ticket;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：token值
	 */
	public void setAcessToken(String acessToken) {
		this.acessToken = acessToken;
	}
	/**
	 * 获取：token值
	 */
	public String getAcessToken() {
		return acessToken;
	}
	/**
	 * 设置：超时时间
	 */
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	/**
	 * 获取：超时时间
	 */
	public String getOutTime() {
		return outTime;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：应用id
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}
	/**
	 * 获取：应用id
	 */
	public String getAppid() {
		return appid;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
}
