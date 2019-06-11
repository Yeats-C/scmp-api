package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;

public interface EncodingRuleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EncodingRule record);

    int insertSelective(EncodingRule record);

    EncodingRule selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EncodingRule record);

    int updateByPrimaryKey(EncodingRule record);
}