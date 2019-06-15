package com.aiqin.bms.scmp.api.purchase.domain.request.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 * 修改订单传入的vo
 * @author: NullPointException
 * @date: 2019-06-15
 * @time: 10:20
 */
@ApiModel("修改订单状态vo")
@Data
public class ChangeOrderStatusReqVO {

    @ApiModelProperty("订单状态")
    private Integer orderStatus;

    @ApiModelProperty("订单编码")
    private String orderCode;

    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty("操作人编码")
    private String operatorCode;

    @ApiModelProperty("备注")
    private String remark;
}
