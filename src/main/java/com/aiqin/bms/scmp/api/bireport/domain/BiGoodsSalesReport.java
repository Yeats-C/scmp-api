package com.aiqin.bms.scmp.api.bireport.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel
@Data
public class BiGoodsSalesReport {
    @ApiModelProperty(value="id")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="年月日")
    @JsonProperty("stat_date")
    private String statDate;

    @ApiModelProperty(value="sku_code")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="sku_name")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="供应商code")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="供应商")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="部门code")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty(value="部门名")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty(value="一级品类code")
    @JsonProperty("lv1")
    private String lv1;

    @ApiModelProperty(value="一级品类name")
    @JsonProperty("lv1_category_name")
    private String lv1CategoryName;

    @ApiModelProperty(value="二级品类code")
    @JsonProperty("lv2")
    private String lv2;

    @ApiModelProperty(value="二级品类name")
    @JsonProperty("lv2_category_name")
    private String lv2CategoryName;

    @ApiModelProperty(value="三级品类code")
    @JsonProperty("lv3")
    private String lv3;

    @ApiModelProperty(value="三级品类name")
    @JsonProperty("lv3_category_name")
    private String lv3CategoryName;

    @ApiModelProperty(value="四级品类code")
    @JsonProperty("lv4")
    private String lv4;

    @ApiModelProperty(value="四级品类name")
    @JsonProperty("lv4_category_name")
    private String lv4CategoryName;

    @ApiModelProperty(value="仓库code")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="仓库")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value="库房code")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="库房")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value="品牌code")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty(value="品牌名")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty(value="售价")
    @JsonProperty("channel_unit_price")
    private BigDecimal channelUnitPrice;

    @ApiModelProperty(value="销售数量")
    @JsonProperty("sales_num")
    private Long salesNum;

    @ApiModelProperty(value="实销金额")
    @JsonProperty("real_sales_amount")
    private Long realSalesAmount;

    @ApiModelProperty(value="实销毛利")
    @JsonProperty("real_sales_margin")
    private Long realSalesMargin;

    @ApiModelProperty(value="毛利率")
    @JsonProperty("margin_rate")
    private BigDecimal marginRate;

    @ApiModelProperty(value="同比销售数量")
    @JsonProperty("sales_num_yearonyear")
    private Long salesNumYearonyear;

    @ApiModelProperty(value="同比实销金额")
    @JsonProperty("real_sales_amount_yearonyear")
    private Long realSalesAmountYearonyear;

    @ApiModelProperty(value="同比实销毛利")
    @JsonProperty("real_sales_margin_yearonyear")
    private Long realSalesMarginYearonyear;

    @ApiModelProperty(value="同比毛利率")
    @JsonProperty("margin_rate_yearonyear")
    private BigDecimal marginRateYearonyear;

    @ApiModelProperty(value="同比销售数量增长率")
    @JsonProperty("sales_num_yearonyear_growth_rate")
    private BigDecimal salesNumYearonyearGrowthRate;

    @ApiModelProperty(value="同比实销金额增长率")
    @JsonProperty("real_sales_amount_yearonyear_growth_rate")
    private BigDecimal realSalesAmountYearonyearGrowthRate;

    @ApiModelProperty(value="同比实销毛利增长率")
    @JsonProperty("real_sales_margin_yearonyear_growth_rate")
    private Long realSalesMarginYearonyearGrowthRate;

    @ApiModelProperty(value="同比毛利率增长率")
    @JsonProperty("margin_rate_yearonyear_growth_rate")
    private BigDecimal marginRateYearonyearGrowthRate;

}