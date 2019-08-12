package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProfitLoss;
import com.aiqin.bms.scmp.api.product.domain.request.profitloss.QueryProfitLossVo;
import com.aiqin.bms.scmp.api.product.domain.response.profitloss.QueryProfitLossRespVo;

import java.util.List;

public interface ProfitLossMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProfitLoss record);

    int insertSelective(ProfitLoss record);

    ProfitLoss selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProfitLoss record);

    int updateByPrimaryKey(ProfitLoss record);

    List<QueryProfitLossRespVo> getList(QueryProfitLossVo vo);

    void insertList(List<ProfitLoss> profitLossList);
}