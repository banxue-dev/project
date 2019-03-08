package com.banxue.onlinemail.entity;

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
 * @since 2019-03-01
 */
@TableName("bx_nuo_wuliu")
public class BxNuoWuliu extends Model<BxNuoWuliu> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("wuliu_id")
    private Long wuliuId;

    /**
     * 运单号
     */
    @TableField("wuliu_code")
    private String wuliuCode;

    /**
     * 接单公司编码
     */
    @TableField("wuliu_compan_code")
    private String wuliuCompanCode;

    /**
     * 接单公司名称
     */
    @TableField("wuliu_compan_name")
    private String wuliuCompanName;

    /**
     * 收货人号码后四位
     */
    @TableField("wuliu_phone_last")
    private String wuliuPhoneLast;

    @TableField("create_time")
    private Date createTime;

    public Long getWuliuId() {
        return wuliuId;
    }

    public void setWuliuId(Long wuliuId) {
        this.wuliuId = wuliuId;
    }
    public String getWuliuCode() {
        return wuliuCode;
    }

    public void setWuliuCode(String wuliuCode) {
        this.wuliuCode = wuliuCode;
    }
    public String getWuliuCompanCode() {
        return wuliuCompanCode;
    }

    public void setWuliuCompanCode(String wuliuCompanCode) {
        this.wuliuCompanCode = wuliuCompanCode;
    }
    public String getWuliuCompanName() {
        return wuliuCompanName;
    }

    public void setWuliuCompanName(String wuliuCompanName) {
        this.wuliuCompanName = wuliuCompanName;
    }
    public String getWuliuPhoneLast() {
        return wuliuPhoneLast;
    }

    public void setWuliuPhoneLast(String wuliuPhoneLast) {
        this.wuliuPhoneLast = wuliuPhoneLast;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.wuliuId;
    }

    @Override
    public String toString() {
        return "BxNuoWuliu{" +
        "wuliuId=" + wuliuId +
        ", wuliuCode=" + wuliuCode +
        ", wuliuCompanCode=" + wuliuCompanCode +
        ", wuliuCompanName=" + wuliuCompanName +
        ", wuliuPhoneLast=" + wuliuPhoneLast +
        ", createTime=" + createTime +
        "}";
    }
}
