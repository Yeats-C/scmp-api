package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductDictionary;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.DictionaryInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.QueryDictionaryReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.dictionary.DictionaryCodeResVo;
import com.aiqin.bms.scmp.api.product.domain.response.dictionary.DictionaryListResVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品字典
 */
public interface ProductDictionaryDao {

    int insertSelective(ProductDictionary productDictionary);

    int offOrOn(Long id);

    List<DictionaryListResVO>listDictionary(QueryDictionaryReqVO querDictionaryReqVO);

    ProductDictionary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductDictionary record);

    Integer checkName(@Param("productName") String productName, @Param("id") Long id, @Param("companyCode") String companyCode);

    List<DictionaryCodeResVo>getCode(DictionaryInfoReqVO dictionaryInfoReqVO);

}
