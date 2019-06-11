package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.product.dao.ProductSkuPriceDao;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPrice;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPrice;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceDraft;
import com.aiqin.bms.scmp.api.product.service.ProductSkuPriceService;
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
 * @date: 2019/1/29 0029 15:13
 */
@Service
public class ProductSkuPriceServiceImpl implements ProductSkuPriceService {
    @Autowired
    ProductSkuPriceDao productSkuPriceDao;

    @Override
    @Transactional(rollbackFor = BizException.class)
    @SaveList
    public int insertDraftList(List<ProductSkuPriceDraft> productSkuPriceDrafts) {
        int num = productSkuPriceDao.insertDraftList(productSkuPriceDrafts);
        return num;
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    @SaveList
    public int insertList(List<ProductSkuPrice> productSkuPrices) {
        int num = productSkuPriceDao.insertList(productSkuPrices);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveList(String skuCode,String applyCode) {
        List<ApplyProductSkuPrice> applyProductSkuPrices = productSkuPriceDao.getApply(skuCode,applyCode);
        if (null != applyProductSkuPrices && applyProductSkuPrices.size() > 0){
            List<ProductSkuPrice> productSkuPrices = new ArrayList<>();
            List<ProductSkuPrice> oldInfo = productSkuPriceDao.getInfo(skuCode);
            applyProductSkuPrices.forEach(item->{
                ProductSkuPrice productSkuPrice = new ProductSkuPrice();
                BeanCopyUtils.copy(item,productSkuPrice);
                productSkuPrices.add(productSkuPrice);
            });
            if (null != oldInfo && oldInfo.size() > 0){
                productSkuPriceDao.deleteList(skuCode);
            }
            return ((ProductSkuPriceService) AopContext.currentProxy()).insertList(productSkuPrices);
        } else {
            return 0;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveApplyList(List<ApplyProductSku> applyProductSkus) {
        try {
            List<ProductSkuPriceDraft> productSkuPriceDrafts = productSkuPriceDao.getDrafts(applyProductSkus);
            List<ApplyProductSkuPrice> applyProductSkuPrices = new ArrayList<>();
            if (productSkuPriceDrafts !=null && productSkuPriceDrafts.size() > 0){
                for (int i = 0;i<productSkuPriceDrafts.size();i++){
                    ApplyProductSkuPrice applyProductSkuPrice = new ApplyProductSkuPrice();
                    BeanCopyUtils.copy(productSkuPriceDrafts.get(i),applyProductSkuPrice);
                    applyProductSkuPrice.setApplyCode(applyProductSkus.get(0).getApplyCode());
                    applyProductSkuPrices.add(applyProductSkuPrice);
                }
                //批量新增申请
                ((ProductSkuPriceService) AopContext.currentProxy()).insertApplyList(applyProductSkuPrices);
                //批量删除草稿
                productSkuPriceDao.deleteDrafts(applyProductSkus);
            }
            return 1;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    @SaveList
    public int insertApplyList(List<ApplyProductSkuPrice> applyProductSkuPrices) {
        int num = productSkuPriceDao.insertApplyList(applyProductSkuPrices);
        return num;
    }
}
