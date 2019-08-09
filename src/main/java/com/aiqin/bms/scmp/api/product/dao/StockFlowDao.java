package com.aiqin.bms.scmp.api.product.dao;


import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockFlow;
import com.aiqin.bms.scmp.api.product.domain.request.StockFlowRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockFlowDao {


    Stock selectOneStockInfoByStockFlow(StockFlowRequest reqVo);

    StockFlow selectOneByLockCode(String lockCode);

    int insertOne(StockFlow stockFlow);

    StockFlow selectOneByHistoryCode(String lockCode);

    void insertBatch(@Param("stockFlows") List<StockFlow> stockFlows);

    List<StockFlow> selectFlowByStockSku(@Param("yesterday") String yesterday,@Param("stockCode") String stockCode);

    /**
     * 回显出库库存成本
     */
    Integer updateStockCost(@Param("stockCost")Long stockCost, @Param("documentNum")String documentNum, @Param("skuCode")String skuCode);

}