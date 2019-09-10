package com.aiqin.bms.scmp.api.statistics.service;

import com.aiqin.bms.scmp.api.statistics.domain.request.SupplierRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.supplier.SupplierDeliveryResponse;
import com.aiqin.ground.util.protocol.http.HttpResponse;

public interface SupplierStatisticsService {

    HttpResponse<SupplierDeliveryResponse> supplierDelivery(SupplierRequest supplierRequest);
}
