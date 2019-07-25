package com.aiqin.bms.scmp.api.bireport.dao;

import com.aiqin.bms.scmp.api.bireport.domain.request.*;
import com.aiqin.bms.scmp.api.bireport.domain.response.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ReportAllDao {

    /**
     *  供应商到货率
     * @param supplierArrivalRateReqVo
     * @return
     */
    List<SupplierArrivalRateRespVo> selectSupplierArrivalRate(SupplierArrivalRateReqVo supplierArrivalRateReqVo);

    /**
     *  批次商品进销存
     * @param goodsBuySalesReqVo
     * @return
     */
    List<GoodsBuySalesRespVo> selectGoodsBuySales(GoodsBuySalesReqVo goodsBuySalesReqVo);

    GoodsBuySalesRespVo selectGoodsSkuNameByCodeOne(@Param("categoryCodeOne") String categoryCodeOne);
    GoodsBuySalesRespVo selectGoodsSkuNameByCodeTwo(@Param("categoryCodeTwo") String categoryCodeTwo);
    GoodsBuySalesRespVo selectGoodsSkuNameByCodeThree(@Param("categoryCodeThree") String categoryCodeThree);
    GoodsBuySalesRespVo selectGoodsSkuNameByCode(@Param("categoryCode") String categoryCode);

    /**
     *  赠品进销存
     * @param giftsBuySalesReqVo
     * @return
     */
    List<GiftsBuySalesRespVo> selectGiftsBuySales(GiftsBuySalesReqVo giftsBuySalesReqVo);

    GiftsBuySalesRespVo selectGiftsSkuNameByCodeOne(@Param("categoryCodeOne") String categoryCodeOne);
    GiftsBuySalesRespVo selectGiftsSkuNameByCodeTwo(@Param("categoryCodeTwo") String categoryCodeTwo);
    GiftsBuySalesRespVo selectGiftsSkuNameByCodeThree(@Param("categoryCodeThree") String categoryCodeThree);
    GiftsBuySalesRespVo selectGiftsSkuNameByCode(@Param("categoryCode") String categoryCode);

    /**
     *  供应商退货(退供)
     * @param supplierReturnReqVo
     * @return
     */
    List<SupplierReturnRespVo> selectSupplierReturn(SupplierReturnReqVo supplierReturnReqVo);

    /**
     *  新品批次动销率
     * @param newProductBatchMovingRateReqVo
     * @return
     */
    List<NewProductBatchMovingRateRespVo> selectNewProductBatchMovingRate(NewProductBatchMovingRateReqVo newProductBatchMovingRateReqVo);

    NewProductBatchMovingRateRespVo selectNewProductSkuNameByCodeOne(@Param("categoryCodeOne") String categoryCodeOne);
    NewProductBatchMovingRateRespVo selectNewProductSkuNameByCodeTwo(@Param("categoryCodeTwo") String categoryCodeTwo);
    NewProductBatchMovingRateRespVo selectNewProductSkuNameByCodeThree(@Param("categoryCodeThree") String categoryCodeThree);
    NewProductBatchMovingRateRespVo selectNewProductSkuNameByCode(@Param("categoryCode") String categoryCode);

    /**
     *  门店复购率
     * @param storeRepurchaseRateReqVo
     * @return
     */
    List<StoreRepurchaseRateRespVo> selectStoreRepurchaseRate(StoreRepurchaseRateReqVo storeRepurchaseRateReqVo);

    /**
     *  负毛利
     * @param negativeMarginReqVo
     * @return
     */
    List<NegativeMarginRespVo> selectNegativeMargin(NegativeMarginReqVo negativeMarginReqVo);

    /**
     *  建议补货
     * @param suggestReplenishmentReqVo
     * @return
     */
    List<SuggestReplenishmentRespVo> selectSuggestReplenishment(SuggestReplenishmentReqVo suggestReplenishmentReqVo);

    /**
     *  低库存
     * @param highLowInventoryReqVo
     * @return
     */
    List<LowInventoryRespVo> selectLowInventory(HighLowInventoryReqVo highLowInventoryReqVo);

    /**
     *  高库存
     * @param highLowInventoryReqVo
     * @return
     */
    List<HighInventoryRespVo> selectHighInventory(HighLowInventoryReqVo highLowInventoryReqVo);

    /**
     *  品牌促销
     * @param brandSaleReqVo
     * @return
     */
    List<CategorySaleRespVo> selectBrandSale(CategorySaleReqVo brandSaleReqVo);

    /**
     *  品类促销
     * @param categorySaleReqVo
     * @return
     */
    List<CategorySaleRespVo> selectCategorySale(CategorySaleReqVo categorySaleReqVo);

    /**
     *  大效期
     * @param bigEffectReqVo
     * @return
     */
    List<BigEffectRespVo> selectBigEffect(BigEffectReqVo bigEffectReqVo);

    /**
     *  月销售情况
     * @param monthlySalesReqVo
     * @return
     */
    List<MonthlySalesRespVo> selectMonthlySales(MonthlySalesReqVo monthlySalesReqVo);

    /**
     *  月毛利率情况
     * @param monthlyGrossMarginReqVo
     * @return
     */
    List<MonthlyGrossMarginRespVo> selectMonthlyGrossMargin(MonthlySalesReqVo monthlyGrossMarginReqVo);

    /**
     *  月销售达成情况
     * @param monthSalesAchievementReqVo
     * @return
     */
    List<MonthSalesAchievementRespVo> selectMonthSalesAchievement(MonthSalesAchievementReqVo monthSalesAchievementReqVo);

    /**
     *  月累计品类销售情况
     * @param monthCumulativeBrandSalesReqVo
     * @return
     */
    List<MonthCumulativeBrandSalesRespVo> selectMonthCumulativeBrandSales(MonthSalesAchievementReqVo monthCumulativeBrandSalesReqVo);

    /**
     *  月累计品类毛利率情况
     * @param monthCumulativeGrossProfitMarginReqVo
     * @return
     */
    List<MonthCumulativeGrossProfitMarginRespVo> selectMonthCumulativeGrossProfitMargin(MonthSalesAchievementReqVo monthCumulativeGrossProfitMarginReqVo);


}
