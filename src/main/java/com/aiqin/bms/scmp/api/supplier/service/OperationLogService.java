package com.aiqin.bms.scmp.api.supplier.service;


import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierOperationLog;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogBean;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;

import java.util.Collection;
import java.util.List;

public interface OperationLogService {

    Long saveLog(OperationLogBean operationLogBean);

    Long insert(SupplierOperationLog operationLog);


    BasePage<LogData> getLogType(OperationLogVo operationLogVo, Integer project);

    BasePage<LogData> list(Integer page, Integer count);


    int update(SupplierOperationLog operationLog);

    Integer inserList(Collection<SupplierOperationLog> users);


    List<LogData> selectListByVO(OperationLogBean operationLogBean);



}
