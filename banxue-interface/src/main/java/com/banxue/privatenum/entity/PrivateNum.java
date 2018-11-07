package com.banxue.privatenum.entity;

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
 * @author Mht
 * @since 2018-11-07
 */
@TableName("bx_nuo_private_num")
public class PrivateNum extends Model<PrivateNum> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 华为隐私号码
     */
    @TableField("private_number")
    private String privateNumber;

    /**
     * 0空闲，1，使用中
     */
    private Integer state;

    /**
     * 所绑定的用户的号码
     */
    @TableField("user_phone")
    private String userPhone;

    /**
     * 最后绑定时间
     */
    @TableField("mod_time")
    private Date modTime;

    /**
     * 最后绑定时间毫秒数
     */
    @TableField("mod_time_long")
    private String modTimeLong;

    /**
     * 绑定关系id
     */
    @TableField("bind_gx_id")
    private String bindGxId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getPrivateNumber() {
        return privateNumber;
    }

    public void setPrivateNumber(String privateNumber) {
        this.privateNumber = privateNumber;
    }
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    public Date getModTime() {
        return modTime;
    }

    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }
    public String getModTimeLong() {
        return modTimeLong;
    }

    public void setModTimeLong(String modTimeLong) {
        this.modTimeLong = modTimeLong;
    }
    public String getBindGxId() {
        return bindGxId;
    }

    public void setBindGxId(String bindGxId) {
        this.bindGxId = bindGxId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PrivateNum{" +
        "id=" + id +
        ", privateNumber=" + privateNumber +
        ", state=" + state +
        ", userPhone=" + userPhone +
        ", modTime=" + modTime +
        ", modTimeLong=" + modTimeLong +
        ", bindGxId=" + bindGxId +
        "}";
    }
}
