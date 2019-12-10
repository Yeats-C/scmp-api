package com.aiqin.bms.scmp.api.statistics.service.impl;

import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.statistics.dao.*;
import com.aiqin.bms.scmp.api.statistics.domain.request.OemSaleRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.CompanyAndDeptResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.oem.OemSaleInfoResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.oem.OemSaleResponse;
import com.aiqin.bms.scmp.api.statistics.service.OemSaleService;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-09-18
 **/
@Service
public class OemSaleServiceImpl implements OemSaleService {

    @Resource
    private StatOemCateSalesWeeklyDao statOemCateSalesWeeklyDao;
    @Resource
    private StatOemCateSalesMonthlyDao statOemCateSalesMonthlyDao;
    @Resource
    private StatOemCateSalesQuarterlyDao statOemCateSalesQuarterlyDao;
    @Resource
    private StatOemCateSalesYearlyDao statOemCateSalesYearlyDao;
    @Resource
    private StatOemBrandSalesWeeklyDao statOemBrandSalesWeeklyDao;
    @Resource
    private StatOemBrandSalesMonthlyDao statOemBrandSalesMonthlyDao;
    @Resource
    private StatOemBrandSalesQuarterlyDao statOemBrandSalesQuarterlyDao;
    @Resource
    private StatOemBrandSalesYearlyDao statOemBrandSalesYearlyDao;

    private final static int CATE = 0;
    private final static int BRAND = 1;

    @Override
    public OemSaleResponse oemSale(OemSaleRequest request){
        OemSaleResponse response = new OemSaleResponse();
        if(request == null || StringUtils.isBlank(request.getDate()) || request.getSaleType() == null || request.getReportType() == null){
            return response;
        }
        Long year;
        OemSaleInfoResponse oemSum;
        OemSaleInfoResponse companySum;
        List<OemSaleInfoResponse> list;
        List<OemSaleInfoResponse> categoryList;
        if(request.getReportType().equals(Global.ANNUAL_REPORT)){
            year = Long.valueOf(request.getDate());
        }else if(request.getReportType().equals(Global.QUARTERLY_REPORT)){
            year = Long.valueOf(request.getDate().substring(0, 4));
            Long month = Long.valueOf(request.getDate().substring(5));
            request.setMonth(month);
        }else if(request.getReportType().equals(Global.MONTHLY_REPORT)){
            year = Long.valueOf(request.getDate().substring(0, 4));
            Long month = Long.valueOf(request.getDate().substring(5));
            request.setMonth(month);
        }else {
            year = Long.valueOf(request.getDate().substring(0, 4));
            Long month = Long.valueOf(request.getDate().substring(5));
            request.setMonth(month);
        }
        request.setYear(year);
        // 计算OEM总计
        oemSum = this.oemSum(request);
        if(oemSum != null){
            this.oemSaleRate(oemSum);
            response.setOemSum(oemSum);
            list = this.category1(request);
            if(CollectionUtils.isNotEmptyCollection(list)){
                for(OemSaleInfoResponse info:list){
                    this.oemSaleRate(info);
                    if(StringUtils.isNotBlank(info.getLv1())){
                        request.setLv1(info.getLv1());
                        categoryList = this.category2(request);
                        if(CollectionUtils.isNotEmptyCollection(categoryList)){
                            info.setSubsetList(categoryList);
                        }
                    }
                }
                response.setSubsetList(list);
            }
        }
        // 计算公司总计
        companySum = this.companySum(request);
        if(companySum != null){
            this.oemSaleRate(companySum);
        }
        response.setCompanySum(companySum);
        return response;
    }

    private OemSaleInfoResponse oemSum(OemSaleRequest request){
        OemSaleInfoResponse response;
        if(request.getSaleType().equals(CATE)){
            if (request.getReportType().equals(Global.ANNUAL_REPORT)) {
                response = statOemCateSalesYearlyDao.oemSaleCateSum(request);
            }else if(request.getReportType().equals(Global.QUARTERLY_REPORT)){
                response = statOemCateSalesQuarterlyDao.oemSaleCateSum(request);
            }else if(request.getReportType().equals(Global.MONTHLY_REPORT)){
                response = statOemCateSalesMonthlyDao.oemSaleCateSum(request);
            }else {
                response = statOemCateSalesWeeklyDao.oemSaleCateSum(request);
            }
        }else {
            if (request.getReportType().equals(Global.ANNUAL_REPORT)) {
                response = statOemBrandSalesYearlyDao.oemSaleBrandSum(request);
            }else if(request.getReportType().equals(Global.QUARTERLY_REPORT)){
                response = statOemBrandSalesQuarterlyDao.oemSaleBrandSum(request);
            }else if(request.getReportType().equals(Global.MONTHLY_REPORT)){
                response = statOemBrandSalesMonthlyDao.oemSaleBrandSum(request);
            }else {
                response = statOemBrandSalesWeeklyDao.oemSaleBrandSum(request);
            }
        }
        return response;
    }

