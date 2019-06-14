package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyResponse;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseApplyService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-06-13
 **/
@Service
public class PurchaseApplyServiceImpl implements PurchaseApplyService {

    @Override
    public HttpResponse<List<PurchaseApplyResponse>> applyList(PurchaseApplyRequest purchaseApplyRequest){

        return HttpResponse.success();
    }
}
