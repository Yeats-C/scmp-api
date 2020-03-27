package com.aiqin.bms.scmp.api.product.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-07-18
 * @time: 14:37
 */
@Data
public class SkuSaleAreaImport extends BaseRowModel {

    public static final String HEDE = "SkuSaleAreaImport(saleName=SKU编号, skuCode=sku编码, skuName=sku名称, categoriesSupplyChannelsName=供货渠道类别名称, directDeliverySupplierName=直送供应商名称)";


    @ApiModelProperty("限制区域名称")
    @ExcelProperty(index = 0 , value = "销售区域名称")
    private String saleName;

    @ApiModelProperty(value = "sku编码")
    @ExcelProperty(index = 1 , value = "sku编码")
    private String skuCode;

    @ApiModelProperty(value = "sku名称")
    @ExcelProperty(index = 2 , value = "sku名称")
    private String skuName;

    @ApiModelProperty("供货渠道类别名称")
    @ExcelProperty(index = 3 , value = "sku名称")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("直送供应商名称")
    @ExcelProperty(index = 4 , value = "sku名称")
    private String directDeliverySupplierName;


}
