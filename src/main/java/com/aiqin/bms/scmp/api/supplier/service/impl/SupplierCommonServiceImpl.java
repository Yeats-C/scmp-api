package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.config.CommonInterceptor;
import com.aiqin.bms.scmp.api.common.StatusTypeCode;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierOperationLog;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogBean;
import com.aiqin.bms.scmp.api.supplier.service.SupplierCommonService;
import com.aiqin.bms.scmp.api.supplier.service.OperationLogService;
import com.aiqin.bms.scmp.api.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

@Service
public class SupplierCommonServiceImpl implements SupplierCommonService {
    @Autowired
    private CommonInterceptor commonInterceptor;
    @Autowired
    private OperationLogService operationLogService;
    @Override
    public Long getInstance(String code, Byte handleType, Byte objectType, Object josn, String handleName) {
        OperationLogBean operationLogBean = new OperationLogBean();
        Map<String, String> map = commonInterceptor.getRequest();
        Date date = new Date();
        String userName = null;
        if (map != null) {
            userName = map.get("userName");
        }
        operationLogBean.setDelFlag(StatusTypeCode.UN_DEL_FLAG.getStatus());
        operationLogBean.setCreateTime(date);
        operationLogBean.setCreateBy(userName);
        if (code != null) {
            operationLogBean.setObjectId(code);
        }
        operationLogBean.setHandleType(handleType);
        operationLogBean.setObjectType(objectType);
        if(josn!=null) {
            String contentJson = JsonMapper.toJsonString(josn);
            operationLogBean.setContent(contentJson);
        }
        operationLogBean.setHandleName(handleName);
       return operationLogService.saveLog(operationLogBean);
    }

    @Override
    public int saveList(Collection<SupplierOperationLog> collection) {
        Map<String, String> map = commonInterceptor.getRequest();
        Date date = new Date();
        String userName = null;
        if (map != null) {
            userName = map.get("userName");
        }
        if(collection!=null && collection.size()>1){
            String finalUserName = userName;
            collection.forEach(collecti->{
                collecti.setDelFlag((byte)0);
                collecti.setCreateTime(date);
                collecti.setCreateBy(finalUserName);
            });
        }
        return operationLogService.inserList(collection);
    }


}
