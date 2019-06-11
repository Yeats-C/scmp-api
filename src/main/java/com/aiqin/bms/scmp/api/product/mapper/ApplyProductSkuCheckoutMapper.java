package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuCheckout;

public interface ApplyProductSkuCheckoutMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuCheckout record);

    int insertSelective(ApplyProductSkuCheckout record);

    ApplyProductSkuCheckout selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuCheckout record);

    int updateByPrimaryKey(ApplyProductSkuCheckout record);
}