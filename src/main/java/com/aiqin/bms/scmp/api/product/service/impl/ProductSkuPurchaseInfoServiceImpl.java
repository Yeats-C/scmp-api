package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.product.dao.ProductSkuPurchaseInfoDao;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuPurchaseInfoDraftMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuPurchaseInfoMapper;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPurchaseInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPurchaseInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPurchaseInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;
import com.aiqin.bms.scmp.api.product.service.ProductSkuPurchaseInfoService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 14:47
 */
@Service
public class ProductSkuPurchaseInfoServiceImpl implements ProductSkuPurchaseInfoService {
    @Autowired
    ProductSkuPurchaseInfoDraftMapper productSkuPurchaseInfoDraftMapper;
    @Autowired
    ProductSkuPurchaseInfoDao productSkuPurchaseInfoDao;
    @Autowired
    ProductSkuPurchaseInfoMapper productSkuPurchaseInfoMapper;
    @Override
    @Transactional(rollbackFor = BizException.class)
    @Save
    public int insertDraft(ProductSkuPurchaseInfoDraft productSkuPurchaseInfoDraft) {

        return productSkuPurchaseInfoDraftMapper.insert(productSkuPurchaseInfoDraft);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveApplyList(List<ApplyProductSku> applyProductSkus) {
        try {
            List<ApplyProductSkuPurchaseInfo> applyProductSkuPurchaseInfos = new ArrayList<>();
            List<ProductSkuPurchaseInfoDraft> productSkuPurchaseInfoDrafts = productSkuPurchaseInfoDao.getDrafts(applyProductSkus);
            if (productSkuPurchaseInfoDrafts !=null && productSkuPurchaseInfoDrafts.size() > 0){
                for (int i = 0;i<productSkuPurchaseInfoDrafts.size();i++){
                    ApplyProductSkuPurchaseInfo applyProductSkuPurchaseInfo = new ApplyProductSkuPurchaseInfo();
                    BeanCopyUtils.copy(productSkuPurchaseInfoDrafts.get(i),applyProductSkuPurchaseInfo);
                    applyProductSkuPurchaseInfo.setApplyCode(applyProductSkus.get(0).getApplyCode());
                    applyProductSkuPurchaseInfos.add(applyProductSkuPurchaseInfo);
                }
                //批量新增申请
                ((ProductSkuPurchaseInfoService) AopContext.currentProxy()).insertApplyList(applyProductSkuPurchaseInfos);
                //批量删除草稿
                productSkuPurchaseInfoDao.deleteDrafts(applyProductSkus);
            }
            return 1;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    @SaveList
    public int insertApplyList(List<ApplyProductSkuPurchaseInfo> applyProductSkuPurchaseInfos) {
        int num = productSkuPurchaseInfoDao.insertApplyList(applyProductSkuPurchaseInfos);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveList(String skuCode,String applyCode) {
        ApplyProductSkuPurchaseInfo applyProductSkuPurchaseInfo = productSkuPurchaseInfoDao.getApply(skuCode,applyCode);
        if (null != applyProductSkuPurchaseInfo){
            ProductSkuPurchaseInfo oldInfo = productSkuPurchaseInfoDao.getInfo(skuCode);
            ProductSkuPurchaseInfo productSkuPurchaseInfo = new ProductSkuPurchaseInfo();
            BeanCopyUtils.copy(applyProductSkuPurchaseInfo,productSkuPurchaseInfo);
            if (null != oldInfo){
                productSkuPurchaseInfo.setId(oldInfo.getId());
                return ((ProductSkuPurchaseInfoService)AopContext.currentProxy()).update(productSkuPurchaseInfo);
            } else {
                productSkuPurchaseInfo.setId(null);
                return ((ProductSkuPurchaseInfoService)AopContext.currentProxy()).insert(productSkuPurchaseInfo);
            }
        } else {
            return 0;
        }
    }

    @Override
    @SaveList
    @Transactional(rollbackFor = BizException.class)
    public int insertList(List<ProductSkuPurchaseInfo> productSkuPurchaseInfos) {
        int num = productSkuPurchaseInfoDao.insertList(productSkuPurchaseInfos);
        return num;
    }

    @Override
    @Update
    @Transactional(rollbackFor = BizException.class)
    public int update(ProductSkuPurchaseInfo productSkuPurchaseInfo) {
        int num = productSkuPurchaseInfoMapper.updateByPrimaryKeySelective(productSkuPurchaseInfo);
        return num;
    }

    @Override
    @Save
    @Transactional(rollbackFor = BizException.class)
    public int insert(ProductSkuPurchaseInfo productSkuPurchaseInfo) {
        int num = productSkuPurchaseInfoMapper.insert(productSkuPurchaseInfo);
        return num;
    }

    /**
     * 获取SKU采购配置信息-临时表
     *
     * @param skuCode
     * @return
     */
    @Override
    public List<PurchaseSaleStockRespVo> getDraftList(String skuCode) {
        return productSkuPurchaseInfoDraftMapper.getList(skuCode);
    }

    /**
     * 删除临时表数据
     *
     * @param skuCodes
     * @return
     */
    @Override
    public Integer deleteDrafts(List<String> skuCodes) {
        return productSkuPurchaseInfoDraftMapper.delete(skuCodes);
    }
}
