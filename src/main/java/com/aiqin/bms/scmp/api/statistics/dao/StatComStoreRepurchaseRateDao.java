package com.aiqin.bms.scmp.api.statistics.dao;

import com.aiqin.bms.scmp.api.statistics.domain.response.StoreRepurchaseRateResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatComStoreRepurchaseRateDao {

    List<StoreRepurchaseRateResponse> storeRepurchaseList(@Param("year") String year,
                                                          @Param("month") String month);

}