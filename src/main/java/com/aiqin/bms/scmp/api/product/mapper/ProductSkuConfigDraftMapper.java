package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuConfigDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;

import java.util.List;

public interface ProductSkuConfigDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuConfigDraft record);

    int insertSelective(ProductSkuConfigDraft record);

    ProductSkuConfigDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuConfigDraft record);

    int updateByPrimaryKey(ProductSkuConfigDraft record);

    List<SkuConfigsRepsVo> getList(String companyCode);

    List<ProductSkuConfigDraft> selectByCodes(List<String> codes);

    int deleteByConfigCodes(List<String> codes);
    int deleteOutByConfigCodes(List<String> codes);

    List<ProductSkuConfigDraft> getListBySkuCodes(List<String> skuCodes);


    List<SkuConfigsRepsVo> getListBySkuCode(String skuCode);


    int deleteBySkuCodes(List<String> skuCodes);


    /**
     * 批量添加
     * @param records
     * @return
     */
    int insertBatch(List<ProductSkuConfigDraft> records);
}