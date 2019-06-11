package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductDictionaryInfo;
import com.aiqin.bms.scmp.api.product.domain.response.dictionary.DictionaryInfoResponseVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface ProductDictionaryInfoDao {
    int updateAndCode(@Param("name") String name, @Param("code") String code);

    int infoEnabled(@Param("enabled") Byte enabled, @Param("code") String code);

    int insertList(@Param("list") Collection<ProductDictionaryInfo> list);

    int updateList(Collection<ProductDictionaryInfo> list);

    int deleteList(@Param("code") String code);

    List<DictionaryInfoResponseVO> getCode(@Param("code") String code);


    List<ProductDictionaryInfo>dictionaryCode(@Param("code") String code);

    int updateByPrimaryKeySelective(ProductDictionaryInfo record);

    ProductDictionaryInfo selectByPrimaryKey(Long id);
}
