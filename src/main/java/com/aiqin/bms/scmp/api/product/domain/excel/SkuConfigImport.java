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
public class SkuConfigImport extends BaseRowModel {

    public static final String HEDE = "SkuConfigImport(skuCode=SKU编号, skuName=SKU名称, transportCenterName=仓库, configStatusName=状态, orderCycle=订货周期, arrivalCycle=到货周期, turnoverPeriodAfterArrival=到货后周转期, basicInventoryDay=基本库存天数, largeInventoryWarnDay=大库存预警天数, bigEffectPeriodWarnDay=大效期预警天数, spareWarehouses=备用仓)";

    @ApiModelProperty(value = "sku编码")
    @ExcelProperty(index = 0 , value = "sku编码")
    private String skuCode;

    @ApiModelProperty(value = "sku名称")
    @ExcelProperty(index = 1 , value = "sku名称")
    private String skuName;

    @ApiModelProperty("物流中心(仓库)名称")
    @ExcelProperty(index = 2 , value = "仓库")
    private String transportCenterName;

    @ApiModelProperty("配置状态(0:再用 1:停止进货 2:停止配送 3:停止销售)")
    @ExcelProperty(index = 3 , value = "状态")
    private String configStatusName;

    @ApiModelProperty("订货周期")
    @ExcelProperty(index = 4 , value = "订货周期")
    private String orderCycle;

    @ApiModelProperty("到货周期")
    @ExcelProperty(index = 5 , value = "到货周期")
    private String arrivalCycle;

    @ApiModelProperty("到货后周转期")
    @ExcelProperty(index = 6 , value = "到货后周转期")
    private String turnoverPeriodAfterArrival;

    @ApiModelProperty("基本库存天数")
    @ExcelProperty(index = 7 , value = "基本库存天数")
    private String basicInventoryDay;

    @ApiModelProperty("大库存预警天数")
    @ExcelProperty(index = 8 , value = "大库存预警天数")
    private String largeInventoryWarnDay;

    @ApiModelProperty("大效期预警天数")
    @ExcelProperty(index = 9 , value = "大效期预警天数")
    private String bigEffectPeriodWarnDay;

    @ApiModelProperty("备用仓库")
    @ExcelProperty(index = 10 , value = "备用仓")
    private String spareWarehouses;
}
