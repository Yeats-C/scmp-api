package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSaleAreaInfoDraft;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSkuSaleAreaInfoDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuSaleAreaInfoDraft record);

    int insertSelective(ProductSkuSaleAreaInfoDraft record);

    ProductSkuSaleAreaInfoDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuSaleAreaInfoDraft record);

    int updateByPrimaryKey(ProductSkuSaleAreaInfoDraft record);
    /**
     * 批量插入区域信息
     * @author NullPointException
     * @date 2019/6/3
     * @param productSkuSaleAreaInfoDrafts
     * @return int
     */
    int insertBatch(@Param("items") List<ProductSkuSaleAreaInfoDraft> productSkuSaleAreaInfoDrafts);
    /**
     * 批量删除
     * @author NullPointException
     * @date 2019/6/4
     * @param codes
     * @return int
     */
    int deleteDraftBatchByCodes(@Param("items") List<String> codes);
}