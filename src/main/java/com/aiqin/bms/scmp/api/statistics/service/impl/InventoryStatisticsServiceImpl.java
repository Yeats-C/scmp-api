package com.aiqin.bms.scmp.api.statistics.service.impl;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.statistics.dao.*;
import com.aiqin.bms.scmp.api.statistics.domain.request.InventoryStatisticsRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.inventory.InventorySortResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.inventory.InventoryStatisticsResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.inventory.inventoryInfoResponse;
import com.aiqin.bms.scmp.api.statistics.service.InventoryStatisticsService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-09-16
 **/
@Service
public class InventoryStatisticsServiceImpl implements InventoryStatisticsService {

    @Resource
    private StatDeptLowInventoryWeeklyDao statDeptLowInventoryWeeklyDao;
    @Resource
    private StatDeptLowInventoryMonthlyDao statDeptLowInventoryMonthlyDao;
    @Resource
    private StatDeptLowInventoryQuarterlyDao statDeptLowInventoryQuarterlyDao;
    @Resource
    private StatDeptLowInventoryYearlyDao statDeptLowInventoryYearlyDao;
    @Resource
    private StatDeptHighInventoryWeeklyDao statDeptHighInventoryWeeklyDao;
    @Resource
    private StatDeptHighInventoryMonthlyDao statDeptHighInventoryMonthlyDao;
    @Resource
    private StatDeptHighInventoryQuarterlyDao statDeptHighInventoryQuarterlyDao;
    @Resource
    private StatDeptHighInventoryYearlyDao statDeptHighInventoryYearlyDao;

    private final static int YEAR = 0;
    private final static int QUARTER = 1;
    private final static int MONTH = 2;
    private final static int WEEK = 3;

    private final static int LOW = 0;
    private final static int HIGH = 1;

