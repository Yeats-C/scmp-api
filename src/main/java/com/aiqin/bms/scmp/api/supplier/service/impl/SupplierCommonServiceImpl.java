package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.common.StatusTypeCode;
import com.aiqin.bms.scmp.api.config.CommonInterceptor;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogBean;
import com.aiqin.bms.scmp.api.supplier.service.OperationLogService;
import com.aiqin.bms.scmp.api.supplier.service.SupplierCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class SupplierCommonServiceImpl implements SupplierCommonService {
    @Autowired
    private CommonInterceptor commonInterceptor;
    @Autowired
    private OperationLogService operationLogService;
    @Override
    public Long getInstance(String code, Byte handleType, Byte objectType, String content, String remark, String handleName) {
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

        operationLogBean.setContent(content);
        operationLogBean.setRemark(remark);
        operationLogBean.setHandleName(handleName);
       return operationLogService.saveLog(operationLogBean);
    }
    @Override
    public Long getInstance(String code, Byte handleType, Byte objectType, String content, String remark, String handleName,String userName) {
        OperationLogBean operationLogBean = new OperationLogBean();
        Date date = new Date();
        operationLogBean.setDelFlag(StatusTypeCode.UN_DEL_FLAG.getStatus());
        operationLogBean.setCreateTime(date);
        operationLogBean.setCreateBy(userName);
        if (code != null) {
            operationLogBean.setObjectId(code);
        }
        operationLogBean.setHandleType(handleType);
        operationLogBean.setObjectType(objectType);
        operationLogBean.setContent(content);
        operationLogBean.setRemark(remark);
        operationLogBean.setHandleName(handleName);
        return operationLogService.saveLog(operationLogBean);
    }



}
