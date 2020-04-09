package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.request.QueryStockBatchSkuReqVo;
import com.aiqin.ground.util.protocol.http.HttpResponse;

/**
 * @author: zhao shuai
 * @create: 2020-03-26
 **/
public interface StockBatchService {

    HttpResponse stockBatchList(QueryStockBatchSkuReqVo request);

    HttpResponse stockBatchDetail(String stockBatchCode);

    HttpResponse stockBatchFlow(QueryStockBatchSkuReqVo request);
}
