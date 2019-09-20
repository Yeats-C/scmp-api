package com.aiqin.bms.scmp.api.statistics.dao;

import com.aiqin.bms.scmp.api.statistics.domain.request.InventoryStatisticsRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.inventory.InventorySortResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.inventory.inventoryInfoResponse;

import java.util.List;

public interface StatDeptHighInventoryMonthlyDao {

    List<inventoryInfoResponse> inventoryHighSum(InventoryStatisticsRequest request);

    List<InventorySortResponse> deptHighList(InventoryStatisticsRequest request);

    List<InventorySortResponse> groupHighList(InventoryStatisticsRequest request);

}