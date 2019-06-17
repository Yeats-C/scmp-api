package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class PurchaseOrderProduct {
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="采购入库商品id")
    @JsonProperty("order_product_id")
    private String orderProductId;

    @ApiModelProperty(value="采购单/入库单id")
    @JsonProperty("order_id")
    private String orderId;

    @ApiModelProperty(value="采购单/入库单编码")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty(value="sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="品牌id")
    @JsonProperty("brand_id")
    private String brandId;

    @ApiModelProperty(value="品牌名称")
    @JsonProperty("brand_name")
    private String brandName;

    @ApiModelProperty(value="品类id")
    @JsonProperty("category_id")
    private String categoryId;

    @ApiModelProperty(value="品类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty(value="规格")
    @JsonProperty("product_spec")
    private String productSpec;

    @ApiModelProperty(value="颜色")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value="型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty(value="商品类型 0赠品 1商品 2实物返回")
    @JsonProperty("product_type")
    private Integer productType;

    @ApiModelProperty(value="采购件数（整数）")
    @JsonProperty("purchase_whole")
    private Integer purchaseWhole;

    @ApiModelProperty(value="采购件数（零数）")
    @JsonProperty("purchase_single")
    private Integer purchaseSingle;

    @ApiModelProperty(value="采购包装数量")
    @JsonProperty("pack_number")
    private Integer packNumber;

    @ApiModelProperty(value="采购包装单位")
    @JsonProperty("pack_unit")
    private String packUnit;

    @ApiModelProperty(value="单品数量")
    @JsonProperty("single_count")
    private Integer singleCount;

    @ApiModelProperty(value="")
    @JsonProperty("tax_rate")
    private Integer taxRate;

    @ApiModelProperty(value="含税单价")
    @JsonProperty("product_amount")
    private Integer productAmount;

    @ApiModelProperty(value="含税总价")
    @JsonProperty("product_total_amount")
    private Integer productTotalAmount;

    @ApiModelProperty(value="实际单品数量")
    @JsonProperty("actual_single_count")
    private Integer actualSingleCount;

    @ApiModelProperty(value="实际含税总价")
    @JsonProperty("actual_total_amount")
    private Integer actualTotalAmount;

    @ApiModelProperty(value="库存数量")
    @JsonProperty("stock_count")
    private Integer stockCount;

    @ApiModelProperty(value="0.采购单  1.入库单")
    @JsonProperty("is_purchase")
    private Integer isPurchase;

    @ApiModelProperty(value="结算方式")
    @JsonProperty("settlement")
    private Integer settlement;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value="创建人")
    @JsonProperty("create_by")
    private String createBy;

    @ApiModelProperty(value="修改人")
    @JsonProperty("update_by")
    private String updateBy;
}