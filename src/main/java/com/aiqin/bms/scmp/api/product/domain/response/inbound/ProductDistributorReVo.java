package com.aiqin.bms.scmp.api.product.domain.response.inbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述:
 *
 * @author zhujunchao
 * @create 2019-03-19 10:51
 */
@Data
@ApiModel("盘点商品信息返回vo")
public class ProductDistributorReVo  {
    @ApiModelProperty(value = "spu_code")
    @JsonProperty("spu_code")
    private String spuCode;

    @ApiModelProperty(value = "sku_code")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value = "分销机构id")
    @JsonProperty("distributor_id")
    private String distributorId;

    @ApiModelProperty(value = "分销机构编码")
    @JsonProperty("distributor_code")
    private String distributorCode;

    @ApiModelProperty(value = "分销机构名称")
    @JsonProperty("distributor_name")
    private String distributorName;

    @ApiModelProperty(value = "仓库类型，1为门店自有仓，2为爱亲大仓")
    @JsonProperty("storage_type")
    private Integer storageType;


    @ApiModelProperty(value = "陈列仓位库存")
    @JsonProperty("display_stock")
    private Integer displayStock;

    @ApiModelProperty(value = "退货仓位库存")
    @JsonProperty("return_stock")
    private Integer returnStock;

    @ApiModelProperty(value = "存储仓位库存")
    @JsonProperty("storage_stock")
    private Integer storageStock;

    @ApiModelProperty(value = "条形码")
    @JsonProperty("bar_code")
    private String barCode;

    @ApiModelProperty(value = "商品id")
    @JsonProperty("product_id")
    private String productId;

    @ApiModelProperty(value = "商品编码")
    @JsonProperty("product_code")
    private String productCode;

    @ApiModelProperty(value = "商品名称")
    @JsonProperty("product_name")
    private String productName;

    @ApiModelProperty(value = "商品分类id")
    @JsonProperty("category_id")
    private String categoryId;

    @ApiModelProperty(value = "商品分类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty(value = "规格")
    @JsonProperty("spec")
    private String spec;

    @ApiModelProperty("颜色code")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty("颜色")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty("型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty("单位code")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty(value = "单位")
    @JsonProperty("unit")
    private String unit;

    @ApiModelProperty(value = "列表名称")
    @JsonProperty("show_name")
    private String showName;

    @ApiModelProperty(value = "列表图")
    @JsonProperty("logo")
    private String logo;

    @ApiModelProperty(value = "封面图")
    @JsonProperty("images")
    private String images;

}
