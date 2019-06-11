package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.VariablePriceSku;

import java.util.List;

public interface VariablePriceSkuService {
    int deleteByPrimaryKey(Long id);

    int insert(VariablePriceSku record);

    int insertSelective(VariablePriceSku record);

    VariablePriceSku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VariablePriceSku record);

    int updateByPrimaryKey(VariablePriceSku record);


    int insertList(List<VariablePriceSku> variablePrices);

    List<VariablePriceSku>getList(String variablePriceCode);







}
