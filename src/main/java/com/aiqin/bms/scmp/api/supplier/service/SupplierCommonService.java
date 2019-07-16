package com.aiqin.bms.scmp.api.supplier.service;


public interface SupplierCommonService {
    Long getInstance(String code, Byte handleType, Byte objectType, String content, String remark, String handleName);

    Long getInstance(String code, Byte handleType, Byte objectType,String content, String remark, String handleName, String userName);

}
