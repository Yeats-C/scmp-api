package com.aiqin.bms.scmp.api.statistics.service;

import com.aiqin.bms.scmp.api.statistics.domain.request.InventoryStatisticsRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.inventory.InventoryStatisticsResponse;
import com.aiqin.ground.util.protocol.http.HttpResponse;

public interface InventoryStatisticsService {

    HttpResponse<InventoryStatisticsResponse> lowInventory(InventoryStatisticsRequest request);
}
