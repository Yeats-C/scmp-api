package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2019-08-12
 **/
@Data
public class ProductAndStockResponse{

    @ApiModelProperty(value="年月日")
    @JsonProperty("stat_date")
    private String statDate;

    @ApiModelProperty(value = "sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value = "sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value = "供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value = "供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value = "所属部门")
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

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="库房")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value="状态  0.在用 1.停止进货 2.停止配送  3.停止销售")
    @JsonProperty("config_status")
    private String configStatus;

    @ApiModelProperty(value="仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value="华北仓库编码")
    @JsonProperty("hb_transport_center_code")
    private String hbTransportCenterCode;

    @ApiModelProperty(value="华北仓库名称")
    @JsonProperty("hb_transport_center_name")
    private String hbTransportCenterName;

    @ApiModelProperty(value="华北库存数量")
    @JsonProperty("hb_stock_num")
    private Long hbStockNum;

    @ApiModelProperty(value="华北销售数量")
    @JsonProperty("hb_sales_num")
    private Long hbSalesNum;

    @ApiModelProperty(value="华北周转天数")
    @JsonProperty("hb_turnover_days")
    private Long hbTurnoverDays;

    @ApiModelProperty(value="华北在途数")
    @JsonProperty("hb_on_way")
    private Long hbOnWay;

    @ApiModelProperty(value="华南仓库编码")
    @JsonProperty("hn_transport_center_code")
    private String hnTransportCenterCode;

    @ApiModelProperty(value="华南仓库名称")
    @JsonProperty("hn_transport_center_name")
    private String hnTransportCenterName;

    @ApiModelProperty(value="华南库存数量")
    @JsonProperty("hn_stock_num")
    private Long hnStockNum;

    @ApiModelProperty(value="华南销售数量")
    @JsonProperty("hn_sales_num")
    private Long hnSalesNum;

    @ApiModelProperty(value="华南周转天数")
    @JsonProperty("hn_turnover_days")
    private Long hnTurnoverDays;

    @ApiModelProperty(value="华南在途数")
    @JsonProperty("hn_on_way")
    private Long hnOnWay;

    @ApiModelProperty(value="西南北仓库编码")
    @JsonProperty("xn_transport_center_code")
    private String xnTransportCenterCode;

    @ApiModelProperty(value="西南仓库名称")
    @JsonProperty("xn_transport_center_name")
    private String xnTransportCenterName;

    @ApiModelProperty(value="西南库存数量")
    @JsonProperty("xn_stock_num")
    private Long xnStockNum;

    @ApiModelProperty(value="西南销售数量")
    @JsonProperty("xn_sales_num")
    private Long xnSalesNum;

    @ApiModelProperty(value="西南周转天数")
    @JsonProperty("xn_turnover_days")
    private Long xnTurnoverDays;

    @ApiModelProperty(value="西南在途数")
    @JsonProperty("xn_on_way")
    private Long xnOnWay;

    @ApiModelProperty(value="华东仓库编码")
    @JsonProperty("hd_transport_center_code")
    private String hdTransportCenterCode;

    @ApiModelProperty(value="华东仓库名称")
    @JsonProperty("hd_transport_center_name")
    private String hdTransportCenterName;

    @ApiModelProperty(value="华东库存数量")
    @JsonProperty("hd_stock_num")
    private Long hdStockNum;

    @ApiModelProperty(value="华东销售数量")
    @JsonProperty("hd_sales_num")
    private Long hdSalesNum;

    @ApiModelProperty(value="华东周转天数")
    @JsonProperty("hd_turnover_days")
    private Long hdTurnoverDays;

    @ApiModelProperty(value="华东在途数")
    @JsonProperty("hd_on_way")
    private Long hdOnWay;

    @ApiModelProperty(value="华中仓库编码")
    @JsonProperty("hz_transport_center_code")
    private String hzTransportCenterCode;

    @ApiModelProperty(value="华中仓库名称")
    @JsonProperty("hz_transport_center_name")
    private String hzTransportCenterName;

    @ApiModelProperty(value="华中库存数量")
    @JsonProperty("hz_stock_num")
    private Long hzStockNum;

    @ApiModelProperty(value="华中销售数量")
    @JsonProperty("hz_sales_num")
    private Long hzSalesNum;

    @ApiModelProperty(value="华中周转天数")
    @JsonProperty("hz_turnover_days")
    private Long hzTurnoverDays;

    @ApiModelProperty(value="华中在途数")
    @JsonProperty("hz_on_way")
    private Long hzOnWay;

    @ApiModelProperty(value="总库存数量")
    @JsonProperty("sum_stock_num")
    private Long sumStockNum;

    @ApiModelProperty(value="总销售数量")
    @JsonProperty("sum_sales_num")
    private Long sumSalesNum;

    @ApiModelProperty(value="总周转天数")
    @JsonProperty("sum_turnover_days")
    private Long sumTurnoverDays;

}