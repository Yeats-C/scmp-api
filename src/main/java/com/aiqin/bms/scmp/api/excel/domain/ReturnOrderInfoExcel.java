package com.aiqin.bms.scmp.api.excel.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("退货订单主表")
@Data
public class ReturnOrderInfoExcel extends BaseRowModel {


    @ApiModelProperty(value = "退货订单编码")
    @ExcelProperty(index = 0, value = "退货单号")
    private String returnOrderCode;


    @ApiModelProperty(value = "客户名称")
    @ExcelProperty(index = 3, value = "客户名称")
    private String customerName;


    @ApiModelProperty(value = "物流中心名称")
    @ExcelProperty(index = 6, value = "配送中心")
    private String transportCenterName;


    @ApiModelProperty(value = "商品数量")
    @ExcelProperty(index = 7, value = "退货数量")
    private Long productCount;


    @ApiModelProperty(value = "实际退货数量")
    @ExcelProperty(index = 8, value = "实退数量")
    private Long actualProductCount;


    @ApiModelProperty(value = "退货金额（分销）")
    @ExcelProperty(index = 9, value = "退货金额")
    private BigDecimal returnOrderAmount;


    @ApiModelProperty(value = "实际分销退货金额")
    @ExcelProperty(index = 10, value = "实际分销退货金额")
    private BigDecimal actualReturnOrderAmount;


    @ApiModelProperty(value = "创建时间")
    @ExcelProperty(index = 11, value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;


    @ApiModelProperty(value = "创建人名称")
    @ExcelProperty(index = 12, value = "创建人")
    private String createByName;


    @ApiModelProperty(value = "修改人名称")
    @ExcelProperty(index = 14, value = "操作人")
    private String updateByName;


    @ApiModelProperty(value = "发货时间")
    @ExcelProperty(index = 15, value = "验收时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryTime;


    @ApiModelProperty(value = "收货时间")
    @ExcelProperty(index = 16, value = "回单确认时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receivingTime;


}