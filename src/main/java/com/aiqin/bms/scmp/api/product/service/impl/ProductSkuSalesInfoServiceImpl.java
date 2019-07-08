package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuSalesInfoDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSalesInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSalesInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSalesInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuSalesInfoDraftMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuSalesInfoService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 15:27
 */
@Service
public class ProductSkuSalesInfoServiceImpl implements ProductSkuSalesInfoService {
    @Autowired
    ProductSkuSalesInfoDao productSkuSalesInfoDao;
    @Autowired
    private ProductSkuSalesInfoDraftMapper draftMapper;
    @Override
    @Transactional(rollbackFor = BizException.class)
    @SaveList
    public int insertDraftList(List<ProductSkuSalesInfoDraft> productSkuSalesInfoDrafts) {
        int num = productSkuSalesInfoDao.insertDraftList(productSkuSalesInfoDrafts);
        return num;
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    @SaveList
    public int insertList(List<ProductSkuSalesInfo> productSkuSalesInfos) {
        int num = productSkuSalesInfoDao.insertList(productSkuSalesInfos);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveList(String skuCode,String applyCode) {
        List<ApplyProductSkuSalesInfo> applyProductSkuSalesInfos = productSkuSalesInfoDao.getApply(skuCode,applyCode);
        if (CollectionUtils.isNotEmptyCollection(applyProductSkuSalesInfos)){
            List<ProductSkuSalesInfo> productSkuSalesInfos = BeanCopyUtils.copyList(applyProductSkuSalesInfos,ProductSkuSalesInfo.class);
            productSkuSalesInfoDao.deleteList(skuCode);
            return ((ProductSkuSalesInfoService)AopContext.currentProxy()).insertList(productSkuSalesInfos);
        } else {
            return 0;
        }
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    @SaveList
    public int insertApplyList(List<ApplyProductSkuSalesInfo> applyProductSkuSalesInfos) {
        int num = productSkuSalesInfoDao.insertApplyList(applyProductSkuSalesInfos);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveApplyList(List<ApplyProductSku> applyProductSkus) {
        try {
            List<ApplyProductSkuSalesInfo> applyProductSkuSalesInfos = new ArrayList<>();
            List<ProductSkuSalesInfoDraft> productSkuSalesInfoDrafts = productSkuSalesInfoDao.getDrafts(applyProductSkus);
            if (productSkuSalesInfoDrafts != null && productSkuSalesInfoDrafts.size() > 0){
                for (int i =0;i<productSkuSalesInfoDrafts.size();i++){
                    ApplyProductSkuSalesInfo applyProductSkuSalesInfo = new ApplyProductSkuSalesInfo();
                    BeanCopyUtils.copy(productSkuSalesInfoDrafts.get(i),applyProductSkuSalesInfo);
                    applyProductSkuSalesInfo.setApplyCode(applyProductSkus.get(0).getApplyCode());
                    applyProductSkuSalesInfos.add(applyProductSkuSalesInfo);
                }
                //批量新增申请
                ((ProductSkuSalesInfoService) AopContext.currentProxy()).insertApplyList(applyProductSkuSalesInfos);
                //批量删除草稿
                 productSkuSalesInfoDao.deleteDrafts(applyProductSkus);
            }
            return 1;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 获取SKU销售配置信息-临时表
     *
     * @param skuCode
     * @return
     */
    @Override
    public List<PurchaseSaleStockRespVo> getDraftList(String skuCode) {
        return draftMapper.getList(skuCode);
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

    /**
     * 功能描述: 获取申请表数据
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 22:37
     */
    @Override
    public List<PurchaseSaleStockRespVo> getApplyList(String skuCode, String applyCode) {
        return productSkuSalesInfoDao.getApplys(skuCode,applyCode);
    }

    /**
     * 功能描述: 获取正式数据
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 17:26
     */
    @Override
    public List<PurchaseSaleStockRespVo> getList(String skuCode) {
        return productSkuSalesInfoDao.getRespVoBySkuCode(skuCode);
    }
}
