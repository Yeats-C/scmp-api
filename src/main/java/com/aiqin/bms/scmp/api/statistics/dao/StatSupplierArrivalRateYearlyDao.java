package com.aiqin.bms.scmp.api.statistics.dao;

import com.aiqin.bms.scmp.api.statistics.domain.response.SupplierDeliveryRateResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.SupplierDeliveryResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatSupplierArrivalRateYearlyDao {

    List<SupplierDeliveryResponse> supplyArrivalYearByGroup(String date);

    List<SupplierDeliveryRateResponse> supplyArrivalYearList(@Param("date") String date,
                                                             @Param("supplierCode") String supplierCode,
                                                             @Param("responsiblePersonCode") String responsiblePersonCode);

}