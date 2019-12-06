package com.aiqin.bms.scmp.api.statistics.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class StatDeptLowInventoryWeekly {

    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="年")
    @JsonProperty("stat_year")
    private Long statYear;

    @ApiModelProperty(value="周")
    @JsonProperty("stat_week")
    private Long statWeek;

    @ApiModelProperty(value="部门code")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty(value="部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty(value="采购组code")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value="采购组")
    @JsonProperty("purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty(value="采购负责人code")
    @JsonProperty("responsible_person_code")
    private String responsiblePersonCode;

    @ApiModelProperty(value="采购负责人")
    @JsonProperty("responsible_person_name")
    private String responsiblePersonName;

    @ApiModelProperty(value="仓库code")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="仓库")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value="上期总SKU数量")
    @JsonProperty("pre_total_sku")
    private Long preTotalSku;

    @ApiModelProperty(value="上期低库存sku数")
    @JsonProperty("pre_low_sku")
    private Long preLowSku;

    @ApiModelProperty(value="上期低库存sku占比")
    @JsonProperty("pre_low_sku_rate")
    private BigDecimal preLowSkuRate;

    @ApiModelProperty(value="上期库存总金额")
    @JsonProperty("pre_stock_amount_total")
    private BigDecimal preStockAmountTotal;

    @ApiModelProperty(value="上期低库存金额")
    @JsonProperty("pre_low_inventory_amount")
    private BigDecimal preLowInventoryAmount;

    @ApiModelProperty(value="上期低库存金额占比")
    @JsonProperty("pre_low_inventory_amount_rate")
    private BigDecimal preLowInventoryAmountRate;

    @ApiModelProperty(value="总sku数")
    @JsonProperty("total_sku")
    private Long totalSku;

    @ApiModelProperty(value="低库存sku数")
    @JsonProperty("low_sku")
    private Long lowSku;

    @ApiModelProperty(value="低库存sku占比")
    @JsonProperty("low_sku_rate")
    private BigDecimal lowSkuRate;

    @ApiModelProperty(value="库存总金额")
    @JsonProperty("stock_amount_total")
    private BigDecimal stockAmountTotal;

    @ApiModelProperty(value="低库存金额")
    @JsonProperty("low_inventory_amount")
    private BigDecimal lowInventoryAmount;

    @ApiModelProperty(value="低库存金额占比")
    @JsonProperty("low_inventory_amount_rate")
    private BigDecimal lowInventoryAmountRate;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

}