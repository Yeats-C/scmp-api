package com.aiqin.bms.scmp.api.product.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TemporaryPriceImport extends BaseRowModel {
    public static final String HEAD = "TemporaryPriceImport(skuCode=SKU编号, skuName=SKU名称, warehouseBatchName=仓库批次号, effectiveTimeStart=生效时间, effectiveTimeEnd=失效时间, readyCol67=爱亲临时渠道价, readyCol68=萌贝树临时渠道价, readyCol69=小红马临时渠道价, readyCol70=爱亲临时分销价, readyCol71=萌贝树临时分销价, readyCol72=小红马临时分销价, readyCol73=临时售价, readyCol74=临时会员价)";

    @ApiModelProperty("sku编码")
    @ExcelProperty(index = 0, value = "sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @ExcelProperty(index = 1, value = "sku名称")
    private String skuName;

    @ApiModelProperty("仓库批次号名称")
    @ExcelProperty(index = 2, value = "仓库批次号")
    private String warehouseBatchName;

    @ApiModelProperty("开始生效时间")
    @ExcelProperty(index = 3, value = "生效时间", format = "yyyy-MM-dd")
    private String effectiveTimeStart;

    @ApiModelProperty("开始生效时间")
    @ExcelProperty(index = 4, value = "失效时间", format = "yyyy-MM-dd")
    private String effectiveTimeEnd;

    @ApiModelProperty(value ="爱亲临时渠道价")
    @ExcelProperty(index = 5, value = "爱亲临时渠道价")
    private String readyCol67;

    @ApiModelProperty(value ="萌贝树临时渠道价")
    @ExcelProperty(index = 6, value = "萌贝树临时渠道价")
    private String readyCol68;

    @ApiModelProperty(value ="小红马临时渠道价")
    @ExcelProperty(index = 7, value = "小红马临时渠道价")
    private String readyCol69;

    @ApiModelProperty(value ="爱亲临时分销价")
    @ExcelProperty(index = 8, value = "爱亲临时分销价")
    private String readyCol70;

    @ApiModelProperty(value ="萌贝树临时分销价")
    @ExcelProperty(index = 9, value = "萌贝树临时分销价")
    private String readyCol71;

    @ApiModelProperty(value ="小红马临时分销价")
    @ExcelProperty(index = 10, value = "小红马临时分销价")
    private String readyCol72;

    @ApiModelProperty(value ="临时售价")
    @ExcelProperty(index = 11, value = "临时售价")
    private String readyCol73;

    @ApiModelProperty(value ="临时会员价")
    @ExcelProperty(index = 12, value = "临时会员价")
    private String readyCol74;

//    @ApiModelProperty(value ="小红马临时售价")
//    @ExcelProperty(index = 13, value = "小红马临时售价")
//    private String readyCol75;

}
