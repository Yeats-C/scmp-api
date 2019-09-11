package com.aiqin.bms.scmp.api.statistics.dao;

import com.aiqin.bms.scmp.api.statistics.domain.request.SupplierRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.supplier.StatSupplierReturnRateResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.supplier.SupplierResponse;

import java.util.List;

public interface StatSupplierReturnRateYearlyDao {

    List<StatSupplierReturnRateResponse> supplierReturnSum(SupplierRequest request);

    List<SupplierResponse> supplierList(SupplierRequest request);
}