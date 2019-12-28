package com.aiqin.bms.scmp.api.product.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:sku商品制造商新增导入实体
 * @date: 2019-11-21
 * @time: 10:24
 */
@Data
@ApiModel("sku商品制造商新增导入实体")
public class SkuManufactureImport extends BaseRowModel {

    @ApiModelProperty("sku编号")
    @ExcelProperty(index = 0, value = "sku编号")
    private String productSkuCode;

    @ApiModelProperty("sku名称")
    @ExcelProperty(index = 1, value = "sku名称")
    private String productSkuName;

    @ApiModelProperty("制造商编号")
    @ExcelProperty(index = 2, value = "制造商编号")
    private String manufacturerCode;

    @ApiModelProperty("制造商名称")
    @ExcelProperty(index = 3, value = "制造商名称")
    private String manufacturerName;

    @ApiModelProperty("制造商sku编号")
    @ExcelProperty(index = 4, value = "制造商sku编号")
    private String factoryProductNumber;

    @ApiModelProperty("保修地址")
    @ExcelProperty(index = 5, value = "保修地址")
    private String address;

    @ApiModelProperty("默认值")
    @ExcelProperty(index = 6, value = "默认值")
    private String isDefaultDesc;

    /**1是 0否**/
    private Byte isDefault;

    @ApiModelProperty("状态")
    @ExcelProperty(index = 7, value = "状态")
    private String usageStatusDesc;

    /**0:禁用用 1:在用**/
    private Byte usageStatus;

}