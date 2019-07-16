package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierDictionary;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierDictionaryInfo;
import com.aiqin.bms.scmp.api.supplier.domain.request.dictionary.*;
import com.aiqin.bms.scmp.api.supplier.domain.response.DictionaryType;
import com.aiqin.bms.scmp.api.supplier.domain.response.dictionary.DictionaryCodeResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.dictionary.DictionaryDetailResVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.dictionary.DictionaryListResVO;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.Collection;
import java.util.List;

public interface SupplierDictionaryService {
    HttpResponse<DictionaryDetailResVO> getListDictionary(Long id, String project);

    HttpResponse<Integer> batchInsert(DictionarySaveReqDTO listDTO);

    HttpResponse<Integer> batchUpdate(DictionaryUpdateReqDTO listDto);

    HttpResponse<BasePage<DictionaryListResVO>> listDictionary(QueryDictionaryReqVO querDictionaryReqVO, String project);

    HttpResponse<Integer> offOrOn(Long id, String project);

    Long insertSelective(SupplierDictionary record);

    int updateDictionary(SupplierDictionary supplierDictionary);

    HttpResponse<Integer> enabled(EnabledSave enabledSave, String project);

    int insertList(Collection<SupplierDictionaryInfo> list);

    int updateList(Collection<SupplierDictionaryInfo> list);



    List<DictionaryType> getType();

    List<DictionaryCodeResVo> getCode(DictionaryInfoReqVO dictionaryInfoReqVO, String project);

    void projectAndId(Long id, String project);

    void handleException(HttpResponse httpResponse);


}
