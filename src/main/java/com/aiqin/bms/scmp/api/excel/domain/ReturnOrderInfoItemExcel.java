package com.aiqin.bms.scmp.api.excel.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("订单商品信息")
@Data
public class ReturnOrderInfoItemExcel  extends BaseRowModel {
    @ApiModelProperty(value = "退货订单编码")
    @ExcelProperty(index = 0, value = "退货单号")
    private String returnOrderCode;


    @ApiModelProperty("sku编号")
    @ExcelProperty(index = 2, value = "商品编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @ExcelProperty(index = 3, value = "商品名称")
    private String skuName;


    @ApiModelProperty("规格")
    @ExcelProperty(index = 4, value = "商品名称")
    private String spec;


    @ApiModelProperty("单位")
    @ExcelProperty(index = 5, value = "商品单位")
    private String unitName;

    @ApiModelProperty(value = "数量")
    @ExcelProperty(index = 6, value = "退货数量")
    private Integer num;

    @ApiModelProperty(value = "实际入库数量")
    @ExcelProperty(index = 7, value = "实际退货数量")
    private Integer actualInboundNum;


    @ApiModelProperty(value = "分销单价")
    @ExcelProperty(index = 8, value = "价格")
    private BigDecimal price;



    @ApiModelProperty(value = "分销总价")
    @ExcelProperty(index = 9, value = "退货金额")
    private BigDecimal amount;



    @ApiModelProperty(value = "实际分销总价")
    @ExcelProperty(index = 10, value = "实退金额")
    private BigDecimal actualPrice;

}