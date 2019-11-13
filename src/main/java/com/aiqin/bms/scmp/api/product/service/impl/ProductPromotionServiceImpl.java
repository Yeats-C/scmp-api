package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.product.domain.request.price.PricePromotionReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PriceApplyPromotionRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionDetailRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionProductRespVo;
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
import java.util.stream.Collectors;

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

@Autowired
private PricePromotionProductMapper pricePromotionProductMapper;

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
            //进行买赠的添加
            List<PricePromotionDetailRespVo> b=priceApplyPromotionRespVo.getPricePromotionDetailRespVoList().stream().filter(x->x.getPromotionType().equals(1)).collect(Collectors.toList());
              getProductIn(b,priceApplyPromotionRespVo.getId());
            buyPromotionDetailList.addAll(b);

            //进行满赠金额的添加
            List<PricePromotionDetailRespVo> em=priceApplyPromotionRespVo.getPricePromotionDetailRespVoList().stream().filter(x->x.getPromotionType().equals(2)).filter(x->x.getRuleType().equals(0)).collect(Collectors.toList());
            getProductIn(em,priceApplyPromotionRespVo.getId());
            enoughPromotionDetailMoneyList.addAll(em);

            //进行满赠数量的添加
            List<PricePromotionDetailRespVo> en=priceApplyPromotionRespVo.getPricePromotionDetailRespVoList().stream().filter(x->x.getPromotionType().equals(2)).filter(x->x.getRuleType().equals(1)).collect(Collectors.toList());
            getProductIn(en,priceApplyPromotionRespVo.getId());
            enoughPromotionDetailNumList.addAll(en);


            //进行满减金额的添加
            List<PricePromotionDetailRespVo> rm=priceApplyPromotionRespVo.getPricePromotionDetailRespVoList().stream().filter(x->x.getPromotionType().equals(3)).filter(x->x.getRuleType().equals(0)).collect(Collectors.toList());
            getProductIn(rm,priceApplyPromotionRespVo.getId());
            reducePromotionDetaiMoneylList.addAll(rm);

            //进行满减数量的添加
            List<PricePromotionDetailRespVo> rn=priceApplyPromotionRespVo.getPricePromotionDetailRespVoList().stream().filter(x->x.getPromotionType().equals(3)).filter(x->x.getRuleType().equals(1)).collect(Collectors.toList());
            getProductIn(rn,priceApplyPromotionRespVo.getId());
            reducePromotionDetaiNumlList.addAll(rn);

            //进行满折金额的添加
            List<PricePromotionDetailRespVo> dm=priceApplyPromotionRespVo.getPricePromotionDetailRespVoList().stream().filter(x->x.getPromotionType().equals(4)).filter(x->x.getRuleType().equals(0)).collect(Collectors.toList());
            getProductIn(dm,priceApplyPromotionRespVo.getId());
            discountPromotionDetailMoneyList.addAll(dm);

            //进行满折数量的添加
            List<PricePromotionDetailRespVo> dn=priceApplyPromotionRespVo.getPricePromotionDetailRespVoList().stream().filter(x->x.getPromotionType().equals(4)).filter(x->x.getRuleType().equals(1)).collect(Collectors.toList());
            getProductIn(dn,priceApplyPromotionRespVo.getId());
            discountPromotionDetailNumList.addAll(dn);

        }
        respVo.setBuyPromotionDetailList(buyPromotionDetailList);
        respVo.setEnoughPromotionDetailMoneyList(enoughPromotionDetailMoneyList);
        respVo.setReducePromotionDetaiNumlList(enoughPromotionDetailNumList);
        respVo.setReducePromotionDetaiMoneylList(reducePromotionDetaiMoneylList);
        respVo.setReducePromotionDetaiNumlList(reducePromotionDetaiNumlList);
        respVo.setDiscountPromotionDetailMoneyList(discountPromotionDetailMoneyList);
        respVo.setReducePromotionDetaiNumlList(discountPromotionDetailNumList);
        return respVo;
    }


    void getProductIn(List<PricePromotionDetailRespVo> pricePromotionProductRespVoList,Long businessId){
        for (PricePromotionDetailRespVo p:
                pricePromotionProductRespVoList) {
            List<PricePromotionProductRespVo> pricePromotionProductRespVos  =pricePromotionProductMapper.loadByBusinessId(businessId);
            p.setPricePromotionProductRespVoList(pricePromotionProductRespVos);
        }
    }
}
