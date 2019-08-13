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
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardDao dashboardDao;

    // 年销售情况（部门）
    @Override
    public List<DashboardDepartAnnualSalesStatiRespVo> selectDashboardDepartAnnualSalesStati() {
        String fiveYearStr = DayUtil.getYearStr(-4);
        String oneYearStr = DayUtil.getYearStr(0);
        return dashboardDao.selectDashboardDepartAnnualSalesStati(fiveYearStr, oneYearStr);
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
        List<DashboardDepMonthlyHomocyclicRatioRespVo> dashboardDepMonthlyHomocyclicRatioRespVos = dashboardDao.selectDashboardDepMonthlyHomocyclicRatio(oneMonthStr);
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
    public List<DashboardDepCateProperSalesAmountRespVo> selectDashboardDepCateProperSalesAmount(DashboardDepCateProperSalesAmountReqVo dashboardDepCateProperSalesAmountReqVo) {
        String oneYearStr = DayUtil.getYearStr(0);
        dashboardDepCateProperSalesAmountReqVo.setStatMonth(oneYearStr + "-" + dashboardDepCateProperSalesAmountReqVo.getStatMonth());
        List<DashboardDepCateProperSalesAmountRespVo> dashboardDepCateProperSalesAmountRespVos = dashboardDao.selectDashboardDepCateProperSalesAmount(dashboardDepCateProperSalesAmountReqVo);
        for (DashboardDepCateProperSalesAmountRespVo dashboardDepCateProperSalesAmountRespVo : dashboardDepCateProperSalesAmountRespVos) {
            String statMonth = dashboardDepCateProperSalesAmountRespVo.getStatMonth();
            if (statMonth.substring(5, 6).equals("0")) {
                dashboardDepCateProperSalesAmountRespVo.setStatMonth(statMonth.substring(6, 7));
            } else {
                dashboardDepCateProperSalesAmountRespVo.setStatMonth(statMonth.substring(5, 7));
            }
            dashboardDepCateProperSalesAmountRespVo.setStatYear(statMonth.substring(0, 4));
        }
        return dashboardDepCateProperSalesAmountRespVos;
    }

    // 当月各部门属性下的销售情况
    public List<DashboardDepProperSalesAmountRespVo> selectDashboardDepProperSalesAmount(DashboardDepProperSalesAmountReqVo dashboardDepProperSalesAmountReqVo) {
        String oneYearStr = DayUtil.getYearStr(0);
        dashboardDepProperSalesAmountReqVo.setStatMonth(oneYearStr + "-" + dashboardDepProperSalesAmountReqVo.getStatMonth());
        List<DashboardDepProperSalesAmountRespVo> dashboardDepProperSalesAmountRespVos = dashboardDao.selectDashboardDepProperSalesAmount(dashboardDepProperSalesAmountReqVo);
        for (DashboardDepProperSalesAmountRespVo dashboardDepProperSalesAmountRespVo : dashboardDepProperSalesAmountRespVos) {
            String statMonth = dashboardDepProperSalesAmountRespVo.getStatMonth();
            if (statMonth.substring(5, 6).equals("0")) {
                dashboardDepProperSalesAmountRespVo.setStatMonth(statMonth.substring(6, 7));
            } else {
                dashboardDepProperSalesAmountRespVo.setStatMonth(statMonth.substring(5, 7));
            }
            dashboardDepProperSalesAmountRespVo.setStatYear(statMonth.substring(0, 4));
        }
        return dashboardDepProperSalesAmountRespVos;
    }

    // 当月各部门品类下的销售情况
    public List<DashboardDepCateSalesAmountRespVo> selectDashboardDepCateSalesAmount(DashboardDepCateSalesAmountReqVo dashboardDepCateSalesAmountReqVo) {
        String oneYearStr = DayUtil.getYearStr(0);
        dashboardDepCateSalesAmountReqVo.setStatMonth(oneYearStr + "-" + dashboardDepCateSalesAmountReqVo.getStatMonth());
        List<DashboardDepCateSalesAmountRespVo> dashboardDepCateSalesAmountRespVos = dashboardDao.selectDashboardDepCateSalesAmount(dashboardDepCateSalesAmountReqVo);
        for (DashboardDepCateSalesAmountRespVo dashboardDepCateSalesAmountRespVo : dashboardDepCateSalesAmountRespVos) {
            String statMonth = dashboardDepCateSalesAmountRespVo.getStatMonth();
            if (statMonth.substring(5, 6).equals("0")) {
                dashboardDepCateSalesAmountRespVo.setStatMonth(statMonth.substring(6, 7));
            } else {
                dashboardDepCateSalesAmountRespVo.setStatMonth(statMonth.substring(5, 7));
            }
            dashboardDepCateSalesAmountRespVo.setStatYear(statMonth.substring(0, 4));
        }
        return dashboardDepCateSalesAmountRespVos;
    }

    // 当月部门销售同环比(带条件)
    public List<DashboardDepMonthlyHomocyclicRatioRespVo> selectDashboardDepMonthlyHomocyclicRatioList(DashboardDepMonthlyHomocyclicRatioReqVo dashboardDepMonthlyHomocyclicRatioReqVo) {
        String oneYearStr = DayUtil.getYearStr(0);
        String monthStr = DayUtil.getMonthStr();
        dashboardDepMonthlyHomocyclicRatioReqVo.setBeginStatDate(oneYearStr + "-01");
        dashboardDepMonthlyHomocyclicRatioReqVo.setFinishStatDate(monthStr);
        List<DashboardDepMonthlyHomocyclicRatioRespVo> dashboardDepMonthlyHomocyclicRatioRespVos = dashboardDao.selectDashboardDepMonthlyHomocyclicRatioList(dashboardDepMonthlyHomocyclicRatioReqVo);
        ratioCommon(dashboardDepMonthlyHomocyclicRatioRespVos);
        return dashboardDepMonthlyHomocyclicRatioRespVos;
    }

    // 当月部门销售同环比(带条件和不带条件的截取年月共用类)
    private void ratioCommon(List<DashboardDepMonthlyHomocyclicRatioRespVo> dashboardDepMonthlyHomocyclicRatioRespVos) {
        for (DashboardDepMonthlyHomocyclicRatioRespVo dashboardDepMonthlyHomocyclicRatioRespVo : dashboardDepMonthlyHomocyclicRatioRespVos) {
            String statMonth = dashboardDepMonthlyHomocyclicRatioRespVo.getStatMonth();
            if (statMonth.substring(5, 6).equals("0")) {
                dashboardDepMonthlyHomocyclicRatioRespVo.setStatMonth(statMonth.substring(6, 7));
            } else {
                dashboardDepMonthlyHomocyclicRatioRespVo.setStatMonth(statMonth.substring(5, 7));
            }
            dashboardDepMonthlyHomocyclicRatioRespVo.setStatYear(statMonth.substring(0, 4));
        }
    }

    // 首页头字段
    public DashboardHomePageTitle selectDashboardHomePageTitle() {
        DashboardHomePageTitle dashboardHomePageTitle = new DashboardHomePageTitle();
        String oneYearStr = DayUtil.getYearStr(0);
        // 查询首页7字段之4(渠道金额，渠道达成率，渠道毛利率)
        DashboardDepartAnnualSalesStatiRespVo dashboardDepartAnnualSalesStatiRespVo = dashboardDao.selectAnnualSales(oneYearStr);
        // 查询首页7字段之1(亏损合计)
        DashboardMonthlyLossAmountRespVo dashboardMonthlyLossAmountRespVo = dashboardDao.selectMonthlyLossAmount(oneYearStr);
        // 查询首页7字段之1(A品金额)
        DashboardDepCateProperSalesAmountRespVo dashboardDepCateProperSalesAmountRespVo = dashboardDao.selectPropertyAmount(oneYearStr);
        // 插入数据返回
        if(dashboardDepartAnnualSalesStatiRespVo != null){
            dashboardHomePageTitle.setChannelAmount(dashboardDepartAnnualSalesStatiRespVo.getChannelAmount());
            dashboardHomePageTitle.setAchieveRate(dashboardDepartAnnualSalesStatiRespVo.getAchieveRate());
            dashboardHomePageTitle.setChannelMargin(dashboardDepartAnnualSalesStatiRespVo.getChannelMargin());
            if(dashboardDepartAnnualSalesStatiRespVo.getChannelAmount() != null){
                Double marginRate = dashboardDepartAnnualSalesStatiRespVo.getChannelMargin().doubleValue() / dashboardDepartAnnualSalesStatiRespVo.getChannelAmount().doubleValue();
                dashboardHomePageTitle.setMarginRate(marginRate);
            }
        }
        if(dashboardMonthlyLossAmountRespVo != null){
            dashboardHomePageTitle.setMonthlyLossAmount(dashboardMonthlyLossAmountRespVo.getMonthlyLossAmount());
            dashboardHomePageTitle.setChannelSalesAmount(dashboardDepCateProperSalesAmountRespVo.getChannelSalesAmount());
        }
        if(dashboardDepCateProperSalesAmountRespVo != null){
            Double contributionRate = dashboardDepCateProperSalesAmountRespVo.getChannelSalesAmount().doubleValue() / dashboardDepartAnnualSalesStatiRespVo.getChannelAmount().doubleValue();
            dashboardHomePageTitle.setContributionRate(contributionRate);
        }

        return dashboardHomePageTitle;
    }

}
