package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.StockBatch;
import com.aiqin.bms.scmp.api.product.domain.request.QueryStockBatchSkuReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchResponse;

import java.util.List;

public interface StockBatchDao {

    Integer insert(StockBatch record);

    Integer update(StockBatch record);

    List<StockBatchResponse> stockBatchList(QueryStockBatchSkuReqVo request);

    Integer stockBatchListCount(QueryStockBatchSkuReqVo request);

    StockBatchResponse stockBatchDetail(String stockBatchCode);
}