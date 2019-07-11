package com.aiqin.bms.scmp.api.bireport.service.impl;

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
    public PageInfo<SupplierArrivalRateRespVo> selectSupplierArrivalRate(SupplierArrivalRateReqVo supplierArrivalRateReqVo) {
        try {
            PageHelper.startPage(supplierArrivalRateReqVo.getPageNo(), supplierArrivalRateReqVo.getPageSize());
            return new PageInfo<SupplierArrivalRateRespVo>(reportDao.selectSupplierArrivalRate(supplierArrivalRateReqVo));
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
}
