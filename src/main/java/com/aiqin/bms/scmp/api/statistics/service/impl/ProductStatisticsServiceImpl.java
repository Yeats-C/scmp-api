package com.aiqin.bms.scmp.api.statistics.service.impl;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.statistics.dao.StatDeptNewProductMovingRateDao;
import com.aiqin.bms.scmp.api.statistics.dao.StatDeptStockoutDao;
import com.aiqin.bms.scmp.api.statistics.domain.StatDeptStockout;
import com.aiqin.bms.scmp.api.statistics.domain.request.ProductRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.product.MovableResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.product.NewProductMovingRateResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.product.ProductMovableResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.product.ProductStockOutResponse;
import com.aiqin.bms.scmp.api.statistics.service.ProductStatisticsService;
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
 * @create: 2019-09-11
 **/
@Service
public class ProductStatisticsServiceImpl implements ProductStatisticsService {

    @Resource
    private StatDeptNewProductMovingRateDao statDeptNewProductMovingRateDao;
    @Resource
    private StatDeptStockoutDao statDeptStockoutDao;

    @Override
    public HttpResponse<ProductMovableResponse> productMovable(ProductRequest request){
        if(request == null || StringUtils.isBlank(request.getDate()) || request.getType() == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        Long year = Long.valueOf(request.getDate().substring(0, 4));
        Long month = Long.valueOf(request.getDate().substring(5));
        request.setYear(year);
        request.setMonth(month);
        ProductMovableResponse response;
        if(request.getType().equals(Global.COMPANY)){
            // 计算新品动销率统计 - 公司
            response = this.companyMovable(request);
        }else {
            // 计算新品动销率统计 - 部门
            response = this.deptMovable(request);
        }
        return HttpResponse.success(response);
    }

    private ProductMovableResponse companyMovable(ProductRequest request){
        ProductMovableResponse sumResponse = new ProductMovableResponse();
        List<ProductMovableResponse> deptSum = Lists.newArrayList();
        List<NewProductMovingRateResponse> sumList;
        List<MovableResponse> depts;
        List<NewProductMovingRateResponse> deptList;
        ProductMovableResponse deptResponse;
        sumList = statDeptNewProductMovingRateDao.productMovingSum(request);
        if(CollectionUtils.isNotEmpty(sumList)){
            sumResponse = this.movablePin(sumList);
            depts = statDeptNewProductMovingRateDao.deptList(request);
            if(CollectionUtils.isNotEmpty(depts)){
                for(MovableResponse dept:depts){
                    request.setProductSortCode(dept.getProductSortCode());
                    request.setLv1(null);
                    request.setPriceChannelCode(null);
                    deptList = statDeptNewProductMovingRateDao.productMovingSum(request);
                    if(CollectionUtils.isNotEmpty(deptList)){
                        deptResponse = this.deptMovable(request);
                        deptResponse.setProductSortCode(dept.getProductSortCode());
                        deptResponse.setProductSortName(dept.getProductSortName());
                        deptSum.add(deptResponse);
                    }
                }
                sumResponse.setSubsetList(deptSum);
            }
        }
        return sumResponse;
    }

    private ProductMovableResponse deptMovable(ProductRequest request){
        ProductMovableResponse deptResponse = new ProductMovableResponse();
        List<ProductMovableResponse> categorySum = Lists.newArrayList();
        List<ProductMovableResponse> channelSum;
        List<NewProductMovingRateResponse> deptList;
        List<NewProductMovingRateResponse> categoryList;
        List<NewProductMovingRateResponse> channelList;
        List<MovableResponse> categorys;
        List<MovableResponse> channels;
        ProductMovableResponse categoryResponse;
        ProductMovableResponse channelResponse;
        deptList = statDeptNewProductMovingRateDao.productMovingSum(request);
        if(CollectionUtils.isNotEmpty(deptList)){
            deptResponse = this.movablePin(deptList);
            categorys = statDeptNewProductMovingRateDao.categoryList(request);
            if(CollectionUtils.isNotEmpty(categorys)){
                for(MovableResponse category:categorys){
                    if(StringUtils.isNotBlank(category.getLv1())){
                        request.setLv1(category.getLv1());
                        request.setPriceChannelCode(null);
                        categoryList = statDeptNewProductMovingRateDao.productMovingSum(request);
                        if(CollectionUtils.isNotEmpty(categoryList)){
                            categoryResponse = this.movablePin(categoryList);
                            channels = statDeptNewProductMovingRateDao.channelList(request);
                            channelSum = Lists.newArrayList();
                            if(CollectionUtils.isNotEmpty(channels)){
                                for(MovableResponse channel:channels){
                                    request.setPriceChannelCode(channel.getPriceChannelCode());
                                    channelList = statDeptNewProductMovingRateDao.productMovingSum(request);
                                    channelResponse = this.movablePin(channelList);
                                    channelResponse.setLv1(channel.getLv1());
                                    channelResponse.setLv1CategoryName(channel.getLv1CategoryName());
                                    channelResponse.setPriceChannelCode(channel.getPriceChannelCode());
                                    channelResponse.setPriceChannelName(channel.getPriceChannelName());
                                    channelSum.add(channelResponse);
                                }
                            }
                            categoryResponse.setSubsetList(channelSum);
                            categoryResponse.setLv1(category.getLv1());
                            categoryResponse.setLv1CategoryName(category.getLv1CategoryName());
                            categorySum.add(categoryResponse);
                        }
                    }
                }
                deptResponse.setSubsetList(categorySum);
            }
        }
        return deptResponse;
    }

    private ProductMovableResponse movablePin(List<NewProductMovingRateResponse> movingList){
        ProductMovableResponse response = new ProductMovableResponse();
        if(CollectionUtils.isNotEmpty(movingList)){
            BigDecimal num = BigDecimal.ZERO;
            Long num1 = 0L;
            BigDecimal big = new BigDecimal(0);
            Long sumIniStockSkuNum = 0L,  sumMidPurchaseSkuNum = 0L, sumMidSalesSkuNum = 0L;

            BigDecimal  sumIniStockSkuCost = BigDecimal.ZERO,sumChannelSalesAmount = BigDecimal.ZERO,sumDistributionsalesAmount = BigDecimal.ZERO;
            for(NewProductMovingRateResponse mov:movingList){
                Long iniStockSkuNum = mov.getIniStockSkuNum() == null ? num1 : mov.getIniStockSkuNum();
                BigDecimal iniStockSkuCost = mov.getIniStockSkuCost() == null ? num : mov.getIniStockSkuCost();
                Long midPurchaseSkuNum = mov.getMidPurchaseSkuNum() == null ? num1 : mov.getMidPurchaseSkuNum();
                Long midSalesSkuNum = mov.getMidSalesSkuNum() == null ? num1 : mov.getMidSalesSkuNum();
                BigDecimal channelSalesAmount = mov.getChannelSalesAmount() == null ? num : mov.getChannelSalesAmount();
                BigDecimal distributionsalesAmount = mov.getDistributionsalesAmount() == null ? num : mov.getDistributionsalesAmount();
                sumIniStockSkuNum += iniStockSkuNum;
                sumIniStockSkuCost = sumIniStockSkuCost.add(iniStockSkuCost);
                sumMidPurchaseSkuNum += midPurchaseSkuNum;
                sumMidSalesSkuNum += midSalesSkuNum;
                sumChannelSalesAmount = sumChannelSalesAmount.add(channelSalesAmount);
                sumDistributionsalesAmount = sumDistributionsalesAmount.add(distributionsalesAmount);
                Long newSkuNum = iniStockSkuNum + midPurchaseSkuNum;
                if(StringUtils.isNotBlank(mov.getTransportCenterCode())) {
                    if (mov.getTransportCenterCode().equals(Global.HB_CODE)) {
                        response.setHbIniStockSkuNum(iniStockSkuNum);
                        response.setHbIniStockSkuCost(iniStockSkuCost);
                        response.setHbMidPurchaseSkuNum(midPurchaseSkuNum);
                        response.setHbMidSalesSkuNum(midSalesSkuNum);
                        response.setHbChannelSalesAmount(channelSalesAmount);
                        response.setHbDistributionsalesAmount(distributionsalesAmount);
                        if (midSalesSkuNum == num1 || newSkuNum == num1) {
                            response.setHbNewProMovingSalesRate(big);
                        } else {
                            if (newSkuNum == 0) {
                                response.setHbNewProMovingSalesRate(new BigDecimal(0));
                            } else {
                                response.setHbNewProMovingSalesRate(new BigDecimal(midSalesSkuNum).divide(new BigDecimal(newSkuNum), 4, BigDecimal.ROUND_HALF_UP));
                            }
                        }
                    } else if (mov.getTransportCenterCode().equals(Global.HD_CODE)) {
                        response.setHdIniStockSkuNum(iniStockSkuNum);
                        response.setHdIniStockSkuCost(iniStockSkuCost);
                        response.setHdMidPurchaseSkuNum(midPurchaseSkuNum);
                        response.setHdMidSalesSkuNum(midSalesSkuNum);
                        response.setHdChannelSalesAmount(channelSalesAmount);
                        response.setHdDistributionsalesAmount(distributionsalesAmount);
                        if (midSalesSkuNum == num1 || newSkuNum == num1) {
                            response.setHdNewProMovingSalesRate(big);
                        } else {
                            if (newSkuNum == 0) {
                                response.setHdNewProMovingSalesRate(new BigDecimal(0));
                            } else {
                                response.setHdNewProMovingSalesRate(new BigDecimal(midSalesSkuNum).divide(new BigDecimal(newSkuNum), 4, BigDecimal.ROUND_HALF_UP));
                            }
                        }
                    } else if (mov.getTransportCenterCode().equals(Global.HN_CODE)) {
                        response.setHnIniStockSkuNum(iniStockSkuNum);
                        response.setHnIniStockSkuCost(iniStockSkuCost);
                        response.setHnMidPurchaseSkuNum(midPurchaseSkuNum);
                        response.setHnMidSalesSkuNum(midSalesSkuNum);
                        response.setHnChannelSalesAmount(channelSalesAmount);
                        response.setHnDistributionsalesAmount(distributionsalesAmount);
                        if (midSalesSkuNum == num1 || newSkuNum == num1) {
                            response.setHnNewProMovingSalesRate(big);
                        } else {
                            if (newSkuNum == 0) {
                                response.setHnNewProMovingSalesRate(new BigDecimal(0));
                            } else {
                                response.setHnNewProMovingSalesRate(new BigDecimal(midSalesSkuNum).divide(new BigDecimal(newSkuNum), 4, BigDecimal.ROUND_HALF_UP));
                            }
                        }
                    } else if (mov.getTransportCenterCode().equals(Global.XN_CODE)) {
                        response.setXnIniStockSkuNum(iniStockSkuNum);
                        response.setXnIniStockSkuCost(iniStockSkuCost);
                        response.setXnMidPurchaseSkuNum(midPurchaseSkuNum);
                        response.setXnMidSalesSkuNum(midSalesSkuNum);
                        response.setXnChannelSalesAmount(channelSalesAmount);
                        response.setXnDistributionsalesAmount(distributionsalesAmount);
                        if (midSalesSkuNum == num1 || newSkuNum == num1) {
                            response.setXnNewProMovingSalesRate(big);
                        } else {
                            if (newSkuNum == 0) {
                                response.setXnNewProMovingSalesRate(new BigDecimal(0));
                            } else {
                                response.setXnNewProMovingSalesRate(new BigDecimal(midSalesSkuNum).divide(new BigDecimal(newSkuNum), 4, BigDecimal.ROUND_HALF_UP));
                            }
                        }
                    } else if (mov.getTransportCenterCode().equals(Global.HZ_CODE)) {
                        response.setHzIniStockSkuNum(iniStockSkuNum);
                        response.setHzIniStockSkuCost(iniStockSkuCost);
                        response.setHzMidPurchaseSkuNum(midPurchaseSkuNum);
                        response.setHzMidSalesSkuNum(midSalesSkuNum);
                        response.setHzChannelSalesAmount(channelSalesAmount);
                        response.setHzDistributionsalesAmount(distributionsalesAmount);
                        if (midSalesSkuNum == num1 || newSkuNum == num1) {
                            response.setHzNewProMovingSalesRate(big);
                        } else {
                            if (newSkuNum == 0) {
                                response.setHzNewProMovingSalesRate(new BigDecimal(0));
                            } else {
                                response.setHzNewProMovingSalesRate(new BigDecimal(midSalesSkuNum).divide(new BigDecimal(newSkuNum), 4, BigDecimal.ROUND_HALF_UP));
                            }
                        }
                    }
                }
            }
            response.setSumIniStockSkuNum(sumIniStockSkuNum);
            response.setSumIniStockSkuCost(sumIniStockSkuCost);
            response.setSumMidPurchaseSkuNum(sumMidPurchaseSkuNum);
            response.setSumMidSalesSkuNum(sumMidSalesSkuNum);
            response.setSumChannelSalesAmount(sumChannelSalesAmount);
            response.setSumDistributionsalesAmount(sumDistributionsalesAmount);
            Long sumNewSkuNum = sumIniStockSkuNum + sumMidPurchaseSkuNum;
            if(sumMidSalesSkuNum == num1 || sumNewSkuNum == num1){
                response.setSumNewProMovingSalesRate(big);
            }else {
                if(sumNewSkuNum == 0){
                    response.setSumNewProMovingSalesRate(new BigDecimal(0));
                }else{
                    response.setSumNewProMovingSalesRate(new BigDecimal(sumMidSalesSkuNum).divide(new BigDecimal(sumNewSkuNum), 4, BigDecimal.ROUND_HALF_UP));
                }
            }
        }
        return response;
    }

    @Override
    public HttpResponse<ProductStockOutResponse> productStockOut(ProductRequest request){
        if(request == null || StringUtils.isBlank(request.getDate()) || request.getType() == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        Long year = Long.valueOf(request.getDate().substring(0, 4));
        Long month = Long.valueOf(request.getDate().substring(5));
        request.setYear(year);
        request.setMonth(month);
        ProductStockOutResponse response;
        if(request.getType().equals(Global.COMPANY)){
            // 缺货统计 - 公司
            response = this.companyStockOut(request);
        }else {
            // 缺货统计 - 部门
            if(StringUtils.isBlank(request.getProductSortCode())){
                return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
            }
            response = this.deptStockOut(request);
        }
        return HttpResponse.success(response);
    }

    private ProductStockOutResponse companyStockOut(ProductRequest request){
        ProductStockOutResponse sumResponse = new ProductStockOutResponse();
        List<StatDeptStockout> sumList;
        List<StatDeptStockout> depts;
        ProductStockOutResponse deptResponse;
        List<ProductStockOutResponse> deptSumList = Lists.newArrayList();
        sumList = statDeptStockoutDao.stockOutSum(request);
        if(CollectionUtils.isNotEmpty(sumList)){
            sumResponse = this.stockOutRate(sumList);
            // 查询部门
            depts = statDeptStockoutDao.deptList(request);
            if(CollectionUtils.isNotEmpty(depts)){
                for(StatDeptStockout dept:depts){
                    if(dept != null){
                        request.setProductSortCode(dept.getProductSortCode());
                        request.setPurchaseGroupCode(null);
                        deptResponse = this.deptStockOut(request);
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

    private ProductStockOutResponse deptStockOut(ProductRequest request){
        ProductStockOutResponse deptResponse = new ProductStockOutResponse();
        List<StatDeptStockout> deptList;
        List<StatDeptStockout> groupList;
        List<StatDeptStockout> purchaseGroupList;
        ProductStockOutResponse groupResponse;
        List<ProductStockOutResponse> groupSumList = Lists.newArrayList();
        deptList = statDeptStockoutDao.stockOutSum(request);
        if(CollectionUtils.isNotEmpty(deptList)){
            deptResponse = this.stockOutRate(deptList);
            // 查询采购组
            purchaseGroupList = statDeptStockoutDao.purchaseGroupList(request);
            if(CollectionUtils.isNotEmpty(purchaseGroupList)){
                for (StatDeptStockout group:purchaseGroupList){
                    if(group != null){
                        request.setPurchaseGroupCode(group.getPurchaseGroupCode());
                        groupList = statDeptStockoutDao.stockOutSum(request);
                        if (CollectionUtils.isNotEmpty(groupList)) {
                            groupResponse = this.stockOutRate(groupList);
                            groupResponse.setPurchaseGroupCode(group.getPurchaseGroupCode());
                            groupResponse.setPurchaseGroupName(group.getPurchaseGroupName());
                            groupResponse.setResponsiblePersonCode(group.getResponsiblePersonCode());
                            groupResponse.setResponsiblePersonName(group.getResponsiblePersonName());
                            groupSumList.add(groupResponse);
                        }
                    }
                }
                deptResponse.setSubsetList(groupSumList);
            }
        }
        return deptResponse;
    }

    private ProductStockOutResponse stockOutRate(List<StatDeptStockout> stockList){
        ProductStockOutResponse response = new ProductStockOutResponse();
        if(CollectionUtils.isNotEmpty(stockList)){
            Long num = 0L;
            BigDecimal big = new BigDecimal(0);
            Long sumSkuNumTotal = 0L, sumStockoutSkuNum = 0L, sumStockoutEffectAmount = 0L;
            for(StatDeptStockout stock:stockList){
                Long stockoutSkuNum = stock.getStockoutSkuNum() == null ? num : stock.getStockoutSkuNum();
                Long skuNumTotal = stock.getSkuNumTotal() == null ? num : stock.getSkuNumTotal();
                Long stockoutEffectAmount = stock.getStockoutEffectAmount() == null ? num : stock.getStockoutEffectAmount().longValue();
                sumSkuNumTotal += skuNumTotal;
                sumStockoutSkuNum += stockoutSkuNum;
                sumStockoutEffectAmount += stockoutEffectAmount;
                if(stock.getTransportCenterCode().equals(Global.HB_CODE)){
                    response.setHbSkuNumTotal(skuNumTotal);
                    response.setHbStockoutSkuNum(stockoutSkuNum);
                    response.setHbStockoutEffectAmount(new BigDecimal(stockoutEffectAmount));
                    if(stockoutSkuNum == 0 || skuNumTotal == 0){
                        response.setHbStockoutRate(big);
                    }else {
                        if(skuNumTotal == 0){
                            response.setHbStockoutRate(new BigDecimal(0));
                        }else{
                            response.setHbStockoutRate(new BigDecimal(stockoutSkuNum).
                                    divide(new BigDecimal(skuNumTotal), 4, BigDecimal.ROUND_HALF_UP));
                        }
                    }
                }else if(stock.getTransportCenterCode().equals(Global.HD_CODE)){
                    response.setHdSkuNumTotal(skuNumTotal);
                    response.setHdStockoutSkuNum(stockoutSkuNum);
                    response.setHdStockoutEffectAmount(new BigDecimal(stockoutEffectAmount));
                    if(stockoutSkuNum == 0 || skuNumTotal == 0){
                        response.setHdStockoutRate(big);
                    }else {
                        if(skuNumTotal == 0){
                            response.setHdStockoutRate(new BigDecimal(0));
                        }else{
                            response.setHdStockoutRate(new BigDecimal(stockoutSkuNum).
                                    divide(new BigDecimal(skuNumTotal), 4, BigDecimal.ROUND_HALF_UP));
                        }
                    }
                }else if(stock.getTransportCenterCode().equals(Global.HN_CODE)){
                    response.setHnSkuNumTotal(skuNumTotal);
                    response.setHnStockoutSkuNum(stockoutSkuNum);
                    response.setHnStockoutEffectAmount(new BigDecimal(stockoutEffectAmount));
                    if(stockoutSkuNum == 0 || skuNumTotal == 0){
                        response.setHnStockoutRate(big);
                    }else {
                        if(skuNumTotal == 0){
                            response.setHnStockoutRate(new BigDecimal(0));
                        }else{
                            response.setHnStockoutRate(new BigDecimal(stockoutSkuNum).
                                    divide(new BigDecimal(skuNumTotal), 4, BigDecimal.ROUND_HALF_UP));
                        }
                    }
                }else if(stock.getTransportCenterCode().equals(Global.XN_CODE)){
                    response.setXnSkuNumTotal(skuNumTotal);
                    response.setXnStockoutSkuNum(stockoutSkuNum);
                    response.setXnStockoutEffectAmount(new BigDecimal(stockoutEffectAmount));
                    if(stockoutSkuNum == 0 || skuNumTotal == 0){
                        response.setXnStockoutRate(big);
                    }else {
                        if(skuNumTotal == 0){
                            response.setXnStockoutRate(new BigDecimal(0));
                        }else{
                            response.setXnStockoutRate(new BigDecimal(stockoutSkuNum).
                                    divide(new BigDecimal(skuNumTotal), 4, BigDecimal.ROUND_HALF_UP));
                        }
                    }
                }else if(stock.getTransportCenterCode().equals(Global.HZ_CODE)){
                    response.setHzSkuNumTotal(skuNumTotal);
                    response.setHzStockoutSkuNum(stockoutSkuNum);
                    response.setHzStockoutEffectAmount(new BigDecimal(stockoutEffectAmount));
                    if(stockoutSkuNum == 0 || skuNumTotal == 0){
                        response.setHzStockoutRate(big);
                    }else {
                        if(skuNumTotal == 0){
                            response.setHzStockoutRate(new BigDecimal(0));
                        }else {
                            response.setHzStockoutRate(new BigDecimal(stockoutSkuNum).
                                    divide(new BigDecimal(skuNumTotal), 4, BigDecimal.ROUND_HALF_UP));
                        }
                    }
                }
            }
            response.setSumSkuNumTotal(sumSkuNumTotal);
            response.setSumStockoutSkuNum(sumStockoutSkuNum);
            response.setSumStockoutEffectAmount(new BigDecimal(sumStockoutEffectAmount));
            if(sumSkuNumTotal == 0 || sumStockoutSkuNum == 0){
                response.setSumStockoutRate(big);
            }else {
                if(sumSkuNumTotal == 0){
                    response.setSumStockoutRate(new BigDecimal(0));
                }else {
                    response.setSumStockoutRate(new BigDecimal(sumStockoutSkuNum).
                            divide(new BigDecimal(sumSkuNumTotal), 4, BigDecimal.ROUND_HALF_UP));
                }
            }
        }
        return response;
    }
}
