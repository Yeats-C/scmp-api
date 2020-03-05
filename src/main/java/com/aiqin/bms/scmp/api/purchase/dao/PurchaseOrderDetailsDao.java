package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderDetails;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;

import java.util.List;

public interface PurchaseOrderDetailsDao {

    Integer insert(PurchaseOrderDetails record);

    Integer update(PurchaseOrderDetails record);

    PurchaseApplyDetailResponse purchaseOrderDetail(String purchaseOrderCode);

    /**
     * 根据采购单id对应的详情
     * @param list
     * @return
     */
    List<PurchaseOrderDetails> listByCodes(List<String> list);

    PurchaseOrderDetails orderDetail(String purchaseOrderCode);
}