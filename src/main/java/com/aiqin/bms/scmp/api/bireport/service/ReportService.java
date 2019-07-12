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
    PageInfo<GoodsBuySalesRespVo> selectGoodsBuySales(GoodsBuySalesReqVo goodsBuySalesReqVo);

    /**
     *  赠品进销存
     * @param giftsBuySalesReqVo
     * @return
     */
    PageInfo<GiftsBuySalesRespVo> selectGiftsBuySales(GiftsBuySalesReqVo giftsBuySalesReqVo);

    /**
     *  供应商退货(退供)
     * @param supplierReturnReqVo
     * @return
     */
    PageInfo<SupplierReturnRespVo> selectSupplierReturn(SupplierReturnReqVo supplierReturnReqVo);

    /**
     *  新品批次动销率
     * @param newProductBatchMovingRateReqVo
     * @return
     */
    PageInfo<NewProductBatchMovingRateRespVo> selectNewProductBatchMovingRate(NewProductBatchMovingRateReqVo newProductBatchMovingRateReqVo);

    /**
     *  门店复购率
     * @param storeRepurchaseRateReqVo
     * @return
     */
    PageInfo<StoreRepurchaseRateRespVo> selectStoreRepurchaseRate(StoreRepurchaseRateReqVo storeRepurchaseRateReqVo);

    /**
     *  负毛利
     * @param negativeMarginReqVo
     * @return
     */
    PageInfo<NegativeMarginRespVo> selectNegativeMargin(NegativeMarginReqVo negativeMarginReqVo);

    /**
     *  建议补货
     * @param suggestReplenishmentReqVo
     * @return
     */
    PageInfo<SuggestReplenishmentRespVo> selectSuggestReplenishment(SuggestReplenishmentReqVo suggestReplenishmentReqVo);

    /**
     *  低库存
     * @param highLowInventoryReqVo
     * @return
     */
    PageInfo<LowInventoryRespVo> selectLowInventory(HighLowInventoryReqVo highLowInventoryReqVo);

    /**
     *  高库存
     * @param highLowInventoryReqVo
     * @return
     */
    PageInfo<HighInventoryRespVo> selectHighInventory(HighLowInventoryReqVo highLowInventoryReqVo);

    /**
     *  大效期
     * @param bigEffectReqVo
     * @return
     */
    PageInfo<BigEffectRespVo> selectBigEffect(BigEffectReqVo bigEffectReqVo);
}
