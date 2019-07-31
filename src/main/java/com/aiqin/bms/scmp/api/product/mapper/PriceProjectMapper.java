package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.PriceProject;
import com.aiqin.bms.scmp.api.product.domain.request.basicprice.QueryPriceProjectReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.basicprice.QueryPriceProjectRespVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PriceProjectMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PriceProject record);

    int insertSelective(PriceProject record);

    PriceProject selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PriceProject record);

    int updateByPrimaryKey(PriceProject record);

    List<QueryPriceProjectRespVo> getList(QueryPriceProjectReqVo reqVo);

    Integer checkName(@Param("name") String name, @Param("id") Long id, @Param("companyCode") String companyCode);

    List<PriceProject> selectInfoByImport(@Param("priceTypeCode")Integer priceTypeCode, @Param("nameList")List<String> nameList);
}