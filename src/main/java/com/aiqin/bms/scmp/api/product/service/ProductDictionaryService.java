package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductDictionary;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductDictionaryInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductOperationLog;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.DictionaryInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.DictionarySaveReqDTO;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.DictionaryUpdateReqDTO;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.QueryDictionaryReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.dictionary.DictionaryCodeResVo;
import com.aiqin.bms.scmp.api.product.domain.response.dictionary.DictionaryDetailResVO;
import com.aiqin.bms.scmp.api.product.domain.response.dictionary.DictionaryListResVO;

import java.util.Collection;
import java.util.List;

public interface ProductDictionaryService {

    int batchInsert(DictionarySaveReqDTO productDictionarySaveReqDTO);

    DictionaryDetailResVO getListDictionary(Long id);

    int batchUpdate(DictionaryUpdateReqDTO listDto);

    BasePage<DictionaryListResVO> listDictionary(QueryDictionaryReqVO querDictionaryReqVO);

    List<ProductDictionaryInfo> getList(String code);

    int offOrOn(Long id);

    int insertSelective(ProductDictionary record);

    int updateDictionary(ProductDictionary supplierDictionary);

    int enabled(Long id, Byte status);

    int insertList(Collection<ProductDictionaryInfo> list);

    int updateList(Collection<ProductDictionaryInfo> list);

    /**
     *
     * @return
     */
    List<ProductOperationLog> conversion(List<ProductDictionaryInfo> supplierDictionaryInfos, String status);

    List<DictionaryCodeResVo>getCode(DictionaryInfoReqVO dictionaryName);


}
