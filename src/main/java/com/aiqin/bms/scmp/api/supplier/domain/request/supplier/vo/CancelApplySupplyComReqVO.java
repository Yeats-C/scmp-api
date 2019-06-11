package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/12/12 0012 9:46
 */
@ApiModel("撤销供货单位申请请求")
@Data
public class CancelApplySupplyComReqVO {
    @ApiModelProperty("供货单位申请主键id")
    private Long id;

    @ApiModelProperty("结算信息申请主键id")
    private Long settlementInfoId;

    @ApiModelProperty("收货信息申请主键id")
    private Long deliveryInfoId;
}
