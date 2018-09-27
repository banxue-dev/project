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
@TableName("bx_nuo_car")
public class Car extends Model<Car> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 车牌号
     */
    @TableField("car_no")
    private String carNo;

    /**
     * 手机号绑定
     */
    @TableField("user_phone")
    private String userPhone;

    /**
     * 厂商
     */
    @TableField("car_firm")
    private String carFirm;

    /**
     * 品牌
     */
    @TableField("car_brand")
    private String carBrand;

    /**
     * 型号
     */
    @TableField("car_model")
    private String carModel;

    /**
     * 是否删除
     */
    @TableField("is_del")
    private Integer isDel;

    /**
     * 车辆颜色
     */
    @TableField("car_color")
    private String carColor;

    /**
     * 车辆描述
     */
    @TableField("car_desc")
    private String carDesc;

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
    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    public String getCarFirm() {
        return carFirm;
    }

    public void setCarFirm(String carFirm) {
        this.carFirm = carFirm;
    }
    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }
    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }
    public String getCarDesc() {
        return carDesc;
    }

    public void setCarDesc(String carDesc) {
        this.carDesc = carDesc;
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
        return "Car{" +
        "id=" + id +
        ", carNo=" + carNo +
        ", userPhone=" + userPhone +
        ", carFirm=" + carFirm +
        ", carBrand=" + carBrand +
        ", carModel=" + carModel +
        ", isDel=" + isDel +
        ", carColor=" + carColor +
        ", carDesc=" + carDesc +
        ", createTime=" + createTime +
        ", modTime=" + modTime +
        "}";
    }
}
