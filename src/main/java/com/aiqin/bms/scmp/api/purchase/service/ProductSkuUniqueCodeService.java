package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.ProductSkuUniqueCode;

import javax.validation.Valid;
import java.util.List;

public interface ProductSkuUniqueCodeService {
    Boolean saveUnique(@Valid List<ProductSkuUniqueCode> reqVO);
}
