package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.StockMonthBatchFlow;

public interface StockMonthBatchFlowDao {

    Integer delete(Long id);

    Integer insert(StockMonthBatchFlow record);

    StockMonthBatchFlow stockMonthBatchFlowOne(Long id);

    Integer update(StockMonthBatchFlow record);

}