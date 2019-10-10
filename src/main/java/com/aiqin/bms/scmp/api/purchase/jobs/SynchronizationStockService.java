package com.aiqin.bms.scmp.api.purchase.jobs;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.aiqin.ground.util.protocol.http.HttpResponse;

public interface SynchronizationStockService {

    HttpResponse synchronizationStock(PagesRequest request, Integer isPage);
}
