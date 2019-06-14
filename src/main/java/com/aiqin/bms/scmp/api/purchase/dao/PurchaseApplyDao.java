package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApply;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyResponse;

import java.util.List;

public interface PurchaseApplyDao {

    Integer insert(PurchaseApply record);

    Integer update(PurchaseApply record);

    List<PurchaseApplyResponse> applyList(PurchaseApplyRequest purchaseApplyRequest);

    Integer applyCount(PurchaseApplyRequest purchaseApplyRequest);

}