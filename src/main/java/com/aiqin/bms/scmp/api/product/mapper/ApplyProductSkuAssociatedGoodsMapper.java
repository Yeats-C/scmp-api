package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuAssociatedGoods;

public interface ApplyProductSkuAssociatedGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuAssociatedGoods record);

    int insertSelective(ApplyProductSkuAssociatedGoods record);

    ApplyProductSkuAssociatedGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuAssociatedGoods record);

    int updateByPrimaryKey(ApplyProductSkuAssociatedGoods record);
}