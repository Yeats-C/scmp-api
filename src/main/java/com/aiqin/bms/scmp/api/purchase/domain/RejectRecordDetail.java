package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel
public class RejectRecordDetail {
    @ApiModelProperty(value="")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="")
    @JsonProperty("reject_record_id")
    private String rejectRecordId;

    @ApiModelProperty(value="")
    @JsonProperty("reject_record_detail_id")
    private String rejectRecordDetailId;

    @ApiModelProperty(value="")
    @JsonProperty("reject_record_code")
    private String rejectRecordCode;

    @ApiModelProperty(value="")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="")
    @JsonProperty("product_id")
    private String productId;

    @ApiModelProperty(value="品类id")
    @JsonProperty("category_id")
    private String categoryId;

    @ApiModelProperty(value="")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty(value="品牌id")
    @JsonProperty("brand_id")
    private String brandId;

    @ApiModelProperty(value="")
    @JsonProperty("brand_name")
    private String brandName;

    @ApiModelProperty(value="商品类型 0赠品 1商品 2实物返回")
    @JsonProperty("product_type")
    private Integer productType;

    @ApiModelProperty(value="规格")
    @JsonProperty("product_spec")
    private String productSpec;

    @ApiModelProperty(value="单位")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty(value="单位")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty(value="税率")
    @JsonProperty("tax_rate")
    private Integer taxRate;

    @ApiModelProperty(value="含税单价")
    @JsonProperty("product_amount")
    private Long productAmount;

    @ApiModelProperty(value="含税总价")
    @JsonProperty("product_total_amount")
    private Long productTotalAmount;

    @ApiModelProperty(value="wms 传回来的实际数量")
    @JsonProperty("actual_count")
    private Integer actualCount;

    @ApiModelProperty(value="wms 传回来的实际金额")
    @JsonProperty("actual_amount")
    private Long actualAmount;

    @ApiModelProperty(value="商品批次号")
    @JsonProperty("batch_no")
    private String batchNo;

    @ApiModelProperty(value="批次备注")
    @JsonProperty("batch_remark")
    private String batchRemark;

    @ApiModelProperty(value="批次备注")
    @JsonProperty("batch_create_time")
    private Date batchCreateTime;

    @ApiModelProperty(value="")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value="")
    @JsonProperty("update_by_name")
    private String updateByName;

    @ApiModelProperty(value="")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="")
    @JsonProperty("update_time")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRejectRecordId() {
        return rejectRecordId;
    }

    public void setRejectRecordId(String rejectRecordId) {
        this.rejectRecordId = rejectRecordId;
    }

    public String getRejectRecordCode() {
        return rejectRecordCode;
    }

    public void setRejectRecordCode(String rejectRecordCode) {
        this.rejectRecordCode = rejectRecordCode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Integer taxRate) {
        this.taxRate = taxRate;
    }

    public Long getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Long productAmount) {
        this.productAmount = productAmount;
    }

    public Long getProductTotalAmount() {
        return productTotalAmount;
    }

    public void setProductTotalAmount(Long productTotalAmount) {
        this.productTotalAmount = productTotalAmount;
    }

    public Integer getActualCount() {
        return actualCount;
    }

    public void setActualCount(Integer actualCount) {
        this.actualCount = actualCount;
    }

    public Long getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Long actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBatchRemark() {
        return batchRemark;
    }

    public void setBatchRemark(String batchRemark) {
        this.batchRemark = batchRemark;
    }

    public Date getBatchCreateTime() {
        return batchCreateTime;
    }

    public void setBatchCreateTime(Date batchCreateTime) {
        this.batchCreateTime = batchCreateTime;
    }

    public String getCreateById() {
        return createById;
    }

    public void setCreateById(String createById) {
        this.createById = createById;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getUpdateById() {
        return updateById;
    }

    public void setUpdateById(String updateById) {
        this.updateById = updateById;
    }

    public String getUpdateByName() {
        return updateByName;
    }

    public void setUpdateByName(String updateByName) {
        this.updateByName = updateByName;
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
}