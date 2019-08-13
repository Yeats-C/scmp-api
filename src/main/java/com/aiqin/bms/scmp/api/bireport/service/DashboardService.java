package com.aiqin.bms.scmp.api.bireport.service;

import com.aiqin.bms.scmp.api.bireport.domain.request.dashboard.*;
import com.aiqin.bms.scmp.api.bireport.domain.response.dashboard.*;

import java.util.List;

public interface DashboardService {

    // 年销售情况（部门）
    List<DashboardDepartAnnualSalesStatiRespVo> selectDashboardDepartAnnualSalesStati();

    // 月销售情况（不累计）
    List<DashboardMonthlySalesStatiRespVo> selectDashboardMonthlySalesStati(DashboardMonthlySalesStatiReqVo dashboardMonthlySalesStatiReqVo);

    // 月销售情况（月累计）
    List<DashboardMonthlySalesStatiAccRespVo> selectDashboardMonthlySalesStatiAcc();

    // 月亏损
    List<DashboardMonthlyLossAmountRespVo> selectDashboardMonthlyLossAmount();

    // 当月部门销售同环比
    List<DashboardDepMonthlyHomocyclicRatioRespVo> selectDashboardDepMonthlyHomocyclicRatio();

    // 今年各亏损占比
    DashboardAllKindsLossRatioRespVo selectDashboardAllKindsLossRatio();

    // 当月各部门品类属性下的销售情况
    List<DashboardDepCateProperSalesAmountRespVo> selectDashboardDepCateProperSalesAmount(DashboardDepCateProperSalesAmountReqVo dashboardDepCateProperSalesAmountReqVo);

    // 当月各部门属性下的销售情况
    List<DashboardDepProperSalesAmountRespVo> selectDashboardDepProperSalesAmount(DashboardDepProperSalesAmountReqVo dashboardDepProperSalesAmountReqVo);

    // 当月各部门品类下的销售情况
    List<DashboardDepCateSalesAmountRespVo> selectDashboardDepCateSalesAmount(DashboardDepCateSalesAmountReqVo dashboardDepCateSalesAmountReqVo);

    // 当月部门销售同环比(带条件)
    List<DashboardDepMonthlyHomocyclicRatioRespVo> selectDashboardDepMonthlyHomocyclicRatioList(DashboardDepMonthlyHomocyclicRatioReqVo dashboardDepMonthlyHomocyclicRatioReqVo);

    // 首页头字段
    DashboardHomePageTitle selectDashboardHomePageTitle();
}
