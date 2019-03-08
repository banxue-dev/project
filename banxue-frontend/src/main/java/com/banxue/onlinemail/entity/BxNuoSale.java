package com.banxue.onlinemail.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
@TableName("bx_nuo_sale")
public class BxNuoSale extends Model<BxNuoSale> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "sale_id", type = IdType.AUTO)
    private Integer saleId;

    /**
     * 优惠/活动名称
     */
    @TableField("sale_name")
    private String saleName;

    /**
     * 优惠金额
     */
    @TableField("sale_price")
    private String salePrice;

    /**
     * 憋住
     */
    private String remark;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }
    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }
    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.saleId;
    }

    @Override
    public String toString() {
        return "BxNuoSale{" +
        "saleId=" + saleId +
        ", saleName=" + saleName +
        ", salePrice=" + salePrice +
        ", remark=" + remark +
        ", createTime=" + createTime +
        "}";
    }
}
