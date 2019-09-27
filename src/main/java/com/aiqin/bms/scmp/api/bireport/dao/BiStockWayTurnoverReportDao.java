package com.aiqin.bms.scmp.api.bireport.dao;

import com.aiqin.bms.scmp.api.bireport.domain.request.ProductAndStockRequest;
import com.aiqin.bms.scmp.api.bireport.domain.response.ProductAndStockResponse;
import com.aiqin.bms.scmp.api.bireport.domain.response.StockTurnoverResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BiStockWayTurnoverReportDao {

    List<ProductAndStockResponse> stockWayTurnoverList(ProductAndStockRequest productAndStock);

    Integer stockWayTurnoverCount(ProductAndStockRequest productAndStock);

    List<StockTurnoverResponse> getStockWayTurnoverInfo(@Param("statDate") String statDate, @Param("skuCode") String skuCode);

}