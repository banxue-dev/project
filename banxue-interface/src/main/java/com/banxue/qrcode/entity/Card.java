package com.banxue.qrcode.entity;

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
 * @author feng
 * @since 2018-09-27
 */
@TableName("bx_nuo_card")
public class Card extends Model<Card> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 编号
     */
    @TableField("card_no")
    private String cardNo;

    /**
     * 用户电话
     */
    @TableField("user_phone")
    private String userPhone;

    /**
     * 是否删除。0：正常，1：删除
     */
    @TableField("is_del")
    private Integer isDel;

    /**
     * 卡片链接地址
     */
    @TableField("card_link")
    private String cardLink;

    /**
     * 卡片等级
     */
    @TableField("card_level")
    private Integer cardLevel;

    /**
     * 是否已经制成了实体二维码0：未，1，已经
     */
    @TableField("is_entity")
    private Integer isEntity;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("mod_time")
    private Date modTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public String getCardLink() {
        return cardLink;
    }

    public void setCardLink(String cardLink) {
        this.cardLink = cardLink;
    }
    public Integer getCardLevel() {
        return cardLevel;
    }

    public void setCardLevel(Integer cardLevel) {
        this.cardLevel = cardLevel;
    }
    public Integer getIsEntity() {
        return isEntity;
    }

    public void setIsEntity(Integer isEntity) {
        this.isEntity = isEntity;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getModTime() {
        return modTime;
    }

    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Card{" +
        "id=" + id +
        ", cardNo=" + cardNo +
        ", userPhone=" + userPhone +
        ", isDel=" + isDel +
        ", cardLink=" + cardLink +
        ", cardLevel=" + cardLevel +
        ", isEntity=" + isEntity +
        ", createTime=" + createTime +
        ", modTime=" + modTime +
        "}";
    }
}
