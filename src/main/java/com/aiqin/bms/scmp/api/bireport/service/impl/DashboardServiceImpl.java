package com.aiqin.bms.scmp.api.bireport.service.impl;

import com.aiqin.bms.scmp.api.bireport.dao.DashboardDao;
import com.aiqin.bms.scmp.api.bireport.domain.request.dashboard.*;
import com.aiqin.bms.scmp.api.bireport.domain.response.dashboard.*;
import com.aiqin.bms.scmp.api.bireport.service.DashboardService;
import com.aiqin.bms.scmp.api.util.DayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
                double mr = dashboardDepartAnnualSalesStatiRespVo.getChannelMargin().doubleValue() / dashboardDepartAnnualSalesStatiRespVo.getChannelAmount().doubleValue();
                BigDecimal marginRate = new BigDecimal(mr);
                dashboardHomePageTitle.setMarginRate(marginRate);
            }
        }
        if(dashboardMonthlyLossAmountRespVo != null){
            dashboardHomePageTitle.setMonthlyLossAmount(dashboardMonthlyLossAmountRespVo.getMonthlyLossAmount());
        }
        if(dashboardDepCateProperSalesAmountRespVo != null){
            dashboardHomePageTitle.setChannelSalesAmount(dashboardDepCateProperSalesAmountRespVo.getChannelSalesAmount());
            if(dashboardDepCateProperSalesAmountRespVo.getChannelMargin() != null && dashboardDepartAnnualSalesStatiRespVo.getChannelAmount() != null){
                double cr = dashboardDepCateProperSalesAmountRespVo.getChannelMargin().doubleValue() / dashboardDepartAnnualSalesStatiRespVo.getChannelAmount().doubleValue();
                BigDecimal contributionRate = new BigDecimal(cr);
                dashboardHomePageTitle.setContributionRate(contributionRate);
            }
        }
        return dashboardHomePageTitle;
    }

    public ChannelSectorMonthSalesRespVo selectChannelSectorMonthSales(ChannelSectorMonthSalesReqVo channelSectorMonthSalesReqVo){
        ChannelSectorMonthSalesRespVo channelSectorMonthSalesRespVo = new ChannelSectorMonthSalesRespVo();
        String oneYearStr = DayUtil.getYearStr(0);
        channelSectorMonthSalesReqVo.setStatMonth(oneYearStr+"-"+channelSectorMonthSalesReqVo.getStatMonth());
        // 查询部门销售额贡献率
        // 查询部门销售额（条件筛选的）
        DashboardDepMonthlyHomocyclicRatioRespVo dashboardDepMonthlyHomocyclicRatioRespVo = dashboardDao.selectSalesContributionRate(channelSectorMonthSalesReqVo);
        // 查询所有的部门销售额
        DashboardDepMonthlyHomocyclicRatioRespVo dashboardDepMonthlyHomocyclicRatioRespVoAll = dashboardDao.selectSalesContributionRateAll(channelSectorMonthSalesReqVo.getStatMonth());

        // 查询负毛利SKU数
        DashboardNegativeMarginRespVo dashboardNegativeMarginRespVo = dashboardDao.selectNegativeMarginSkuNumber(channelSectorMonthSalesReqVo);
        // 查询缺货率
        DashboardStockoutRateRespVo dashboardStockoutRateRespVo = dashboardDao.selectOutStockRate(channelSectorMonthSalesReqVo);
        // 渠道退供金额(元)
        DashboardReturnAmountRespVo dashboardReturnAmountRespVo = dashboardDao.selectAmountChannelRefund(channelSectorMonthSalesReqVo);

        //插入数据
        if(dashboardDepMonthlyHomocyclicRatioRespVoAll != null && dashboardDepMonthlyHomocyclicRatioRespVoAll.getChannelSalesAmount() != 0){
            Long salesContributionRate = dashboardDepMonthlyHomocyclicRatioRespVo.getChannelSalesAmount() / dashboardDepMonthlyHomocyclicRatioRespVoAll.getChannelSalesAmount();
            channelSectorMonthSalesRespVo.setSalesContributionRate(salesContributionRate);
        }
        if(dashboardNegativeMarginRespVo != null){
            channelSectorMonthSalesRespVo.setNegativeMarginSkuNumber(dashboardNegativeMarginRespVo.getNegativeMarginSkuNum());
        }
        if(dashboardStockoutRateRespVo != null){
            channelSectorMonthSalesRespVo.setOutStockRate(dashboardStockoutRateRespVo.getStockoutRate());
        }
        if(dashboardReturnAmountRespVo != null){
            channelSectorMonthSalesRespVo.setAmountChannelRefund(dashboardReturnAmountRespVo.getAmt());
        }
        return channelSectorMonthSalesRespVo;
    }

    // 首页跳转的月不累计
    @Override
    public List<DashboardHomepageMonthlySalesRespVo> selectDashboardHomepageMonthlySales(DashboardHomepageMonthlySalesReqVo dashboardHomepageMonthlySalesReqVo) {
        return dashboardDao.selectDashboardHomepageMonthlySales(dashboardHomepageMonthlySalesReqVo);
    }

    // 首页当月部门销售同环比情况
    @Override
    public List<DashboardHomepageMonthlyHomocyclicRatioRespVo> selectDashboardHomepageMonthlyHomocyclicRatio() {
        String oneMonthStr = DayUtil.getMonthStr();
        List<DashboardHomepageMonthlyHomocyclicRatioRespVo> dashboardHomepageMonthlyHomocyclicRatioRespVos = dashboardDao.selectDashboardHomepageMonthlyHomocyclicRatio(oneMonthStr);
        for (DashboardHomepageMonthlyHomocyclicRatioRespVo dashboardHomepageMonthlyHomocyclicRatioRespVo : dashboardHomepageMonthlyHomocyclicRatioRespVos) {
            String statMonth = dashboardHomepageMonthlyHomocyclicRatioRespVo.getStatMonth();
            if (statMonth.substring(5, 6).equals("0")) {
                dashboardHomepageMonthlyHomocyclicRatioRespVo.setStatMonth(statMonth.substring(6, 7));
            } else {
                dashboardHomepageMonthlyHomocyclicRatioRespVo.setStatMonth(statMonth.substring(5, 7));
            }
            dashboardHomepageMonthlyHomocyclicRatioRespVo.setStatYear(statMonth.substring(0, 4));
        }
        return dashboardHomepageMonthlyHomocyclicRatioRespVos;
    }

}
