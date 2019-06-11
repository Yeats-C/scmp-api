package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSaleAreaDraft;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSkuSaleAreaDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuSaleAreaDraft record);

    int insertSelective(ProductSkuSaleAreaDraft record);

    ProductSkuSaleAreaDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuSaleAreaDraft record);

    int updateByPrimaryKey(ProductSkuSaleAreaDraft record);
    /**
     * 通过sku编码查询数据
     * @author NullPointException
     * @date 2019/6/3
     * @param skuCodes
     * @return java.util.List<com.aiqin.mgs.product.api.domain.pojo.ProductSkuSaleAreaDraft>
     */
    List<ProductSkuSaleAreaDraft> selectBySkuCodes(@Param("items") List<String> skuCodes);
    /**
     * 批量插入sku信息
     * @author NullPointException
     * @date 2019/6/3
     * @param skuSaleAreaDrafts
     * @return int
     */
    int insertBatch(@Param("items") List<ProductSkuSaleAreaDraft> skuSaleAreaDrafts);
    /**
     * 批量删除
     * @author NullPointException
     * @date 2019/6/4
     * @param codes
     * @return int
     */
    int deleteDraftBatchByCodes(@Param("items") List<String> codes);
}