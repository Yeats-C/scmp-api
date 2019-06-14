package com.aiqin.bms.scmp.api.purchase.mapper;


import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.QueryOrderListReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderListRespVO;

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
}