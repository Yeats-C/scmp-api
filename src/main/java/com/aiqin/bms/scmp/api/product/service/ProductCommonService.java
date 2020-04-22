package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductOperationLog;

import java.util.Collection;
import java.util.Date;

public interface ProductCommonService {

    Long getInstance(String code, Byte handleType, Byte objectType, Object josn, String handleName);

    /**
     * 三方日志
     * 门店订单
     */
     Long instanceThreeParty(String code, Byte handleType, Byte objectType, Object josn, String handleName, Date createTime, String createBy, String remark);

    Integer saveList(Collection<ProductOperationLog> collection);
}
