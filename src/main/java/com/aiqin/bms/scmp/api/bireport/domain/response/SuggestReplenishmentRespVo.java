package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("建议补货respVo")
@Data
public class SuggestReplenishmentRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("品类编码")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty("品牌编码")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("品牌名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("商品编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("商品名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("近三月平均日销量")
    @JsonProperty("average_amount")
    private Long averageAmount;

    @ApiModelProperty("库存数量")
    @JsonProperty("inventory_num")
    private Long inventoryNum;

    @ApiModelProperty("库存金额")
    @JsonProperty("inventory_money")
    private Long inventoryMoney;

    @ApiModelProperty("周转天数")
    @JsonProperty("turnover_days")
    private Long turnoverDays;

    @ApiModelProperty("连续缺货天数")
    @JsonProperty("out_stock_continuous_days")
    private Long outStockContinuousDays;

    @ApiModelProperty("近一月内缺货天数")
    @JsonProperty("last_month_out_stock_days")
    private Long lastMonthOutStockDays;

    @ApiModelProperty("建议订货量")
    @JsonProperty("advice_orders")
    private Long adviceOrders;

    @ApiModelProperty("商品状态(0:再用 1:停止进货 2:停止配送 3:停止销售)")
    @JsonProperty("config_status")
    private int configStatus;

}
