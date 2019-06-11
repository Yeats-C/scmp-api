package com.aiqin.bms.scmp.api.supplier.dao;


import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierOperationLog;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogBean;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface SupplierOperationLogDao {

    int insert(SupplierOperationLog operationLog);

    List<LogData>getLogType(@Param("objectType") Byte objectType, @Param("objectId") String objectId);

    List<LogData>getLogList(OperationLogBean operationLogBean);

    List<LogData>getList();

    int insertList(Collection<SupplierOperationLog> supplierOperationLogs);


}
