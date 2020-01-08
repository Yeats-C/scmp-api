package com.aiqin.bms.scmp.api.product.domain.response;

import com.aiqin.bms.scmp.api.product.domain.request.ReturnBatchDetailDLReq;
import com.aiqin.bms.scmp.api.product.domain.request.ReturnOrderDetailDLReq;
import com.aiqin.bms.scmp.api.product.domain.request.ReturnOrderInfoDLReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Auther: mamingze
 * @Date: 2020-01-03 18:11
 * @Description:
 */
@Data
public class ReturnDLResp {
    @ApiModelProperty(value = "DL退货主表")
    private ReturnOrderInfoDLReq returnOrderInfoDLReq;
    @ApiModelProperty(value = "退货单明细")
    private List<ReturnOrderDetailDLReq> returnOrderDetailDLReqList;

    @ApiModelProperty(value = "退货单批次明细")
    private List<ReturnBatchDetailDLReq> returnBatchDetailDLReqList;
}
