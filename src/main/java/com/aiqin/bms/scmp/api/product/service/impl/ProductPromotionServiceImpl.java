package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.product.domain.request.price.PricePromotionReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PriceApplyPromotionRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionDetailRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionRespVo;
import com.aiqin.bms.scmp.api.product.mapper.PricePromotionMapper;
import com.aiqin.bms.scmp.api.product.mapper.PricePromotionProductMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductApplyPromotionMapper;
import com.aiqin.bms.scmp.api.product.service.ProductPromotionService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: mamingze
 * @Date: 2019-11-06 10:58
 * @Description:
 */
@Service
@Slf4j
public class ProductPromotionServiceImpl extends BaseServiceImpl implements ProductPromotionService {

@Autowired
private PricePromotionMapper pricePromotionMapper;

@Autowired
private ProductApplyPromotionMapper productApplyPromotionMapper;
    @Override
    public BasePage<PricePromotionRespVo> list(PricePromotionReqVo priceApplyPromotionReqVo) {
        PageHelper.startPage(priceApplyPromotionReqVo.getPageNo(),priceApplyPromotionReqVo.getPageSize());

        //获取当前用户
        AuthToken authToken = getUser();
        return pricePromotionMapper.list(priceApplyPromotionReqVo);
    }

    @Override
    public PricePromotionRespVo detail(Long id) {
        PricePromotionRespVo respVo=pricePromotionMapper.loadById(id);
         //买赠列表
         List<PricePromotionDetailRespVo> buyPromotionDetailList= Lists.newArrayList();
         //满赠：金额
         List<PricePromotionDetailRespVo> enoughPromotionDetailMoneyList= Lists.newArrayList();
         //满赠：数量
         List<PricePromotionDetailRespVo> enoughPromotionDetailNumList= Lists.newArrayList();
         //满减：金额
         List<PricePromotionDetailRespVo> reducePromotionDetaiMoneylList= Lists.newArrayList();
         //满减：数量
         List<PricePromotionDetailRespVo> reducePromotionDetaiNumlList= Lists.newArrayList();
         //满折：金额
        List<PricePromotionDetailRespVo> discountPromotionDetailMoneyList= Lists.newArrayList();
         //满折数量
         List<PricePromotionDetailRespVo> discountPromotionDetailNumList= Lists.newArrayList();
        //获取对应的申请列表
        List<PriceApplyPromotionRespVo> priceApplyPromotionRespVoList=productApplyPromotionMapper.loadByPromotionNo(respVo.getPromotionNo());
        for (PriceApplyPromotionRespVo priceApplyPromotionRespVo:
        priceApplyPromotionRespVoList) {
            priceApplyPromotionRespVo.getPricePromotionDetailRespVoList();
        }


        return null;
    }
}
