package com.aiqin.bms.scmp.api.product.domain.response;

import com.aiqin.bms.scmp.api.product.domain.request.ReturnOrderDetailReq;
import com.aiqin.bms.scmp.api.product.domain.request.ReturnOrderInfoReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Auther: mamingze
 * @Date: 2020-01-03 18:11
 * @Description:
 */
@Data
public class ReturnResp {
    @ApiModelProperty(value = "退货信息表")
    private ReturnOrderInfoReq returnOrderInfo;
    @ApiModelProperty(value = "退货单明细")
    private List<ReturnOrderDetailReq> returnOrderDetailReqList;
}
