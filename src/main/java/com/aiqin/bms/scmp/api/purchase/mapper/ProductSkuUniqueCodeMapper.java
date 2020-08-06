package com.aiqin.bms.scmp.api.purchase.mapper;


import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.ProductSkuUniqueCode;

import java.util.List;

public interface ProductSkuUniqueCodeMapper {

    int insertAll(List<ProductSkuUniqueCode> productSkuUniqueCodes);
}
