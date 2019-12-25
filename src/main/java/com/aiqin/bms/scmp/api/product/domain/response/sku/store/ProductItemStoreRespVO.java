package com.aiqin.bms.scmp.api.product.domain.response.sku.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/28 0028 16:12
 */
@ApiModel("门店商品信息返回")
@Data
public class ProductItemStoreRespVO {

    @ApiModelProperty("商品编码")
    @JsonProperty("product_code")
    private String productCode;

    @ApiModelProperty("商品名称")
    @JsonProperty("product_name")
    private String productName;

    @ApiModelProperty("商品主图")
    @JsonProperty("product_image")
    private String productImage;

    @ApiModelProperty("最低动销价")
    @JsonProperty("lowest_price")
    private BigDecimal lowestPrice;

    @ApiModelProperty("最高动销价")
    @JsonProperty("highest_price")
    private BigDecimal highestPrice;

   /* @ApiModelProperty("商品标签")
    @JsonProperty("product_label")
    private String productLabel;

    @ApiModelProperty("品类集合")
    @JsonProperty("product_category_list")
    private List<ProductCategory> productCategories;

    @ApiModelProperty("品牌id")
    @JsonProperty("brand_id")
    private String brandId;

    @ApiModelProperty("品牌名称")
    @JsonProperty("brand_name")
    private String brandName;*/

}
