package com.aiqin.bms.scmp.api.product.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 * 类型	SKU名称	SKU简称	品牌	品类	所属SPU	商品属性	所属部门	颜色	型号	是否管理保质期	保质期单位	保质天数	供货渠道类别	助记码	厂商指导价	适用起始月龄	是否季节商品	仓位类型	结构性商品	使用时长	库存模式	唯一码管理	覆盖渠道	商品标签	商品备注	库存规格	库存单位	库存长	库存宽	库存高	库存毛重	库存净重	库存条形码	采购规格	采购单位	采购长	采购宽	采购高	采购毛重	采购净重	采购基商品含量	采购拆零系数	采购条形码	分销规格	分销单位	分销基商品含量	分销拆零系数	分销条形码	最大订购数量	销售规格	销售单位	销售基商品含量	销售拆零系数	销售条形码	销售描述	结算方式	进项税率	销项税率	积分系数	物流费奖励比例	供应商	含税采购价	联营扣点	返点	厂商SKU编号	供应商供货渠道类别	爱亲渠道价	萌贝树渠道价	小红马渠道价	爱亲分销价	萌贝树分销价	小红马分销价	爱亲售价	萌贝树售价	小红马售价	华北仓状态	华东仓状态	华南仓状态	西南仓状态	华中仓状态	生产厂家	厂方商品编号	保修地址	图片文件夹编号
 * @author: NullPointException
 * @date: 2019-07-21
 * @time: 14:24
 */
@Data
@ApiModel("sku新增导入实体")
public class SkuInfoImportNew extends BaseRowModel {

    public static final String HEAD = "SkuInfoImportNew(goodsGiftsDesc=类型, productSortName=所属部门, productCategoryName=品类编码, skuName=SKU名称, skuAbbreviation=SKU简称, productName=所属SPU, productBrandName=品牌, productPropertyName=商品属性, colorName=颜色, modelNumber=型号, qualityDate=保质天数, categoriesSupplyChannelsName=供货渠道类别, mnemonicCode=助记码, applicableMonthAge=适用起始月龄, seasonalGoodsDesc=是否季节商品, warehouseTypeName=仓位类型, structuralGoodsDesc=结构性商品, useTime=使用时长, uniqueCodeDesc=唯一码管理, priceChannelName=覆盖渠道, tagName=商品标签, remark=商品备注, productDesc=商品介绍, manufacturerGuidePrice=厂商指导价, taxIncludedPrice=含税采购价, readyCol67=爱亲渠道价, readyCol68=萌贝树渠道价, readyCol69=小红马渠道价, readyCol70=爱亲分销价, readyCol71=萌贝树分销价, readyCol72=小红马分销价, readyCol74=售价, readyCol75=会员价, supplyUnitName=供应商, jointFranchiseRate=联营扣点, point=返点, factorySkuCode=厂商SKU编号, supplyCategoriesSupplyChannelsName=供应商供货渠道类别, stockSpec=库存规格, stockUnitName=库存单位, stockBoxLength=库存长(MM), stockBoxWidth=库存宽(MM), stockBoxHeight=库存高(MM), stockBoxGrossWeight=库存毛重(KG), stockNetWeight=库存净重(KG), purchaseSpec=采购规格, purchaseUnitName=采购单位, purchaseBoxLength=采购长(MM), purchaseBoxWidth=采购宽(MM), purchaseBoxHeight=采购高(MM), purchaseBoxGrossWeight=采购毛重(KG), purchaseNetWeight=采购净重(KG), purchaseBaseProductContent=采购单位含量, purchaseBarCode=采购条形码, distributionZeroRemovalCoefficient=分销交易倍数, maxOrderNum=最大订购数量, saleBarCode=销售条形码, description=销售描述, settlementMethodName=结算方式, inputTaxRate=进项税率, outputTaxRate=销项税率, integralCoefficient=积分系数, logisticsFeeAwardRatio=物流费奖励比例, readyCol76=华北仓状态, readyCol77=华东仓状态, readyCol78=华南仓状态, readyCol79=西南仓状态, readyCol80=华中仓状态, manufacturerName=生产厂家, factoryProductNumber=生产厂家编号, address=保修地址, picFolderCode=图片文件夹编号)";

    @ApiModelProperty("商品/赠品(0:商品，1:赠品 2:组合商品)")
    @ExcelProperty(index = 0, value = "类型")
    private String goodsGiftsDesc;

    @ApiModelProperty("商品类别(所属部门)名称")
    @ExcelProperty(index = 1, value = "所属部门")
    private String productSortName;

    @ApiModelProperty("商品品类名称")
    @ExcelProperty(index = 2, value = "品类编码")
    private String productCategoryName;

    @ApiModelProperty("sku名称")
    @ExcelProperty(index = 3, value = "SKU名称")
    private String skuName;

