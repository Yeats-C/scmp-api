package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierDictionaryInfo;
import com.aiqin.bms.scmp.api.supplier.domain.response.dictionary.DictionaryInfoResponseVO;

import java.util.List;

public interface SupplierDictionaryInfoService {
    List<SupplierDictionaryInfo>getList(String code);

    List<DictionaryInfoResponseVO> selectPriceTypeAndCategory(Integer status);
}
