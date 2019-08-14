package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.product.domain.pojo.NewProduct;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductSaveReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductUpdateReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.QueryNewProductReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.NewProductResponseVO;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.NewSkuDetailsResponseVO;
import com.aiqin.bms.scmp.api.product.mapper.NewProductMapper;
import com.aiqin.bms.scmp.api.product.service.NewProductService;
import com.aiqin.bms.scmp.api.product.service.ProductCommonService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class NewProductServiceImpl extends BaseServiceImpl implements NewProductService {
    @Autowired
    private NewProductMapper newProductMapper;
    @Autowired
    private EncodingRuleDao encodingRuleDao;
    @Autowired
    private ProductCommonService productCommonService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insertProduct(NewProductSaveReqVO newProductSaveReqVO) {
        int flg = 0;
        EncodingRule encodingRule = encodingRuleDao.getNumberingType("PRODUCT_CODE");
        long code = encodingRule.getNumberingValue();
        encodingRuleDao.updateNumberValue(code, encodingRule.getId());
        NewProduct newProduct = new NewProduct();
        newProduct.setProductCode(Long.toString(code));
        BeanCopyUtils.copy(newProductSaveReqVO, newProduct);
        flg = ((NewProductService) AopContext.currentProxy()).save(newProduct);
        productCommonService.getInstance(Long.toString(code), HandleTypeCoce.ADD_PRODUCT.getStatus(), ObjectTypeCode.PRODUCT_MANAGEMENT.getStatus(), newProduct, HandleTypeCoce.ADD_PRODUCT.getName());
        return newProduct.getProductCode();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateProduct(NewProductUpdateReqVO newProductUpdateReqVO) {
        String productCode = newProductUpdateReqVO.getProductCode();
        ExceptionId(productCode);

        NewProduct newProduct = newProductMapper.getProductCode(productCode);
        int i = newProductMapper.checkName(newProductUpdateReqVO.getProductName(), newProduct.getCompanyCode(), productCode);
        if(i>0){
            throw  new BizException(ResultCode.SPU_NAME_EXISTS);
        }
        BeanCopyUtils.copy(newProductUpdateReqVO, newProduct);
        //设置审批状态为审批中
       //newProduct.setApplyStatus(ApplyStatus.APPROVAL.getNumber());
        int flg = ((NewProductService) AopContext.currentProxy()).update(newProduct);
        productCommonService.getInstance(newProduct.getProductCode(), HandleTypeCoce.UPDATE_PRODUCT.getStatus(), ObjectTypeCode.PRODUCT_MANAGEMENT.getStatus(), newProduct, HandleTypeCoce.UPDATE_PRODUCT.getName());
        return flg;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Save
    public int save(NewProduct newProduct) {
        newProduct.setCompanyCode(getUser().getCompanyCode());
        newProduct.setCompanyName(getUser().getCompanyName());
        int i = newProductMapper.checkName(newProduct.getProductName(), newProduct.getCompanyCode(),null);
        if(i>0){
            throw  new BizException(ResultCode.SPU_NAME_EXISTS);
        }
        int k = newProductMapper.insertSelective(newProduct);
        if (k > 0) {
            return k;
        } else {
            log.error(HandlingExceptionCode.PRODUCT);
            throw new GroundRuntimeException(HandlingExceptionCode.PRODUCT);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Update
    public int update(NewProduct newProduct) {
        int k = newProductMapper.updateByPrimaryKeySelective(newProduct);
        if (k > 0) {
            return k;
        } else {
            log.error(HandlingExceptionCode.PRODUCT_UPDATE);
            throw new GroundRuntimeException(HandlingExceptionCode.PRODUCT_UPDATE);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertList(List<NewProduct> list) {
        int flg = 0;
        if (list != null && list.size() > 0) {
            newProductMapper.deleteList(list);
            flg = newProductMapper.insertList(list);
            if (flg > 0) {
                return flg;
            }
            else {
                log.error(HandlingExceptionCode.PRODUCT);
                throw new GroundRuntimeException(HandlingExceptionCode.PRODUCT);
            }
        }
        return flg;
    }

    @Override
    public void ExceptionId(String productCode) {
        if (StringUtils.isBlank(productCode)) {
            log.error(HandlingExceptionCode.PRODUCT_PRODUCTCODE);
            throw new GroundRuntimeException(HandlingExceptionCode.PRODUCT_PRODUCTCODE);
        }
    }

    @Override
    public BasePage<NewProductResponseVO> getList(QueryNewProductReqVO queryNewProductReqVO) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            queryNewProductReqVO.setCompanyCode(authToken.getCompanyCode());
        }
        PageHelper.startPage(queryNewProductReqVO.getPageNo(), queryNewProductReqVO.getPageSize());
        List<NewProductResponseVO> list = newProductMapper.getList(queryNewProductReqVO);
        return PageUtil.getPageList(queryNewProductReqVO.getPageNo(), list);
    }

    @Override
    public List<NewSkuDetailsResponseVO> productSku(String productCode, String productName) {
        return newProductMapper.productSku(productCode, productName);
    }

    @Override
    public List<NewProductResponseVO> getPageList(QueryNewProductReqVO queryNewProductReqVO) {
        return newProductMapper.getList(queryNewProductReqVO);
    }
   @Override
    public Map<String,NewProduct> selectBySpuName(Set<String> list, String companyCode) {
        return newProductMapper.selectBySpuName(list,companyCode);
    }


}
