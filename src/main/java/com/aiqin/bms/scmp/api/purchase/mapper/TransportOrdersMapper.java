package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.TransportOrders;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransportOrdersMapper {

    void insertBatch(@Param("transportOrders") List<TransportOrders> transportOrders);

    List<TransportOrders> selectListByTransportCode(TransportRequest transportRequest);

    List<TransportOrders> selectOrderCodeByTransportCode(@Param("transportCode") String transportCode);

    TransportOrders selectTransportOrdersByTransportCode(@Param("transportCode") String transportCode);
}
