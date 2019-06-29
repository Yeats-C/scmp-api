package com.aiqin.bms.scmp.api.purchase.service;


import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.Transport;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.TransportLog;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.TransportOrders;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportAddRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportLogsRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportOrdersResquest;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportRequest;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;

public interface TransportService {
    BasePage<Transport> selectPage(TransportRequest transportRequest);

    BasePage<TransportOrders> selectTransportOrdersPage(TransportOrdersResquest transportOrdersResquest);

    BasePage<TransportOrders> selectDeliverOrdersPage(TransportOrdersResquest transportOrdersResquest);

    HttpResponse saveTransport(TransportAddRequest transportAddRequest);

    HttpResponse deliver(List<String> transportCode);

    HttpResponse sign(List<String> transportCode);

    void updateStatus(List<String> transportCode, Integer status);

    void saveLog(List<TransportLog> logs);

    BasePage<TransportLog> transportLogs(TransportLogsRequest transportLogsRequest);
}
