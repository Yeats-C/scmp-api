package com.aiqin.bms.scmp.api.bireport.service.impl;

import com.aiqin.bms.scmp.api.bireport.dao.ReportDao;
import com.aiqin.bms.scmp.api.bireport.domain.request.GiftsBuySalesReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.request.GoodsBuySalesReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.request.SupplierArrivalRateReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.GiftsBuySalesRespVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.GoodsBuySalesRespVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.SupplierArrivalRateRespVo;
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
}
