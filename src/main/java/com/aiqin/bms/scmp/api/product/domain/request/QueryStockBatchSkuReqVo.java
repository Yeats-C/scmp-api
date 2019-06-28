package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 *
 * @className QueryStockBatchSkuReqVo
 * @date 2019/6/27 9:24
 * @description 查询批次库存商品sku请求VO
 *
 */
@Data
@ApiModel("查询批次库存商品sku请求VO")
public class QueryStockBatchSkuReqVo extends PageReq implements Serializable {

    @ApiModelProperty("供应商code")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty("物流中心")
    @NotEmpty(message = "物流中心不能为空")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("库房")
    @NotEmpty(message = "库房不能为空")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("采购组")
    @NotEmpty(message = "采购组不能为空")
    @JsonProperty("procurement_section_code")
    private String procurementSectionCode;

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("sku品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty("sku品牌名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("商品属性名称")
    @JsonProperty("product_property_name")
    private String productPropertyName;

    public QueryStockBatchSkuReqVo(String supplierCode, String supplierName, @NotEmpty(message = "物流中心不能为空") String transportCenterCode, @NotEmpty(message = "库房不能为空") String warehouseCode, @NotEmpty(message = "采购组不能为空") String procurementSectionCode, String skuCode, String skuName, String productCategoryName, String productBrandName, String productPropertyName) {
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        this.transportCenterCode = transportCenterCode;
        this.warehouseCode = warehouseCode;
        this.procurementSectionCode = procurementSectionCode;
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.productCategoryName = productCategoryName;
        this.productBrandName = productBrandName;
        this.productPropertyName = productPropertyName;
    }

    public QueryStockBatchSkuReqVo() {
    }
}
