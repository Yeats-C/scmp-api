package com.aiqin.bms.scmp.api.statistics.service.impl;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.statistics.dao.StatComStoreRepurchaseRateDao;
import com.aiqin.bms.scmp.api.statistics.dao.StatDeptStoreRepurchaseRateDao;
import com.aiqin.bms.scmp.api.statistics.dao.StatSupplierArrivalRateMonthlyDao;
import com.aiqin.bms.scmp.api.statistics.dao.StatSupplierArrivalRateYearlyDao;
import com.aiqin.bms.scmp.api.statistics.domain.StatComStoreRepurchaseRate;
import com.aiqin.bms.scmp.api.statistics.domain.response.StoreRepurchaseRateResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.StoreRepurchaseRateSubtotalResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.SupplierDeliveryRateResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.SupplierDeliveryResponse;
import com.aiqin.bms.scmp.api.statistics.service.StatisticsService;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.druid.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-08-19
 **/
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private StatSupplierArrivalRateMonthlyDao statSupplierArrivalRateMonthlyDao;
    @Resource
    private StatSupplierArrivalRateYearlyDao statSupplierArrivalRateYearlyDao;
    @Resource
    private StatDeptStoreRepurchaseRateDao statDeptStoreRepurchaseRateDao;
    @Resource
    private StatComStoreRepurchaseRateDao statComStoreRepurchaseRateDao;

    private void currency(Integer type, String date){
        if(type == null && StringUtils.isEmpty(date)){
            type = 0;
            Calendar cale = null;
            cale = Calendar.getInstance();
            date = Integer.toString(cale.get(Calendar.YEAR));
        }
    }

    @Override
    public HttpResponse<SupplierDeliveryResponse> supplierDelivery(Integer type, String date){
       this.currency(type, date);
       // 查询年报信息
       List<SupplierDeliveryResponse> deliveryList;
       List<SupplierDeliveryRateResponse> rateList;
       if(type == 0){
           deliveryList = statSupplierArrivalRateYearlyDao.supplyArrivalYearByGroup(date);
           if(CollectionUtils.isNotEmptyCollection(deliveryList)){
               for(SupplierDeliveryResponse response:deliveryList){
                   rateList = statSupplierArrivalRateYearlyDao.supplyArrivalYearList(date, response.getSupplierCode(), response.getResponsiblePersonCode());
               }
           }
       }else {
           String year = date.substring(0, 4);
           String month = date.substring(5, date.length());
           deliveryList = statSupplierArrivalRateMonthlyDao.supplyArrivalMonthByGroup(year, month);
           if(CollectionUtils.isNotEmptyCollection(deliveryList)){
               for(SupplierDeliveryResponse response:deliveryList){
                   rateList = statSupplierArrivalRateMonthlyDao.supplyArrivalMonthList(year, month, response.getSupplierCode(), response.getResponsiblePersonCode());
               }
           }
       }
       return HttpResponse.success();
    }

    private void supplyArrivalRate(List<SupplierDeliveryRateResponse> list){
        if(CollectionUtils.isNotEmptyCollection(list)){
            SupplierDeliveryResponse delivery = new SupplierDeliveryResponse();
            Long hbSubtotalGoodsCount  = 0L, hbSubtotalGoodsAmount = 0L, hbSubtotalWarehouseCount = 0L, hbSubtotalWarehouseAmount = 0L;
            Long num = 0L;
            BigDecimal big = new BigDecimal(0);
            for(SupplierDeliveryRateResponse response:list){
                Long preInboundNum = response.getPreInboundNum() == null ? num : response.getPreInboundNum();
                Long preTaxAmount = response.getPreTaxAmount() == null ? num : response.getPreTaxAmount();
                Long praInboundNum = response.getPraInboundNum() == null ? num : response.getPraInboundNum();
                Long praTaxAmount = response.getPraTaxAmount() == null ? num : response.getPraTaxAmount();
                BigDecimal amountRate = response.getInboundAmountFillRate() == null ? big : response.getInboundAmountFillRate();
                BigDecimal goodsRate = response.getArrivalRate() == null ? big : response.getArrivalRate();
               if(response.getLogisticsCenterCode().equals(Global.HB_CODE)){
                   delivery.setHbGoodsCount(preInboundNum);
                   delivery.setHbGoodsAmount(preTaxAmount);
                   delivery.setHbAmountRate(amountRate);
                   delivery.setHbWarehouseCount(praInboundNum);
                   delivery.setHbWarehouseAmount(praTaxAmount);
                   delivery.setHbGoodsRate(goodsRate);
                   hbSubtotalGoodsCount += preInboundNum;
                   hbSubtotalGoodsAmount += preTaxAmount;
                   hbSubtotalWarehouseCount += praInboundNum;
                   hbSubtotalWarehouseAmount += praTaxAmount;
               }
            }
            delivery.setHbSubtotalGoodsCount(hbSubtotalGoodsCount);
            delivery.setHbSubtotalGoodsAmount(hbSubtotalGoodsAmount);
            delivery.setHbSubtotalWarehouseCount(hbSubtotalWarehouseCount);
            delivery.setHbSubtotalWarehouseAmount(hbSubtotalWarehouseAmount);
            delivery.setHbSubtotalAmountRate(new BigDecimal(hbSubtotalGoodsAmount).divide(new BigDecimal(hbSubtotalWarehouseAmount)));
        }
    }

    @Override
    public HttpResponse<StoreRepurchaseRateResponse> storeRepurchaseRate(String date, Integer type, String productSortCode){
        this.currency(type, date);
        List<StatComStoreRepurchaseRate> repurchaseRateList;
        StoreRepurchaseRateResponse comRepurchase;
        List<StoreRepurchaseRateSubtotalResponse> deptList = new ArrayList<>();
        StoreRepurchaseRateSubtotalResponse deptRepurchase;
        Long year = Long.valueOf(date.substring(0, 4));
        Long month = Long.valueOf(date.substring(5, date.length()));
        if(type == 0){
            // 计算公司合计
            comRepurchase = statComStoreRepurchaseRateDao.storeRepurchaseSum(year, month);
            if(comRepurchase != null){
                // 查询该公司部门
                List<StatComStoreRepurchaseRate> sortList = statComStoreRepurchaseRateDao.storeRepurchaseBySort(year, month);
                for(StatComStoreRepurchaseRate sort:sortList){
                    // 计算各部门的小计
                    deptRepurchase = statComStoreRepurchaseRateDao.storeRepurchaseByDeptSum(year, month, sort.getProductSortCode());
                    repurchaseRateList = statComStoreRepurchaseRateDao.storeRepurchaseList(year, month, sort.getProductSortCode());
                    if(CollectionUtils.isNotEmptyCollection(repurchaseRateList)){
                        deptRepurchase.setSubtotalList(repurchaseRateList);
                    }
                    deptRepurchase.setProductSortCode(sort.getProductSortCode());
                    deptRepurchase.setProductSortName(sort.getProductSortName());
                    deptList.add(deptRepurchase);
                }
                comRepurchase.setSumList(deptList);
            }
            return HttpResponse.success(comRepurchase);
        }else {
            if(StringUtils.isEmpty(productSortCode)){
                return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
            }
            deptRepurchase = statDeptStoreRepurchaseRateDao.storeRepurchaseSum(year, month, productSortCode);
            if(deptRepurchase != null){
                // 计算部门各省
                repurchaseRateList = statDeptStoreRepurchaseRateDao.storeRepurchaseList(year, month, productSortCode);
                if(CollectionUtils.isNotEmptyCollection(repurchaseRateList)){
                    deptRepurchase.setSubtotalList(repurchaseRateList);
                }
            }
            return HttpResponse.success(deptRepurchase);
        }
    }
}
