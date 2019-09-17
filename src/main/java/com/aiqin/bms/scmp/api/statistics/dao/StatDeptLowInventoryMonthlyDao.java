package com.aiqin.bms.scmp.api.statistics.dao;

import com.aiqin.bms.scmp.api.statistics.domain.request.InventoryStatisticsRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.inventory.InventorySortResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.inventory.inventoryInfoResponse;

import java.util.List;

public interface StatDeptLowInventoryMonthlyDao {

    List<inventoryInfoResponse> inventorySum(InventoryStatisticsRequest request);

    List<InventorySortResponse> deptList(InventoryStatisticsRequest request);

    List<InventorySortResponse> groupList(InventoryStatisticsRequest request);
}
