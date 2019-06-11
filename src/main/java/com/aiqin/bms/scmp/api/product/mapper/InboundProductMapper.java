package com.aiqin.bms.scmp.api.product.mapper;


import com.aiqin.bms.scmp.api.product.domain.pojo.InboundProduct;

public interface InboundProductMapper {

    int deleteByPrimaryKey(Long id);

    int insert(InboundProduct record);

    int insertSelective(InboundProduct record);

    InboundProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InboundProduct record);

    int updateByPrimaryKey(InboundProduct record);
}