package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.BiStockoutRate;
import org.apache.ibatis.annotations.Param;

public interface BiStockoutRateDao {

    BiStockoutRate stockOutRateInfo(@Param("beginTime") String beginTime, @Param("finishTime")String finishTime);
}