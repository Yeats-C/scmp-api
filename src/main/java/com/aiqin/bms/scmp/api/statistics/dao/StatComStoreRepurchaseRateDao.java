package com.aiqin.bms.scmp.api.statistics.dao;

import com.aiqin.bms.scmp.api.statistics.domain.StatComStoreRepurchaseRate;
import com.aiqin.bms.scmp.api.statistics.domain.response.StoreRepurchaseRateResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.StoreRepurchaseRateSubtotalResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatComStoreRepurchaseRateDao {

    List<StatComStoreRepurchaseRate> storeRepurchaseList(@Param("year") Long year,
                                                         @Param("month") Long month,
                                                         @Param("productSortCode") String productSortCode);

    List<StatComStoreRepurchaseRate> storeRepurchaseBySort(@Param("year") Long year,
                                       @Param("month") Long month);

    StoreRepurchaseRateResponse storeRepurchaseSum(@Param("year") Long year,
                                                   @Param("month") Long month);

    StoreRepurchaseRateSubtotalResponse storeRepurchaseByDeptSum(@Param("year") Long year,
                                                                 @Param("month") Long month,
                                                                 @Param("productSortCode") String productSortCode);

}