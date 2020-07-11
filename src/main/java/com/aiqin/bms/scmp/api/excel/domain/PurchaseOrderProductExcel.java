package com.aiqin.bms.scmp.api.excel.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel
@Data
public class PurchaseOrderProductExcel extends BaseRowModel {


    @ApiModelProperty(value = "采购单编码")
    @ExcelProperty(index = 0, value = "采购单编号")
    private String purchaseOrderCode;

    @ApiModelProperty(value = "行号")
    @ExcelProperty(index = 1, value = "行号")
    private Integer linnum;

    @ApiModelProperty(value = "sku编号")
    @ExcelProperty(index = 2, value = "商品编码")
    private String skuCode;

    @ApiModelProperty(value = "sku名称")
    @ExcelProperty(index = 3, value = "商品名称")
    private String skuName;

    @ApiModelProperty(value = "规格")
    @ExcelProperty(index = 4, value = "规格")
    private String productSpec;


    @ApiModelProperty(value = "税率")
    @ExcelProperty(index = 6, value = "进项税率")
    private BigDecimal taxRate;


    @ExcelProperty(index = 7, value = "基商品含量")
    private String productCount;

    @ExcelProperty(index = 8, value = "单位")
    private String danwei;


    @ApiModelProperty(value = "含税单价")
    @ExcelProperty(index = 9, value = "含税进进价")
    private BigDecimal productAmount;


    @ExcelProperty(index = 11, value = "类别")
    private String liebie;





    @ApiModelProperty(value = "采购件数（整数）")
    @ExcelProperty(index = 12, value = "订货包装数整")
    private Integer purchaseWhole;

    @ApiModelProperty(value = "采购件数（零数）")
    @ExcelProperty(index = 13, value = "订货包装-零")
    private Integer purchaseSingle;


    @ApiModelProperty(value = "单品数量")
    @ExcelProperty(index = 14, value = "单品数量")
    private Integer singleCount;

    @ApiModelProperty(value = "含税总价")
    @ExcelProperty(index = 15, value = "订货含税金额")
    private BigDecimal productTotalAmount;


    @ApiModelProperty(value = "实际单品数量")
    @ExcelProperty(index = 17, value = "实际单品数量")
    private Integer actualSingleCount;


}