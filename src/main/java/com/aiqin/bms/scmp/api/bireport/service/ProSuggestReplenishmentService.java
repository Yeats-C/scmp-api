package com.aiqin.bms.scmp.api.bireport.service;

import com.aiqin.bms.scmp.api.bireport.domain.response.editpurchase.ProReplenishmentOutStockRespVo;
import com.aiqin.ground.util.protocol.http.HttpResponse;


public interface ProSuggestReplenishmentService {

    /**
     * 查询14大A品建议补货
     * @return
     */
    ProReplenishmentOutStockRespVo selectSuggestReplenishmentByPro();

    /**
     * 获取畅销建议补货skuCode
     * @return
     */
    ProReplenishmentOutStockRespVo  selectSuggestReplenishmentBySell();

    /**
     * 查询14大A品缺货
     * @return
     */
    ProReplenishmentOutStockRespVo selectOutStockByPro();

    /**
     * 获取畅销缺货
     * @return
     */
    ProReplenishmentOutStockRespVo selectOutStockBySell();

}
