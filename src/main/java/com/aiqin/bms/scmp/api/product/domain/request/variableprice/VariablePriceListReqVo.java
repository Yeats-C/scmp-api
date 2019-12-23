package com.aiqin.bms.scmp.api.product.domain.request.variableprice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("变价数据列表")
public class VariablePriceListReqVo {

    @NotEmpty(message = "sku编码不能为空")
    @ApiModelProperty("sku编码")
    private String skuCode;
    @NotEmpty(message = "sku名称不能为空")
    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("最新采购价")
    private BigDecimal newPurchasingPrice;

    @ApiModelProperty("供应商code")
    private String supplierCode;

    @ApiModelProperty("0:否 1：是")
    private Byte isDefault;

    @ApiModelProperty("原含税采购价")
    private Long originalTaxPurchasePrice;

    @ApiModelProperty("生效时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date takeEffectTime;

    @ApiModelProperty("新含税采购价")
    private BigDecimal newTaxedPurchasingPrice;

    @ApiModelProperty("原会员价")
    private BigDecimal originalMembershipPrice;

    @ApiModelProperty("新含税会员价")
    private BigDecimal newTaxedMembershipPrice;

    @ApiModelProperty("调价原因")
    private String updatePriceReason;

    @ApiModelProperty("变价原因关联字典code")
    private String updatePriceReasonCode;

    @ApiModelProperty("失效时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date failureTime;

    @ApiModelProperty("临时含税售价")
    private BigDecimal temporaryTaxedPrice;


}