    private OemSaleInfoResponse companySum(OemSaleRequest request){
        OemSaleInfoResponse response;
        if(request.getSaleType().equals(CATE)){
            if (request.getReportType().equals(Global.ANNUAL_REPORT)) {
                response = statOemCateSalesYearlyDao.companySaleCateSum(request);
            }else if(request.getReportType().equals(Global.QUARTERLY_REPORT)){
                response = statOemCateSalesQuarterlyDao.companySaleCateSum(request);
            }else if(request.getReportType().equals(Global.MONTHLY_REPORT)){
                response = statOemCateSalesMonthlyDao.companySaleCateSum(request);
            }else {
                response = statOemCateSalesWeeklyDao.companySaleCateSum(request);
            }
        }else {
            if (request.getReportType().equals(Global.ANNUAL_REPORT)) {
                response = statOemBrandSalesYearlyDao.companySaleBrandSum(request);
            }else if(request.getReportType().equals(Global.QUARTERLY_REPORT)){
                response = statOemBrandSalesQuarterlyDao.companySaleBrandSum(request);
            }else if(request.getReportType().equals(Global.MONTHLY_REPORT)){
                response = statOemBrandSalesMonthlyDao.companySaleBrandSum(request);
            }else {
                response = statOemBrandSalesWeeklyDao.companySaleBrandSum(request);
            }
        }
        return response;
    }

    private List<OemSaleInfoResponse>  category1(OemSaleRequest request){
        List<OemSaleInfoResponse> list;
        if(request.getSaleType().equals(CATE)){
            // 查询大分类
            if (request.getReportType().equals(Global.ANNUAL_REPORT)) {
                list = statOemCateSalesYearlyDao.oemSaleCategory(request);
            }else if(request.getReportType().equals(Global.QUARTERLY_REPORT)){
                list = statOemCateSalesQuarterlyDao.oemSaleCategory(request);
            }else if(request.getReportType().equals(Global.MONTHLY_REPORT)){
                list = statOemCateSalesMonthlyDao.oemSaleCategory(request);
            }else {
                list = statOemCateSalesWeeklyDao.oemSaleCategory(request);
            }
        }else {
            if (request.getReportType().equals(Global.ANNUAL_REPORT)) {
                list = statOemBrandSalesYearlyDao.oemBrandeCategory(request);
            }else if(request.getReportType().equals(Global.QUARTERLY_REPORT)){
                list = statOemBrandSalesQuarterlyDao.oemBrandeCategory(request);
            }else if(request.getReportType().equals(Global.MONTHLY_REPORT)){
                list = statOemBrandSalesMonthlyDao.oemBrandeCategory(request);
            }else {
                list = statOemBrandSalesWeeklyDao.oemBrandeCategory(request);
            }
        }
        return list;
    }

    private List<OemSaleInfoResponse>  category2(OemSaleRequest request){
        List<OemSaleInfoResponse> list;
        if(request.getSaleType().equals(CATE)){
            // 查询大分类
            if (request.getReportType().equals(Global.ANNUAL_REPORT)) {
                list = statOemCateSalesYearlyDao.oemSaleCategory2(request);
            }else if(request.getReportType().equals(Global.QUARTERLY_REPORT)){
                list = statOemCateSalesQuarterlyDao.oemSaleCategory2(request);
            }else if(request.getReportType().equals(Global.MONTHLY_REPORT)){
                list = statOemCateSalesMonthlyDao.oemSaleCategory2(request);
            }else {
                list = statOemCateSalesWeeklyDao.oemSaleCategory2(request);
            }
        }else {
            if (request.getReportType().equals(Global.ANNUAL_REPORT)) {
                list = statOemBrandSalesYearlyDao.oemBrandCategory2(request);
            }else if(request.getReportType().equals(Global.QUARTERLY_REPORT)){
                list = statOemBrandSalesQuarterlyDao.oemBrandCategory2(request);
            }else if(request.getReportType().equals(Global.MONTHLY_REPORT)){
                list = statOemBrandSalesMonthlyDao.oemBrandCategory2(request);
            }else {
                list = statOemBrandSalesWeeklyDao.oemBrandCategory2(request);
            }
        }
        return list;
    }

