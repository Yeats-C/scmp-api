package com.aiqin.bms.scmp.api.purchase.dao.bi;

import com.aiqin.bms.scmp.api.purchase.domain.bi.BiStockoutRate;
import org.apache.ibatis.annotations.Param;

public interface BiStockoutRateDao {

    BiStockoutRate stockOutRateInfo(@Param("beginTime") String beginTime, @Param("finishTime")String finishTime);
}