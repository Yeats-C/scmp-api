package com.aiqin.bms.scmp.api.product.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PriceImport {

    @ApiModelProperty("sku编码")
    @ExcelProperty(index = 0, value = "sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @ExcelProperty(index = 1, value = "sku名称")
    private String skuName;

    @ApiModelProperty("供应商编码")
    @ExcelProperty(index = 2, value = "供应商编码")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    @ExcelProperty(index = 3, value = "供应商名称")
    private String supplierName;

    @ApiModelProperty("含税采购价")
    @ExcelProperty(index = 4, value = "含税采购价")
    private String purchasePriceNew;

    @ApiModelProperty("开始生效时间")
    @ExcelProperty(index = 5, value = "生效时间", format = "yyyy-MM-dd")
    private String effectiveTimeStart;

    @ApiModelProperty("结束生效时间")
    @ExcelProperty(index = 5, value = "结束时间", format = "yyyy-MM-dd")
    private String effectiveTimeEnd;

    @ApiModelProperty("价格项目名称")
    private String priceItemName;

    @ApiModelProperty("仓库批次号名称")
    private String warehouseBatchName;

    @ApiModelProperty("新含税价")
    private String newPrice;

    @ApiModelProperty("调价原因描述")
    private String changePriceReasonName;

    @ApiModelProperty("临时含税价")
    private String temporaryPrice;
}