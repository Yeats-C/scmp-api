package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuFileDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuFile;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuFile;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuFileDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuFileRespVO;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuFileDraftMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuFileService;
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
 * @date: 2019/1/29 0029 15:40
 */
@Service
public class ProductSkuFileServiceImpl implements ProductSkuFileService {
    @Autowired
    ProductSkuFileDao productSkuFileDao;
    @Autowired
    private ProductSkuFileDraftMapper draftMapper;
    @Override
    @SaveList
    @Transactional(rollbackFor = BizException.class)
    public int insertDraftList(List<ProductSkuFileDraft> productSkuFileDrafts) {
        int num = productSkuFileDao.insertDraftList(productSkuFileDrafts);
        return num;
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    public int insertList(List<ProductSkuFile> productSkuFiles) {
        int num = productSkuFileDao.insertList(productSkuFiles);
        return num;
    }

    @Override
    @SaveList
    @Transactional(rollbackFor = BizException.class)
    public int insertApplyList(List<ApplyProductSkuFile> applyProductSkuFiles) {
        int num = productSkuFileDao.insertApplyList(applyProductSkuFiles);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveApplyList(List<ApplyProductSku> applyProductSkus) {
        try {
            List<ApplyProductSkuFile> applyProductSkuFiles = new ArrayList<>();
            List<ProductSkuFileDraft> productSkuFileDrafts = productSkuFileDao.getDrafts(applyProductSkus);
            if (productSkuFileDrafts != null && productSkuFileDrafts.size() > 0){
                for (int i=0;i<productSkuFileDrafts.size();i++){
                    ApplyProductSkuFile applyProductSkuFile = new ApplyProductSkuFile();
                    BeanCopyUtils.copy(productSkuFileDrafts.get(i),applyProductSkuFile);
                    applyProductSkuFile.setApplyCode(applyProductSkus.get(0).getApplyCode());
                    applyProductSkuFiles.add(applyProductSkuFile);
                }
                //批量新增申请
                ((ProductSkuFileService) AopContext.currentProxy()).insertApplyList(applyProductSkuFiles);
                //批量删除草稿
                productSkuFileDao.deleteDrafts(applyProductSkus);
            }
            return 1;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveList(String skuCode,String applyCode) {
        List<ApplyProductSkuFile> applyProductSkuFiles = productSkuFileDao.getApply(skuCode,applyCode);
        List<ProductSkuFile> oldSkuFiles = productSkuFileDao.getInfo(skuCode);
        if (null != applyProductSkuFiles && applyProductSkuFiles.size() > 0){
            List<ProductSkuFile> productSkuFiles = new ArrayList<>();
            applyProductSkuFiles.forEach(item->{
                ProductSkuFile productSkuFile = new ProductSkuFile();
                BeanCopyUtils.copy(item,productSkuFile);
                productSkuFiles.add(productSkuFile);
            });

            if (null != oldSkuFiles && oldSkuFiles.size() > 0){
                //如果以前存在记录则先删除再新增
                productSkuFileDao.deleteList(skuCode);
            }
            return ((ProductSkuFileService) AopContext.currentProxy()).insertList(productSkuFiles);
        } else {
            return 0;
        }
    }

    /**
     * 获取临时数据
     *
     * @param skuCode
     * @return
     */
    @Override
    public List<ProductSkuFileRespVO> getDraftList(String skuCode) {
        return productSkuFileDao.getDraft(skuCode);
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
     * 功能描述: 获取申请数据
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 23:19
     */
    @Override
    public List<ProductSkuFileRespVO> getApply(String skuCode, String applyCode) {
        return productSkuFileDao.getApplys(skuCode,applyCode);
    }

    /**
     * 功能描述: 获取正式数据
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 17:35
     */
    @Override
    public List<ProductSkuFileRespVO> getList(String skuCode) {
        return productSkuFileDao.getRespVoBySkuCode(skuCode);
    }
}
