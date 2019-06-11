package com.aiqin.bms.scmp.api.product.domain;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("商品品牌与分销机构关联")
public class ProductBrandDistribution extends PagesRequest {
    @ApiModelProperty(value = "自增主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "品牌类型id")
    @JsonProperty("brand_id")
    private String brandId;

    @ApiModelProperty(value = "品牌首字母")
    @JsonProperty("brand_initials")
    private String brandInitials;

    @ApiModelProperty(value = "排序")
    @JsonProperty("brand_sort")
    private Integer brandSort;

    @ApiModelProperty(value = "品牌类型名称")
    @JsonProperty("brand_name")
    private String brandName;

    @ApiModelProperty(value = "状态，0为启用，1为禁用")
    @JsonProperty("brand_status")
    private Integer brandStatus;

    @ApiModelProperty(value = "品牌logo")
    @JsonProperty("brand_logo")
    private String brandLogo;

    @ApiModelProperty(value = "是否优选，0为优选")
    @JsonProperty("brand_top")
    private Integer brandTop;

    @ApiModelProperty(value = "品牌标签（A、B、C）")
    @JsonProperty("brand_tag")
    private String brandTag;

    @ApiModelProperty(value = "分销机构id")
    @JsonProperty("distributor_id")
    private String distributorId;

    @ApiModelProperty(value = "分销机构名称")
    @JsonProperty("distributor_name")
    private String distributorName;

    @ApiModelProperty(value = "是否展示，0为展示，1为不展示")
    @JsonProperty("is_show")
    private Integer isShow;

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

    @ApiModelProperty(value = "删除标记(0:正常 1:删除)")
    @JsonProperty("del_flag")
    private Integer delFlag;


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

    public String getBrandInitials() {
        return brandInitials;
    }

    public void setBrandInitials(String brandInitials) {
        this.brandInitials = brandInitials;
    }

    public Integer getBrandSort() {
        return brandSort;
    }

    public void setBrandSort(Integer brandSort) {
        this.brandSort = brandSort;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer isBrandStatus() {
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

    public Integer isBrandTop() {
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

    public ProductBrandDistribution() {
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public ProductBrandDistribution(String brandId, String brandName, String distributorId, Integer pageSize, Integer pageNo) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.distributorId = distributorId;
        this.setPageNo(pageNo);
        this.setPageSize(pageSize);
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}