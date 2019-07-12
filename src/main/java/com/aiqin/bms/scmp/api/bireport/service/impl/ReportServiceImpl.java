package com.aiqin.bms.scmp.api.bireport.service.impl;

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
    // public PageInfo<SupplierArrivalRateRespVo> selectSupplierArrivalRate(SupplierArrivalRateReqVo supplierArrivalRateReqVo) {
    public PageResData selectSupplierArrivalRate(SupplierArrivalRateReqVo supplierArrivalRateReqVo) {
        try {
            PageHelper.startPage(supplierArrivalRateReqVo.getPageNo(), supplierArrivalRateReqVo.getPageSize());
          // return new PageInfo<SupplierArrivalRateRespVo>(reportDao.selectSupplierArrivalRate(supplierArrivalRateReqVo));
            List<SupplierArrivalRateRespVo> supplierArrivalRateRespVos = reportDao.selectSupplierArrivalRate(supplierArrivalRateReqVo);
            Integer total = reportDao.countSupplierArrivalRate(supplierArrivalRateReqVo);
            return new PageResData<SupplierArrivalRateRespVo>(total,supplierArrivalRateRespVos);
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
    public PageInfo<GoodsBuySalesRespVo> selectGoodsBuySales(GoodsBuySalesReqVo goodsBuySalesReqVo) {
        try {
            PageHelper.startPage(goodsBuySalesReqVo.getPageNo(), goodsBuySalesReqVo.getPageSize());
            return new PageInfo<GoodsBuySalesRespVo>(reportDao.selectGoodsBuySales(goodsBuySalesReqVo));
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
    public PageInfo<GiftsBuySalesRespVo> selectGiftsBuySales(GiftsBuySalesReqVo giftsBuySalesReqVo) {
        try {
            PageHelper.startPage(giftsBuySalesReqVo.getPageNo(), giftsBuySalesReqVo.getPageSize());
            return new PageInfo<GiftsBuySalesRespVo>(reportDao.selectGiftsBuySales(giftsBuySalesReqVo));
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
    public PageInfo<SupplierReturnRespVo> selectSupplierReturn(SupplierReturnReqVo supplierReturnReqVo) {
        try {
            PageHelper.startPage(supplierReturnReqVo.getPageNo(), supplierReturnReqVo.getPageSize());
            return new PageInfo<SupplierReturnRespVo>(reportDao.selectSupplierReturn(supplierReturnReqVo));
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
    public PageInfo<NewProductBatchMovingRateRespVo> selectNewProductBatchMovingRate(NewProductBatchMovingRateReqVo newProductBatchMovingRateReqVo) {
        try {
            PageHelper.startPage(newProductBatchMovingRateReqVo.getPageNo(), newProductBatchMovingRateReqVo.getPageSize());
            return new PageInfo<NewProductBatchMovingRateRespVo>(reportDao.selectNewProductBatchMovingRate(newProductBatchMovingRateReqVo));
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
    public PageInfo<StoreRepurchaseRateRespVo> selectStoreRepurchaseRate(StoreRepurchaseRateReqVo storeRepurchaseRateReqVo) {
        try {
            PageHelper.startPage(storeRepurchaseRateReqVo.getPageNo(), storeRepurchaseRateReqVo.getPageSize());
            return new PageInfo<StoreRepurchaseRateRespVo>(reportDao.selectStoreRepurchaseRate(storeRepurchaseRateReqVo));
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
    public PageInfo<NegativeMarginRespVo> selectNegativeMargin(NegativeMarginReqVo negativeMarginReqVo) {
        try {
            PageHelper.startPage(negativeMarginReqVo.getPageNo(), negativeMarginReqVo.getPageSize());
            return new PageInfo<NegativeMarginRespVo>(reportDao.selectNegativeMargin(negativeMarginReqVo));
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
    public PageInfo<SuggestReplenishmentRespVo> selectSuggestReplenishment(SuggestReplenishmentReqVo suggestReplenishmentReqVo) {
        try {
            PageHelper.startPage(suggestReplenishmentReqVo.getPageNo(), suggestReplenishmentReqVo.getPageSize());
            return new PageInfo<SuggestReplenishmentRespVo>(reportDao.selectSuggestReplenishment(suggestReplenishmentReqVo));
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
    public PageInfo<LowInventoryRespVo> selectLowInventory(HighLowInventoryReqVo highLowInventoryReqVo) {
        try {
            PageHelper.startPage(highLowInventoryReqVo.getPageNo(), highLowInventoryReqVo.getPageSize());
            return new PageInfo<LowInventoryRespVo>(reportDao.selectLowInventory(highLowInventoryReqVo));
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
    public PageInfo<HighInventoryRespVo> selectHighInventory(HighLowInventoryReqVo highLowInventoryReqVo) {
        try {
            PageHelper.startPage(highLowInventoryReqVo.getPageNo(), highLowInventoryReqVo.getPageSize());
            return new PageInfo<HighInventoryRespVo>(reportDao.selectHighInventory(highLowInventoryReqVo));
        } catch (Exception ex) {
            log.error("查询高库存失败");
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
    public PageInfo<BigEffectRespVo> selectBigEffect(BigEffectReqVo bigEffectReqVo) {
        try {
            PageHelper.startPage(bigEffectReqVo.getPageNo(), bigEffectReqVo.getPageSize());
            return new PageInfo<BigEffectRespVo>(reportDao.selectBigEffect(bigEffectReqVo));
        } catch (Exception ex) {
            log.error("查询高库存失败");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }
}
