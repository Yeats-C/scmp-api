package com.aiqin.bms.scmp.api.product.domain.response.variableprice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel("价格详细")
public class PriceDetailedResponse {
    @ApiModelProperty("sku编码")
    private String skuCode;
    @ApiModelProperty("sku名称")
    private String skuName;
    @ApiModelProperty("价格类型code")
    private String priceTypeCode;
    @ApiModelProperty("采购组")
    private String procurementSectionCode;
    @ApiModelProperty("采购组名称")
    private String procurementSectionName;
    @ApiModelProperty("价格类型")
    private String priceTypeName;
    @ApiModelProperty("含税金额")
    private Long taxAmount;
    @ApiModelProperty("未税金额")
    private Long nonTaxAmount;
    @ApiModelProperty("税率")
    private Long taxRate;

    @ApiModelProperty("生效时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date takeEffectTime;

    @ApiModelProperty("失效时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date failureTime;

    @ApiModelProperty("品类code")
    private String productCategoryCode;

    @ApiModelProperty("品类名称")
    private String  productCategoryName;

    @ApiModelProperty("品牌code")
    private String productBrandCode;

    @ApiModelProperty("品牌名称")
    private String  productBrandName;

    @ApiModelProperty("商品属性code")
    private String productPropertyCode;

    @ApiModelProperty("商品属性名称")
    private String  productPropertyName;
    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("供应商code")
    private String supplierCode;

    @ApiModelProperty("0:否 1：是")
    private Byte isDefault;
    @ApiModelProperty("修改人")
    private String updateBy;
    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private List<PriceDetailedLogResponse>priceDetailedLogResponses;


}
