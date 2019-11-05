package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.request.price.PriceApplyPromotionReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PriceApplyPromotionRespVo;
import org.springframework.stereotype.Service;

/**
 * @Auther: mamingze
 * @Date: 2019-11-05 10:51
 * @Description:
 */
@Service
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
    Boolean add(PriceApplyPromotionReqVo priceApplyPromotionReqVo);

    /**
     * 修改促销
     * @param priceApplyPromotionReqVo
     * @return
     */
    Boolean updateOrDelete(PriceApplyPromotionReqVo priceApplyPromotionReqVo,Integer type);


    PriceApplyPromotionRespVo load(Long id);
}
