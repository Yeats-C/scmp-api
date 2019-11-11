package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.request.price.PricePromotionReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionRespVo;
import org.springframework.stereotype.Service;

/**
 * @Auther: mamingze
 * @Date: 2019-11-06 10:53
 * @Description:
 */

public interface ProductPromotionService {

    BasePage<PricePromotionRespVo> list(PricePromotionReqVo priceApplyPromotionReqVo);
}
