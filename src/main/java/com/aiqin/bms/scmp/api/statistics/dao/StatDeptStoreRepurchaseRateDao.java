package com.aiqin.bms.scmp.api.statistics.dao;

import com.aiqin.bms.scmp.api.statistics.domain.StatDeptStoreRepurchaseRate;
import com.aiqin.bms.scmp.api.statistics.domain.response.StoreRepurchaseRateResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.StoreRepurchaseRateSubtotalResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatDeptStoreRepurchaseRateDao {

    List<StatDeptStoreRepurchaseRate> storeRepurchaseList(@Param("year") Long year,
                                                          @Param("month") Long month,
                                                          @Param("productSortCode") String productSortCode);

    StoreRepurchaseRateResponse storeRepurchaseSum(@Param("year") Long year,
                                                   @Param("month") Long month);

    List<StatDeptStoreRepurchaseRate> storeRepurchaseBySort(@Param("year") Long year,
                                                           @Param("month") Long month);

    StoreRepurchaseRateSubtotalResponse storeRepurchaseByDeptSum(@Param("year") Long year,
                                                                 @Param("month") Long month,
                                                                 @Param("productSortCode") String productSortCode);

}