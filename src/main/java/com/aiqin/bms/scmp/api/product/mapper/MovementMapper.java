package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.Movement;

public interface MovementMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Movement record);

    int insertSelective(Movement record);

    Movement selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Movement record);

    int updateByPrimaryKey(Movement record);
}