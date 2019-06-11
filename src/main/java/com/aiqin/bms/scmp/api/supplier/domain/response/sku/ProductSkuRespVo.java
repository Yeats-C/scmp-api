package com.aiqin.bms.scmp.api.supplier.domain.response.sku;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuRespVo
 * @date 2019/5/14 15:05
 * @description TODO
 */
@ApiModel("SKU基本信息返回")
@Data
public class ProductSkuRespVo extends CommonBean {

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

    @ApiModelProperty("商品/赠品(0:商品，1:赠品)")
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
    private Long manufacturerGuidePrice;

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

    @ApiModelProperty("是否爱亲主推(0:否，1:是)")
    private Byte isMainPush;

    @ApiModelProperty("是否新品(0:否，1:是)")
    private Byte newProduct;

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
}
