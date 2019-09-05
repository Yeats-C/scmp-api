package com.aiqin.bms.scmp.api.bireport.dao;

import com.aiqin.bms.scmp.api.bireport.domain.request.PurchaseApplyReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.editpurchase.ProReplenishmentOutStockRespVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.editpurchase.PurchaseApplyRespVo;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProSuggestReplenishmentDao {

    /**
     *  查询14大A品建议补货
     * @return
     */
    List<String> selectSuggestReplenishmentByPro(int proStatus);

    /**
     * 获取畅销建议补货
     * @return
     */
    List<String> selectSuggestReplenishmentBySell();

    /**
     *  查询14大A品缺货
     * @return
     */
    List<String> selectOutStockByPro(@Param("proStatus") int proStatus, @Param("continuousDays")  int continuousDays);

    /**
     * 获取畅销缺货
     * @return
     */
    List<String> selectOutStockBySell();

    /**
     * 获取采购申请列表
     * @return
     */
    List<PurchaseApplyRespVo> selectPurchaseApplySkuList(PurchaseApplyReqVo purchaseApplyReqVo);

    /**
     *  获取固定规则数量
     * @return
     */
    PurchaseApplyRespVo selectPurchaseRuleNum();
}
