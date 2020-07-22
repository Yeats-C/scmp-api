package com.aiqin.bms.scmp.api.abutment.web;

import com.aiqin.bms.scmp.api.abutment.domain.request.dl.OrderInfoRequest;
import com.aiqin.bms.scmp.api.abutment.domain.request.dl.ReturnOrderInfoRequest;
import com.aiqin.bms.scmp.api.abutment.domain.request.dl.SupplierAbutmentRequest;
import com.aiqin.bms.scmp.api.abutment.domain.request.dl.SupplierInfoRequest;
import com.aiqin.bms.scmp.api.product.domain.request.ReturnReq;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.ErpOrderInfo;

public interface ParameterAssemblyService {

    ErpOrderInfo orderInfoParameter(OrderInfoRequest request);

    ReturnReq returnInfoParameter(ReturnOrderInfoRequest request);

    SupplierInfoRequest supplierParameter(SupplierAbutmentRequest request);

}
