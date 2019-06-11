package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("SKU临时表信息")
@Data
public class ProductSkuDraft {

    @ApiModelProperty(value = "所属商品编码",position = 2)
    private String productCode;

    @ApiModelProperty(value = "商品名称",position = 3)
    private String productName;

    @ApiModelProperty(value = "sku编号",position = 4)
    private String skuCode;

    @ApiModelProperty(value ="sku简称",position = 5)
    private String skuAbbreviation;

    @ApiModelProperty(value ="sku名称",position = 6)
    private String skuName;

    @ApiModelProperty(value ="商品品牌code",position = 7)
    private String productBrandCode;

    @ApiModelProperty(value ="商品品牌",position = 8)
    private String productBrandName;

    @ApiModelProperty(value ="商品品类code",position = 9)
    private String productCategoryCode;

    @ApiModelProperty(value ="商品品类名称",position = 10)
    private String productCategoryName;

    @ApiModelProperty(value ="商品/赠品(0:商品，1:赠品)",position = 11)
    private Byte goodsGifts;

    @ApiModelProperty(value ="商品属性code",position = 12)
    private String productPropertyCode;

    @ApiModelProperty(value ="商品属性名称",position = 13)
    private String productPropertyName;

    @ApiModelProperty(value ="商品类别(所属部门)code",position = 14)
    private String productSortCode;

    @ApiModelProperty(value ="商品类别(所属部门)名称",position = 15)
    private String productSortName;

    @ApiModelProperty(value ="采购组编码",position = 16)
    private String procurementSectionCode;

    @ApiModelProperty(value ="采购组名称",position = 17)
    private String procurementSectionName;

    @ApiModelProperty(value ="颜色code",position = 18)
    private String colorCode;

    @ApiModelProperty(value ="颜色名称",position = 19)
    private String colorName;

    @ApiModelProperty(value ="型号",position = 20)
    private String modelNumber;

    @ApiModelProperty(value ="管理方式code",position = 21)
    private String managementStyleCode;

    @ApiModelProperty(value ="管理方式名称",position = 22)
    private String managementStyleName;

    @ApiModelProperty(value ="保质管理（0:管理 1:不管理）",position = 23)
    private Byte qualityAssuranceManagement;

    @ApiModelProperty(value ="保质数量",position = 24)
    private String qualityNumber;

    @ApiModelProperty(value ="保质日期",position = 25)
    private String qualityDate;

    @ApiModelProperty(value ="供货渠道类别code",position = 26)
    private String categoriesSupplyChannelsCode;

    @ApiModelProperty(value ="供货渠道类别名称",position = 27)
    private String categoriesSupplyChannelsName;

    @ApiModelProperty(value ="助记码",position = 28)
    private String mnemonicCode;

    @ApiModelProperty(value ="厂家指导价",position = 29)
    private Long manufacturerGuidePrice;

    @ApiModelProperty(value ="适用起始月龄",position = 30)
    private String applicableMonthAge;

    @ApiModelProperty(value ="是否季节性商品(0:是 1:否)",position = 31)
    private Byte seasonalGoods;

    @ApiModelProperty(value ="仓位类型编码--商品数据字典",position = 32)
    private String warehouseTypeCode;

    @ApiModelProperty(value = "仓位类型名称--商品数据字典",position = 33)
    private String warehouseTypeName;

    @ApiModelProperty(value = "是否结构性商品(0:是 1:否)",position = 34)
    private Byte structuralGoods;
    @ApiModelProperty(value = "是否爱亲主推(0:否，1:是)",position = 35)
    private Byte isMainPush;

    @ApiModelProperty(value = "是否新品(0:否，1:是)",position = 36)
    private Byte newProduct;

    @ApiModelProperty(value = "备注",position = 37)
    private String remark;




    @ApiModelProperty(value = "规格", hidden = true)
    private String spec;

    @ApiModelProperty(value = "单位code", hidden = true)
    private String unitCode;

    @ApiModelProperty(value = "单位名称",hidden = true)
    private String unitName;

    @ApiModelProperty(value = "sku状态，0为启用，1为禁用", hidden = true)
    private Byte skuStatus;

    @ApiModelProperty(value = "是否上架，0为未上架，1为上架",hidden = true)
    private Byte onSale;

    @ApiModelProperty(value = "箱规",hidden = true)
    private String boxGauge;

    @ApiModelProperty(value = "返点", hidden = true)
    private String point;

    @ApiModelProperty(value = "条码", hidden = true)
    private String barCode;

    @ApiModelProperty(value = "是否使用生效时间(0:是1:否)",hidden = true)
    private Byte selectionEffectiveTime;

    @ApiModelProperty(value = "生效起始时间", hidden = true)
    private String selectionEffectiveStartTime;

    @ApiModelProperty(value = "生效结束时间", hidden = true)
    private String selectionEffectiveEndTime;

    @ApiModelProperty(hidden = true)
    private String spuCode;

    @ApiModelProperty(value = "logo",hidden = true)
    private String logo;

    @ApiModelProperty(value = "图片",hidden = true)
    private String images;

    @ApiModelProperty(value = "封面图", hidden = true)
    private String itroImages;

    @ApiModelProperty(value = "拆零系数",hidden = true)
    private Long zeroRemovalCoefficient;


}