package com.aiqin.bms.scmp.api.bireport.service;

import com.aiqin.bms.scmp.api.bireport.domain.request.PurchaseApplyReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.editpurchase.PurchaseApplyRespVo;

import java.util.List;


public interface ProSuggestReplenishmentService {

    /**
     * 查询14大A品建议补货
     * @return
     */
    List<String> selectSuggestReplenishmentByPro();

    /**
     * 获取畅销建议补货skuCode
     * @return
     */
    List<String> selectSuggestReplenishmentBySell();

    /**
     * 查询14大A品缺货
     * @return
     */
    List<String> selectOutStockByPro();

    /**
     * 获取畅销缺货
     * @return
     */
    List<String> selectOutStockBySell();

    /**
     * 获取采购申请列表
     * @param purchaseApplyReqVo
     * @return
     */
    PurchaseApplyRespVo selectPurchaseApplySkuList(PurchaseApplyReqVo purchaseApplyReqVo);
}
