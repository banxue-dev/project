package com.banxue.onlinemail.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Feng
 * @since 2019-02-21
 */
@TableName("bx_nuo_order")
public class BxNuoOrder extends Model<BxNuoOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "orderId", type = IdType.AUTO)
    private Integer orderId;

    /**
     * 订单号
     */
    @TableField("order_code")
    private String orderCode;

    /**
     * 购买人id，bx_nuo_user的phone
     */
    @TableField("buy_user_openid")
    private String buyUserOpenid;

    /**
     * 物流编号
     */
    @TableField("wuliu_id")
    private Long wuliuId;

    /**
     * 收货地址id
     */
    @TableField("order_addr_id")
    private Integer orderAddrId;

    /**
     * 订单状态
     */
    @TableField("order_status")
    private Integer orderStatus;
    
    /**
     * 总金额
     */
    @TableField("totail_price")
    private Double totailPrice;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 订单来源，0：微信，1：web，2：其他
     */
    @TableField("order_source")
    private Integer orderSource;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 备注
     */
    @TableField("order_remark")
    private String orderRemark;
    @TableField(exist = false)
    private List<BxNuoGoods> goods;

    
    public List<BxNuoGoods> getGoods() {
		return goods;
	}

	public void setGoods(List<BxNuoGoods> goods) {
		this.goods = goods;
	}

	public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
  
    public Long getWuliuId() {
		return wuliuId;
	}

	public void setWuliuId(Long wuliuId) {
		this.wuliuId = wuliuId;
	}

	public Integer getOrderAddrId() {
        return orderAddrId;
    }

    public void setOrderAddrId(Integer orderAddrId) {
        this.orderAddrId = orderAddrId;
    }
    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Integer getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(Integer orderSource) {
        this.orderSource = orderSource;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    @Override
    protected Serializable pkVal() {
        return this.orderId;
    }
    

    public Double getTotailPrice() {
		return totailPrice;
	}

	public void setTotailPrice(Double totailPrice) {
		this.totailPrice = totailPrice;
	}

	
	public String getBuyUserOpenid() {
		return buyUserOpenid;
	}

	public void setBuyUserOpenid(String buyUserOpenid) {
		this.buyUserOpenid = buyUserOpenid;
	}

	@Override
    public String toString() {
        return "BxNuoOrder{" +
        "orderId=" + orderId +
        ", orderCode=" + orderCode +
        ", wuliuCode=" + wuliuId +
        ", orderAddrId=" + orderAddrId +
        ", orderStatus=" + orderStatus +
        ", createTime=" + createTime +
        ", orderSource=" + orderSource +
        ", updateTime=" + updateTime +
        ", orderRemark=" + orderRemark +
        "}";
    }
}
