package com.aiqin.bms.scmp.api.product.domain.request;

import io.swagger.annotations.ApiModel;

/**
 * @author knight.xie
 * @version 1.0
 * @className ILockStockItemReqVo
 * @date 2019/1/9 10:46
 * @description 库存锁定明细请求VO
 */
@ApiModel("库存锁定明细请求VO")
public interface ILockStockItemReqVo {

    String getSkuCode();

    String getSkuName();

    String getPictureUrl();

    String getNorms();

    String getUnitCode();

    String getUnitName();

    String getColorName();

    String getColorCode();

    String getPurchaseNorms();

    Long getPreOutboundNum();

    Long getPreOutboundMainNum();

    Long getPreTaxPurchaseAmount();

    Long getPreTaxAmount();

    Long getNum();

    String getOperator();
}
