package com.aiqin.bms.scmp.api.statistics.service.impl;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.statistics.dao.*;
import com.aiqin.bms.scmp.api.statistics.domain.request.SaleRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.CompanyAndDeptResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.category.CategoryResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.sale.*;
import com.aiqin.bms.scmp.api.statistics.dao.StatDeptSalesMonthlyDao;
import com.aiqin.bms.scmp.api.statistics.dao.StatDeptSalesYearlyDao;
import com.aiqin.bms.scmp.api.statistics.service.SalesStatisticsService;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-09-03
 **/
@Service
public class SalesStatisticsServiceImpl implements SalesStatisticsService {

    @Resource
    private StatDeptSalesYearlyDao statDeptSalesYearlyDao;
    @Resource
    private StatDeptSalesMonthlyDao statDeptSalesMonthlyDao;
    @Resource
    private StatDeptMonthAccSalesDao statDeptMonthAccSalesDao;
    @Resource
    private StatDeptCategorySalesDao statDeptCategorySalesDao;

    private final static int YEAR = 0;
    private final static int MONTH = 2;

    @Override
    public HttpResponse<SaleSumResponse> saleInfo(SaleRequest saleRequest) {
        if (saleRequest == null || StringUtils.isBlank(saleRequest.getDate()) || saleRequest.getType() == null ||
                saleRequest.getReportType() == null) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        if (saleRequest.getType().equals(Global.COMPANY)) {
            SaleSumResponse sumResponse = new SaleSumResponse();
            // 销售统计 - 公司
            if (saleRequest.getReportType().equals(Global.ANNUAL_REPORT)) {
                // 公司 - 年报
                Long year = Long.valueOf(saleRequest.getDate());
                saleRequest.setYear(year);
                sumResponse = this.companySale(saleRequest, YEAR);
            } else if (saleRequest.getReportType().equals(Global.MONTHLY_REPORT)) {
                // 公司 - 月报
                Long year = Long.valueOf(saleRequest.getDate().substring(0, 4));
                Long month = Long.valueOf(saleRequest.getDate().substring(5));
                saleRequest.setYear(year);
                saleRequest.setMonth(month);
                sumResponse = this.companySale(saleRequest, MONTH);
            }
            return HttpResponse.success(sumResponse);
        } else if (saleRequest.getType().equals(Global.DEPARTMENT)) {
            if (StringUtils.isBlank(saleRequest.getProductSortCode())) {
                return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
            }
            SaleDeptResponse deptResponse = new SaleDeptResponse();
            // 销售统计 - 部门
            if (saleRequest.getReportType().equals(Global.ANNUAL_REPORT)) {
                // 部门 - 年报
                Long year = Long.valueOf(saleRequest.getDate());
                saleRequest.setYear(year);
                deptResponse = this.deptSale(saleRequest, YEAR);
            } else if (saleRequest.getReportType().equals(Global.MONTHLY_REPORT)) {
                // 部门 - 月报
                Long year = Long.valueOf(saleRequest.getDate().substring(0, 4));
                Long month = Long.valueOf(saleRequest.getDate().substring(5));
                saleRequest.setYear(year);
                saleRequest.setMonth(month);
                deptResponse = this.deptSale(saleRequest, MONTH);
                if(deptResponse != null){
                    deptResponse.setChanneRate(new BigDecimal(1));
                    deptResponse.setDistributionRate(new BigDecimal(1));
                }
            }
            return HttpResponse.success(deptResponse);
        }
        return HttpResponse.success();
    }

