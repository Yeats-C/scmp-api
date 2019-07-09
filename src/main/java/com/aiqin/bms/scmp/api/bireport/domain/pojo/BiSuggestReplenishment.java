package com.aiqin.bms.scmp.api.bireport.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("建议补货实体Model")
@Data
public class BiSuggestReplenishment {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("种类")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty("品牌")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("商品编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("商品名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("商品状态")
    @JsonProperty("config_status")
    private int configStatus;

    @ApiModelProperty("仓库")
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

    @ApiModelProperty("品类")
    @JsonProperty("product_property_name")
    private String productPropertyName;

    @ApiModelProperty("是否A类")
    @JsonProperty("is_product_property_name")
    private int isProductPropertyName;

    @ApiModelProperty("是否畅销品")
    @JsonProperty("is_sell")
    private int isSell;

}
