package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.StockMonthBatch;

public interface StockMonthBatchDao {

    Integer delete(Long id);

    Integer insert(StockMonthBatch record);

    StockMonthBatch stockMonthBatchOne(Long id);

    Integer update(StockMonthBatch record);

}