    private SaleSumResponse companySale(SaleRequest saleRequest, int i) {
        SaleSumResponse sumResponse;
        List<SaleDeptResponse> deptList = Lists.newArrayList();
        List<CompanyAndDeptResponse> departments;
        SaleDeptResponse deptResponse;
        SaleResponse saleResponse;
        if (i == YEAR) {
            sumResponse = statDeptSalesYearlyDao.saleSum(saleRequest);
        } else {
            sumResponse = statDeptSalesYearlyDao.saleSum(saleRequest);
        }
        if (sumResponse != null) {
            if (i == YEAR) {
                departments = statDeptSalesYearlyDao.saleByDept(saleRequest);
            } else {
                departments = statDeptSalesYearlyDao.saleByDept(saleRequest);
            }
            if (CollectionUtils.isNotEmptyCollection(departments)) {
                for (CompanyAndDeptResponse dept : departments) {
                    if(dept != null){
                        saleRequest.setProductSortCode(dept.getProductSortCode());
                        deptResponse = this.deptSale(saleRequest, i);
                        if(deptResponse != null){
                            if(sumResponse.getChannelSalesAmount().longValue() == 0){
                                deptResponse.setChanneRate(new BigDecimal(0));
                            }else{
                                deptResponse.setChanneRate(deptResponse.getChannelSalesAmount().
                                        divide(sumResponse.getChannelSalesAmount(), 4, BigDecimal.ROUND_HALF_UP));
                            }
                            if(sumResponse.getDistributionSalesAmount().longValue() == 0){
                                deptResponse.setDistributionRate(new BigDecimal(0));
                            }else{
                                deptResponse.setDistributionRate(deptResponse.getDistributionSalesAmount().
                                        divide(sumResponse.getDistributionSalesAmount(), 4, BigDecimal.ROUND_HALF_UP));
                            }
                        }
                        deptList.add(deptResponse);
                    }
                }
            }
            saleResponse = new SaleResponse();
            BeanUtils.copyProperties(sumResponse, saleResponse);
            SaleResponse rate = this.saleRate(saleResponse, 1);
            BeanUtils.copyProperties(rate, sumResponse);
            sumResponse.setDeptList(deptList);
            sumResponse.setChanneRate(new BigDecimal(1));
            sumResponse.setDistributionRate(new BigDecimal(1));
        }
        return sumResponse;
    }

    private SaleDeptResponse deptSale(SaleRequest saleRequest, int i) {
        List<CompanyAndDeptResponse> companys;
        List<SaleCompanyResponse> companyList;
        List<SaleStoreResponse> saleList;
        SaleDeptResponse deptResponse;
        SaleCompanyResponse companyResponse;
        SaleResponse saleResponse;
        if(i == YEAR){
            deptResponse = statDeptSalesYearlyDao.saleSumDept(saleRequest);
            companys = statDeptSalesYearlyDao.saleByCompany(saleRequest);
        }else if(i == MONTH){
            deptResponse = statDeptSalesMonthlyDao.saleSumDept(saleRequest);
            companys = statDeptSalesMonthlyDao.saleByCompany(saleRequest);
        }else if(i == 3){
            deptResponse = statDeptMonthAccSalesDao.saleSumDept(saleRequest);
            companys = statDeptMonthAccSalesDao.saleByCompany(saleRequest);
        }else {
            deptResponse = null;
            companys = null;
        }
        if (CollectionUtils.isNotEmptyCollection(companys)) {
            companyList = Lists.newArrayList();
            for (CompanyAndDeptResponse company : companys) {
                saleRequest.setPriceChannelCode(company.getPriceChannelCode());
                if(i == YEAR){
                    companyResponse = statDeptSalesYearlyDao.saleSumCompany(saleRequest);
                }else if(i == MONTH){
                    companyResponse = statDeptSalesMonthlyDao.saleSumCompany(saleRequest);
                }else if(i == 3){
                    companyResponse = statDeptMonthAccSalesDao.saleSumCompany(saleRequest);
                }else {
                    companyResponse = null;
                }
                if (companyResponse != null) {
                    if(i == YEAR){
                        saleList = statDeptSalesYearlyDao.saleStoreList(saleRequest);
                    }else if(i == MONTH){
                        saleList = statDeptSalesMonthlyDao.saleStoreList(saleRequest);
                    }else if(i == 3){
                        saleList = statDeptMonthAccSalesDao.saleStoreList(saleRequest);
                    }else {
                        saleList = null;
                    }
                    saleResponse = new SaleResponse();
                    this.saleList(saleList, saleResponse, companyResponse);
                    companyResponse.setStoreList(saleList);
                    saleResponse = new SaleResponse();
                    BeanUtils.copyProperties(companyResponse, saleResponse);
                    SaleResponse rate = this.saleRate(saleResponse, 0);
                    BeanUtils.copyProperties(rate, companyResponse);
                    if(deptResponse.getChannelSalesAmount().longValue() == 0){
                        companyResponse.setChanneRate(new BigDecimal(0));
                    }else {
                        companyResponse.setChanneRate(companyResponse.getChannelSalesAmount().
                                divide(deptResponse.getChannelSalesAmount(), 4, BigDecimal.ROUND_HALF_UP));
                    }
                    if(deptResponse.getDistributionSalesAmount().longValue() == 0){
                        companyResponse.setDistributionRate(new BigDecimal(0));
                    }else {
                        companyResponse.setDistributionRate(companyResponse.getDistributionSalesAmount().
                                divide(deptResponse.getDistributionSalesAmount(), 4, BigDecimal.ROUND_HALF_UP));
                    }
                    companyList.add(companyResponse);
                }
            }
            deptResponse.setCompanyList(companyList);
            saleResponse = new SaleResponse();
            BeanUtils.copyProperties(deptResponse, saleResponse);
            SaleResponse rate = this.saleRate(saleResponse, 1);
            BeanUtils.copyProperties(rate, deptResponse);
        }
        return deptResponse;
    }

