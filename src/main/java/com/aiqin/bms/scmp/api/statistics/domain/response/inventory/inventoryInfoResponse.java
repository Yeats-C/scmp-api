package com.aiqin.bms.scmp.api.statistics.domain.response.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: zhao shuai
 * @create: 2019-09-16
 **/
@Data
public class inventoryInfoResponse {

    @ApiModelProperty(value="仓库code")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="仓库")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value="上期总SKU数量")
    @JsonProperty("pre_total_sku")
    private Long preTotalSku;

    @ApiModelProperty(value="上期库存sku数")
    @JsonProperty("pre_sku_count")
    private Long preSkuCount;

    @ApiModelProperty(value="上期库存sku占比")
    @JsonProperty("pre_sku_rate")
    private BigDecimal preSkuRate;

    @ApiModelProperty(value="上期库存总金额")
    @JsonProperty("pre_stock_amount_total")
    private BigDecimal preStockAmountTotal;

    @ApiModelProperty(value="上期库存金额")
    @JsonProperty("pre_stock_amount")
    private BigDecimal preStockAmount;

    @ApiModelProperty(value="上期库存金额占比")
    @JsonProperty("pre_stock_amount_rate")
    private BigDecimal preStockAmountRate;

    @ApiModelProperty(value="本期总sku数")
    @JsonProperty("total_sku")
    private Long totalSku;

    @ApiModelProperty(value="本期库存sku数")
    @JsonProperty("sku_count")
    private Long skuCount;

    @ApiModelProperty(value="本期库存sku占比")
    @JsonProperty("sku_rate")
    private BigDecimal skuRate;

    @ApiModelProperty(value="本期库存总金额")
    @JsonProperty("stock_amount_total")
    private BigDecimal stockAmountTotal;

    @ApiModelProperty(value="本期库存金额")
    @JsonProperty("stock_amount")
    private BigDecimal stockAmount;

    @ApiModelProperty(value="本期库存金额占比")
    @JsonProperty("stock_amount_rate")
    private BigDecimal stockAmountRate;
}
