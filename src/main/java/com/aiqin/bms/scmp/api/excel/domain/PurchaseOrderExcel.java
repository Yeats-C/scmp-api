package com.aiqin.bms.scmp.api.excel.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class PurchaseOrderExcel extends BaseRowModel {


    @ApiModelProperty(value = "采购单号")
    @ExcelProperty(index = 0, value = "采购单编号")
    private String purchaseOrderCode;

    /**
     * 不做excel映射
     */
    @ApiModelProperty(value = "采购单id")
    private String purchaseOrderId;


//    @ApiModelProperty(value = "库房编码")
//    @ExcelProperty(index = 1, value = "配送中心编号")
//    private String warehouseCode;

    @ApiModelProperty(value = "供应商编码")
    @ExcelProperty(index = 2, value = "供应商编码")
    private String supplierCode;

    @ApiModelProperty(value = "供应商名称")
    @ExcelProperty(index = 3, value = "供货单位名称")
    private String supplierName;


    @ApiModelProperty(value = "最小单位数量")
    @ExcelProperty(index = 4, value = "商品数量")
    private String singleCount;

    @ApiModelProperty(value = "商品含税金额")
    @ExcelProperty(index = 5, value = "product_total_amount")
    private BigDecimal productTotalAmount;

    @ApiModelProperty(value = "实物返含税金额")
    @ExcelProperty(index = 6, value = "实物返订单金额")
    private BigDecimal returnAmount;

    @ApiModelProperty(value = "赠品含税金额")
    @ExcelProperty(index = 7, value = "赠品订单金额")
    private BigDecimal giftTaxSum;

    @ApiModelProperty(value = "实际最小单位数量")
    @ExcelProperty(index = 8, value = "实际商品数量")
    private String actualTotalCount;

    @ApiModelProperty(value = "实际商品金额")
    @ExcelProperty(index = 9, value = "实际普通订单金额")
    private BigDecimal actualProductAmount;

    @ApiModelProperty(value = "实际实物返订单金额")
    @ExcelProperty(index = 10, value = "实际实物返金额")
    private BigDecimal actualReturnAmount;

    @ApiModelProperty(value = "实际赠品订单金额")
    @ExcelProperty(index = 11, value = "实际赠品金额")
    private BigDecimal actualGiftAmount;

    @ApiModelProperty(value = "创建时间")
    @ExcelProperty(index = 12, value = "订货时间")
    private Date createTime;


    @ApiModelProperty(value = "订货人")
    @ExcelProperty(index = 13, value = "创建人名称")
    private String createByName;

    @ApiModelProperty(value = "状态")
    @ExcelProperty(index = 14, value = "状态")
    private String purchaseOrderStatusMsg;


    @ApiModelProperty(value = "预计到货时间")
    @ExcelProperty(index = 15, value = "预计到货日期")
    private String preArrivalDate;


    @ApiModelProperty(value = "更新时间")
    @ExcelProperty(index = 18, value = "操作时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "修改人名称")
    @ExcelProperty(index = 19, value = "操作人")
    private String updateByName;



    @ApiModelProperty(value = "库房编号")
    @ExcelProperty(index = 20, value = "仓库编号")
    private String warehouseCode;


    @ApiModelProperty(value = "库房名称")
    @ExcelProperty(index = 21, value = "仓库名称")
    private String warehouseName;


    @ApiModelProperty(value = "仓库编号")
    @ExcelProperty(index = 22, value = "库房编号")
    private String transportCenterCode;


    @ApiModelProperty(value = "仓库名称")
    @ExcelProperty(index = 23, value = "库房名称")
    private String transportCenterName;






    @ApiModelProperty(value = "采购组编码")
    @ExcelProperty(index = 24, value = "采购组 编码")
    private String purchaseGroupCode;


    @ApiModelProperty(value = "采购组名称")
    @ExcelProperty(index = 25, value = "采购组名称")
    private String purchaseGroupName;

}