    private void saleList(List<SaleStoreResponse> saleList,SaleResponse saleResponse, SaleCompanyResponse companyResponse){
        if (CollectionUtils.isNotEmptyCollection(saleList)) {
            for (SaleStoreResponse sale : saleList) {
                BeanUtils.copyProperties(sale, saleResponse);
                SaleResponse rate = this.saleRate(saleResponse, 0);
                BeanUtils.copyProperties(rate, sale);
                if(companyResponse.getChannelSalesAmount().longValue() == 0){
                    sale.setChanneRate(new BigDecimal(0));
                }else {
                    if(companyResponse.getChannelSalesAmount().longValue() == 0){
                        sale.setChanneRate(new BigDecimal(0));
                    }else {
                        if(companyResponse.getChannelSalesAmount().longValue() == 0){
                            sale.setChanneRate(new BigDecimal(0));
                        }else{
                            sale.setChanneRate(sale.getChannelSalesAmount().
                                    divide(companyResponse.getChannelSalesAmount(), 4, BigDecimal.ROUND_HALF_UP));
                        }
                    }
                }
                if(companyResponse.getDistributionSalesAmount().longValue() == 0){
                    sale.setDistributionRate(new BigDecimal(0));
                }else {
                    if(companyResponse.getDistributionSalesAmount().longValue() == 0){
                        sale.setDistributionRate(new BigDecimal(0));
                    }else{
                        sale.setDistributionRate(sale.getDistributionSalesAmount().
                                divide(companyResponse.getDistributionSalesAmount(), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }
            }
        }
    }

    private SaleResponse saleRate(SaleResponse sale, int i){
        if(sale != null){
            BigDecimal big = new BigDecimal(0);
            // 计算渠道销售金额的同比
            Long amount = 0L;
            Long channelSalesAmount = sale.getChannelSalesAmount() == null ? amount : sale.getChannelSalesAmount().longValue();
            Long channelAmountLastYear = sale.getChannelAmountLastYear() == null ? amount : sale.getChannelAmountLastYear().longValue();
            if(channelSalesAmount == amount || channelAmountLastYear == amount){
                sale.setChannelSalesAmountYearonyear(big);
            }else {
                if(channelAmountLastYear == 0){
                    sale.setChannelSalesAmountYearonyear(new BigDecimal(0));
                }else{
                    sale.setChannelSalesAmountYearonyear(new BigDecimal(channelSalesAmount).
                            divide(new BigDecimal(channelAmountLastYear), 4, BigDecimal.ROUND_HALF_UP));
                }
            }
            // 计算渠道销售金额的环比
            Long channelAmountLastMonth = sale.getChannelAmountLastMonth() == null ? amount : sale.getChannelAmountLastMonth().longValue();
            if(channelSalesAmount == amount || channelAmountLastMonth == amount){
                sale.setChannelSalesAmountLinkRela(big);
            }else {
                if(channelAmountLastMonth == 0){
                    sale.setChannelSalesAmountLinkRela(new BigDecimal(0));
                }else{
                    sale.setChannelSalesAmountLinkRela(new BigDecimal(channelSalesAmount).
                            divide(new BigDecimal(channelAmountLastMonth), 4, BigDecimal.ROUND_HALF_UP));
                }
            }
            // 计算渠道毛利率
            Long channelSalesCost = sale.getChannelSalesCost() == null ? amount : sale.getChannelSalesCost().longValue();
            if(channelSalesCost == amount || channelSalesAmount == amount){
                sale.setChannelMarginRate(big);
            }else {
                if(channelSalesAmount == 0){
                    sale.setChannelMarginRate(new BigDecimal(0));
                }else{
                    sale.setChannelMarginRate(new BigDecimal(channelSalesAmount - channelSalesCost).
                            divide(new BigDecimal(channelSalesAmount), 4, BigDecimal.ROUND_HALF_UP));
                }
            }

            // 计算渠道毛利同比
            Long channelMargin = sale.getChannelMargin() == null ? amount : sale.getChannelMargin().longValue();
            Long channelMarginLastYear = sale.getChannelMarginLastYear() == null ? amount : sale.getChannelMarginLastYear().longValue();
            if(channelMargin == amount || channelMarginLastYear == amount){
                sale.setChannelMarginYearonyear(big);
            }else {
                if(channelMarginLastYear == 0){
                    sale.setChannelMarginYearonyear(new BigDecimal(0));
                }else{
                    sale.setChannelMarginYearonyear(new BigDecimal(channelMargin).
                            divide(new BigDecimal(channelMarginLastYear), 4, BigDecimal.ROUND_HALF_UP));
                }
            }
            // 计算渠道毛利环比
            Long channelMarginLastMonth  = sale.getChannelMarginLastMonth() == null ? amount : sale.getChannelMarginLastMonth().longValue();
            if(channelMargin == amount || channelMarginLastMonth == amount){
                sale.setChannelMarginLinkRela(big);
            }else {
                if(channelMarginLastMonth == 0){
                    sale.setChannelMarginLinkRela(new BigDecimal(0));
                }else{
                    sale.setChannelMarginLinkRela(new BigDecimal(channelMargin).
                            divide(new BigDecimal(channelMarginLastMonth), 4, BigDecimal.ROUND_HALF_UP));
                }
            }

            // 计算分销销售额同比
            Long distributionSalesAmount = sale.getDistributionSalesAmount() == null ? amount : sale.getDistributionSalesAmount().longValue();
            Long distributionAmountLastYear = sale.getDistributionAmountLastYear() == null ? amount : sale.getDistributionAmountLastYear().longValue();
            if(distributionSalesAmount == amount || distributionAmountLastYear == amount){
                sale.setDistributionSalesAmountYearonyear(big);
            }else {
                if(distributionAmountLastYear == 0){
                    sale.setDistributionSalesAmountYearonyear(new BigDecimal(0));
                }else{
                    sale.setDistributionSalesAmountYearonyear(new BigDecimal(distributionSalesAmount).
                            divide(new BigDecimal(distributionAmountLastYear), 4, BigDecimal.ROUND_HALF_UP));
                }
            }
            // 计算分销销售额环比
            Long distributionAmountLastMonth = sale.getDistributionAmountLastMonth() == null ? amount : sale.getDistributionAmountLastMonth().longValue();
            if(distributionSalesAmount == amount || distributionAmountLastMonth == amount){
                sale.setDistributionSalesAmountLinkRela(big);
            }else {
                if(distributionAmountLastMonth == 0){
                    sale.setDistributionSalesAmountLinkRela(new BigDecimal(0));
                }else{
                    sale.setDistributionSalesAmountLinkRela(new BigDecimal(distributionSalesAmount).
                            divide(new BigDecimal(distributionAmountLastMonth), 4, BigDecimal.ROUND_HALF_UP));
                }
            }
            // 计算分销毛利额
            Long distributionSalesCost = sale.getDistributionSalesCost() == null ? amount : sale.getDistributionSalesCost().longValue();
            if(distributionSalesCost == amount || distributionSalesAmount == amount){
                sale.setDistributionMarginRate(big);
            }else {
                if(distributionSalesAmount == 0){
                    sale.setDistributionMarginRate(new BigDecimal(0));
                }else {
                    sale.setDistributionMarginRate(new BigDecimal(distributionSalesAmount - distributionSalesCost).
                            divide(new BigDecimal(distributionSalesAmount), 4, BigDecimal.ROUND_HALF_UP));
                }
            }
            // 计算分销毛利额同比
            Long distributionMargin = sale.getDistributionMargin() == null ? amount : sale.getDistributionMargin().longValue();
            Long distributionMarginLastYear = sale.getDistributionMarginLastYear() == null ? amount : sale.getDistributionMarginLastYear().longValue();
            if(distributionMargin == amount || distributionMarginLastYear == amount){
                sale.setDistributionMarginYearonyear(big);
            }else {
                if(distributionMarginLastYear == 0){
                    sale.setDistributionMarginYearonyear(new BigDecimal(0));
                }else {
                    sale.setDistributionMarginYearonyear(new BigDecimal(distributionMargin).
                            divide(new BigDecimal(distributionMarginLastYear), 4, BigDecimal.ROUND_HALF_UP));
                }
            }
            // 计算分销毛利额同比
            Long distributionMarginLastMonth = sale.getDistributionMarginLastMonth() == null ? amount : sale.getDistributionMarginLastMonth().longValue();
            if(distributionMargin == amount || distributionMarginLastMonth == amount){
                sale.setDistributionMarginLinkRela(big);
            }else {
                if(distributionMarginLastMonth == 0){
                    sale.setDistributionMarginLinkRela(new BigDecimal(0));
                }else {
                    sale.setDistributionMarginLinkRela(new BigDecimal(distributionMargin).
                            divide(new BigDecimal(distributionMarginLastMonth), 4, BigDecimal.ROUND_HALF_UP));
                }
            }
            if(i == 1){
                // 计算渠道的达成率
                Long channelBudget = sale.getChannelBudget() == null ? amount : sale.getChannelBudget().longValue();
                Long distributionBudget = sale.getDistributionBudget() == null ? amount : sale.getDistributionBudget().longValue();
                if(channelSalesAmount == amount || channelBudget == amount){
                    sale.setChannelAchievement(big);
                }else {
                    if(channelBudget == 0){
                        sale.setChannelAchievement(new BigDecimal(0));
                    }else {
                        sale.setChannelAchievement(new BigDecimal(channelSalesAmount).
                                divide(new BigDecimal(channelBudget), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }
                // 计算分销的达成率
                if(distributionSalesAmount == amount || distributionBudget == amount){
                    sale.setDistributionAchievement(big);
                }else {
                    if(distributionBudget == 0){
                        sale.setDistributionAchievement(new BigDecimal(0));
                    }else {
                        sale.setDistributionAchievement(new BigDecimal(distributionSalesAmount).
                                divide(new BigDecimal(distributionBudget), 4, BigDecimal.ROUND_HALF_UP));
                    }
                }
            }
        }
        return sale;
    }

    @Override
    public HttpResponse<SaleSumResponse> monthSaleInfo(SaleRequest saleRequest){
        if(saleRequest == null || StringUtils.isBlank(saleRequest.getDate()) ||  saleRequest.getType() == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        Long year = Long.valueOf(saleRequest.getDate().substring(0, 4));
        Long month = Long.valueOf(saleRequest.getDate().substring(5));
        saleRequest.setYear(year);
        saleRequest.setMonth(month);
        if(saleRequest.getType().equals(Global.COMPANY)){
            // 计算月累计销售统计 - 公司
            SaleSumResponse sumResponse = this.monthCompanySale(saleRequest);
            return HttpResponse.success(sumResponse);
        }else {
            // 计算月累计销售统计 - 部门
            if(StringUtils.isBlank(saleRequest.getProductSortCode())){
                return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
            }
            SaleDeptResponse deptResponse = this.deptSale(saleRequest, 3);
            if(deptResponse != null){
                deptResponse.setChanneRate(new BigDecimal(1));
                deptResponse.setDistributionRate(new BigDecimal(1));
            }
            return HttpResponse.success(deptResponse);
        }
    }

    private SaleSumResponse monthCompanySale(SaleRequest saleRequest) {
        SaleSumResponse sumResponse;
        List<SaleDeptResponse> deptList = Lists.newArrayList();
        List<CompanyAndDeptResponse> departments;
        SaleDeptResponse deptResponse;
        SaleResponse saleResponse;
        sumResponse = statDeptMonthAccSalesDao.saleSum(saleRequest);
        if (sumResponse != null) {
            departments = statDeptMonthAccSalesDao.saleByDept(saleRequest);
            if (CollectionUtils.isNotEmptyCollection(departments)) {
                for (CompanyAndDeptResponse dept : departments) {
                    if(dept != null){
                        saleRequest.setProductSortCode(dept.getProductSortCode());
                        deptResponse = this.deptSale(saleRequest, 3);
                        if(deptResponse != null){
                            if(sumResponse.getChannelSalesAmount().longValue() == 0){
                                deptResponse.setChanneRate(new BigDecimal(0));
                            }else {
                                deptResponse.setChanneRate(deptResponse.getChannelSalesAmount().
                                        divide(sumResponse.getChannelSalesAmount(), 4, BigDecimal.ROUND_HALF_UP));
                            }
                            if(sumResponse.getDistributionSalesAmount().longValue() == 0){
                                deptResponse.setDistributionRate(new BigDecimal(0));
                            }else {
                                deptResponse.setDistributionRate(deptResponse.getDistributionSalesAmount().
                                        divide(sumResponse.getDistributionSalesAmount(), 4, BigDecimal.ROUND_HALF_UP));
                            }
                        }
                        deptList.add(deptResponse);
                    }
                }
            }
            saleResponse = new SaleResponse();
            BeanUtils.copyProperties(sumResponse, saleResponse);
            SaleResponse rate = this.saleRate(saleResponse, 1);
            BeanUtils.copyProperties(rate, sumResponse);
            sumResponse.setDeptList(deptList);
            sumResponse.setChanneRate(new BigDecimal(1));
            sumResponse.setDistributionRate(new BigDecimal(1));
        }
        return sumResponse;
    }

    @Override
    public HttpResponse<CategoryResponse> categoryPromotion(SaleRequest saleRequest){
        if(saleRequest == null || StringUtils.isBlank(saleRequest.getDate()) || saleRequest.getType() == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        Long year = Long.valueOf(saleRequest.getDate().substring(0, 4));
        Long month = Long.valueOf(saleRequest.getDate().substring(5));
        saleRequest.setYear(year);
        saleRequest.setMonth(month);
        CategoryResponse response;
        if(saleRequest.getType().equals(Global.COMPANY)){
            // 计算品类促销统计 - 公司
            response = this.companyCategory(saleRequest);
        }else {
            // 计算品类促销统计 - 部门
            if(StringUtils.isBlank(saleRequest.getProductSortCode())){
                return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
            }
            response = this.deptCategory(saleRequest);
            if(response != null){
                response.setRate(new BigDecimal(1));
            }
        }
        return HttpResponse.success(response);
    }

    private CategoryResponse companyCategory(SaleRequest saleRequest) {
        List<CompanyAndDeptResponse> departments;
        List<CategoryResponse> deptList = Lists.newArrayList();
        CategoryResponse deptSum;
        CategoryResponse sum = statDeptCategorySalesDao.categorySum(saleRequest);
        if (sum != null) {
            departments = statDeptCategorySalesDao.categoryByDept(saleRequest);
            if (CollectionUtils.isNotEmptyCollection(departments)) {
                for (CompanyAndDeptResponse dept : departments) {
                    saleRequest.setProductSortCode(dept.getProductSortCode());
                    saleRequest.setPriceChannelCode(null);
                    saleRequest.setStoreTypeCode(null);
                    deptSum = this.deptCategory(saleRequest);
                    if(sum.getCurrSalesAmount().longValue() == 0){
                        deptSum.setRate(new BigDecimal(0));
                    }else {
                        deptSum.setRate(new BigDecimal(deptSum.getCurrSalesAmount().longValue()).
                                divide(new BigDecimal(sum.getCurrSalesAmount().longValue()), 4, BigDecimal.ROUND_HALF_UP));
                    }
                    deptList.add(deptSum);
                }
            }
            sum.setCategoryList(deptList);
            sum.setSalesAmountLinkRelaGrowthRate(this.categoryRatio(sum.getCurrSalesAmount().longValue(), sum.getPreSalesAmount().longValue()));
            sum.setMarginLinkRelaGrowthRate(this.categoryRatio(sum.getCurrMargin().longValue(), sum.getPreMargin().longValue()));
            sum.setRate(new BigDecimal(1));
        }
        return sum;
    }

    private CategoryResponse deptCategory(SaleRequest saleRequest) {
        List<CompanyAndDeptResponse> companys;
        List<CategoryResponse> companyList;
        List<CategoryResponse> categoryList;
        CategoryResponse deptSum;
        CategoryResponse companySum;
        deptSum = statDeptCategorySalesDao.categorySum(saleRequest);
        companys = statDeptCategorySalesDao.categoryByCompany(saleRequest);
        if (CollectionUtils.isNotEmptyCollection(companys)) {
            companyList = Lists.newArrayList();
            for (CompanyAndDeptResponse company : companys) {
                saleRequest.setPriceChannelCode(company.getPriceChannelCode());
                saleRequest.setStoreTypeCode(null);
                companySum = statDeptCategorySalesDao.categorySum(saleRequest);
                if (companySum != null) {
                    categoryList = statDeptCategorySalesDao.categoryList(saleRequest);
                    if (CollectionUtils.isNotEmptyCollection(categoryList)) {
                        for (CategoryResponse category : categoryList) {
                            category.setSalesAmountLinkRelaGrowthRate(this.categoryRatio(category.getCurrSalesAmount().longValue(), category.getPreSalesAmount().longValue()));
                            category.setMarginLinkRelaGrowthRate(this.categoryRatio(category.getCurrMargin().longValue(), category.getPreMargin().longValue()));
                            if(companySum.getCurrSalesAmount().longValue() == 0){
                                category.setRate(new BigDecimal(0));
                            }else {
                                category.setRate(new BigDecimal(category.getCurrSalesAmount().longValue()).
                                        divide(new BigDecimal(companySum.getCurrSalesAmount().longValue()), 4, BigDecimal.ROUND_HALF_UP));
                            }
                        }
                    }
                    companySum.setCategoryList(categoryList);
                    companySum.setSalesAmountLinkRelaGrowthRate(this.categoryRatio(companySum.getCurrSalesAmount().longValue(), companySum.getPreSalesAmount().longValue()));
                    companySum.setMarginLinkRelaGrowthRate(this.categoryRatio(companySum.getCurrMargin().longValue(), companySum.getPreMargin().longValue()));
                    if(deptSum.getCurrSalesAmount().longValue() == 0){
                        companySum.setRate(new BigDecimal(0));
                    }else {
                        companySum.setRate(new BigDecimal(companySum.getCurrSalesAmount().longValue()).
                                divide(new BigDecimal(deptSum.getCurrSalesAmount().longValue()), 4, BigDecimal.ROUND_HALF_UP));
                    }
                    companyList.add(companySum);
                }
            }
            deptSum.setCategoryList(companyList);
            deptSum.setSalesAmountLinkRelaGrowthRate(this.categoryRatio(deptSum.getCurrSalesAmount().longValue(), deptSum.getPreSalesAmount().longValue()));
            deptSum.setMarginLinkRelaGrowthRate(this.categoryRatio(deptSum.getCurrMargin().longValue(), deptSum.getPreMargin().longValue()));
        }
        return deptSum;
    }

    private BigDecimal categoryRatio(Long currAmount, Long preAmount){
        BigDecimal big;
        if(currAmount == 0 || preAmount == 0){
            big = new BigDecimal(0);
        }else {
            BigDecimal curr = new BigDecimal(currAmount);
            BigDecimal pre = new BigDecimal(preAmount);
            if(pre == new BigDecimal(0)){
                big = new BigDecimal(0);
            }
            else{
                big = curr.divide(pre, 4, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1));
            }
        }
        return big;
    }

}
