package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.StockBatchFlow;
import com.aiqin.bms.scmp.api.product.domain.request.QueryStockBatchSkuReqVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockBatchFlowDao {

    Integer insert(StockBatchFlow record);

    Integer update(StockBatchFlow record);

    List<StockBatchFlow> stockBatchFlowList(QueryStockBatchSkuReqVo request);

    Integer stockBatchFlowCount(QueryStockBatchSkuReqVo request);

    Integer insertAll(@Param("list") List<StockBatchFlow> flows);

}