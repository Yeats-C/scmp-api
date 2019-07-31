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

}
