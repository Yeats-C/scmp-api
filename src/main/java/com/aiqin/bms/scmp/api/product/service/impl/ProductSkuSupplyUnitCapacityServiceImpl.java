package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.mapper.ApplyProductSkuSupplyUnitCapacityMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuSupplyUnitCapacityDraftMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuSupplyUnitCapacityMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuSupplyUnitCapacityService;
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
 * @date 2019/5/8 15:55
 * @description TODO
 */
@Service
public class ProductSkuSupplyUnitCapacityServiceImpl implements ProductSkuSupplyUnitCapacityService {


    @Autowired
    private ProductSkuSupplyUnitCapacityDraftMapper draftMapper;

    @Autowired
    private ApplyProductSkuSupplyUnitCapacityMapper applyMapper;

    @Autowired
    private ProductSkuSupplyUnitCapacityMapper mapper;

    /**
     * 批量添加临时表
     *
     * @param draftList
     * @return
     */
    @Override
    @SaveList
    @Transactional(rollbackFor = Exception.class)
    public int insertDraftList(List<ProductSkuSupplyUnitCapacityDraft> draftList) {
        return draftMapper.saveBatch(draftList);
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
        List<ApplyProductSkuSupplyUnitCapacity> applyProductSkuSupplyUnitCapacities = Lists.newArrayList();
        List<String> skuList = applyProductSkus.stream().map(ApplyProductSku::getSkuCode).distinct().collect(Collectors.toList());
        List<ProductSkuSupplyUnitCapacityDraft> drafts = draftMapper.getDrafts(skuList);
        if (CollectionUtils.isNotEmptyCollection(drafts)) {
            for (int i = 0; i < drafts.size(); i++) {
                ApplyProductSkuSupplyUnitCapacity apply = new ApplyProductSkuSupplyUnitCapacity();
                BeanCopyUtils.copy(drafts.get(i), apply);
                apply.setApplyCode(applyProductSkus.get(0).getApplyCode());
                applyProductSkuSupplyUnitCapacities.add(apply);
            }
            //批量新增申请
            ((ProductSkuSupplyUnitCapacityService) AopContext.currentProxy()).insertApplyList(applyProductSkuSupplyUnitCapacities);
            //批量删除草稿
            draftMapper.delete(skuList);
        }
        return 1;
    }

    /**
     * 批量插入申请数据到数据库
     *
     * @param applyProductSkuSupplyUnitCapacities
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int insertApplyList(List<ApplyProductSkuSupplyUnitCapacity> applyProductSkuSupplyUnitCapacities) {
        return applyMapper.insertBatch(applyProductSkuSupplyUnitCapacities);
    }

    /**
     * 功能描述: 根据供应商信息查询
     *
     * @param skuSupplyUnitDrafts
     * @return
     * @auther knight.xie
     * @date 2019/7/4 16:46
     */
    @Override
    public List<ProductSkuSupplyUnitCapacityDraft> getDraftsBySupplyUnitDrafts(List<ProductSkuSupplyUnitDraft> skuSupplyUnitDrafts) {
        return draftMapper.getDraftsBySupplyUnitDrafts(skuSupplyUnitDrafts);
    }

    /**
     * 功能描述: 根据Ids批量删除
     *
     * @param ids
     * @return
     * @auther knight.xie
     * @date 2019/7/4 16:53
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteDraftByIds(List<Long> ids) {
        return draftMapper.deleteByIds(ids);
    }

    /**
     * 功能描述: 根据申请编码保存正式数据
     *
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/4 20:30
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveList(String skuCode, String applyCode) {
        //根据申请编码查询供应商产能
        List<ApplyProductSkuSupplyUnitCapacity> applyProductSkuSupplyUnitCapacities =
                applyMapper.selectByApplyCode(applyCode);
        if(CollectionUtils.isNotEmptyCollection(applyProductSkuSupplyUnitCapacities)){
            List<ProductSkuSupplyUnitCapacity> productSkuSupplyUnitCapacities = BeanCopyUtils.copyList(applyProductSkuSupplyUnitCapacities,ProductSkuSupplyUnitCapacity.class);
            mapper.deleteBySkuCode(skuCode);
            return ((ProductSkuSupplyUnitCapacityService)AopContext.currentProxy()).insertList(productSkuSupplyUnitCapacities);
        }
        return 0;
    }

    /**
     * 功能描述: 批量插入数据库
     *
     * @param capacities
     * @return
     * @auther knight.xie
     * @date 2019/7/4 20:52
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertList(List<ProductSkuSupplyUnitCapacity> capacities) {
        return mapper.insertBatch(capacities);
    }

    @Override
    public int deleteDraftsByVos(List<ProductSkuSupplyUnitCapacityDraft> capacityDrafts) {
        return mapper.deleteDraftsByVos(capacityDrafts);
    }
}