    @ApiModelProperty("sku简称")
    @ExcelProperty(index = 4, value = "SKU简称")
    private String skuAbbreviation;

    @ApiModelProperty("商品名称")
    @ExcelProperty(index = 5, value = "所属SPU")
    private String productName;

    @ApiModelProperty("商品品牌")
    @ExcelProperty(index = 6, value = "品牌")
    private String productBrandName;

    @ApiModelProperty("商品属性名称")
    @ExcelProperty(index = 7, value = "商品属性")
    private String productPropertyName;

    @ApiModelProperty("颜色名称")
    @ExcelProperty(index = 8, value = "颜色")
    private String colorName;

    @ApiModelProperty("型号")
    @ExcelProperty(index = 9, value = "型号")
    private String modelNumber;

    @ApiModelProperty("保质数量")
    @ExcelProperty(index = 10, value = "保质天数")
    private String qualityDate;

    @ApiModelProperty("供货渠道类别名称")
    @ExcelProperty(index = 11, value = "供货渠道类别")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("助记码")
    @ExcelProperty(index = 12, value = "助记码")
    private String mnemonicCode;

    @ApiModelProperty("适用起始月龄")
    @ExcelProperty(index = 13, value = "适用起始月龄")
    private String applicableMonthAge;

    @ApiModelProperty("是否季节性商品(0:是 1:否)")
    @ExcelProperty(index = 14, value = "是否季节商品")
    private String seasonalGoodsDesc;

    @ApiModelProperty("仓位类型名称--商品数据字典")
    @ExcelProperty(index = 15, value = "仓位类型")
    private String warehouseTypeName;

    @ApiModelProperty("是否结构性商品(0:是 1:否)")
    @ExcelProperty(index = 16, value = "结构性商品")
    private String structuralGoodsDesc;

    @ApiModelProperty("使用时长")
    @ExcelProperty(index = 17, value = "使用时长")
    private String useTime;

    @ApiModelProperty("唯一码管理(0:是 1:否)")
    @ExcelProperty(index = 18, value = "唯一码管理")
    private String uniqueCodeDesc;

    @ApiModelProperty("价格渠道名称")
    @ExcelProperty(index = 19, value = "覆盖渠道")
    private String priceChannelName;

    @ApiModelProperty("标签名称")
    @ExcelProperty(index = 20, value = "商品标签")
    private String tagName;

    @ApiModelProperty("备注")
    @ExcelProperty(index = 21, value = "商品备注")
    private String remark;

    @ApiModelProperty("商品介绍")
    @ExcelProperty(index = 22, value = "商品介绍")
    private String productDesc;

    @ApiModelProperty("厂商指导价")
    @ExcelProperty(index = 23, value = "厂商指导价")
    private String manufacturerGuidePrice;

    @ApiModelProperty("含税采购价")
    @ExcelProperty(index = 24, value = "含税采购价")
    private String taxIncludedPrice;

    @ApiModelProperty(value ="爱亲渠道价")
    @ExcelProperty(index = 25, value = "爱亲渠道价")
    private String readyCol67;

    @ApiModelProperty(value ="萌贝树渠道价")
    @ExcelProperty(index = 26, value = "萌贝树渠道价")
    private String readyCol68;

    @ApiModelProperty(value ="小红马渠道价")
    @ExcelProperty(index = 27, value = "小红马渠道价")
    private String readyCol69;

    @ApiModelProperty(value ="爱亲分销价")
    @ExcelProperty(index = 28, value = "爱亲分销价")
    private String readyCol70;

    @ApiModelProperty(value ="萌贝树分销价")
    @ExcelProperty(index = 29, value = "萌贝树分销价")
    private String readyCol71;

    @ApiModelProperty(value ="小红马分销价")
    @ExcelProperty(index = 30, value = "小红马分销价")
    private String readyCol72;

    @ApiModelProperty(value ="售价")
    @ExcelProperty(index = 31, value = "售价")
    private String readyCol74;

    @ApiModelProperty(value ="会员价")
    @ExcelProperty(index = 32, value = "会员价")
    private String readyCol75;

    @ApiModelProperty("供货单位名称")
    @ExcelProperty(index = 33, value = "供应商")
    private String supplyUnitName;

    @ApiModelProperty("联营扣点")
    @ExcelProperty(index = 34, value = "联营扣点")
    private String jointFranchiseRate;

    @ApiModelProperty("返点")
    @ExcelProperty(index = 35, value = "返点")
    private String point;

    @ApiModelProperty("厂商SKU编码")
    @ExcelProperty(index = 36, value = "厂商SKU编码")
    private String factorySkuCode;

    @ApiModelProperty(value ="供应商供货渠道类别")
    @ExcelProperty(index = 37, value = "供应商供货渠道类别")
    private String supplyCategoriesSupplyChannelsName;

