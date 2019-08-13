package com.aiqin.bms.scmp.api.bireport.service;

import com.aiqin.bms.scmp.api.bireport.domain.BiGoodsSalesReport;
import com.aiqin.bms.scmp.api.bireport.domain.request.ProductAndStockRequest;
import com.aiqin.bms.scmp.api.bireport.domain.response.ProductAndStockResponse;
import com.aiqin.ground.util.protocol.http.HttpResponse;

public interface ReportFormService {

    HttpResponse<BiGoodsSalesReport> productSaleInfo(ProductAndStockRequest productAndStock);

    HttpResponse<ProductAndStockResponse> stockTurnover(ProductAndStockRequest productAndStock);

    HttpResponse<ProductAndStockResponse> stockWayTurnover(ProductAndStockRequest productAndStock);
}
