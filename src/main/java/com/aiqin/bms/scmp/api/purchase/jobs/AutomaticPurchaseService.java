package com.aiqin.bms.scmp.api.purchase.jobs;

import com.aiqin.ground.util.protocol.http.HttpResponse;

public interface AutomaticPurchaseService {

    HttpResponse automaticPurchase();

    HttpResponse executeWarehousing();
}
