package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoLog;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.ChangeOrderStatusReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.OrderInfoReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.QueryOrderListReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderInfoRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderListRespVO;

import java.util.List;

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
    Boolean save(List<OrderInfoReqVO> reqVO);

    void saveLog(List<OrderInfoLog> logs);

    /**
     * 保存订单数据
     * @author NullPointException
     * @date 2019/6/13
     * @param reqVO
     * @param info
     * @return void
     * @exception Exception copy异常
     */
    void saveData(List<OrderInfoItem> infoItems, List<OrderInfo> info);
    /**
     * 订单列表
     * @author NullPointException
     * @date 2019/6/14
     * @param reqVO
     * @return com.aiqin.bms.scmp.api.base.BasePage<com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderListRespVO>
     */
    BasePage<QueryOrderListRespVO> list(QueryOrderListReqVO reqVO);
    /**
     * 详情
     * @author NullPointException
     * @date 2019/6/14
     * @param orderCode
     * @return com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderInfoRespVO
     */
    QueryOrderInfoRespVO view(String orderCode);
    /**
     * 改变订单状态
     * @author NullPointException
     * @date 2019/6/15
     * @param reqVO
     * @return java.lang.Boolean
     */
    Boolean changeStatus(ChangeOrderStatusReqVO reqVO);

    void updateByOrderCode(OrderInfo order);
}
