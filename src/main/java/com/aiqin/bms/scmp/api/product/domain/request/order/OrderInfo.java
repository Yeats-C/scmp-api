package com.aiqin.bms.scmp.api.product.domain.request.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-01-15
 * @time: 10:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {
    /**
     * 订单主表信息
     */
    private SupplyOrderInfo orderInfo;
    /**
     * 商品信息
     */
    private List<SupplyOrderProductItem> productItem;
}
