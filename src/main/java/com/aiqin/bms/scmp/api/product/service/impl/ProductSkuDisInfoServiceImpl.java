package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.Save;
import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDisInfoDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuDisInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuDisInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuDistributionInfo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuDisInfoDraftMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuDistributionInfoMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuDisInfoService;
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
 * @date: 2019/1/29 0029 14:52
 */
@Service
public class ProductSkuDisInfoServiceImpl implements ProductSkuDisInfoService {
    @Autowired
    ProductSkuDisInfoDraftMapper productSkuDisInfoDraftMapper;
    @Autowired
    ProductSkuDisInfoDao productSkuDisInfoDao;
    @Autowired
    ProductSkuDistributionInfoMapper productSkuDistributionInfoMapper;
    @Override
    @Transactional(rollbackFor = BizException.class)
    @Save
    public int insertDraft(ProductSkuDisInfoDraft productSkuDisInfoDraft) {
      return productSkuDisInfoDraftMapper.insert(productSkuDisInfoDraft);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveApplyList(List<ApplyProductSku> applyProductSkus) {
        try {
            List<ApplyProductSkuDisInfo> applyProductSkuDisInfos = new ArrayList<>();
            List<ProductSkuDisInfoDraft> productSkuDisInfoDrafts = productSkuDisInfoDao.getDrafts(applyProductSkus);
            if (productSkuDisInfoDrafts != null && productSkuDisInfoDrafts.size() > 0){
                for (int i = 0;i<productSkuDisInfoDrafts.size();i++){
                    ApplyProductSkuDisInfo applyProductSkuDisInfo = new ApplyProductSkuDisInfo();
                    BeanCopyUtils.copy(productSkuDisInfoDrafts.get(i),applyProductSkuDisInfo);
                    applyProductSkuDisInfo.setApplyCode(applyProductSkus.get(0).getApplyCode());
                    applyProductSkuDisInfos.add(applyProductSkuDisInfo);
                }
                //批量新增申请
                ((ProductSkuDisInfoService) AopContext.currentProxy()).insertApplyList(applyProductSkuDisInfos);
                //批量删除草稿
                productSkuDisInfoDao.deleteDrafts(applyProductSkus);
            }
            return 1;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public int insertApplyList(List<ApplyProductSkuDisInfo> applyProductSkuDisInfos) {
        int num = productSkuDisInfoDao.insertApplyList(applyProductSkuDisInfos);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveInfo(String skuCode,String applyCode) {
        ApplyProductSkuDisInfo applyProductSkuDisInfo = productSkuDisInfoDao.getApply(skuCode,applyCode);
        if (null != applyProductSkuDisInfo) {
            ProductSkuDistributionInfo productSkuDistributionInfo = new ProductSkuDistributionInfo();
            BeanCopyUtils.copy(applyProductSkuDisInfo,productSkuDistributionInfo);
            ProductSkuDistributionInfo oldSkuDis = productSkuDisInfoDao.getInfo(skuCode);
            if (null != oldSkuDis){
                productSkuDistributionInfo.setId(oldSkuDis.getId());
                return ((ProductSkuDisInfoService)AopContext.currentProxy()).update(productSkuDistributionInfo);
            } else {
                productSkuDistributionInfo.setId(null);
                return ((ProductSkuDisInfoService)AopContext.currentProxy()).insert(productSkuDistributionInfo);
            }
        } else {
            return 0;
        }
    }

    @Override
    @SaveList
    @Transactional(rollbackFor = BizException.class)
    public int insertList(List<ProductSkuDistributionInfo> productSkuDistributionInfos) {
        int num = productSkuDisInfoDao.insertDisInfoList(productSkuDistributionInfos);
        return num;
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    public int insert(ProductSkuDistributionInfo productSkuDistributionInfo) {
        int num = productSkuDistributionInfoMapper.insertSelective(productSkuDistributionInfo);
        return  num;
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    public int update(ProductSkuDistributionInfo productSkuDistributionInfo) {
        int num = productSkuDistributionInfoMapper.updateByPrimaryKeySelective(productSkuDistributionInfo);
        return num;
    }

    /**
     * 获取SKU销售配置信息-临时表
     *
     * @param skuCode
     * @return
     */
    @Override
    public List<PurchaseSaleStockRespVo> getDraftList(String skuCode) {

        return productSkuDisInfoDraftMapper.getList(skuCode);
    }

    /**
     * 删除临时表数据
     *
     * @param skuCodes
     * @return
     */
    @Override
    public Integer deleteDrafts(List<String> skuCodes) {
        return productSkuDisInfoDraftMapper.delete(skuCodes);
    }

    /**
     * 功能描述: 获取申请信息
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 22:32
     */
    @Override
    public List<PurchaseSaleStockRespVo> getApplyList(String skuCode, String applyCode) {
        return productSkuDisInfoDao.getApplys(skuCode,applyCode);
    }

    /**
     * 功能描述:
     *
     * @param skuCode@return
     * @auther knight.xie
     * @date 2019/7/8 17:02
     */
    @Override
    public List<PurchaseSaleStockRespVo> getList(String skuCode) {
        return productSkuDistributionInfoMapper.getList(skuCode);
    }
}
