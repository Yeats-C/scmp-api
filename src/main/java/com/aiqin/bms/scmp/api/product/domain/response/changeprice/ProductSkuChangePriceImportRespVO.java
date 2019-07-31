package com.aiqin.bms.scmp.api.product.domain.response.changeprice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by 爱亲 on 2019/7/19.
 */
@ApiModel("采购变价导入")
@Data
public class ProductSkuChangePriceImportRespVO {

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("是否默认(0否1是)")
    private Integer beDefault;

    @ApiModelProperty("价格项目名称")
    private String priceProjectName;

    @ApiModelProperty("开始生效时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date effectiveTimeStart;

    @ApiModelProperty("结束生效时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date effectiveTimeEnd;

    @ApiModelProperty("新的含税采购价")
    private Long purchasePriceNew;

    @ApiModelProperty("调价原因")
    private String reason;

    @ApiModelProperty("错误原因")
    private String errorReason;

    public ProductSkuChangePriceImportRespVO(String skuCode, String skuName, String supplierCode, String supplierName, Integer beDefault, String priceProjectName, Date effectiveTimeStart, Date effectiveTimeEnd, Long purchasePriceNew, String reason, String errorReason) {
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        this.beDefault = beDefault;
        this.priceProjectName = priceProjectName;
        this.effectiveTimeStart = effectiveTimeStart;
        this.effectiveTimeEnd = effectiveTimeEnd;
        this.purchasePriceNew = purchasePriceNew;
        this.reason = reason;
        this.errorReason = errorReason;
    }

    public ProductSkuChangePriceImportRespVO() {
    }
}