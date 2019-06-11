package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductOperationLog;
import com.aiqin.bms.scmp.api.product.domain.request.OperationLogBean;
import com.aiqin.bms.scmp.api.product.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.product.domain.response.LogData;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ProductOperationLogService {
    Long saveLog(OperationLogBean operationLogBean);

    Long insert(ProductOperationLog operationLog);


    List<LogData> getLogType(OperationLogVo operationLogVo);

    Map<String,Object> list(Integer page, Integer count);

    int update(ProductOperationLog operationLog);

    Integer saveList(Collection<ProductOperationLog> users);


    List<LogData> selectListByVO(OperationLogBean operationLogBean);


}
