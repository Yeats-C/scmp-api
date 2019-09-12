package com.aiqin.bms.scmp.api.statistics.domain.response.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-09-11
 **/
@Data
public class ProductMovableResponse {

    @ApiModelProperty(value="新品动销的子集")
    @JsonProperty("subset_list")
    private List<ProductMovableResponse> subsetList;

    @ApiModelProperty(value="所属部门code")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty(value="所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty(value="品类code")
    @JsonProperty("lv1")
    private String lv1;

    @ApiModelProperty(value="品类")
    @JsonProperty("lv1_category_name")
    private String lv1CategoryName;

    @ApiModelProperty(value="渠道code")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    @ApiModelProperty(value="渠道名")
    @JsonProperty("price_channel_name")
    private String priceChannelName;

    @ApiModelProperty(value="全国初始库存sku数")
    @JsonProperty("sum_ini_stock_sku_num")
    private Long sumIniStockSkuNum;

    @ApiModelProperty(value="全国初始库存成本")
    @JsonProperty("sum_ini_stock_sku_cost")
    private Long sumIniStockSkuCost;

    @ApiModelProperty(value="全国期中新采购sku数")
    @JsonProperty("sum_mid_purchase_sku_num")
    private Long sumMidPurchaseSkuNum;

    @ApiModelProperty(value="全国期中销售过sku数")
    @JsonProperty("sum_mid_sales_sku_num")
    private Long sumMidSalesSkuNum;

    @ApiModelProperty(value="全国渠道销售额")
    @JsonProperty("sum_channel_sales_amount")
    private Long sumChannelSalesAmount;

    @ApiModelProperty(value="全国分销销售额")
    @JsonProperty("sum_distributionsales_amount")
    private Long sumDistributionsalesAmount;

    @ApiModelProperty(value="全国新品动销率")
    @JsonProperty("sum_new_pro_moving_sales_rate")
    private BigDecimal sumNewProMovingSalesRate;

    @ApiModelProperty(value="华北初始库存sku数")
    @JsonProperty("hb_ini_stock_sku_num")
    private Long hbIniStockSkuNum;

    @ApiModelProperty(value="华北初始库存成本")
    @JsonProperty("hb_ini_stock_sku_cost")
    private Long hbIniStockSkuCost;

    @ApiModelProperty(value="华北期中新采购sku数")
    @JsonProperty("hb_mid_purchase_sku_num")
    private Long hbMidPurchaseSkuNum;

    @ApiModelProperty(value="华北期中销售过sku数")
    @JsonProperty("hb_mid_sales_sku_num")
    private Long hbMidSalesSkuNum;

    @ApiModelProperty(value="华北渠道销售额")
    @JsonProperty("hb_channel_sales_amount")
    private Long hbChannelSalesAmount;

    @ApiModelProperty(value="华北分销销售额")
    @JsonProperty("hb_distributionsales_amount")
    private Long hbDistributionsalesAmount;

    @ApiModelProperty(value="华北新品动销率")
    @JsonProperty("hb_new_pro_moving_sales_rate")
    private BigDecimal hbNewProMovingSalesRate;

    @ApiModelProperty(value="华东初始库存sku数")
    @JsonProperty("hd_ini_stock_sku_num")
    private Long hdIniStockSkuNum;

    @ApiModelProperty(value="华东初始库存成本")
    @JsonProperty("hd_ini_stock_sku_cost")
    private Long hdIniStockSkuCost;

    @ApiModelProperty(value="华东期中新采购sku数")
    @JsonProperty("hd_mid_purchase_sku_num")
    private Long hdMidPurchaseSkuNum;

    @ApiModelProperty(value="华东期中销售过sku数")
    @JsonProperty("hd_mid_sales_sku_num")
    private Long hdMidSalesSkuNum;

    @ApiModelProperty(value="华东渠道销售额")
    @JsonProperty("hd_channel_sales_amount")
    private Long hdChannelSalesAmount;

    @ApiModelProperty(value="华东分销销售额")
    @JsonProperty("hd_distributionsales_amount")
    private Long hdDistributionsalesAmount;

    @ApiModelProperty(value="华东新品动销率")
    @JsonProperty("hd_new_pro_moving_sales_rate")
    private BigDecimal hdNewProMovingSalesRate;

    @ApiModelProperty(value="华南初始库存sku数")
    @JsonProperty("hn_ini_stock_sku_num")
    private Long hnIniStockSkuNum;

    @ApiModelProperty(value="华南初始库存成本")
    @JsonProperty("hn_ini_stock_sku_cost")
    private Long hnIniStockSkuCost;

    @ApiModelProperty(value="华南期中新采购sku数")
    @JsonProperty("hn_mid_purchase_sku_num")
    private Long hnMidPurchaseSkuNum;

    @ApiModelProperty(value="华南期中销售过sku数")
    @JsonProperty("hn_mid_sales_sku_num")
    private Long hnMidSalesSkuNum;

    @ApiModelProperty(value="华南渠道销售额")
    @JsonProperty("hn_channel_sales_amount")
    private Long hnChannelSalesAmount;

    @ApiModelProperty(value="华南分销销售额")
    @JsonProperty("hn_distributionsales_amount")
    private Long hnDistributionsalesAmount;

    @ApiModelProperty(value="华南新品动销率")
    @JsonProperty("hn_new_pro_moving_sales_rate")
    private BigDecimal hnNewProMovingSalesRate;

    @ApiModelProperty(value="西南初始库存sku数")
    @JsonProperty("xn_ini_stock_sku_num")
    private Long xnIniStockSkuNum;

    @ApiModelProperty(value="西南初始库存成本")
    @JsonProperty("xn_ini_stock_sku_cost")
    private Long xnIniStockSkuCost;

    @ApiModelProperty(value="西南期中新采购sku数")
    @JsonProperty("xn_mid_purchase_sku_num")
    private Long xnMidPurchaseSkuNum;

    @ApiModelProperty(value="西南期中销售过sku数")
    @JsonProperty("xn_mid_sales_sku_num")
    private Long xnMidSalesSkuNum;

    @ApiModelProperty(value="西南渠道销售额")
    @JsonProperty("xn_channel_sales_amount")
    private Long xnChannelSalesAmount;

    @ApiModelProperty(value="西南分销销售额")
    @JsonProperty("xn_distributionsales_amount")
    private Long xnDistributionsalesAmount;

    @ApiModelProperty(value="西南新品动销率")
    @JsonProperty("xn_new_pro_moving_sales_rate")
    private BigDecimal xnNewProMovingSalesRate;

    @ApiModelProperty(value="华中初始库存sku数")
    @JsonProperty("hz_ini_stock_sku_num")
    private Long hzIniStockSkuNum;

    @ApiModelProperty(value="华中初始库存成本")
    @JsonProperty("hz_ini_stock_sku_cost")
    private Long hzIniStockSkuCost;

    @ApiModelProperty(value="华中期中新采购sku数")
    @JsonProperty("hz_mid_purchase_sku_num")
    private Long hzMidPurchaseSkuNum;

    @ApiModelProperty(value="华中期中销售过sku数")
    @JsonProperty("hz_mid_sales_sku_num")
    private Long hzMidSalesSkuNum;

    @ApiModelProperty(value="华中渠道销售额")
    @JsonProperty("hz_channel_sales_amount")
    private Long hzChannelSalesAmount;

    @ApiModelProperty(value="华中分销销售额")
    @JsonProperty("hz_distributionsales_amount")
    private Long hzDistributionsalesAmount;

    @ApiModelProperty(value="华中新品动销率")
    @JsonProperty("hz_new_pro_moving_sales_rate")
    private BigDecimal hzNewProMovingSalesRate;

}
