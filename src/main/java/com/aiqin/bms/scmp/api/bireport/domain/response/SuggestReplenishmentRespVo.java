package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

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

    @ApiModelProperty("库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("近三月销量总量")
    @JsonProperty("sales_3_months_num")
    private Long sales3monthsNum;

    @ApiModelProperty("近三月平均日销量")
    @JsonProperty("sales_avg_3_month_num")
    private Double salesAvg3MonthNum;

    @ApiModelProperty("可用库存数量")
    @JsonProperty("available_num")
    private Long availableNum;

    @ApiModelProperty("库存金额")
    @JsonProperty("stock_amount")
    private Long stockAmount;

    @ApiModelProperty("周转天数")
    @JsonProperty("days_turnover")
    private Double daysTurnover;

    @ApiModelProperty("连续缺货天数")
    @JsonProperty("stockout_days")
    private Long stockoutDays;

    @ApiModelProperty("缺货影响金额")
    @JsonProperty("out_stock_affects_amount")
    private Long outStockAffectsAmount;

    @ApiModelProperty("近一月内缺货天数")
    @JsonProperty("stock_one_month_days")
    private Long stockOneMonthDays;

    @ApiModelProperty("建议订货量")
    @JsonProperty("suggested_order_num")
    private Long suggestedOrderNum;

    @ApiModelProperty("商品状态(0:在用 1:停止进货 2:停止配送 3:停止销售)")
    @JsonProperty("config_status")
    private int configStatus;

    @ApiModelProperty("预计到货天数")
    @JsonProperty("expected_days_arrival")
    private Long expectedDaysArrival;

    @ApiModelProperty("商品属性编码")
    @JsonProperty("product_property_code")
    private String productPropertyCode;

    @ApiModelProperty("商品属性")
    @JsonProperty("product_property_name")
    private String productPropertyName;

    @ApiModelProperty("计算时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty("返回列名")
    @JsonProperty("column_list")
    private List<Map> columnList;

}
