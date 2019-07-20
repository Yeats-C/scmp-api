package com.aiqin.bms.scmp.api.product.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 * SKU编号	SKU名称	供应商编号	供应商名称	联营扣点(%)	返点(%)	厂商SKU编号	供货渠道类别	是否缺省
 * @author: NullPointException
 * @date: 2019-07-19
 * @time: 16:01
 */
@Data
public class SkuSupplierImport extends BaseRowModel {

    public static final String HEAD = "SkuSupplierImport(productSkuCode=SKU编号, productSkuName=SKU名称, supplyUnitCode=供应商编号, supplyUnitName=供应商名称, jointFranchiseRate=联营扣点(%), point=返点(%), factorySkuCode=厂商SKU编号, categoriesSupplyChannelsName=供货渠道类别, isDefault=是否缺省)";

    @ApiModelProperty("skuCode")
    @ExcelProperty(index = 0 , value = "SKU编号")
    private String productSkuCode;

    @ApiModelProperty("skuName")
    @ExcelProperty(index = 1 , value = "SKU名称")
    private String productSkuName;

    @ApiModelProperty("供货单位code")
    @ExcelProperty(index = 2 , value = "供应商编号")
    private String supplyUnitCode;

    @ApiModelProperty("供货单位名称")
    @ExcelProperty(index = 3 , value = "供应商名称")
    private String supplyUnitName;

    @ApiModelProperty("联营扣率")
    @ExcelProperty(index = 4 , value = "联营扣点(%)")
    private String jointFranchiseRate;

    @ApiModelProperty("返点")
    @ExcelProperty(index = 5 , value = "返点(%)")
    private String point;

    @ApiModelProperty("厂商SKU编码")
    @ExcelProperty(index = 6 , value = "厂商SKU编号")
    private String factorySkuCode;

    @ApiModelProperty(value ="供货渠道类别")
    @ExcelProperty(index = 7 , value = "供货渠道类别")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("是否缺省（0:否,1：是）")
    @ExcelProperty(index = 8 , value = "是否缺省")
    private String isDefault;
}
