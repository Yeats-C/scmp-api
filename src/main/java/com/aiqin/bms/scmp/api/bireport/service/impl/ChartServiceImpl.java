package com.aiqin.bms.scmp.api.bireport.service.impl;

import com.aiqin.bms.scmp.api.base.PageImportResData;
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
            String createTime = chartReqVo.getCreateTime();
           /* if(createTime != null){
                chartReqVo.setBeginCreateTime(createTime.substring(0,5)+"01");
            }*/
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
            String createTime = chartReqVo.getCreateTime();
            /*if(createTime != null){
                chartReqVo.setBeginCreateTime(createTime.substring(0,5)+"01");
            }*/
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
    public PageImportResData<MonthSalesAchievementRespVo> selectMonthSalesAchievement(ChartReqVo chartReqVo) {
        try {
            String createTime = chartReqVo.getCreateTime();
            /*if(createTime != null){
                chartReqVo.setBeginCreateTime(createTime.substring(0,5)+"01");
            }*/
            List<MonthSalesAchievementRespVo> monthSalesAchievementRespVos = chartDao.selectMonthSalesAchievement(chartReqVo);
            MonthSalesAchievementRespVo monthSalesAchievementSum = chartDao.sumMonthSalesAchievement(chartReqVo);
            return new PageImportResData<>(monthSalesAchievementSum,monthSalesAchievementRespVos);
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
            String createTime = chartReqVo.getCreateTime();
            /*if(createTime != null){
                chartReqVo.setBeginCreateTime(createTime.substring(0,5)+"01");
            }*/
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
            String createTime = chartReqVo.getCreateTime();
           /* if(createTime != null){
                chartReqVo.setBeginCreateTime(createTime.substring(0,5)+"01");
            }*/
            List<MonthCumulativeGrossProfitMarginRespVo> monthCumulativeGrossProfitMarginRespVos = chartDao.selectMonthCumulativeGrossProfitMargin(chartReqVo);
            return monthCumulativeGrossProfitMarginRespVos;
        } catch (Exception ex) {
            log.error("月销售达成情况");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }
}
