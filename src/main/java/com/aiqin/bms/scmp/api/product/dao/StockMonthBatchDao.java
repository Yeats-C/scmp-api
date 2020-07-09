package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.StockMonthBatch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockMonthBatchDao {

    Integer delete(StockMonthBatch record);

    Integer insertAll(@Param("list") List<StockMonthBatch> record);

    StockMonthBatch stockMonthBatchOne(StockMonthBatch batch);

    Integer update(StockMonthBatch record);

}