package com.aiqin.bms.scmp.api.product.domain;

import com.aiqin.bms.scmp.api.common.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("商品分类")
public class ProductCategory extends CommonBean {
    @ApiModelProperty(value = "自增主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "分类id")
    @JsonProperty("category_id")
    private String categoryId;

    @ApiModelProperty(value = "分类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty(value = "状态，0为启用，1为禁用")
    @JsonProperty("category_status")
    private Integer categoryStatus;

    @ApiModelProperty(value = "父级id")
    @JsonProperty("parent_id")
    private String parentId;

    @ApiModelProperty(value = "级别，1、2、3级")
    @JsonProperty("category_level")
    private Integer categoryLevel;

    @ApiModelProperty(value = "商品分类编号")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty(value = "图片路径")
    @JsonProperty("picture_path")
    private String picturePath;

    @ApiModelProperty(value = "排序")
    @JsonProperty("weight")
    private Integer weight;

    @ApiModelProperty(value = "图片名称")
    @JsonProperty("picture_name")
    private String pictureName;

    @ApiModelProperty(value = "删除标记(0:正常 1:删除)")
    @JsonProperty("del_flag")
    private Byte delFlag;

    @ApiModelProperty(value = "create_time")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value = "update_time")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "create_by")
    @JsonProperty("create_by")
    private String createBy;

    @ApiModelProperty(value = "update_by")
    @JsonProperty("update_by")
    private String updateBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(Integer categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(Integer categoryLevel) {
        this.categoryLevel = categoryLevel;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public String getProductCategoryCode() {
        return productCategoryCode;
    }

    public void setProductCategoryCode(String productCategoryCode) {
        this.productCategoryCode = productCategoryCode;
    }

}