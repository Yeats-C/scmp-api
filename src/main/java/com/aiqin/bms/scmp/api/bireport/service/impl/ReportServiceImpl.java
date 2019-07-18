package com.aiqin.bms.scmp.api.bireport.service.impl;

import com.aiqin.bms.scmp.api.base.PageReportResData;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.bireport.dao.ReportDao;
import com.aiqin.bms.scmp.api.bireport.domain.request.*;
import com.aiqin.bms.scmp.api.bireport.domain.response.*;
import com.aiqin.bms.scmp.api.bireport.service.ReportService;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
           // PageHelper.startPage(supplierArrivalRateReqVo.getPageNo(), supplierArrivalRateReqVo.getPageSize());
            List<SupplierArrivalRateRespVo> supplierArrivalRateRespVos = reportDao.selectSupplierArrivalRate(supplierArrivalRateReqVo);
            List<Map> maps = reportDao.selectSupplierArrivalRateTableCloumnName();
            Integer total = reportDao.countSupplierArrivalRate(supplierArrivalRateReqVo);
            return new PageReportResData<SupplierArrivalRateRespVo>(total,supplierArrivalRateRespVos,maps);
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
            // PageHelper.startPage(goodsBuySalesReqVo.getPageNo(), goodsBuySalesReqVo.getPageSize());
            List<GoodsBuySalesRespVo> goodsBuySalesRespVos = reportDao.selectGoodsBuySales(goodsBuySalesReqVo);
            Integer total = reportDao.countGoodsBuySales(goodsBuySalesReqVo);
            List<Map> maps = reportDao.selectGoodsBuySalesTableCloumnName();
            return new PageReportResData<GoodsBuySalesRespVo>(total,goodsBuySalesRespVos,maps);
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
            // PageHelper.startPage(giftsBuySalesReqVo.getPageNo(), giftsBuySalesReqVo.getPageSize());
            List<GiftsBuySalesRespVo> giftsBuySalesRespVos = reportDao.selectGiftsBuySales(giftsBuySalesReqVo);
            Integer total = reportDao.countGiftsBuySales(giftsBuySalesReqVo);
            String cloumnName = "bi_gifts_buy_sales";
            List<Map> maps = reportDao.selectTableCloumnName(cloumnName);
            return new PageReportResData<GiftsBuySalesRespVo>(total,giftsBuySalesRespVos,maps);
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
    public PageResData selectSupplierReturn(SupplierReturnReqVo supplierReturnReqVo) {
        try {
            // PageHelper.startPage(supplierReturnReqVo.getPageNo(), supplierReturnReqVo.getPageSize());
            List<SupplierReturnRespVo> supplierReturnRespVos = reportDao.selectSupplierReturn(supplierReturnReqVo);
            Integer total = reportDao.countSupplierReturn(supplierReturnReqVo);
            return new PageResData<SupplierReturnRespVo>(total,supplierReturnRespVos);
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
    public PageResData selectNewProductBatchMovingRate(NewProductBatchMovingRateReqVo newProductBatchMovingRateReqVo) {
        try {
            // PageHelper.startPage(newProductBatchMovingRateReqVo.getPageNo(), newProductBatchMovingRateReqVo.getPageSize());
            List<NewProductBatchMovingRateRespVo> newProductBatchMovingRateRespVos = reportDao.selectNewProductBatchMovingRate(newProductBatchMovingRateReqVo);
            Integer total = reportDao.countNewProductBatchMovingRate(newProductBatchMovingRateReqVo);
            return new PageResData<NewProductBatchMovingRateRespVo>(total,newProductBatchMovingRateRespVos);
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
    public PageResData selectStoreRepurchaseRate(StoreRepurchaseRateReqVo storeRepurchaseRateReqVo) {
        try {
            // PageHelper.startPage(storeRepurchaseRateReqVo.getPageNo(), storeRepurchaseRateReqVo.getPageSize());
            List<StoreRepurchaseRateRespVo> storeRepurchaseRateRespVos = reportDao.selectStoreRepurchaseRate(storeRepurchaseRateReqVo);
            Integer total = reportDao.countStoreRepurchaseRate(storeRepurchaseRateReqVo);
            return new PageResData<StoreRepurchaseRateRespVo>(total,storeRepurchaseRateRespVos);
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
    public PageResData selectNegativeMargin(NegativeMarginReqVo negativeMarginReqVo) {
        try {
            // PageHelper.startPage(negativeMarginReqVo.getPageNo(), negativeMarginReqVo.getPageSize());
            List<NegativeMarginRespVo> negativeMarginRespVos = reportDao.selectNegativeMargin(negativeMarginReqVo);
            Integer total = reportDao.countNegativeMargin(negativeMarginReqVo);
            return new PageResData<NegativeMarginRespVo>(total,negativeMarginRespVos);
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
    public PageResData selectSuggestReplenishment(SuggestReplenishmentReqVo suggestReplenishmentReqVo) {
        try {
            // PageHelper.startPage(suggestReplenishmentReqVo.getPageNo(), suggestReplenishmentReqVo.getPageSize());
            List<SuggestReplenishmentRespVo> suggestReplenishmentRespVos = reportDao.selectSuggestReplenishment(suggestReplenishmentReqVo);
            Integer total = reportDao.countSuggestReplenishment(suggestReplenishmentReqVo);
            return new PageResData<SuggestReplenishmentRespVo>(total,suggestReplenishmentRespVos);
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
    public PageResData selectLowInventory(HighLowInventoryReqVo highLowInventoryReqVo) {
        try {
           // PageHelper.startPage(highLowInventoryReqVo.getPageNo(), highLowInventoryReqVo.getPageSize());
            List<LowInventoryRespVo> lowInventoryRespVos = reportDao.selectLowInventory(highLowInventoryReqVo);
            Integer total = reportDao.countLowInventory(highLowInventoryReqVo);
            return new PageResData<LowInventoryRespVo>(total,lowInventoryRespVos);
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
    public PageResData selectHighInventory(HighLowInventoryReqVo highLowInventoryReqVo) {
        try {
            // PageHelper.startPage(highLowInventoryReqVo.getPageNo(), highLowInventoryReqVo.getPageSize());
            List<HighInventoryRespVo> highInventoryRespVos = reportDao.selectHighInventory(highLowInventoryReqVo);
            Integer total = reportDao.countHighInventory(highLowInventoryReqVo);
            return new PageResData<HighInventoryRespVo>(total,highInventoryRespVos);
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
    public PageResData selectBrandSale(CategorySaleReqVo brandSaleReqVo) {
        try {
            // PageHelper.startPage(categorySaleReqVo.getPageNo(), categorySaleReqVo.getPageSize());
            List<CategorySaleRespVo> brandSaleRespVo = reportDao.selectBrandSale(brandSaleReqVo);
            Integer total = reportDao.countBrandSale(brandSaleReqVo);
            return new PageResData<CategorySaleRespVo>(total,brandSaleRespVo);
        } catch (Exception ex) {
            log.error("品类促销");
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
    public PageResData selectCategorySale(CategorySaleReqVo categorySaleReqVo) {
        try {
            // PageHelper.startPage(categorySaleReqVo.getPageNo(), categorySaleReqVo.getPageSize());
            List<CategorySaleRespVo> categorySaleRespVo = reportDao.selectCategorySale(categorySaleReqVo);
            Integer total = reportDao.countCategorySale(categorySaleReqVo);
            return new PageResData<CategorySaleRespVo>(total,categorySaleRespVo);
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
    public PageResData selectBigEffect(BigEffectReqVo bigEffectReqVo) {
        try {
            // PageHelper.startPage(bigEffectReqVo.getPageNo(), bigEffectReqVo.getPageSize());
            List<BigEffectRespVo> bigEffectRespVos = reportDao.selectBigEffect(bigEffectReqVo);
            Integer total = reportDao.countBigEffect(bigEffectReqVo);
            return new PageResData<BigEffectRespVo>(total,bigEffectRespVos);
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
    public PageResData selectMonthlySales(MonthlySalesReqVo monthlySalesReqVo) {
        try {
            // PageHelper.startPage(monthlySalesReqVo.getPageNo(), monthlySalesReqVo.getPageSize());
            List<MonthlySalesRespVo> monthlySalesRespVos = reportDao.selectMonthlySales(monthlySalesReqVo);
            Integer total = reportDao.countMonthlySales(monthlySalesReqVo);
            return new PageResData<MonthlySalesRespVo>(total,monthlySalesRespVos);
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
    public PageResData selectMonthlyGrossMargin(MonthlySalesReqVo monthlyGrossMarginReqVo) {
        try {
            // PageHelper.startPage(monthlyGrossMarginReqVo.getPageNo(), monthlyGrossMarginReqVo.getPageSize());
            List<MonthlyGrossMarginRespVo> monthlySalesRespVos = reportDao.selectMonthlyGrossMargin(monthlyGrossMarginReqVo);
            Integer total = reportDao.countMonthlyGrossMargin(monthlyGrossMarginReqVo);
            return new PageResData<MonthlyGrossMarginRespVo>(total,monthlySalesRespVos);
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
    public PageResData selectMonthSalesAchievement(MonthSalesAchievementReqVo monthSalesAchievementReqVo) {
        try {
           // PageHelper.startPage(monthSalesAchievementReqVo.getPageNo(), monthSalesAchievementReqVo.getPageSize());
            List<MonthSalesAchievementRespVo> monthSalesAchievementRespVos = reportDao.selectMonthSalesAchievement(monthSalesAchievementReqVo);
            Integer total = reportDao.countMonthSalesAchievement(monthSalesAchievementReqVo);
            return new PageResData<MonthSalesAchievementRespVo>(total,monthSalesAchievementRespVos);
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
    public PageResData selectMonthCumulativeBrandSales(MonthSalesAchievementReqVo monthCumulativeBrandSalesReqVo) {
        try {
          //  PageHelper.startPage(monthCumulativeBrandSalesReqVo.getPageNo(), monthCumulativeBrandSalesReqVo.getPageSize());
            List<MonthCumulativeBrandSalesRespVo> monthCumulativeBrandSalesRespVos = reportDao.selectMonthCumulativeBrandSales(monthCumulativeBrandSalesReqVo);
            Integer total = reportDao.countMonthCumulativeBrandSales(monthCumulativeBrandSalesReqVo);
            return new PageResData<MonthCumulativeBrandSalesRespVo>(total,monthCumulativeBrandSalesRespVos);
        } catch (Exception ex) {
            log.error("月销售达成情况");
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
    public PageResData selectMonthCumulativeGrossProfitMargin(MonthSalesAchievementReqVo monthCumulativeGrossProfitMarginReqVo) {
        try {
         //   PageHelper.startPage(monthCumulativeGrossProfitMarginReqVo.getPageNo(), monthCumulativeGrossProfitMarginReqVo.getPageSize());
            List<MonthCumulativeGrossProfitMarginRespVo> monthCumulativeGrossProfitMarginRespVos = reportDao.selectMonthCumulativeGrossProfitMargin(monthCumulativeGrossProfitMarginReqVo);
            Integer total = reportDao.countMonthCumulativeGrossProfitMargin(monthCumulativeGrossProfitMarginReqVo);
            return new PageResData<MonthCumulativeGrossProfitMarginRespVo>(total,monthCumulativeGrossProfitMarginRespVos);
        } catch (Exception ex) {
            log.error("月销售达成情况");
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
}
