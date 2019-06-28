package com.aiqin.bms.scmp.api.purchase.mapper;


import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.TransportLog;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportLogsRequest;

import java.util.List;

public interface TransportLogMapper {
    Integer insertOne(TransportLog transportLog);

    List<TransportLog> selectList(TransportLogsRequest transportLogsRequest);
}
