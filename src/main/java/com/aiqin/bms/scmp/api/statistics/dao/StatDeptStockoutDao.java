package com.aiqin.bms.scmp.api.statistics.dao;

import com.aiqin.bms.scmp.api.statistics.domain.StatDeptStockout;
import com.aiqin.bms.scmp.api.statistics.domain.request.ProductRequest;

import java.util.List;

public interface StatDeptStockoutDao {

    List<StatDeptStockout> stockOutSum(ProductRequest request);

    List<StatDeptStockout> purchaseGroupList(ProductRequest request);

    List<StatDeptStockout> deptList(ProductRequest request);
}