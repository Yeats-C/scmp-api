package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.QueryOrderProductListReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderProductListRespVO;

import java.util.List;

public interface OrderInfoItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInfoItem record);

    int insertSelective(OrderInfoItem record);

    OrderInfoItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfoItem record);

    int updateByPrimaryKey(OrderInfoItem record);
    /**
     * 批量插入数据
     * @author NullPointException
     * @date 2019/6/13
     * @param items
     * @return int
     */
    int insertBatch(List<OrderInfoItem> items);
    /**
     * 订单商品查询
     * @author NullPointException
     * @date 2019/6/17
     * @param reqVO
     * @return java.util.List<com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderProductListRespVO>
     */
    List<QueryOrderProductListRespVO> selectOrderProductList(QueryOrderProductListReqVO reqVO);
}