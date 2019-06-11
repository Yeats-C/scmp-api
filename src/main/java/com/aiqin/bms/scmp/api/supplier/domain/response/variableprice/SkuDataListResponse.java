package com.aiqin.bms.scmp.api.supplier.domain.response.variableprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("变价数据列表")
public class SkuDataListResponse {
    @ApiModelProperty("sku编码")
    private String skuCode;
    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("品类code")
    private String productCategoryCode;

    @ApiModelProperty("品类名称")
    private String  productCategoryName;

    @ApiModelProperty("品牌code")
    private String productBrandCode;

    @ApiModelProperty("品牌名称")
    private String  productBrandName;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("颜色编码")
    private String colorCode;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("型号")
    private String modelNumber;

    @ApiModelProperty("单位编号")
    private String unitCode;

    @ApiModelProperty("单位")
    private String unitName;

    @ApiModelProperty("商品类别")
    private String productSortCode;

    @ApiModelProperty("商品类别名称")
    private String productSortName;

    @ApiModelProperty("赠品(0:商品，1:赠品")
    private Byte goodsGifts;

    @ApiModelProperty("价格类型code")
    private String priceTypeCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("价格类型")
    private String priceTypeName;

    @ApiModelProperty("最新采购价")
    private Long newPurchasingPrice;

    @ApiModelProperty("供应商code")
    private String supplierCode;

    @ApiModelProperty("0:否 1：是")
    private Byte isDefault;

    @ApiModelProperty("原含税采购价")
    private Long originalTaxPurchasePrice;

    @ApiModelProperty("原会员价")
    private Long originalMembershipPrice;


}
