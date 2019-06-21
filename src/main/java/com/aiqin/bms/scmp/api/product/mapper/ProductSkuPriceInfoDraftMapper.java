package com.aiqin.bms.scmp.api.product.mapper;


import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfoDraft;

import java.util.List;

public interface ProductSkuPriceInfoDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuPriceInfoDraft record);

    int insertSelective(ProductSkuPriceInfoDraft record);

    ProductSkuPriceInfoDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuPriceInfoDraft record);

    int updateByPrimaryKey(ProductSkuPriceInfoDraft record);
    /**
     * 批量保存价格数据
     * @author NullPointException
     * @date 2019/6/17
     * @param drafts
     * @return int
     */
    int insertBatch(List<ProductSkuPriceInfoDraft> drafts);
    /**
     * 通过sku编码集合查询时价格信息
     * @author NullPointException
     * @date 2019/6/17
     * @param skuCode
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceDraft>
     */
    List<ProductSkuPriceInfoDraft> selectBySkuCodes(List<String> skuCode);
    /**
     * 通过sku编码删除价格临时数据
     * @author NullPointException
     * @date 2019/6/17
     * @param skuCode
     * @return int
     */
    int deleteBySkuCodes(List<String> skuCode);
}