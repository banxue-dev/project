package com.banxue.onlinemail.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
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
@TableName("bx_nuo_order_by_goods")
public class BxNuoOrderByGoods extends Model<BxNuoOrderByGoods> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "buy_goods_id", type = IdType.AUTO)
    private Long buyGoodsId;

    /**
     * 商品id关联
     */
    @TableField("goods_id")
    private Long goodsId;

    /**
     * 购买数量
     */
    @TableField("buy_count")
    private Integer buyCount;

    /**
     * 购买的实际价格
     */
    @TableField("buy_price")
    private Double buyPrice;

    /**
     * 属于那个订单
     */
    @TableField("order_code")
    private String orderCode;

    public Long getBuyGoodsId() {
        return buyGoodsId;
    }

    public void setBuyGoodsId(Long buyGoodsId) {
        this.buyGoodsId = buyGoodsId;
    }
    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }
    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Override
    protected Serializable pkVal() {
        return this.buyGoodsId;
    }

    @Override
    public String toString() {
        return "BxNuoOrderByGoods{" +
        "buyGoodsId=" + buyGoodsId +
        ", goodsId=" + goodsId +
        ", buyCount=" + buyCount +
        ", buyPrice=" + buyPrice +
        ", orderCode=" + orderCode +
        "}";
    }
}
