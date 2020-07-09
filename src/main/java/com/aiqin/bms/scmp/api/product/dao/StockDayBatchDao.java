package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.StockDayBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockMonthBatch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockDayBatchDao {

    Integer delete(Long synchrTime);

    Integer insertAll(@Param("list") List<StockMonthBatch> record);

    StockDayBatch stockDayBatchOne(StockDayBatch record);

    Integer update(StockDayBatch record);

    List<StockDayBatch> stockDayBatchList(StockMonthBatch record);

    List<StockMonthBatch> dayBatchByGroup(Long synchrTime);

}