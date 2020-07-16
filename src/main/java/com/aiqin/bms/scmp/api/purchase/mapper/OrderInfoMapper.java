package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.QueryOrderListReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderInfoRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderListRespVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface OrderInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

    /**  批量插入*/
    int insertBatch(List<OrderInfo> info);

    /*** 查询订单列表*/
    List<QueryOrderListRespVO> selectListByQueryVO(QueryOrderListReqVO reqVO);

    /** 通过编码查询订单*/
    QueryOrderInfoRespVO selectByOrderCode(String orderCode);

    /** 通过编码查询数据*/
    OrderInfo selectByOrderCode2(String orderCode);

    /** 通过编码更新数据*/
    int updateByOrderCode(OrderInfo order);

    /** 查询销售订单用于sap对接*/
    List<OrderInfo> listForSap(SapOrderRequest sapOrderRequest);

    List<OrderInfo> listByIds(@Param(value = "list")List<String> orderIds);

    Integer updateByOrderCodes(@Param(value = "list") List<String> orderCodes);

    Integer updateBatch(@Param(value = "list") List<OrderInfo> list);

    Integer updateDevliverAmountByOrderCode(@Param("orderCode") String orderCode, @Param("deliverAmount") BigDecimal deliverAmount);

    /** 销售订单同步sap **/
    void updateOrderSynchrSap(OrderInfo order);

    List<OrderInfo> selectByOrderCodes(@Param("list") List<String> orderCodeList);

    int insertMany(@Param("list") List<OrderInfo> orderInfos1);
}