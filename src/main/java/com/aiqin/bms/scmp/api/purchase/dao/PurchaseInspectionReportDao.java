package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseInspectionReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseInspectionReportDao {

    Integer insertAll(@Param("list") List<PurchaseInspectionReport> record);

    Integer update(PurchaseInspectionReport record);

    List<PurchaseInspectionReport> inspectionReportInfo(@Param("purchaseOrderId") String purchaseOrderId);
}