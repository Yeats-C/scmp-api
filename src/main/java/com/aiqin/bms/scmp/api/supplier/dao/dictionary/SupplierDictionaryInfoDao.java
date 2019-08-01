package com.aiqin.bms.scmp.api.supplier.dao.dictionary;

import com.aiqin.bms.scmp.api.purchase.domain.response.InnerValue;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierDictionaryInfo;
import com.aiqin.bms.scmp.api.supplier.domain.response.dictionary.DictionaryInfoResponseVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface SupplierDictionaryInfoDao {

    int deleteByPrimaryKey(Long id);

    int updateAndCode(@Param("name") String name, @Param("code") String code);

    int insertList(Collection<SupplierDictionaryInfo> list);

    int updateList(Collection<SupplierDictionaryInfo> list);

    int deleteList(@Param("code") String code);

    List<DictionaryInfoResponseVO>getCode(@Param("code") String code);

    List<SupplierDictionaryInfo>dictionaryCode(@Param("code") String code);

    int infoEnabled(@Param("enabled") Byte enabled, @Param("code") String code);

    /**
     * 通过字典名称查类型
     * @author NullPointException
     * @date 2019/7/16
     * @param dicName
     * @return java.util.List<com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierDictionaryInfo>
     */
    @MapKey("supplierContent")
    Map<String,SupplierDictionaryInfo> selectByName(@Param("list") List<String> dicName, @Param("companyCode") String companyCode);

    List<InnerValue> allList();
}