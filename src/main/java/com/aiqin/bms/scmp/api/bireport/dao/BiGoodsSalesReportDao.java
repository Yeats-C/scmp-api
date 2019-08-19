package com.aiqin.bms.scmp.api.bireport.dao;

import com.aiqin.bms.scmp.api.bireport.domain.BiGoodsSalesReport;
import com.aiqin.bms.scmp.api.bireport.domain.request.ProductAndStockRequest;

import java.util.List;

public interface BiGoodsSalesReportDao {

    List<BiGoodsSalesReport> goodsSalesList(ProductAndStockRequest productAndStock);

    Integer goodsSalesCount(ProductAndStockRequest productAndStock);

}