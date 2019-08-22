package com.aiqin.bms.scmp.api.statistics.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2019-08-19 15:48
 **/
@Data
@Api(tags = "统计报表共用属性")
public class ShareResponse {

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

    @ApiModelProperty(value="品牌名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty(value="品类code")
    @JsonProperty("category_code")
    private String categoryCode;

    @ApiModelProperty(value="品类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="库房")
    @JsonProperty("warehouse_name")
    private String warehouseName;

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

    @ApiModelProperty(value="华南仓库编码")
    @JsonProperty("hn_transport_center_code")
    private String hnTransportCenterCode;

    @ApiModelProperty(value="华南仓库名称")
    @JsonProperty("hn_transport_center_name")
    private String hnTransportCenterName;

    @ApiModelProperty(value="西南北仓库编码")
    @JsonProperty("xn_transport_center_code")
    private String xnTransportCenterCode;

    @ApiModelProperty(value="西南仓库名称")
    @JsonProperty("xn_transport_center_name")
    private String xnTransportCenterName;

    @ApiModelProperty(value="华东仓库编码")
    @JsonProperty("hd_transport_center_code")
    private String hdTransportCenterCode;

    @ApiModelProperty(value="华东仓库名称")
    @JsonProperty("hd_transport_center_name")
    private String hdTransportCenterName;

    @ApiModelProperty(value="华中仓库编码")
    @JsonProperty("hz_transport_center_code")
    private String hzTransportCenterCode;

    @ApiModelProperty(value="华中仓库名称")
    @JsonProperty("hz_transport_center_name")
    private String hzTransportCenterName;

}
