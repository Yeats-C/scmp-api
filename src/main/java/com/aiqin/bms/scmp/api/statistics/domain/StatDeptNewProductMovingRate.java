package com.aiqin.bms.scmp.api.statistics.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class StatDeptNewProductMovingRate {

    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="年")
    @JsonProperty("stat_year")
    private Long statYear;

    @ApiModelProperty(value="月")
    @JsonProperty("stat_month")
    private Long statMonth;

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

    @ApiModelProperty(value="仓库code")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="仓库名")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value="初始库存sku数")
    @JsonProperty("ini_stock_sku_num")
    private Long iniStockSkuNum;

    @ApiModelProperty(value="初始库存成本")
    @JsonProperty("ini_stock_sku_cost")
    private Long iniStockSkuCost;

    @ApiModelProperty(value="期中新采购sku数")
    @JsonProperty("mid_purchase_sku_num")
    private Long midPurchaseSkuNum;

    @ApiModelProperty(value="期中销售过sku数")
    @JsonProperty("mid_sales_sku_num")
    private Long midSalesSkuNum;

    @ApiModelProperty(value="渠道销售额")
    @JsonProperty("channel_sales_amount")
    private Long channelSalesAmount;

    @ApiModelProperty(value="分销销售额")
    @JsonProperty("distributionsales_amount")
    private Long distributionsalesAmount;

    @ApiModelProperty(value="新品动销率")
    @JsonProperty("new_pro_moving_sales_rate")
    private BigDecimal newProMovingSalesRate;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

}