package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuSupplyUnitDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSupplyUnit;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnit;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuSupplyUnitDraftMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuSupplyUnitService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.google.common.collect.Lists;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 15:32
 */
@Service
public class ProductSkuSupplyUnitServiceImpl implements ProductSkuSupplyUnitService {
    @Autowired
    ProductSkuSupplyUnitDao productSkuSupplyUnitDao;

    @Autowired
    private ProductSkuSupplyUnitDraftMapper draftMapper;
    @Override
    @SaveList
    @Transactional(rollbackFor = BizException.class)
    public int insertDraftList(List<ProductSkuSupplyUnitDraft> productSkuSupplyUnitDrafts) {
        int num = productSkuSupplyUnitDao.insertDraftList(productSkuSupplyUnitDrafts);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertList(List<ProductSkuSupplyUnit> productSkuSupplyUnits) {
        int num = productSkuSupplyUnitDao.insertList(productSkuSupplyUnits);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveList(String skuCode,String applyCode) {
        //通过申请编码查询供应商信息
        List<ApplyProductSkuSupplyUnit> applyProductSkuSupplyUnits = productSkuSupplyUnitDao.getApply(skuCode,applyCode);
        if (CollectionUtils.isNotEmptyCollection(applyProductSkuSupplyUnits)){
            List<ProductSkuSupplyUnit> productSkuSupplyUnits = BeanCopyUtils.copyList(applyProductSkuSupplyUnits,ProductSkuSupplyUnit.class);
            productSkuSupplyUnitDao.deleteList(skuCode);
            return ((ProductSkuSupplyUnitService) AopContext.currentProxy()).insertList(productSkuSupplyUnits);
        } else {
            return 0;
        }
    }

    @Override
    @SaveList
    @Transactional(rollbackFor = BizException.class)
    public int insertApplyList(List<ApplyProductSkuSupplyUnit> applyProductSkuSupplyUnits) {
        int num = productSkuSupplyUnitDao.insertApplyList(applyProductSkuSupplyUnits);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveApplyList(List<ApplyProductSku> applyProductSkus) {
        try {
            List<ApplyProductSkuSupplyUnit> applyProductSkuSupplyUnits = new ArrayList<>();
            List<ProductSkuSupplyUnitDraft> productSkuSupplyUnitDrafts = productSkuSupplyUnitDao.getDrafts(applyProductSkus);
            if (productSkuSupplyUnitDrafts != null && productSkuSupplyUnitDrafts.size() > 0){
                for (int i=0;i<productSkuSupplyUnitDrafts.size();i++){
                    ApplyProductSkuSupplyUnit applyProductSkuSupplyUnit = new ApplyProductSkuSupplyUnit();
                    BeanCopyUtils.copy(productSkuSupplyUnitDrafts.get(i),applyProductSkuSupplyUnit);
                    applyProductSkuSupplyUnit.setApplyCode(applyProductSkus.get(0).getApplyCode());
                    applyProductSkuSupplyUnits.add(applyProductSkuSupplyUnit);
                }
                //批量新增申请
                ((ProductSkuSupplyUnitService) AopContext.currentProxy()).insertApplyList(applyProductSkuSupplyUnits);
                //批量删除草稿
                productSkuSupplyUnitDao.deleteDrafts(applyProductSkus);
            }
            return 1;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 供应商信息-临时表
     *
     * @param skuCode
     * @return
     */
    @Override
    public List<ProductSkuSupplyUnitRespVo> getDraftList(String skuCode) {
        return productSkuSupplyUnitDao.getDraft(skuCode);
    }

    @Override
    public List<ProductSkuSupplyUnitRespVo> getDraftList(List<String> skuCodes) {
        return productSkuSupplyUnitDao.getDraftBySkuCodes(skuCodes);
    }

    /**
     * 删除临时表数据
     *
     * @param skuCodes
     * @return
     */
    @Override
    public Integer deleteDrafts(List<String> skuCodes) {
        return draftMapper.delete(skuCodes);
    }

    @Override
    public List<ProductSkuSupplyUnitRespVo> selectBySkuCode(String skuCode) {
        List<ProductSkuSupplyUnitRespVo> list = productSkuSupplyUnitDao.selectBySkuCode(skuCode);
        if(CollectionUtils.isEmptyCollection(list)){
            return Lists.newArrayList();
        }
        return list;
    }

    /**
     * 功能描述: 根据Id批量查询临时表细腻些
     *
     * @param ids
     * @return
     * @auther knight.xie
     * @date 2019/7/4 16:09
     */
    @Override
    public List<ProductSkuSupplyUnitDraft> getDraftByIds(List<Long> ids) {
        return draftMapper.selectByIds(ids);
    }

    /**
     * 功能描述: 根据Id批量删除临时表信息
     *
     * @param ids
     * @return
     * @auther knight.xie
     * @date 2019/7/4 16:19
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteDraftByIds(List<Long> ids) {
        return draftMapper.deleteDraftByIds(ids);
    }

    /**
     * 功能描述: 获取申请数据
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 22:59
     */
    @Override
    public List<ProductSkuSupplyUnitRespVo> getApply(String skuCode, String applyCode) {
        List<ProductSkuSupplyUnitRespVo> list = productSkuSupplyUnitDao.getApplys(skuCode,applyCode);
        if(CollectionUtils.isEmptyCollection(list)){
            return Lists.newArrayList();
        }
        return list;
    }
}
