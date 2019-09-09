package com.aiqin.bms.scmp.api.statistics.dao;

import com.aiqin.bms.scmp.api.statistics.domain.response.SupplierDeliveryRateResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.SupplierDeliveryResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatSupplierArrivalRateMonthlyDao {

    List<SupplierDeliveryResponse> supplyArrivalMonthByGroup(String year, String month);

    List<SupplierDeliveryRateResponse> supplyArrivalMonthList(@Param("year") String year,
                                                              @Param("month") String month,
                                                              @Param("supplierCode") String supplierCode,
                                                              @Param("responsiblePersonCode") String responsiblePersonCode);


}