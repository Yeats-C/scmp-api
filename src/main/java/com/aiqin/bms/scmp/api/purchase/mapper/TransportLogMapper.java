package com.aiqin.bms.scmp.api.purchase.mapper;


import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.TransportLog;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportLogsRequest;

import java.util.List;

public interface TransportLogMapper {
    Integer insertOne(TransportLog transportLog);

    List<TransportLog> selectList(TransportLogsRequest transportLogsRequest);
    /**
     * 批量插入日志
     * @author NullPointException
     * @date 2019/6/29
     * @param logs
     * @return int
     */
    int insertBatch(List<TransportLog> logs);
}
