package com.aiqin.bms.scmp.api.bireport.dao;

import com.aiqin.bms.scmp.api.bireport.domain.request.dashboard.*;
import com.aiqin.bms.scmp.api.bireport.domain.response.dashboard.*;

import java.util.List;

public interface DashboardDao {

    // 年销售情况（部门）
    List<DashboardDepartAnnualSalesStatiRespVo> selectDashboardDepartAnnualSalesStati(DashboardDepartAnnualSalesStatiReqVo dashboardDepartAnnualSalesStatiReqVo);

    // 月销售情况（不累计）
    List<DashboardMonthlySalesStatiRespVo> selectDashboardMonthlySalesStati(DashboardMonthlySalesStatiReqVo dashboardMonthlySalesStatiReqVo);

    // 月销售情况（月累计）
    List<DashboardMonthlySalesStatiAccRespVo> selectDashboardMonthlySalesStatiAcc(DashboardMonthlySalesStatiAccReqVo dashboardMonthlySalesStatiAccReqVo);

    // 月亏损
    List<DashboardMonthlyLossAmountRespVo> selectDashboardMonthlyLossAmount(DashboardMonthlyLossAmountReqVo dashboardMonthlyLossAmountReqVo);

    // 当月部门销售同环比
    List<DashboardDepMonthlyHomocyclicRatioRespVo> selectDashboardDepMonthlyHomocyclicRatio(DashboardDepMonthlyHomocyclicRatioReqVo dashboardDepMonthlyHomocyclicRatioReqVo);

    // 今年各亏损占比
    List<DashboardAllKindsLossRatioRespVo> selectDashboardAllKindsLossRatio(DashboardAllKindsLossRatioReqVo dashboardAllKindsLossRatioReqVo);

    // 当月各部门品类属性下的销售情况
    List<DashboardDepCateProperSalesAmountRespVo> selectDashboardDepCateProperSalesAmount(DashboardDepCateProperSalesAmountReqVo dashboardDepCateProperSalesAmountReqVo);

    // 当月各部门属性下的销售情况
    List<DashboardDepProperSalesAmountRespVo> selectDashboardDepProperSalesAmount(DashboardDepProperSalesAmountReqVo dashboardDepProperSalesAmountReqVo);

    // 当月各部门品类下的销售情况
    List<DashboardDepCateSalesAmountRespVo> selectDashboardDepCateSalesAmount(DashboardDepCateSalesAmountReqVo dashboardDepCateSalesAmountReqVo);
}
