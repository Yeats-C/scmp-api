package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuManufacturerDao;
import com.aiqin.bms.scmp.api.product.domain.excel.SkuManufactureImport;
import com.aiqin.bms.scmp.api.product.domain.excel.SkuManufacturerImportMain;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.sku.ProductSkuManufacturerReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.*;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuManufacturerDraftMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuManufacturerService;
import com.aiqin.bms.scmp.api.product.service.SkuInfoService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.bms.scmp.api.util.excel.exception.ExcelException;
import com.aiqin.bms.scmp.api.util.excel.utils.ExcelUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    @Autowired
    private ProductSkuDao productSkuDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int insertDraftList(List<ProductSkuManufacturerDraft> productSkuManufacturerDrafts) {
        int num = productSkuManufacturerDao.insertDraftList(productSkuManufacturerDrafts);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertDraftList(String applyCode) {
        List<ApplyProductSkuManufacturer> applyProductSkuManufacturers = productSkuManufacturerDao.getApply(null,applyCode);
        if(CollectionUtils.isNotEmptyCollection(applyProductSkuManufacturers)){
            List<ProductSkuManufacturerDraft> productSkuManufacturerDrafts = BeanCopyUtils.copyList(applyProductSkuManufacturers, ProductSkuManufacturerDraft.class);
            return ((ProductSkuManufacturerService)AopContext.currentProxy()).insertDraftList(productSkuManufacturerDrafts);
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertList(List<ProductSkuManufacturer> productSkuManufacturers) {
        int num = productSkuManufacturerDao.insertList(productSkuManufacturers);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveList(String skuCode,String applyCode) {
        List<ApplyProductSkuManufacturer> applyProductSkuManufacturers = productSkuManufacturerDao.getApply(skuCode,applyCode);
        if (CollectionUtils.isNotEmptyCollection(applyProductSkuManufacturers)){
            List<ProductSkuManufacturer> productSkuManufacturers = BeanCopyUtils.copyList(applyProductSkuManufacturers,ProductSkuManufacturer.class);
            productSkuManufacturerDao.deleteList(skuCode);
            return ((ProductSkuManufacturerService)AopContext.currentProxy()).insertList(productSkuManufacturers);
        } else {
            return 0;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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

    /**
     * 功能描述: 正式数据
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 17:13
     */
    @Override
    public List<ProductSkuManufacturerRespVo> getList(String skuCode) {
        return productSkuManufacturerDao.getRespVo(skuCode);
    }

    @Override
    public BasePage<ProductSkuManufacturerRespVo> getList(ProductSkuManufacturerReqVO productSkuManufacturerReqVO) {
        if (StringUtils.isNotBlank(productSkuManufacturerReqVO.getPersonId())) {
            AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
            if (Objects.nonNull(currentAuthToken)) {
                productSkuManufacturerReqVO.setPersonId(currentAuthToken.getPersonId());
            }
        }
        PageHelper.startPage(productSkuManufacturerReqVO.getPageNo(), productSkuManufacturerReqVO.getPageSize());
        List<ProductSkuManufacturerRespVo> list = productSkuManufacturerDao.getPageList(productSkuManufacturerReqVO);
        return PageUtil.getPageList(productSkuManufacturerReqVO.getPageNo(), list);
    }

    @Override
    public ProductSkuManufacturerDetailRespVo getDetail(String skuCode) {
        // 获取sku信息
        ProductSkuRespVo skuRespVo = productSkuDao.getSkuInfoResp(skuCode);
        // 获取制造商信息
        List<ProductSkuManufacturerRespVo> manufacturerRespVos = productSkuManufacturerDao.getRespVo(skuCode);
        ProductSkuManufacturerDetailRespVo detailRespVo = new ProductSkuManufacturerDetailRespVo();
        detailRespVo.setProductSkuInfo(skuRespVo);
        detailRespVo.setProductSkuManufacturerRespVo(manufacturerRespVos);
        return detailRespVo;
    }

    @Override
    public Boolean deleteById(String id) {
        productSkuManufacturerDao.deleteById(Long.valueOf(id));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean save(List<ProductSkuManufacturer> productSkuManufacturer) {
        if (CollectionUtils.isNotEmptyCollection(productSkuManufacturer)) {
            String skuCode = productSkuManufacturer.get(0).getProductSkuCode();
            if (StringUtils.isNotBlank(skuCode)) {
                productSkuManufacturerDao.deleteList(skuCode);
            }
            productSkuManufacturerDao.insertList(productSkuManufacturer);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SkuManufacturerImportMain importManufacturer(MultipartFile file) {
        List<SkuManufactureImport> importList;
        try {
             importList = ExcelUtil.readExcel(file, SkuManufactureImport.class, 1, 1);
        } catch (ExcelException e) {
            throw new BizException(ResultCode.IMPORT_DATA_ERROR);
        }
        List<String> errors = Lists.newArrayList();
        for (int i = 0; i < importList.size(); i++) {
            // 检查信息
            StringBuilder stringBuilder = new StringBuilder();
            // 检查skuCode否为空
            if (StringUtils.isBlank(importList.get(i).getProductSkuCode())) {
                stringBuilder.append("sku编号不能为空;");
            } else {
                // 检查数据库是否存在该sku
                ProductSkuInfo skuInfo = productSkuDao.getSkuInfo(importList.get(i).getProductSkuCode());
                if (Objects.isNull(skuInfo)) {
                    stringBuilder.append("不存在skuCode:").append(importList.get(i).getProductSkuCode()).append(";");
                } else {
                    // 从数据库取skuName
                    importList.get(i).setProductSkuName(skuInfo.getProductName());
                }
            }
            // 默认值
            if (StringUtils.isBlank(importList.get(i).getIsDefaultDesc())) {
                stringBuilder.append("默认值不能为空;");
            } else {
                if (StringUtils.equals(importList.get(i).getIsDefaultDesc(), "是")) {
                    importList.get(i).setIsDefault((byte) 1);
                } else if(StringUtils.equals(importList.get(i).getIsDefaultDesc(), "否")) {
                    importList.get(i).setIsDefault((byte) 0);
                } else {
                    stringBuilder.append("默认值格式不正确");
                }
            }
            // 状态
            if (StringUtils.isBlank(importList.get(i).getIsDefaultDesc())) {
                stringBuilder.append("状态不能为空;");
            } else {
                if (StringUtils.equals(importList.get(i).getUsageStatusDesc(), "在用")) {
                    importList.get(i).setUsageStatus((byte) 1);
                } else if(StringUtils.equals(importList.get(i).getUsageStatusDesc(), "禁用")) {
                    importList.get(i).setUsageStatus((byte) 0);
                } else {
                    stringBuilder.append("状态格式不正确");
                }
            }
            if (StringUtils.isNotBlank(stringBuilder.toString())) {
                String s = "第" + (i + 2) + "行 " + stringBuilder.toString();
                errors.add(s);
            }
        }
        SkuManufacturerImportMain manufacturerImportMain = new SkuManufacturerImportMain();
        if (CollectionUtils.isNotEmptyCollection(errors)) {
            manufacturerImportMain.setError(errors);
        }
        List<ProductSkuManufacturer> manufacturerList = BeanCopyUtils.copyList(importList, ProductSkuManufacturer.class);
        manufacturerImportMain.setAddList(manufacturerList);
        manufacturerImportMain.setSkuManufactureImports(importList);
        // 返回实体
        return manufacturerImportMain;
    }

    @Override
    public Boolean importManufacturerSave(SkuManufacturerImportMain importMain) {
        List<ProductSkuManufacturer> addList = importMain.getAddList();
        if (CollectionUtils.isNotEmptyCollection(addList)) {
            productSkuManufacturerDao.insertList(addList);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
