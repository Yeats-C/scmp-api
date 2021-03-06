package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.dto.changeprice.ProductSkuChangePriceDTO;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-07-04
 * @time: 14:14
 */
public interface SaveChangePrice {
    /**
     * 保存变价
     * @author NullPointException
     * @date 2019/7/4
     * @param
     * @return java.lang.Boolean
     */
    Boolean save(ProductSkuChangePriceDTO dto);
}
