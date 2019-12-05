package com.aiqin.bms.scmp.api.statistics.service.impl;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.statistics.dao.StatSupplierArrivalRateMonthlyDao;
import com.aiqin.bms.scmp.api.statistics.dao.StatSupplierArrivalRateYearlyDao;
import com.aiqin.bms.scmp.api.statistics.dao.StatSupplierReturnRateMonthlyDao;
import com.aiqin.bms.scmp.api.statistics.dao.StatSupplierReturnRateYearlyDao;
import com.aiqin.bms.scmp.api.statistics.domain.request.SupplierRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.supplier.*;
import com.aiqin.bms.scmp.api.statistics.service.SupplierStatisticsService;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-09-07
 **/
@Service
public class SupplierStatisticsServiceImpl implements SupplierStatisticsService {

    @Resource
    private StatSupplierArrivalRateYearlyDao statSupplierArrivalRateYearlyDao;
    @Resource
    private StatSupplierArrivalRateMonthlyDao statSupplierArrivalRateMonthlyDao;
    @Resource
    private StatSupplierReturnRateYearlyDao statSupplierReturnRateYearlyDao;
    @Resource
    private StatSupplierReturnRateMonthlyDao statSupplierReturnRateMonthlyDao;

    private final static int YEAR = 0;
    private final static int MONTH = 2;

