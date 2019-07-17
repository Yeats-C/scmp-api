package com.aiqin.bms.scmp.api.bireport.dao;

import com.aiqin.bms.scmp.api.bireport.domain.request.ChartReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.*;

import java.util.List;

public interface ChartDao {
    /**
     *  月销售情况
     * @param chartReqVo
     * @return
     */
    List<MonthlySalesRespVo> selectMonthlySales(ChartReqVo chartReqVo);


    /**
     *  月毛利率情况
     * @param chartReqVo
     * @return
     */
    List<MonthlyGrossMarginRespVo> selectMonthlyGrossMargin(ChartReqVo chartReqVo);

    /**
     *  月销售达成情况
     * @param chartReqVo
     * @return
     */
    List<MonthSalesAchievementRespVo> selectMonthSalesAchievement(ChartReqVo chartReqVo);

    /**
     *  月累计品类销售情况
     * @param chartReqVo
     * @return
     */
    List<MonthCumulativeBrandSalesRespVo> selectMonthCumulativeBrandSales(ChartReqVo chartReqVo);

    /**
     *  月累计品类毛利率情况
     * @param chartReqVo
     * @return
     */
    List<MonthCumulativeGrossProfitMarginRespVo> selectMonthCumulativeGrossProfitMargin(ChartReqVo chartReqVo);

}
