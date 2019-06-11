package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuLabelInfo;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/14 0014 16:38
 */
public interface ProductLabelDao {

    List<ProductSkuLabelInfo> getLabelListBySkuCode(String skuCode);

}
