package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuAssociatedGoods;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuAssociatedGoods;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuAssociatedGoodsDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuAssociatedGoodsRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ApplyProductSkuAssociatedGoodsMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuAssociatedGoodsDraftMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuAssociatedGoodsMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuAssociatedGoodsService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuAssociatedGoodsServiceImpl
 * @date 2019/5/8 16:05
 * @description TODO
 */
@Service
public class ProductSkuAssociatedGoodsServiceImpl implements ProductSkuAssociatedGoodsService {

    @Autowired
    private ProductSkuAssociatedGoodsDraftMapper draftMapper;

    @Autowired
    private ApplyProductSkuAssociatedGoodsMapper applyMapper;

    @Autowired
    private ProductSkuAssociatedGoodsMapper mapper;

    /**
     * 保存临时表数据
     *
     * @param draftList
     * @return
     */
    @Override
    @SaveList
    @Transactional(rollbackFor = Exception.class)
    public int insertDraftList(List<ProductSkuAssociatedGoodsDraft> draftList) {
        return draftMapper.saveBatch(draftList);
    }

    /**
     * 获取临时表数据
     *
     * @param skuCode
     * @return
     */
    @Override
    public List<ProductSkuAssociatedGoodsRespVo> getDraftList(String skuCode) {
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
     * 保存申请
     *
     * @param applyProductSkus
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveApplyList(List<ApplyProductSku> applyProductSkus) {
        try {
            List<ApplyProductSkuAssociatedGoods> applyProductSkuAssociatedGoodList = new ArrayList<>();
            List<ProductSkuAssociatedGoodsDraft> productSkuAssociatedGoodsDrafts = draftMapper.getDrafts(applyProductSkus);
            if (productSkuAssociatedGoodsDrafts != null && productSkuAssociatedGoodsDrafts.size() > 0){
                for (int i=0;i<productSkuAssociatedGoodsDrafts.size();i++){
                    ApplyProductSkuAssociatedGoods applyProductSkuAssociatedGoods = new ApplyProductSkuAssociatedGoods();
                    BeanCopyUtils.copy(productSkuAssociatedGoodsDrafts.get(i),applyProductSkuAssociatedGoods);
                    applyProductSkuAssociatedGoods.setApplyCode(applyProductSkus.get(0).getApplyCode());
                    applyProductSkuAssociatedGoodList.add(applyProductSkuAssociatedGoods);
                }
                //批量新增申请
                ((ProductSkuAssociatedGoodsService) AopContext.currentProxy()).insertApplyList(applyProductSkuAssociatedGoodList);
                //批量删除草稿
                draftMapper.deleteDrafts(applyProductSkus);
            }
            return 1;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 批量插入申请表
     *
     * @param applyProductSkuAssociatedGoods
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int insertApplyList(List<ApplyProductSkuAssociatedGoods> applyProductSkuAssociatedGoods) {
        return applyMapper.insertBatch(applyProductSkuAssociatedGoods);
    }

    /**
     * 功能描述: 申请详情
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 23:06
     */
    @Override
    public List<ProductSkuAssociatedGoodsRespVo> getApply(String skuCode, String applyCode) {
        return applyMapper.getApplys(skuCode,applyCode);
    }

    /**
     * 功能描述: 正式详情
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 17:10
     */
    @Override
    public List<ProductSkuAssociatedGoodsRespVo> getList(String skuCode) {
        return mapper.getList(skuCode);
    }

    /**
     * 功能描述: 正式保存
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 22:13
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveList(String skuCode, String applyCode) {
        List<ApplyProductSkuAssociatedGoods> applys = applyMapper.getApply(skuCode, applyCode);
        if(CollectionUtils.isNotEmptyCollection(applys)){
            List<ProductSkuAssociatedGoods> productSkuAssociatedGoods = BeanCopyUtils.copyList(applys,ProductSkuAssociatedGoods.class);
            mapper.deleteBySkuCode(skuCode);
            return ((ProductSkuAssociatedGoodsService)AopContext.currentProxy()).insertBatch(productSkuAssociatedGoods);
        }

        return 0;
    }

    /**
     * 功能描述: 批量保存到数据库
     *
     * @param list
     * @return
     * @auther knight.xie
     * @date 2019/7/8 22:17
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBatch(List<ProductSkuAssociatedGoods> list) {
        return mapper.insertBatch(list);
    }
}

