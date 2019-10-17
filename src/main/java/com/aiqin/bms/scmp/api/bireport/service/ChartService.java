package com.aiqin.bms.scmp.api.bireport.service;

import com.aiqin.bms.scmp.api.base.PageImportResData;
import com.aiqin.bms.scmp.api.bireport.domain.request.ChartReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.MonthCumulativeBrandSalesRespVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.MonthCumulativeGrossProfitMarginRespVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.MonthlyGrossMarginRespVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.MonthlySalesRespVo;

import java.util.List;

public interface ChartService {

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
    PageImportResData selectMonthSalesAchievement(ChartReqVo chartReqVo);

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

    // 部门预算额
    Object selectMonthCumulativeMargin(String productSortName,String saleTypeCode);
}
