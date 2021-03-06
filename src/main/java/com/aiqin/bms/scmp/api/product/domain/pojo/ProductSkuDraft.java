package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@ApiModel("SKU临时表信息")
@Data
public class ProductSkuDraft extends CommonBean {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty("所属商品编码")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty(value = "sku编号")
    private String skuCode;

    @ApiModelProperty("sku简称")
    private String skuAbbreviation;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("商品品牌code")
    private String productBrandCode;

    @ApiModelProperty("商品品牌")
    private String productBrandName;

    @ApiModelProperty("商品品类code")
    private String productCategoryCode;

    @ApiModelProperty("商品品类名称")
    private String productCategoryName;

    @ApiModelProperty("商品/赠品(0:商品，1:赠品 2:组合商品)")
    private Byte goodsGifts;

    @ApiModelProperty("商品属性code")
    private String productPropertyCode;

    @ApiModelProperty("商品属性名称")
    private String productPropertyName;

    @ApiModelProperty("商品类别(所属部门)code")
    private String productSortCode;

    @ApiModelProperty("商品类别(所属部门)名称")
    private String productSortName;

    @ApiModelProperty("采购组编码")
    private String procurementSectionCode;

    @ApiModelProperty("采购组名称")
    private String procurementSectionName;

    @ApiModelProperty("颜色code")
    private String colorCode;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("型号")
    private String modelNumber;

    @ApiModelProperty("管理方式code")
    private String managementStyleCode;

    @ApiModelProperty("管理方式名称")
    private String managementStyleName;

    @ApiModelProperty("保质管理（0:管理 1:不管理）")
    private Byte qualityAssuranceManagement;

    @ApiModelProperty("保质数量")
    private String qualityNumber;

    @ApiModelProperty("保质日期")
    private String qualityDate;

    @ApiModelProperty("供货渠道类别code")
    private String categoriesSupplyChannelsCode;

    @ApiModelProperty("供货渠道类别名称")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("助记码")
    private String mnemonicCode;

    @ApiModelProperty("厂家指导价")
    private BigDecimal manufacturerGuidePrice;

    @ApiModelProperty("适用起始月龄")
    private String applicableMonthAge;

    @ApiModelProperty("是否季节性商品(0:是 1:否)")
    private Byte seasonalGoods;

    @ApiModelProperty("仓位类型编码--商品数据字典")
    private String warehouseTypeCode;

    @ApiModelProperty("仓位类型名称--商品数据字典")
    private String warehouseTypeName;

    @ApiModelProperty("是否结构性商品(0:是 1:否)")
    private Byte structuralGoods;

    @ApiModelProperty(value = "是否爱亲主推(0:否，1:是)", hidden = true)
    private Byte isMainPush;

    @ApiModelProperty(value = "是否新品(0:否，1:是)", hidden = true)
    private Byte newProduct;

    @ApiModelProperty("库存模式(0:有库存销售 1:无库存销售)")
    private Byte inventoryModel;

    @ApiModelProperty("使用时长")
    private Integer useTime;

    @ApiModelProperty("唯一码管理(0:是 1:否)")
    private Byte uniqueCode;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty(value = "公司编码",hidden = true)
    private String companyCode;

    @ApiModelProperty(value = "公司名称",hidden = true)
    private String companyName;

    @ApiModelProperty(value = "申请类型", hidden = true)
    private Byte applyType;

    @ApiModelProperty(value = "申请类型名称", hidden = true)
    private String applyTypeName;

    @ApiModelProperty(" 库存分配-组合独有(0:共享)")
    private Byte inventoryAllocation;

    @ApiModelProperty("价格模式-组合独有(0:人工设置)")
    private Byte priceModel;


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

    @ApiModelProperty(value = "图片文件夹编码")
    private String picFolderCode;

    @ApiModelProperty(value = "改变内容",hidden = true)
    private String changeContent;

    @ApiModelProperty(value = "修改人")
    private String updateBy;

    @ApiModelProperty("供应商信息")
    private List<ProductSkuSupplyUnitDraft> supplyList;

    @ApiModelProperty(value = "来源 0供应链1供应商平台",hidden = true)
    private Integer originalCode = 0;

    @ApiModelProperty("商品描述")
    private String productDesc;

    @ApiModelProperty("货号")
    private String itemNumber;

    @ApiModelProperty("季节波段")
    private String seasonBand;

    @ApiModelProperty("等级")
    private String level;

    @ApiModelProperty("特征编码")
    private String featureCode;

    @ApiModelProperty("特征名称")
    private String featureName;

    @ApiModelProperty("通货等级编码")
    private String currencyLevelCode;

    @ApiModelProperty("通货等级名称")
    private String currencyLevelName;


}