package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class ApplyPurchaseOrderProduct {
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="采购入库商品id")
    @JsonProperty("order_product_id")
    private String orderProductId;

    @ApiModelProperty(value="采购单id")
    @JsonProperty("purchase_order_id")
    private String purchaseOrderId;

    @ApiModelProperty(value="采购单编码")
    @JsonProperty("purchase_order_code")
    private String purchaseOrderCode;

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

    @ApiModelProperty(value="基商品含量")
    @JsonProperty("base_product_content")
    private Integer baseProductContent;

    @ApiModelProperty(value="采购包装")
    @JsonProperty("box_gauge")
    private String boxGauge;

    @ApiModelProperty(value="单品数量")
    @JsonProperty("single_count")
    private Integer singleCount;

    @ApiModelProperty(value="税率")
    @JsonProperty("tax_rate")
    private Integer taxRate;

    @ApiModelProperty(value="含税单价")
    @JsonProperty("product_amount")
    private Integer productAmount;

    @ApiModelProperty(value="含税总价")
    @JsonProperty("product_total_amount")
    private Integer productTotalAmount;

    @ApiModelProperty(value="库存数量")
    @JsonProperty("stock_count")
    private Integer stockCount;

    @ApiModelProperty(value="实际单品数量")
    @JsonProperty("actual_single_count")
    private Integer actualSingleCount;

    @ApiModelProperty(value="厂商SKU编码")
    @JsonProperty("factory_sku_code")
    private String factorySkuCode;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value="创建人id")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="修改人id")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value="创建人名称")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="修改人名称")
    @JsonProperty("update_by_name")
    private String updateByName;

}