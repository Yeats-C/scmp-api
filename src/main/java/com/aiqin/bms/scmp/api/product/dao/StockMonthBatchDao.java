package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.StockMonthBatch;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QuerySkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QuerySkuInfoRespVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockMonthBatchDao {

    Integer delete(StockMonthBatch record);

    Integer insertAll(@Param("list") List<StockMonthBatch> record);

    StockMonthBatch stockMonthBatchOne(StockMonthBatch batch);

    Integer update(StockMonthBatch record);

    List<QuerySkuInfoRespVO> querySkuBatchMonthList(QuerySkuInfoReqVO reqVO);

    List<StockMonthBatch> getMonthBatch(@Param("skuCode") String skuCode);

    Integer querySkuBatchMonthCount(QuerySkuInfoReqVO reqVO);

}