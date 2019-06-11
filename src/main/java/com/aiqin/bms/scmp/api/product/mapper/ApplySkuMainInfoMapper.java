package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplySkuMainInfo;

public interface ApplySkuMainInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplySkuMainInfo record);

    int insertSelective(ApplySkuMainInfo record);

    ApplySkuMainInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplySkuMainInfo record);

    int updateByPrimaryKey(ApplySkuMainInfo record);
}