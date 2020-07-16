package com.aiqin.bms.scmp.api.excel.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("订单商品信息")
@Data
public class OrderInfoItemExcel extends BaseRowModel {


    @ApiModelProperty("订单主表编码")
    @ExcelProperty(index = 0, value = "销售单号")
    private String orderCode;


    @ApiModelProperty("商品行号")
    @ExcelProperty(index = 1, value = "行号")
    private Integer productLineNum;

    @ApiModelProperty("sku编号")
    @ExcelProperty(index = 2, value = "销售单号")
    private String skuCode;


    @ApiModelProperty("规格")
    @ExcelProperty(index = 3, value = "规格")
    private String spec;


    @ApiModelProperty("商品单位")
    @ExcelProperty(index = 4, value = "单位")
    private String unitName;


    @ApiModelProperty("是否是赠品")
    @ExcelProperty(index = 5, value = "是否赠品")
    private Integer givePromotion;




    @ApiModelProperty("数量")
    @ExcelProperty(index = 7, value = "订货数量")
    private String num;


    @ApiModelProperty("实际分销单价")
    @ExcelProperty(index = 8, value = "价格")
    private BigDecimal actualPrice;


    @ApiModelProperty("优惠分摊")
    @ExcelProperty(index = 9, value = "优惠券抵扣价格")
    private BigDecimal preferentialAllocation;

    @ApiModelProperty("分销总价")
    @ExcelProperty(index = 10, value = "订货金额")
    private BigDecimal amount;


    @ApiModelProperty("实发数量")
    @ExcelProperty(index = 11, value = "实发数量")
    private Long actualDeliverNum;


    @ApiModelProperty("实际分销总价")
    @ExcelProperty(index = 12, value = "实发金额")
    private BigDecimal actualAmount;


}