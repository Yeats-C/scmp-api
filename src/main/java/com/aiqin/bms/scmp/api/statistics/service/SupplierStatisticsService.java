package com.aiqin.bms.scmp.api.statistics.service;

import com.aiqin.bms.scmp.api.statistics.domain.response.SupplierDeliveryResponse;
import com.aiqin.ground.util.protocol.http.HttpResponse;

public interface SupplierStatisticsService {

    HttpResponse<SupplierDeliveryResponse> supplierDelivery(String date, Integer reportType, String productSortCode);
}
