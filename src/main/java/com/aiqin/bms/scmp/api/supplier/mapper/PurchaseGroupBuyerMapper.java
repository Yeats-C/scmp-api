package com.aiqin.bms.scmp.api.supplier.mapper;


import com.aiqin.bms.scmp.api.supplier.domain.pojo.PurchaseGroupBuyer;

public interface PurchaseGroupBuyerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PurchaseGroupBuyer record);

    int insertSelective(PurchaseGroupBuyer record);

    PurchaseGroupBuyer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PurchaseGroupBuyer record);

    int updateByPrimaryKey(PurchaseGroupBuyer record);
}