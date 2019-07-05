package com.aiqin.bms.scmp.api.product.domain.request;

import io.swagger.annotations.ApiModel;

/**
 * @className ILockStockBatchItemReqVo
 * @date 2019/1/9 10:46
 * @description 批次库存锁定明细请求VO
 */
@ApiModel("库存锁定明细请求VO")
public interface ILockStockBatchItemReqVo {

    String getSkuCode();

    String getSkuName();

    String getBatchCode();

    Long getNum();

    String getOperator();
}
