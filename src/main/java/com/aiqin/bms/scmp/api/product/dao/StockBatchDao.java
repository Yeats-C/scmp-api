package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.StockBatch;
import com.aiqin.bms.scmp.api.product.domain.request.QueryStockBatchSkuReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.stock.StockBatchInfoRequest;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.SkuBatchRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockBatchDao {

    Integer insert(StockBatch record);

    Integer update(StockBatch record);

    List<StockBatchResponse> stockBatchList(QueryStockBatchSkuReqVo request);

    Integer stockBatchListCount(QueryStockBatchSkuReqVo request);

    StockBatchResponse stockBatchDetail(String stockBatchCode);

    List<StockBatch> stockBatchAndSku(@Param("list") List<StockBatchInfoRequest> stockBatchList);

    Integer updateBatchAll(@Param("list") List<StockBatch> stockList);

    Integer insertAll(@Param("list") List<StockBatch> stockList);

    List<SkuBatchRespVO> selectStockBatch(@Param("skuCode") String skuCode, @Param("transportCenterCode") String transportCenterCode, @Param("warehouseCode") String warehouseCode);

    List<StockBatch> stockBatchByReject(@Param("skuCode") String skuCode, @Param("warehouseCode") String warehouseCode,
                                        @Param("supplierCode") String supplierCode);

    StockBatch stockBatchInfoOne(String batchInfoCode);
}