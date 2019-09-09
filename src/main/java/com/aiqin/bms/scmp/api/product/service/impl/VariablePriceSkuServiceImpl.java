package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.HandlingExceptionCode;
import com.aiqin.bms.scmp.api.common.Save;
import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.common.Update;
import com.aiqin.bms.scmp.api.product.domain.pojo.VariablePriceSku;
import com.aiqin.bms.scmp.api.product.mapper.VariablePriceSkuMapper;
import com.aiqin.bms.scmp.api.product.service.VariablePriceSkuService;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class VariablePriceSkuServiceImpl implements VariablePriceSkuService {
    @Autowired
    private VariablePriceSkuMapper variablePriceSkuMapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        return variablePriceSkuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(VariablePriceSku record) {
        return variablePriceSkuMapper.insert(record);
    }

    @Override
    @Transactional
    @Save
    public int insertSelective(VariablePriceSku record) {
        int k=variablePriceSkuMapper.insertSelective(record);
        if(k>0){
            return k;
        }else {
            log.error(HandlingExceptionCode.VARIABLE_PRICE);
            throw new GroundRuntimeException(HandlingExceptionCode.VARIABLE_PRICE);
        }
    }

    @Override
    public VariablePriceSku selectByPrimaryKey(Long id) {
        return variablePriceSkuMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    @Update
    public int updateByPrimaryKeySelective(VariablePriceSku record) {
        int k=variablePriceSkuMapper.updateByPrimaryKeySelective(record);
        if(k>0){
            return k;
        }else {
            log.error(HandlingExceptionCode.UPDATE_VARIABLE_PRICE);
            throw new GroundRuntimeException(HandlingExceptionCode.UPDATE_VARIABLE_PRICE);
        }
    }

    @Override
    public int updateByPrimaryKey(VariablePriceSku record) {
        return variablePriceSkuMapper.updateByPrimaryKey(record);
    }

    @Override
    @Transactional
    @SaveList
    public int insertList(List<VariablePriceSku> variablePrices) {
        int k=variablePriceSkuMapper.insertList(variablePrices);
        if(k>0){
            return k;
        }else {
            log.error(HandlingExceptionCode.VARIABLE_PRICE);
            throw new GroundRuntimeException(HandlingExceptionCode.VARIABLE_PRICE);
        }
    }

    @Override
    public List<VariablePriceSku> getList(String variablePriceCode) {
        return variablePriceSkuMapper.getList(variablePriceCode);
    }
}
