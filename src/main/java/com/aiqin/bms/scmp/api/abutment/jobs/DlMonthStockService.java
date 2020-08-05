package com.aiqin.bms.scmp.api.abutment.jobs;

import com.aiqin.bms.scmp.api.abutment.domain.request.dl.MonthStockRequest;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;

public interface DlMonthStockService {

    HttpResponse<List<MonthStockRequest>> monthStockDl(String warehouseCode);
}
