package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuChannel;

public interface ApplyProductSkuChannelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuChannel record);

    int insertSelective(ApplyProductSkuChannel record);

    ApplyProductSkuChannel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuChannel record);

    int updateByPrimaryKey(ApplyProductSkuChannel record);
}