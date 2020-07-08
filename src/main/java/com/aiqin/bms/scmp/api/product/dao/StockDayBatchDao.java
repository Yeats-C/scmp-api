package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.StockDayBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockMonthBatch;

import java.util.List;

public interface StockDayBatchDao {

    Integer delete(Long id);

    Integer insert(StockDayBatch record);

    StockDayBatch stockDayBatchOne(StockDayBatch record);

    Integer update(StockDayBatch record);

    List<StockDayBatch> stockDayBatchList(StockMonthBatch record);

}