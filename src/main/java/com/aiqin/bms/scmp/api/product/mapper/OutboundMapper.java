package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.Outbound;


public interface OutboundMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Outbound record);

    int insertSelective(Outbound record);


    Outbound selectByPrimaryKey(Long id);


    int updateByPrimaryKeySelective(Outbound record);

    int updateByPrimaryKey(Outbound record);
}