package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.product.dao.ProductSkuInspReportDao;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuInspReportDraftMapper;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuInspReportRespVo;
import com.aiqin.bms.scmp.api.product.service.ProductSkuInspReportService;
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
 * @date: 2019/3/13 0013 16:32
 */
@Service
public class ProductSkuInspReportServiceImpl implements ProductSkuInspReportService {
    @Autowired
    ProductSkuInspReportDao productSkuInspReportDao;
    @Autowired
    private ProductSkuInspReportDraftMapper draftMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveApplyList(List<ApplyProductSku> applyProductSkus) {
       try {
            List<ApplyProductSkuInspReport> applyProductSkuInspReports = new ArrayList<>();
            List<ProductSkuInspReportDraft> productSkuInspReportDrafts = productSkuInspReportDao.getDrafts(applyProductSkus);
            if (productSkuInspReportDrafts != null && productSkuInspReportDrafts.size() > 0){
                for (int i=0;i<productSkuInspReportDrafts.size();i++){
                    ApplyProductSkuInspReport applyProductSkuInspReport = new ApplyProductSkuInspReport();
                    BeanCopyUtils.copy(productSkuInspReportDrafts.get(i),applyProductSkuInspReport);
                    applyProductSkuInspReport.setApplyCode(applyProductSkus.get(0).getApplyCode());
                    applyProductSkuInspReports.add(applyProductSkuInspReport);
                }
                //批量新增申请
                ((ProductSkuInspReportService) AopContext.currentProxy()).insertApplyList(applyProductSkuInspReports);
                //批量删除草稿
                productSkuInspReportDao.deleteDrafts(applyProductSkus);
            }
            return 1;
       } catch (BizException e){
           throw new BizException(e.getMessage());
       }
    }

    @Override
    @SaveList
    @Transactional(rollbackFor = BizException.class)
    public int insertApplyList(List<ApplyProductSkuInspReport> applyProductSkuInspReports) {
        try {
            return productSkuInspReportDao.insertApplyList(applyProductSkuInspReports);
        } catch (BizException e){
            return 0;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveList(String skuCode,String applyCode) {
        List<ApplyProductSkuInspReport> applyProductSkuInspReports = productSkuInspReportDao.getApply(skuCode,applyCode);
        if (null != applyProductSkuInspReports && applyProductSkuInspReports.size() > 0){
            List<ProductSkuInspReport> oldReport = productSkuInspReportDao.getInfo(skuCode);
            List<ProductSkuInspReport> productSkuInspReports = new ArrayList<>();
            applyProductSkuInspReports.forEach(item->{
                ProductSkuInspReport productSkuInspReport = new ProductSkuInspReport();
                BeanCopyUtils.copy(item,productSkuInspReport);
                productSkuInspReports.add(productSkuInspReport);
            });
            if (null != oldReport && oldReport.size() > 0){
                productSkuInspReportDao.deleteList(skuCode);
            }
            return ((ProductSkuInspReportService)AopContext.currentProxy()).insertList(productSkuInspReports);
        } else {
            return 0;
        }
    }

    @Override
    @SaveList
    @Transactional(rollbackFor = BizException.class)
    public int insertList(List<ProductSkuInspReport> productSkuInspReports) {
        int num = productSkuInspReportDao.insertInspReportList(productSkuInspReports);
        return num;
    }

    @Override
    @SaveList
    @Transactional(rollbackFor = BizException.class)
    public int insertDraftList(List<ProductSkuInspReportDraft> productSkuInspReportDrafts) {
        int num = productSkuInspReportDao.insertDraftList(productSkuInspReportDrafts);
        return num;
    }

    /**
     * 获取临时数据
     *
     * @param skuCode
     * @return
     */
    @Override
    public List<ProductSkuInspReportRespVo> getDraftList(String skuCode) {
        return productSkuInspReportDao.getDraft(skuCode);
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
}
