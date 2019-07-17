package com.aiqin.bms.scmp.api.bireport.service.impl;

import com.aiqin.bms.scmp.api.bireport.dao.ChartDao;
import com.aiqin.bms.scmp.api.bireport.domain.request.ChartReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.*;
import com.aiqin.bms.scmp.api.bireport.service.ChartService;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChartServiceImpl implements ChartService{

    @Autowired
    private ChartDao chartDao;

    /**
     *  月销售情况
     * @param chartReqVo
     * @return
     */
    @Override
    public List<MonthlySalesRespVo> selectMonthlySales(ChartReqVo chartReqVo) {
        try {
            List<MonthlySalesRespVo> monthlySalesRespVos = chartDao.selectMonthlySales(chartReqVo);
            return monthlySalesRespVos;
        } catch (Exception ex) {
            log.error("月销售情况");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  月毛利率情况
     * @param chartReqVo
     * @return
     */
    @Override
    public List<MonthlyGrossMarginRespVo> selectMonthlyGrossMargin(ChartReqVo chartReqVo) {
        try {
            List<MonthlyGrossMarginRespVo> monthlySalesRespVos = chartDao.selectMonthlyGrossMargin(chartReqVo);
            return monthlySalesRespVos;
        } catch (Exception ex) {
            log.error("月毛利率情况");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  月销售达成情况
     * @param chartReqVo
     * @return
     */
    @Override
    public List<MonthSalesAchievementRespVo> selectMonthSalesAchievement(ChartReqVo chartReqVo) {
        try {
            List<MonthSalesAchievementRespVo> monthSalesAchievementRespVos = chartDao.selectMonthSalesAchievement(chartReqVo);
            return monthSalesAchievementRespVos;
        } catch (Exception ex) {
            log.error("月销售达成情况");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  月累计品类销售情况
     * @param chartReqVo
     * @return
     */
    @Override
    public List<MonthCumulativeBrandSalesRespVo> selectMonthCumulativeBrandSales(ChartReqVo chartReqVo) {
        try {
            List<MonthCumulativeBrandSalesRespVo> monthCumulativeBrandSalesRespVos = chartDao.selectMonthCumulativeBrandSales(chartReqVo);
            return monthCumulativeBrandSalesRespVos;
        } catch (Exception ex) {
            log.error("月销售达成情况");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  月累计品类毛利率情况
     * @param chartReqVo
     * @return
     */
    @Override
    public List<MonthCumulativeGrossProfitMarginRespVo> selectMonthCumulativeGrossProfitMargin(ChartReqVo chartReqVo) {
        try {
            List<MonthCumulativeGrossProfitMarginRespVo> monthCumulativeGrossProfitMarginRespVos = chartDao.selectMonthCumulativeGrossProfitMargin(chartReqVo);
            return monthCumulativeGrossProfitMarginRespVos;
        } catch (Exception ex) {
            log.error("月销售达成情况");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }
}
