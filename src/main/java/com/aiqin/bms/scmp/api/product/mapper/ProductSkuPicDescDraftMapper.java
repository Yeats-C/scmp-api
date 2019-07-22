package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPicDescDraft;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPicturesDraft;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSkuPicDescDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuPicDescDraft record);

    int insertSelective(ProductSkuPicDescDraft record);

    ProductSkuPicDescDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuPicDescDraft record);

    int updateByPrimaryKey(ProductSkuPicDescDraft record);

    Integer delete(List<String> skuCodes);

    Integer insertAll(@Param("list") List<ProductSkuPicturesDraft> productSkuPicturesDraftList);
}