package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSupplyUnitCapacity;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuSupplyUnitCapacityDraftMapper;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitCapacityDraft;
import com.aiqin.bms.scmp.api.product.service.ProductSkuSupplyUnitCapacityService;
import com.google.common.collect.Lists;
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
    public int saveApplyList(List<ApplyProductSku> applyProductSkus) {
        List<ApplyProductSkuSupplyUnitCapacity> applyProductSkuSupplyUnitCapacities = Lists.newArrayList();
        List<String> skuList = applyProductSkus.stream().map(ApplyProductSku::getSkuCode).distinct().collect(Collectors.toList());

        //draftMapper.getDraftList()
        return 0;
    }

    /**
     * 批量插入申请数据到数据库
     *
     * @param applyProductSkuSupplyUnitCapacities
     * @return
     */
    @Override
    public int insertApplyList(List<ApplyProductSkuSupplyUnitCapacity> applyProductSkuSupplyUnitCapacities) {
        return 0;
    }
}
