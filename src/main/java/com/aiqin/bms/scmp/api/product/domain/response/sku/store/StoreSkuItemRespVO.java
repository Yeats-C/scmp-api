package com.aiqin.bms.scmp.api.product.domain.response.sku.store;

import com.aiqin.bms.scmp.api.product.domain.ProductCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/29 0029 11:01
 */
@ApiModel("商品下的sku列表返回")
@Data
public class StoreSkuItemRespVO {
    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("sku主图")
    @JsonProperty("sku_image")
    private String skuImage;

    @ApiModelProperty("进价")
    @JsonProperty("purchase_price")
    private BigDecimal purchasePrice;

    @ApiModelProperty("售价")
    @JsonProperty("sale_price")
    private BigDecimal salePrice;

    @ApiModelProperty("品类code")
    @JsonProperty("category_code")
    private String categoryCode;

    @ApiModelProperty("商品品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty("商品品牌")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("商品品牌编码")
    @JsonProperty("product_brand_Code")
    private String productBrandCode;

    @ApiModelProperty("条码")
    @JsonProperty("bar_code")
    private String barCode;

    @ApiModelProperty("颜色code")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty("颜色")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty("属性code")
    @JsonProperty("property_code")
    private String propertyCode;

    @ApiModelProperty("属性名称")
    @JsonProperty("property_name")
    private String propertyName;

    @ApiModelProperty("规格")
    @JsonProperty("spec")
    private String spec;

    @ApiModelProperty("型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty("单位code")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty("单位名称")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty("可配库存")
    @JsonProperty("available_stock")
    private Long availableStock;

    @ApiModelProperty("毛重")
    @JsonProperty("box_volume")
    private Long boxVolume;

    @ApiModelProperty("供货单位编码")
    @JsonProperty("supply_unit_code")
    private String supplyUnitCode;

    @ApiModelProperty("供货单位名称")
    @JsonProperty("supply_unit_name")
    private String supplyUnitName;


    @ApiModelProperty("14天销量")
    @JsonProperty("sales_volume")
    private Long salesVolume;

    @ApiModelProperty("拆零系数")
    @JsonProperty("zero_removal_coefficient")
    private Long zeroRemovalCoefficient;

    @ApiModelProperty("仓库可配库存明细")
    @JsonProperty("stock_item_list")
    private List<StoreAvailableStockItem> storeAvailableStockItemList;

    @ApiModelProperty("当前品类所有父级集合")
    @JsonProperty("product_category_list")
    private List<ProductCategory> productCategories;

    @ApiModelProperty("sku图片集合")
    @JsonProperty("sku_images")
    private List<String> skuImages;

}
