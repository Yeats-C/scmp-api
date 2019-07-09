package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuChannel;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuChannel;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuChannelDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuChannelRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ApplyProductSkuChannelMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuChannelDraftMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuChannelMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuChannelService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.google.common.collect.Lists;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuChannelServiceImpl
 * @date 2019/5/7 17:33
 * @description TODO
 */
@Service
public class ProductSkuChannelServiceImpl implements ProductSkuChannelService {

    @Autowired
    private ProductSkuChannelDraftMapper draftMapper;
    @Autowired
    private ApplyProductSkuChannelMapper applyMapper;

    @Autowired
    private ProductSkuChannelMapper mapper;
    /**
     * 保存信息到临时表
     *
     * @param productSkuChannelDrafts
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int insertDraftList(List<ProductSkuChannelDraft> productSkuChannelDrafts) {
        return draftMapper.saveBatch(productSkuChannelDrafts);
    }

    /**
     * 通过SKU获取临时表渠道信息
     *
     * @param skuCode
     * @return
     */
    @Override
    public List<ProductSkuChannelRespVo> getDraftList(String skuCode) {
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
     * 批量保存申请数据
     *
     * @param applyProductSkus
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveApplyList(List<ApplyProductSku> applyProductSkus) {

        List<ApplyProductSkuChannel> applyProductSkuChannels = Lists.newArrayList();
        List<String> skuList = applyProductSkus.stream().map(ApplyProductSku::getSkuCode).distinct().collect(Collectors.toList());
        List<ProductSkuChannelDraft> drafts = draftMapper.getDrafts(skuList);
        if (CollectionUtils.isNotEmptyCollection(drafts)) {
            for (int i = 0; i < drafts.size(); i++) {
                ApplyProductSkuChannel apply = new ApplyProductSkuChannel();
                BeanCopyUtils.copy(drafts.get(i), apply);
                apply.setApplyCode(applyProductSkus.get(0).getApplyCode());
                applyProductSkuChannels.add(apply);
            }
            //批量新增申请
            ((ProductSkuChannelService) AopContext.currentProxy()).insertApplyList(applyProductSkuChannels);
            //批量删除草稿
            draftMapper.delete(skuList);
        }
        return 1;
    }

    /**
     * 批量插入申请数据到数据库
     *
     * @param applyProductSkuChannels
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int insertApplyList(List<ApplyProductSkuChannel> applyProductSkuChannels) {
        return applyMapper.insertBartch(applyProductSkuChannels);
    }

    /**
     * 通过SKU获取申请表渠道信息
     *
     * @param skuCode
     * @param applyCode
     * @return
     */
    @Override
    public List<ProductSkuChannelRespVo> getApplyList(String skuCode, String applyCode) {
        return applyMapper.selectBySkuAndApplyCode(skuCode,applyCode);
    }

    /**
     * 功能描述: 获取sku渠道
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 16:50
     */
    @Override
    public List<ProductSkuChannelRespVo> getList(String skuCode) {
        return mapper.getList(skuCode);
    }

    /**
     * 功能描述: 保存正式数据
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 20:30
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(String skuCode, String applyCode) {
        List<ApplyProductSkuChannel> applyProductSkuChannels = applyMapper.selectApplyProductSkuChannelBySkuAndApplyCode(skuCode, applyCode);
        if(CollectionUtils.isNotEmptyCollection(applyProductSkuChannels)){
            List<ProductSkuChannel> productSkuChannels = BeanCopyUtils.copyList(applyProductSkuChannels,ProductSkuChannel.class);
            mapper.deleteBySkuCode(skuCode);
            return ((ProductSkuChannelService)AopContext.currentProxy()).insertBatch(productSkuChannels);
        }
        return 0;
    }

    /**
     * 功能描述: 批量插入数据库
     *
     * @param list
     * @return
     * @auther knight.xie
     * @date 2019/7/8 20:38
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int insertBatch(List<ProductSkuChannel> list) {
        return mapper.insertBatch(list);
    }
}
