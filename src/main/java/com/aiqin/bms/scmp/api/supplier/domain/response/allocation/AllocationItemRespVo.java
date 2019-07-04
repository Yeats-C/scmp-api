package com.aiqin.bms.scmp.api.supplier.domain.response.allocation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述:调拨单sku导入返回实体
 *
 * @Author: Kt.w
 * @Date: 2019/1/11
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("调拨单sku导入返回实体")
public class AllocationItemRespVo {
    @ApiModelProperty("sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("商品code")
    @JsonProperty("spu_code")
    private String productCode;

    @ApiModelProperty("商品名称")
    @JsonProperty("spu_name")
    private String productName;

    @ApiModelProperty("商品类别code")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("商品类别名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty("商品类型名称")
    @JsonProperty("goods_gifts_name")
    private String productTypeName;

    @ApiModelProperty("商品类型编码")
    @JsonProperty("goods_gifts")
    private String productTypeCode;

    @ApiModelProperty("商品品牌编码")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("商品品牌名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("单位编码")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty("单位名称")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty("颜色编码")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty("颜色名称")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty("商品类别编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("商品类别名字")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("税率")
    @JsonProperty("tax_rate")
    private Long taxRate;

    @ApiModelProperty("昨天含税成本")
    @JsonProperty("tax_cost")
    private Long price;

    @ApiModelProperty("库存")
    @JsonProperty("available_num")
    private Long stockNum;

    @ApiModelProperty("数量")
    @JsonProperty("quantity")
    private Long number;

    @ApiModelProperty("含税总成本")
    @JsonProperty("total_tax_cost")
    private Long totalPrice;

    @ApiModelProperty("型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty("错误原因")
    private String errorReason;

     public AllocationItemRespVo(){}

    public AllocationItemRespVo(String skuCode,String skuName, String errorReason) {
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.errorReason = errorReason;
    }
}
