package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.product.domain.request.order.SupplyOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.Transport;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.TransportOrders;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportOrdersResquest;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransportMapper {

    List<Transport> selectList(TransportRequest transportRequest);

    List<TransportOrders> selectTransportOrdersWithOutCodeList(TransportOrdersResquest transportOrdersResquest);

    List<TransportOrders> selectTransportOrdersList(TransportOrdersResquest transportOrdersResquest);

    List<TransportOrders> selectDeliverOrdersWithOutCodeList(TransportOrdersResquest transportOrdersResquest);

    Integer insertOne(Transport transport);

    SupplyOrderInfo selectOrderOneByOrderCode(String orderCode);

    Transport selectByTransportCode(String transportCode);

    Integer updateStatusByTransportCode(@Param("transportCode") String transportCode, @Param("status") int i);

    List<Transport> selectByTransportCodes(List<String> transportCode);
    /**
     * 批量更新发运状态
     * @author NullPointException
     * @date 2019/6/29
     * @param transportCode
     * @param status
     * @return int
     */
    int updateStatusByTransportCodes(@Param("items") List<String> transportCode, @Param("status") int status);

    void updateTransport(Transport transport);
}
