package com.aiqin.bms.scmp.api.statistics.service;

import com.aiqin.bms.scmp.api.statistics.domain.response.StoreRepurchaseRateResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.SupplierDeliveryResponse;
import com.aiqin.ground.util.protocol.http.HttpResponse;

public interface StatisticsService {

    HttpResponse<SupplierDeliveryResponse> supplierDelivery(Integer formType, String date);

    HttpResponse<StoreRepurchaseRateResponse> storeRepurchaseRate(String date, Integer type);

}
