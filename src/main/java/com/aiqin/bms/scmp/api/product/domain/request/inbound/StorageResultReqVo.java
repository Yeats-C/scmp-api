package com.aiqin.bms.scmp.api.product.domain.request.inbound;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author HuangLong
 * @date 2018/12/27
 */
@Data
@ApiModel("配送采购单入库后库房回调接口的请求参数对象")
public class StorageResultReqVo {

    @ApiModelProperty("采购单编号")
    private String purchaseCode;

    @ApiModelProperty("入库数据录入人员")
    private String userName;

    @ApiModelProperty("实际到货销售单位数量")
    private Long saleUnitActualNum;

    @ApiModelProperty("实际不含税采购总金额")
    private BigDecimal noTaxActualAmount;

    @ApiModelProperty("实际到货数量")
    private Long actualNum;

    @ApiModelProperty("实际含税采购总金额")
    private BigDecimal actualAmount;

    @ApiModelProperty("采购单商品集合")
    private List<StorageResultItemReqVo> itemReqVos;
}
