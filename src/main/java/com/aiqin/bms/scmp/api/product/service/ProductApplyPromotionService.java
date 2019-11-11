package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.request.price.PriceApplyPromotionReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PriceApplyPromotionRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionRespVo;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: mamingze
 * @Date: 2019-11-05 10:51
 * @Description:
 */

public interface ProductApplyPromotionService {
    /**
     * 获取申请促销列表
     * @param priceApplyPromotionReqVo
     * @return
     */
    BasePage<PriceApplyPromotionRespVo> list(PriceApplyPromotionReqVo priceApplyPromotionReqVo);

    /**
     * 新增促销
     * @param priceApplyPromotionReqVo
     * @return
     */
    Boolean add(PriceApplyPromotionReqVo priceApplyPromotionReqVo) throws Exception;

    /**
     * 修改促销
     * @param priceApplyPromotionReqVo
     * @return
     */
    Boolean updateOrDelete(PriceApplyPromotionReqVo priceApplyPromotionReqVo,Integer type);

    /**
     * 查看详情
     * @param id
     * @return
     */
    PriceApplyPromotionRespVo load(Long id);

    /**
     * 生成促销单
     * @param priceApplyPromotionReqVoList
     * @return
     */
    Boolean generatePromotion(List<PriceApplyPromotionReqVo> priceApplyPromotionReqVoList);

    /**
     * 获取当前用户的的所属销售组名称
     * @return
     */
    List<String> loadNamesByPersonId();


    /**
     * 审批流调入
     * @param formNo
     * @param applyCode
     * @param userName
     */
    void workFlow(String formNo, String applyCode, String userName,String directSupervisorCode,String approvalName,String approvalRemark);


}
