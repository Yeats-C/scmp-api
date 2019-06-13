package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.OrderInfoReqVO;

/**
 * Description:
 * 订单接口
 * @author: NullPointException
 * @date: 2019-06-13
 * @time: 17:34
 */
public interface OrderService {
    /**
     * 保存订单
     * @author NullPointException
     * @date 2019/6/13
     * @param reqVO
     * @return java.lang.Boolean
     * @exception
     */
    Boolean save(OrderInfoReqVO reqVO);
    /**
     * 保存订单数据
     * @author NullPointException
     * @date 2019/6/13
     * @param reqVO
     * @param info
     * @return void
     * @exception Exception copy异常
     */
    void saveData(OrderInfoReqVO reqVO, OrderInfo info);
}
