package com.aiqin.bms.scmp.api.product.domain.request.sku.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @功能说明: 新增sku商品数据接收类 数据直接保存真实表
 * @author: ch
 * @date: 2020/07/04
 */
@ApiModel("新增SKU信息对象")
@Data
public class SaveSkuInfoReqVo {

    @ApiModelProperty(value = "sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("采购组编码")
    @JsonProperty("procurement_section_code")
    private String procurementSectionCode;

    @ApiModelProperty("采购组名称")
    @JsonProperty("procurement_section_name")
    private String procurementSectionName;

    @ApiModelProperty("商品/赠品名称(0:商品，1:赠品 2:组合商品)")
    @JsonProperty("goods_gifts_name")
    private String goodsGiftsName;

    @ApiModelProperty("商品类别(所属部门)code")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("商品类别(所属部门)名称")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("品类编码")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("sku简称")
    @JsonProperty("sku_abbreviation")
    private String skuAbbreviation;

    @ApiModelProperty("助记码")
    @JsonProperty("mnemonic_code")
    private String mnemonicCode;

    @ApiModelProperty("商品编码")
    @JsonProperty("product_code")
    private String productCode;

    @ApiModelProperty("商品名称")
    @JsonProperty("product_name")
    private String productName;

    @ApiModelProperty("spu助记码") // product表里没有这个字段
    @JsonProperty("spu_mnemonic_code")
    private String spuMnemonicCode;

    @ApiModelProperty("款号") // spu款号   sku货号
    @JsonProperty("style_number")
    private String styleNumber;

    @ApiModelProperty("货号")
    @JsonProperty("item_number")
    private String itemNumber;

    @ApiModelProperty(value = "品牌类型code")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty(value = "品牌类型名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("商品属性编码")
    @JsonProperty("product_property_code")
    private String productPropertyCode;

    @ApiModelProperty("商品属性名称")
    @JsonProperty("product_property_name")
    private String productPropertyName;

    @ApiModelProperty("颜色code")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty("颜色名称")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty("型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty("保质天数")
    @JsonProperty("quality_number")
    private String qualityNumber;

    @ApiModelProperty("适用起始月龄")
    @JsonProperty("applicable_month_age")
    private String applicableMonthAge;

    @ApiModelProperty("是否季节性商品(0:是 1:否)")
    @JsonProperty("seasonal_goods")
    private String seasonalGoods;

    @ApiModelProperty("仓位类型名称--商品数据字典")
    @JsonProperty("warehouse_type_name")
    private String warehouseTypeName;

    @ApiModelProperty("是否结构性商品名称(0:是 1:否)")
    @JsonProperty("structural_goods_name")
    private String structuralGoodsName;

    @ApiModelProperty("使用时长")
    @JsonProperty("use_time")
    private Integer useTime;

    @ApiModelProperty("季节波段")
    @JsonProperty("season_band")
    private String seasonBand;

    @ApiModelProperty("唯一码管理名称(0:是 1:否)")
    @JsonProperty("unique_name")
    private String uniqueName;

    @ApiModelProperty("等级")
    @JsonProperty("level")
    private String level;

    @ApiModelProperty("特征名称")
    @JsonProperty("feature_name")
    private String featureName;

    @ApiModelProperty("通货等级名称")
    @JsonProperty("currency_level_name")
    private String currencyLevelName;

    @ApiModelProperty("覆盖渠道")
    @JsonProperty("price_channel_name")
    private String priceChannelName;

    @ApiModelProperty("标签名称")
    @JsonProperty("tag_name")
    private String tagName;

    @ApiModelProperty("商品备注")
    @JsonProperty(value = "remark")
    private String remark;

    @ApiModelProperty("商品介绍")
    @JsonProperty(value = "product_desc")
    private String productDesc;

    @ApiModelProperty("厂商指导价")
    @JsonProperty(value = "manufacturer_guide_price")
    private BigDecimal manufacturerGuidePrice;

    @ApiModelProperty("爱亲渠道价")
    @JsonProperty(value = "sku_price_tax1")
    private BigDecimal skuPriceTax1;

    @ApiModelProperty("萌贝树渠道价")
    @JsonProperty(value = "sku_price_tax2")
    private BigDecimal skuPriceTax2;

    @ApiModelProperty("小红马渠道价")
    @JsonProperty(value = "sku_price_tax3")
    private BigDecimal skuPriceTax3;

    @ApiModelProperty("爱亲分销价")
    @JsonProperty(value = "sku_price_tax4")
    private BigDecimal skuPriceTax4;

    @ApiModelProperty("萌贝树分销价")
    @JsonProperty(value = "sku_price_tax5")
    private BigDecimal skuPriceTax5;

    @ApiModelProperty("小红马分销价")
    @JsonProperty(value = "sku_price_tax6")
    private BigDecimal skuPriceTax6;

    @ApiModelProperty("售价")
    @JsonProperty(value = "sku_price_tax7")
    private BigDecimal skuPriceTax7;

    @ApiModelProperty("会员价")
    @JsonProperty(value = "sku_price_tax8")
    private BigDecimal skuPriceTax8;

    @ApiModelProperty("供应商名称")
    @JsonProperty(value = "supply_unit_name")
    private String supplyUnitName;

    @ApiModelProperty("联营扣率")
    @JsonProperty(value = "joint_franchise_rate")
    private BigDecimal jointFranchiseRate;

    @ApiModelProperty("返点")
    @JsonProperty(value = "point")
    private BigDecimal point;

    @ApiModelProperty("供应商sku_code")
    @JsonProperty(value = "factory_sku_code")
    private String factorySkuCode;

    @ApiModelProperty("含税进价")
    @JsonProperty(value = "tax_included_price")
    private BigDecimal taxIncludedPrice;

    @ApiModelProperty(value ="供货渠道类别名称")
    @JsonProperty(value = "categories_supply_channels_name")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("规格")
    @JsonProperty(value = "spec")
    private String spec;

    @ApiModelProperty(value = "单位编码")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty(value = "单位名称")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty("包装箱子长度")
    @JsonProperty("box_length")
    private Long boxLength;

    @ApiModelProperty("宽度（mm）")
    @JsonProperty("box_width")
    private Long boxWidth;

    @ApiModelProperty("箱子高度")
    @JsonProperty("box_height")
    private Long boxHeight;

    @ApiModelProperty("箱子体积")
    @JsonProperty("box_volume")
    private Long boxVolume;

    @ApiModelProperty("毛重")
    @JsonProperty("box_gross_weight")
    private BigDecimal boxGrossWeight;

    @ApiModelProperty("净重")
    @JsonProperty("net_weight")
    private BigDecimal netWeight;

    @ApiModelProperty("采购规格")
    @JsonProperty(value = "purchase_spec")
    private String purchaseSpec;

    @ApiModelProperty(value = "采购单位编码")
    @JsonProperty("purchase_unit_code")
    private String purchaseUnitCode;

    @ApiModelProperty(value = "采购单位名称")
    @JsonProperty("purchase_unit_name")
    private String purchaseUnitName;

    @ApiModelProperty("采购包装箱子长度")
    @JsonProperty("purchase_box_length")
    private Long purchaseBoxLength;

    @ApiModelProperty("采购宽度（mm）")
    @JsonProperty("purchase_box_width")
    private Long purchaseBoxWidth;

    @ApiModelProperty("采购箱子高度")
    @JsonProperty("purchase_box_height")
    private Long purchaseBoxHeight;

    @ApiModelProperty("采购箱子体积")
    @JsonProperty("purchase_box_volume")
    private Long purchaseBoxVolume;

    @ApiModelProperty("采购毛重")
    @JsonProperty("purchase_box_gross_weight")
    private BigDecimal purchaseBoxGrossWeight;

    @ApiModelProperty("采购净重")
    @JsonProperty("purchase_net_weight")
    private BigDecimal purchaseNetWeight;

    @ApiModelProperty("基商品含量（单位含量）")
    @JsonProperty("base_product_content")
    private Integer baseProductContent;

    @ApiModelProperty("采购条形码")
    @JsonProperty("purchase_bar_code")
    private String purchaseBarCode;

    @ApiModelProperty("拆零系数(交意倍数)")
    @JsonProperty("zero_removal_coefficient")
    private Long zeroRemovalCoefficient;

    @ApiModelProperty("最大订购数-销售信息独有")
    @JsonProperty("max_order_num")
    private Integer maxOrderNum;

    @ApiModelProperty("销售条形码")
    @JsonProperty("sale_bar_code")
    private String saleBarCode;

    @ApiModelProperty("描述-门店销售独有")
    @JsonProperty("description")
    private String description;

    @ApiModelProperty("结算方式名称")
    @JsonProperty("settlement_method_name")
    private String settlementMethodName;

    @ApiModelProperty("进项税率")
    @JsonProperty("input_tax_rate")
    private BigDecimal inputTaxRate;

    @ApiModelProperty("销项税率")
    @JsonProperty("output_tax_rate")
    private BigDecimal outputTaxRate;

    @ApiModelProperty("积分系数")
    @JsonProperty("integral_coefficient")
    private BigDecimal integralCoefficient;

    @ApiModelProperty("物流费奖励比例")
    @JsonProperty("logistics_fee_award_ratio")
    private BigDecimal logisticsFeeAwardRatio;

    @ApiModelProperty("华北仓状态")
    @JsonProperty("config_status_name1")
    private String configStatusName1;

    @ApiModelProperty("华东仓状态")
    @JsonProperty("config_status_name2")
    private String configStatusName2;

    @ApiModelProperty("华南仓状态")
    @JsonProperty("config_status_name3")
    private String configStatusName3;

    @ApiModelProperty("西南仓状态")
    @JsonProperty("config_status_name4")
    private String configStatusName4;

    @ApiModelProperty("华中仓状态")
    @JsonProperty("config_status_name5")
    private String configStatusName5;

    @ApiModelProperty("生产厂家")
    @JsonProperty("manufacturer_name")
    private String manufacturerName;

    @ApiModelProperty("厂方商品编号")
    @JsonProperty("factory_product_number")
    private String factoryProductNumber;

    @ApiModelProperty("保修地址")
    @JsonProperty("address")
    private String address;
}
