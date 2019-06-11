package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;

public interface PriceJobService {
    void price(Stock code);
}
