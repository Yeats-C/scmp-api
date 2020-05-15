package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockBatchFlow;
import com.aiqin.bms.scmp.api.product.domain.request.QueryStockBatchSkuReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchDetailResponse;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchResponse;
import com.aiqin.ground.util.protocol.http.HttpResponse;

/**
 * @author: zhao shuai
 * @create: 2020-03-26
 **/
public interface StockBatchService {

    HttpResponse<PageResData<StockBatchResponse>> stockBatchList(QueryStockBatchSkuReqVo request);

    HttpResponse<StockBatchDetailResponse> stockBatchDetail(String stockBatchCode);

    HttpResponse<PageResData<StockBatchFlow>> stockBatchFlow(QueryStockBatchSkuReqVo request);
}
