package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.product.domain.request.price.PricePromotionReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionRespVo;
import com.aiqin.bms.scmp.api.product.mapper.PricePromotionProductMapper;
import com.aiqin.bms.scmp.api.product.service.ProductPromotionService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: mamingze
 * @Date: 2019-11-06 10:58
 * @Description:
 */
@Service
@Slf4j
public class ProductPromotionServiceImpl extends BaseServiceImpl implements ProductPromotionService {

@Autowired
private PricePromotionProductMapper productPromotionMapper;

    @Override
    public BasePage<PricePromotionRespVo> list(PricePromotionReqVo priceApplyPromotionReqVo) {
        PageHelper.startPage(priceApplyPromotionReqVo.getPageNo(),priceApplyPromotionReqVo.getPageSize());

        //获取当前用户
        AuthToken authToken = getUser();


        return productPromotionMapper.list(priceApplyPromotionReqVo);
    }
}
