package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.HandlingExceptionCode;
import com.aiqin.bms.scmp.api.config.CommonInterceptor;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductOperationLog;
import com.aiqin.bms.scmp.api.product.domain.request.OperationLogBean;
import com.aiqin.bms.scmp.api.product.service.ProductCommonService;
import com.aiqin.bms.scmp.api.product.service.ProductOperationLogService;
import com.aiqin.bms.scmp.api.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

@Service
public class ProductCommonServiceImpl implements ProductCommonService {
    @Autowired
    private CommonInterceptor commonInterceptor;
    @Autowired
    private ProductOperationLogService productOperationLogService;
    @Override
    public Long getInstance(String code, Byte handleType, Byte objectType, Object josn, String handleName) {
        OperationLogBean operationLogBean = new OperationLogBean();
        Map<String, String> map = commonInterceptor.getRequest();
        Date date = new Date();
        String userName = null;
        if (map != null) {
            userName = map.get("userName");
        }
        operationLogBean.setDelFlag((byte) 0);
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
        return productOperationLogService.saveLog(operationLogBean);
    }

    @Override
    public Long instanceThreeParty(String code, Byte handleType, Byte objectType, Object josn, String handleName, Date createTime, String createBy) {
        OperationLogBean operationLogBean = new OperationLogBean();
        operationLogBean.setDelFlag(HandlingExceptionCode.ZERO);
        operationLogBean.setCreateTime(createTime);
        operationLogBean.setCreateBy(createBy);
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
        return productOperationLogService.saveLog(operationLogBean);
    }

    @Override
    public Integer saveList(Collection<ProductOperationLog> collection) {
        Map<String, String> map = commonInterceptor.getRequest();
        Date date = new Date();
        String userName = null;
        if (map != null) {
            userName = map.get("userName");
        }
        if(collection!=null && collection.size()>1){
            String finalUserName = userName;
            collection.forEach(collecti->{
                collecti.setDelFlag(HandlingExceptionCode.ZERO);
                collecti.setCreateTime(date);
                collecti.setCreateBy(finalUserName);
            });
        }
        return productOperationLogService.saveList(collection);
    }
}
