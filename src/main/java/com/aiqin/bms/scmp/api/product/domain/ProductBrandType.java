package com.aiqin.bms.scmp.api.product.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("商品品牌类型")
public class ProductBrandType {
    @ApiModelProperty(value = "自增主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "品牌类型id")
    @JsonProperty("brand_id")
    private String brandId;

    @ApiModelProperty(value = "品牌类型名称")
    @JsonProperty("brand_name")
    private String brandName;

    @ApiModelProperty(value = "状态，0为启用，1为禁用")
    @JsonProperty("brand_status")
    private Integer brandStatus;

    @ApiModelProperty(value = "'品牌logo")
    @JsonProperty("brand_logo")
    private String brandLogo;

    @ApiModelProperty(value = "是否优选，0为优选")
    @JsonProperty("brand_top")
    private Integer brandTop;

    @ApiModelProperty(value = "品牌标签（A、B、C）")
    @JsonProperty("brand_tag")
    private String brandTag;

    @ApiModelProperty(value = "品牌code")
    @JsonProperty("brand_code")
    private String brandCode;

    @ApiModelProperty(value = "品牌文件名称")
    @JsonProperty("brand_logo_name")
    private String brandLogoName;

    @ApiModelProperty(value = "品牌首字母")
    @JsonProperty("brand_initials")
    private String brandInitials;

    @ApiModelProperty(value = "品牌介绍")
    @JsonProperty("brand_introduction")
    private String brandIntroduction;

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

    @ApiModelProperty(value = "(0:正常 1:删除)")
    @JsonProperty("del_flag")
    private Integer delFlag;

    @ApiModelProperty(value = "持有人")
    @JsonProperty("holder")
    private String holder;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getBrandStatus() {
        return brandStatus;
    }

    public void setBrandStatus(Integer brandStatus) {
        this.brandStatus = brandStatus;
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public Integer getBrandTop() {
        return brandTop;
    }

    public void setBrandTop(Integer brandTop) {
        this.brandTop = brandTop;
    }

    public String getBrandTag() {
        return brandTag;
    }

    public void setBrandTag(String brandTag) {
        this.brandTag = brandTag;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandLogoName() {
        return brandLogoName;
    }

    public void setBrandLogoName(String brandLogoName) {
        this.brandLogoName = brandLogoName;
    }

    public String getBrandInitials() {
        return brandInitials;
    }

    public void setBrandInitials(String brandInitials) {
        this.brandInitials = brandInitials;
    }

    public String getBrandIntroduction() {
        return brandIntroduction;
    }

    public void setBrandIntroduction(String brandIntroduction) {
        this.brandIntroduction = brandIntroduction;
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }


    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}