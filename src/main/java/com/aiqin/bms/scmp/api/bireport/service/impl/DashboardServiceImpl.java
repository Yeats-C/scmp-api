package com.aiqin.bms.scmp.api.bireport.service.impl;

import com.aiqin.bms.scmp.api.bireport.dao.DashboardDao;
import com.aiqin.bms.scmp.api.bireport.domain.request.dashboard.DashboardDepartAnnualSalesStatiReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.request.dashboard.DashboardMonthlySalesStatiAccReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.request.dashboard.DashboardMonthlySalesStatiReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.dashboard.DashboardDepartAnnualSalesStatiRespVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.dashboard.DashboardMonthlySalesStatiAccRespVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.dashboard.DashboardMonthlySalesStatiRespVo;
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
}
