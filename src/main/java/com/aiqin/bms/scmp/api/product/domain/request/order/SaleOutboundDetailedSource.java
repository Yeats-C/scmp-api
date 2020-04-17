package com.aiqin.bms.scmp.api.product.domain.request.order;

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
    /****/
    @ApiModelProperty(value="Sku编码")
    @JsonProperty("sku")
    private String sku;

    @ApiModelProperty(value="数量")
    @JsonProperty("qty")
    private Integer qty;

    @ApiModelProperty(value="单价")
    @JsonProperty("price")
    private Double price;

    @ApiModelProperty(value="实际金额")
    @JsonProperty("actual_amount")
    private Double actualAmount;

    @ApiModelProperty(value="批次编码")
    @JsonProperty("batchCode")
    private String batchcode;

    @ApiModelProperty(value = "备用字段1 销售单号")
    @JsonProperty("gwf1")
    private String gwf1;

    @ApiModelProperty(value = "备用字段2 Sku行号")
    @JsonProperty("gwf2")
    private String gwf2;

    @ApiModelProperty(value = "备用字段3 颜色")
    @JsonProperty("gwf3")
    private String gwf3;

    @ApiModelProperty(value = "备用字段4 尺码")
    @JsonProperty("gwf4")
    private String gwf4;

    @ApiModelProperty(value = "备用字段5 分销单位")
    @JsonProperty("gwf5")
    private String gwf5;

    /**加上的**/

    /*@ApiModelProperty(value="销售单号")
    @JsonProperty("sale_num")
    private String saleNum;

    @ApiModelProperty(value="Sku行号")
    @JsonProperty("line_num")
    private String lineNum;

    @ApiModelProperty(value="颜色")
    @JsonProperty("sku_colour")
    private String skuColour;

    @ApiModelProperty(value="尺码")
    @JsonProperty("sku_size")
    private String skuSzie;

    @ApiModelProperty(value="分销单位")
    @JsonProperty("distribution_unit")
    private String distributionUnit;*/
}
