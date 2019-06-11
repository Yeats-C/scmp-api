package com.aiqin.bms.scmp.api.product.mapper;


import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundProduct;

public interface OutboundProductMapper {

    int deleteByPrimaryKey(Long id);

    int insert(OutboundProduct record);

    int insertSelective(OutboundProduct record);

    OutboundProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OutboundProduct record);

    int updateByPrimaryKey(OutboundProduct record);
}