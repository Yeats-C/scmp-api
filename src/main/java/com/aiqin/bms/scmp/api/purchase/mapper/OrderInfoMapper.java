package com.aiqin.bms.scmp.api.purchase.mapper;


import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.QueryOrderListReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderInfoRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderListRespVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);
    /**
     * 批量插入
     * @author NullPointException
     * @date 2019/6/14
     * @param info
     * @return int
     */
    int insertBatch(List<OrderInfo> info);
    /**
     * 查询订单列表
     * @author NullPointException
     * @date 2019/6/14
     * @param reqVO
     * @return java.util.List<com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderListRespVO>
     */
    List<QueryOrderListRespVO> selectListByQueryVO(QueryOrderListReqVO reqVO);
    /**
     * 通过编码查询订单
     * @author NullPointException
     * @date 2019/6/14
     * @param orderCode
     * @return com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderInfoRespVO
     */
    QueryOrderInfoRespVO selectByOrderCode(String orderCode);

    /**
     * 通过编码查询数据
     * @author NullPointException
     * @date 2019/6/15
     * @param orderCode
     * @return com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo
     */
    OrderInfo selectByOrderCode2(String orderCode);
    /**
     * 通过编码更新数据
     * @author NullPointException
     * @date 2019/6/15
     * @param order
     * @return int
     */
    int updateByOrderCode(OrderInfo order);

    /**
     * 查询销售订单用于sap对接
     * @param sapOrderRequest
     * @return
     */
    List<OrderInfo> listForSap(SapOrderRequest sapOrderRequest);

    int updateByOrderCodes(@Param(value = "list") List<String> orderCodes);
}