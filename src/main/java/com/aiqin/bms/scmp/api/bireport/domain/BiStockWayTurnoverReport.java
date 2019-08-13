package com.aiqin.bms.scmp.api.bireport.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class BiStockWayTurnoverReport {
    @ApiModelProperty(value="id")
    @JsonProperty("id")
    private Integer id;

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

    @ApiModelProperty(value="品牌code")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty(value="品牌名")
    @JsonProperty("product_brand_name")
    private String productBrandName;

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

    @ApiModelProperty(value="状态")
    @JsonProperty("config_status")
    private String configStatus;

    @ApiModelProperty(value="库存数量")
    @JsonProperty("stock_num")
    private Long stockNum;

    @ApiModelProperty(value="在途数量")
    @JsonProperty("on_way")
    private Long onWay;

    @ApiModelProperty(value="销售数量")
    @JsonProperty("sales_num")
    private Long salesNum;

    @ApiModelProperty(value="周转天数")
    @JsonProperty("turnover_days")
    private Long turnoverDays;

}