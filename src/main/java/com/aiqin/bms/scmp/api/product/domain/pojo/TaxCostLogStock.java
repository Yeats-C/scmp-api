package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("sku库存成本日志")
@Data
public class TaxCostLogStock {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Integer id;

    @ApiModelProperty("昨天日期")
    @JsonProperty("tax_date")
    private String taxDate;

    @ApiModelProperty("SKU编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("SKU名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("仓储类型")
    @JsonProperty("warehous_type")
    private Long warehousType;

    @ApiModelProperty("仓储名称")
    @JsonProperty("warehous_name")
    private String warehousName;

    @ApiModelProperty("成本日期总库存数")
    @JsonProperty("stock_sum_num")
    private Long stockSumNum;

    @ApiModelProperty("成本日期含税总成本")
    @JsonProperty("stock_sum_cost")
    private BigDecimal stockSumCost;

    @ApiModelProperty("成本日期含税成本")
    @JsonProperty("stock_tax_cost")
    private BigDecimal stockTaxCost;

    @ApiModelProperty("创建时间")
    @JsonProperty("create_time")
    private Date createTime;

}