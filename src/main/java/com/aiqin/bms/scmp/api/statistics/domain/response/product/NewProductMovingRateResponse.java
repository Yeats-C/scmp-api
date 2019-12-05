package com.aiqin.bms.scmp.api.statistics.domain.response.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: zhao shuai
 * @create: 2019-09-11
 **/
@Data
public class NewProductMovingRateResponse {

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
    private BigDecimal iniStockSkuCost;

    @ApiModelProperty(value="期中新采购sku数")
    @JsonProperty("mid_purchase_sku_num")
    private Long midPurchaseSkuNum;

    @ApiModelProperty(value="期中销售过sku数")
    @JsonProperty("mid_sales_sku_num")
    private Long midSalesSkuNum;

    @ApiModelProperty(value="渠道销售额")
    @JsonProperty("channel_sales_amount")
    private BigDecimal channelSalesAmount;

    @ApiModelProperty(value="分销销售额")
    @JsonProperty("distributionsales_amount")
    private BigDecimal distributionsalesAmount;

    @ApiModelProperty(value="新品动销率")
    @JsonProperty("new_pro_moving_sales_rate")
    private BigDecimal newProMovingSalesRate;
}
