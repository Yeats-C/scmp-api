package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.MovementProduct;

public interface MovementProductMapper {

    int deleteByPrimaryKey(Long id);

    int insert(MovementProduct record);

    int insertSelective(MovementProduct record);

    MovementProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MovementProduct record);

    int updateByPrimaryKey(MovementProduct record);


}