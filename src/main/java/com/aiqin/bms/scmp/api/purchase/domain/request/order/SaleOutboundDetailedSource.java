package com.aiqin.bms.scmp.api.purchase.domain.request.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "销售出库推送源数据明细")
@Data
public class SaleOutboundDetailedSource implements Serializable {

    @ApiModelProperty(value="行号")
    @JsonProperty("line_code")
    private String lineCode;

    @ApiModelProperty(value="SKU编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="SKU名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="最小单位数量")
    @JsonProperty("product_count")
    private String productCount;

    @ApiModelProperty(value="分销单价")
    @JsonProperty("product_amount")
    private String productAmount;

    @ApiModelProperty(value="分销总价")
    @JsonProperty("actual_total_product_amount")
    private String actualTotalProductAmount;

    @ApiModelProperty(value="颜色")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value="型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty(value="库存单位编号")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty(value="库存单位名称")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty(value="SKU条码")
    @JsonProperty("sku_bar_code")
    private String skuBarCode;
}
