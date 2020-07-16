package com.aiqin.bms.scmp.api.product.domain.response.stock;

import com.aiqin.bms.scmp.api.product.domain.pojo.StockBatch;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2020-03-27
 **/
@Data
public class StockBatchResponse extends StockBatch {

    @ApiModelProperty(value="状态 0:再用 1:停止进货 2:停止配送 3:停止销售")
    @JsonProperty("config_status")
    private Integer configStatus;

    @ApiModelProperty(value="供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="规格")
    @JsonProperty("product_spec")
    private String productSpec;

    @ApiModelProperty(value="颜色")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value="型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty(value="品牌")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty(value="品牌名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty(value="品类")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty(value="品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty(value="商品属性")
    @JsonProperty("product_property_code")
    private String productPropertyCode;

    @ApiModelProperty(value="商品属性名称")
    @JsonProperty("product_property_name")
    private String productPropertyName;

    @ApiModelProperty(value="采购组")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value="采购组名称")
    @JsonProperty("purchase_group_Name")
    private String purchaseGroupName;

    @ApiModelProperty(value="单位")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty(value="包装")
    @JsonProperty("box_gauge")
    private String boxGauge;

}
