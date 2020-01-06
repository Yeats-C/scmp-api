package com.aiqin.bms.scmp.api.product.domain.request;

import lombok.Data;

import java.util.List;

/**
 * @Auther: mamingze
 * @Date: 2020-01-03 18:11
 * @Description:
 */
@Data
public class ReturnReq {

    private ReturnOrderInfoReq returnOrderInfo;

    private List<ReturnOrderDetailReq> returnOrderDetailReqList;
}
