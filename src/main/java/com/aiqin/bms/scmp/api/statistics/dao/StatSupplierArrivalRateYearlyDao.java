package com.aiqin.bms.scmp.api.statistics.dao;

import com.aiqin.bms.scmp.api.statistics.domain.request.SupplierRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.supplier.StatSupplierArrivalRateResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.supplier.SupplierResponse;

import java.util.List;

public interface StatSupplierArrivalRateYearlyDao {

    List<StatSupplierArrivalRateResponse> supplierArrivalSum(SupplierRequest request);

    List<SupplierResponse>  supplierList(SupplierRequest request);

    List<SupplierResponse>  categoryList(SupplierRequest request);

}