package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductDraft;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductSaveReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductUpdateReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.draft.ProductSkuDraftRespVo;

import java.util.List;

public interface ApplyProductDraftService {
    int deleteByPrimaryKey(Long id);

    int deleteCode(List<String> productCode);

    int insert(ApplyProductDraft record);

    int insertSelective(ApplyProductDraft record);

    ApplyProductDraft selectByPrimaryKey(Long id);


    int updateByPrimaryKeySelective(ApplyProductDraft record);

    String insertProduct(NewProductSaveReqVO newProductSaveReqVO);

    int updateProduct(NewProductUpdateReqVO newProductUpdateReqVO);

    void ExceptionId(String id);


    Integer getName(String proName);

    ApplyProductDraft getDraftByProductCode(String productCode);


    /**
     * 根据公司信息获取Sku临时信息
     * @param companyCode
     * @return
     */
    List<ProductSkuDraftRespVo> getProductListDraftByCompanyCode(String companyCode);


}
