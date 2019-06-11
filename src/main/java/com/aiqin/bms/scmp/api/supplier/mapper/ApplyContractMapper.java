package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyContract;

public interface ApplyContractMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyContract record);

    int insertSelective(ApplyContract record);

    ApplyContract selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyContract record);

    int updateByPrimaryKey(ApplyContract record);
}