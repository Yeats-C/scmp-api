package com.aiqin.bms.scmp.api.product.domain.dto.order;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-25
 * @time: 14:55
 */
@Data
public class OrderInfoDTO extends OrderInfo {
    /**
     * 商品
     */
    private List<OrderInfoItemDTO> itemList;

    /**
     * 批次
     */
    private List<OrderInfoItemProductBatchDTO> batchList;
}
