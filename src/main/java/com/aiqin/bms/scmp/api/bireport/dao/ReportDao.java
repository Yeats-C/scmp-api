package com.aiqin.bms.scmp.api.bireport.dao;

import com.aiqin.bms.scmp.api.bireport.domain.request.*;
import com.aiqin.bms.scmp.api.bireport.domain.response.*;

import java.util.List;

public interface ReportDao {

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

    /**
     *  赠品进销存
     * @param giftsBuySalesReqVo
     * @return
     */
    List<GiftsBuySalesRespVo> selectGiftsBuySales(GiftsBuySalesReqVo giftsBuySalesReqVo);

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
}
