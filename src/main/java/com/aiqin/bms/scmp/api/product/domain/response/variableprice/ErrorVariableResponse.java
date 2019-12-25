package com.aiqin.bms.scmp.api.product.domain.response.variableprice;

import com.aiqin.bms.scmp.api.common.HandlingExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("错误信息map")
public class ErrorVariableResponse {
    @ApiModelProperty("错误原因")
    private String errorReason;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("品类code")
    private String productCategoryCode;

    @ApiModelProperty("品类名称")
    private String productCategoryName;

    @ApiModelProperty("品牌code")
    private String productBrandCode;

    @ApiModelProperty("品牌名称")
    private String productBrandName;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("颜色编码")
    private String colorCode;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("型号")
    private String modelNumber;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("供应商code")
    private String supplierCode;

    @ApiModelProperty("0:否 1：是")
    private Byte isDefault;

    @ApiModelProperty("新含税采购价")
    private BigDecimal newTaxedPurchasingPrice;

    @ApiModelProperty("生效时间")
    private Date takeEffectTime;

    @ApiModelProperty("价格类型")
    private String priceTypeCode;

    @ApiModelProperty("价格类型")
    private String priceTypeName;


    @ApiModelProperty("最新采购价")
    private BigDecimal newPurchasingPrice;

    @ApiModelProperty("原含税采购价")
    private BigDecimal originalTaxPurchasePrice;

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
    private BigDecimal originalMembershipPrice;

    @ApiModelProperty("新含税会员价")
    private BigDecimal newTaxedMembershipPrice;

    @ApiModelProperty("调价原因")
    private String updatePriceReason;

    @ApiModelProperty("变价原因关联字典code")
    private String updatePriceReasonCode;

    @ApiModelProperty("失效时间")
    private Date failureTime;

    @ApiModelProperty("临时含税售价")
    private BigDecimal temporaryTaxedPrice;

    public void setPriceTypeCode(String priceTypeCode) {
        switch (priceTypeCode) {
            case HandlingExceptionCode.PURCHASE_PRICE:
                this.newTaxedPurchasingPrice=originalTaxPurchasePrice;
                break;
            case HandlingExceptionCode.DISTRIBUTION_PRICE:
                this.originalMembershipPrice=originalTaxPurchasePrice;
                break;
            case HandlingExceptionCode.TEMPORARY_DISTRIBUTION_PRICE:
                this.originalMembershipPrice=originalTaxPurchasePrice;
                break;
            case HandlingExceptionCode.PRICE:
                this.originalMembershipPrice=originalTaxPurchasePrice;
                break;
            case HandlingExceptionCode.TEMPORARY_SELLING_PRICE:
                this.temporaryTaxedPrice=originalTaxPurchasePrice;
                break;
            case HandlingExceptionCode.MEMBERSHIP_PRICE:
                this.originalMembershipPrice=originalTaxPurchasePrice;
                break;
            case HandlingExceptionCode.LARGE_EFFECT_PRICE:
                this.originalMembershipPrice=originalTaxPurchasePrice;
                break;
            default:
                break;
        }
        this.priceTypeCode = priceTypeCode;
    }


    public String getPriceTypeName() {
        return priceTypeName;
    }
    public void setPriceTypeName(String priceTypeName) {
        switch (priceTypeName) {
            case HandlingExceptionCode.PURCHASE_PRICE_NAME:
                this.newTaxedPurchasingPrice=originalTaxPurchasePrice;
                break;
            case HandlingExceptionCode.DISTRIBUTION_PRICE_NAME:
                this.originalMembershipPrice=originalTaxPurchasePrice;
                break;
            case HandlingExceptionCode.TEMPORARY_DISTRIBUTION_PRICE_NAME:
                this.originalMembershipPrice=originalTaxPurchasePrice;
                break;
            case HandlingExceptionCode.PRICE_NAME:
                this.originalMembershipPrice=originalTaxPurchasePrice;
                break;
            case HandlingExceptionCode.TEMPORARY_SELLING_PRICE_NAME:
                this.temporaryTaxedPrice=originalTaxPurchasePrice;
                break;
            case HandlingExceptionCode.MEMBERSHIP_PRICE_NAME:
                this.originalMembershipPrice=originalTaxPurchasePrice;
                break;
            case HandlingExceptionCode.LARGE_EFFECT_PRICE_NAME:
                this.originalMembershipPrice=originalTaxPurchasePrice;
                break;
            default:
                break;
        }
        this.priceTypeName = priceTypeName;
    }
}
