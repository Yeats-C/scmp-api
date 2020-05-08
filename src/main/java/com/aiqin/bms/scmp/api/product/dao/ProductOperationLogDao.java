package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductOperationLog;
import com.aiqin.bms.scmp.api.product.domain.response.LogData;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface ProductOperationLogDao {

    int insert(ProductOperationLog operationLog);

    List<LogData> getLogType(@Param("objectType") Byte objectType, @Param("objectId") String objectId);

    List<LogData>getList();

    Integer insertList(Collection<ProductOperationLog> productOperationLogs);

    Integer updateByPrimaryKeySelective(ProductOperationLog productOperationLog);

}
