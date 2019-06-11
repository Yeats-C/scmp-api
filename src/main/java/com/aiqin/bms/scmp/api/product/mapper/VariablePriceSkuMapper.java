package com.aiqin.bms.scmp.api.product.mapper;


import com.aiqin.bms.scmp.api.product.domain.pojo.VariablePriceSku;
import com.aiqin.bms.scmp.api.product.domain.request.variableprice.PriceRevokeReqVo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface VariablePriceSkuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VariablePriceSku record);

    int insertSelective(VariablePriceSku record);

    VariablePriceSku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VariablePriceSku record);

    int updateByPrimaryKey(VariablePriceSku record);

    int insertList(@Param("list") Collection<VariablePriceSku> list);

    List<VariablePriceSku> getList(@Param("variablePriceCode") String variablePriceCode);

    int updateAndCode(PriceRevokeReqVo priceRevokeReqVo);



}