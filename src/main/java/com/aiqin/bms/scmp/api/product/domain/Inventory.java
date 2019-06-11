package com.aiqin.bms.scmp.api.product.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wuyongqiang
 * @description 库存对象
 * @date 2018/11/20 13:43
 */
@Data
@ApiModel("库存内容")
public class Inventory {
    @ApiModelProperty(value = "所属门店")
    @JsonProperty("store_id")
    private String storeId;

    @ApiModelProperty(value = "商品主键")
    @JsonProperty("product_id")
    private String productId;

    @ApiModelProperty(value = "商品编号")
    @JsonProperty("product_code")
    private String productCode;

    @ApiModelProperty(value = "商品名称")
    @JsonProperty("product_name")
    private String productName;

    @ApiModelProperty(value = "商品SKU码")
    @JsonProperty("product_sku")
    private String productSku;

    @ApiModelProperty(value = "商品分类主键")
    @JsonProperty("product_type_id")
    private String productTypeId;

    @ApiModelProperty(value = "商品分类名称")
    @JsonProperty("product_type_name")
    private String productTypeName;

    @ApiModelProperty(value = "商品条码")
    @JsonProperty("product_barcode")
    private String productBarcode;

    @ApiModelProperty(value = "总库存")
    @JsonProperty("total_storage")
    private Integer totalStorage;

    @ApiModelProperty(value = "可售库存")
    @JsonProperty("sellable_storage")
    private Integer sellableStorage;

    @ApiModelProperty(value = "库存类别，多个类别间使用“,”号隔开")
    @JsonProperty("storage_type")
    private String storageType;

    @ApiModelProperty(value = "商品单价")
    @JsonProperty("product_price")
    private Long productPrice;

    @ApiModelProperty(value = "商品规格")
    @JsonProperty("product_spec")
    private String productSpec;

    @ApiModelProperty(value = "商品单位")
    @JsonProperty("product_unit")
    private String productUnit;


    @ApiModelProperty(value = "颜色名称")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value = "型号")
    @JsonProperty("model_number")
    private String modelNumber;


    @ApiModelProperty(value = "预警状态（1：库存安全、2：低库存、3：停止预警）")
    @JsonProperty("warning_type")
    private Integer warningType;


    @ApiModelProperty(value = "是否预警（0为正常，1为预警）")
    @JsonProperty("warning_status")
    private Integer warningStatus;

    @ApiModelProperty(value = "列表图")
    @JsonProperty("logo")
    private String logo;

    @ApiModelProperty(value = "封面图")
    @JsonProperty("images")
    private String images;

    @ApiModelProperty(value = "详情图")
    @JsonProperty("itro_images")
    private String itroImages;

    @ApiModelProperty(value = "陈列仓位库存")
    @JsonProperty("display_stock")
    private Integer displayStock;

    @ApiModelProperty(value = "锁库库存")
    @JsonProperty("lock_stock")
    private Integer lockStock;

    @ApiModelProperty(value = "预警库存")
    @JsonProperty("warning_stock")
    private Integer warningStock;

    @ApiModelProperty(value = "默认库存预警值(默认值为3)")
    @JsonProperty("default_warning_stock")
    private Integer defaultWarningStock;
}
