package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuPicDescDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPicDesc;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPicDesc;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPicDescDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuPicDescRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuPicDescDraftMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuPicDescService;
import com.aiqin.bms.scmp.api.supplier.domain.FilePathEnum;
import com.aiqin.bms.scmp.api.supplier.service.FileInfoService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 15:22
 */
@Service
public class ProductSkuPicDescServiceImpl implements ProductSkuPicDescService {
    @Autowired
    ProductSkuPicDescDao productSkuPicDescDao;
    @Autowired
    private ProductSkuPicDescDraftMapper descDraftMapper;
    @Autowired
    private FileInfoService fileInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int insertDraftList(List<ProductSkuPicDescDraft> productSkuPicDescDrafts) {
        int num = productSkuPicDescDao.insertDraftList(productSkuPicDescDrafts);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertDraftList(String applyCode) {
        List<ApplyProductSkuPicDesc> applyProductSkuPicDescs = productSkuPicDescDao.getApply(null,applyCode);
        if(CollectionUtils.isNotEmptyCollection(applyProductSkuPicDescs)){
            List<ProductSkuPicDescDraft> productSkuPicDescDrafts = BeanCopyUtils.copyList(applyProductSkuPicDescs,ProductSkuPicDescDraft.class);
            return ((ProductSkuPicDescService)AopContext.currentProxy()).insertDraftList(productSkuPicDescDrafts);
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertList(List<ProductSkuPicDesc> productSkuPicDescs) {
        int num = productSkuPicDescDao.insertList(productSkuPicDescs);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveList(String skuCode,String applyCode) {
        List<ApplyProductSkuPicDesc> applyProductSkuPicDescs = productSkuPicDescDao.getApply(skuCode,applyCode);
        if (null != applyProductSkuPicDescs && applyProductSkuPicDescs.size() > 0){
            List<ProductSkuPicDesc> productSkuPicDescs = new ArrayList<>();
            List<ProductSkuPicDesc> oldInfo = productSkuPicDescDao.getInfo(skuCode);
            String destinationPicKey = new StringBuilder().append(FilePathEnum.PRODUCT_PICTURE.getFilePath()).append(skuCode).append("/").toString();
            applyProductSkuPicDescs.forEach(item->{
                ProductSkuPicDesc productSkuPicDesc = new ProductSkuPicDesc();
                BeanCopyUtils.copy(item,productSkuPicDesc);
                //重置图片URL
                if (StringUtils.isNotBlank(item.getPicDescPath())) {
                    Map<String, String> map = fileInfoService.getKeyAndType(item.getPicDescPath());
                    if(null != map) {
                        String destinationKey = new StringBuilder().append(destinationPicKey).append("sm_").append(item.getSortingNumber() + 1).append(map.get("contentType")).toString();
                        if (!map.get("key").endsWith(destinationKey)) {
                            String newUrl = fileInfoService.copyObject(map.get("key"), destinationKey, false);
                            if (StringUtils.isNotBlank(newUrl)) {
                                productSkuPicDesc.setPicDescPath(newUrl);
                            }
                        }
                    }
                }
                productSkuPicDescs.add(productSkuPicDesc);
            });
            if (null != oldInfo && oldInfo.size() > 0){
                productSkuPicDescDao.deleteList(skuCode);
            }
            return ((ProductSkuPicDescService)AopContext.currentProxy()).insertList(productSkuPicDescs);
        } else {
            return 0;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveApplyList(List<ApplyProductSku> applyProductSkus) {
        try {
            List<ProductSkuPicDescDraft> productSkuPicDescDrafts = productSkuPicDescDao.getDrafts(applyProductSkus);
            List<ApplyProductSkuPicDesc> applyProductSkuPicDescs = new ArrayList<>();
            if (productSkuPicDescDrafts !=null && productSkuPicDescDrafts.size() > 0){
                for (int i =0;i<productSkuPicDescDrafts.size();i++){
                    ApplyProductSkuPicDesc applyProductSkuPicDesc = new ApplyProductSkuPicDesc();
                    BeanCopyUtils.copy(productSkuPicDescDrafts.get(i),applyProductSkuPicDesc);
                    applyProductSkuPicDesc.setApplyCode(applyProductSkus.get(0).getApplyCode());
                    applyProductSkuPicDescs.add(applyProductSkuPicDesc);
                }
                //批量新增申请
                ((ProductSkuPicDescService) AopContext.currentProxy()).insertApplyList(applyProductSkuPicDescs);
                //批量删除草稿
                productSkuPicDescDao.deleteDrafts(applyProductSkus);
            }
            return 1;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int insertApplyList(List<ApplyProductSkuPicDesc> applyProductSkuPicDescs) {
        int num = productSkuPicDescDao.insertApplyList(applyProductSkuPicDescs);
        return num;
    }

    /**
     * 获取临时数据
     *
     * @param skuCode
     * @return
     */
    @Override
    public List<ProductSkuPicDescRespVo> getDraftList(String skuCode) {
        return productSkuPicDescDao.getDraft(skuCode);
    }

    /**
     * 删除临时表数据
     *
     * @param skuCodes
     * @return
     */
    @Override
    public Integer deleteDrafts(List<String> skuCodes) {
        return descDraftMapper.delete(skuCodes);
    }

    /**
     * 功能描述: 获取申请数据
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 23:16
     */
    @Override
    public List<ProductSkuPicDescRespVo> getApply(String skuCode, String applyCode) {
        return productSkuPicDescDao.getApplys(skuCode,applyCode);
    }

    /**
     * 功能描述: 获取正式数据
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 17:32
     */
    @Override
    public List<ProductSkuPicDescRespVo> getList(String skuCode) {
        return productSkuPicDescDao.getRespVoBySkuCode(skuCode);
    }
}
