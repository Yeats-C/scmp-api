package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.product.domain.request.price.PriceApplyPromotionReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.price.PricePromotionDetailReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.price.PricePromotionProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PriceApplyPromotionRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionDetailRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionProductRespVo;
import com.aiqin.bms.scmp.api.product.mapper.PricePromotionDetailMapper;
import com.aiqin.bms.scmp.api.product.mapper.PricePromotionProductMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductApplyPromotionMapper;
import com.aiqin.bms.scmp.api.product.service.ProductApplyPromotionService;
import com.aiqin.bms.scmp.api.product.service.ProductApplyService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.IdSequenceUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: mamingze
 * @Date: 2019-11-05 11:18
 * @Description:
 */
public class ProductApplyPromotionServiceImpl extends BaseServiceImpl implements ProductApplyPromotionService {

  @Autowired
  private ProductApplyPromotionMapper productApplyPromotionMapper;

  @Autowired
  private PricePromotionDetailMapper pricePromotionDetailMapper;

    @Autowired
    private PricePromotionProductMapper pricePromotionProductMapper;

    @Override
    public BasePage<PriceApplyPromotionRespVo> list(PriceApplyPromotionReqVo priceApplyPromotionReqVo) {
       //获取当前用户
        AuthToken authToken = getUser();
     //   vo.setCompanyCode(authToken.getCompanyCode());
        PageHelper.startPage(priceApplyPromotionReqVo.getPageNo(),priceApplyPromotionReqVo.getPageSize());
        return productApplyPromotionMapper.list(priceApplyPromotionReqVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean add(PriceApplyPromotionReqVo priceApplyPromotionReqVo) {
        //获取当前用户
        AuthToken authToken = getUser();
        //   vo.setCompanyCode(authToken.getCompanyCode());
        priceApplyPromotionReqVo.setCreateName(authToken.getPersonName());
        Long promotionId= IdSequenceUtils.getInstance().nextId();
        priceApplyPromotionReqVo.setId(promotionId);
        productApplyPromotionMapper.insert(priceApplyPromotionReqVo);
        //进行下属规则的添加，主键关联
        addDetailAndproduct(priceApplyPromotionReqVo);
        return true;
    }



    @Override
    public Boolean updateOrDelete(PriceApplyPromotionReqVo priceApplyPromotionReqVo,Integer type) {

       //把规则下面的产品
        List<PricePromotionDetailRespVo> pricePromotionDetailRespVoList=pricePromotionDetailMapper.loadByPromotionId(priceApplyPromotionReqVo.getId());
        for (PricePromotionDetailRespVo pricePromotionDetailRespVo:
                pricePromotionDetailRespVoList) {
           pricePromotionProductMapper.deteleByBusinessId(pricePromotionDetailRespVo.getId());
        }
        //把原来促销下面的规则删除了
        pricePromotionDetailMapper.deteleByPromotionId(priceApplyPromotionReqVo.getId());
        //进行调整
       if(type==1){
           productApplyPromotionMapper.updateById(priceApplyPromotionReqVo);
           addDetailAndproduct(priceApplyPromotionReqVo);
       }else {
           productApplyPromotionMapper.deteleById(priceApplyPromotionReqVo.getId());
       }


        return true;
    }

    @Override
    public PriceApplyPromotionRespVo load(Long id) {
        PriceApplyPromotionRespVo priceApplyPromotionRespVo=productApplyPromotionMapper.loadById(id);
        List<PricePromotionDetailRespVo> pricePromotionDetailRespVoList=pricePromotionDetailMapper.loadByPromotionId(id);
        for (PricePromotionDetailRespVo pricePromotionDetailRespVo:
        pricePromotionDetailRespVoList) {
            List<PricePromotionProductRespVo> pricePromotionProductRespVoList=pricePromotionProductMapper.loadByBusinessId(pricePromotionDetailRespVo.getId());
            pricePromotionDetailRespVo.setPricePromotionProductRespVoList(pricePromotionProductRespVoList);
        }
        priceApplyPromotionRespVo.setPricePromotionDetailRespVoList(pricePromotionDetailRespVoList);
        return priceApplyPromotionRespVo;
    }

    private void addDetailAndproduct(PriceApplyPromotionReqVo priceApplyPromotionReqVo){
        //进行下属规则的添加，主键关联
        List<PricePromotionDetailReqVo> pricePromotionDetailReqVoList= priceApplyPromotionReqVo.getPricePromotionDetailReqVoList();
        pricePromotionDetailReqVoList.stream().forEach(x->x.setPromotionId(priceApplyPromotionReqVo.getId()));
        pricePromotionDetailReqVoList.stream().forEach(x->x.setId(IdSequenceUtils.getInstance().nextId()));
        pricePromotionDetailMapper.insertSelective(pricePromotionDetailReqVoList);
        //进行下属的产品的属性书写
        for (PricePromotionDetailReqVo pricePromotionDetailReqVo:
                pricePromotionDetailReqVoList) {
            List<PricePromotionProductReqVo> pricePromotionProductReqVoList=pricePromotionDetailReqVo.getPricePromotionProductReqVoList();
            pricePromotionProductReqVoList.stream().forEach(x->x.setBusinessId(pricePromotionDetailReqVo.getId()));
            pricePromotionProductReqVoList.stream().forEach(x->x.setId(IdSequenceUtils.getInstance().nextId()));
            pricePromotionProductMapper.insertSelective(pricePromotionProductReqVoList);
        }
    }
}
