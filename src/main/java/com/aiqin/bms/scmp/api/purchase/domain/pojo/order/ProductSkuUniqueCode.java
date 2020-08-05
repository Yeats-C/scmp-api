package com.aiqin.bms.scmp.api.purchase.domain.pojo.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("订单商品唯一码信息")
@Data
public class ProductSkuUniqueCode {

    @ApiModelProperty("商品主键")
    @JsonProperty(value = "id")
    private Long id;

    @ApiModelProperty("订单主表编码")
    @JsonProperty(value = "order_code")
    private String orderCode;

    @ApiModelProperty("sku编号")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("SKU名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("商品唯一码")
    @JsonProperty(value = "unique_code")
    private String uniqueCode;


}
