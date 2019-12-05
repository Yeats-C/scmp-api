package com.aiqin.bms.scmp.api.statistics.domain.response.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-09-16
 **/
@Data
public class InventoryStatisticsResponse {

    @ApiModelProperty(value="库存的子集")
    @JsonProperty("subset_list")
    private List<InventoryStatisticsResponse> subsetList;

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

    @ApiModelProperty(value="上期全国总SKU数量")
    @JsonProperty("pre_total_sku")
    private Long preTotalSku;

    @ApiModelProperty(value="上期全国库存sku数")
    @JsonProperty("pre_sku_count")
    private Long preSkuCount;

    @ApiModelProperty(value="上期全国库存sku占比")
    @JsonProperty("pre_sku_rate")
    private BigDecimal preSkuRate;

    @ApiModelProperty(value="上期全国库存总金额")
    @JsonProperty("pre_stock_amount_total")
    private BigDecimal preStockAmountTotal;

    @ApiModelProperty(value="上期全国库存金额")
    @JsonProperty("pre_stock_amount")
    private BigDecimal preStockAmount;

    @ApiModelProperty(value="上期全国库存金额占比")
    @JsonProperty("pre_stock_amount_rate")
    private BigDecimal preStockAmountRate;

    @ApiModelProperty(value="本期全国总sku数")
    @JsonProperty("total_sku")
    private Long totalSku;

    @ApiModelProperty(value="本期全国库存sku数")
    @JsonProperty("sku_count")
    private Long skuCount;

    @ApiModelProperty(value="本期全国库存sku占比")
    @JsonProperty("sku_rate")
    private BigDecimal skuRate;

    @ApiModelProperty(value="本期全国库存总金额")
    @JsonProperty("stock_amount_total")
    private BigDecimal stockAmountTotal;

    @ApiModelProperty(value="本期全国库存金额")
    @JsonProperty("stock_amount")
    private BigDecimal stockAmount;

    @ApiModelProperty(value="本期全国库存金额占比")
    @JsonProperty("stock_amount_rate")
    private BigDecimal stockAmountRate;

    @ApiModelProperty(value="华北总sku数")
    @JsonProperty("hb_total_sku")
    private Long hbTotalSku;

    @ApiModelProperty(value="华北库存sku数")
    @JsonProperty("hb_sku_count")
    private Long hbSkuCount;

    @ApiModelProperty(value="华北库存sku占比")
    @JsonProperty("hb_sku_rate")
    private BigDecimal hbSkuRate;

    @ApiModelProperty(value="华北库存总金额")
    @JsonProperty("hb_stock_amount_total")
    private BigDecimal hbStockAmountTotal;

    @ApiModelProperty(value="华北库存金额")
    @JsonProperty("hb_stock_amount")
    private BigDecimal hbStockAmount;

    @ApiModelProperty(value="华北库存金额占比")
    @JsonProperty("hb_stock_amount_rate")
    private BigDecimal hbStockAmountRate;

    @ApiModelProperty(value="华东总sku数")
    @JsonProperty("hd_total_sku")
    private Long hdTotalSku;

    @ApiModelProperty(value="华东库存sku数")
    @JsonProperty("hd_sku_count")
    private Long hdSkuCount;

    @ApiModelProperty(value="华东库存sku占比")
    @JsonProperty("hd_sku_rate")
    private BigDecimal hdSkuRate;

    @ApiModelProperty(value="华东库存总金额")
    @JsonProperty("hd_stock_amount_total")
    private BigDecimal hdStockAmountTotal;

    @ApiModelProperty(value="华东库存金额")
    @JsonProperty("hd_stock_amount")
    private BigDecimal hdStockAmount;

    @ApiModelProperty(value="华东库存金额占比")
    @JsonProperty("hd_stock_amount_rate")
    private BigDecimal hdStockAmountRate;

    @ApiModelProperty(value="华中总sku数")
    @JsonProperty("hz_total_sku")
    private Long hzTotalSku;

    @ApiModelProperty(value="华中库存sku数")
    @JsonProperty("hz_sku_count")
    private Long hzSkuCount;

    @ApiModelProperty(value="华中库存sku占比")
    @JsonProperty("hz_sku_rate")
    private BigDecimal hzSkuRate;

    @ApiModelProperty(value="华中库存总金额")
    @JsonProperty("hz_stock_amount_total")
    private BigDecimal hzStockAmountTotal;

    @ApiModelProperty(value="华中库存金额")
    @JsonProperty("hz_stock_amount")
    private BigDecimal hzStockAmount;

    @ApiModelProperty(value="华中库存金额占比")
    @JsonProperty("hz_stock_amount_rate")
    private BigDecimal hzStockAmountRate;

    @ApiModelProperty(value="西南总sku数")
    @JsonProperty("xn_total_sku")
    private Long xnTotalSku;

    @ApiModelProperty(value="西南库存sku数")
    @JsonProperty("xn_sku_count")
    private Long xnSkuCount;

    @ApiModelProperty(value="西南库存sku占比")
    @JsonProperty("xn_sku_rate")
    private BigDecimal xnSkuRate;

    @ApiModelProperty(value="西南库存总金额")
    @JsonProperty("xn_stock_amount_total")
    private BigDecimal xnStockAmountTotal;

    @ApiModelProperty(value="西南库存金额")
    @JsonProperty("xn_stock_amount")
    private BigDecimal xnStockAmount;

    @ApiModelProperty(value="西南库存金额占比")
    @JsonProperty("xn_stock_amount_rate")
    private BigDecimal xnStockAmountRate;

    @ApiModelProperty(value="华南总sku数")
    @JsonProperty("hn_total_sku")
    private Long hnTotalSku;

    @ApiModelProperty(value="华南库存sku数")
    @JsonProperty("hn_sku_count")
    private Long hnSkuCount;

    @ApiModelProperty(value="华南库存sku占比")
    @JsonProperty("hn_sku_rate")
    private BigDecimal hnSkuRate;

    @ApiModelProperty(value="华南库存总金额")
    @JsonProperty("hn_stock_amount_total")
    private BigDecimal hnStockAmountTotal;

    @ApiModelProperty(value="华南库存金额")
    @JsonProperty("hn_stock_amount")
    private BigDecimal hnStockAmount;

    @ApiModelProperty(value="华南库存金额占比")
    @JsonProperty("hn_stock_amount_rate")
    private BigDecimal hnStockAmountRate;
}
