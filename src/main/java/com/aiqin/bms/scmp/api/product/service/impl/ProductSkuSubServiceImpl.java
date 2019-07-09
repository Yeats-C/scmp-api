package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSub;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSub;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSubDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSubRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ApplyProductSkuSubMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuSubDraftMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuSubMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuSubService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuSubServiceImpl
 * @date 2019/7/6 16:07
 * @description TODO
 */
@Service
public class ProductSkuSubServiceImpl implements ProductSkuSubService {

    @Autowired
    private ProductSkuSubDraftMapper draftMapper;

    @Autowired
    private ApplyProductSkuSubMapper applyMapper;


    @Autowired
    private ProductSkuSubMapper mapper;

    /**
     * 功能描述: 批量插入临时表
     *
     * @param draftList
     * @return
     * @auther knight.xie
     * @date 2019/7/6 16:07
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int insertDraftList(List<ProductSkuSubDraft> draftList) {
        return draftMapper.insertBatch(draftList);
    }

    /**
     * 功能描述: 根据SkuCode查询临时表信息
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 18:02
     */
    @Override
    public List<ProductSkuSubRespVo> draftDetail(String skuCode) {
        return draftMapper.selectBySkuCode(skuCode);
    }

    /**
     * 功能描述: 申请批量保存
     *
     * @param applyProductSkus
     * @return
     * @auther knight.xie
     * @date 2019/7/6 18:42
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveApplyList(List<ApplyProductSku> applyProductSkus) {
        List<String> skuList = applyProductSkus.stream().map(ApplyProductSku::getSkuCode).distinct().collect(Collectors.toList());
        List<ProductSkuSubDraft> productSkuSubDrafts = draftMapper.selectBySkuCodes(skuList);
        if(CollectionUtils.isNotEmptyCollection(productSkuSubDrafts)) {
            List<ApplyProductSkuSub> applyList = BeanCopyUtils.copyList(productSkuSubDrafts,ApplyProductSkuSub.class);
            applyList.forEach(item->item.setApplyCode(applyProductSkus.get(0).getApplyCode()));
            return ((ProductSkuSubService)AopContext.currentProxy()).insertApplyList(applyList);
        }
        return 0;
    }

    /**
     * 功能描述: 申请数据批量保存到数据库
     *
     * @param applyList
     * @return
     * @auther knight.xie
     * @date 2019/7/6 18:43
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public Integer insertApplyList(List<ApplyProductSkuSub> applyList) {
        return applyMapper.insertBatch(applyList);
    }

    /**
     * 功能描述: 查询申请数据
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 23:40
     */
    @Override
    public List<ProductSkuSubRespVo> getApply(String skuCode, String applyCode) {
        return applyMapper.getApplys(skuCode,applyCode);
    }

    /**
     * 功能描述: 获取正式表数据
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 17:24
     */
    @Override
    public List<ProductSkuSubRespVo> getList(String skuCode) {
        return mapper.selectBySkuCode(skuCode);
    }

    /**
     * 功能描述: 保存到正式
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 22:28
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveList(String skuCode, String applyCode) {
        List<ApplyProductSkuSub> applyProductSkuSubList = applyMapper.getApply(skuCode, applyCode);
        if(CollectionUtils.isNotEmptyCollection(applyProductSkuSubList)){
            List<ProductSkuSub> list = BeanCopyUtils.copyList(applyProductSkuSubList,ProductSkuSub.class);
            mapper.selectBySkuCode(skuCode);
            return ((ProductSkuSubService)AopContext.currentProxy()).insertBatch(list);
        }
        return 0;
    }

    /**
     * 功能描述: 批量插入到数据库
     *
     * @param list
     * @return
     * @auther knight.xie
     * @date 2019/7/8 22:29
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int insertBatch(List<ProductSkuSub> list) {
        return mapper.insertBatch(list);
    }
}
