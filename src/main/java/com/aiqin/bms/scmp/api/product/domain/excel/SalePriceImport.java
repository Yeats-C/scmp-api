package com.aiqin.bms.scmp.api.product.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 爱亲渠道价	萌贝树渠道价	小红马渠道价	爱亲分销价	萌贝树分销价	小红马分销价	爱亲售价	萌贝树售价	小红马售价
 */
@Data
public class SalePriceImport extends BaseRowModel {
    public static final String HEAD = "SalePriceImport(skuCode=SKU编号, skuName=SKU名称, warehouseBatchName=仓库批次号, effectiveTimeStart=生效时间, readyCol67=爱亲渠道价, readyCol68=萌贝树渠道价, readyCol69=小红马渠道价, readyCol70=爱亲分销价, readyCol71=萌贝树分销价, readyCol72=小红马分销价, readyCol73=爱亲售价, readyCol74=萌贝树售价, readyCol75=小红马售价)";
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

    @ApiModelProperty(value ="爱亲渠道价")
    @ExcelProperty(index = 4, value = "爱亲渠道价")
    private String readyCol67;

    @ApiModelProperty(value ="萌贝树渠道价")
    @ExcelProperty(index = 5, value = "萌贝树渠道价")
    private String readyCol68;

    @ApiModelProperty(value ="小红马渠道价")
    @ExcelProperty(index = 6, value = "小红马渠道价")
    private String readyCol69;

    @ApiModelProperty(value ="爱亲分销价")
    @ExcelProperty(index = 7, value = "爱亲分销价")
    private String readyCol70;

    @ApiModelProperty(value ="萌贝树分销价")
    @ExcelProperty(index = 8, value = "萌贝树分销价")
    private String readyCol71;

    @ApiModelProperty(value ="小红马分销价")
    @ExcelProperty(index = 9, value = "小红马分销价")
    private String readyCol72;

    @ApiModelProperty(value ="爱亲售价")
    @ExcelProperty(index = 10, value = "爱亲售价")
    private String readyCol73;

    @ApiModelProperty(value ="萌贝树售价")
    @ExcelProperty(index = 11, value = "萌贝树售价")
    private String readyCol74;

    @ApiModelProperty(value ="小红马售价")
    @ExcelProperty(index = 12, value = "小红马售价")
    private String readyCol75;
}
