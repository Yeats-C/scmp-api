package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.StockDayBatch;

public interface StockDayBatchDao {

    Integer delete(Long id);

    Integer insert(StockDayBatch record);

    StockDayBatch stockDayBatchOne(Long id);

    Integer update(StockDayBatch record);

}