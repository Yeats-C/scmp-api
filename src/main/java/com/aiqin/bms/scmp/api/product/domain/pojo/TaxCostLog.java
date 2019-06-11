package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("库存成本日志")
@Data
public class TaxCostLog {
    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Integer id;

    @ApiModelProperty("昨天日期")
    @JsonProperty("tax_date")
    private String taxDate;

    @ApiModelProperty("库存编码")
    @JsonProperty("stock_code")
    private String stockCode;

    @ApiModelProperty("SKU编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("SKU名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("仓编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("昨天库存数")
    @JsonProperty("stock_num")
    private Long stockNum;

    @ApiModelProperty("库存相对前天的变化数")
    @JsonProperty("stock_change_num")
    private Long stockChangeNum;

    @ApiModelProperty("昨天采购价")
    @JsonProperty("new_purchase_price")
    private Long newPurchasePrice;

    @ApiModelProperty("前天含税成本价")
    @JsonProperty("tax_cost_last_day")
    private Long taxCostLastDay;

    @ApiModelProperty("昨天含税成本价")
    @JsonProperty("tax_cost")
    private Long taxCost;

    @ApiModelProperty("创建时间")
    @JsonProperty("create_time")
    private Date createTime;
}
