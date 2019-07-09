package com.aiqin.bms.scmp.api.bireport.service;

import com.aiqin.bms.scmp.api.bireport.domain.request.PurchaseApplyReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.editpurchase.ProReplenishmentOutStockRespVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.editpurchase.PurchaseApplyRespVo;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;


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

    /**
     * 获取采购申请列表
     * @param purchaseApplyReqVo
     * @return
     */
    List<PurchaseApplyRespVo> selectPurchaseApplySkuList(PurchaseApplyReqVo purchaseApplyReqVo);
}
