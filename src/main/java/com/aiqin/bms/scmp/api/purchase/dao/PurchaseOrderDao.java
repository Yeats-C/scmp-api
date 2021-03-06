package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.excel.domain.PurchaseOrderExcel;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseOrderResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseOrderDao {

    Integer insert(PurchaseOrder record);

    Integer update(PurchaseOrder record);

    List<PurchaseOrderResponse> purchaseOrderList(PurchaseApplyRequest purchaseApplyRequest);

    Integer purchaseOrderCount(PurchaseApplyRequest purchaseApplyRequest);

    PurchaseOrder purchaseOrder(String purchaseOrderId);

    PurchaseOrder purchaseOrderInfo(PurchaseOrder purchaseOrder);

    List<PurchaseOrder> orderByExecuteWarehousing(@Param("beginTime") String beginTime, @Param("finishTime") String finishTime);

    List<PurchaseOrder> listForSap(SapOrderRequest sapOrderRequest);

    void updateByOrderCodes(List<String> orderCodes);

    List<String> getPurchaseOrderPre(@Param("purchaseGroupCode") String purchaseGroupCode,
                                     @Param("purchaseOrderTypeCode") Integer purchaseOrderTypeCode,
                                     @Param("purchaseOrderCode") String purchaseOrderCode);

    List<PurchaseOrder> orderList(@Param("purchaseOrderCode") String purchaseOrderCode);

    List<PurchaseOrder> selectByPurchaseOrderCode(@Param("codes") List<String> code);

    int insertMany(@Param("list") List<PurchaseOrder> purchaseOrders);

    List<PurchaseOrder> selectByPurchaseApplyCode(@Param("purchaseApplyCode") String purchaseApplyCode);
}