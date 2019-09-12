package com.aiqin.bms.scmp.api.statistics.service.impl;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.statistics.dao.StatComNewProductMovingRateDao;
import com.aiqin.bms.scmp.api.statistics.dao.StatDeptNewProductMovingRateDao;
import com.aiqin.bms.scmp.api.statistics.domain.request.ProductRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.product.MovableResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.product.NewProductMovingRateResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.product.ProductMovableResponse;
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
    private StatComNewProductMovingRateDao statComNewProductMovingRateDao;
    @Resource
    private StatDeptNewProductMovingRateDao statDeptNewProductMovingRateDao;

    @Override
    public HttpResponse<ProductMovableResponse> productMovable(ProductRequest request){
        if(request == null || StringUtils.isBlank(request.getDate()) || request.getType() == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        Long year = Long.valueOf(request.getDate().substring(0, 4));
        Long month = Long.valueOf(request.getDate().substring(5));
        request.setYear(year);
        request.setMonth(month);
        ProductMovableResponse response = new ProductMovableResponse();
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
                deptResponse.setSubsetList(categorySum);
            }
        }
        return deptResponse;
    }

    private ProductMovableResponse movablePin(List<NewProductMovingRateResponse> movingList){
        ProductMovableResponse response = new ProductMovableResponse();
        if(CollectionUtils.isNotEmpty(movingList)){
            Long num = 0L;
            BigDecimal big = new BigDecimal(0);
            Long sumIniStockSkuNum = 0L, sumIniStockSkuCost = 0L, sumMidPurchaseSkuNum = 0L, sumMidSalesSkuNum = 0L,
                    sumChannelSalesAmount = 0L, sumDistributionsalesAmount = 0L;
            for(NewProductMovingRateResponse mov:movingList){
                Long iniStockSkuNum = mov.getIniStockSkuNum() == null ? num : mov.getIniStockSkuNum();
                Long iniStockSkuCost = mov.getIniStockSkuCost() == null ? num : mov.getIniStockSkuCost();
                Long midPurchaseSkuNum = mov.getMidPurchaseSkuNum() == null ? num : mov.getMidPurchaseSkuNum();
                Long midSalesSkuNum = mov.getMidSalesSkuNum() == null ? num : mov.getMidSalesSkuNum();
                Long channelSalesAmount = mov.getChannelSalesAmount() == null ? num : mov.getChannelSalesAmount();
                Long distributionsalesAmount = mov.getDistributionsalesAmount() == null ? num : mov.getDistributionsalesAmount();
                sumIniStockSkuNum += iniStockSkuNum;
                sumIniStockSkuCost += iniStockSkuCost;
                sumMidPurchaseSkuNum += midPurchaseSkuNum;
                sumMidSalesSkuNum += midSalesSkuNum;
                sumChannelSalesAmount += channelSalesAmount;
                sumDistributionsalesAmount += distributionsalesAmount;
                Long newSkuNum = iniStockSkuNum + midPurchaseSkuNum;
                if(mov.getTransportCenterCode().equals(Global.HB_CODE)){
                    response.setHbIniStockSkuNum(iniStockSkuNum);
                    response.setHbIniStockSkuCost(iniStockSkuCost);
                    response.setHbMidPurchaseSkuNum(midPurchaseSkuNum);
                    response.setHbMidSalesSkuNum(midSalesSkuNum);
                    response.setHbChannelSalesAmount(channelSalesAmount);
                    response.setHbDistributionsalesAmount(distributionsalesAmount);
                    if(midSalesSkuNum == num || newSkuNum == num){
                        response.setHbNewProMovingSalesRate(big);
                    }else {
                        response.setHbNewProMovingSalesRate(new BigDecimal(midSalesSkuNum).divide(new BigDecimal(newSkuNum), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }
                if(mov.getTransportCenterCode().equals(Global.HD_CODE)){
                    response.setHdIniStockSkuNum(iniStockSkuNum);
                    response.setHdIniStockSkuCost(iniStockSkuCost);
                    response.setHdMidPurchaseSkuNum(midPurchaseSkuNum);
                    response.setHdMidSalesSkuNum(midSalesSkuNum);
                    response.setHdChannelSalesAmount(channelSalesAmount);
                    response.setHdDistributionsalesAmount(distributionsalesAmount);
                    if(midSalesSkuNum == num || newSkuNum == num){
                        response.setHdNewProMovingSalesRate(big);
                    }else {
                        response.setHdNewProMovingSalesRate(new BigDecimal(midSalesSkuNum).divide(new BigDecimal(newSkuNum), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }
                if(mov.getTransportCenterCode().equals(Global.HN_CODE)){
                    response.setHnIniStockSkuNum(iniStockSkuNum);
                    response.setHnIniStockSkuCost(iniStockSkuCost);
                    response.setHnMidPurchaseSkuNum(midPurchaseSkuNum);
                    response.setHnMidSalesSkuNum(midSalesSkuNum);
                    response.setHnChannelSalesAmount(channelSalesAmount);
                    response.setHnDistributionsalesAmount(distributionsalesAmount);
                    if(midSalesSkuNum == num || newSkuNum == num){
                        response.setHnNewProMovingSalesRate(big);
                    }else {
                        response.setHnNewProMovingSalesRate(new BigDecimal(midSalesSkuNum).divide(new BigDecimal(newSkuNum), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }
                if(mov.getTransportCenterCode().equals(Global.XN_CODE)){
                    response.setXnIniStockSkuNum(iniStockSkuNum);
                    response.setXnIniStockSkuCost(iniStockSkuCost);
                    response.setXnMidPurchaseSkuNum(midPurchaseSkuNum);
                    response.setXnMidSalesSkuNum(midSalesSkuNum);
                    response.setXnChannelSalesAmount(channelSalesAmount);
                    response.setXnDistributionsalesAmount(distributionsalesAmount);
                    if(midSalesSkuNum == num || newSkuNum == num){
                        response.setXnNewProMovingSalesRate(big);
                    }else {
                        response.setXnNewProMovingSalesRate(new BigDecimal(midSalesSkuNum).divide(new BigDecimal(newSkuNum), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }
                if(mov.getTransportCenterCode().equals(Global.HZ_CODE)){
                    response.setHzIniStockSkuNum(iniStockSkuNum);
                    response.setHzIniStockSkuCost(iniStockSkuCost);
                    response.setHzMidPurchaseSkuNum(midPurchaseSkuNum);
                    response.setHzMidSalesSkuNum(midSalesSkuNum);
                    response.setHzChannelSalesAmount(channelSalesAmount);
                    response.setHzDistributionsalesAmount(distributionsalesAmount);
                    if(midSalesSkuNum == num || newSkuNum == num){
                        response.setHzNewProMovingSalesRate(big);
                    }else {
                        response.setHzNewProMovingSalesRate(new BigDecimal(midSalesSkuNum).divide(new BigDecimal(newSkuNum), 4, BigDecimal.ROUND_HALF_UP));
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
            if(sumMidSalesSkuNum == num || sumNewSkuNum == num){
                response.setSumNewProMovingSalesRate(big);
            }else {
                response.setSumNewProMovingSalesRate(new BigDecimal(sumMidSalesSkuNum).divide(new BigDecimal(sumNewSkuNum), 4, BigDecimal.ROUND_HALF_UP));
            }
        }
        return response;
    }
}
