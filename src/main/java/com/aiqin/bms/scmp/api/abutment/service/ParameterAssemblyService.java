package com.aiqin.bms.scmp.api.abutment.service;

import com.aiqin.bms.scmp.api.abutment.domain.DlOrderBill;
import com.aiqin.bms.scmp.api.abutment.domain.DlOtherInfo;
import com.aiqin.bms.scmp.api.abutment.domain.request.dl.*;
import com.aiqin.bms.scmp.api.product.domain.request.ReturnReq;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.ErpOrderInfo;

public interface ParameterAssemblyService {

    ErpOrderInfo orderInfoParameter(OrderInfoRequest request, DlOrderBill info);

    ReturnReq returnInfoParameter(ReturnOrderInfoRequest request, DlOrderBill info);

    SupplierInfoRequest supplierParameter(SupplierAbutmentRequest request);

    void orderTransportParameter(OrderTransportRequest request, DlOrderBill info);

    void echoOrderInfoParameter(EchoOrderRequest request, DlOrderBill info, String url);

    void stockChangeParameter(StockChangeRequest request, DlOtherInfo info);

}
