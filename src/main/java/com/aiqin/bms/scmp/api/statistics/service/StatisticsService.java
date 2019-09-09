package com.aiqin.bms.scmp.api.statistics.service;

import com.aiqin.bms.scmp.api.statistics.domain.response.StoreRepurchaseRateResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.SupplierDeliveryResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.negative.NegativeSumResponse;
import com.aiqin.ground.util.protocol.http.HttpResponse;

public interface StatisticsService {

    HttpResponse<StoreRepurchaseRateResponse> storeRepurchaseRate(String date, Integer type, String productSortCode);

    HttpResponse<NegativeSumResponse> negativeGross(String date, Integer type, Integer reportType, Long seasonType, String productSortCode);

}
