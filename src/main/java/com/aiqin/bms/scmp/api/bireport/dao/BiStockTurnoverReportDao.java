package com.aiqin.bms.scmp.api.bireport.dao;

import com.aiqin.bms.scmp.api.bireport.domain.request.ProductAndStockRequest;
import com.aiqin.bms.scmp.api.bireport.domain.response.ProductAndStockResponse;
import com.aiqin.bms.scmp.api.bireport.domain.response.StockTurnoverResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BiStockTurnoverReportDao {

    List<ProductAndStockResponse> stockTurnoverList(ProductAndStockRequest productAndStock);

    Integer stockTurnoverCount(ProductAndStockRequest productAndStock);

    List<StockTurnoverResponse> getStockTurnoverInfo(@Param("statDate") String statDate, @Param("skuCode") String skuCode);

}