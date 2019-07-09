package com.aiqin.bms.scmp.api.supplier.service;


import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierOperationLog;

import java.util.Collection;

public interface SupplierCommonService {
    Long getInstance(String code, Byte handleType, Byte objectType, Object josn, String handleName);

    Long getInstance(String code, Byte handleType, Byte objectType, Object josn, String handleName, String userName);

    int saveList(Collection<SupplierOperationLog> collection);
}
