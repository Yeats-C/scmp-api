package com.aiqin.bms.scmp.api.excel.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel
@Data
public class RejectRecordDetailExcel extends BaseRowModel {


    @ApiModelProperty(value = "退供单号")
    @ExcelProperty(index = 0, value = "退供单编号")
    private String rejectRecordCode;


    @ApiModelProperty(value = "行号")
    @ExcelProperty(index = 1, value = "行号")
    private Integer lineCode;


    @ApiModelProperty(value = "sku编号")
    @ExcelProperty(index = 2, value = "商品编码")
    private String skuCode;

    @ApiModelProperty(value = "sku名称")
    @ExcelProperty(index = 3, value = "商品名称")
    private String skuName;

    @ApiModelProperty(value = "规格")
    @ExcelProperty(index = 4, value = "规格")
    private String productSpec;


    @ApiModelProperty(value = "单位")
    @ExcelProperty(index = 5, value = "单位")
    private String unitName;

    @ApiModelProperty(value = "税率")
    @ExcelProperty(index = 6, value = "进项税率")
    private BigDecimal taxRate;

    @ApiModelProperty(value = "含税单价")
    @ExcelProperty(index = 8, value = "含税进进价")
    private BigDecimal productAmount;


    @ApiModelProperty(value = "最小单位数量")
    @ExcelProperty(index = 11, value = "单品数量")
    private Long totalCount;

    @ApiModelProperty(value = "含税总价")
    @ExcelProperty(index = 12, value = "订货含税金额")
    private BigDecimal productTotalAmount;


    @ApiModelProperty(value = "实际单品数量")
    @ExcelProperty(index = 14, value = "实际单品数量")
    private Integer actualTotalCount;

    @ApiModelProperty(value = "实际含税总价")
    @ExcelProperty(index = 15, value = "实际含税金额")
    private BigDecimal actualProductTotalAmount;


    @ApiModelProperty(value = "库存含税成本")
    @ExcelProperty(index = 17, value = "仓卡价格")
    private BigDecimal productCost;


}