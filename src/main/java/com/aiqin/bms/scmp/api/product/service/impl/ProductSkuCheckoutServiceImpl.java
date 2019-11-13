package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.Save;
import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuCheckoutDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuCheckoutRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuCheckoutDraftMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuCheckoutMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuCheckoutService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 14:43
 */
@Service
public class ProductSkuCheckoutServiceImpl implements ProductSkuCheckoutService {
    @Autowired
    ProductSkuCheckoutDraftMapper productSkuCheckoutDraftMapper;
    @Autowired
    ProductSkuCheckoutDao productSkuCheckoutDao;
    @Autowired
    ProductSkuCheckoutMapper productSkuCheckoutMapper;
    @Override
    @Save
    @Transactional(rollbackFor = BizException.class)
    public int insertDraft(ProductSkuCheckoutDraft productSkuCheckoutDraft) {
        int num = productSkuCheckoutDraftMapper.insert(productSkuCheckoutDraft);
        return num;
    }

    @Override
    @SaveList
    @Transactional(rollbackFor = BizException.class)
    public int insertDraftList(String applyCode) {
        List<ApplyProductSkuCheckout> applyProductSkuCheckouts = productSkuCheckoutDao.getApplys(applyCode);
        if(CollectionUtils.isNotEmptyCollection(applyProductSkuCheckouts)){
            List<ProductSkuCheckoutDraft> productSkuCheckoutDrafts = BeanCopyUtils.copyList(applyProductSkuCheckouts, ProductSkuCheckoutDraft.class);
            return productSkuCheckoutDraftMapper.insertBatch(productSkuCheckoutDrafts);
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveApply(List<ApplyProductSku> productSkus) {
        try {
            List<ProductSkuCheckoutDraft> productSkuCheckoutDrafts = productSkuCheckoutDao.getDrafts(productSkus);
            List<ApplyProductSkuCheckout> applyProductSkuCheckouts = new ArrayList<>();
            if (null != productSkuCheckoutDrafts && productSkuCheckoutDrafts.size() > 0){
                for (int i = 0;i<productSkuCheckoutDrafts.size();i++){
                    ApplyProductSkuCheckout applyProductSkuCheckout = new ApplyProductSkuCheckout();
                    BeanCopyUtils.copy(productSkuCheckoutDrafts.get(i),applyProductSkuCheckout);
                    applyProductSkuCheckout.setApplyCode(productSkus.get(0).getApplyCode());
                    applyProductSkuCheckouts.add(applyProductSkuCheckout);
                }
                //批量新增申请
                ((ProductSkuCheckoutService)AopContext.currentProxy()).insertApply(applyProductSkuCheckouts);
                //批量删除草稿
                productSkuCheckoutDao.deleteDrafts(productSkus);
            }
            return 1;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    @SaveList
    @Transactional(rollbackFor = BizException.class)
    public int insertApply(List<ApplyProductSkuCheckout> applyProductSkuCheckouts) {
        int num = productSkuCheckoutDao.insertApply(applyProductSkuCheckouts);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveInfo(String skuCode,String applyCode) {
        ApplyProductSkuCheckout applyProductSkuCheckout = productSkuCheckoutDao.getApply(skuCode,applyCode);
        if (null != applyProductSkuCheckout){
            ProductSkuCheckout productSkuCheckout = new ProductSkuCheckout();
            BeanCopyUtils.copy(applyProductSkuCheckout,productSkuCheckout);
            ProductSkuCheckout oldSkuCheckOut = productSkuCheckoutDao.getInfo(skuCode);
            if (null != oldSkuCheckOut){
                productSkuCheckout.setId(oldSkuCheckOut.getId());
                return ((ProductSkuCheckoutService)AopContext.currentProxy()).update(productSkuCheckout);
            } else {
                productSkuCheckout.setId(null);
                return ((ProductSkuCheckoutService)AopContext.currentProxy()).insert(productSkuCheckout);
            }
        } else {
            return 0;
        }
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    @SaveList
    public int insertList(List<ProductSkuCheckout> productSkuCheckouts) {
        int num = productSkuCheckoutDao.insertCheckOuts(productSkuCheckouts);
        return num;
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    public int insert(ProductSkuCheckout productSkuCheckout) {
        int num = productSkuCheckoutMapper.insertSelective(productSkuCheckout);
        return num;
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    public int update(ProductSkuCheckout productSkuCheckout) {
        int num = productSkuCheckoutMapper.updateByPrimaryKeySelective(productSkuCheckout);
        return num;
    }

    /**
     * 获取结算信息
     *
     * @param skuCode
     * @return
     */
    @Override
    public ProductSkuCheckoutRespVo getDraft(String skuCode) {
        return productSkuCheckoutDao.getDraftInfo(skuCode);
    }

    /**
     * 删除临时表信息
     *
     * @param skuCodes
     * @return
     */
    @Override
    public Integer deleteDrafts(List<String> skuCodes) {
        return productSkuCheckoutDraftMapper.delete(skuCodes);
    }

    /**
     * 功能描述: 根据skuCode编码查询正式结算信息
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 17:00
     */
    @Override
    public ProductSkuCheckoutRespVo getBySkuCode(String skuCode) {
        return productSkuCheckoutDao.getBySkuCode(skuCode);
    }

    /**
     * 功能描述: 获取申请结算信息
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 22:52
     */
    @Override
    public ProductSkuCheckoutRespVo getApply(String skuCode, String applyCode) {
        return productSkuCheckoutDao.getApplyInfo(skuCode,applyCode);
    }

    @Override
    public Map<String, ProductSkuCheckoutRespVo> selectBySkuCodes(Set<String> skuList) {
        return productSkuCheckoutDao.selectBySkuCodes(skuList);
    }
}
