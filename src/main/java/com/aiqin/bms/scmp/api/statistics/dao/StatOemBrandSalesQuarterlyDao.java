package com.aiqin.bms.scmp.api.statistics.dao;

import com.aiqin.bms.scmp.api.statistics.domain.request.OemSaleRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.oem.OemSaleInfoResponse;

import java.util.List;

public interface StatOemBrandSalesQuarterlyDao {

    OemSaleInfoResponse oemSaleBrandSum(OemSaleRequest request);

    List<OemSaleInfoResponse> oemBrandeCategory(OemSaleRequest request);

    List<OemSaleInfoResponse> oemBrandCategory2(OemSaleRequest request);

    OemSaleInfoResponse companySaleBrandSum(OemSaleRequest request);
}