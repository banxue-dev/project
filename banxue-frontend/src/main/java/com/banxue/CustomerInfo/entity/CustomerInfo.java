package com.banxue.CustomerInfo.entity;

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
 * @since 2019-01-28
 */
@TableName("customer_info")
public class CustomerInfo extends Model<CustomerInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "customer_id", type = IdType.AUTO)
    private Integer customerId;

    private String names;

    private String phone;

    private String email;

    /**
     * 公司名称
     */
    @TableField("company_name")
    private String companyName;

    private String remark;

    @TableField("create_time")
    private Date createTime;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
        return this.customerId;
    }

    @Override
    public String toString() {
        return "CustomerInfo{" +
        "customerId=" + customerId +
        ", names=" + names +
        ", phone=" + phone +
        ", email=" + email +
        ", companyName=" + companyName +
        ", remark=" + remark +
        ", createTime=" + createTime +
        "}";
    }
}
