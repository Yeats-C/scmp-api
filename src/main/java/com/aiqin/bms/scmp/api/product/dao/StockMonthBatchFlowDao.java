package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.StockMonthBatchFlow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockMonthBatchFlowDao {

    Integer delete(Long id);

    Integer insertAll(@Param("list") List<StockMonthBatchFlow> record);

    StockMonthBatchFlow stockMonthBatchFlowOne(Long id);

    Integer update(StockMonthBatchFlow record);

}