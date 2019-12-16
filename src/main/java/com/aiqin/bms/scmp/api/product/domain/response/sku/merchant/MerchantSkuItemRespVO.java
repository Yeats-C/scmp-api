package com.aiqin.bms.scmp.api.product.domain.response.sku.merchant;

import com.aiqin.bms.scmp.api.product.domain.ProductCategory;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuLabelInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @功能说明:
 * @author wangxu
 * @date 2019/1/2 0002 16:14
 */
@Data
@ApiModel("微商城根据sku编码获取sku详情")
public class MerchantSkuItemRespVO {

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("品牌code")
    @JsonProperty("brand_code")
    private String brandCode;

    @ApiModelProperty("品牌名称")
    @JsonProperty("brand_name")
    private String brandName;

    @ApiModelProperty("属性code")
    @JsonProperty("property_code")
    private String propertyCode;

    @ApiModelProperty("属性名称")
    @JsonProperty("property_name")
    private String propertyName;

    @ApiModelProperty("品类code")
    @JsonProperty("category_code")
    private String categoryCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty("当前品类所有父级集合")
    @JsonProperty("product_category_list")
    private List<ProductCategory> productCategories;

    @ApiModelProperty("sku主图路径")
    @JsonProperty("sku_image")
    private String skuImage;

    @ApiModelProperty("颜色code")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty("颜色名称")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty("型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty("规格")
    @JsonProperty("spec")
    private String spec;

    @ApiModelProperty("价格")
    @JsonProperty("price")
    private BigDecimal price;

    @ApiModelProperty("描述")
    @JsonProperty("description")
    private String description;

    @ApiModelProperty("单位code")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty("单位名称")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty("库存数量")
    @JsonProperty("stock_quantity")
    private Long stockQuantity;

    @ApiModelProperty("商品标签集合")
    @JsonProperty("product_label_list")
    private List<ProductSkuLabelInfo> productLabelList;
}
