package com.aiqin.bms.scmp.api.supplier.domain.response.variableprice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("价格管理")
@Data
public class PriceManagement {
    @ApiModelProperty("sku编码")
    private String skuCode;
    @ApiModelProperty("sku名称")
    private String skuName;
    @ApiModelProperty("价格类型code")
    private String priceTypeCode;

    @ApiModelProperty("价格类型")
    private String priceTypeName;
    @ApiModelProperty("含税金额")
    private Long taxAmount;

    @ApiModelProperty("生效时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date takeEffectTime;

    @ApiModelProperty("失效时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date failureTime;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("供应商code")
    private String supplierCode;

    @ApiModelProperty("0:否 1：是")
    private Byte isDefault;

    @ApiModelProperty("商品sku价格管理Id")
    private Long productSkuPriceId;

}
