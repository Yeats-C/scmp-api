package com.aiqin.bms.scmp.api.supplier.dao.dictionary;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierDictionary;
import com.aiqin.bms.scmp.api.supplier.domain.request.dictionary.DictionaryInfoReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.dictionary.QueryDictionaryReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.dictionary.SupplierDictionaryReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.dictionary.DictionaryCodeResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.dictionary.DictionaryListResVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupplierDictionaryDao {

    List<DictionaryListResVO> listDictionary(QueryDictionaryReqVO querDictionaryReqVO);

    int offOrOn(Long id);

    int insert(SupplierDictionaryReqVO record);

    int insertSelective(SupplierDictionary record);

    Integer checkName(@Param("supplierDictionaryName") String supplierDictionaryName, @Param("id") Long id, @Param("companyCode") String companyCode);

    Integer checkCode(@Param("supplierDictionaryCode") String supplierDictionaryCode, @Param("id") Long id, @Param("companyCode") String companyCode);

    List<DictionaryCodeResVo>getCode(DictionaryInfoReqVO dictionaryInfoReqVO);


}