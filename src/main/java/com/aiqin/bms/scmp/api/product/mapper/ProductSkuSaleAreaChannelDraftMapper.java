package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSaleAreaChannelDraft;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSkuSaleAreaChannelDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuSaleAreaChannelDraft record);

    int insertSelective(ProductSkuSaleAreaChannelDraft record);

    ProductSkuSaleAreaChannelDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuSaleAreaChannelDraft record);

    int updateByPrimaryKey(ProductSkuSaleAreaChannelDraft record);
    /**
     * 批量插入渠道信息
     * @author NullPointException
     * @date 2019/6/4
     * @param channelDrafts
     * @return int
     */
    int insertBatch(@Param("items") List<ProductSkuSaleAreaChannelDraft> channelDrafts);
    /**
     * 批量删除
     * @author NullPointException
     * @date 2019/6/4
     * @param codes
     * @return int
     */
    int deleteDraftBatchByCodes(@Param("items") List<String> codes);
}