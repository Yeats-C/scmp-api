package com.aiqin.bms.scmp.api.statistics.service.impl;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.statistics.dao.*;
import com.aiqin.bms.scmp.api.statistics.domain.StatDeptStoreRepurchaseRate;
import com.aiqin.bms.scmp.api.statistics.domain.response.*;
import com.aiqin.bms.scmp.api.statistics.domain.response.negative.*;
import com.aiqin.bms.scmp.api.statistics.service.StatisticsService;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.druid.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-08-19
 **/
@Service
public class StatisticsServiceImpl implements StatisticsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    @Resource
    private StatDeptStoreRepurchaseRateDao statDeptStoreRepurchaseRateDao;
    @Resource
    private StatComNegativeMarginYearlyDao statComNegativeMarginYearlyDao;
    @Resource
    private StatComNegativeMarginQuarterlyDao statComNegativeMarginQuarterlyDao;
    @Resource
    private StatComNegativeMarginMonthlyDao statComNegativeMarginMonthlyDao;
    @Resource
    private StatDeptNegativeMarginYearlyDao statDeptNegativeMarginYearlyDao;
    @Resource
    private StatDeptNegativeMarginQuarterlyDao statDeptNegativeMarginQuarterlyDao;
    @Resource
    private StatDeptNegativeMarginMonthlyDao statDeptNegativeMarginMonthlyDao;

    private final static int YEAR = 0;
    private final static int QUARTER = 1;
    private final static int MONTH = 2;

    @Override
    public HttpResponse<StoreRepurchaseRateResponse> storeRepurchaseRate(String date, Integer type, String productSortCode){
        if(StringUtils.isEmpty(date) || type == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        StoreRepurchaseRateResponse comRepurchase;
        List<StoreRepurchaseRateSubtotalResponse> deptList = new ArrayList<>();
        StoreRepurchaseRateSubtotalResponse deptRepurchase;
        Long year = Long.valueOf(date.substring(0, 4));
        Long month = Long.valueOf(date.substring(5));
        if(type.equals(Global.COMPANY)){
            // 计算公司合计
            comRepurchase = statDeptStoreRepurchaseRateDao.storeRepurchaseSum(year, month);
            if(comRepurchase != null){
                // 查询该公司部门
                List<StatDeptStoreRepurchaseRate> sortList = statDeptStoreRepurchaseRateDao.storeRepurchaseBySort(year, month);
                for(StatDeptStoreRepurchaseRate sort:sortList){
                    // 计算各部门的小计
                    deptRepurchase = this.deptStoreRate(year, month, sort.getProductSortCode());
                    deptRepurchase.setProductSortCode(sort.getProductSortCode());
                    deptRepurchase.setProductSortName(sort.getProductSortName());
                    deptList.add(deptRepurchase);
                }
                if(comRepurchase.getRepurchaseNum() == null || comRepurchase.getRepurchaseNum() == 0){
                    comRepurchase.setRepurchaseRate(new BigDecimal(0));
                }else {
                    comRepurchase.setRepurchaseRate(new BigDecimal(comRepurchase.getRepurchaseNum()).
                            divide(new BigDecimal(comRepurchase.getPurchaseNum()), 4, BigDecimal.ROUND_HALF_UP));
                }
                comRepurchase.setSumList(deptList);
            }
            return HttpResponse.success(comRepurchase);
        }else {
            if(StringUtils.isEmpty(productSortCode)){
                return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
            }
            deptRepurchase = this.deptStoreRate(year, month, productSortCode);
            return HttpResponse.success(deptRepurchase);
        }
    }

    private StoreRepurchaseRateSubtotalResponse deptStoreRate(Long year, Long month, String productSortCode){
        StoreRepurchaseRateSubtotalResponse deptRepurchase;
        List<StatDeptStoreRepurchaseRate> repurchaseRateList;
        deptRepurchase = statDeptStoreRepurchaseRateDao.storeRepurchaseByDeptSum(year, month, productSortCode);
        if(deptRepurchase != null){
            // 计算部门各省
            repurchaseRateList = statDeptStoreRepurchaseRateDao.storeRepurchaseList(year, month, productSortCode);
            if(CollectionUtils.isNotEmptyCollection(repurchaseRateList)){
                deptRepurchase.setSubtotalList(repurchaseRateList);
            }
            if(deptRepurchase.getRepurchaseNum() == null || deptRepurchase.getRepurchaseNum() == 0){
                deptRepurchase.setRepurchaseRate(new BigDecimal(0));
            }else {
                deptRepurchase.setRepurchaseRate(new BigDecimal(deptRepurchase.getRepurchaseNum()).
                        divide(new BigDecimal(deptRepurchase.getPurchaseNum()), 4, BigDecimal.ROUND_HALF_UP));
            }
        }
        return deptRepurchase;
    }

    @Override
    public HttpResponse<NegativeSumResponse> negativeGross(String date, Integer type, Integer reportType, Long seasonType, String productSortCode){
        if(StringUtils.isEmpty(date) || type == null || reportType == null){
            LOGGER.info("参数缺失");
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 计算公司的负毛利统计
        if(type.equals(Global.COMPANY)){
            NegativeSumResponse sumResponse = new NegativeSumResponse();
            if(reportType.equals(Global.ANNUAL_REPORT)){
                Long year = Long.valueOf(date);
                // 计算公司的负毛利统计- 年报
                sumResponse = this.companyNegative(year, null, sumResponse, YEAR);
            }else if(reportType.equals(Global.QUARTERLY_REPORT)){
                // 计算公司的负毛利统计- 季报
                if(seasonType == null){
                    return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
                }
                Long year = Long.valueOf(date);
                sumResponse = this.companyNegative(year, seasonType, sumResponse, QUARTER);
            }else if(reportType.equals(Global.MONTHLY_REPORT)){
                // 计算公司的负毛利统计- 月报
                Long year = Long.valueOf(date.substring(0, 4));
                Long month = Long.valueOf(date.substring(5));
                sumResponse = this.companyNegative(year, month, sumResponse, MONTH);
            }
            return HttpResponse.success(sumResponse);
        }else {
            NegativeDeptResponse deptResponse = new NegativeDeptResponse();
            if(StringUtils.isEmpty(productSortCode) ){
                return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
            }
            // 计算部门的负毛利统计
            if(reportType.equals(Global.ANNUAL_REPORT)){
                // 计算部门的负毛利统计- 年报
                Long year = Long.valueOf(date);
                deptResponse = this.departmentNegative(year, null, productSortCode, deptResponse, YEAR);
            }else if(reportType.equals(Global.QUARTERLY_REPORT)){
                // 计算部门的负毛利统计- 季报
                if(seasonType == null){
                    return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
                }
                Long year = Long.valueOf(date);
                deptResponse = this.departmentNegative(year, seasonType, productSortCode, deptResponse, QUARTER);
            }else if(reportType.equals(Global.MONTHLY_REPORT)){
                // 计算部门的负毛利统计- 月报
                Long year = Long.valueOf(date.substring(0, 4));
                Long month = Long.valueOf(date.substring(5));
                deptResponse = this.departmentNegative(year, month, productSortCode, deptResponse, MONTH);
            }
            return HttpResponse.success(deptResponse);
        }
    }

    private NegativeSumResponse companyNegative(Long year, Long data, NegativeSumResponse sumResponse, int i){
        List<NegativeDeptResponse> deptList = new ArrayList<>();
        List<CompanyAndDeptResponse> companys;
        List<CompanyAndDeptResponse> departments;
        List<NegativeCompanyResponse> companyList;
        NegativeDeptResponse deptResponse;
        NegativeCompanyResponse companyResponse;
        List<NegativeCategoryResponse> categoryList;
        NegativeRateResponse rateResponse;
        if(i == YEAR){
            sumResponse = statComNegativeMarginYearlyDao.negativeSum(year);
        }else if(i == QUARTER){
            sumResponse = statComNegativeMarginQuarterlyDao.negativeSum(year, data);
        }else{
            sumResponse = statComNegativeMarginMonthlyDao.negativeSum(year, data);
        }
        if(sumResponse != null){
            if(i == YEAR){
                departments = statComNegativeMarginYearlyDao.negativeByDept(year);
            }else if(i == QUARTER){
                departments = statComNegativeMarginQuarterlyDao.negativeByDept(year, data);
            }else {
                departments = statComNegativeMarginMonthlyDao.negativeByDept(year, data);
            }
            if(CollectionUtils.isNotEmptyCollection(departments)){
                for(CompanyAndDeptResponse dept:departments){
                    if(i == YEAR){
                        deptResponse = statComNegativeMarginYearlyDao.negativeDeptSum(year, dept.getProductSortCode());
                        companys = statComNegativeMarginYearlyDao.negativeByCompany(year, dept.getProductSortCode());
                    }else if(i == QUARTER){
                        deptResponse = statComNegativeMarginQuarterlyDao.negativeDeptSum(year, data, dept.getProductSortCode());
                        companys = statComNegativeMarginQuarterlyDao.negativeByCompany(year, data, dept.getProductSortCode());
                    }else {
                        deptResponse = statComNegativeMarginMonthlyDao.negativeDeptSum(year, data, dept.getProductSortCode());
                        companys = statComNegativeMarginMonthlyDao.negativeByCompany(year, data, dept.getProductSortCode());
                    }
                    if(CollectionUtils.isNotEmptyCollection(companys)){
                        companyList = new ArrayList<>();
                        for (CompanyAndDeptResponse company:companys){
                            if(i == YEAR){
                                companyResponse = statComNegativeMarginYearlyDao.negativeCompanySum(year, company.getPriceChannelCode(),
                                        company.getProductSortCode());
                            }else if(i == QUARTER){
                                companyResponse = statComNegativeMarginQuarterlyDao.negativeCompanySum(year, data, company.getPriceChannelCode(),
                                        company.getProductSortCode());
                            } else{
                                companyResponse = statComNegativeMarginMonthlyDao.negativeCompanySum(year, data, company.getPriceChannelCode(),
                                        company.getProductSortCode());
                            }
                            if(companyResponse != null){
                                if(i == YEAR) {
                                    categoryList = statComNegativeMarginYearlyDao.negativeCategoryList(year, company.getPriceChannelCode(),
                                            company.getProductSortCode());
                                }else if(i == QUARTER){
                                        categoryList = statComNegativeMarginQuarterlyDao.negativeCategoryList(year, data, company.getPriceChannelCode(),
                                                company.getProductSortCode());
                                }else {
                                        categoryList = statComNegativeMarginMonthlyDao.negativeCategoryList(year, data, company.getPriceChannelCode(),
                                                company.getProductSortCode());
                                }
                                companyResponse.setCategoryList(categoryList);
                                rateResponse = new NegativeRateResponse();
                                BeanUtils.copyProperties(companyResponse, rateResponse);
                                NegativeRateResponse rate = this.negativeRate(rateResponse);
                                BeanUtils.copyProperties(rate, companyResponse);
                                companyList.add(companyResponse);
                            }
                        }
                        rateResponse = new NegativeRateResponse();
                        BeanUtils.copyProperties(deptResponse, rateResponse);
                        NegativeRateResponse rate = this.negativeRate(rateResponse);
                        BeanUtils.copyProperties(rate, deptResponse);
                        deptResponse.setCompanyList(companyList);
                        deptList.add(deptResponse);
                    }
                }
                rateResponse = new NegativeRateResponse();
                BeanUtils.copyProperties(sumResponse, rateResponse);
                NegativeRateResponse rate = this.negativeRate(rateResponse);
                BeanUtils.copyProperties(rate, sumResponse);
                sumResponse.setDeptList(deptList);
            }
        }
        return sumResponse;
    }

    private NegativeDeptResponse departmentNegative(Long year, Long data, String productSortCode, NegativeDeptResponse deptResponse, int i) {
        List<CompanyAndDeptResponse> companys;
        List<NegativeCompanyResponse> companyList;
        NegativeCompanyResponse companyResponse;
        List<NegativeCategoryResponse> categoryList;
        NegativeRateResponse rateResponse;
        if (i == YEAR) {
            deptResponse = statDeptNegativeMarginYearlyDao.negativeDeptSum(year, productSortCode);
            companys = statDeptNegativeMarginYearlyDao.negativeByCompany(year, productSortCode);
        } else if (i == QUARTER) {
            deptResponse = statDeptNegativeMarginQuarterlyDao.negativeDeptSum(year, data, productSortCode);
            companys = statDeptNegativeMarginQuarterlyDao.negativeByCompany(year, data, productSortCode);
        } else {
            deptResponse = statDeptNegativeMarginMonthlyDao.negativeDeptSum(year, data, productSortCode);
            companys = statDeptNegativeMarginMonthlyDao.negativeByCompany(year, data, productSortCode);
        }
        if (CollectionUtils.isNotEmptyCollection(companys)) {
            companyList = new ArrayList<>();
            for (CompanyAndDeptResponse company : companys) {
                if (i == YEAR) {
                    companyResponse = statDeptNegativeMarginYearlyDao.negativeCompanySum(year, company.getPriceChannelCode(),
                            productSortCode);
                } else if (i == QUARTER) {
                    companyResponse = statDeptNegativeMarginQuarterlyDao.negativeCompanySum(year, data, company.getPriceChannelCode(),
                            productSortCode);
                } else {
                    companyResponse = statDeptNegativeMarginMonthlyDao.negativeCompanySum(year, data, company.getPriceChannelCode(),
                            productSortCode);
                }
                if (companyResponse != null) {
                    if (i == YEAR) {
                        categoryList = statDeptNegativeMarginYearlyDao.negativeCategoryList(year, company.getPriceChannelCode(),
                                productSortCode);
                    } else if (i == QUARTER) {
                        categoryList = statDeptNegativeMarginQuarterlyDao.negativeCategoryList(year, data, company.getPriceChannelCode(),
                                company.getProductSortCode());
                    } else {
                        categoryList = statDeptNegativeMarginMonthlyDao.negativeCategoryList(year, data, company.getPriceChannelCode(),
                                company.getProductSortCode());
                    }
                    companyResponse.setCategoryList(categoryList);
                    rateResponse = new NegativeRateResponse();
                    BeanUtils.copyProperties(companyResponse, rateResponse);
                    NegativeRateResponse rate = this.negativeRate(rateResponse);
                    BeanUtils.copyProperties(rate, companyResponse);
                    companyList.add(companyResponse);
                }
            }
            rateResponse = new NegativeRateResponse();
            BeanUtils.copyProperties(deptResponse, rateResponse);
            NegativeRateResponse rate = this.negativeRate(rateResponse);
            BeanUtils.copyProperties(rate, deptResponse);
            deptResponse.setCompanyList(companyList);
        }
        return deptResponse;
    }

    private NegativeRateResponse negativeRate(NegativeRateResponse rateResponse){
        // 渠道销售金额同比
        BigDecimal big = new BigDecimal(0);
        Long rate = 0L;
        Long channelSalesAmount = rateResponse.getChannelSalesAmount() == null ? rate : rateResponse.getChannelSalesAmount();
        Long preChannelSalesAmount = rateResponse.getPreChannelSalesAmount() == null ? rate : rateResponse.getPreChannelSalesAmount();
        if(channelSalesAmount == rate || preChannelSalesAmount == rate){
            rateResponse.setChannelSalesAmountYearonyear(big);
        }else {
            rateResponse.setChannelSalesAmountYearonyear(new BigDecimal(channelSalesAmount).
                    divide(new BigDecimal(preChannelSalesAmount), 4, BigDecimal.ROUND_HALF_UP));
        }
        // 渠道毛利同比
        Long channelMargin = rateResponse.getChannelMargin() == null ? rate : rateResponse.getChannelMargin();
        Long preChannelMargin = rateResponse.getPreChannelMargin() == null ? rate : rateResponse.getPreChannelMargin();
        if(channelMargin == rate || preChannelMargin == rate){
            rateResponse.setChannelMarginYearonyear(big);
        }else {
            rateResponse.setChannelMarginYearonyear(new BigDecimal(channelMargin).
                    divide(new BigDecimal(preChannelMargin), 4, BigDecimal.ROUND_HALF_UP));
        }

        // 分销销售额同比
        Long distributionSalesAmount = rateResponse.getDistributionSalesAmount() == null ? rate : rateResponse.getDistributionSalesAmount();
        Long preDistributionSalesAmount = rateResponse.getPreDistributionSalesAmount() == null ? rate : rateResponse.getPreDistributionSalesAmount();
        if(distributionSalesAmount == rate || preDistributionSalesAmount == rate){
            rateResponse.setDistributionSalesAmountYearonyear(big);
        }else {
            rateResponse.setDistributionSalesAmountYearonyear(new BigDecimal(distributionSalesAmount).
                    divide(new BigDecimal(preDistributionSalesAmount), 4, BigDecimal.ROUND_HALF_UP));
        }

        // 分销毛利同比
        Long distributionMargin = rateResponse.getDistributionMargin() == null ? rate : rateResponse.getDistributionMargin();
        Long preDistributionMargin = rateResponse.getPreDistributionMargin() == null ? rate : rateResponse.getPreDistributionMargin();
        if(distributionMargin == rate || preDistributionMargin == null){
            rateResponse.setDistributionMarginYearonyear(big);
        }else {
            rateResponse.setDistributionMarginYearonyear(new BigDecimal(distributionMargin).
                    divide(new BigDecimal(preDistributionMargin), 4, BigDecimal.ROUND_HALF_UP));
        }
        return rateResponse;
    }

}
