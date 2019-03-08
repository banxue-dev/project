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
@TableName("bx_nuo_addr")
public class BxNuoAddr extends Model<BxNuoAddr> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "addr_id", type = IdType.AUTO)
    private Integer addrId;

    /**
     * 绑定的人
     */
    @TableField("user_phone")
    private String userPhone;

    /**
     * 这个收货地址的人的号码
     */
    @TableField("addr_phone")
    private String addrPhone;

    /**
     * 是否默认
     */
    @TableField("is_deafualt")
    private Integer isDeafualt;

    /**
     * 收货地址
     */
    private String addrs;

    /**
     * 这个地址的收货人姓名
     */
    @TableField("user_name")
    private String userName;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    public Integer getAddrId() {
        return addrId;
    }

    public void setAddrId(Integer addrId) {
        this.addrId = addrId;
    }
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    public String getAddrPhone() {
        return addrPhone;
    }

    public void setAddrPhone(String addrPhone) {
        this.addrPhone = addrPhone;
    }
    public Integer getIsDeafualt() {
        return isDeafualt;
    }

    public void setIsDeafualt(Integer isDeafualt) {
        this.isDeafualt = isDeafualt;
    }
    public String getAddrs() {
        return addrs;
    }

    public void setAddrs(String addrs) {
        this.addrs = addrs;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.addrId;
    }

    @Override
    public String toString() {
        return "BxNuoAddr{" +
        "addrId=" + addrId +
        ", userPhone=" + userPhone +
        ", addrPhone=" + addrPhone +
        ", isDeafualt=" + isDeafualt +
        ", addrs=" + addrs +
        ", userName=" + userName +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
