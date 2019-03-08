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
@TableName("bx_nuo_goods")
public class BxNuoGoods extends Model<BxNuoGoods> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "goods_id", type = IdType.AUTO)
    private Long goodsId;

    /**
     * 品名
     */
    @TableField("goods_name")
    private String goodsName;

    /**
     * 商品的图片，逗号 隔开
     */
    @TableField("goods_imgs")
    private String goodsImgs;

    /**
     * 详情
     */
    @TableField("goods_detail")
    private String goodsDetail;

    /**
     * 库存
     */
    @TableField("goods_count")
    private Integer goodsCount;

    /**
     * 品牌
     */
    @TableField("goods_brand")
    private String goodsBrand;

    /**
     * 参与的活动，=0表示不参加
     */
    @TableField("sale_id")
    private Integer saleId;

    /**
     * 关键字，用于搜索
     */
    @TableField("goods_keyword")
    private String goodsKeyword;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 备注
     */
    @TableField("goods_remark")
    private String goodsRemark;

    /**
     * 标题图片
     */
    @TableField("goods_title_img")
    private String goodsTitleImg;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public String getGoodsImgs() {
        return goodsImgs;
    }

    public void setGoodsImgs(String goodsImgs) {
        this.goodsImgs = goodsImgs;
    }
    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }
    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }
    public String getGoodsBrand() {
        return goodsBrand;
    }

    public void setGoodsBrand(String goodsBrand) {
        this.goodsBrand = goodsBrand;
    }
    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }
    public String getGoodsKeyword() {
        return goodsKeyword;
    }

    public void setGoodsKeyword(String goodsKeyword) {
        this.goodsKeyword = goodsKeyword;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getGoodsRemark() {
        return goodsRemark;
    }

    public void setGoodsRemark(String goodsRemark) {
        this.goodsRemark = goodsRemark;
    }
    public String getGoodsTitleImg() {
        return goodsTitleImg;
    }

    public void setGoodsTitleImg(String goodsTitleImg) {
        this.goodsTitleImg = goodsTitleImg;
    }

    @Override
    protected Serializable pkVal() {
        return this.goodsId;
    }

    @Override
    public String toString() {
        return "BxNuoGoods{" +
        "goodsId=" + goodsId +
        ", goodsName=" + goodsName +
        ", goodsImgs=" + goodsImgs +
        ", goodsDetail=" + goodsDetail +
        ", goodsCount=" + goodsCount +
        ", goodsBrand=" + goodsBrand +
        ", saleId=" + saleId +
        ", goodsKeyword=" + goodsKeyword +
        ", createTime=" + createTime +
        ", goodsRemark=" + goodsRemark +
        ", goodsTitleImg=" + goodsTitleImg +
        "}";
    }
}
