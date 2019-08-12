package com.aiqin.bms.scmp.api.product.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("采购价导入")
public class PurchasePriceImport extends BaseRowModel {

    public static final String HEAD = "PurchasePriceImport(skuCode=SKU编号, skuName=SKU名称, supplierCode=供应商编号, supplierName=供应商名称, purchasePriceNew=含税采购价, effectiveTimeStart=生效时间)";
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
    @ExcelProperty(index = 5, value = "生效时间", format = "yyyy-MM-dd HH:mm")
    private String effectiveTimeStart;

}
