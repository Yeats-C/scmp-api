package com.aiqin.bms.scmp.api.statistics.domain.response.product;

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
public class ProductStockOutResponse {

    @ApiModelProperty(value="缺货统计的子集")
    @JsonProperty("subset_list")
    private List<ProductStockOutResponse> subsetList;

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

    @ApiModelProperty(value="全国总sku数")
    @JsonProperty("sum_sku_num_total")
    private Long sumSkuNumTotal;

    @ApiModelProperty(value="全国缺货sku数")
    @JsonProperty("sum_stockout_sku_num")
    private Long sumStockoutSkuNum;

    @ApiModelProperty(value="全国缺货占比")
    @JsonProperty("sum_stockout_rate")
    private BigDecimal sumStockoutRate;

    @ApiModelProperty(value="全国缺货影响金额")
    @JsonProperty("sum_stockout_effect_amount")
    private Long sumStockoutEffectAmount;

    @ApiModelProperty(value="华北总sku数")
    @JsonProperty("hb_sku_num_total")
    private Long hbSkuNumTotal;

    @ApiModelProperty(value="华北缺货sku数")
    @JsonProperty("hb_stockout_sku_num")
    private Long hbStockoutSkuNum;

    @ApiModelProperty(value="华北缺货占比")
    @JsonProperty("hb_stockout_rate")
    private BigDecimal hbStockoutRate;

    @ApiModelProperty(value="华北缺货影响金额")
    @JsonProperty("hb_stockout_effect_amount")
    private Long hbStockoutEffectAmount;

    @ApiModelProperty(value="华东总sku数")
    @JsonProperty("hd_sku_num_total")
    private Long hdSkuNumTotal;

    @ApiModelProperty(value="华东缺货sku数")
    @JsonProperty("hd_stockout_sku_num")
    private Long hdStockoutSkuNum;

    @ApiModelProperty(value="华东缺货占比")
    @JsonProperty("hd_stockout_rate")
    private BigDecimal hdStockoutRate;

    @ApiModelProperty(value="华东缺货影响金额")
    @JsonProperty("hd_stockout_effect_amount")
    private Long hdStockoutEffectAmount;

    @ApiModelProperty(value="华南总sku数")
    @JsonProperty("hn_sku_num_total")
    private Long hnSkuNumTotal;

    @ApiModelProperty(value="华南缺货sku数")
    @JsonProperty("hn_stockout_sku_num")
    private Long hnStockoutSkuNum;

    @ApiModelProperty(value="华南缺货占比")
    @JsonProperty("hn_stockout_rate")
    private BigDecimal hnStockoutRate;

    @ApiModelProperty(value="华南缺货影响金额")
    @JsonProperty("hn_stockout_effect_amount")
    private Long hnStockoutEffectAmount;

    @ApiModelProperty(value="西南总sku数")
    @JsonProperty("xn_sku_num_total")
    private Long xnSkuNumTotal;

    @ApiModelProperty(value="西南缺货sku数")
    @JsonProperty("xn_stockout_sku_num")
    private Long xnStockoutSkuNum;

    @ApiModelProperty(value="西南缺货占比")
    @JsonProperty("xn_stockout_rate")
    private BigDecimal xnStockoutRate;

    @ApiModelProperty(value="西南缺货影响金额")
    @JsonProperty("xn_stockout_effect_amount")
    private Long xnStockoutEffectAmount;

    @ApiModelProperty(value="华中总sku数")
    @JsonProperty("hz_sku_num_total")
    private Long hzSkuNumTotal;

    @ApiModelProperty(value="华中缺货sku数")
    @JsonProperty("hz_stockout_sku_num")
    private Long hzStockoutSkuNum;

    @ApiModelProperty(value="华中缺货占比")
    @JsonProperty("hz_stockout_rate")
    private BigDecimal hzStockoutRate;

    @ApiModelProperty(value="华中缺货影响金额")
    @JsonProperty("hz_stockout_effect_amount")
    private Long hzStockoutEffectAmount;

}
