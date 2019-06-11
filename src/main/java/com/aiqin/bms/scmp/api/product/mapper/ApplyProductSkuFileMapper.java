package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuFile;

public interface ApplyProductSkuFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuFile record);

    int insertSelective(ApplyProductSkuFile record);

    ApplyProductSkuFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuFile record);

    int updateByPrimaryKey(ApplyProductSkuFile record);
}