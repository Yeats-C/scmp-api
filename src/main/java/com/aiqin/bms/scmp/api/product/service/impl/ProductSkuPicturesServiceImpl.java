package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuPicturesDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPictures;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPictures;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPicturesDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuPicturesRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuPicturesDraftMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuPicturesService;
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
 * @date: 2019/1/29 0029 15:05
 */
@Service
public class ProductSkuPicturesServiceImpl implements ProductSkuPicturesService {
    @Autowired
    ProductSkuPicturesDao productSkuPicturesDao;
    @Autowired
    private ProductSkuPicturesDraftMapper draftMapper;
    @Autowired
    private FileInfoService fileInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int insertDraftList(List<ProductSkuPicturesDraft> productSkuPicturesDrafts) {
        int num = productSkuPicturesDao.insertDraftList(productSkuPicturesDrafts);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertDraftList(String applyCode) {
        List<ApplyProductSkuPictures> applyProductSkuPictures = productSkuPicturesDao.getApply(null,applyCode);
        if(CollectionUtils.isNotEmptyCollection(applyProductSkuPictures)){
            List<ProductSkuPicturesDraft> productSkuPicturesDrafts = BeanCopyUtils.copyList(applyProductSkuPictures,ProductSkuPicturesDraft.class);
           return ((ProductSkuPicturesService)AopContext.currentProxy()).insertDraftList(productSkuPicturesDrafts);
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertList(List<ProductSkuPictures> productSkuPictures) {
        int num = productSkuPicturesDao.insertList(productSkuPictures);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveList(String skuCode,String applyCode) {
        List<ApplyProductSkuPictures> applyProductSkuPictures = productSkuPicturesDao.getApply(skuCode,applyCode);
        if (null != applyProductSkuPictures && applyProductSkuPictures.size() > 0){
            List<ProductSkuPictures> productSkuPicturesList = new ArrayList<>();
            List<ProductSkuPictures> oldInfo = productSkuPicturesDao.getInfo(skuCode);
            String destinationPicKey = new StringBuilder().append(FilePathEnum.PRODUCT_PICTURE.getFilePath()).append(skuCode).append("/").toString();
            String destinationKey = "";
            int i = 1;
            for (ApplyProductSkuPictures item : applyProductSkuPictures) {
                ProductSkuPictures productSkuPictures = new ProductSkuPictures();
                BeanCopyUtils.copy(item,productSkuPictures);
                //重置图片URL
                if (StringUtils.isNotBlank(item.getProductPicturePath())) {
                    Map<String, String> map = fileInfoService.getKeyAndType(item.getProductPicturePath());
                    if(null != map){
                        destinationKey = new StringBuilder().append(destinationPicKey).append(i).append(map.get("contentType")).toString();
                        if(!map.get("key").endsWith(destinationKey)){
                            String newUrl = fileInfoService.copyObject(map.get("key"), destinationKey, false);
                            if(StringUtils.isNotBlank(newUrl)){
                                productSkuPictures.setProductPicturePath(newUrl);
                            }
                        }
                    }
                }
                productSkuPicturesList.add(productSkuPictures);
                i++;
            }
            if (null != oldInfo && oldInfo.size() > 0){
                productSkuPicturesDao.deleteList(skuCode);
            }
            return ((ProductSkuPicturesService)AopContext.currentProxy()).insertList(productSkuPicturesList);
        } else {
            return 0;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveApplyList(List<ApplyProductSku> productSkus) {
        try {
            List<ProductSkuPicturesDraft> productSkuPicturesDrafts = productSkuPicturesDao.getDrafts(productSkus);
            List<ApplyProductSkuPictures> applyProductSkuPicturesList = new ArrayList<>();
            if(productSkuPicturesDrafts != null && productSkuPicturesDrafts.size() > 0){
                for (int i = 0;i<productSkuPicturesDrafts.size();i++){
                    ApplyProductSkuPictures applyProductSkuPictures = new ApplyProductSkuPictures();
                    BeanCopyUtils.copy(productSkuPicturesDrafts.get(i),applyProductSkuPictures);
                    applyProductSkuPictures.setApplyCode(productSkus.get(0).getApplyCode());
                    applyProductSkuPicturesList.add(applyProductSkuPictures);
                }
                //批量新增申请
                ((ProductSkuPicturesService) AopContext.currentProxy()).insertApplyList(applyProductSkuPicturesList);
                //批量删除草稿
                productSkuPicturesDao.deleteDrafts(productSkus);
            }
            return 1;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int insertApplyList(List<ApplyProductSkuPictures> applyProductSkuPictures) {
        int num = productSkuPicturesDao.insertApplyList(applyProductSkuPictures);
        return num;
    }

    /**
     * 临时表数据
     *
     * @param skuCode
     * @return
     */
    @Override
    public List<ProductSkuPicturesRespVo> getDraftList(String skuCode) {
        return productSkuPicturesDao.getDraftInfo(skuCode);
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
     * @date 2019/7/6 23:14
     */
    @Override
    public List<ProductSkuPicturesRespVo> getApply(String skuCode, String applyCode) {
        return productSkuPicturesDao.getApplys(skuCode,applyCode);
    }

    /**
     * 功能描述: 获取正式数据
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 17:31
     */
    @Override
    public List<ProductSkuPicturesRespVo> getList(String skuCode) {
        return productSkuPicturesDao.getRespInfoBySkuCode(skuCode);
    }
}


