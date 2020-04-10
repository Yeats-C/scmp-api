package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockFlow;
import com.aiqin.bms.scmp.api.product.domain.request.StockFlowRequest;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface StockFlowDao {

    Stock selectOneStockInfoByStockFlow(StockFlowRequest reqVo);

    Integer insertOne(StockFlow stockFlow);

    Integer insertAll(@Param("list") List<StockFlow> stockFlows);

    List<StockFlow> selectFlowByStockSku(@Param("yesterday") String yesterday,@Param("stockCode") String stockCode);

    /** 回显出库库存成本*/
    Integer updateStockCost(@Param("stockCost") BigDecimal stockCost, @Param("documentNum")String documentNum, @Param("skuCode")String skuCode);

}