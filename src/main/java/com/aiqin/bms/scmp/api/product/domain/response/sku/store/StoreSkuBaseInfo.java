package com.aiqin.bms.scmp.api.product.domain.response.sku.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/29 0029 16:45
 */
@ApiModel("门店sku基本信息")
@Data
public class StoreSkuBaseInfo {

    @ApiModelProperty("商品名称")
    @JsonProperty("product_name")
    private String productName;

    @ApiModelProperty("商品类别名称")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("商品类型名称")
    @JsonProperty("product_type_name")
    private String productTypeName;

    @ApiModelProperty("商品属性名称")
    @JsonProperty("product_property_name")
    private String productPropertyName;

    @ApiModelProperty("商品品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty("商品品牌")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("sku简称")
    @JsonProperty("sku_abbreviation")
    private String skuAbbreviation;

    @ApiModelProperty("颜色名称")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty("规格")
    @JsonProperty("spec")
    private String spec;

    @ApiModelProperty("型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty("单位名称")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty("保质管理（0:管理 1:不管理）")
    @JsonProperty("quality_assurance_management")
    private Byte qualityAssuranceManagement;

    @ApiModelProperty("保质数量")
    @JsonProperty("quality_number")
    private String qualityNumber;

    @ApiModelProperty("管理方式名称")
    @JsonProperty("management_style_name")
    private String managementStyleName;

    @ApiModelProperty("供货渠道类别名称")
    @JsonProperty("categories_supply_channels_name")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("箱规")
    @JsonProperty("box_gauge")
    private String boxGauge;

    @ApiModelProperty("适用起始月龄")
    @JsonProperty("applicable_month_age")
    private String applicableMonthAge;

    @ApiModelProperty("条码")
    @JsonProperty("bar_code")
    private String barCode;

    @ApiModelProperty("售价")
    @JsonProperty("sale_price")
    private Long salePrice;

    @ApiModelProperty("助记码")
    @JsonProperty("mnemonic_code")
    private String mnemonicCode;

    @ApiModelProperty("拆零系数")
    @JsonProperty("zero_removal_coefficient")
    private Long zeroRemovalCoefficient;

    @ApiModelProperty("毛重")
    @JsonProperty("box_volume")
    private Long boxVolume;

    @ApiModelProperty("供货单位编码")
    @JsonProperty("supply_unit_code")
    private String supplyUnitCode;

    @ApiModelProperty("供货单位名称")
    @JsonProperty("supply_unit_name")
    private String supplyUnitName;

    @ApiModelProperty("采购组名称")
    @JsonProperty("procurement_section_name")
    private String procurementSectionName;

    @ApiModelProperty("备注")
    @JsonProperty("notes")
    private String notes;

    @ApiModelProperty("生效起始时间")
    @JsonProperty("selection_effective_start_time")
    private String selectionEffectiveStartTime;

    @ApiModelProperty("生效结束时间")
    @JsonProperty("selection_effective_end_time")
    private String selectionEffectiveEndTime;

}
