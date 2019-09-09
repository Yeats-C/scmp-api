package com.aiqin.bms.scmp.api.statistics.dao;

import com.aiqin.bms.scmp.api.statistics.domain.response.SupplierDeliveryResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatSupplierArrivalRateYearlyDao {

    List<SupplierDeliveryResponse> supplierArrivalSum(@Param("year") Long year, @Param("productSortCode") String productSortCode);

}