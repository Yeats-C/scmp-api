package com.aiqin.bms.scmp.api.product.domain.response.variableprice;

import com.aiqin.bms.scmp.api.common.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("变价数据列表")
public class SkuDataListResponse {
    @ApiModelProperty("sku编码")
    private String skuCode;
    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("品类code")
    private String productCategoryCode;

    @ApiModelProperty("品类名称")
    private String  productCategoryName;

    @ApiModelProperty("品牌code")
    private String productBrandCode;

    @ApiModelProperty("品牌名称")
    private String  productBrandName;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("颜色编码")
    private String colorCode;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("型号")
    private String modelNumber;

    @ApiModelProperty("单位编号")
    private String unitCode;

    @ApiModelProperty("单位")
    private String unitName;

    @ApiModelProperty("商品类别")
    private String productSortCode;

    @ApiModelProperty("商品类别名称")
    private String productSortName;

    @ApiModelProperty("赠品(0:商品，1:赠品")
    private Byte goodsGifts;

    @ApiModelProperty("价格类型code")
    private String priceTypeCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("价格类型")
    private String priceTypeName;

    @ApiModelProperty("最新采购价")
    private Long newPurchasingPrice;

    @ApiModelProperty("供应商code")
    private String supplierCode;

    @ApiModelProperty("0:否 1：是")
    private Byte isDefault;

    @ApiModelProperty("原含税采购价")
    private Long originalTaxPurchasePrice;

    @ApiModelProperty("原会员价")
    private Long originalMembershipPrice;

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

    public String getProductCategoryCode() {
        return productCategoryCode;
    }

    public void setProductCategoryCode(String productCategoryCode) {
        this.productCategoryCode = productCategoryCode;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public String getProductBrandCode() {
        return productBrandCode;
    }

    public void setProductBrandCode(String productBrandCode) {
        this.productBrandCode = productBrandCode;
    }

    public String getProductBrandName() {
        return productBrandName;
    }

    public void setProductBrandName(String productBrandName) {
        this.productBrandName = productBrandName;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
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

    public String getProductSortCode() {
        return productSortCode;
    }

    public void setProductSortCode(String productSortCode) {
        this.productSortCode = productSortCode;
    }

    public String getProductSortName() {
        return productSortName;
    }

    public void setProductSortName(String productSortName) {
        this.productSortName = productSortName;
    }

    public Byte getGoodsGifts() {
        return goodsGifts;
    }

    public void setGoodsGifts(Byte goodsGifts) {
        this.goodsGifts = goodsGifts;
    }

    public String getPriceTypeCode() {
        return priceTypeCode;
    }

    public void setPriceTypeCode(String priceTypeCode) {
        this.priceTypeCode = priceTypeCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getPriceTypeName() {
        return priceTypeName;
    }

    public void setPriceTypeName(String priceTypeName) {
        this.priceTypeName = priceTypeName;
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
        this.supplierCode = supplierCode;
    }

    public Byte getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Byte isDefault) {
        this.isDefault = isDefault;
    }
    public void setOriginalTaxPurchasePrice(Long originalTaxPurchasePrice) {
        this.originalTaxPurchasePrice = originalTaxPurchasePrice;
    }

    public Long getOriginalMembershipPrice() {
        return originalMembershipPrice;
    }
    public void setOriginalMembershipPrice(Long originalMembershipPrice) {
       this.originalMembershipPrice=originalMembershipPrice;
    }
    public Long getOriginalTaxPurchasePrice() {
        switch (priceTypeCode) {
            case HandlingExceptionCode.PURCHASE_PRICE:
                this.originalTaxPurchasePrice=originalTaxPurchasePrice;
                break;
            case HandlingExceptionCode.DISTRIBUTION_PRICE:
                this.originalMembershipPrice=originalTaxPurchasePrice;
                this.originalTaxPurchasePrice=null;
                break;
            case HandlingExceptionCode.TEMPORARY_DISTRIBUTION_PRICE:
                this.originalMembershipPrice=originalTaxPurchasePrice;
                this.originalTaxPurchasePrice=null;
                break;
            case HandlingExceptionCode.PRICE:
                this.originalMembershipPrice=originalTaxPurchasePrice;
                this.originalTaxPurchasePrice=null;
                break;
            case HandlingExceptionCode.MEMBERSHIP_PRICE:
                this.originalMembershipPrice=originalTaxPurchasePrice;
                this.originalTaxPurchasePrice=null;
                break;
            case HandlingExceptionCode.LARGE_EFFECT_PRICE:
                this.originalMembershipPrice=originalTaxPurchasePrice;
                this.originalTaxPurchasePrice=null;
                break;
            default:
                break;
        }
        return originalTaxPurchasePrice;
    }
}
