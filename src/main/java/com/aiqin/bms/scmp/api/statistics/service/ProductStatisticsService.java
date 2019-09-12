package com.aiqin.bms.scmp.api.statistics.service;

import com.aiqin.bms.scmp.api.statistics.domain.request.ProductRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.product.ProductMovableResponse;
import com.aiqin.ground.util.protocol.http.HttpResponse;

public interface ProductStatisticsService {

    HttpResponse<ProductMovableResponse> productMovable(ProductRequest request);
}
