package com.aiqin.bms.scmp.api.bireport.service;

import com.aiqin.bms.scmp.api.bireport.domain.request.GiftsBuySalesReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.request.GoodsBuySalesReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.request.SupplierArrivalRateReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.GiftsBuySalesRespVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.GoodsBuySalesRespVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.SupplierArrivalRateRespVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ReportService {

    /**
     *  供应商到货率
     * @param supplierArrivalRateReqVo
     * @return
     */
    PageInfo<SupplierArrivalRateRespVo> selectSupplierArrivalRate(SupplierArrivalRateReqVo supplierArrivalRateReqVo);

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
}
