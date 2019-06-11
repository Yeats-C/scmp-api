package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.ground.util.protocol.http.HttpResponse;

/**
 * Createed by sunx on 2018/11/22.<br/>
 */
public interface BrandService {
    HttpResponse selectAllBrand();

    HttpResponse selectAllTopBrand();
}
