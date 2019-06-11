package com.aiqin.bms.scmp.api.product.dao;


import com.aiqin.bms.scmp.api.product.domain.ProductPriceChange;
import com.aiqin.bms.scmp.api.product.domain.request.ProductPriceChangeQuery;

import java.util.List;

/**
 * @author Joker
 * @date 2019/2/27 上午11:36
 */
public interface ProductPriceChangeDao  {

    /**
     * 新增商品变价记录
     * @param change
     * @return
     */
    Integer insert(ProductPriceChange change);

    /**
     * 获取变价记录列表
     * @param query
     * @return
     */
    List<ProductPriceChange> listProductPriceChange(ProductPriceChangeQuery query);

    /**
     * 统计数量
     * @return
     */
    Integer countPriceChange(ProductPriceChangeQuery query);

}
