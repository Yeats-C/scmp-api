package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2019-08-12
 **/
@Data
public class ProductAndStockRequest extends PagesRequest {

    @ApiModelProperty(value = "开始时间")
    @JsonProperty("begin_time")
    private String beginTime;

    @ApiModelProperty(value = "结束时间")
    @JsonProperty("finish_time")
    private String finishTime;

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

    @ApiModelProperty(value="品牌id")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty(value="一级品类")
    @JsonProperty("lv1")
    private String lv1;

    @ApiModelProperty(value="二级品类")
    @JsonProperty("lv2")
    private String lv2;

    @ApiModelProperty(value="三级品类")
    @JsonProperty("lv3")
    private String lv3;

    @ApiModelProperty(value="四级品类")
    @JsonProperty("lv4")
    private String lv4;

    @ApiModelProperty(value="仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="库存周转天数最小值")
    @JsonProperty("turnover_days_min")
    private Integer turnoverDaysMin;

    @ApiModelProperty(value="库存周转天数最大值")
    @JsonProperty("turnover_days_max")
    private Integer turnoverDaysMax;

    public ProductAndStockRequest(String beginTime, String finishTime, String skuCode, String skuName, String supplierCode,
                                  String productSortCode, String productBrandCode, String lv1, String lv2, String lv3,
                                  String lv4, String transportCenterCode, String warehouseCode, String supplierName) {
        this.beginTime = beginTime;
        this.finishTime = finishTime;
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.supplierCode = supplierCode;
        this.productSortCode = productSortCode;
        this.productBrandCode = productBrandCode;
        this.lv1 = lv1;
        this.lv2 = lv2;
        this.lv3 = lv3;
        this.lv4 = lv4;
        this.transportCenterCode = transportCenterCode;
        this.warehouseCode = warehouseCode;
        this.supplierName = supplierName;
    }

    public ProductAndStockRequest(String beginTime, String finishTime, String skuCode, String skuName, String supplierCode,
                                  String productSortCode, String productBrandCode, String transportCenterCode,
                                  String warehouseCode, Integer turnoverDaysMin, Integer turnoverDaysMax) {
        this.beginTime = beginTime;
        this.finishTime = finishTime;
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.supplierCode = supplierCode;
        this.productSortCode = productSortCode;
        this.productBrandCode = productBrandCode;
        this.transportCenterCode = transportCenterCode;
        this.warehouseCode = warehouseCode;
        this.turnoverDaysMin = turnoverDaysMin;
        this.turnoverDaysMax = turnoverDaysMax;
    }
}