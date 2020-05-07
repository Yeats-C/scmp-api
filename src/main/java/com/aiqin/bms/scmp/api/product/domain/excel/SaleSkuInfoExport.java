package com.aiqin.bms.scmp.api.product.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: sku正式数据导出对象
 * @date: 2019-11-15
 * @time: 17:00
 */
@Data
public class SaleSkuInfoExport extends BaseRowModel {



    @ApiModelProperty("销售区域名称")
    @ExcelProperty(index = 1, value = "销售区域名称")
    private String saleName;

    @ApiModelProperty("销售区域编码")
    @ExcelProperty(index = 2,value = "销售区域编码")
    private String saleCode;


    @ApiModelProperty("sku编码")
    @ExcelProperty(index = 3, value = "SKU编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @ExcelProperty(index = 4, value = "SKU名称")
    private String skuName;

    @ApiModelProperty("商品属性名称")
    @ExcelProperty(index = 5, value = "商品属性") // TODO: 2020-04-08
    private String productPropertyName;

    @ApiModelProperty("商品品牌名称")
    @ExcelProperty(index = 6, value = "商品品牌")// TODO: 2020-04-08
    private String productBrandName;

    @ApiModelProperty("商品品类名称")
    @ExcelProperty(index = 7, value = "品类名称")// TODO: 2020-04-08
    private String productCategoryName;


    @ApiModelProperty("供货渠道类别名称")
    @ExcelProperty(index = 8, value = "供货渠道类别")
    private String categoriesSupplyChannelsName;// TODO: 2020-04-08

    @ApiModelProperty("供应商编码")
    @ExcelProperty(index = 9, value = "供应商编码")
    private String supplyUnitCode;

    @ApiModelProperty("供应商名称")
    @ExcelProperty(index = 10, value = "供应商名称")
    private String supplyUnitName;// TODO: 2020-04-08

    @ApiModelProperty("采购组名称")
    @ExcelProperty(index = 11, value = "采购组")// TODO: 2020-04-08
    private String procurementSectionName;

}