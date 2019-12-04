package com.aiqin.bms.scmp.api.product.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Description:
 * SKU编号	SKU名称	供应商编号	供应商名称	联营扣点(%)	返点(%)	厂商SKU编号	供货渠道类别	是否缺省
 * @author: NullPointException
 * @date: 2019-07-19
 * @time: 16:01
 */
@Data
public class SkuSupplierImport extends BaseRowModel {

    public static final String HEAD = "SkuSupplierImport(applyType=项目, productSkuCode=SKU编号, productSkuName=SKU名称, supplyUnitCode=供应商编号, supplyUnitName=供应商名称, taxIncludedPrice=含税采购价, jointFranchiseRate=联营扣点(%), point=返点(%), factorySkuCode=厂商SKU编号, isDefault=默认值, categoriesSupplyChannelsName=供货渠道类别, usageStatusName=状态)";

    @ApiModelProperty("applyType")
    @ExcelProperty(index = 0 , value = "项目")
    private String applyType;

    @ApiModelProperty("skuCode")
    @ExcelProperty(index = 1 , value = "SKU编号")
    private String productSkuCode;

    @ApiModelProperty("skuName")
    @ExcelProperty(index = 2 , value = "SKU名称")
    private String productSkuName;

    @ApiModelProperty("供应商编号")
    @ExcelProperty(index = 3 , value = "供应商编号")
    private String supplyUnitCode;

    @ApiModelProperty("供应商名称")
    @ExcelProperty(index = 4 , value = "供应商名称")
    private String supplyUnitName;

    @ApiModelProperty("含税采购价")
    @ExcelProperty(index = 5 , value = "含税采购价")
    private String taxIncludedPrice;

    @ApiModelProperty("联营扣率")
    @ExcelProperty(index = 6 , value = "联营扣点(%)")
    private String jointFranchiseRate;

    @ApiModelProperty("返点")
    @ExcelProperty(index = 7 , value = "返点(%)")
    private String point;

    @ApiModelProperty("厂商SKU编码")
    @ExcelProperty(index =  8, value = "厂商SKU编号")
    private String factorySkuCode;

    @ApiModelProperty("默认值（0:否,1：是）")
    @ExcelProperty(index = 9 , value = "默认值")
    private String isDefault;

    @ApiModelProperty(value ="供货渠道类别")
    @ExcelProperty(index = 10 , value = "供货渠道类别")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty(value ="状态")
    @ExcelProperty(index = 11 , value = "状态")
    private String usageStatusName;
}