    private OemSaleInfoResponse oemSaleRate(OemSaleInfoResponse response){
        // 计算OEM销售数据
        if(response != null){
            Long num = 0L;
            BigDecimal big = new BigDecimal(0);
            BigDecimal subtract = new BigDecimal(1);
             // 销售数量
            Long salesNum = response.getSalesNum() == null ? num : response.getSalesNum();
            Long preSalesNum = response.getPreSalesNum() == null ? num : response.getPreSalesNum();
            if(salesNum == num || preSalesNum == num){
                response.setSalesNumWow(big);
            }else {
                response.setSalesNumWow(new BigDecimal(salesNum).divide(new BigDecimal(preSalesNum), 4, BigDecimal.ROUND_HALF_UP).subtract(subtract));
            }
            // 含税销售金额
            Long salesAmount = response.getSalesAmount() == null ? num : response.getSalesAmount().longValue();
            Long preSalesAmount = response.getPreSalesAmount() == null ? num : response.getPreSalesAmount().longValue();
            response.setSalesAmountVs(new BigDecimal(salesAmount).subtract(new BigDecimal(preSalesAmount)));
            if(salesAmount == num || preSalesAmount == num){
                response.setSalesAmountWow(big);
            }else {
                response.setSalesAmountWow(new BigDecimal(salesAmount).divide(new BigDecimal(preSalesAmount), 4, BigDecimal.ROUND_HALF_UP).subtract(subtract));
            }
            // 分类销售占比
            Long comSalesAmount = response.getComSalesAmount() == null ? num : response.getComSalesAmount().longValue();
            Long preComSalesAmount = response.getPreComSalesAmount() == null ? num : response.getPreComSalesAmount().longValue();
            if(salesAmount == num || comSalesAmount == num){
                response.setSalesRate(big);
            }else {
                response.setSalesRate(new BigDecimal(salesAmount).divide(new BigDecimal(comSalesAmount), 4, BigDecimal.ROUND_HALF_UP));
            }
            if(preSalesAmount == num || preComSalesAmount == num){
                response.setPreSalesRate(big);
            }else {
                response.setPreSalesRate(new BigDecimal(preSalesAmount).divide(new BigDecimal(preComSalesAmount), 4, BigDecimal.ROUND_HALF_UP));
            }
            response.setSalesRateWow(response.getSalesRate().subtract(response.getPreSalesRate()));
            // 含税毛利额
            Long salesMargin = response.getSalesMargin() == null ? num : response.getSalesMargin().longValue();
            Long preSalesMargin = response.getPreSalesMargin() == null ? num : response.getPreSalesMargin().longValue();
            if(salesMargin == num || preSalesMargin == num){
                response.setSalesMarginWow(big);
            }else {
                response.setSalesMarginWow(new BigDecimal(salesMargin).divide(new BigDecimal(preSalesMargin), 4, BigDecimal.ROUND_HALF_UP).subtract(subtract));
            }
            // 分类毛利占比
            Long comSalesMargin = response.getComSalesMargin() == null ? num : response.getComSalesMargin().longValue();
            Long preComSalesMargin = response.getPreComSalesMargin() == null ? num : response.getPreComSalesMargin().longValue();
            if(salesMargin == num || comSalesMargin == num){
                response.setSalesMarginRatio(big);
            }else {
                response.setSalesMarginRatio(new BigDecimal(salesMargin).divide(new BigDecimal(comSalesMargin), 4, BigDecimal.ROUND_HALF_UP));
            }
            if(preSalesMargin == num || preComSalesMargin == num){
                response.setPreSalesMarginRatio(big);
            }else {
                response.setPreSalesMarginRatio(new BigDecimal(preSalesMargin).divide(new BigDecimal(preComSalesMargin), 4, BigDecimal.ROUND_HALF_UP));
            }
            response.setSalesMarginRatioWow(response.getSalesMarginRatio().subtract(response.getPreSalesMarginRatio()));
            // 毛利率
            if(salesMargin == num || salesAmount == num){
                response.setSalesMarginRate(big);
            }else {
                response.setSalesMarginRate(new BigDecimal(salesMargin).divide(new BigDecimal(salesAmount), 4, BigDecimal.ROUND_HALF_UP));
            }
            if(preSalesMargin == num || preSalesAmount == num){
                response.setPreSalesMarginRate(big);
            }else {
                response.setPreSalesMarginRate(new BigDecimal(preSalesMargin).divide(new BigDecimal(preSalesAmount), 4, BigDecimal.ROUND_HALF_UP));
            }
            response.setSalesMarginRateWow(response.getSalesMarginRate().subtract(response.getPreSalesMarginRate()));
        }
        return response;
    }
}
