package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyTransportCenter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseApplyTransportCenterDao {

    int insert(PurchaseApplyTransportCenter record);

    int update(PurchaseApplyTransportCenter record);

    List<PurchaseApplyTransportCenter> selectList(@Param("purchaseApplyCode") String purchaseApplyCode,
                                                  @Param("transportCenterCode") String transportCenterCode);
}