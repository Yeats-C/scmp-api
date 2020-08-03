package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.dto.order.OrderInfoDTO;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoLog;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderInfoRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderListRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderProductListRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryProductUniqueCodeListRespVO;
import com.aiqin.bms.scmp.api.purchase.service.impl.OrderServiceImpl;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;

/**
 * Description:
 * 订单接口
 * @author: NullPointException
 * @date: 2019-06-13
 * @time: 17:34
 */
public interface OrderService {

    /**保存订单*/
    Boolean save(List<OrderInfoReqVO> reqVO);

    /**推送到库房*/
    void sendOrderToOutBound(List<OrderInfoDTO> dto);

    /** 锁定批次库存*/
    void lockBatchStock(List<OrderInfo> orders, List<OrderInfoItem> orderItems, OrderServiceImpl service);

    /** 保存锁定的信息*/
    void saveLockBatch(List<OrderInfoItemProductBatch> list);

    /** 保存日志*/
    void saveLog(List<OrderInfoLog> logs);

    /** 保存订单数据*/
    void saveData(List<OrderInfoItem> infoItems, List<OrderInfo> info);

    /**  订单列表*/
    BasePage<QueryOrderListRespVO> list(QueryOrderListReqVO reqVO);

    /** 详情*/
    QueryOrderInfoRespVO view(String orderCode);

    /** 改变订单状态*/
    Boolean changeStatus(ChangeOrderStatusReqVO reqVO);

    /** 通过编码更新*/
    void updateByOrderCode(OrderInfo order);

    /**  配货或者发货*/
    Boolean distribution(String orderCode,Integer status);

    /**  TODO 订单发货后传给结算系统 */
    void sendToSettlement();

    /** TODO 订单完成后传状态给结算系统*/
    void sendStatusToSettlement();

    /** 订单商品列表*/
    BasePage<QueryOrderProductListRespVO> orderProductList(QueryOrderProductListReqVO reqVO);

    /** 商品唯一码*/
    BasePage<QueryProductUniqueCodeListRespVO> productUniqueCodeList(QueryProductUniqueCodeListReqVO reqVO);

    /** 直送修改数量*/
    Boolean delivery(DeliveryReqVO reqVO);

    /** 编码查询订单*/
    OrderInfo selectByOrderCode(String orderCode);

    HttpResponse insertSaleOrder(ErpOrderInfo vo);

    HttpResponse orderCancel(String orderCode, String operatorId, String operatorName);

    HttpResponse insertWmsOrder(List<String> orderCodes);
}
