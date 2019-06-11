package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.supplier.dao.dictionary.SupplierDictionaryInfoDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierDictionaryInfo;
import com.aiqin.bms.scmp.api.supplier.service.SupplierDictionaryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierDictionaryInfoServiceImpl implements SupplierDictionaryInfoService {

    @Autowired
    private SupplierDictionaryInfoDao supplierDictionaryInfoDao;

    @Override
    public List<SupplierDictionaryInfo> getList(String code) {
        return   supplierDictionaryInfoDao.dictionaryCode(code);
    }

}
