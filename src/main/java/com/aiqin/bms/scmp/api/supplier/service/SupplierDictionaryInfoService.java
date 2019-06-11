package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierDictionaryInfo;

import java.util.List;

public interface SupplierDictionaryInfoService {
    List<SupplierDictionaryInfo>getList(String code);

}
