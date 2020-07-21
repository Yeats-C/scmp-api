package com.aiqin.bms.scmp.api.abutment.domain.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("DL- 商品推送供应商信息")
public class ProductSupplierRequest {

    @ApiModelProperty(value = "供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value = "供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value = "含税采购价")
    @JsonProperty("product_amount")
    private BigDecimal productAmount;

    @ApiModelProperty(value = "厂商SKU编号")
    @JsonProperty("factory_sku_code")
    private String factorySkuCode;

    @ApiModelProperty(value = "供货渠道类别")
    @JsonProperty("supply_channel_code")
    private String supplyChannelCode;

    @ApiModelProperty(value = "默认值 0:否,1：是")
    @JsonProperty("is_default")
    private Integer isDefault;
}
