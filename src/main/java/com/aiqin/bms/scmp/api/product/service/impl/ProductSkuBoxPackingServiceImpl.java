package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.Save;
import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuBoxPackingDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuBoxPacking;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuBoxPacking;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuBoxPackingDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuBoxPackingRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuBoxPackingDraftMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuBoxPackingMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuBoxPackingService;
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
 * @date: 2019/1/29 0029 14:56
 */
@Service
public class ProductSkuBoxPackingServiceImpl implements ProductSkuBoxPackingService {
    @Autowired
    ProductSkuBoxPackingDraftMapper productSkuBoxPackingDraftMapper;
    @Autowired
    ProductSkuBoxPackingDao productSkuBoxPackingDao;
    @Autowired
    ProductSkuBoxPackingMapper productSkuBoxPackingMapper;
    @Override
    @Transactional(rollbackFor = BizException.class)
    @Save
    public int insertDraft(ProductSkuBoxPackingDraft productSkuBoxPackingDraft) {
        int num = productSkuBoxPackingDraftMapper.insert(productSkuBoxPackingDraft);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveApplyList(List<ApplyProductSku> applyProductSkus) {
        try {
            List<ApplyProductSkuBoxPacking> applyProductSkuBoxPackings = new ArrayList<>();
            List<ProductSkuBoxPackingDraft> productSkuBoxPackingDrafts = productSkuBoxPackingDao.getDrafts(applyProductSkus);
            if (productSkuBoxPackingDrafts != null && productSkuBoxPackingDrafts.size() > 0){
                for (int i = 0;i<productSkuBoxPackingDrafts.size();i++){
                    ApplyProductSkuBoxPacking applyProductSkuBoxPacking = new ApplyProductSkuBoxPacking();
                    BeanCopyUtils.copy(productSkuBoxPackingDrafts.get(i),applyProductSkuBoxPacking);
                    applyProductSkuBoxPacking.setApplyCode(applyProductSkus.get(0).getApplyCode());
                    applyProductSkuBoxPackings.add(applyProductSkuBoxPacking);
                }
                //批量新增申请
                ((ProductSkuBoxPackingService) AopContext.currentProxy()).insertApplyList(applyProductSkuBoxPackings);
                //批量删除草稿
                productSkuBoxPackingDao.deleteDrafts(applyProductSkus);
            }
            return 1;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public int insertApplyList(List<ApplyProductSkuBoxPacking> applyProductSkuBoxPackings) {
        int num = productSkuBoxPackingDao.insertApplyList(applyProductSkuBoxPackings);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveInfo(String skuCode,String applyCode) {
        List<ApplyProductSkuBoxPacking> applyProductSkuBoxPackings = productSkuBoxPackingDao.getApply(skuCode,applyCode);
        if(CollectionUtils.isNotEmptyCollection(applyProductSkuBoxPackings)){
            List<ProductSkuBoxPacking> productSkuBoxPackings = BeanCopyUtils.copyList(applyProductSkuBoxPackings,ProductSkuBoxPacking.class);
            productSkuBoxPackingMapper.deleteBySkuCode(skuCode);
            return ((ProductSkuBoxPackingService)AopContext.currentProxy()).insertList(productSkuBoxPackings);
        }
       return 0;
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    public int insertList(List<ProductSkuBoxPacking> productSkuBoxPackings) {
        int num = productSkuBoxPackingDao.insertBoxList(productSkuBoxPackings);
        return num;
    }

    @Override
    public int insert(ProductSkuBoxPacking productSkuBoxPacking) {
        int num = productSkuBoxPackingMapper.insert(productSkuBoxPacking);
        return num;
    }

    @Override
    public int update(ProductSkuBoxPacking productSkuBoxPacking) {
        int num = productSkuBoxPackingMapper.updateByPrimaryKeySelective(productSkuBoxPacking);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int insertDraftList(List<ProductSkuBoxPackingDraft> productSkuBoxPackingDrafts) {
        return productSkuBoxPackingDraftMapper.saveBatch(productSkuBoxPackingDrafts);
    }

    /**
     * 获取整包装信息-临时表
     *
     * @param skuCode
     * @return
     */
    @Override
    public List<ProductSkuBoxPackingRespVo> getDraftList(String skuCode) {
        return productSkuBoxPackingDao.getDraft(skuCode);
    }

    /**
     * 删除临时表数据
     *
     * @param skuCodes
     * @return
     */
    @Override
    public Integer deleteDrafts(List<String> skuCodes) {
        return productSkuBoxPackingDraftMapper.delete(skuCodes);
    }

    /**
     * 功能描述: 获取申请信息
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 22:44
     */
    @Override
    public List<ProductSkuBoxPackingRespVo> getApply(String skuCode, String applyCode) {
        return productSkuBoxPackingDao.getApplys(skuCode,applyCode);
    }

    /**
     * 功能描述: 获取正式表数据
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 17:03
     */
    @Override
    public List<ProductSkuBoxPackingRespVo> getList(String skuCode) {
        return productSkuBoxPackingDao.getList(skuCode);
    }
}
