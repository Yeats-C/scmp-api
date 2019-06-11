package com.aiqin.bms.scmp.api.product.domain.response.sku.oms;

import lombok.Data;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/28 0028 14:28
 */
@Data
public class OmsSkuTagResp {
    private Byte mainPush;
    private Byte newProduct;
    private String productCode;
}
