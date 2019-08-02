package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseInspectionReport;
import org.apache.ibatis.annotations.Param;

public interface PurchaseInspectionReportDao {

    Integer delete(Long id);

    Integer insert(PurchaseInspectionReport record);

    Integer update(PurchaseInspectionReport record);

    PurchaseInspectionReport inspectionReportInfo(@Param("purchaseOrderId") String purchaseOrderId,
                                                  @Param("skuCode") String skuCode,
                                                  @Param("productionDate") String productionDate);
}