package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.TransportOrders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransportOrdersMapper {

    void insertBatch(@Param("transportOrders") List<TransportOrders> transportOrders);
}
