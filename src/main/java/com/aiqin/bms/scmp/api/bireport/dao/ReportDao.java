package com.aiqin.bms.scmp.api.bireport.dao;

import com.aiqin.bms.scmp.api.bireport.domain.request.SupplierArrivalRateReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.SupplierArrivalRateRespVo;

import java.util.List;

public interface ReportDao {

    /**
     *  供应商到货率
     * @param supplierArrivalRateReqVo
     * @return
     */
    List<SupplierArrivalRateRespVo> selectSupplierArrivalRate(SupplierArrivalRateReqVo supplierArrivalRateReqVo);
}
