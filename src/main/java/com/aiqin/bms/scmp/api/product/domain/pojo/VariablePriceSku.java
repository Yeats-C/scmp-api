package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("商品变价sku集合")
public class VariablePriceSku extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("最新采购价")
    private Long newPurchasingPrice;

    @ApiModelProperty("供应商code")
    private String supplierCode;

    @ApiModelProperty("0:否 1：是")
    private Byte isDefault;

    @ApiModelProperty("原含税采购价")
    private Long originalTaxPurchasePrice;

    @ApiModelProperty("生效时间")
    private Date takeEffectTime;

    @ApiModelProperty("新含税采购价")
    private Long newTaxedPurchasingPrice;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("原会员价")
    private Long originalMembershipPrice;

    @ApiModelProperty("新含税会员价")
    private Long newTaxedMembershipPrice;

    @ApiModelProperty("调价原因")
    private String updatePriceReason;

    @ApiModelProperty("变价原因关联字典code")
    private String updatePriceReasonCode;

    @ApiModelProperty("失效时间")
    private Date failureTime;

    @ApiModelProperty("临时含税售价")
    private Long temporaryTaxedPrice;

    @ApiModelProperty("变价名称")
    private String variablePriceName;

    @ApiModelProperty("商品品类code")
    private String productCategoryCode;

    @ApiModelProperty("商品品类名称")
    private String productCategoryName;

    @ApiModelProperty("变价code")
    private String variablePriceCode;
    @ApiModelProperty("变价详细code")
    private String variablePriceSkuCode;

    @ApiModelProperty("(0:不撤销,1:撤销)")
    private Byte priceRevoke;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode == null ? null : skuCode.trim();
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public Long getNewPurchasingPrice() {
        return newPurchasingPrice;
    }

    public void setNewPurchasingPrice(Long newPurchasingPrice) {
        this.newPurchasingPrice = newPurchasingPrice;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    public Byte getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Byte isDefault) {
        this.isDefault = isDefault;
    }

    public Long getOriginalTaxPurchasePrice() {
        return originalTaxPurchasePrice;
    }

    public void setOriginalTaxPurchasePrice(Long originalTaxPurchasePrice) {
        this.originalTaxPurchasePrice = originalTaxPurchasePrice;
    }

    public Date getTakeEffectTime() {
        return takeEffectTime;
    }

    public void setTakeEffectTime(Date takeEffectTime) {
        this.takeEffectTime = takeEffectTime;
    }

    public Long getNewTaxedPurchasingPrice() {
        return newTaxedPurchasingPrice;
    }

    public void setNewTaxedPurchasingPrice(Long newTaxedPurchasingPrice) {
        this.newTaxedPurchasingPrice = newTaxedPurchasingPrice;
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Long getOriginalMembershipPrice() {
        return originalMembershipPrice;
    }

    public void setOriginalMembershipPrice(Long originalMembershipPrice) {
        this.originalMembershipPrice = originalMembershipPrice;
    }

    public Long getNewTaxedMembershipPrice() {
        return newTaxedMembershipPrice;
    }

    public void setNewTaxedMembershipPrice(Long newTaxedMembershipPrice) {
        this.newTaxedMembershipPrice = newTaxedMembershipPrice;
    }

    public String getUpdatePriceReason() {
        return updatePriceReason;
    }

    public void setUpdatePriceReason(String updatePriceReason) {
        this.updatePriceReason = updatePriceReason == null ? null : updatePriceReason.trim();
    }

    public String getUpdatePriceReasonCode() {
        return updatePriceReasonCode;
    }

    public void setUpdatePriceReasonCode(String updatePriceReasonCode) {
        this.updatePriceReasonCode = updatePriceReasonCode == null ? null : updatePriceReasonCode.trim();
    }

    public Date getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(Date failureTime) {
        this.failureTime = failureTime;
    }

    public Long getTemporaryTaxedPrice() {
        return temporaryTaxedPrice;
    }

    public void setTemporaryTaxedPrice(Long temporaryTaxedPrice) {
        this.temporaryTaxedPrice = temporaryTaxedPrice;
    }

    public String getVariablePriceName() {
        return variablePriceName;
    }

    public void setVariablePriceName(String variablePriceName) {
        this.variablePriceName = variablePriceName == null ? null : variablePriceName.trim();
    }

    public String getProductCategoryCode() {
        return productCategoryCode;
    }

    public void setProductCategoryCode(String productCategoryCode) {
        this.productCategoryCode = productCategoryCode == null ? null : productCategoryCode.trim();
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName == null ? null : productCategoryName.trim();
    }

    public String getVariablePriceCode() {
        return variablePriceCode;
    }

    public void setVariablePriceCode(String variablePriceCode) {
        this.variablePriceCode = variablePriceCode == null ? null : variablePriceCode.trim();
    }
    public String getVariablePriceSkuCode() {
        return variablePriceSkuCode;
    }

    public void setVariablePriceSkuCode(String variablePriceSkuCode) {
        this.variablePriceSkuCode = variablePriceSkuCode == null ? null : variablePriceSkuCode.trim();
    }
    public Byte getPriceRevoke() {
        return priceRevoke;
    }

    public void setPriceRevoke(Byte priceRevoke) {
        this.priceRevoke = priceRevoke;
    }
}