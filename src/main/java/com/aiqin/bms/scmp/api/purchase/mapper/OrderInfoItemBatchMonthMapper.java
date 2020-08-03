package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemBatchMonth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderInfoItemBatchMonthMapper {

    OrderInfoItemBatchMonth selectByPrimaryKey(Long id);

    int insert(OrderInfoItemBatchMonth record);

    int insertSelective(OrderInfoItemBatchMonth record);

    Integer insertList(@Param(value = "list") List<OrderInfoItemBatchMonth> detailList);

    List<OrderInfoItemBatchMonth> orderBatchMonthList(@Param("orderCode") String orderCode);

}
