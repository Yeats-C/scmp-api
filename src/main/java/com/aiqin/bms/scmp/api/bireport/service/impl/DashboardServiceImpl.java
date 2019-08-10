package com.aiqin.bms.scmp.api.bireport.service.impl;

import com.aiqin.bms.scmp.api.bireport.dao.DashboardDao;
import com.aiqin.bms.scmp.api.bireport.domain.request.dashboard.*;
import com.aiqin.bms.scmp.api.bireport.domain.response.dashboard.*;
import com.aiqin.bms.scmp.api.bireport.service.DashboardService;
import com.aiqin.bms.scmp.api.util.DayUtil;
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
    public List<DashboardDepartAnnualSalesStatiRespVo> selectDashboardDepartAnnualSalesStati() {
        String fiveYearStr = DayUtil.getYearStr(-4);
        String oneYearStr = DayUtil.getYearStr(0);
        return dashboardDao.selectDashboardDepartAnnualSalesStati(fiveYearStr,oneYearStr);
    }

    // 月销售情况（不累计）
    @Override
    public List<DashboardMonthlySalesStatiRespVo> selectDashboardMonthlySalesStati(DashboardMonthlySalesStatiReqVo dashboardMonthlySalesStatiReqVo) {
        return dashboardDao.selectDashboardMonthlySalesStati(dashboardMonthlySalesStatiReqVo);
    }

    // 月销售情况（月累计）
    @Override
    public List<DashboardMonthlySalesStatiAccRespVo> selectDashboardMonthlySalesStatiAcc() {
        String oneYearStr = DayUtil.getYearStr(0);
        return dashboardDao.selectDashboardMonthlySalesStatiAcc(oneYearStr);
    }

    // 月亏损
    @Override
    public List<DashboardMonthlyLossAmountRespVo> selectDashboardMonthlyLossAmount() {
        String oneYearStr = DayUtil.getYearStr(0);
        return dashboardDao.selectDashboardMonthlyLossAmount(oneYearStr);
    }

    // 当月部门销售同环比
    @Override
    public List<DashboardDepMonthlyHomocyclicRatioRespVo> selectDashboardDepMonthlyHomocyclicRatio() {
        String oneMonthStr = DayUtil.getMonthStr();
        List<DashboardDepMonthlyHomocyclicRatioRespVo> dashboardDepMonthlyHomocyclicRatioRespVos  = dashboardDao.selectDashboardDepMonthlyHomocyclicRatio(oneMonthStr);
        ratioCommon(dashboardDepMonthlyHomocyclicRatioRespVos);
        return dashboardDepMonthlyHomocyclicRatioRespVos;
    }

    // 今年各亏损占比
    @Override
    public DashboardAllKindsLossRatioRespVo selectDashboardAllKindsLossRatio() {
        String oneYearStr = DayUtil.getYearStr(0);
        return dashboardDao.selectDashboardAllKindsLossRatio(oneYearStr);
    }

    // 当月各部门品类属性下的销售情况
    public List<DashboardDepCateProperSalesAmountRespVo> selectDashboardDepCateProperSalesAmount(DashboardDepCateProperSalesAmountReqVo dashboardDepCateProperSalesAmountReqVo){
        String oneYearStr = DayUtil.getYearStr(0);
        dashboardDepCateProperSalesAmountReqVo.setStatMonth(oneYearStr+"-"+dashboardDepCateProperSalesAmountReqVo.getStatMonth());
        return dashboardDao.selectDashboardDepCateProperSalesAmount(dashboardDepCateProperSalesAmountReqVo);
    }

    // 当月各部门属性下的销售情况
    public List<DashboardDepProperSalesAmountRespVo> selectDashboardDepProperSalesAmount(DashboardDepProperSalesAmountReqVo dashboardDepProperSalesAmountReqVo){
        String oneYearStr = DayUtil.getYearStr(0);
        dashboardDepProperSalesAmountReqVo.setStatMonth(oneYearStr+"-"+dashboardDepProperSalesAmountReqVo.getStatMonth());
        return dashboardDao.selectDashboardDepProperSalesAmount(dashboardDepProperSalesAmountReqVo);
    }

    // 当月各部门品类下的销售情况
    public List<DashboardDepCateSalesAmountRespVo> selectDashboardDepCateSalesAmount(DashboardDepCateSalesAmountReqVo dashboardDepCateSalesAmountReqVo){
        String oneYearStr = DayUtil.getYearStr(0);
        dashboardDepCateSalesAmountReqVo.setStatMonth(oneYearStr+"-"+dashboardDepCateSalesAmountReqVo.getStatMonth());
        return dashboardDao.selectDashboardDepCateSalesAmount(dashboardDepCateSalesAmountReqVo);
    }

    // 当月部门销售同环比(带条件)
    public List<DashboardDepMonthlyHomocyclicRatioRespVo> selectDashboardDepMonthlyHomocyclicRatioList(DashboardDepMonthlyHomocyclicRatioReqVo dashboardDepMonthlyHomocyclicRatioReqVo){
        String oneYearStr = DayUtil.getYearStr(0);
        dashboardDepMonthlyHomocyclicRatioReqVo.setStatMonth(oneYearStr+"-"+dashboardDepMonthlyHomocyclicRatioReqVo.getStatMonth());
        List<DashboardDepMonthlyHomocyclicRatioRespVo> dashboardDepMonthlyHomocyclicRatioRespVos = dashboardDao.selectDashboardDepMonthlyHomocyclicRatioList(dashboardDepMonthlyHomocyclicRatioReqVo);
        ratioCommon(dashboardDepMonthlyHomocyclicRatioRespVos);
        return dashboardDepMonthlyHomocyclicRatioRespVos;
    }

    // 当月部门销售同环比(带条件和不带条件的截取年月共用类)
    private void ratioCommon(List<DashboardDepMonthlyHomocyclicRatioRespVo> dashboardDepMonthlyHomocyclicRatioRespVos) {
        for (DashboardDepMonthlyHomocyclicRatioRespVo dashboardDepMonthlyHomocyclicRatioRespVo: dashboardDepMonthlyHomocyclicRatioRespVos) {
            String statMonth = dashboardDepMonthlyHomocyclicRatioRespVo.getStatMonth();
            if (statMonth.substring(5, 6).equals("0")){
                dashboardDepMonthlyHomocyclicRatioRespVo.setStatMonth(statMonth.substring(6, 7));
            }else {
                dashboardDepMonthlyHomocyclicRatioRespVo.setStatMonth(statMonth.substring(5, 7));
            }
            dashboardDepMonthlyHomocyclicRatioRespVo.setStatYear(statMonth.substring(0, 4));
        }
    }
}
