package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.HandlingExceptionCode;
import com.aiqin.bms.scmp.api.common.Save;
import com.aiqin.bms.scmp.api.common.StatusTypeCode;
import com.aiqin.bms.scmp.api.common.Update;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductDraft;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductSaveReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductUpdateReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.draft.ProductSkuDraftRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ApplyProductDraftMapper;
import com.aiqin.bms.scmp.api.product.service.ApplyProductDraftService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ApplyProductDraftServiceImpl implements ApplyProductDraftService {

    @Autowired
    private EncodingRuleDao encodingRuleDao;

    @Autowired
    private ApplyProductDraftMapper applyProductDraftMapper;


    @Override
    public int deleteByPrimaryKey(Long id) {
        return applyProductDraftMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteCode(List<String> productCode) {
        return applyProductDraftMapper.deleteCode(productCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Save
    public int insert(ApplyProductDraft record) {
        return applyProductDraftMapper.insert(record);
    }

    @Override
    @Save
    @Transactional(rollbackFor = Exception.class)
    public int insertSelective(ApplyProductDraft record) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            record.setCompanyCode(authToken.getCompanyCode());
            record.setCompanyName(authToken.getCompanyName());
        }
        int k = applyProductDraftMapper.insertSelective(record);
        if (k > 0) {
            return k;
        } else {
            log.error(HandlingExceptionCode.APPLY_PRODUCT_ADD);
            throw new GroundRuntimeException(HandlingExceptionCode.APPLY_PRODUCT_ADD);
        }
    }

    @Override
    public ApplyProductDraft selectByPrimaryKey(Long id) {
        return applyProductDraftMapper.selectByPrimaryKey(id);
    }

    @Override
    @Update
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(ApplyProductDraft record) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            record.setCompanyCode(authToken.getCompanyCode());
            record.setCompanyName(authToken.getCompanyName());
        }
        int k = applyProductDraftMapper.updateByPrimaryKeySelective(record);
        if (k > 0) {
            return k;
        } else {
            log.error(HandlingExceptionCode.APPLY_PRODUCT_UPDATE);
            throw new GroundRuntimeException(HandlingExceptionCode.APPLY_PRODUCT_UPDATE);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insertProduct(NewProductSaveReqVO newProductSaveReqVO) {
        try {
            EncodingRule encodingRule = encodingRuleDao.getNumberingType("PRODUCT_CODE");
            long code = encodingRule.getNumberingValue();
            encodingRuleDao.updateNumberValue(code, encodingRule.getId());
            Integer size = getName(newProductSaveReqVO.getProductName());
            if (size > 0) {
                throw new GroundRuntimeException(HandlingExceptionCode.PRODUCT_IS_EXIST);
            }
            ApplyProductDraft applyProduct = new ApplyProductDraft();
            applyProduct.setProductCode(code + "");
            BeanCopyUtils.copy(newProductSaveReqVO, applyProduct);
            applyProduct.setDelFlag(HandlingExceptionCode.ZERO);
            applyProduct.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
            applyProduct.setApplyTypeName(StatusTypeCode.ADD_APPLY.getName());
             ((ApplyProductDraftService) AopContext.currentProxy()).insertSelective(applyProduct);
             return Long.toString(code);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateProduct(NewProductUpdateReqVO newProductUpdateReqVO) {
        try {
            String code = newProductUpdateReqVO.getProductCode();
            ExceptionId(code);
            ApplyProductDraft applyProduct = applyProductDraftMapper.getProductCode(code);
            if(null == applyProduct){
                applyProduct = new ApplyProductDraft();
            }
            BeanCopyUtils.copy(newProductUpdateReqVO, applyProduct);
            int i = 0;
            if(null != applyProduct.getId()){
                applyProduct.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
                applyProduct.setApplyTypeName(StatusTypeCode.UPDATE_APPLY.getName());
                i = ((ApplyProductDraftService) AopContext.currentProxy()).updateByPrimaryKeySelective(applyProduct);
            }else{
                applyProduct.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
                applyProduct.setApplyTypeName(StatusTypeCode.ADD_APPLY.getName());
                i = ((ApplyProductDraftService) AopContext.currentProxy()).insertSelective(applyProduct);
            }
            return i;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    @Override
    public void ExceptionId(String id) {
        if (StringUtils.isBlank(id)) {
            log.error(HandlingExceptionCode.PRODUCT_PRODUCTCODE);
            throw new GroundRuntimeException(HandlingExceptionCode.PRODUCT_PRODUCTCODE);
        }
    }

    @Override
    public Integer getName(String proName) {
        return applyProductDraftMapper.getName(proName);
    }

    @Override
    public ApplyProductDraft getDraftByProductCode(String productCode) {
        return applyProductDraftMapper.getProductCode(productCode);
    }

    /**
     * 获取商品临时数据
     *
     * @param companyCode
     * @return
     */
    @Override
    public List<ProductSkuDraftRespVo> getProductListDraftByCompanyCode(String companyCode) {
        return applyProductDraftMapper.getProductDraft(companyCode);
    }
}
