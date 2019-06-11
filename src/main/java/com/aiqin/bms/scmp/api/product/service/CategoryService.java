package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.ground.util.protocol.http.HttpResponse;

/**
 * Createed by sunx on 2018/11/22.<br/>
 */
public interface CategoryService {
    HttpResponse selectProductCategoryByParentId(String parentId);

    HttpResponse selectCategoryDisInfoByDisId(String distributorId);
}
