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

    @ApiModelProperty("sku编号")
    @ExcelProperty(index = 2, value = "销售单号")
    private String skuCode;


    @ApiModelProperty("规格")
    @ExcelProperty(index = 3, value = "规格")
    private String spec;


    @ApiModelProperty("商品单位")
    @ExcelProperty(index = 4, value = "单位")
    private String unitName;


    @ApiModelProperty("实际分销单价")
    @ExcelProperty(index = 8, value = "价格")
    private BigDecimal actualPrice;


    @ApiModelProperty("实发数量")
    @ExcelProperty(index = 11, value = "实发数量")
    private Long actualDeliverNum;


    @ApiModelProperty("实际分销总价")
    @ExcelProperty(index = 12, value = "实发金额")
    private BigDecimal actualAmount;


}