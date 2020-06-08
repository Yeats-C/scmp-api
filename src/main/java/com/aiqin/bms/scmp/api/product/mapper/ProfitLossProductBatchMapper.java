package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProfitLoss;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProfitLossProductBatch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProfitLossProductBatchMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProfitLossProductBatch record);

    int insertSelective(ProfitLossProductBatch record);

    ProfitLossProductBatch selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProfitLossProductBatch record);

    int updateByPrimaryKey(ProfitLossProductBatch record);

    List<ProfitLossProductBatch> getListByOrderCode(String orderCode);

    void insertList(@Param(value = "list") List<ProfitLossProductBatch> batchList);

    void insertBatchList(@Param(value = "list") List<ProfitLossProductBatch> batchList);

    List<ProfitLossProductBatch> listForSap(SapOrderRequest sapOrderRequest);

    List<ProfitLossProductBatch> getBatchListByOrderCode(@Param("skuCode") String skuCode,
                                                         @Param("orderCode") String orderCode,
                                                         @Param("lineCode") Long lineCode);
}