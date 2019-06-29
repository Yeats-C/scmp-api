package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProfitLossProductBatch;

import java.util.List;

public interface ProfitLossProductBatchMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProfitLossProductBatch record);

    int insertSelective(ProfitLossProductBatch record);

    ProfitLossProductBatch selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProfitLossProductBatch record);

    int updateByPrimaryKey(ProfitLossProductBatch record);

    List<ProfitLossProductBatch> getListByOrderCode(String orderCode);
}