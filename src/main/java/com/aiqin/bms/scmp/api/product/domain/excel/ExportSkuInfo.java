package com.aiqin.bms.scmp.api.product.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("sku导出模板")
public class ExportSkuInfo extends BaseRowModel {
    public static final String HEAD = "";
    @ApiModelProperty("sku编码")
    @ExcelProperty(index = 0, value = {"基本信息","SKU编号"})
    private String skuCode;

    @ApiModelProperty("商品/赠品(0:商品，1:赠品 2:组合商品)")
    @ExcelProperty(index = 1, value = {"基本信息","类型"})
    private String goodsGiftsDesc;

    @ApiModelProperty("sku名称")
    @ExcelProperty(index = 2, value = {"基本信息","SKU名称"})
    private String skuName;

    @ApiModelProperty("sku简称")
    @ExcelProperty(index = 3, value = {"基本信息","SKU简称"})
    private String skuAbbreviation;

    @ApiModelProperty("商品品牌")
    @ExcelProperty(index = 4, value = {"基本信息","品牌"})
    private String productBrandName;

    @ApiModelProperty("商品品类名称")
    @ExcelProperty(index = 5, value = {"基本信息","品类"})
    private String productCategoryName;

    @ApiModelProperty("商品名称")
    @ExcelProperty(index = 6, value = {"基本信息","所属SPU"})
    private String productName;

    @ApiModelProperty("商品属性名称")
    @ExcelProperty(index = 7, value = {"基本信息","商品属性"})
    private String productPropertyName;

    @ApiModelProperty("商品类别(所属部门)名称")
    @ExcelProperty(index = 8, value = {"基本信息","所属部门"})
    private String productSortName;

    @ApiModelProperty("采购组")
    @ExcelProperty(index = 9, value = {"基本信息","采购组"})
    private String procurement_section_name;

    @ApiModelProperty("颜色名称")
    @ExcelProperty(index = 10, value = {"基本信息","颜色"})
    private String colorName;

    @ApiModelProperty("型号")
    @ExcelProperty(index = 11, value = {"基本信息","型号"})
    private String modelNumber;

    @ApiModelProperty("保质管理（0:管理 1:不管理）")
    @ExcelProperty(index = 12, value = {"基本信息","是否管理保质期"})
    private String qualityAssuranceManagementDesc;

    @ApiModelProperty("保质日期")
    @ExcelProperty(index = 13, value = {"基本信息","保质期单位"})
    private String qualityDate;

    @ApiModelProperty("保质数量")
    @ExcelProperty(index = 14, value = {"基本信息","保质天数"})
    private String qualityNumber;

    @ApiModelProperty("供货渠道类别名称")
    @ExcelProperty(index = 15, value = {"基本信息","供货渠道类别"})
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("助记码")
    @ExcelProperty(index = 16, value = {"基本信息","助记码"})
    private String mnemonicCode;

    @ApiModelProperty("厂家指导价")
    @ExcelProperty(index = 17, value = {"基本信息","厂家指导价"})
    private String manufacturerGuidePrice;

    @ApiModelProperty("适用起始月龄")
    @ExcelProperty(index = 18, value = {"基本信息","适用起始月龄"})
    private String applicableMonthAge;

    @ApiModelProperty("是否季节性商品(0:是 1:否)")
    @ExcelProperty(index = 19, value = {"基本信息","是否季节商品"})
    private String seasonalGoodsDesc;

    @ApiModelProperty("仓位类型名称--商品数据字典")
    @ExcelProperty(index = 20, value = {"基本信息","仓位类型"})
    private String warehouseTypeName;

    @ApiModelProperty("是否结构性商品(0:是 1:否)")
    @ExcelProperty(index = 21, value = {"基本信息","结构性商品"})
    private String structuralGoodsDesc;

    @ApiModelProperty("使用时长")
    @ExcelProperty(index = 22, value = {"基本信息","使用时长"})
    private String useTime;

    @ApiModelProperty("库存模式(0:有库存销售 1:无库存销售)")
    @ExcelProperty(index = 23, value = {"基本信息","库存模式"})
    private String inventoryModelDesc;

    @ApiModelProperty("唯一码管理(0:是 1:否)")
    @ExcelProperty(index = 24, value = {"基本信息","唯一码管理"})
    private String uniqueCodeDesc;

    @ApiModelProperty("价格渠道名称")
    @ExcelProperty(index = 25, value = {"基本信息","覆盖渠道"})
    private String priceChannelName;

    @ApiModelProperty("标签名称")
    @ExcelProperty(index = 26, value = {"基本信息","商品标签"})
    private String tagName;

    @ApiModelProperty("备注")
    @ExcelProperty(index = 27, value = {"基本信息","商品备注"})
    private String remark;

    @ApiModelProperty("规格")
    @ExcelProperty(index = 28, value = {"进销存及包装信息","库存规格"})
    private String stockSpec;

    @ApiModelProperty("单位名称")
    @ExcelProperty(index = 29, value = {"进销存及包装信息","库存单位"})
    private String stockUnitName;

    @ApiModelProperty("包装箱子长度")
    @ExcelProperty(index = 30, value = {"进销存及包装信息","库存长"})
    private String stockBoxLength;

    @ApiModelProperty("宽度（mm）")
    @ExcelProperty(index = 31, value = {"进销存及包装信息","库存宽"})
    private String stockBoxWidth;

    @ApiModelProperty("箱子高度")
    @ExcelProperty(index = 32, value = {"进销存及包装信息","库存高"})
    private String stockBoxHeight;

    @ApiModelProperty("毛重")
    @ExcelProperty(index = 33, value = {"进销存及包装信息","库存毛重"})
    private String stockBoxGrossWeight;

