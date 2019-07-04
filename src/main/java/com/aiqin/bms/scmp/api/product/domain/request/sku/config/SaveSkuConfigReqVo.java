package com.aiqin.bms.scmp.api.product.domain.request.sku.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className SaveSkuConfigReqVo
 * @date 2019/5/25 10:03
 * @description TODO
 */
@ApiModel("保存SKU配置信息VO")
@Data
public class SaveSkuConfigReqVo {

    @ApiModelProperty(value = "商品名称",hidden = true)
    private String productCode;

    @ApiModelProperty(value = "商品编码",hidden = true)
    private String productName;

    @ApiModelProperty(value = "sku编码",hidden = true)
    private String skuCode;

    @ApiModelProperty(value = "sku名称",hidden = true)
    private String skuName;

    @ApiModelProperty("物流中心(仓库)编码")
    private String transportCenterCode;

    @ApiModelProperty("物流中心(仓库)名称")
    private String transportCenterName;

    @ApiModelProperty("配置状态(0:再用 1:停止进货 2:停止配送 3:停止销售)")
    private Byte configStatus;

    @ApiModelProperty("到货周期")
    private Integer arrivalCycle;

    @ApiModelProperty("订货周期")
    private Integer orderCycle;

    @ApiModelProperty("基本库存天数")
    private Integer basicInventoryDay;

    @ApiModelProperty("大库存预警天数")
    private Integer largeInventoryWarnDay;

    @ApiModelProperty("大效期预警天数")
    private Integer bigEffectPeriodWarnDay;

    @ApiModelProperty("到货后周转期")
    private Integer turnoverPeriodAfterArrival;

    @ApiModelProperty("备用仓库")
    private List<SpareWarehouseReqVo> spareWarehouses;
}
