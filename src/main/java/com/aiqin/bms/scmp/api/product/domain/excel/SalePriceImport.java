package com.aiqin.bms.scmp.api.product.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SalePriceImport extends BaseRowModel {
    public static final String HEAD = "SalePriceImport(skuCode=SKU编号, skuName=SKU名称, priceItemName=价格项目, effectiveTimeStart=生效时间, newPrice=含税价, warehouseBatchName=仓库批次号, changePriceReasonName=调价原因)";
    @ApiModelProperty("sku编码")
    @ExcelProperty(index = 0, value = "sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @ExcelProperty(index = 1, value = "sku名称")
    private String skuName;

    @ApiModelProperty("价格项目名称")
    @ExcelProperty(index = 2, value = "价格项目")
    private String priceItemName;

    @ApiModelProperty("开始生效时间")
    @ExcelProperty(index = 3, value = "生效时间", format = "yyyy-MM-dd")
    private String effectiveTimeStart;

    @ApiModelProperty("新含税价")
    @ExcelProperty(index = 4, value = "含税价")
    private String newPrice;

    @ApiModelProperty("仓库批次号名称")
    @ExcelProperty(index = 5, value = "仓库批次号")
    private String warehouseBatchName;

    @ApiModelProperty("调价原因描述")
    @ExcelProperty(index = 6, value = "调价原因")
    private String changePriceReasonName;

}