    @ApiModelProperty("库存净重")
    @ExcelProperty(index = 34, value = {"进销存及包装信息","库存净重"})
    private String stockNetWeight;

    @ApiModelProperty("条形码")
    @ExcelProperty(index = 35, value = {"进销存及包装信息","库存条形码"})
    private String stockBarCode;

    @ApiModelProperty("规格")
    @ExcelProperty(index = 36, value = {"进销存及包装信息","采购规格"})
    private String purchaseSpec;

    @ApiModelProperty("单位名称")
    @ExcelProperty(index = 37, value = {"进销存及包装信息","采购单位"})
    private String purchaseUnitName;

    @ApiModelProperty("包装箱子长度")
    @ExcelProperty(index = 38, value = {"进销存及包装信息","采购长"})
    private String purchaseBoxLength;

    @ApiModelProperty("宽度（mm）")
    @ExcelProperty(index = 39, value = {"进销存及包装信息","采购宽"})
    private String purchaseBoxWidth;

    @ApiModelProperty("箱子高度")
    @ExcelProperty(index = 40, value = {"进销存及包装信息","采购高"})
    private String purchaseBoxHeight;

    @ApiModelProperty("毛重")
    @ExcelProperty(index = 41, value = {"进销存及包装信息","采购毛重"})
    private String purchaseBoxGrossWeight;

    @ApiModelProperty("库存净重")
    @ExcelProperty(index = 42, value = {"进销存及包装信息","采购净重"})
    private String purchaseNetWeight;

    @ApiModelProperty("基商品含量")
    @ExcelProperty(index = 43, value = {"进销存及包装信息","采购基商品含量"})
    private String purchaseBaseProductContent;

    @ApiModelProperty("拆零系数")
    @ExcelProperty(index = 44, value = {"进销存及包装信息","采购拆零系数"})
    private String purchaseZeroRemovalCoefficient;

    @ApiModelProperty("条形码")
    @ExcelProperty(index = 45, value = {"进销存及包装信息","采购条形码"})
    private String purchaseBarCode;

    @ApiModelProperty("规格")
    @ExcelProperty(index = 46, value = {"进销存及包装信息","分销规格"})
    private String distributionSpec;

    @ApiModelProperty("单位名称")
    @ExcelProperty(index = 47, value = {"进销存及包装信息","分销单位"})
    private String distributionUnitName;

    @ApiModelProperty("基商品含量")
    @ExcelProperty(index = 48, value = {"进销存及包装信息","分销基商品含量"})
    private String distributionBaseProductContent;

    @ApiModelProperty("拆零系数")
    @ExcelProperty(index = 49, value = {"进销存及包装信息","分销拆零系数"})
    private String distributionZeroRemovalCoefficient;

    @ApiModelProperty("条形码")
    @ExcelProperty(index = 50, value = {"进销存及包装信息","分销条形码"})
    private String distributionBarCode;

    @ApiModelProperty("最大订购数-销售信息独有")
    @ExcelProperty(index = 51, value = {"进销存及包装信息","最大订购数"})
    private String maxOrderNum;

    @ApiModelProperty("规格")
    @ExcelProperty(index = 52, value = {"进销存及包装信息","销售规格"})
    private String saleSpec;

    @ApiModelProperty("单位名称")
    @ExcelProperty(index = 53, value = {"进销存及包装信息","销售单位"})
    private String saleUnitName;

    @ApiModelProperty("条形码")
    @ExcelProperty(index = 54, value = {"进销存及包装信息","销售条形码"})
    private String saleBarCode;

    @ApiModelProperty("描述-门店销售独有")
    @ExcelProperty(index = 55, value = {"进销存及包装信息","销售描述"})
    private String description;

    @ApiModelProperty("结算方式名称")
    @ExcelProperty(index = 56, value = {"结算信息","结算方式"})
    private String settlementMethodName;

    @ApiModelProperty("进项税率")
    @ExcelProperty(index = 57, value = {"结算信息","进项税率"})
    private String inputTaxRate;

    @ApiModelProperty("销项税率")
    @ExcelProperty(index = 58, value = {"结算信息","销项税率"})
    private String outputTaxRate;

    @ApiModelProperty("积分系数")
    @ExcelProperty(index = 59, value = {"结算信息","积分系数"})
    private String integralCoefficient;

    @ApiModelProperty("物流费奖励比例")
    @ExcelProperty(index = 60, value = {"结算信息","物流费奖励比例"})
    private String logisticsFeeAwardRatio;

    @ApiModelProperty("供货单位名称")
    @ExcelProperty(index = 61, value = {"供应商信息","供应商编码"})
    private String supplyUnitCode;

    @ApiModelProperty("供货单位名称")
    @ExcelProperty(index = 62, value = {"供应商信息","供应商名称"})
    private String supplyUnitName;

    @ApiModelProperty("含税进价")
    @ExcelProperty(index = 63, value = {"供应商信息","含税采购价"})
    private String taxIncludedPrice;

    @ApiModelProperty("联营扣点")
    @ExcelProperty(index = 64, value = {"供应商信息","联营返点(%)"})
    private String jointFranchiseRate;

    @ApiModelProperty("返点")
    @ExcelProperty(index = 65, value = {"供应商信息","返点(%)"})
    private String point;

    @ApiModelProperty("厂商SKU编码")
    @ExcelProperty(index = 66, value = {"供应商信息","厂商SKU编号"})
    private String factorySkuCode;

    @ApiModelProperty(value ="供应商供货渠道类别")
    @ExcelProperty(index = 67, value = {"供应商信息","供应商供货渠道类别"})
    private String supplyCategoriesSupplyChannelsName;
}