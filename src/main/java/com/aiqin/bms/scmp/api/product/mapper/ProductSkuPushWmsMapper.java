package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPushWms;

import java.util.List;

public interface ProductSkuPushWmsMapper {

    List<ProductSkuPushWms> selectAll();

    List<String> selectAllSkuCode();

    Integer updateWmsStatusBySkuCode(String skuCode);
}