    @Override
    public HttpResponse<SupplierDeliveryResponse> supplierDelivery(SupplierRequest request){
        if(request == null || StringUtils.isBlank(request.getDate()) || request.getReportType() == null ||
                StringUtils.isBlank(request.getProductSortCode())){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        SupplierDeliveryResponse response;
        if(request.getReportType().equals(Global.ANNUAL_REPORT)){
            // 供应商到货率统计 - 部门 - 年报
            Long year = Long.valueOf(request.getDate());
            request.setYear(year);
            response = this.supplierArrival(request, YEAR);
        }else {
            // 供应商到货率统计 - 部门 - 月报
            Long year = Long.valueOf(request.getDate().substring(0, 4));
            Long month = Long.valueOf(request.getDate().substring(5));
            request.setYear(year);
            request.setMonth(month);
            response = this.supplierArrival(request, MONTH);
        }
        return HttpResponse.success(response);
    }

    private SupplierDeliveryResponse supplierArrival(SupplierRequest request, int i){
        SupplierDeliveryResponse response = new SupplierDeliveryResponse();
        List<SupplierDeliveryResponse> list = Lists.newArrayList();
        List<SupplierDeliveryResponse> cateList;
        List<StatSupplierArrivalRateResponse> supplierList;
        List<StatSupplierArrivalRateResponse> categoryList;
        SupplierDeliveryResponse supplierResponse;
        SupplierDeliveryResponse categoryResponse;
        List<SupplierResponse> categorys;
        List<StatSupplierArrivalRateResponse> sumList = i == YEAR ?
                statSupplierArrivalRateYearlyDao.supplierArrivalSum(request) : statSupplierArrivalRateMonthlyDao.supplierArrivalSum(request);
        if(CollectionUtils.isNotEmptyCollection(sumList)){
            response = this.deliveryRate(sumList);
            // 计算供应商
            List<SupplierResponse> suppliers = i == YEAR ?
                    statSupplierArrivalRateYearlyDao.supplierList(request): statSupplierArrivalRateMonthlyDao.supplierList(request);
            if(CollectionUtils.isNotEmptyCollection(suppliers)){
                for(SupplierResponse supplier:suppliers){
                    request.setSupplierCode(supplier.getSupplierCode());
                    request.setLv1(null);
                    supplierList = i == YEAR ?
                            statSupplierArrivalRateYearlyDao.supplierArrivalSum(request) : statSupplierArrivalRateMonthlyDao.supplierArrivalSum(request);
                    if(CollectionUtils.isNotEmptyCollection(supplierList)){
                        // 查询品类
                        categorys = i == YEAR ?
                                statSupplierArrivalRateYearlyDao.categoryList(request): statSupplierArrivalRateMonthlyDao.categoryList(request);
                        cateList = Lists.newArrayList();
                        if(CollectionUtils.isNotEmptyCollection(categorys)){
                            for(SupplierResponse category:categorys){
                                request.setLv1(category.getLv1());
                                categoryList = i == YEAR ?
                                        statSupplierArrivalRateYearlyDao.supplierArrivalSum(request) : statSupplierArrivalRateMonthlyDao.supplierArrivalSum(request);
                                categoryResponse = this.deliveryRate(categoryList);
                                categoryResponse.setSupplierCode(category.getSupplierCode());
                                categoryResponse.setSupplierName(category.getSupplierName());
                                categoryResponse.setLv1(category.getLv1());
                                categoryResponse.setLv1CategoryName(category.getLv1CategoryName());
                                categoryResponse.setResponsiblePersonCode(category.getResponsiblePersonCode());
                                categoryResponse.setResponsiblePersonName(category.getResponsiblePersonName());
                                cateList.add(categoryResponse);
                            }
                        }
                        supplierResponse = this.deliveryRate(supplierList);
                        supplierResponse.setSupplierCode(supplier.getSupplierCode());
                        supplierResponse.setSupplierName(supplier.getSupplierName());
                        supplierResponse.setSubsetList(cateList);
                        list.add(supplierResponse);
                    }
                }
                response.setSubsetList(list);
            }
        }
        return response;
    }

    // 计算各仓的到货率
    private SupplierDeliveryResponse deliveryRate(List<StatSupplierArrivalRateResponse> responseList) {
        SupplierDeliveryResponse response = new SupplierDeliveryResponse();
        if (CollectionUtils.isNotEmptyCollection(responseList)) {
            // 计算各供应商到货率小计
            Long amount = 0L;
            BigDecimal big = new BigDecimal(0);
            Long sumGoodsCount = 0L, sumGoodsAmount = 0L, sumWarehouseCount = 0L, sumWarehouseAmount = 0L;
            for (StatSupplierArrivalRateResponse supplier : responseList) {
                Long preInboundNum = supplier.getPreInboundNum() == null ? amount : supplier.getPreInboundNum();
                Long preTaxAmount = supplier.getPreTaxAmount() == null ? amount : supplier.getPreTaxAmount().longValue();
                Long praInboundNum = supplier.getPraInboundNum() == null ? amount : supplier.getPraInboundNum();
                Long praTaxAmount = supplier.getPraTaxAmount() == null ? amount : supplier.getPraInboundNum();
                sumGoodsCount += preInboundNum;
                sumGoodsAmount += preTaxAmount;
                sumWarehouseCount += praInboundNum;
                sumWarehouseAmount += praTaxAmount;
                // 华北仓
                if (supplier.getTransportCenterCode().equals(Global.HB_CODE)) {
                    response.setHbGoodsCount(preInboundNum);
                    response.setHbGoodsAmount(new BigDecimal(preTaxAmount));
                    if (praInboundNum == amount || preInboundNum == amount) {
                        response.setHbGoodsRate(big);
                    } else {
                        response.setHbGoodsRate(new BigDecimal(praInboundNum).divide(new BigDecimal(preInboundNum), 4, BigDecimal.ROUND_HALF_UP));
                    }
                    response.setHbWarehouseCount(praInboundNum);
                    response.setHbWarehouseAmount(new BigDecimal(praTaxAmount));
                    if (praTaxAmount == amount || preInboundNum == amount) {
                        response.setHbAmountRate(big);
                    } else {
                        response.setHbAmountRate(new BigDecimal(praTaxAmount).divide(new BigDecimal(preInboundNum), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }
                // 华南仓
                if (supplier.getTransportCenterCode().equals(Global.HN_CODE)) {
                    response.setHnGoodsCount(preInboundNum);
                    response.setHnGoodsAmount(new BigDecimal(preTaxAmount));
                    if (praInboundNum == amount || preInboundNum == amount) {
                        response.setHnGoodsRate(big);
                    } else {
                        response.setHnGoodsRate(new BigDecimal(praInboundNum).divide(new BigDecimal(preInboundNum), 4, BigDecimal.ROUND_HALF_UP));
                    }
                    response.setHnWarehouseCount(praInboundNum);
                    response.setHnWarehouseAmount(new BigDecimal(praTaxAmount));
                    if (praTaxAmount == amount || preInboundNum == amount) {
                        response.setHnAmountRate(big);
                    } else {
                        response.setHnAmountRate(new BigDecimal(praTaxAmount).divide(new BigDecimal(preInboundNum), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }
                // 西南仓
                if (supplier.getTransportCenterCode().equals(Global.XN_CODE)) {
                    response.setXnGoodsCount(preInboundNum);
                    response.setXnGoodsAmount(new BigDecimal(preTaxAmount));
                    if (praInboundNum == amount || preInboundNum == amount) {
                        response.setXnGoodsRate(big);
                    } else {
                        response.setXnGoodsRate(new BigDecimal(praInboundNum).divide(new BigDecimal(preInboundNum), 4, BigDecimal.ROUND_HALF_UP));
                    }
                    response.setXnWarehouseCount(praInboundNum);
                    response.setXnWarehouseAmount(new BigDecimal(praTaxAmount));
                    if (praTaxAmount == amount || preInboundNum == amount) {
                        response.setXnAmountRate(big);
                    } else {
                        response.setXnAmountRate(new BigDecimal(praTaxAmount).divide(new BigDecimal(preInboundNum), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }
                // 华中仓
                if (supplier.getTransportCenterCode().equals(Global.HZ_CODE)) {
                    response.setHzGoodsCount(preInboundNum);
                    response.setHzGoodsAmount(new BigDecimal(preTaxAmount));
                    if (praInboundNum == amount || preInboundNum == amount) {
                        response.setHzGoodsRate(big);
                    } else {
                        response.setHzGoodsRate(new BigDecimal(praInboundNum).divide(new BigDecimal(preInboundNum), 4, BigDecimal.ROUND_HALF_UP));
                    }
                    response.setHzWarehouseCount(praInboundNum);
                    response.setHzWarehouseAmount(new BigDecimal(praTaxAmount));
                    if (praTaxAmount == amount || preInboundNum == amount) {
                        response.setHzAmountRate(big);
                    } else {
                        response.setHzAmountRate(new BigDecimal(praTaxAmount).divide(new BigDecimal(preInboundNum), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }
                // 华东仓
                if (supplier.getTransportCenterCode().equals(Global.HD_CODE)) {
                    response.setHdGoodsCount(preInboundNum);
                    response.setHdGoodsAmount(new BigDecimal(preTaxAmount));
                    if (praInboundNum == amount || preInboundNum == amount) {
                        response.setHdGoodsRate(big);
                    } else {
                        response.setHdGoodsRate(new BigDecimal(praInboundNum).divide(new BigDecimal(preInboundNum), 4, BigDecimal.ROUND_HALF_UP));
                    }
                    response.setHdWarehouseCount(praInboundNum);
                    response.setHdWarehouseAmount(new BigDecimal(praTaxAmount));
                    if (praTaxAmount == amount || preInboundNum == amount) {
                        response.setHdAmountRate(big);
                    } else {
                        response.setHdAmountRate(new BigDecimal(praTaxAmount).divide(new BigDecimal(preInboundNum), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }
            }
            response.setSumGoodsCount(sumGoodsCount);
            response.setSumGoodsAmount(new BigDecimal(sumGoodsAmount));
            response.setSumWarehouseCount(sumWarehouseCount);
            response.setSumWarehouseAmount(new BigDecimal(sumWarehouseAmount));
            if (sumGoodsCount == amount || sumWarehouseCount == amount) {
                response.setSumGoodsRate(big);
            } else {
                response.setSumGoodsRate(new BigDecimal(sumGoodsCount).divide(new BigDecimal(sumWarehouseCount), 4, BigDecimal.ROUND_HALF_UP));
            }
            if (sumWarehouseAmount == amount || sumGoodsAmount == amount) {
                response.setSumAmountRate(big);
            } else {
                response.setSumAmountRate(new BigDecimal(sumWarehouseAmount).divide(new BigDecimal(sumGoodsAmount), 4, BigDecimal.ROUND_HALF_UP));
            }
        }
        return response;
    }

    @Override
    public HttpResponse<SupplierReturnResponse> supplierRetreat(SupplierRequest request){
        if(request == null || StringUtils.isBlank(request.getDate()) || request.getReportType() == null ||
                StringUtils.isBlank(request.getProductSortCode())){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        SupplierReturnResponse response;
        if(request.getReportType().equals(Global.ANNUAL_REPORT)){
            // 供应商到货率统计 - 部门 - 年报
            Long year = Long.valueOf(request.getDate());
            request.setYear(year);
            response = this.supplierReturn(request, YEAR);
        }else {
            // 供应商到货率统计 - 部门 - 月报
            Long year = Long.valueOf(request.getDate().substring(0, 4));
            Long month = Long.valueOf(request.getDate().substring(5));
            request.setYear(year);
            request.setMonth(month);
            response = this.supplierReturn(request, MONTH);
        }
        return HttpResponse.success(response);
    }

    private SupplierReturnResponse supplierReturn(SupplierRequest request, int i){
        SupplierReturnResponse response = new SupplierReturnResponse();
        SupplierReturnResponse supplierResponse;
        List<SupplierReturnResponse> returnList = Lists.newArrayList();
        List<StatSupplierReturnRateResponse> supplierList;
        List<StatSupplierReturnRateResponse> sumList = i == YEAR ?
                statSupplierReturnRateYearlyDao.supplierReturnSum(request) : statSupplierReturnRateMonthlyDao.supplierReturnSum(request);
        if(CollectionUtils.isNotEmptyCollection(sumList)){
            response = this.proivnceReturn(sumList);
            // 查询供应商
            List<SupplierResponse> suppliers = i == YEAR ?
                    statSupplierReturnRateYearlyDao.supplierList(request): statSupplierReturnRateMonthlyDao.supplierList(request);
            if(CollectionUtils.isNotEmptyCollection(suppliers)){
                for(SupplierResponse supplier:suppliers){
                    request.setSupplierCode(supplier.getSupplierCode());
                    supplierList = i == YEAR ?
                            statSupplierReturnRateYearlyDao.supplierReturnSum(request) : statSupplierReturnRateMonthlyDao.supplierReturnSum(request);
                    if(CollectionUtils.isNotEmptyCollection(supplierList)){
                        supplierResponse = this.proivnceReturn(sumList);
                        supplierResponse.setSupplierCode(supplier.getSupplierCode());
                        supplierResponse.setSupplierName(supplier.getSupplierName());
                        supplierResponse.setResponsiblePersonCode(supplier.getResponsiblePersonCode());
                        supplierResponse.setResponsiblePersonName(supplier.getResponsiblePersonName());
                        returnList.add(supplierResponse);
                    }
                }
                response.setSubsetList(returnList);
            }
        }
        return response;
    }

    private SupplierReturnResponse proivnceReturn(List<StatSupplierReturnRateResponse> suppliers){
        SupplierReturnResponse response = new SupplierReturnResponse();
        if(CollectionUtils.isNotEmptyCollection(suppliers)){
            Long amount = 0L;
            BigDecimal big = new BigDecimal(0);
            Long sumSingleCount = 0L, sumAmt = 0L, sumSalesAmount = 0L;
            for (StatSupplierReturnRateResponse supplier:suppliers){
                Long singleCount = supplier.getSingleCount() == null ? amount : supplier.getSingleCount();
                Long amt = supplier.getAmt() == null ? amount : supplier.getAmt().longValue();
                Long salesAmount = supplier.getSalesAmount() == null ? amount : supplier.getSalesAmount().longValue();
                sumSingleCount += singleCount;
                sumAmt += amt;
                sumSalesAmount += salesAmount;
                // 华北仓
                if (supplier.getTransportCenterCode().equals(Global.HB_CODE)) {
                    response.setHbSingleCount(singleCount);
                    response.setHbAmt(new BigDecimal(amt));
                    if (amt == amount || salesAmount == amount) {
                        response.setHbReturnRate(big);
                    } else {
                        response.setHbReturnRate(new BigDecimal(amt).divide(new BigDecimal(salesAmount), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }
                // 华东仓
                if (supplier.getTransportCenterCode().equals(Global.HD_CODE)) {
                    response.setHdSingleCount(singleCount);
                    response.setHdAmt(new BigDecimal(amt));
                    if (amt == amount || salesAmount == amount) {
                        response.setHdReturnRate(big);
                    } else {
                        response.setHdReturnRate(new BigDecimal(amt).divide(new BigDecimal(salesAmount), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }
                // 华南仓
                if (supplier.getTransportCenterCode().equals(Global.HN_CODE)) {
                    response.setHnSingleCount(singleCount);
                    response.setHnAmt(new BigDecimal(amt));
                    if (amt == amount || salesAmount == amount) {
                        response.setHnReturnRate(big);
                    } else {
                        response.setHnReturnRate(new BigDecimal(amt).divide(new BigDecimal(salesAmount), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }
                // 西南仓
                if (supplier.getTransportCenterCode().equals(Global.XN_CODE)) {
                    response.setXnSingleCount(singleCount);
                    response.setXnAmt(new BigDecimal(amt));
                    if (amt == amount || salesAmount == amount) {
                        response.setXnReturnRate(big);
                    } else {
                        response.setXnReturnRate(new BigDecimal(amt).divide(new BigDecimal(salesAmount), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }
                // 华中仓
                if (supplier.getTransportCenterCode().equals(Global.HZ_CODE)) {
                    response.setHzSingleCount(singleCount);
                    response.setHzAmt(new BigDecimal(amt));
                    if (amt == amount || salesAmount == amount) {
                        response.setHzReturnRate(big);
                    } else {
                        response.setHzReturnRate(new BigDecimal(amt).divide(new BigDecimal(salesAmount), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }
            }
            response.setSumSingleCount(sumSingleCount);
            response.setSumAmt(new BigDecimal(sumAmt));
            if(sumAmt == amount || sumSalesAmount == amount){
                response.setSumReturnRate(big);
            }else {
                response.setSumReturnRate(new BigDecimal(sumAmt).divide(new BigDecimal(sumSalesAmount), 4, BigDecimal.ROUND_HALF_UP));
            }
        }
        return response;
    }
}
