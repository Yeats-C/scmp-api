package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.product.dao.StockBatchDao;
import com.aiqin.bms.scmp.api.product.dao.StockBatchFlowDao;
import com.aiqin.bms.scmp.api.product.dao.StockDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockBatchFlow;
import com.aiqin.bms.scmp.api.product.domain.request.QueryStockBatchSkuReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchDetailResponse;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchResponse;
import com.aiqin.bms.scmp.api.product.service.StockBatchService;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2020-03-26
 **/

@Service
public class StockBatchServiceImpl implements StockBatchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockBatchServiceImpl.class);

    @Resource
    private StockDao  stockDao;
    @Resource
    private StockBatchDao stockBatchDao;
    @Resource
    private StockBatchFlowDao stockBatchFlowDao;

    @Override
    public HttpResponse<PageResData<StockBatchResponse>> stockBatchList(QueryStockBatchSkuReqVo request){
        LOGGER.info("批次库存查询参数：" + request);
        PageResData pageResData = new PageResData();
        List<StockBatchResponse> list = stockBatchDao.stockBatchList(request);
        Integer count = stockBatchDao.stockBatchListCount(request);
        pageResData.setDataList(list);
        pageResData.setTotalCount(count);
        return HttpResponse.success(pageResData);
    }

    @Override
    public HttpResponse<StockBatchDetailResponse> stockBatchDetail(String stockBatchCode){
        LOGGER.info("批次库存详情参数：" + stockBatchCode);
        if(StringUtils.isBlank(stockBatchCode)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        StockBatchResponse stockBatch = stockBatchDao.stockBatchDetail(stockBatchCode);
        StockBatchDetailResponse detailResponse = new StockBatchDetailResponse();
        if(stockBatch != null){
            detailResponse = stockDao.stockInfoByBatchDetail(stockBatch.getSkuCode(), stockBatch.getWarehouseCode());
            // 计算含税总成本
            BigDecimal sumAmount = detailResponse.getTaxCost().multiply(BigDecimal.valueOf(detailResponse.getInventoryCount())).
                    setScale(4, BigDecimal.ROUND_HALF_UP);
            detailResponse.setTotalTaxCost(sumAmount);
            // 计算未税金额
            BigDecimal noTax = Calculate.computeNoTaxPrice(detailResponse.getTaxCost(), BigDecimal.valueOf(detailResponse.getTaxRate().longValue()));
            detailResponse.setNoTaxCost(noTax);
        }
        detailResponse.setStockBatchResponse(stockBatch);
        return HttpResponse.success(detailResponse);
    }

    @Override
    public HttpResponse<PageResData<StockBatchFlow>> stockBatchFlow(QueryStockBatchSkuReqVo request){
        LOGGER.info("批次库存流水参数：" + request);
        if(request == null || StringUtils.isBlank(request.getStockBatchCode())){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        PageResData pageResData = new PageResData();
        List<StockBatchFlow> stockBatchFlows = stockBatchFlowDao.stockBatchFlowList(request);
        Integer count = stockBatchFlowDao.stockBatchFlowCount(request);
        pageResData.setDataList(stockBatchFlows);
        pageResData.setTotalCount(count);
        return HttpResponse.success(pageResData);
    }

}
