package com.aiqin.bms.scmp.api.statistics.service;

import com.aiqin.bms.scmp.api.statistics.domain.request.OemSaleRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.oem.OemSaleResponse;

/**
 * @author: zhao shuai
 * @create: 2019-09-18
 **/
public interface OemSaleService {

    OemSaleResponse oemSale(OemSaleRequest request);
}
