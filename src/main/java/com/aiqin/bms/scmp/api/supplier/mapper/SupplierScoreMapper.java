package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierScore;
import com.aiqin.bms.scmp.api.supplier.domain.request.score.QueryScoreReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.score.ScoreListRespVo;

import java.util.List;

public interface SupplierScoreMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierScore record);

    int insertSelective(SupplierScore record);

    SupplierScore selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierScore record);

    int updateByPrimaryKey(SupplierScore record);

    List<ScoreListRespVo> getList(QueryScoreReqVo reqVo);

    SupplierScore selectByCode(String code);
}