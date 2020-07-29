package com.aiqin.bms.scmp.api.abutment.service;

import com.aiqin.bms.scmp.api.abutment.domain.DlStoreInfo;
import com.aiqin.bms.scmp.api.abutment.domain.request.dl.*;
import com.aiqin.bms.scmp.api.abutment.domain.request.product.ProductInfoRequest;
import com.aiqin.bms.scmp.api.abutment.domain.request.product.ProductInspectionRequest;
import com.aiqin.ground.util.protocol.http.HttpResponse;

public interface DlAbutmentService {

    HttpResponse orderInfo(OrderInfoRequest request);

    HttpResponse returnInfo(ReturnOrderInfoRequest request);

    HttpResponse orderTransport(OrderTransportRequest request);

    HttpResponse echoOrderInfo(EchoOrderRequest request);

    HttpResponse stockChange(StockChangeRequest request);

    HttpResponse storeInfo(DlStoreInfo request);

    HttpResponse orderCancel(OrderCancelRequest request);

    HttpResponse supplierInfo(SupplierInfoRequest request);

    HttpResponse productInspection(ProductInspectionRequest request);

    HttpResponse productInfo(ProductInfoRequest request);
}