    @Override
    public HttpResponse<InventoryStatisticsResponse> inventory(InventoryStatisticsRequest request){
        if(request == null || StringUtils.isBlank(request.getDate()) || request.getType() == null ||
                request.getReportType() == null || request.getStockType() == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        InventoryStatisticsResponse response;
        if(request.getType().equals(Global.COMPANY)){
            // 公司低库存统计
            if(request.getReportType().equals(Global.ANNUAL_REPORT)){
               // 公司年报
               this.date(request, YEAR);
                response = this.companyInventory(request, YEAR);
            }else if(request.getReportType().equals(Global.QUARTERLY_REPORT)){
                // 公司季报
                this.date(request, QUARTER);
                response = this.companyInventory(request, QUARTER);
            }else if (request.getReportType().equals(Global.MONTHLY_REPORT)){
                // 公司月报
                this.date(request, MONTH);
                response = this.companyInventory(request, MONTH);
            }else {
                // 公司周报
                this.date(request, WEEK);
                response = this.companyInventory(request, WEEK);
            }
        }else {
            if (StringUtils.isBlank(request.getProductSortCode())){
                return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
            }
            // 部门低库存统计
            if(request.getReportType().equals(Global.ANNUAL_REPORT)){
                // 部门年报
                this.date(request, YEAR);
                response = this.deptInventory(request, YEAR);
            }else if(request.getReportType().equals(Global.QUARTERLY_REPORT)){
                // 部门季报
                this.date(request, QUARTER);
                response = this.deptInventory(request, QUARTER);
            }else if (request.getReportType().equals(Global.MONTHLY_REPORT)){
                // 部门月报
                this.date(request, MONTH);
                response = this.deptInventory(request, MONTH);
            }else {
                // 部门周报
                this.date(request, WEEK);
                response = this.deptInventory(request, WEEK);
            }
        }
        return HttpResponse.success(response);
    }

    private void date(InventoryStatisticsRequest request, int i){
        if(i == YEAR){
            Long year = Long.valueOf(request.getDate());
            request.setYear(year);
        }else {
            Long year = Long.valueOf(request.getDate().substring(0, 4));
            Long month = Long.valueOf(request.getDate().substring(5));
            request.setYear(year);
            request.setMonth(month);
        }
    }

    private InventoryStatisticsResponse companyInventory(InventoryStatisticsRequest request, int i) {
        InventoryStatisticsResponse sumResponse = new InventoryStatisticsResponse();
        List<InventoryStatisticsResponse> deptSumList = Lists.newArrayList();
        List<inventoryInfoResponse> sumList;
        List<InventorySortResponse> depts;
        InventoryStatisticsResponse deptResponse;
        if(request.getStockType().equals(LOW)){
            sumList = this.inventoryLowList(request, i);
        }else {
            sumList = this.inventoryHighList(request, i);
        }
        if (CollectionUtils.isNotEmpty(sumList)) {
            sumResponse = this.inventoryRate(sumList);
            // 查询所属部门
            if(request.getStockType().equals(LOW)){
                if(i == YEAR){
                    depts = statDeptLowInventoryYearlyDao.deptList(request);
                }else if (i == QUARTER){
                    depts = statDeptLowInventoryQuarterlyDao.deptList(request);
                }else if (i == MONTH){
                    depts = statDeptLowInventoryMonthlyDao.deptList(request);
                }else {
                    depts = statDeptLowInventoryWeeklyDao.deptList(request);
                }
            }else {
                if(i == YEAR){
                    depts = statDeptHighInventoryYearlyDao.deptHighList(request);
                }else if (i == QUARTER){
                    depts = statDeptHighInventoryQuarterlyDao.deptHighList(request);
                }else if (i == MONTH){
                    depts = statDeptHighInventoryMonthlyDao.deptHighList(request);
                }else {
                    depts = statDeptHighInventoryWeeklyDao.deptHighList(request);
                }
            }
            if(CollectionUtils.isNotEmpty(depts)){
                for(InventorySortResponse dept:depts){
                    request.setProductSortCode(dept.getProductSortCode());
                    request.setPurchaseGroupCode(null);
                    deptResponse = this.deptInventory(request, i);
                    if(deptResponse != null){
                        deptResponse.setProductSortCode(dept.getProductSortCode());
                        deptResponse.setProductSortName(dept.getProductSortName());
                        deptSumList.add(deptResponse);
                    }
                }
                sumResponse.setSubsetList(deptSumList);
            }
        }
        return sumResponse;
    }

    private InventoryStatisticsResponse deptInventory(InventoryStatisticsRequest request, int i){
        InventoryStatisticsResponse deptResponse = new InventoryStatisticsResponse();
        List<InventoryStatisticsResponse> groupSumList = Lists.newArrayList();
        InventoryStatisticsResponse groupResponse;
        List<inventoryInfoResponse> deptList;
        List<InventorySortResponse> groups;
        List<inventoryInfoResponse> groupList;
        if(request.getStockType().equals(LOW)){
            deptList = this.inventoryLowList(request, i);
        }else {
            deptList = this.inventoryHighList(request, i);
        }
        if(CollectionUtils.isNotEmpty(deptList)){
            deptResponse = this.inventoryRate(deptList);
            // 查询采购组
            if(request.getStockType().equals(LOW)){
                if(i == YEAR){
                    groups = statDeptLowInventoryYearlyDao.groupList(request);
                }else if (i == QUARTER){
                    groups = statDeptLowInventoryQuarterlyDao.groupList(request);
                }else if (i == MONTH){
                    groups = statDeptLowInventoryMonthlyDao.groupList(request);
                }else {
                    groups = statDeptLowInventoryWeeklyDao.groupList(request);
                }
            }else {
                if(i == YEAR){
                    groups = statDeptHighInventoryYearlyDao.groupHighList(request);
                }else if (i == QUARTER){
                    groups = statDeptHighInventoryQuarterlyDao.groupHighList(request);
                }else if (i == MONTH){
                    groups = statDeptHighInventoryMonthlyDao.groupHighList(request);
                }else {
                    groups = statDeptHighInventoryWeeklyDao.groupHighList(request);
                }
            }
            if(CollectionUtils.isNotEmpty(groups)){
                for(InventorySortResponse group:groups){
                    request.setPurchaseGroupCode(group.getPurchaseGroupCode());
                    if(request.getStockType().equals(LOW)){
                        groupList = this.inventoryLowList(request, i);
                    }else {
                        groupList = this.inventoryHighList(request, i);
                    }
                    if(CollectionUtils.isNotEmpty(groupList)){
                        groupResponse = this.inventoryRate(groupList);
                        groupResponse.setPurchaseGroupCode(group.getPurchaseGroupCode());
                        groupResponse.setPurchaseGroupName(group.getPurchaseGroupName());
                        groupResponse.setResponsiblePersonCode(group.getResponsiblePersonCode());
                        groupResponse.setResponsiblePersonName(group.getResponsiblePersonName());
                        groupSumList.add(groupResponse);
                    }
                }
                deptResponse.setSubsetList(groupSumList);
            }
        }
        return deptResponse;
    }

    private List<inventoryInfoResponse> inventoryLowList(InventoryStatisticsRequest request, int i) {
        List<inventoryInfoResponse> list;
        if (i == YEAR) {
            list = statDeptLowInventoryYearlyDao.inventorySum(request);
        } else if (i == QUARTER) {
            list = statDeptLowInventoryQuarterlyDao.inventorySum(request);
        } else if (i == MONTH) {
            list = statDeptLowInventoryMonthlyDao.inventorySum(request);
        } else {
            list = statDeptLowInventoryWeeklyDao.inventorySum(request);
        }
        return list;
    }

    private List<inventoryInfoResponse> inventoryHighList(InventoryStatisticsRequest request, int i) {
        List<inventoryInfoResponse> list;
        if(i == YEAR){
            list = statDeptHighInventoryYearlyDao.inventoryHighSum(request);
        }else if (i == QUARTER){
            list = statDeptHighInventoryQuarterlyDao.inventoryHighSum(request);
        }else if (i == MONTH){
            list = statDeptHighInventoryMonthlyDao.inventoryHighSum(request);
        }else {
            list = statDeptHighInventoryWeeklyDao.inventoryHighSum(request);
        }
        return list;
    }

    private InventoryStatisticsResponse inventoryRate(List<inventoryInfoResponse> infoList){
        InventoryStatisticsResponse response = new InventoryStatisticsResponse();
        if(CollectionUtils.isNotEmpty(infoList)){
            Long num = 0L;
            BigDecimal big = new BigDecimal(0);
            Long preTotalSkuSum = 0L, preSkuCountSum  = 0L, preStockAmountTotalSum  = 0L, preStockAmountSum  = 0L;
            Long totalSkuSum  = 0L, skuCountSum  = 0L, stockAmountTotalSum  = 0L, stockAmountSum  = 0L;
            for(inventoryInfoResponse info:infoList){
                Long preTotalSku = info.getPreTotalSku() == null ? num : info.getPreTotalSku();
                Long preSkuCount = info.getPreSkuCount() == null ? num : info.getPreSkuCount();
                Long preStockAmountTotal = info.getPreStockAmountTotal() == null ? num : info.getPreStockAmountTotal();
                Long preStockAmount = info.getPreStockAmount() == null ? num : info.getPreStockAmount();
                Long totalSku = info.getTotalSku() == null ? num : info.getTotalSku();
                Long skuCount = info.getSkuCount() == null ? num : info.getSkuCount();
                Long stockAmountTotal = info.getStockAmountTotal() == null ? num : info.getStockAmountTotal();
                Long stockAmount = info.getStockAmount() == null ? num : info.getStockAmount();
                preTotalSkuSum += preTotalSku;
                preSkuCountSum += preSkuCount;
                preStockAmountTotalSum += preStockAmountTotal;
                preStockAmountSum += preStockAmount;
                totalSkuSum += totalSku;
                skuCountSum += skuCount;
                stockAmountTotalSum += stockAmountTotal;
                stockAmountSum += stockAmount;
                if(info.getTransportCenterCode().equals(Global.HB_CODE)){
                    response.setHbSkuCount(skuCount);
                    response.setHbTotalSku(totalSku);
                    if(skuCount == 0 || totalSku == 0){
                        response.setHbSkuRate(big);
                    }else {
                        response.setHbSkuRate(new BigDecimal(skuCount).divide(new BigDecimal(totalSku), 4, BigDecimal.ROUND_HALF_UP));
                    }
                    response.setHbStockAmount(stockAmount);
                    response.setHbStockAmountTotal(stockAmountTotal);
                    if(stockAmount == 0 || stockAmountTotal == 0){
                        response.setHbStockAmountRate(big);
                    }else {
                        response.setHbStockAmountRate(new BigDecimal(stockAmount).divide(new BigDecimal(stockAmountTotal), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }else if(info.getTransportCenterCode().equals(Global.HD_CODE)){
                    response.setHdSkuCount(skuCount);
                    response.setHdTotalSku(totalSku);
                    if(skuCount == 0 || totalSku == 0){
                        response.setHdSkuRate(big);
                    }else {
                        response.setHdSkuRate(new BigDecimal(skuCount).divide(new BigDecimal(totalSku), 4, BigDecimal.ROUND_HALF_UP));
                    }
                    response.setHdStockAmount(stockAmount);
                    response.setHdStockAmountTotal(stockAmountTotal);
                    if(stockAmount == 0 || stockAmountTotal == 0){
                        response.setHdStockAmountRate(big);
                    }else {
                        response.setHdStockAmountRate(new BigDecimal(stockAmount).divide(new BigDecimal(stockAmountTotal), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }else if(info.getTransportCenterCode().equals(Global.HN_CODE)){
                    response.setHnSkuCount(skuCount);
                    response.setHnTotalSku(totalSku);
                    if(skuCount == 0 || totalSku == 0){
                        response.setHnSkuRate(big);
                    }else {
                        response.setHnSkuRate(new BigDecimal(skuCount).divide(new BigDecimal(totalSku), 4, BigDecimal.ROUND_HALF_UP));
                    }
                    response.setHnStockAmount(stockAmount);
                    response.setHnStockAmountTotal(stockAmountTotal);
                    if(stockAmount == 0 || stockAmountTotal == 0){
                        response.setHnStockAmountRate(big);
                    }else {
                        response.setHnStockAmountRate(new BigDecimal(stockAmount).divide(new BigDecimal(stockAmountTotal), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }else if(info.getTransportCenterCode().equals(Global.XN_CODE)){
                    response.setXnSkuCount(skuCount);
                    response.setXnTotalSku(totalSku);
                    if(skuCount == 0 || totalSku == 0){
                        response.setXnSkuRate(big);
                    }else {
                        response.setXnSkuRate(new BigDecimal(skuCount).divide(new BigDecimal(totalSku), 4, BigDecimal.ROUND_HALF_UP));
                    }
                    response.setXnStockAmount(stockAmount);
                    response.setXnStockAmountTotal(stockAmountTotal);
                    if(stockAmount == 0 || stockAmountTotal == 0){
                        response.setXnStockAmountRate(big);
                    }else {
                        response.setXnStockAmountRate(new BigDecimal(stockAmount).divide(new BigDecimal(stockAmountTotal), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }else if(info.getTransportCenterCode().equals(Global.HZ_CODE)){
                    response.setHzSkuCount(skuCount);
                    response.setHzTotalSku(totalSku);
                    if(skuCount == 0 || totalSku == 0){
                        response.setHzSkuRate(big);
                    }else {
                        response.setHzSkuRate(new BigDecimal(skuCount).divide(new BigDecimal(totalSku), 4, BigDecimal.ROUND_HALF_UP));
                    }
                    response.setHzStockAmount(stockAmount);
                    response.setHzStockAmountTotal(stockAmountTotal);
                    if(stockAmount == 0 || stockAmountTotal == 0){
                        response.setHzStockAmountRate(big);
                    }else {
                        response.setHzStockAmountRate(new BigDecimal(stockAmount).divide(new BigDecimal(stockAmountTotal), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }
            }
            response.setPreSkuCount(preSkuCountSum);
            response.setPreTotalSku(preTotalSkuSum);
            if(preSkuCountSum == 0 || preTotalSkuSum == 0){
                response.setPreSkuRate(big);
            }else {
                response.setPreSkuRate(new BigDecimal(preSkuCountSum).divide(new BigDecimal(preTotalSkuSum), 4, BigDecimal.ROUND_HALF_UP));
            }
            response.setPreStockAmount(preStockAmountSum);
            response.setPreStockAmountTotal(preStockAmountTotalSum);
            if(preStockAmountSum == 0 || preStockAmountTotalSum == 0){
                response.setPreStockAmountRate(big);
            }else {
                response.setPreStockAmountRate(new BigDecimal(preStockAmountSum).divide(new BigDecimal(preStockAmountTotalSum), 4, BigDecimal.ROUND_HALF_UP));
            }
            response.setTotalSku(totalSkuSum);
            response.setSkuCount(skuCountSum);
            if(skuCountSum == 0 || totalSkuSum == 0){
                response.setSkuRate(big);
            }else {
                response.setSkuRate(new BigDecimal(skuCountSum).divide(new BigDecimal(totalSkuSum), 4, BigDecimal.ROUND_HALF_UP));
            }
            response.setStockAmount(stockAmountSum);
            response.setStockAmountTotal(stockAmountTotalSum);
            if(stockAmountSum == 0 || stockAmountTotalSum == 0){
                response.setStockAmountRate(big);
            }else {
                response.setStockAmountRate(new BigDecimal(stockAmountSum).divide(new BigDecimal(stockAmountTotalSum), 4, BigDecimal.ROUND_HALF_UP));
            }
        }
        return response;
    }
}
