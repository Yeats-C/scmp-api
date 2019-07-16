package com.aiqin.bms.scmp.api.bireport.service;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.bireport.domain.request.*;
import com.aiqin.bms.scmp.api.bireport.domain.response.*;
import com.github.pagehelper.PageInfo;

public interface ReportService {

    /**
     *  供应商到货率
     * @param supplierArrivalRateReqVo
     * @return
     */
    // PageInfo<SupplierArrivalRateRespVo> selectSupplierArrivalRate(SupplierArrivalRateReqVo supplierArrivalRateReqVo);
    PageResData selectSupplierArrivalRate(SupplierArrivalRateReqVo supplierArrivalRateReqVo);

    /**
     *  批次商品进销存
     * @param goodsBuySalesReqVo
     * @return
     */
    PageResData selectGoodsBuySales(GoodsBuySalesReqVo goodsBuySalesReqVo);

    /**
     *  赠品进销存
     * @param giftsBuySalesReqVo
     * @return
     */
    PageResData selectGiftsBuySales(GiftsBuySalesReqVo giftsBuySalesReqVo);

    /**
     *  供应商退货(退供)
     * @param supplierReturnReqVo
     * @return
     */
    PageResData selectSupplierReturn(SupplierReturnReqVo supplierReturnReqVo);

    /**
     *  新品批次动销率
     * @param newProductBatchMovingRateReqVo
     * @return
     */
    PageResData selectNewProductBatchMovingRate(NewProductBatchMovingRateReqVo newProductBatchMovingRateReqVo);

    /**
     *  门店复购率
     * @param storeRepurchaseRateReqVo
     * @return
     */
    PageResData selectStoreRepurchaseRate(StoreRepurchaseRateReqVo storeRepurchaseRateReqVo);

    /**
     *  负毛利
     * @param negativeMarginReqVo
     * @return
     */
    PageResData selectNegativeMargin(NegativeMarginReqVo negativeMarginReqVo);

    /**
     *  建议补货
     * @param suggestReplenishmentReqVo
     * @return
     */
    PageResData selectSuggestReplenishment(SuggestReplenishmentReqVo suggestReplenishmentReqVo);

    /**
     *  低库存
     * @param highLowInventoryReqVo
     * @return
     */
    PageResData selectLowInventory(HighLowInventoryReqVo highLowInventoryReqVo);

    /**
     *  高库存
     * @param highLowInventoryReqVo
     * @return
     */
    PageResData selectHighInventory(HighLowInventoryReqVo highLowInventoryReqVo);

    /**
     *  品类促销
     * @param categorySaleReqVo
     * @return
     */
    PageResData selectCategorySale(CategorySaleReqVo categorySaleReqVo);

    /**
     *  大效期
     * @param bigEffectReqVo
     * @return
     */
    PageResData selectBigEffect(BigEffectReqVo bigEffectReqVo);

    /**
     *  月销售情况
     * @param monthlySalesReqVo
     * @return
     */
    PageResData selectMonthlySales(MonthlySalesReqVo monthlySalesReqVo);

    /**
     *  月毛利率情况
     * @param monthlyGrossMarginReqVo
     * @return
     */
    PageResData selectMonthlyGrossMargin(MonthlySalesReqVo monthlyGrossMarginReqVo);

    /**
     *  月销售达成情况
     * @param monthSalesAchievementReqVo
     * @return
     */
    PageResData selectMonthSalesAchievement(MonthSalesAchievementReqVo monthSalesAchievementReqVo);

    /**
     *  月累计品类销售情况
     * @param monthCumulativeBrandSalesReqVo
     * @return
     */
    PageResData selectMonthCumulativeBrandSales(MonthSalesAchievementReqVo monthCumulativeBrandSalesReqVo);

    /**
     *  月累计品类毛利率情况
     * @param monthCumulativeGrossProfitMarginReqVo
     * @return
     */
    PageResData selectMonthCumulativeGrossProfitMargin(MonthSalesAchievementReqVo monthCumulativeGrossProfitMarginReqVo);
}
