package com.aiqin.bms.scmp.api.product.domain.response.outbound;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Classname: ReturnStorageResultReqVo
 * 描述:  退供回传实体
 * @Author: Kt.w
 * @Date: 2019/3/26
 * @Version 1.0
 * @Since 1.0
 */
@ApiModel("退供回传实体")
@Data
public class ReturnStorageResultReqVo {
    @ApiModelProperty("采购单编号")
    private String returnSupplyCode;

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
    private List<ReturnStorageResultItemReqVo> itemReqVos;
}
