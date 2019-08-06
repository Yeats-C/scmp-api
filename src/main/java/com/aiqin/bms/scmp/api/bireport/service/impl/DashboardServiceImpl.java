package com.aiqin.bms.scmp.api.bireport.service.impl;

import com.aiqin.bms.scmp.api.bireport.dao.DashboardDao;
import com.aiqin.bms.scmp.api.bireport.domain.request.dashboard.*;
import com.aiqin.bms.scmp.api.bireport.domain.response.dashboard.*;
import com.aiqin.bms.scmp.api.bireport.service.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService{

    @Autowired
    private DashboardDao dashboardDao;

    // 年销售情况（部门）
    @Override
    public List<DashboardDepartAnnualSalesStatiRespVo> selectDashboardDepartAnnualSalesStati(DashboardDepartAnnualSalesStatiReqVo dashboardDepartAnnualSalesStatiReqVo) {

        return dashboardDao.selectDashboardDepartAnnualSalesStati(dashboardDepartAnnualSalesStatiReqVo);
    }

    // 月销售情况（不累计）
    @Override
    public List<DashboardMonthlySalesStatiRespVo> selectDashboardMonthlySalesStati(DashboardMonthlySalesStatiReqVo dashboardMonthlySalesStatiReqVo) {

        return dashboardDao.selectDashboardMonthlySalesStati(dashboardMonthlySalesStatiReqVo);
    }

    // 月销售情况（月累计）
    @Override
    public List<DashboardMonthlySalesStatiAccRespVo> selectDashboardMonthlySalesStatiAcc(DashboardMonthlySalesStatiAccReqVo dashboardMonthlySalesStatiAccReqVo) {
        return dashboardDao.selectDashboardMonthlySalesStatiAcc(dashboardMonthlySalesStatiAccReqVo);
    }

    // 月亏损
    @Override
    public List<DashboardMonthlyLossAmountRespVo> selectDashboardMonthlyLossAmount(DashboardMonthlyLossAmountReqVo dashboardMonthlyLossAmountReqVo) {
        return dashboardDao.selectDashboardMonthlyLossAmount(dashboardMonthlyLossAmountReqVo);
    }

    // 当月部门销售同环比
    @Override
    public List<DashboardDepMonthlyHomocyclicRatioRespVo> selectDashboardDepMonthlyHomocyclicRatio(DashboardDepMonthlyHomocyclicRatioReqVo dashboardDepMonthlyHomocyclicRatioReqVo) {
        return dashboardDao.selectDashboardDepMonthlyHomocyclicRatio(dashboardDepMonthlyHomocyclicRatioReqVo);
    }

    // 今年各亏损占比
    @Override
    public List<DashboardAllKindsLossRatioRespVo> selectDashboardAllKindsLossRatio(DashboardAllKindsLossRatioReqVo dashboardAllKindsLossRatioReqVo) {
        return dashboardDao.selectDashboardAllKindsLossRatio(dashboardAllKindsLossRatioReqVo);
    }

    // 当月各部门品类属性下的销售情况
    public List<DashboardDepCateProperSalesAmountRespVo> selectDashboardDepCateProperSalesAmount(DashboardDepCateProperSalesAmountReqVo dashboardDepCateProperSalesAmountReqVo){
        return dashboardDao.selectDashboardDepCateProperSalesAmount(dashboardDepCateProperSalesAmountReqVo);
    }

    // 当月各部门属性下的销售情况
    public List<DashboardDepProperSalesAmountRespVo> selectDashboardDepProperSalesAmount(DashboardDepProperSalesAmountReqVo dashboardDepProperSalesAmountReqVo){
        return dashboardDao.selectDashboardDepProperSalesAmount(dashboardDepProperSalesAmountReqVo);
    }

    // 当月各部门品类下的销售情况
    public List<DashboardDepCateSalesAmountRespVo> selectDashboardDepCateSalesAmount(DashboardDepCateSalesAmountReqVo dashboardDepCateSalesAmountReqVo){
        return dashboardDao.selectDashboardDepCateSalesAmount(dashboardDepCateSalesAmountReqVo);
    }
}
