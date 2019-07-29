package com.aiqin.bms.scmp.api.bireport.service;

import com.aiqin.bms.scmp.api.bireport.domain.request.dashboard.DashboardDepartAnnualSalesStatiReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.request.dashboard.DashboardMonthlySalesStatiAccReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.request.dashboard.DashboardMonthlySalesStatiReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.dashboard.DashboardDepartAnnualSalesStatiRespVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.dashboard.DashboardMonthlySalesStatiAccRespVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.dashboard.DashboardMonthlySalesStatiRespVo;

import java.util.List;

public interface DashboardService {

    // 年销售情况（部门）
    List<DashboardDepartAnnualSalesStatiRespVo> selectDashboardDepartAnnualSalesStati(DashboardDepartAnnualSalesStatiReqVo dashboardDepartAnnualSalesStatiReqVo);

    // 月销售情况（不累计）
    List<DashboardMonthlySalesStatiRespVo> selectDashboardMonthlySalesStati(DashboardMonthlySalesStatiReqVo dashboardMonthlySalesStatiReqVo);

    // 月销售情况（月累计）
    List<DashboardMonthlySalesStatiAccRespVo> selectDashboardMonthlySalesStatiAcc(DashboardMonthlySalesStatiAccReqVo dashboardMonthlySalesStatiAccReqVo);
}
