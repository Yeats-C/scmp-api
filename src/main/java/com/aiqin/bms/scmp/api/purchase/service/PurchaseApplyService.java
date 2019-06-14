package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyResponse;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;

public interface PurchaseApplyService {

    HttpResponse<List<PurchaseApplyResponse>> applyList(PurchaseApplyRequest purchaseApplyRequest);
}
