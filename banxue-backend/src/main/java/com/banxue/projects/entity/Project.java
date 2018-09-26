package com.banxue.projects.entity;

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
 * @since 2018-09-25
 */
@TableName("bx_project")
public class Project extends Model<Project> {

    private static final long serialVersionUID = 1L;

    /**
     * 增长id
     */
    @TableId(value = "pro_id", type = IdType.AUTO)
    private Integer proId;

    /**
     * 项目编号
     */
    @TableField("pro_no")
    private String proNo;

    /**
     * 项目名称
     */
    @TableField("pro_name")
    private String proName;

    /**
     * 状态：0-初始讨论，1-确认，2-开发，3-测试，4-上线，5-完成
     */
    @TableField("pro_status")
    private Integer proStatus;

    /**
     * 项目进度，0~100，表示进度百分比
     */
    @TableField("pro_progress")
    private Integer proProgress;

    /**
     * 实际结束时间
     */
    @TableField("plan_start_time")
    private Date planStartTime;

    /**
     * 计划结束时间
     */
    @TableField("plan_end_time")
    private Date planEndTime;

    /**
     * 实际开始时间
     */
    @TableField("actual_start_time")
    private Date actualStartTime;

    /**
     * 实际结束时间
     */
    @TableField("actual_end_time")
    private Date actualEndTime;

    /**
     * 项目主体类型:1-网页项目，2-后台项目，3-公众号，4-小程序
     */
    @TableField("pro_main_type")
    private Integer proMainType;

    /**
     * 关联的配置信息
     */
    @TableField("relation_config")
    private String relationConfig;

    /**
     * 父级项目
     */
    @TableField("pro_parent")
    private Integer proParent;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 是否删除，1删除，0正常
     */
    @TableField("is_del")
    private Integer isDel;

    /**
     * 备注
     */
    private String remark;

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }
    public String getProNo() {
        return proNo;
    }

    public void setProNo(String proNo) {
        this.proNo = proNo;
    }
    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }
    public Integer getProStatus() {
        return proStatus;
    }

    public void setProStatus(Integer proStatus) {
        this.proStatus = proStatus;
    }
    public Integer getProProgress() {
        return proProgress;
    }

    public void setProProgress(Integer proProgress) {
        this.proProgress = proProgress;
    }
    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }
    public Date getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }
    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }
    public Date getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
    }
    public Integer getProMainType() {
        return proMainType;
    }

    public void setProMainType(Integer proMainType) {
        this.proMainType = proMainType;
    }
    public String getRelationConfig() {
        return relationConfig;
    }

    public void setRelationConfig(String relationConfig) {
        this.relationConfig = relationConfig;
    }
    public Integer getProParent() {
        return proParent;
    }

    public void setProParent(Integer proParent) {
        this.proParent = proParent;
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
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.proId;
    }

    @Override
    public String toString() {
        return "Project{" +
        "proId=" + proId +
        ", proNo=" + proNo +
        ", proName=" + proName +
        ", proStatus=" + proStatus +
        ", proProgress=" + proProgress +
        ", planStartTime=" + planStartTime +
        ", planEndTime=" + planEndTime +
        ", actualStartTime=" + actualStartTime +
        ", actualEndTime=" + actualEndTime +
        ", proMainType=" + proMainType +
        ", relationConfig=" + relationConfig +
        ", proParent=" + proParent +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", isDel=" + isDel +
        ", remark=" + remark +
        "}";
    }
}
