package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.Save;
import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.common.Update;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuStockInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuStockInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuStockInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ApplyProductSkuStockInfoMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuStockInfoDraftMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuStockInfoMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuStockInfoService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.google.common.collect.Lists;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuStockInfoServiceImpl
 * @date 2019/5/9 15:12

 */
@Service
public class ProductSkuStockInfoServiceImpl implements ProductSkuStockInfoService {

    @Autowired
    private ProductSkuStockInfoDraftMapper draftMapper;
    @Autowired
    private ApplyProductSkuStockInfoMapper applyMapper;
    @Autowired
    private ProductSkuStockInfoMapper mapper;

    /**
     * 保存临时表数据
     *
     * @param draft
     * @return
     */
    @Override
    @Save
    @Transactional(rollbackFor = Exception.class)
    public int insertDraft(ProductSkuStockInfoDraft draft) {
        return draftMapper.insertSelective(draft);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int insertDraftList(String applyCode) {
        List<ApplyProductSkuStockInfo> applyProductSkuStockInfo = applyMapper.getApplyProductSkuStockInfo(null, applyCode);
        if(CollectionUtils.isNotEmptyCollection(applyProductSkuStockInfo)){
            List<ProductSkuStockInfoDraft> drafts = BeanCopyUtils.copyList(applyProductSkuStockInfo,ProductSkuStockInfoDraft.class);
            return draftMapper.insertBatch(drafts);
        }
        return 0;
    }

    /**
     * 获取SKU库存配置信息-临时表
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
     * 批量新增申请
     *
     * @param applyProductSkus
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveApplyList(List<ApplyProductSku> applyProductSkus) {
        try {
            List<ApplyProductSkuStockInfo> applyProductSkuPurchaseInfos = Lists.newArrayList();
            List<ProductSkuStockInfoDraft> productSkuStockInfoDrafts = draftMapper.getDrafts(applyProductSkus);
            if (CollectionUtils.isNotEmptyCollection(productSkuStockInfoDrafts)){
                for (int i = 0;i<productSkuStockInfoDrafts.size();i++){
                    ApplyProductSkuStockInfo applyProductSkuStockInfo = new ApplyProductSkuStockInfo();
                    BeanCopyUtils.copy(productSkuStockInfoDrafts.get(i),applyProductSkuStockInfo);
                    applyProductSkuStockInfo.setApplyCode(applyProductSkus.get(0).getApplyCode());
                    applyProductSkuPurchaseInfos.add(applyProductSkuStockInfo);
                }
                //批量新增申请
                ((ProductSkuStockInfoService) AopContext.currentProxy()).insertApplyList(applyProductSkuPurchaseInfos);
                //批量删除草稿
                draftMapper.deleteDrafts(applyProductSkus);
            }
            return 1;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 批量新增申请(保存到数据库)
     *
     * @param applyProductSkuPurchaseInfos
     * @return
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    @SaveList
    public int insertApplyList(List<ApplyProductSkuStockInfo> applyProductSkuPurchaseInfos) {
        int num = applyMapper.insertBatch(applyProductSkuPurchaseInfos);
        return num;
    }

    /**
     * 获取SKU库存配置信息-临时表
     *
     * @param skuCode
     * @param applyCode
     * @return
     */
    @Override
    public List<PurchaseSaleStockRespVo> getApplyList(String skuCode, String applyCode) {
        return applyMapper.getList(skuCode,applyCode);
    }

    /**
     * 功能描述: 获取SKU库存配置信息
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 16:53
     */
    @Override
    public List<PurchaseSaleStockRespVo> getList(String skuCode) {
        return mapper.getList(skuCode);
    }

    /**
     * 功能描述: 正式保存
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 21:04
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveInfo(String skuCode, String applyCode) {
        List<ApplyProductSkuStockInfo> applyProductSkuStockInfo = applyMapper.getApplyProductSkuStockInfo(skuCode, applyCode);
        if(CollectionUtils.isNotEmptyCollection(applyProductSkuStockInfo)){
            ProductSkuStockInfo productSkuStockInfo = BeanCopyUtils.copy(applyProductSkuStockInfo.get(0),ProductSkuStockInfo.class);
            //查询旧数据
            ProductSkuStockInfo oldData = mapper.getBySkuCode(skuCode);
            if(null != oldData){
                productSkuStockInfo.setId(oldData.getId());
                return ((ProductSkuStockInfoService)AopContext.currentProxy()).updateByPrimaryKeySelective(productSkuStockInfo);
            } else {
                return ((ProductSkuStockInfoService)AopContext.currentProxy()).insertSelective(productSkuStockInfo);
            }
        }
        return 0;
    }

    /**
     * 功能描述: 保存到数据库-正式表
     *
     * @param record
     * @return
     * @auther knight.xie
     * @date 2019/7/8 21:05
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSelective(ProductSkuStockInfo record) {
        return mapper.insertSelective(record);
    }

    /**
     * 功能描述: 更新到数据库-正式表
     *
     * @param record
     * @return
     * @auther knight.xie
     * @date 2019/7/8 21:05
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(ProductSkuStockInfo record) {
        return mapper.updateByPrimaryKeySelective(record);
    }
}
