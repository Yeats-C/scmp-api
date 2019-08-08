package com.aiqin.bms.scmp.api.purchase.dao.bi;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BiStockoutDetailDao {

    List<String> stockOutDetail(@Param("beginTime") String beginTime, @Param("finishTime") String finishTime);

}