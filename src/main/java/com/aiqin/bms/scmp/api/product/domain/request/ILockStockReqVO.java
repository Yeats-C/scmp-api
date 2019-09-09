package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.common.OutboundTypeEnum;
import com.aiqin.bms.scmp.api.common.StockStatusEnum;
import io.swagger.annotations.ApiModel;

import java.util.Date;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ILockStockReqVO
 * @date 2019/1/8 19:35
 * @description 库存锁定请求Vo
 */
@ApiModel("库存锁定请求Vo")
public interface ILockStockReqVO {

    String getSourceOderCode();

    OutboundTypeEnum getOutboundTypeCode();

    StockStatusEnum getStockStatusCode();

    String getCompanyCode();

    String getCompanyName();

    String getSupplyCode();

    String getSupplyName();

    String getTransportCenterCode();

    String getTransportCenterName();

    String getWarehouseCode();

    String getWarehouseName();

    String getPurchaseGroupCode();

    String getPurchaseGroupName();

    Date getOutboundTime();

    Date getPreArrivalTime();

    Long getPreOutboundNum();

    Long getPreTaxAmount();

    String getOperator();

    Long getPreMainUnitNum();

    Long getPreAmount();

    List<? extends ILockStockItemReqVo> getItemReqVos();


}
