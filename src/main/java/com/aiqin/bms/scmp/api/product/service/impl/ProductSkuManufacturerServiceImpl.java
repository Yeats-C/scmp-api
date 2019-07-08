package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuManufacturerDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuManufacturer;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuManufacturer;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuManufacturerDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuManufacturerRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuManufacturerDraftMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuManufacturerService;
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
 * @date: 2019/1/29 0029 15:36
 */
@Service
public class ProductSkuManufacturerServiceImpl implements ProductSkuManufacturerService {
    @Autowired
    ProductSkuManufacturerDao productSkuManufacturerDao;

    @Autowired
    private ProductSkuManufacturerDraftMapper draftMapper;
    @Override
    @Transactional(rollbackFor = BizException.class)
    @SaveList
    public int insertDraftList(List<ProductSkuManufacturerDraft> productSkuManufacturerDrafts) {
        int num = productSkuManufacturerDao.insertDraftList(productSkuManufacturerDrafts);
        return num;
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    @SaveList
    public int insertList(List<ProductSkuManufacturer> productSkuManufacturers) {
        int num = productSkuManufacturerDao.insertList(productSkuManufacturers);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveList(String skuCode,String applyCode) {
        List<ApplyProductSkuManufacturer> applyProductSkuManufacturers = productSkuManufacturerDao.getApply(skuCode,applyCode);
        if (null != applyProductSkuManufacturers && applyProductSkuManufacturers.size() > 0){
            List<ProductSkuManufacturer> productSkuManufacturers = new ArrayList<>();
            List<ProductSkuManufacturer> oldInfo = productSkuManufacturerDao.getInfo(skuCode);
            applyProductSkuManufacturers.forEach(item->{
                ProductSkuManufacturer productSkuManufacturer = new ProductSkuManufacturer();
                BeanCopyUtils.copy(item,productSkuManufacturer);
                productSkuManufacturers.add(productSkuManufacturer);
            });
            if (null != oldInfo && oldInfo.size() > 0){
                productSkuManufacturerDao.deleteList(skuCode);
            }
            return ((ProductSkuManufacturerService)AopContext.currentProxy()).insertList(productSkuManufacturers);
        } else {
            return 0;
        }
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    @SaveList
    public int insertApplyList(List<ApplyProductSkuManufacturer> applyProductSkuManufacturers) {
        int num = productSkuManufacturerDao.insertApplyList(applyProductSkuManufacturers);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveApplyList(List<ApplyProductSku> applyProductSkus) {
        try {
            List<ApplyProductSkuManufacturer> applyProductSkuManufacturers = new ArrayList<>();
            List<ProductSkuManufacturerDraft> productSkuManufacturerDrafts = productSkuManufacturerDao.getDrafts(applyProductSkus);
            if (productSkuManufacturerDrafts != null && productSkuManufacturerDrafts.size() > 0){
                for (int i=0;i<productSkuManufacturerDrafts.size();i++){
                    ApplyProductSkuManufacturer applyProductSkuManufacturer = new ApplyProductSkuManufacturer();
                    BeanCopyUtils.copy(productSkuManufacturerDrafts.get(i),applyProductSkuManufacturer);
                    applyProductSkuManufacturer.setApplyCode(applyProductSkus.get(0).getApplyCode());
                    applyProductSkuManufacturers.add(applyProductSkuManufacturer);
                }
                //批量新增申请
                ((ProductSkuManufacturerService) AopContext.currentProxy()).insertApplyList(applyProductSkuManufacturers);
                //批量删除草稿
                productSkuManufacturerDao.deleteDrafts(applyProductSkus);
            }
            return 1;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public List<ProductSkuManufacturerRespVo> getDraftList(String skuCode) {
        return productSkuManufacturerDao.getDraft(skuCode);
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
     * 功能描述: 申请数据
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 23:11
     */
    @Override
    public List<ProductSkuManufacturerRespVo> getApply(String skuCode, String applyCode) {
        return productSkuManufacturerDao.getApplys(skuCode,applyCode);
    }
}
