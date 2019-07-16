package com.aiqin.bms.scmp.api.bireport.dao;

import com.aiqin.bms.scmp.api.bireport.domain.request.*;
import com.aiqin.bms.scmp.api.bireport.domain.response.*;
import com.aiqin.bms.scmp.api.product.domain.request.StockBatchRequest;

import java.util.List;

public interface ReportDao {

    /**
     *  供应商到货率
     * @param supplierArrivalRateReqVo
     * @return
     */
    List<SupplierArrivalRateRespVo> selectSupplierArrivalRate(SupplierArrivalRateReqVo supplierArrivalRateReqVo);

    Integer countSupplierArrivalRate(SupplierArrivalRateReqVo supplierArrivalRateReqVo);

    /**
     *  批次商品进销存
     * @param goodsBuySalesReqVo
     * @return
     */
    List<GoodsBuySalesRespVo> selectGoodsBuySales(GoodsBuySalesReqVo goodsBuySalesReqVo);

    Integer countGoodsBuySales(GoodsBuySalesReqVo goodsBuySalesReqVo);

    /**
     *  赠品进销存
     * @param giftsBuySalesReqVo
     * @return
     */
    List<GiftsBuySalesRespVo> selectGiftsBuySales(GiftsBuySalesReqVo giftsBuySalesReqVo);

    Integer countGiftsBuySales(GiftsBuySalesReqVo giftsBuySalesReqVo);

    /**
     *  供应商退货(退供)
     * @param supplierReturnReqVo
     * @return
     */
    List<SupplierReturnRespVo> selectSupplierReturn(SupplierReturnReqVo supplierReturnReqVo);

    Integer countSupplierReturn(SupplierReturnReqVo supplierReturnReqVo);

    /**
     *  新品批次动销率
     * @param newProductBatchMovingRateReqVo
     * @return
     */
    List<NewProductBatchMovingRateRespVo> selectNewProductBatchMovingRate(NewProductBatchMovingRateReqVo newProductBatchMovingRateReqVo);

    Integer countNewProductBatchMovingRate(NewProductBatchMovingRateReqVo newProductBatchMovingRateReqVo);

    /**
     *  门店复购率
     * @param storeRepurchaseRateReqVo
     * @return
     */
    List<StoreRepurchaseRateRespVo> selectStoreRepurchaseRate(StoreRepurchaseRateReqVo storeRepurchaseRateReqVo);

    Integer countStoreRepurchaseRate(StoreRepurchaseRateReqVo storeRepurchaseRateReqVo);

    /**
     *  负毛利
     * @param negativeMarginReqVo
     * @return
     */
    List<NegativeMarginRespVo> selectNegativeMargin(NegativeMarginReqVo negativeMarginReqVo);

    Integer countNegativeMargin(NegativeMarginReqVo negativeMarginReqVo);

    /**
     *  建议补货
     * @param suggestReplenishmentReqVo
     * @return
     */
    List<SuggestReplenishmentRespVo> selectSuggestReplenishment(SuggestReplenishmentReqVo suggestReplenishmentReqVo);

    Integer countSuggestReplenishment(SuggestReplenishmentReqVo suggestReplenishmentReqVo);

    /**
     *  低库存
     * @param highLowInventoryReqVo
     * @return
     */
    List<LowInventoryRespVo> selectLowInventory(HighLowInventoryReqVo highLowInventoryReqVo);

    Integer countLowInventory(HighLowInventoryReqVo highLowInventoryReqVo);

    /**
     *  高库存
     * @param highLowInventoryReqVo
     * @return
     */
    List<HighInventoryRespVo> selectHighInventory(HighLowInventoryReqVo highLowInventoryReqVo);

    Integer countHighInventory(HighLowInventoryReqVo highLowInventoryReqVo);

    /**
     *  品类促销
     * @param categorySaleReqVo
     * @return
     */
    List<CategorySaleRespVo> selectCategorySale(CategorySaleReqVo categorySaleReqVo);

    Integer countCategorySale(CategorySaleReqVo categorySaleReqVo);

    /**
     *  大效期
     * @param bigEffectReqVo
     * @return
     */
    List<BigEffectRespVo> selectBigEffect(BigEffectReqVo bigEffectReqVo);

    Integer countBigEffect(BigEffectReqVo bigEffectReqVo);

    /**
     *  月销售情况
     * @param monthlySalesReqVo
     * @return
     */
    List<MonthlySalesRespVo> selectMonthlySales(MonthlySalesReqVo monthlySalesReqVo);

    Integer countMonthlySales(MonthlySalesReqVo monthlySalesReqVo);

    /**
     *  月毛利率情况
     * @param monthlyGrossMarginReqVo
     * @return
     */
    List<MonthlySalesRespVo> selectMonthlyGrossMargin(MonthlySalesReqVo monthlyGrossMarginReqVo);

    Integer countMonthlyGrossMargin(MonthlySalesReqVo monthlyGrossMarginReqVo);

    /**
     *  月销售达成情况
     * @param monthSalesAchievementReqVo
     * @return
     */
    List<MonthSalesAchievementRespVo> selectMonthSalesAchievement(MonthSalesAchievementReqVo monthSalesAchievementReqVo);

    Integer countMonthSalesAchievement(MonthSalesAchievementReqVo monthSalesAchievementReqVo);

    /**
     *  月累计品类销售情况
     * @param monthCumulativeBrandSalesReqVo
     * @return
     */
    List<MonthCumulativeBrandSalesRespVo> selectMonthCumulativeBrandSales(MonthSalesAchievementReqVo monthCumulativeBrandSalesReqVo);

    Integer countMonthCumulativeBrandSales(MonthSalesAchievementReqVo monthCumulativeBrandSalesReqVo);

    /**
     *  月累计品类毛利率情况
     * @param monthCumulativeGrossProfitMarginReqVo
     * @return
     */
    List<MonthCumulativeGrossProfitMarginRespVo> selectMonthCumulativeGrossProfitMargin(MonthSalesAchievementReqVo monthCumulativeGrossProfitMarginReqVo);

    Integer countMonthCumulativeGrossProfitMargin(MonthSalesAchievementReqVo monthCumulativeGrossProfitMarginReqVo);


}
