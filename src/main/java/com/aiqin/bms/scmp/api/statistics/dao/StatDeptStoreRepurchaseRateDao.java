package com.aiqin.bms.scmp.api.statistics.dao;

import com.aiqin.bms.scmp.api.statistics.domain.StatComStoreRepurchaseRate;
import com.aiqin.bms.scmp.api.statistics.domain.response.StoreRepurchaseRateSubtotalResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatDeptStoreRepurchaseRateDao {

    List<StatComStoreRepurchaseRate> storeRepurchaseList(@Param("year") Long year,
                                                         @Param("month") Long month,
                                                         @Param("productSortCode") String productSortCode);

    StoreRepurchaseRateSubtotalResponse storeRepurchaseSum(@Param("year") Long year,
                                                           @Param("month") Long month,
                                                           @Param("productSortCode") String productSortCode);

}