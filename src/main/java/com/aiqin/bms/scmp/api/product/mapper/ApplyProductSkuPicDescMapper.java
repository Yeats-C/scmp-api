package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPicDesc;

public interface ApplyProductSkuPicDescMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuPicDesc record);

    int insertSelective(ApplyProductSkuPicDesc record);

    ApplyProductSkuPicDesc selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuPicDesc record);

    int updateByPrimaryKey(ApplyProductSkuPicDesc record);
}