package com.aiqin.bms.scmp.api.product.domain.request.movement;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("wms移库推送商品表")
@Data
public class MovementProductWmsReq {

    @ApiModelProperty("行号")
    @JsonProperty(value = "line_code")
    private Long lineCode;

    @ApiModelProperty("sku编号")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("sku条码")
    @JsonProperty(value = "sku_bar_name")
    private String skuBarName;

    @ApiModelProperty("数量")
    @JsonProperty(value = "quantity")
    private Long quantity;

    @ApiModelProperty("备注")
    @JsonProperty(value = "remark")
    private String remark;

}
