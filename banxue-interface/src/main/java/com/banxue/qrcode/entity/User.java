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
@TableName("bx_nuo_user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 微信的openid
     */
    @TableField("wx_opend_id")
    private String wxOpendId;

    /**
     * 手机号
     */
    @TableField("user_phone")
    private String userPhone;

    /**
     * 昵称，若有微信昵称，则使用微信昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 性别，1男，0，女
     */
    @TableField("user_sexual")
    private Integer userSexual;

    /**
     * 省
     */
    @TableField("user_province")
    private String userProvince;

    /**
     * 市
     */
    @TableField("user_city")
    private String userCity;

    /**
     * 使用封装加密
     */
    @TableField("user_pwd")
    private String userPwd;

    /**
     * 用户头像
     */
    @TableField("user_head_url")
    private String userHeadUrl;

    /**
     * 用户留言
     */
    @TableField("user_message")
    private String userMessage;

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
    public String getWxOpendId() {
        return wxOpendId;
    }

    public void setWxOpendId(String wxOpendId) {
        this.wxOpendId = wxOpendId;
    }
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public Integer getUserSexual() {
        return userSexual;
    }

    public void setUserSexual(Integer userSexual) {
        this.userSexual = userSexual;
    }
    public String getUserProvince() {
        return userProvince;
    }

    public void setUserProvince(String userProvince) {
        this.userProvince = userProvince;
    }
    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }
    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }
    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
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
        return "User{" +
        "id=" + id +
        ", wxOpendId=" + wxOpendId +
        ", userPhone=" + userPhone +
        ", nickName=" + nickName +
        ", userSexual=" + userSexual +
        ", userProvince=" + userProvince +
        ", userCity=" + userCity +
        ", userPwd=" + userPwd +
        ", userHeadUrl=" + userHeadUrl +
        ", userMessage=" + userMessage +
        ", createTime=" + createTime +
        ", modTime=" + modTime +
        "}";
    }
}
