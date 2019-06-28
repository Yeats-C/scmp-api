package com.aiqin.bms.scmp.api.purchase.service;


import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.Transport;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.TransportLog;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.TransportOrders;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportAddRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportLogsRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportOrdersResquest;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportRequest;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.github.pagehelper.PageInfo;

public interface TransportService {
    PageInfo<Transport> selectPage(TransportRequest transportRequest);

    PageInfo<TransportOrders> selectTransportOrdersPage(TransportOrdersResquest transportOrdersResquest);

    PageInfo<TransportOrders> selectDeliverOrdersPage(TransportOrdersResquest transportOrdersResquest);

    HttpResponse saveTransport(TransportAddRequest transportAddRequest);

    HttpResponse deliver(String transportCode);

    HttpResponse sign(String transportCode);

    PageInfo<TransportLog> transportLogs(TransportLogsRequest transportLogsRequest);
}
