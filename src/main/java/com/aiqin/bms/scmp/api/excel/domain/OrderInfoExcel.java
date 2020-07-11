package com.aiqin.bms.scmp.api.excel.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("订单主表")
@Data
public class OrderInfoExcel extends BaseRowModel {


    @ApiModelProperty("订单编码(订单号)")
    @ExcelProperty(index = 0, value = "销售单号")
    private String orderCode;


    @ApiModelProperty("客户编码")
    @ExcelProperty(index = 1, value = "客户编码")
    private String customerCode;

    @ApiModelProperty("客户名称")
    @ExcelProperty(index = 2, value = "客户名称")
    private String customerName;


    @ApiModelProperty("物流中心编码")
    @ExcelProperty(index = 5, value = "配送中心")
    private String transportCenterCode;


    @ApiModelProperty("创建时间")
    @ExcelProperty(index = 9, value = "申请时间")
    private Date createTime;


    @ApiModelProperty("创建人")
    @ExcelProperty(index = 10, value = "申请人")
    private String createByName;


    @ApiModelProperty("修改时间")
    @ExcelProperty(index = 11, value = "审核时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    @ExcelProperty(index = 12, value = "审核人")
    private String updateByName;


    @ApiModelProperty(value = "实际运费")
    @ExcelProperty(index = 16, value = "实付物流费")
    private BigDecimal actualDeliverAmount;


    @ApiModelProperty("实际发货数量")
    @ExcelProperty(index = 17, value = "实发数量")
    private Long actualProductNum;


    @ApiModelProperty("实际订单金额")
    @ExcelProperty(index = 18, value = "实付金额")
    private BigDecimal actualOrderAmount;

    @ApiModelProperty("操作时间")
    @ExcelProperty(index = 19, value = "操作时间")
    private Date operatorTime;


    @ApiModelProperty("操作人")
    @ExcelProperty(index = 20, value = "操作人")
    private String operator;

    @ApiModelProperty("发货时间")
    @ExcelProperty(index = 21, value = "发货时间")
    private Date deliveryTime;


    @ApiModelProperty("收货时间")
    @ExcelProperty(index = 22, value = "回单确认时间")
    private Date receivingTime;


}