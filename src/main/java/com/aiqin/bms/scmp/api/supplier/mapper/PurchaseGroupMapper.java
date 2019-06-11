package com.aiqin.bms.scmp.api.supplier.mapper;


import com.aiqin.bms.scmp.api.supplier.domain.pojo.PurchaseGroup;

public interface PurchaseGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PurchaseGroup record);

    int insertSelective(PurchaseGroup record);

    PurchaseGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PurchaseGroup record);

    int updateByPrimaryKey(PurchaseGroup record);
}