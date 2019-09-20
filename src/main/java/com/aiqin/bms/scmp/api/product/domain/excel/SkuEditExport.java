package com.aiqin.bms.scmp.api.product.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * 商品编号	厂商指导价	名称	简称	规格	单位	型号	等级	拆零系数	状态	属性	特征	颜色	波段	是否记录唯一码	唯一码位数
 */
@Data
public class SkuEditExport extends BaseRowModel {

    @ExcelProperty(index = 0, value = "商品编号")
    private String skuCode;

    @ExcelProperty(index = 1, value = "厂商指导价")
    private String manufacturerGuidePrice;

    @ExcelProperty(index = 2, value = "名称")
    private String skuName;

    @ExcelProperty(index = 3, value = "简称")
    private String skuAbbreviation;

    @ExcelProperty(index = 4, value = "规格")
    private String spec;

    @ExcelProperty(index = 5, value = "单位")
    private String unitName;

    @ExcelProperty(index = 6, value = "型号")
    private String applicableMonthAge;

    @ExcelProperty(index = 7, value = "等级")
    private String level;

    @ExcelProperty(index = 8, value = "拆零系数")
    private String zeroRemovalCoefficient;

    @ExcelProperty(index = 9, value = "状态")
    private String skuStatus;

    @ExcelProperty(index = 10, value = "商品属性")
    private String productPropertyName;

    @ExcelProperty(index = 11, value = "特征")
    private String warehouseTypeName;

    @ExcelProperty(index = 12, value = "颜色")
    private String colorName;

    @ExcelProperty(index = 13, value = "波段")
    private String waveBand = "-";

    @ExcelProperty(index = 14, value = "是否记录唯一码")
    private String uniqueCode;

    @ExcelProperty(index = 15, value = "唯一码位数")
    private String uniqueCodeNumber = "0";

}
