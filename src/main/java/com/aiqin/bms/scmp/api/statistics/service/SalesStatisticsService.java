package com.aiqin.bms.scmp.api.statistics.service;

import com.aiqin.ground.util.protocol.http.HttpResponse;

public interface SalesStatisticsService {

    HttpResponse saleInfo(String date, Integer type, Integer reportType, Integer storeTypeCode, Integer productPropertyCode);
}
