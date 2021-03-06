package com.aiqin.bms.scmp.api.bireport.service.impl;

import com.aiqin.bms.scmp.api.base.PageReportResData;
import com.aiqin.bms.scmp.api.bireport.dao.ReportDao;
import com.aiqin.bms.scmp.api.bireport.domain.request.*;
import com.aiqin.bms.scmp.api.bireport.domain.response.*;
import com.aiqin.bms.scmp.api.bireport.service.ReportService;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;


    /**
     *  供应商到货率
     * @param supplierArrivalRateReqVo
     * @return
     */
    @Override
    public PageReportResData selectSupplierArrivalRate(SupplierArrivalRateReqVo supplierArrivalRateReqVo) {
        try {
            List<SupplierArrivalRateRespVo> supplierArrivalRateRespVos = reportDao.selectSupplierArrivalRate(supplierArrivalRateReqVo);
            List<Map> maps = reportDao.selectSupplierArrivalRateTableCloumnName();
            SupplierArrivalRateRespVo supplierArrivalRateRespVoSum = reportDao.sumSupplierArrivalRate(supplierArrivalRateReqVo);
            Integer total = reportDao.countSupplierArrivalRate(supplierArrivalRateReqVo);
            SupplierArrivalRateRespVo supplierArrivalRateRespVo = new SupplierArrivalRateRespVo();
            supplierArrivalRateRespVo.setColumnList(maps);
            if(supplierArrivalRateRespVoSum != null){
                supplierArrivalRateRespVo.setPreInboundNums(supplierArrivalRateRespVoSum.getPreInboundNums());
                supplierArrivalRateRespVo.setPreTaxAmounts(supplierArrivalRateRespVoSum.getPreTaxAmounts());
                supplierArrivalRateRespVo.setPraInboundNums(supplierArrivalRateRespVoSum.getPraInboundNums());
                supplierArrivalRateRespVo.setPraTaxAmounts(supplierArrivalRateRespVoSum.getPraTaxAmounts());
                supplierArrivalRateRespVo.setPraTaxAmountRates(supplierArrivalRateRespVoSum.getPraTaxAmountRates());
            }
            return new PageReportResData<SupplierArrivalRateRespVo>(total,supplierArrivalRateRespVos,supplierArrivalRateRespVo);
        } catch (Exception ex) {
            log.error("查询供应商到货率失败");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  批次商品进销存
     * @param goodsBuySalesReqVo
     * @return
     */
    @Override
    public PageReportResData selectGoodsBuySales(GoodsBuySalesReqVo goodsBuySalesReqVo) {
        try {
            List<GoodsBuySalesRespVo> goodsBuySalesRespVos = reportDao.selectGoodsBuySales(goodsBuySalesReqVo);
            Integer total = reportDao.countGoodsBuySales(goodsBuySalesReqVo);
            for (GoodsBuySalesRespVo goodsBuySalesRespVo : goodsBuySalesRespVos) {
                GoodsBuySalesRespVo goodsBuySalesRespVosOne = reportDao.selectGoodsSkuNameByCodeOne(goodsBuySalesRespVo.getCategoryCodeOne());
                GoodsBuySalesRespVo goodsBuySalesRespVosTwo = reportDao.selectGoodsSkuNameByCodeTwo(goodsBuySalesRespVo.getCategoryCodeTwo());
                GoodsBuySalesRespVo goodsBuySalesRespVosThree = reportDao.selectGoodsSkuNameByCodeThree(goodsBuySalesRespVo.getCategoryCodeThree());
                GoodsBuySalesRespVo goodsBuySalesRespVosFour = reportDao.selectGoodsSkuNameByCode(goodsBuySalesRespVo.getCategoryCode());
                if(goodsBuySalesRespVosOne != null){ goodsBuySalesRespVo.setCategoryNameOne(goodsBuySalesRespVosOne.getCategoryNameOne()); }
                if(goodsBuySalesRespVosTwo != null){ goodsBuySalesRespVo.setCategoryNameTwo(goodsBuySalesRespVosTwo.getCategoryNameTwo()); }
                if(goodsBuySalesRespVosThree != null){ goodsBuySalesRespVo.setCategoryNameThree(goodsBuySalesRespVosThree.getCategoryNameThree()); }
                if(goodsBuySalesRespVosFour != null){goodsBuySalesRespVo.setCategoryName(goodsBuySalesRespVosFour.getCategoryName());}
            }
            List<Map> maps = reportDao.selectGoodsBuySalesTableCloumnName();
            GoodsBuySalesRespVo goodsBuySalesRespVo = new GoodsBuySalesRespVo();
            goodsBuySalesRespVo.setColumnList(maps);
            return new PageReportResData<GoodsBuySalesRespVo>(total,goodsBuySalesRespVos,goodsBuySalesRespVo);
        } catch (Exception ex) {
            log.error("查询批次商品进销存失败");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  赠品进销存
     * @param giftsBuySalesReqVo
     * @return
     */
    @Override
    public PageReportResData selectGiftsBuySales(GiftsBuySalesReqVo giftsBuySalesReqVo) {
        try {
            List<GiftsBuySalesRespVo> giftsBuySalesRespVos = reportDao.selectGiftsBuySales(giftsBuySalesReqVo);
            Integer total = reportDao.countGiftsBuySales(giftsBuySalesReqVo);
            for (GiftsBuySalesRespVo giftsBuySalesRespVo : giftsBuySalesRespVos) {
                GiftsBuySalesRespVo giftsBuySalesRespVosOne = reportDao.selectGiftsSkuNameByCodeOne(giftsBuySalesRespVo.getCategoryCodeOne());
                GiftsBuySalesRespVo giftsBuySalesRespVosTwo = reportDao.selectGiftsSkuNameByCodeTwo(giftsBuySalesRespVo.getCategoryCodeTwo());
                GiftsBuySalesRespVo giftsBuySalesRespVosThree = reportDao.selectGiftsSkuNameByCodeThree(giftsBuySalesRespVo.getCategoryCodeThree());
                GiftsBuySalesRespVo giftsBuySalesRespVosFour = reportDao.selectGiftsSkuNameByCode(giftsBuySalesRespVo.getCategoryCode());
                if(giftsBuySalesRespVosOne != null){ giftsBuySalesRespVo.setCategoryNameOne(giftsBuySalesRespVosOne.getCategoryNameOne()); }
                if(giftsBuySalesRespVosTwo != null){ giftsBuySalesRespVo.setCategoryNameTwo(giftsBuySalesRespVosTwo.getCategoryNameTwo()); }
                if(giftsBuySalesRespVosThree != null){ giftsBuySalesRespVo.setCategoryNameThree(giftsBuySalesRespVosThree.getCategoryNameThree()); }
                if(giftsBuySalesRespVosFour != null){giftsBuySalesRespVo.setCategoryName(giftsBuySalesRespVosFour.getCategoryName());}
            }
            String cloumnName = "bi_gifts_buy_sales";
            List<Map> maps = reportDao.selectTableCloumnName(cloumnName);
            GiftsBuySalesRespVo giftsBuySalesRespVo = new GiftsBuySalesRespVo();
            giftsBuySalesRespVo.setColumnList(maps);
            return new PageReportResData<GiftsBuySalesRespVo>(total,giftsBuySalesRespVos,giftsBuySalesRespVo);
        } catch (Exception ex) {
            log.error("查询赠品进销存失败");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  供应商退货(退供)
     * @param supplierReturnReqVo
     * @return
     */
    @Override
    public PageReportResData selectSupplierReturn(SupplierReturnReqVo supplierReturnReqVo) {
        try {
            List<SupplierReturnRespVo> supplierReturnRespVos = reportDao.selectSupplierReturn(supplierReturnReqVo);
            Integer total = reportDao.countSupplierReturn(supplierReturnReqVo);
            SupplierReturnRespVo supplierReturnRespVoSum = reportDao.sumSupplierReturn(supplierReturnReqVo);
            String cloumnName = "bi_supplier_return";
            List<Map> maps = reportDao.selectTableCloumnName(cloumnName);
            SupplierReturnRespVo supplierReturnRespVo = new SupplierReturnRespVo();
            supplierReturnRespVo.setColumnList(maps);
            if(supplierReturnRespVoSum != null){
                supplierReturnRespVo.setSumCounts(supplierReturnRespVoSum.getSumCounts());
                supplierReturnRespVo.setSumAmounts(supplierReturnRespVoSum.getSumAmounts());
            }
            return new PageReportResData<SupplierReturnRespVo>(total,supplierReturnRespVos,supplierReturnRespVo);
        } catch (Exception ex) {
            log.error("查询供应商退货失败");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  新品批次动销率
     * @param newProductBatchMovingRateReqVo
     * @return
     */
    @Override
    public PageReportResData selectNewProductBatchMovingRate(NewProductBatchMovingRateReqVo newProductBatchMovingRateReqVo) {
        try {
            List<NewProductBatchMovingRateRespVo> newProductBatchMovingRateRespVos = reportDao.selectNewProductBatchMovingRate(newProductBatchMovingRateReqVo);
            Integer total = reportDao.countNewProductBatchMovingRate(newProductBatchMovingRateReqVo);
            for (NewProductBatchMovingRateRespVo newProductBatchMovingRateRespVo : newProductBatchMovingRateRespVos) {
                NewProductBatchMovingRateRespVo newProductBatchMovingRateRespVosOne = reportDao.selectNewProductSkuNameByCodeOne(newProductBatchMovingRateRespVo.getCategoryCodeOne());
                NewProductBatchMovingRateRespVo newProductBatchMovingRateRespVosTwo = reportDao.selectNewProductSkuNameByCodeTwo(newProductBatchMovingRateRespVo.getCategoryCodeTwo());
                NewProductBatchMovingRateRespVo newProductBatchMovingRateRespVosThree = reportDao.selectNewProductSkuNameByCodeThree(newProductBatchMovingRateRespVo.getCategoryCodeThree());
                NewProductBatchMovingRateRespVo newProductBatchMovingRateRespVosFour = reportDao.selectNewProductSkuNameByCode(newProductBatchMovingRateRespVo.getCategoryCode());
                if(newProductBatchMovingRateRespVosOne != null){ newProductBatchMovingRateRespVo.setCategoryNameOne(newProductBatchMovingRateRespVosOne.getCategoryNameOne()); }
                if(newProductBatchMovingRateRespVosTwo != null){ newProductBatchMovingRateRespVo.setCategoryNameTwo(newProductBatchMovingRateRespVosTwo.getCategoryNameTwo()); }
                if(newProductBatchMovingRateRespVosThree != null){ newProductBatchMovingRateRespVo.setCategoryNameThree(newProductBatchMovingRateRespVosThree.getCategoryNameThree()); }
                if(newProductBatchMovingRateRespVosFour != null){newProductBatchMovingRateRespVo.setCategoryName(newProductBatchMovingRateRespVosFour.getCategoryName());}
            }
            String cloumnName = "bi_new_product_batch_moving_rate";
            List<Map> maps = reportDao.selectNewProductBatchMovingRateTableCloumnName(cloumnName);
            NewProductBatchMovingRateRespVo newProductBatchMovingRateRespVo = new NewProductBatchMovingRateRespVo();
            newProductBatchMovingRateRespVo.setColumnList(maps);
            return new PageReportResData<NewProductBatchMovingRateRespVo>(total,newProductBatchMovingRateRespVos,newProductBatchMovingRateRespVo);
        } catch (Exception ex) {
            log.error("查询新品批次动销率失败");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  门店复购率
     * @param storeRepurchaseRateReqVo
     * @return
     */
    @Override
    public PageReportResData selectStoreRepurchaseRate(StoreRepurchaseRateReqVo storeRepurchaseRateReqVo) {
        try {
            List<StoreRepurchaseRateRespVo> storeRepurchaseRateRespVos = reportDao.selectStoreRepurchaseRate(storeRepurchaseRateReqVo);
            StoreRepurchaseRateRespVo storeRepurchaseRateRespVoSum = reportDao.sumStoreRepurchaseRate(storeRepurchaseRateReqVo);
            Integer total = reportDao.countStoreRepurchaseRate(storeRepurchaseRateReqVo);
            String cloumnName = "bi_store_repurchase_rate";
            List<Map> maps = reportDao.selectNewProductBatchMovingRateTableCloumnName(cloumnName);
            StoreRepurchaseRateRespVo storeRepurchaseRateRespVo = new StoreRepurchaseRateRespVo();
            storeRepurchaseRateRespVo.setColumnList(maps);
            if(storeRepurchaseRateRespVoSum != null){
                storeRepurchaseRateRespVo.setProductNum(storeRepurchaseRateRespVoSum.getProductNum());
                storeRepurchaseRateRespVo.setChannelOrderAmount(storeRepurchaseRateRespVoSum.getChannelOrderAmount());
                storeRepurchaseRateRespVo.setDistributionOrderAmount(storeRepurchaseRateRespVoSum.getDistributionOrderAmount());
            }
            return new PageReportResData<StoreRepurchaseRateRespVo>(total,storeRepurchaseRateRespVos,storeRepurchaseRateRespVo);
        } catch (Exception ex) {
            log.error("查询门店复购率失败");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  负毛利
     * @param negativeMarginReqVo
     * @return
     */
    @Override
    public PageReportResData selectNegativeMargin(NegativeMarginReqVo negativeMarginReqVo) {
        try {
            List<NegativeMarginRespVo> negativeMarginRespVos = reportDao.selectNegativeMargin(negativeMarginReqVo);
            NegativeMarginRespVo negativeMarginRespVoSum = reportDao.sumNegativeMargin(negativeMarginReqVo);
            Integer total = reportDao.countNegativeMargin(negativeMarginReqVo);
            String cloumnName = "bi_negative_margin";
            List<Map> maps = reportDao.selectNewProductBatchMovingRateTableCloumnName(cloumnName);
            NegativeMarginRespVo negativeMarginRespVo = new NegativeMarginRespVo();
            negativeMarginRespVo.setColumnList(maps);
            if(negativeMarginRespVoSum != null){
                negativeMarginRespVo.setProductNums(negativeMarginRespVoSum.getProductNums());
                negativeMarginRespVo.setSalesCosts(negativeMarginRespVoSum.getSalesCosts());
                negativeMarginRespVo.setChannelOrderAmounts(negativeMarginRespVoSum.getChannelOrderAmounts());
                negativeMarginRespVo.setChannelMaoris(negativeMarginRespVoSum.getChannelMaoris());
                negativeMarginRespVo.setChannelMaoriRates(negativeMarginRespVoSum.getChannelMaoriRates());
                negativeMarginRespVo.setDistributionOrderAmounts(negativeMarginRespVoSum.getDistributionOrderAmounts());
                negativeMarginRespVo.setDistributionMaoris(negativeMarginRespVoSum.getDistributionMaoris());
                negativeMarginRespVo.setDistributionMaoriRates(negativeMarginRespVoSum.getDistributionMaoriRates());
            }
            return new PageReportResData<NegativeMarginRespVo>(total,negativeMarginRespVos,negativeMarginRespVo);
        } catch (Exception ex) {
            log.error("查询负毛利失败");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  建议补货
     * @param suggestReplenishmentReqVo
     * @return
     */
    @Override
    public PageReportResData selectSuggestReplenishment(SuggestReplenishmentReqVo suggestReplenishmentReqVo) {
        try {
            List<SuggestReplenishmentRespVo> suggestReplenishmentRespVos = reportDao.selectSuggestReplenishment(suggestReplenishmentReqVo);
            Integer total = reportDao.countSuggestReplenishment(suggestReplenishmentReqVo);
            String cloumnName = "bi_app_suggest_replenishment";
            List<Map> maps = reportDao.selectNewProductBatchMovingRateTableCloumnName(cloumnName);
            SuggestReplenishmentRespVo suggestReplenishmentRespVo = new SuggestReplenishmentRespVo();
            suggestReplenishmentRespVo.setColumnList(maps);
            return new PageReportResData<SuggestReplenishmentRespVo>(total,suggestReplenishmentRespVos,suggestReplenishmentRespVo);
        } catch (Exception ex) {
            log.error("查询建议补货失败");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  低库存
     * @param highLowInventoryReqVo
     * @return
     */
    @Override
    public PageReportResData selectLowInventory(HighLowInventoryReqVo highLowInventoryReqVo) {
        try {
            List<LowInventoryRespVo> lowInventoryRespVos = reportDao.selectLowInventory(highLowInventoryReqVo);
            LowInventoryRespVo lowInventoryRespVoSum = reportDao.sumLowInventory(highLowInventoryReqVo);
            Integer total = reportDao.countLowInventory(highLowInventoryReqVo);
            String cloumnName = "bi_low_inventory";
            List<Map> maps = reportDao.selectNewProductBatchMovingRateTableCloumnName(cloumnName);
            LowInventoryRespVo lowInventoryRespVo = new LowInventoryRespVo();
            lowInventoryRespVo.setColumnList(maps);
            if (lowInventoryRespVoSum != null){
                lowInventoryRespVo.setSzqgTotalSkuNum(lowInventoryRespVoSum.getSzqgTotalSkuNum());
                lowInventoryRespVo.setSzqgLowSkuNum(lowInventoryRespVoSum.getSzqgLowSkuNum());
                lowInventoryRespVo.setSzqgLowInventoryRatio(lowInventoryRespVoSum.getSzqgLowInventoryRatio());
                lowInventoryRespVo.setBzqgTotalSkuNum(lowInventoryRespVoSum.getBzqgTotalSkuNum());
                lowInventoryRespVo.setBzqgLowSkuNum(lowInventoryRespVoSum.getBzqgLowSkuNum());
                lowInventoryRespVo.setBzqgLowInventoryRatio(lowInventoryRespVoSum.getBzqgLowInventoryRatio());
                lowInventoryRespVo.setHbTotalSkuNum(lowInventoryRespVoSum.getHbTotalSkuNum());
                lowInventoryRespVo.setHbLowSkuNum(lowInventoryRespVoSum.getHbLowSkuNum());
                lowInventoryRespVo.setHbLowInventoryRatio(lowInventoryRespVoSum.getHbLowInventoryRatio());
                lowInventoryRespVo.setHnTotalSkuNum(lowInventoryRespVoSum.getHnTotalSkuNum());
                lowInventoryRespVo.setHnLowSkuNum(lowInventoryRespVoSum.getHnLowSkuNum());
                lowInventoryRespVo.setHnLowInventoryRatio(lowInventoryRespVoSum.getHnLowInventoryRatio());
                lowInventoryRespVo.setXnTotalSkuNum(lowInventoryRespVoSum.getXnTotalSkuNum());
                lowInventoryRespVo.setXnLowSkuNum(lowInventoryRespVoSum.getXnLowSkuNum());
                lowInventoryRespVo.setXnLowInventoryRatio(lowInventoryRespVoSum.getXnLowInventoryRatio());
                lowInventoryRespVo.setHdTotalSkuNum(lowInventoryRespVoSum.getHdTotalSkuNum());
                lowInventoryRespVo.setHdLowSkuNum(lowInventoryRespVoSum.getHdLowSkuNum());
                lowInventoryRespVo.setHdLowInventoryRatio(lowInventoryRespVoSum.getHdLowInventoryRatio());
            }
            return new PageReportResData<LowInventoryRespVo>(total,lowInventoryRespVos,lowInventoryRespVo);
        } catch (Exception ex) {
            log.error("查询低库存失败");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  高库存
     * @param highLowInventoryReqVo
     * @return
     */
    @Override
    public PageReportResData selectHighInventory(HighLowInventoryReqVo highLowInventoryReqVo) {
        try {
            List<HighInventoryRespVo> highInventoryRespVos = reportDao.selectHighInventory(highLowInventoryReqVo);
            HighInventoryRespVo highInventoryRespVoSum = reportDao.sumHighInventory(highLowInventoryReqVo);
            Integer total = reportDao.countHighInventory(highLowInventoryReqVo);
            String cloumnName = "bi_low_inventory";
            List<Map> maps = reportDao.selectNewProductBatchMovingRateTableCloumnName(cloumnName);
            HighInventoryRespVo highInventoryRespVo = new HighInventoryRespVo();
            highInventoryRespVo.setColumnList(maps);
            if(highInventoryRespVoSum != null){
                highInventoryRespVo.setSzqgHighInventoryAmount(highInventoryRespVoSum.getSzqgHighInventoryAmount());
                highInventoryRespVo.setSzqgTotalInventoryAmount(highInventoryRespVoSum.getSzqgTotalInventoryAmount());
                highInventoryRespVo.setSzqgRate(highInventoryRespVoSum.getSzqgRate());
                highInventoryRespVo.setBzqgHighInventoryAmount(highInventoryRespVoSum.getBzqgHighInventoryAmount());
                highInventoryRespVo.setBzqgTotalInventoryAmount(highInventoryRespVoSum.getBzqgTotalInventoryAmount());
                highInventoryRespVo.setBzqgRate(highInventoryRespVoSum.getBzqgRate());
                highInventoryRespVo.setHbHighInventoryAmount(highInventoryRespVoSum.getHbHighInventoryAmount());
                highInventoryRespVo.setHbTotalInventoryAmount(highInventoryRespVoSum.getHbTotalInventoryAmount());
                highInventoryRespVo.setHbRate(highInventoryRespVoSum.getHbRate());
                highInventoryRespVo.setHnHighInventoryAmount(highInventoryRespVoSum.getHnHighInventoryAmount());
                highInventoryRespVo.setHnTotalInventoryAmount(highInventoryRespVoSum.getHnTotalInventoryAmount());
                highInventoryRespVo.setHnRate(highInventoryRespVoSum.getHnRate());
                highInventoryRespVo.setXnHighInventoryAmount(highInventoryRespVoSum.getXnHighInventoryAmount());
                highInventoryRespVo.setXnTotalInventoryAmount(highInventoryRespVoSum.getXnTotalInventoryAmount());
                highInventoryRespVo.setXnRate(highInventoryRespVoSum.getXnRate());
                highInventoryRespVo.setHdHhighInventoryAmount(highInventoryRespVoSum.getHdHhighInventoryAmount());
                highInventoryRespVo.setHdTotalInventoryAmount(highInventoryRespVoSum.getHdTotalInventoryAmount());
                highInventoryRespVo.setHdRate(highInventoryRespVoSum.getHdRate());

            }
            return new PageReportResData<HighInventoryRespVo>(total,highInventoryRespVos,highInventoryRespVo);
        } catch (Exception ex) {
            log.error("查询高库存失败");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  品牌促销
     * @param brandSaleReqVo
     * @return
     */
    @Override
    public PageReportResData selectBrandSale(CategorySaleReqVo brandSaleReqVo) {
        try {
            List<BrandSaleRespVo> brandSaleRespVos = reportDao.selectBrandSale(brandSaleReqVo);
            BrandSaleRespVo brandSaleRespVoSum = reportDao.sumBrandSale(brandSaleReqVo);
            Integer total = reportDao.countBrandSale(brandSaleReqVo);
            String cloumnName = "bi_brand_sale";
            List<Map> maps = reportDao.selectNewProductBatchMovingRateTableCloumnName(cloumnName);
            BrandSaleRespVo brandSaleRespVo = new BrandSaleRespVo();
            brandSaleRespVo.setColumnList(maps);
            if(brandSaleRespVoSum != null){
                brandSaleRespVo.setChannelSaleAmount(brandSaleRespVoSum.getChannelSaleAmount());
                brandSaleRespVo.setLastMonthChannelAmountDaily(brandSaleRespVoSum.getLastMonthChannelAmountDaily());
                brandSaleRespVo.setChannelGrossMargin(brandSaleRespVoSum.getChannelGrossMargin());
                brandSaleRespVo.setLastMonthChannelGrossMargin(brandSaleRespVoSum.getLastMonthChannelGrossMargin());
                brandSaleRespVo.setChannelSalesSequentialAdd(brandSaleRespVoSum.getChannelSalesSequentialAdd());
                brandSaleRespVo.setChannelMaoriSequentialAdd(brandSaleRespVoSum.getChannelMaoriSequentialAdd());
                brandSaleRespVo.setDistributionAmountDaily(brandSaleRespVoSum.getDistributionAmountDaily());
                brandSaleRespVo.setLastMonthDistributionAmountDaily(brandSaleRespVoSum.getLastMonthDistributionAmountDaily());
                brandSaleRespVo.setDistributionAmount(brandSaleRespVoSum.getDistributionAmount());
                brandSaleRespVo.setLastMonthDistributionAmount(brandSaleRespVoSum.getLastMonthDistributionAmount());
                brandSaleRespVo.setDistributionSalesSequentialAdd(brandSaleRespVoSum.getDistributionSalesSequentialAdd());
                brandSaleRespVo.setDistributionMaoriSequentialAdd(brandSaleRespVoSum.getDistributionMaoriSequentialAdd());
            }
            return new PageReportResData<BrandSaleRespVo>(total,brandSaleRespVos,brandSaleRespVo);
        } catch (Exception ex) {
            log.error("品牌促销");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  品类促销
     * @param categorySaleReqVo
     * @return
     */
    @Override
    public PageReportResData selectCategorySale(CategorySaleReqVo categorySaleReqVo) {
        try {
            List<CategorySaleRespVo> categorySaleRespVos = reportDao.selectCategorySale(categorySaleReqVo);
            CategorySaleRespVo categorySaleRespVoSum = reportDao.sumCategorySale(categorySaleReqVo);
            Integer total = reportDao.countCategorySale(categorySaleReqVo);
            String cloumnName = "bi_category_sale";
            List<Map> maps = reportDao.selectNewProductBatchMovingRateTableCloumnName(cloumnName);
            CategorySaleRespVo categorySaleRespVo = new CategorySaleRespVo();
            categorySaleRespVo.setColumnList(maps);
            if(categorySaleRespVoSum != null){
                categorySaleRespVo.setChannelAmountDaily(categorySaleRespVoSum.getChannelAmountDaily());
                categorySaleRespVo.setLastMonthChannelAmountDaily(categorySaleRespVoSum.getLastMonthChannelAmountDaily());
                categorySaleRespVo.setChannelMaori(categorySaleRespVoSum.getChannelMaori());
                categorySaleRespVo.setLastMonthChannelMaori(categorySaleRespVoSum.getLastMonthChannelMaori());
                categorySaleRespVo.setChannelSalesSequentialAdd(categorySaleRespVoSum.getChannelSalesSequentialAdd());
                categorySaleRespVo.setChannelMaoriSequentialAdd(categorySaleRespVoSum.getChannelMaoriSequentialAdd());
                categorySaleRespVo.setDistributionAmountDaily(categorySaleRespVoSum.getDistributionAmountDaily());
                categorySaleRespVo.setLastMonthDistributionAmountDaily(categorySaleRespVoSum.getLastMonthDistributionAmountDaily());
                categorySaleRespVo.setDistributionAmount(categorySaleRespVoSum.getDistributionAmount());
                categorySaleRespVo.setLastMonthDistributionAmount(categorySaleRespVoSum.getLastMonthDistributionAmount());
                categorySaleRespVo.setDistributionSalesSequentialAdd(categorySaleRespVoSum.getDistributionSalesSequentialAdd());
                categorySaleRespVo.setDistributionMaoriSequentialAdd(categorySaleRespVoSum.getDistributionMaoriSequentialAdd());
            }
            return new PageReportResData<CategorySaleRespVo>(total,categorySaleRespVos,categorySaleRespVo);
        } catch (Exception ex) {
            log.error("品类促销");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  大效期
     * @param bigEffectReqVo
     * @return
     */
    @Override
    public PageReportResData selectBigEffect(BigEffectReqVo bigEffectReqVo) {
        try {
            List<BigEffectRespVo> bigEffectRespVos = reportDao.selectBigEffect(bigEffectReqVo);
            Integer total = reportDao.countBigEffect(bigEffectReqVo);
            String cloumnName = "bi_big_effect";
            List<Map> maps = reportDao.selectNewProductBatchMovingRateTableCloumnName(cloumnName);
            BigEffectRespVo bigEffectRespVo = new BigEffectRespVo();
            bigEffectRespVo.setColumnList(maps);
            return new PageReportResData<BigEffectRespVo>(total,bigEffectRespVos,bigEffectRespVo);
        } catch (Exception ex) {
            log.error("大效期");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  月销售情况
     * @param monthlySalesReqVo
     * @return
     */
    @Override
    public PageReportResData selectMonthlySales(MonthlySalesReqVo monthlySalesReqVo) {
        try {
            List<MonthlySalesRespVo> monthlySalesRespVos = reportDao.selectMonthlySales(monthlySalesReqVo);
            Integer total = reportDao.countMonthlySales(monthlySalesReqVo);
            String cloumnName = "bi_monthly_sales";
            List<Map> maps = reportDao.selectNewProductBatchMovingRateTableCloumnName(cloumnName);
            MonthlySalesRespVo monthlySalesRespVo = new MonthlySalesRespVo();
            monthlySalesRespVo.setColumnList(maps);
            return new PageReportResData<MonthlySalesRespVo>(total,monthlySalesRespVos,monthlySalesRespVo);
        } catch (Exception ex) {
            log.error("月销售情况");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  月毛利率情况
     * @param monthlyGrossMarginReqVo
     * @return
     */
    @Override
    public PageReportResData selectMonthlyGrossMargin(MonthlySalesReqVo monthlyGrossMarginReqVo) {
        try {
            List<MonthlyGrossMarginRespVo> monthlySalesRespVos = reportDao.selectMonthlyGrossMargin(monthlyGrossMarginReqVo);
            Integer total = reportDao.countMonthlyGrossMargin(monthlyGrossMarginReqVo);
            String cloumnName = "bi_month_gross_margin";
            List<Map> maps = reportDao.selectNewProductBatchMovingRateTableCloumnName(cloumnName);
            MonthlyGrossMarginRespVo monthlyGrossMarginRespVo = new MonthlyGrossMarginRespVo();
            monthlyGrossMarginRespVo.setColumnList(maps);
            return new PageReportResData<MonthlyGrossMarginRespVo>(total,monthlySalesRespVos,monthlyGrossMarginRespVo);
        } catch (Exception ex) {
            log.error("月毛利率情况");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  月销售达成情况
     * @param monthSalesAchievementReqVo
     * @return
     */
    @Override
    public PageReportResData selectMonthSalesAchievement(MonthSalesAchievementReqVo monthSalesAchievementReqVo) {
        try {
            List<MonthSalesAchievementRespVo> monthSalesAchievementRespVos = reportDao.selectMonthSalesAchievement(monthSalesAchievementReqVo);
            Integer total = reportDao.countMonthSalesAchievement(monthSalesAchievementReqVo);
            String cloumnName = "bi_month_sales_achievement";
            List<Map> maps = reportDao.selectNewProductBatchMovingRateTableCloumnName(cloumnName);
            MonthSalesAchievementRespVo monthSalesAchievementRespVo = new MonthSalesAchievementRespVo();
            monthSalesAchievementRespVo.setColumnList(maps);
            return new PageReportResData<MonthSalesAchievementRespVo>(total,monthSalesAchievementRespVos,monthSalesAchievementRespVo);
        } catch (Exception ex) {
            log.error("月销售达成情况");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  月累计品类销售情况
     * @param monthCumulativeBrandSalesReqVo
     * @return
     */
    @Override
    public PageReportResData selectMonthCumulativeBrandSales(MonthSalesAchievementReqVo monthCumulativeBrandSalesReqVo) {
        try {
            List<MonthCumulativeBrandSalesRespVo> monthCumulativeBrandSalesRespVos = reportDao.selectMonthCumulativeBrandSales(monthCumulativeBrandSalesReqVo);
            Integer total = reportDao.countMonthCumulativeBrandSales(monthCumulativeBrandSalesReqVo);
            String cloumnName = "bi_month_cumulative_brand_sales";
            List<Map> maps = reportDao.selectNewProductBatchMovingRateTableCloumnName(cloumnName);
            MonthCumulativeBrandSalesRespVo monthCumulativeBrandSalesRespVo = new MonthCumulativeBrandSalesRespVo();
            monthCumulativeBrandSalesRespVo.setColumnList(maps);
            return new PageReportResData<MonthCumulativeBrandSalesRespVo>(total,monthCumulativeBrandSalesRespVos,monthCumulativeBrandSalesRespVo);
        } catch (Exception ex) {
            log.error("月累计品类销售情况");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  月累计品类毛利率情况
     * @param monthCumulativeGrossProfitMarginReqVo
     * @return
     */
    @Override
    public PageReportResData selectMonthCumulativeGrossProfitMargin(MonthSalesAchievementReqVo monthCumulativeGrossProfitMarginReqVo) {
        try {
            List<MonthCumulativeGrossProfitMarginRespVo> monthCumulativeGrossProfitMarginRespVos = reportDao.selectMonthCumulativeGrossProfitMargin(monthCumulativeGrossProfitMarginReqVo);
            Integer total = reportDao.countMonthCumulativeGrossProfitMargin(monthCumulativeGrossProfitMarginReqVo);
            String cloumnName = "bi_month_cumulative_gross_profit_margin";
            List<Map> maps = reportDao.selectNewProductBatchMovingRateTableCloumnName(cloumnName);
            MonthCumulativeGrossProfitMarginRespVo monthCumulativeGrossProfitMarginRespVo = new MonthCumulativeGrossProfitMarginRespVo();
            monthCumulativeGrossProfitMarginRespVo.setColumnList(maps);
            return new PageReportResData<MonthCumulativeGrossProfitMarginRespVo>(total,monthCumulativeGrossProfitMarginRespVos,monthCumulativeGrossProfitMarginRespVo);
        } catch (Exception ex) {
            log.error("月累计品类毛利率情况");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     *  查询所有门店类型
     * @param
     * @return
     */
    @Override
    public List<MonthlySalesRespVo> selectStoreType() {
        return reportDao.selectStoreType();
    }

    /**
     *  查询所有数据类型
     * @param
     * @return
     */
    @Override
    public List<MonthlySalesRespVo> selectDataStyle() {
        return reportDao.selectDataStyle();
    }

    /**
     *  查询所有一级品类
     * @param
     * @return
     */
    @Override
    public List<String> selectAllOneCategory() {
        return reportDao.selectAllOneCategory();
    }

    @Override
    public List<HighInventoryRespVo> selectHighInventorys(HighLowInventoryReqVo highLowInventoryReqVo) {
        List<HighInventoryRespVo> lowInventoryRespVos = reportDao.selectHighInventory(highLowInventoryReqVo);
        return lowInventoryRespVos;
    }

    @Override
    public List<LowInventoryRespVo> selectLowInventorys(HighLowInventoryReqVo highLowInventoryReqVo) {
        List<LowInventoryRespVo> lowInventoryRespVos = reportDao.selectLowInventory(highLowInventoryReqVo);
        return lowInventoryRespVos;
    }

}
