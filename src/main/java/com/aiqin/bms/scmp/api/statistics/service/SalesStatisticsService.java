package com.aiqin.bms.scmp.api.statistics.service;

import com.aiqin.bms.scmp.api.statistics.domain.request.SaleRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.sale.SaleSumResponse;
import com.aiqin.ground.util.protocol.http.HttpResponse;

public interface SalesStatisticsService {

    HttpResponse<SaleSumResponse> saleInfo(SaleRequest saleRequest);

    HttpResponse<SaleSumResponse> monthSaleInfo(SaleRequest saleRequest);
}
