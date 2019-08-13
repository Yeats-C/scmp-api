package com.aiqin.bms.scmp.api.bireport.service.impl;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.bireport.dao.BiGoodsSalesReportDao;
import com.aiqin.bms.scmp.api.bireport.dao.BiStockTurnoverReportDao;
import com.aiqin.bms.scmp.api.bireport.dao.BiStockWayTurnoverReportDao;
import com.aiqin.bms.scmp.api.bireport.domain.BiGoodsSalesReport;
import com.aiqin.bms.scmp.api.bireport.domain.BiStockTurnoverReport;
import com.aiqin.bms.scmp.api.bireport.domain.BiStockWayTurnoverReport;
import com.aiqin.bms.scmp.api.bireport.domain.request.ProductAndStockRequest;
import com.aiqin.bms.scmp.api.bireport.domain.response.ProductAndStockResponse;
import com.aiqin.bms.scmp.api.bireport.domain.response.StockTurnoverResponse;
import com.aiqin.bms.scmp.api.bireport.service.ReportFormService;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-08-12
 **/
@Service
public class ReportFormServiceImpl implements ReportFormService {

    @Resource
    private BiGoodsSalesReportDao biGoodsSalesReportDao;
    @Resource
    private BiStockTurnoverReportDao biStockTurnoverReportDao;
    @Resource
    private BiStockWayTurnoverReportDao biStockWayTurnoverReportDao;

    @Override
    public HttpResponse<BiGoodsSalesReport> productSaleInfo(ProductAndStockRequest productAndStock){
        PageResData pageResData = new PageResData();
        List<BiGoodsSalesReport> list = biGoodsSalesReportDao.goodsSalesList(productAndStock);
        Integer count = biGoodsSalesReportDao.goodsSalesCount(productAndStock);
        pageResData.setTotalCount(count);
        pageResData.setDataList(list);
        return HttpResponse.success(pageResData);
    }

    @Override
    public HttpResponse<ProductAndStockResponse> stockTurnover(ProductAndStockRequest productAndStock){
        PageResData pageResData = new PageResData();
        List<ProductAndStockResponse> list = biStockTurnoverReportDao.stockTurnoverList(productAndStock);
        this.transportCenter(list, 1);
        pageResData.setTotalCount(list.size());
        pageResData.setDataList(list);
        return HttpResponse.success(pageResData);
    }

    @Override
    public HttpResponse<ProductAndStockResponse> stockWayTurnover(ProductAndStockRequest productAndStock){
        PageResData pageResData = new PageResData();
        List<ProductAndStockResponse> list = biStockWayTurnoverReportDao.stockWayTurnoverList(productAndStock);
        this.transportCenter(list, 2);
        pageResData.setTotalCount(list.size());
        pageResData.setDataList(list);
        return HttpResponse.success(pageResData);
    }

    private void transportCenter(List<ProductAndStockResponse> list, int i){
        if(CollectionUtils.isNotEmptyCollection(list)){
            Long num = 0L;
            for(ProductAndStockResponse response:list){
                // 查询对应仓库、sku的数据
                List<StockTurnoverResponse> stockList;
                if(i == 1){
                    stockList = biStockTurnoverReportDao.getStockTurnoverInfo(response.getStatDate(), response.getSkuCode());
                }else {
                    stockList = biStockWayTurnoverReportDao.getStockWayTurnoverInfo(response.getStatDate(), response.getSkuCode());
                }
                if(CollectionUtils.isNotEmptyCollection(stockList)){
                    Long sumStockNum = 0L, sumSalesNum = 0L, sumTurnoverDays = 0L;
                    for (StockTurnoverResponse stock: stockList){
                        Long stockNum = stock.getStockNum() == null ? 0L : stock.getStockNum();
                        Long salesNum = stock.getSalesNum() == null ? 0L : stock.getSalesNum();
                        Long turnoverDays = stock.getTurnoverDays() == null ? 0L : stock.getTurnoverDays();
                        Long onWay = 0L;
                        if(i == 2){
                            onWay = stock.getOnWay() == null ? 0L : stock.getOnWay();
                        }
                        sumStockNum += stockNum;
                        sumSalesNum += salesNum;
                        sumTurnoverDays += turnoverDays;
                        // 华北仓
                        if(stock.getTransportCenterCode().equals(Global.HB_CODE)){
                            response.setHbTransportCenterCode(stock.getTransportCenterCode());
                            response.setHbTransportCenterName(stock.getTransportCenterName());
                            response.setHbStockNum(stockNum);
                            response.setHbSalesNum(salesNum);
                            response.setHbTurnoverDays(turnoverDays);
                            if(i == 2){
                                response.setHbOnWay(onWay);
                            }
                        }else {
                            response.setHbStockNum(num);
                            response.setHbSalesNum(num);
                            response.setHbTurnoverDays(num);
                            response.setHbOnWay(num);
                        }
                        // 华南仓
                        if(stock.getTransportCenterCode().equals(Global.HN_CODE)){
                            response.setHnTransportCenterCode(stock.getTransportCenterCode());
                            response.setHnTransportCenterName(stock.getTransportCenterName());
                            response.setHnStockNum(stockNum);
                            response.setHnSalesNum(salesNum);
                            response.setHnTurnoverDays(turnoverDays);
                            if(i == 2){
                                response.setHnOnWay(onWay);
                            }
                        }else {
                            response.setHnStockNum(num);
                            response.setHnSalesNum(num);
                            response.setHnTurnoverDays(num);
                            response.setHnOnWay(num);
                        }
                        // 西南仓
                        if(stock.getTransportCenterCode().equals(Global.XN_CODE)){
                            response.setXnTransportCenterCode(stock.getTransportCenterCode());
                            response.setXnTransportCenterName(stock.getTransportCenterName());
                            response.setXnStockNum(stockNum);
                            response.setXnSalesNum(salesNum);
                            response.setXnTurnoverDays(turnoverDays);
                            if(i == 2){
                                response.setXnOnWay(onWay);
                            }
                        }else {
                            response.setXnStockNum(num);
                            response.setXnSalesNum(num);
                            response.setXnTurnoverDays(num);
                            response.setXnOnWay(num);
                        }
                        // 华东仓
                        if(stock.getTransportCenterCode().equals(Global.HD_CODE)){
                            response.setHdTransportCenterCode(stock.getTransportCenterCode());
                            response.setHdTransportCenterName(stock.getTransportCenterName());
                            response.setHdStockNum(stockNum);
                            response.setHdSalesNum(salesNum);
                            response.setHdTurnoverDays(turnoverDays);
                            if(i == 2){
                                response.setHdOnWay(onWay);
                            }
                        }else {
                            response.setHdStockNum(num);
                            response.setHdSalesNum(num);
                            response.setHdTurnoverDays(num);
                            response.setHdOnWay(num);
                        }
                    }
                    response.setSumStockNum(sumStockNum);
                    response.setSumSalesNum(sumSalesNum);
                    response.setSumTurnoverDays(sumTurnoverDays);
                }
            }
        }
    }
}
