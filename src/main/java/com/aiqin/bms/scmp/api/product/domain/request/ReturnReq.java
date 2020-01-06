package com.aiqin.bms.scmp.api.product.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Auther: mamingze
 * @Date: 2020-01-03 18:11
 * @Description:
 */
@Data
public class ReturnReq {
    @ApiModelProperty(value = "退货信息表")
    private ReturnOrderInfoReq returnOrderInfo;
    @ApiModelProperty(value = "退货单明细")
    private List<ReturnOrderDetailReq> returnOrderDetailReqList;
}
