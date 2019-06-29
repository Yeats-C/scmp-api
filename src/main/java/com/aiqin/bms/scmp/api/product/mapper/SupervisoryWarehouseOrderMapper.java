package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.SupervisoryWarehouseOrder;

public interface SupervisoryWarehouseOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupervisoryWarehouseOrder record);

    int insertSelective(SupervisoryWarehouseOrder record);

    SupervisoryWarehouseOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupervisoryWarehouseOrder record);

    int updateByPrimaryKey(SupervisoryWarehouseOrder record);
}