    @ApiModelProperty("规格")
    @ExcelProperty(index = 38, value = "库存规格")
    private String stockSpec;

    @ApiModelProperty("单位名称")
    @ExcelProperty(index = 39, value = "库存单位")
    private String stockUnitName;

    @ApiModelProperty("包装箱子长度")
    @ExcelProperty(index = 40, value = "库存长")
    private String stockBoxLength;

    @ApiModelProperty("宽度（mm）")
    @ExcelProperty(index = 41, value = "库存宽")
    private String stockBoxWidth;

    @ApiModelProperty("箱子高度")
    @ExcelProperty(index = 42, value = "库存高")
    private String stockBoxHeight;

    @ApiModelProperty("毛重")
    @ExcelProperty(index = 43, value = "库存毛重")
    private String stockBoxGrossWeight;

    @ApiModelProperty("库存净重")
    @ExcelProperty(index = 44, value = "库存净重")
    private String stockNetWeight;

    @ApiModelProperty("规格")
    @ExcelProperty(index = 45, value = "采购规格")
    private String purchaseSpec;

    @ApiModelProperty("单位名称")
    @ExcelProperty(index = 46, value = "采购单位")
    private String purchaseUnitName;

    @ApiModelProperty("包装箱子长度")
    @ExcelProperty(index = 47, value = "采购长")
    private String purchaseBoxLength;

    @ApiModelProperty("宽度（mm）")
    @ExcelProperty(index = 48, value = "采购宽")
    private String purchaseBoxWidth;

    @ApiModelProperty("箱子高度")
    @ExcelProperty(index = 49, value = "采购高")
    private String purchaseBoxHeight;

    @ApiModelProperty("毛重")
    @ExcelProperty(index = 50, value = "采购毛重")
    private String purchaseBoxGrossWeight;

    @ApiModelProperty("库存净重")
    @ExcelProperty(index = 51, value = "采购净重")
    private String purchaseNetWeight;

    @ApiModelProperty("基商品含量")
    @ExcelProperty(index = 52, value = "采购基商品含量")
    private String purchaseBaseProductContent;

    @ApiModelProperty("条形码")
    @ExcelProperty(index = 53, value = "采购条形码")
    private String purchaseBarCode;

    @ApiModelProperty("拆零系数")
    @ExcelProperty(index = 54, value = "分销拆零系数")
    private String distributionZeroRemovalCoefficient;

    @ApiModelProperty("最大订购数-销售信息独有")
    @ExcelProperty(index = 55, value = "最大订购数")
    private String maxOrderNum;

    @ApiModelProperty("条形码")
    @ExcelProperty(index = 56, value = "销售条形码")
    private String saleBarCode;

    @ApiModelProperty("描述-门店销售独有")
    @ExcelProperty(index = 57, value = "销售描述")
    private String description;

    @ApiModelProperty("结算方式名称")
    @ExcelProperty(index = 58, value = "结算方式")
    private String settlementMethodName;

    @ApiModelProperty("进项税率")
    @ExcelProperty(index = 59, value = "进项税率")
    private String inputTaxRate;

    @ApiModelProperty("销项税率")
    @ExcelProperty(index = 60, value = "销项税率")
    private String outputTaxRate;

    @ApiModelProperty("积分系数")
    @ExcelProperty(index = 61, value = "积分系数")
    private String integralCoefficient;

    @ApiModelProperty("物流费奖励比例")
    @ExcelProperty(index = 62, value = "物流费奖励比例")
    private String logisticsFeeAwardRatio;

    @ApiModelProperty(value ="华北仓状态")
    @ExcelProperty(index = 63, value = "华北仓状态")
    private String readyCol76;

    @ApiModelProperty(value ="华东仓状态")
    @ExcelProperty(index = 64, value = "华东仓状态")
    private String readyCol77;

    @ApiModelProperty(value ="华南仓状态")
    @ExcelProperty(index = 65, value = "华南仓状态")
    private String readyCol78;

    @ApiModelProperty(value ="西南仓状态")
    @ExcelProperty(index = 66, value = "西南仓状态")
    private String readyCol79;

    @ApiModelProperty(value ="华中仓状态")
    @ExcelProperty(index = 67, value = "华中仓状态")
    private String readyCol80;

    @ApiModelProperty("生产厂家")
    @ExcelProperty(index = 68, value = "生产厂家")
    private String manufacturerName;

    @ApiModelProperty("厂方商品编号")
    @ExcelProperty(index = 69, value = "厂方商品编号")
    private String factoryProductNumber;

    @ApiModelProperty("保修地址")
    @ExcelProperty(index = 70, value = "保修地址")
    private String address;

    @ApiModelProperty("图片文件夹编号")
    @ExcelProperty(index = 71, value = "图片文件夹编号")
    private String picFolderCode;
}