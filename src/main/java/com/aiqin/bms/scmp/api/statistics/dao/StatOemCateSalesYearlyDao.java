package com.aiqin.bms.scmp.api.statistics.dao;

import com.aiqin.bms.scmp.api.statistics.domain.request.OemSaleRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.oem.OemSaleInfoResponse;

import java.util.List;

public interface StatOemCateSalesYearlyDao {

    OemSaleInfoResponse oemSaleCateSum(OemSaleRequest request);

    List<OemSaleInfoResponse> oemSaleCategory(OemSaleRequest request);

    List<OemSaleInfoResponse> oemSaleCategory2(OemSaleRequest request);

    OemSaleInfoResponse companySaleCateSum(OemSaleRequest request);
}